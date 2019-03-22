package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.FriendDescribeDialog;
import com.yiwo.friendscometogether.custom.HuoZanDialog;
import com.yiwo.friendscometogether.model.KVMode;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonSameLabelAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYePicsAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJuAdapter;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.pages.OtherPicActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonMainActivity extends BaseActivity {

    @BindView(R.id.recycler_view_labels)
    NestedRecyclerView recycler_view_labels;
    @BindView(R.id.recycler_view_pics)
    RecyclerView recyclerView_pics;
    @BindView(R.id.recycler_view_youji)
    RecyclerView recyclerView_youji;
    @BindView(R.id.recycler_view_youju)
    RecyclerView recyclerView_youju;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    // 他人主页 或 我的主页 有区别的控件；
    @BindView(R.id.tv_label_wode_or_tade)
    TextView tv_label_wode_or_tade;
    @BindView(R.id.tv_title_wode_or_tade)
    TextView tv_title_wode_or_tade;
    @BindView(R.id.tv_pics_wode_or_tade)
    TextView tv_pics_wode_or_tade;
    @BindView(R.id.tv_youji_wode_or_tade)
    TextView tv_youji_wode_or_tade;
    @BindView(R.id.tv_youju_wode_or_tade)
    TextView tv_youju_wode_or_tade;
    //头像右侧控件
    @BindView(R.id.rl_algin_right_tade)
    RelativeLayout rl_algin_right_tade;
    @BindView(R.id.rl_algin_right_wode)
    RelativeLayout rl_algin_right_wode;

    @BindView(R.id.iv_person_icon)
    ImageView iv_person_icon;
    @BindView(R.id.iv_person_sex)
    ImageView iv_person_sex;
    @BindView(R.id.tv_person_name)
    TextView tv_person_name;
    @BindView(R.id.tv_person_age)
    TextView tv_person_age;
    @BindView(R.id.tv_person_address)
    TextView tv_person_address;
    @BindView(R.id.tv_person_sign_text)
    TextView tv_person_sign_text;
    @BindView(R.id.tv_guanzhu_num)
    TextView tv_guanzhu_num;
    @BindView(R.id.tv_huozan_num)
    TextView tv_huozan_num;
    @BindView(R.id.tv_fans_num)
    TextView tv_fans_num;
    @BindView(R.id.iv_addfriend)
    ImageView iv_addfriend;
    @BindView(R.id.iv_image_guanzhu)
    ImageView iv_image_guanzhu;
    @BindView(R.id.iv_heart)
    ImageView iv_image_heart;

    @BindView(R.id.rl_label_text)
    RelativeLayout rl_label_text;
    @BindView(R.id.rl_pics_text)
    RelativeLayout rl_pics_text;
    @BindView(R.id.rl_youji_text)
    RelativeLayout rl_youji_text;
    @BindView(R.id.rl_youju_text)
    RelativeLayout rl_youju_text;

    @BindView(R.id.person_main_refreshLayout)
    RefreshLayout refreshLayout;
    private String ta = "他";
    private SpImp spImp;
    private int type_tade_or_wode = 0;//0 为他的 1 为我的
    private String person_id;
    private String  status; //status   =0时传 用户ID    =1时传网易ID
    private PersonMainModel model;
    private List<PersonMainModel.ObjBean.PhotoBean> list_pics = new ArrayList<>();
    private List<PersonMainModel.ObjBean.FriendBean> list_youji = new ArrayList<>();
    private List<PersonMainModel.ObjBean.ActivityBean> list_youju = new ArrayList<>();

    private List<KVMode> list_label = new ArrayList<>();
    private int isFollow = -1;//是否关注 0为未关注 1为已关注

    private TaRenZhuYePicsAdapter taRenZhuYePicsAdapter;
    private TaRenZhuYeYouJiAdapter taRenZhuYeYouJiAdapter;
    private TaRenZhuYeYouJuAdapter  taRenZhuYeYouJuAdapter;
    private PersonSameLabelAdapter personSameLabelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonMainActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        if (getIntent().getStringExtra("status") == null||getIntent().getStringExtra("status").equals("")){
            status = "0";
        }else {
            status = getIntent().getStringExtra("status");
        }
        Log.d("person_id", person_id);
        spImp = new SpImp(PersonMainActivity.this);
        if (spImp.getUID().equals(person_id)||spImp.getYXID().equals(person_id)) {
            type_tade_or_wode = 1;
            recycler_view_labels.setVisibility(View.GONE);
            rl_label_text.setVisibility(View.GONE);
        } else {
            type_tade_or_wode = 0;
            recycler_view_labels.setVisibility(View.VISIBLE);
            rl_label_text.setVisibility(View.VISIBLE);
        }
        //---------------先设置label 的 manager-------------
        FlowLayoutManager managerFlow = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recycler_view_labels.setLayoutManager(managerFlow);
//        ----------------------------------------------------
        Log.d("personIDIDID",person_id);
        initData();
        if (type_tade_or_wode == 0) {
//            iv_image_heart.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(PersonMainActivity.this).load(R.drawable.sayhi).apply(options).into(iv_image_heart);
            rl_algin_right_tade.setVisibility(View.VISIBLE);
            rl_algin_right_wode.setVisibility(View.GONE);
        } else if (type_tade_or_wode == 1) {
            tv_label_wode_or_tade.setText("我的标签");
            tv_title_wode_or_tade.setText("我的主页");
            tv_pics_wode_or_tade.setText("我的照片");
            tv_youji_wode_or_tade.setText("我的友记");
            tv_youju_wode_or_tade.setText("我的友聚");
//            iv_image_heart.setVisibility(View.GONE);
            rl_algin_right_tade.setVisibility(View.GONE);
            rl_algin_right_wode.setVisibility(View.VISIBLE);
        }
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(PersonMainActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.personMain)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.personMain))
                        .addParam("uid", spImp.getUID())
                        .addParam("tid", person_id)
                        .addParam("status",status)
                        .addParam("type", "0")//0 为5条  1为全部
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        model = gson.fromJson(data, PersonMainModel.class);
                                        Glide.with(PersonMainActivity.this).load(model.getObj().getInfo().getUserpic()).into(iv_person_icon);
                                        if (model.getObj().getPhoto().size() == 0){
                                            rl_pics_text.setVisibility(View.GONE);
                                        }
                                        if (model.getObj().getFriend().size() == 0){
                                            rl_youji_text.setVisibility(View.GONE);
                                        }
                                        if (model.getObj().getActivity().size() == 0){
                                            rl_youju_text.setVisibility(View.GONE);
                                        }

                                        if (model.getObj().getInfo().getSex().equals("0")) {
                                            ta = "他";
                                            if (type_tade_or_wode == 0) {
                                                tv_title_wode_or_tade.setText(ta + "的主页");
                                                tv_pics_wode_or_tade.setText(ta + "的照片");
                                                tv_youji_wode_or_tade.setText(ta + "的友记");
                                                tv_youju_wode_or_tade.setText(ta + "的友聚");
                                            }
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.tarenhzuye_icon_boy).into(iv_person_sex);
                                        } else if (model.getObj().getInfo().getSex().equals("1")) {
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.tarenhzuye_icon_girl).into(iv_person_sex);
                                            ta = "她";
                                            if (type_tade_or_wode == 0) {
                                                tv_title_wode_or_tade.setText(ta + "的主页");
                                                tv_pics_wode_or_tade.setText(ta + "的照片");
                                                tv_youji_wode_or_tade.setText(ta + "的友记");
                                                tv_youju_wode_or_tade.setText(ta + "的友聚");
                                            }
                                        }
                                        tv_person_name.setText(model.getObj().getInfo().getUsername());
                                        tv_person_age.setText(model.getObj().getInfo().getAge());
                                        tv_person_address.setText(model.getObj().getInfo().getAddress());
                                        tv_person_sign_text.setText(model.getObj().getInfo().getAutograph());
                                        tv_guanzhu_num.setText(model.getObj().getInfo().getUserlike());
                                        tv_huozan_num.setText(model.getObj().getInfo().getGiveCount() + "");
                                        tv_fans_num.setText(model.getObj().getInfo().getFans());
                                        if (model.getObj().getInfo().getFriends().equals("1")) {
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                        } else if (model.getObj().getInfo().getFriends().equals("0")) {
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_tianjiahaoyou).into(iv_addfriend);

                                        }
                                        if (model.getObj().getInfo().getFollow().equals("0")) {
                                            isFollow = 0 ;
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                        } else if (model.getObj().getInfo().getFollow().equals("1")) {
                                            isFollow = 1 ;
                                            Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                        }
                                        //--------共同标签----------------------
                                        PersonMainModel.ObjBean.UsertagBean.SameBean sameBean = model.getObj().getUsertag().getSame();
                                        list_label.clear();
                                        String[] strings1 = sameBean.getPersonality().split(",");
                                        String[] strings2 = sameBean.getMotion().split(",");
                                        String[] strings3 = sameBean.getMusic().split(",");
                                        String[] strings4 = sameBean.getDelicious().split(",");
                                        String[] strings5 = sameBean.getFilm().split(",");
                                        String[] strings6 = sameBean.getBook().split(",");
                                        String[] strings7 = sameBean.getTravel().split(",");
                                        list_label.addAll(addLabelToList(1,strings1));
                                        list_label.addAll(addLabelToList(2,strings2));
                                        list_label.addAll(addLabelToList(3,strings3));
                                        list_label.addAll(addLabelToList(4,strings4));
                                        list_label.addAll(addLabelToList(5,strings5));
                                        list_label.addAll(addLabelToList(6,strings6));
                                        list_label.addAll(addLabelToList(7,strings7));
                                        personSameLabelAdapter.notifyDataSetChanged();
                                        //--------照片-------------------------
                                        list_pics.clear();
                                        list_pics.addAll(model.getObj().getPhoto());
                                        taRenZhuYePicsAdapter.notifyDataSetChanged();

                                        //----------友记---------------
                                        LinearLayoutManager manager1 = new LinearLayoutManager(PersonMainActivity.this) {
                                            @Override
                                            public boolean canScrollVertically() {
                                                return false;
                                            }
                                        };
                                        list_youji.clear();
                                        list_youji.addAll(model.getObj().getFriend());
                                        taRenZhuYeYouJiAdapter.notifyDataSetChanged();

                                        //-----------友聚-------------
                                        LinearLayoutManager manager2 = new LinearLayoutManager(PersonMainActivity.this) {
                                            @Override
                                            public boolean canScrollVertically() {
                                                return false;
                                            }
                                        };
                                        list_youju.clear();
                                        list_youju.addAll(model.getObj().getActivity());
                                        taRenZhuYeYouJuAdapter.notifyDataSetChanged();
                                    }
                                    refreshLayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
    }

    private void initData() {

        ViseHttp.POST(NetConfig.personMain)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.personMain))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .addParam("type", "0")//0 为5条  1为全部
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, PersonMainModel.class);
                                Glide.with(PersonMainActivity.this).load(model.getObj().getInfo().getUserpic()).into(iv_person_icon);
                                if (model.getObj().getPhoto().size() == 0){
                                    rl_pics_text.setVisibility(View.GONE);
                                }
                                if (model.getObj().getFriend().size() == 0){
                                    rl_youji_text.setVisibility(View.GONE);
                                }
                                if (model.getObj().getActivity().size() == 0){
                                    rl_youju_text.setVisibility(View.GONE);
                                }

                                if (model.getObj().getInfo().getSex().equals("0")) {
                                    ta = "他";
                                    if (type_tade_or_wode == 0) {
                                        tv_title_wode_or_tade.setText(ta + "的主页");
                                        tv_pics_wode_or_tade.setText(ta + "的照片");
                                        tv_youji_wode_or_tade.setText(ta + "的友记");
                                        tv_youju_wode_or_tade.setText(ta + "的友聚");
                                    }
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.tarenhzuye_icon_boy).into(iv_person_sex);
                                } else if (model.getObj().getInfo().getSex().equals("1")) {
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.tarenhzuye_icon_girl).into(iv_person_sex);
                                    ta = "她";
                                    if (type_tade_or_wode == 0) {
                                        tv_title_wode_or_tade.setText(ta + "的主页");
                                        tv_pics_wode_or_tade.setText(ta + "的照片");
                                        tv_youji_wode_or_tade.setText(ta + "的友记");
                                        tv_youju_wode_or_tade.setText(ta + "的友聚");
                                    }
                                }
                                tv_person_name.setText(model.getObj().getInfo().getUsername());
                                tv_person_age.setText(model.getObj().getInfo().getAge());
                                tv_person_address.setText(model.getObj().getInfo().getAddress());
                                tv_person_sign_text.setText(model.getObj().getInfo().getAutograph());
                                tv_guanzhu_num.setText(model.getObj().getInfo().getUserlike());
                                tv_huozan_num.setText(model.getObj().getInfo().getGiveCount() + "");
                                tv_fans_num.setText(model.getObj().getInfo().getFans());
                                if (model.getObj().getInfo().getFriends().equals("1")) {
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                } else if (model.getObj().getInfo().getFriends().equals("0")) {
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_tianjiahaoyou).into(iv_addfriend);

                                }
                                if (model.getObj().getInfo().getFollow().equals("0")) {
                                    isFollow = 0 ;
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                } else if (model.getObj().getInfo().getFollow().equals("1")) {
                                    isFollow = 1 ;
                                    Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                }
                                //---------共同标签-----------------------------
                                PersonMainModel.ObjBean.UsertagBean.SameBean sameBean = model.getObj().getUsertag().getSame();

                                String[] strings1 = sameBean.getPersonality().split(",");
                                String[] strings2 = sameBean.getMotion().split(",");
                                String[] strings3 = sameBean.getMusic().split(",");
                                String[] strings4 = sameBean.getDelicious().split(",");
                                String[] strings5 = sameBean.getFilm().split(",");
                                String[] strings6 = sameBean.getBook().split(",");
                                String[] strings7 = sameBean.getTravel().split(",");
                                list_label.addAll(addLabelToList(1,strings1));
                                list_label.addAll(addLabelToList(2,strings2));
                                list_label.addAll(addLabelToList(3,strings3));
                                list_label.addAll(addLabelToList(4,strings4));
                                list_label.addAll(addLabelToList(5,strings5));
                                list_label.addAll(addLabelToList(6,strings6));
                                list_label.addAll(addLabelToList(7,strings7));
                                personSameLabelAdapter =new PersonSameLabelAdapter(list_label);
                                recycler_view_labels.setAdapter(personSameLabelAdapter);
                                //--------照片-------------------------
                                list_pics.addAll(model.getObj().getPhoto());
                                GridLayoutManager manager = new GridLayoutManager(PersonMainActivity.this, 3) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView_pics.setLayoutManager(manager);
                                taRenZhuYePicsAdapter = new TaRenZhuYePicsAdapter(list_pics, person_id);
                                recyclerView_pics.setAdapter(taRenZhuYePicsAdapter);
                                //----------友记---------------
                                LinearLayoutManager manager1 = new LinearLayoutManager(PersonMainActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                list_youji.addAll(model.getObj().getFriend());
                                recyclerView_youji.setLayoutManager(manager1);
                                taRenZhuYeYouJiAdapter = new TaRenZhuYeYouJiAdapter(list_youji);
                                recyclerView_youji.setAdapter(taRenZhuYeYouJiAdapter);
                                //-----------友聚-------------
                                LinearLayoutManager manager2 = new LinearLayoutManager(PersonMainActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                list_youju.addAll(model.getObj().getActivity());
                                recyclerView_youju.setLayoutManager(manager2);
                                taRenZhuYeYouJuAdapter = new TaRenZhuYeYouJuAdapter(list_youju);
                                recyclerView_youju.setAdapter(taRenZhuYeYouJuAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private List<KVMode> addLabelToList(int i ,String[] strings) {
        List<KVMode> list  =new ArrayList<>();
        for (int j = 0 ;j<strings.length;j++){
            if (TextUtils.isEmpty(strings[j])) continue;
            KVMode kvMode = new KVMode();
            kvMode.setI(i);
            kvMode.setString(strings[j]);
            list.add(kvMode);
        }
        return list;
    }

    @OnClick({R.id.rl_back,R.id.rl_label_text, R.id.ll_person_all_pics, R.id.ll_person_all_youji, R.id.ll_person_all_youju,
            R.id.rl_algin_right_wode, R.id.rl_add_friend, R.id.rl_guanzhu,R.id.iv_heart,R.id.ll_huozan,R.id.ll_guanzhu,R.id.ll_fans})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_label_text:
                if (type_tade_or_wode == 0){
                    intent.setClass(PersonMainActivity.this,PersonLabelActivity.class);
                    intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    intent.putExtra("userSex",ta);
                    intent.putExtra("labelData",model.getObj().getUsertag());
                    startActivity(intent);
                }else {
                    intent.setClass(PersonMainActivity.this, EditorLabelActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_person_all_pics:
                if (type_tade_or_wode == 1) {
                    intent.setClass(PersonMainActivity.this, MyPicturesActivity.class);
                } else if (type_tade_or_wode == 0) {
                    intent.putExtra("otheruid", person_id);
                    intent.setClass(PersonMainActivity.this, OtherPicActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.ll_person_all_youji:
                if (model.getObj().getFriend().size() > 0) {
                    intent.setClass(PersonMainActivity.this, PersonRememberActivity.class);
                    intent.putExtra("person_id", person_id);
                    startActivity(intent);
                } else {
                    toToast(PersonMainActivity.this, "他还没有发过友记");
                }
                break;
            case R.id.ll_person_all_youju:
                if (model.getObj().getActivity().size() > 0) {
                    intent.setClass(PersonMainActivity.this, PersonTogetherActivity.class);
                    intent.putExtra("person_id", person_id);
                    startActivity(intent);
                } else {
                    toToast(PersonMainActivity.this, "他还没有参加过友聚");
                }
                break;
            case R.id.rl_algin_right_wode:
                intent.setClass(PersonMainActivity.this, MyFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_add_friend:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    intent.setClass(PersonMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (model.getObj().getInfo().getFriends().equals("1")) {
                        liaotian(model.getObj().getInfo().getWy_accid());
                    } else if (model.getObj().getInfo().getFriends().equals("0")) {
                        FriendDescribeDialog dialog = new FriendDescribeDialog(PersonMainActivity.this);
                        dialog.show();
                        dialog.setOnReturnListener(new FriendDescribeDialog.OnReturnListener() {
                            @Override
                            public void onReturn(String title) {
                                ViseHttp.POST(NetConfig.addFriendsUrl)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addFriendsUrl))
                                        .addParam("uid", spImp.getUID())
                                        .addParam("friendId", person_id)
                                        .addParam("describe", title)
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200) {
                                                        toToast(PersonMainActivity.this, "好友请求已发送");
                                                    } else {
                                                        toToast(PersonMainActivity.this, jsonObject.getString("message"));
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFail(int errCode, String errMsg) {

                                            }
                                        });
                            }
                        });
                    }
                }
                break;
            case R.id.rl_guanzhu:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    intent.setClass(PersonMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (isFollow == 0){
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                .addParam("uid", spImp.getUID())
                                .addParam("likeId", person_id)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(PersonMainActivity.this, "关注成功");
                                                Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                                isFollow = 1;
                                            } else {
                                                toToast(PersonMainActivity.this, jsonObject.getString("message"));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    }else if (isFollow == 1){
                        ViseHttp.POST(NetConfig.removeConcerns)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.removeConcerns))
                                .addParam("uid", spImp.getUID())
                                .addParam("bid", person_id)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(PersonMainActivity.this, "取消关注成功");
                                                Glide.with(PersonMainActivity.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                                isFollow = 0;
                                            } else {
                                                toToast(PersonMainActivity.this, jsonObject.getString("message"));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    }

                }
                break;
            case R.id.iv_heart:
                ViseHttp.POST(NetConfig.sayHello)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.sayHello))
                        .addForm("uid", spImp.getUID())
                        .addForm("bid", person_id)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Log.d("22222", data);
                                        toToast(PersonMainActivity.this, "打招呼成功！");
                                    } else {
                                        toToast(PersonMainActivity.this, "您已经打过招呼了");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
            case R.id.ll_huozan:
                HuoZanDialog dialog = new HuoZanDialog(PersonMainActivity.this,tv_person_name.getText().toString(),tv_huozan_num.getText().toString());
                dialog.show();
                break;
            case R.id.ll_guanzhu:
                if (model.getObj().getInfo().getUserlike().equals("0")){
                    toToast(PersonMainActivity.this,"他还没有关注任何人");
                }else {
                    intent.setClass(PersonMainActivity.this,PersonGuanZhuActivity.class);
                    intent.putExtra("userID",person_id);
                    intent.putExtra("type",1);
                    if (type_tade_or_wode == 1){
                        intent.putExtra("userName","我");
                    }else{
                        intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.ll_fans:
                if (model.getObj().getInfo().getFans().equals("0")){
                    toToast(PersonMainActivity.this,"他还没有粉丝");
                }else {
                    intent.setClass(PersonMainActivity.this,PersonGuanZhuActivity.class);
                    intent.putExtra("userID",person_id);
                    intent.putExtra("type",2);
                    if (type_tade_or_wode == 1){
                        intent.putExtra("userName","我");
                    }else{
                        intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    }
                    startActivity(intent);
                }
                break;
        }


    }

    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(spImp.getYXID());
        NimUIKit.startP2PSession(PersonMainActivity.this, liaotianAccount);
    }
}
