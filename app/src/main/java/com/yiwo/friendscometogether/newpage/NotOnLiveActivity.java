package com.yiwo.friendscometogether.newpage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.DuiZhangShowDialog;
import com.yiwo.friendscometogether.custom.FriendDescribeDialog;
import com.yiwo.friendscometogether.custom.HuoZanDialog;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.KVMode;
import com.yiwo.friendscometogether.model.PersonMain_YouJu_model;
import com.yiwo.friendscometogether.model.PersonMain_Youji_model;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_Video_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_YouJi_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_YouJu_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonSameLabelAdapter;
import com.yiwo.friendscometogether.newmodel.NewPersonMainMode_part1;
import com.yiwo.friendscometogether.newmodel.PersonMain_Videos_Model;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.pages.OtherPicActivity;
import com.yiwo.friendscometogether.pages.WelcomeActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotOnLiveActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    RelativeLayout rl_level;

    @BindView(R.id.rl_tab_2)
    RelativeLayout rl_tab_2;
    @BindView(R.id.rl_tab_3)
    RelativeLayout rl_tab_3;
//    @BindView(R.id.bottom_line_1)
//    View bottom_line_1;
    @BindView(R.id.bottom_line_2)
    View bottom_line_2;
    @BindView(R.id.bottom_line_3)
    View bottom_line_3;
    @BindView(R.id.bottom_line_4)
    View bottom_line_4;

//    @BindView(R.id.rv_pic)
//    RecyclerView rv_pic;
    @BindView(R.id.rv_youji)
    RecyclerView rv_youji;
    @BindView(R.id.rv_youju)
    RecyclerView rv_youju;
    @BindView(R.id.rv_videos)
    RecyclerView rv_videos;

//    @BindView(R.id.rl_pics)
//    RelativeLayout rl_pics;
    @BindView(R.id.rl_youji)
    RelativeLayout rl_youji;
    @BindView(R.id.rl_youju)
    RelativeLayout rl_youju;
    @BindView(R.id.rl_vides)
    RelativeLayout rl_videos;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.person_main_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.btn_gaunzhu)
    TextView btn_gaunzhu;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    private String ta = "他";
    private SpImp spImp;
    private String person_id;// 进入activity传入的id 可能为云信ID
    private String liver_name,liver_icon;
    private String guanzhuLiver;
    private String  status; //status   =0时传 用户ID    =1时传网易ID
    private NewPersonMainMode_part1 model;

    private List<KVMode> list_label = new ArrayList<>();
    private int isFollow = -1;//是否关注 0为未关注 1为已关注

    //recyclerView
    private int show_tab = 2;//1为照片 2为友记 3为友聚 4为视频
    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private int page4 = 1;
//    //照片 //// 去掉照片卡页
//    private PersonMainActivity_Pics_Adapter picsAdapter;
//    private List<PersonMain_Pics_model.ObjBean.PhotoBean> picsList = new ArrayList<>();
    //友记
    private PersonMainActivity_YouJi_Adapter youJiAdapter;
    private List<PersonMain_Youji_model.ObjBean.FriendBean> youJiList = new ArrayList<>();
    //y友聚
    private PersonMainActivity_YouJu_Adapter youJuAdapter;
    private List<PersonMain_YouJu_model.ObjBean.ActivityBean> youJuList = new ArrayList<>();
    //视频
    private PersonMainActivity_Video_Adapter videoAdapter;
    private List<PersonMain_Videos_Model.ObjBean> videosList = new ArrayList<>();

    private PersonSameLabelAdapter personSameLabelAdapter;

    private String start_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_on_live);
//        StatusBarUtils.setStatusBarTransparent(NotOnLiveActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(NotOnLiveActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        start_time = getIntent().getStringExtra("start_time");
        liver_name = getIntent().getStringExtra("liver_name");
        liver_icon = getIntent().getStringExtra("liver_icon");
        guanzhuLiver = getIntent().getStringExtra("guanzhuLiver");
        if (getIntent().getStringExtra("status") == null||getIntent().getStringExtra("status").equals("")){
            status = "0";
        }else {
            status = getIntent().getStringExtra("status");
        }

        Log.d("person_id", person_id);
        spImp = new SpImp(NotOnLiveActivity.this);
//        ----------------------------------------------------
        Log.d("personIDIDID",person_id);
        rl_youji.setVisibility(View.VISIBLE);
        bottom_line_2.setVisibility(View.VISIBLE);
        tv_start_time.setText("敬请期待\n"+start_time+"\n"+"与您相见");
        tv_name.setText(liver_name);
        Glide.with(NotOnLiveActivity.this).load(liver_icon).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(iv_icon);
        btn_gaunzhu.setText(guanzhuLiver.equals("1")?"已关注":"关注");
        if (person_id.equals(spImp.getUID())){
            btn_gaunzhu.setVisibility(View.GONE);
        }else {
            btn_gaunzhu.setVisibility(View.VISIBLE);
        }
        initRecyclerView();
        initRefresh();
    }

    private void initRecyclerView() {

        StaggeredGridLayoutManager mLayoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_youji.setLayoutManager(mLayoutManager2);
        youJiAdapter = new PersonMainActivity_YouJi_Adapter(youJiList);
        rv_youji.setAdapter(youJiAdapter);


        StaggeredGridLayoutManager mLayoutManager3 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_youju.setLayoutManager(mLayoutManager3);
        youJuAdapter = new PersonMainActivity_YouJu_Adapter(youJuList);
        rv_youju.setAdapter(youJuAdapter);

        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_videos.setLayoutManager(mLayoutManager);
        videoAdapter = new PersonMainActivity_Video_Adapter(videosList);
        rv_videos.setAdapter(videoAdapter);
        ViseHttp.POST(NetConfig.homepagePartTwo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asdasfsada_Youji",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_Youji_model model = gson.fromJson(data,PersonMain_Youji_model.class);
                                youJiList.clear();
                                youJiList.addAll(model.getObj().getFriend());
                                youJiAdapter.notifyDataSetChanged();
                                page2 = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        //----------友聚----------
        ViseHttp.POST(NetConfig.homepagePartthree)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asdasfsada_YouJu",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_YouJu_model model = gson.fromJson(data,PersonMain_YouJu_model.class);
                                youJuList.clear();
                                youJuList.addAll(model.getObj().getActivity());
                                youJuAdapter.notifyDataSetChanged();
                                page3 = 3;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        //视频
        ViseHttp.POST(NetConfig.homepageVideos)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homepageVideos))
                .addParam("status", status)
                .addParam("tid",person_id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_Videos_Model model = gson.fromJson(data, PersonMain_Videos_Model.class);
                                page4 = 2;
                                videosList.clear();
                                videosList.addAll(model.getObj());
                                videoAdapter.notifyDataSetChanged();
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

    private void initRefresh() {
        ClassicsFooter footer = new ClassicsFooter(NotOnLiveActivity.this);
//        footer.setPrimaryColor(Color.BLACK);
//        footer.setAccentColor(Color.WHITE);
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (show_tab)
                {
                    case 1:
                        break;
                    case 2:
                        //----------友记----------
                        ViseHttp.POST(NetConfig.homepagePartTwo)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                                .addParam("uid", spImp.getUID())
                                .addParam("tid", person_id)
                                .addParam("status",status)
                                .addParam("page",page2+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.d("asdasfsada_Youji",data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_Youji_model model = gson.fromJson(data,PersonMain_Youji_model.class);
                                                youJiList.addAll(model.getObj().getFriend());
                                                youJiAdapter.notifyDataSetChanged();
                                                page2++;

                                            }
                                            refreshLayout.finishLoadMore(1000);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                    case 3:
                        //----------友聚----------
                        ViseHttp.POST(NetConfig.homepagePartthree)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                                .addParam("uid", spImp.getUID())
                                .addParam("tid", person_id)
                                .addParam("status",status)
                                .addParam("page",page3+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.d("asdasfsada_YouJu",data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_YouJu_model model = gson.fromJson(data,PersonMain_YouJu_model.class);
                                                youJuList.addAll(model.getObj().getActivity());
                                                youJuAdapter.notifyDataSetChanged();
                                                page3++;
                                            }
                                            refreshLayout.finishLoadMore(1000);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                    case 4:
                        ViseHttp.POST(NetConfig.homepageVideos)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homepageVideos))
                                .addParam("status", status)
                                .addParam("page",page4+"")
                                .addParam("tid",person_id)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_Videos_Model model = gson.fromJson(data, PersonMain_Videos_Model.class);
                                                videosList.addAll(model.getObj());
                                                videoAdapter.notifyDataSetChanged();
                                                page4++;
                                                refreshLayout.finishLoadMore(1000);
                                            }
                                        } catch (JSONException e) {
                                            refreshLayout.finishLoadMore(1000);
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                }
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

    @OnClick({R.id.rl_back,R.id.btn_gaunzhu,
            R.id.rl_tab_2,R.id.rl_tab_3,R.id.rl_tab_4})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_gaunzhu:
                guanZhuPerson();
                break;
            case R.id.rl_tab_2:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.VISIBLE);
                bottom_line_3.setVisibility(View.GONE);
                bottom_line_4.setVisibility(View.GONE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.VISIBLE);
                rl_youju.setVisibility(View.GONE);
                rl_videos.setVisibility(View.GONE);
                show_tab = 2;
                break;
            case R.id.rl_tab_3:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.GONE);
                bottom_line_3.setVisibility(View.VISIBLE);
                bottom_line_4.setVisibility(View.GONE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.GONE);
                rl_youju.setVisibility(View.VISIBLE);
                rl_videos.setVisibility(View.GONE);
                show_tab = 3;
                break;
            case R.id.rl_tab_4:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.GONE);
                bottom_line_3.setVisibility(View.GONE);
                bottom_line_4.setVisibility(View.VISIBLE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.GONE);
                rl_youju.setVisibility(View.GONE);
                rl_videos.setVisibility(View.VISIBLE);
                show_tab = 4;
                break;
        }
    }
    // 直播列表关注
    private void guanZhuPerson(){
            if (guanzhuLiver.equals("0")){//未关注
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("likeId", person_id)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("code") == 200) {
                                        btn_gaunzhu.setText("已关注");
                                        Toast.makeText(NotOnLiveActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                    }else if(jsonObject.getInt("code") == 400){

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
}
