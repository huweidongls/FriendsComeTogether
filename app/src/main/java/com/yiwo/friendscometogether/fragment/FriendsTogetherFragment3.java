package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.library.banner.BannerLayout;
import com.google.gson.Gson;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FriendsTogetherFragmentLabelTopAdapter;
import com.yiwo.friendscometogether.newadapter.SwipeFIingViewAdapter;
import com.yiwo.friendscometogether.newmodel.HuoDongShaiXuanMode;
import com.yiwo.friendscometogether.newmodel.YouJuTopLabelModel;
import com.yiwo.friendscometogether.newpage.AllHuoDongActivity;
import com.yiwo.friendscometogether.newpage.YoujuShaixuanActivity;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.swipecard.SwipeFlingView;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/20.
 */

public class FriendsTogetherFragment3 extends BaseFragment {

    @BindView(R.id.swipe_view)
    SwipeFlingView sfv;
    @BindView(R.id.iv_youju_focus)
    ImageView ivFocus;
    @BindView(R.id.rv_label_1)
    RecyclerView rv_label_1;
    @BindView(R.id.rv_label_2)
    RecyclerView rv_label_2;
    @BindView(R.id.rl_label2)
    RelativeLayout rl_label2;
    @BindView(R.id.iv_guanzhu_icon1)
    ImageView iv_guanzhu_icon1;
    @BindView(R.id.iv_guanzhu_icon2)
    ImageView iv_guanzhu_icon2;
    @BindView(R.id.iv_guanzhu_icon3)
    ImageView iv_guanzhu_icon3;
    @BindView(R.id.tv_guanzhu_user_names)
    TextView tv_guanzhu_user_names;
    @BindView(R.id.tv_guanzhu_num)
    TextView tv_guanzhu_num;
    @BindView(R.id.ll_guanzhu_num)
    LinearLayout ll_guanzhu_num;
    @BindView(R.id.rl_myhuodong)
    RelativeLayout rl_myhuodong;
    @BindView(R.id.tv_daikaishi)
    TextView tvDaiKaiShi;

    @BindView(R.id.rlzanwu)
    RelativeLayout rlzanwu;

    @BindView(R.id.rl_ball)
    public RelativeLayout rl_ball;
    private float rl_ball_lastY;
    public boolean downOnBall = false;
    private boolean isShowFloatImage;
    private List<FriendsTogethermodel.ObjBean> mList = new ArrayList<>() ;
    private SwipeFIingViewAdapter adapter;

    private SpImp spImp;
    private String uid = "";
    // 筛选价格区间、标签、商家名称
    private String min = "";
    private String max = "";
    private String price = "";
    private String label = "";

    private FriendsTogethermodel.ObjBean bean;
    private Dialog dialog;
    boolean isChangPing = false;
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 2001;
    private List<UserLabelModel.ObjBean> labelList = new ArrayList<>();//所有label 的list
    private List<YouJuTopLabelModel> labelList_1 = new ArrayList<>();//第一行label 的list
    private List<YouJuTopLabelModel> labelList_2 = new ArrayList<>();//第二行label 的list
    private FriendsTogetherFragmentLabelTopAdapter labelTopAdapter1 = new FriendsTogetherFragmentLabelTopAdapter(labelList_1);
    private FriendsTogetherFragmentLabelTopAdapter labelTopAdapter2 = new FriendsTogetherFragmentLabelTopAdapter(labelList_2);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_together3, null);
        ButterKnife.bind(this, view);
        ScreenAdapterTools.getInstance().loadView(view);
        //第一种方式获取屏幕的像素宽高在Activity里面可使用
        //DisplayMetrics metric = new DisplayMetrics();
        //Activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        //第二种方式获取屏幕的像素宽高
        //获取屏幕的像素高度PIX
        final double screenHeight = getResources().getDisplayMetrics().heightPixels;
        //获取屏幕的像素宽度PIX
        double screenWidth = getResources().getDisplayMetrics().widthPixels;
        BigDecimal b1 = new BigDecimal(Double.valueOf(screenHeight));
        BigDecimal b2 = new BigDecimal(Double.valueOf(screenWidth));
        Log.d("asdasd","screenHeight:"+screenHeight+"///screenWidth::"+screenWidth+"///"+(b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue()));
        spImp = new SpImp(getContext());
        initSonicEngine();
        initLabelTop();
        initSwipe();
        initData();
        rl_ball.setOnTouchListener(new View.OnTouchListener() {//调整悬浮球位置
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downOnBall = true;
                        rl_ball_lastY=event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getRawY()>500 && event.getRawY()< screenHeight-400){
                            v.setY(v.getY()+event.getRawY()-rl_ball_lastY);
                            rl_ball_lastY=event.getRawY();
                        }
                        break;
                }
                return false;
            }
        });
        return view;
    }
    private void initSwipe() {
        adapter = new SwipeFIingViewAdapter(getContext(),mList);
        initAdapterListen();
        sfv.setAdapter(adapter);
        sfv.refresh_view();
        sfv.setOnSwipeFlingListener(new SwipeFlingView.OnSwipeFlingListener() {
            @Override
            public void onStartDragCard() {

            }

            @Override
            public boolean canLeftCardExit() {
                return mList.size()>1?true:false;
            }

            @Override
            public boolean canRightCardExit() {
                return mList.size()>1?true:false;
            }

            @Override
            public void onPreCardExit() {

            }

            @Override
            public void onLeftCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
                int i =(int)dataObject;
                Log.d("mlist的Size",mList.size()+"");

                if (i>=0&&i<mList.size())
                    bean = mList.get(i);
                if (bean.getFocusOn().equals("0")) {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                } else {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                }
//                RelativeLayout rl_price = sfv.getSelectedView().findViewById(R.id.rl_price);
//                if (i>0) {
//                    FriendsTogethermodel.ObjBean model = mList.remove(i-1);//划出的数据删除掉
//                    mList.add(mList.size()-1,model);
//                    adapter.notifyDataSetChanged();
//                }

            }

            @Override
            public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
                int position =(int)dataObject;
                if (position>=0&&position<mList.size())
                    bean = mList.get(position);
                if (bean.getFocusOn().equals("0")) {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                } else {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                }
                //关注数量
                initGuanzhuNum();
            }

            @Override
            public void onSuperLike(View view, Object dataObject, boolean triggerByTouchMove) {

            }

            @Override
            public void onTopCardViewFinish() {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Log.d("asdasdasdas","about emptytytytytytyt");
//                List<FriendsTogethermodel.ObjBean> tList = new ArrayList<>();
//                tList.addAll(mList);
//                mList.clear();
//                mList.addAll(tList);
//                if (mList.size()<20){
//                    mList.addAll(tList);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    initData();
//                }
                try {
//                    mList.addAll(tList);
//                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    initData();
                }

            }

            @Override
            public void onAdapterEmpty() {
                Log.d("asdasdasdas","is emptytytytytytyt");
                rlzanwu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScroll(View selectedView, float scrollProgressPercent) {

            }

            @Override
            public void onEndDragCard() {

            }
        });
    }
    private void initLabelTop() {
        /**
         * 获取顶部未开始活动数量
         *
         *
         * */
        ViseHttp.POST(NetConfig.myWillBegin)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.myWillBegin))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String s = jsonObject.getJSONObject("obj").getString("willBeginNum");
                                tvDaiKaiShi.setText("您有"+s+"个待开始活动");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        /**
         * 判断屏幕比例 决定显示几排标签
         */
        //获取屏幕的像素高度PIX
        double screenHeight = getResources().getDisplayMetrics().heightPixels;
        //获取屏幕的像素宽度PIX
        double screenWidth = getResources().getDisplayMetrics().widthPixels;
        BigDecimal b1 = new BigDecimal(Double.valueOf(screenHeight));
        BigDecimal b2 = new BigDecimal(Double.valueOf(screenWidth));
        if (b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue()>1.7778){
            Log.d("asdasd","长坪");
            isChangPing = true;
        }

        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label_1.setLayoutManager(manager1);
        labelTopAdapter1.setOnSelelectedListionner(new FriendsTogetherFragmentLabelTopAdapter.OnSelelectedListionner() {
            @Override
            public void onSelect(int position) {
                for (YouJuTopLabelModel model:labelList_1){
                    model.setIstSelected(false);
                }
                labelList_1.get(position).setIstSelected(true);
                for (YouJuTopLabelModel model:labelList_2){
                    model.setIstSelected(false);
                }
                labelTopAdapter1.notifyDataSetChanged();
                labelTopAdapter2.notifyDataSetChanged();
                clickTopLabelChangeData(labelList_1.get(position).getId());
            }
        });
        rv_label_1.setAdapter(labelTopAdapter1);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label_2.setLayoutManager(manager2);
        labelTopAdapter2.setOnSelelectedListionner(new FriendsTogetherFragmentLabelTopAdapter.OnSelelectedListionner() {
            @Override
            public void onSelect(int position) {
                for (YouJuTopLabelModel model:labelList_2){
                    model.setIstSelected(false);
                }
                labelList_2.get(position).setIstSelected(true);
                for (YouJuTopLabelModel model:labelList_1){
                    model.setIstSelected(false);
                }
                labelTopAdapter1.notifyDataSetChanged();
                labelTopAdapter2.notifyDataSetChanged();
                clickTopLabelChangeData(labelList_2.get(position).getId());
            }
        });
        rv_label_2.setAdapter(labelTopAdapter2);

//        final boolean finalIsChangPing = isChangPing;
        final boolean finalIsChangPing = false;//此处修改于0812，无论长短屏幕 上方只显示一排标签，改为长屏在下方显示关注人数
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                labelList = userLabelModel.getObj();
                                labelList_1.clear();
                                labelList_2.clear();
                                YouJuTopLabelModel modelFir = new YouJuTopLabelModel();
                                modelFir.setId("");
                                modelFir.setName("推荐");
                                modelFir.setIstSelected(true);
                                labelList_1.add(modelFir);
                                if (finalIsChangPing){
                                    for (int i =0;i<labelList.size()/2;i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_1.add(model);
                                    }
                                    for (int i =labelList.size()/2;i<labelList.size();i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_2.add(model);
                                    }
                                    labelTopAdapter2.notifyDataSetChanged();
                                    rl_label2.setVisibility(View.VISIBLE);
                                }else {
                                    for (int i =0;i<labelList.size();i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_1.add(model);
                                    }
                                    rl_label2.setVisibility(View.GONE);
                                }
                                labelTopAdapter1.notifyDataSetChanged();
                                labelTopAdapter2.notifyDataSetChanged();
//                                GridLayoutManager manager = new GridLayoutManager(getContext(), 3){
//                                    @Override
//                                    public boolean canScrollVertically() {
//                                        return false;
//                                    }
//                                };
//                                recyclerView.setLayoutManager(manager);
//                                adapter = new LabelAdapter(mList);
//                                recyclerView.setAdapter(adapter);
//                                adapter.setListener(new LabelAdapter.OnSelectLabelListener() {
//                                    @Override
//                                    public void onSelete(int i) {
//                                        labelList.add(mList.get(i).getLID());
//                                    }
//                                });
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

    private void initAdapterListen(){
        adapter.setOnBottomButtonClickListionner(new SwipeFIingViewAdapter.OnBottomButtonClickListionner() {
            @Override
            public void OnGuanZhuClick(final int postion) {
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        return;
                    }
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", uid)
                            .addParam("pfID", mList.get(postion).getPfID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getInt("code") == 200) {
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                //__________adapter.notifyDataSetChanged(); f方法无效，只能获取当前View修改关注状态
                                                ImageView imageView = sfv.getSelectedView().findViewById(R.id.iv_guanzhu);
                                                TextView tvGuanzhu = sfv.getSelectedView().findViewById(R.id.tv_guanzhu);
                                                if (model.getObj().equals("1")) {
                                                    mList.get(postion).setFocusOn("1");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                                    imageView.setImageResource(R.mipmap.guanzhu128_r);
                                                    tvGuanzhu.setTextColor(Color.parseColor("#d84c37"));
                                                    Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    mList.get(postion).setFocusOn("0");
                                                    imageView.setImageResource(R.mipmap.guanzhu128_g);
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                                    tvGuanzhu.setTextColor(Color.parseColor("#101010"));
                                                    Toast.makeText(getContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                                }
//                                                adapter.data = null;
//                                                adapter.data = mList;
                                                adapter.notifyDataSetChanged();
                                            }
                                        } else if (jsonObject.getInt("code") == 400) {
                                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void OnBaoMingClick(final int postion) {
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent1 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent1);
                } else {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        return;
                    }
                    ViseHttp.POST(NetConfig.isRealNameUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.isRealNameUrl))
                            .addParam("userID", spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.i("123321", data);
                                    IsRealNameModel models = new Gson().fromJson(data, IsRealNameModel.class);
                                    if (models.getCode() == 200) {
                                        if (models.getObj().getOk().equals("2")) {
                                            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherDetailsUrl);
                                            ViseHttp.POST(NetConfig.friendsTogetherDetailsUrl)
                                                    .addParam("app_key", token)
                                                    .addParam("pfID", mList.get(postion).getPfID())
                                                    .addParam("userID", spImp.getUID())
                                                    .request(new ACallback<String>() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            Log.e("222", data);
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(data);
                                                                if (jsonObject.getInt("code") == 200) {
                                                                    FriendsTogetherDetailsModel model = new Gson().fromJson(data, FriendsTogetherDetailsModel.class);
                                                                    Intent it = new Intent(getContext(), ApplyActivity.class);
                                                                    it.putExtra("if_pay", model.getObj().getIf_pay());
                                                                    it.putExtra("title", model.getObj().getTitle());
                                                                    it.putExtra("begin_time", model.getObj().getBegin_time());
                                                                    it.putExtra("price", model.getObj().getPrice());
                                                                    it.putExtra("pfID", model.getObj().getPfID());
                                                                    it.putExtra("name", model.getObj().getTruename());
                                                                    it.putExtra("sex", model.getObj().getPeoplesex());
                                                                    it.putExtra("age", model.getObj().getAge());
                                                                    it.putExtra("pic", model.getObj().getShow_pic());
                                                                    it.putExtra("issingle", model.getObj().getMarry());
                                                                    it.putExtra("city", model.getObj().getCity());
                                                                    it.putExtra("tel", model.getObj().getUser_tel());
                                                                    it.putExtra("Pfexplain",model.getObj().getPfexplain());
                                                                    startActivity(it);
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFail(int errCode, String errMsg) {

                                                        }
                                                    });
                                        } else if (models.getObj().getOk().equals("1")) {
                                            toToast(getContext(), "请于身份审核通过后报名");
                                        } else {
                                            startActivity(new Intent(getContext(), RealNameActivity.class));
                                        }
                                    } else {
                                        toToast(getContext(), models.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
            }

            @Override
            public void OnFenXiangClick(final int postion) {
                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(getActivity(), mList.get(postion).getShare_url(), mList.get(postion).getPftitle(),
                                        mList.get(postion).getPfcontent(), mList.get(postion).getPfpic(), share_media);
                            }
                        }).open();
            }
        });
    }
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            initLabelTop();
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            initLabelTop();
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    private void initData() {
         min = "";
         max = "";
         label = "";
//        dialog = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
        String token = getToken(NetConfig.BaseUrl + NetConfig.activityAllList);
        ViseHttp.POST(NetConfig.activityAllList)
                .addParam("app_key", token)
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                final FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                initSwipe();
//                                tList.clear();
//                                tList.addAll(model.getObj());
//                                while (mList.size()>0 &&mList.size()<=4){//如果活动列表长度大于0，并且小于4时添加至四张以上
//                                    mList.addAll(tList);
//                                }
                                if (mList.size() > 0) {
                                    if (hasPermission()){
                                        preLoadYouJu(mList);
                                    }else {
                                        requestPermission();
                                    }
                                    rlzanwu.setVisibility(View.GONE);
                                    bean = mList.get(0);
                                    if (bean.getFocusOn().equals("0")) {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                    }
                                    //关注数量
                                    initGuanzhuNum();
                                }else {
                                    rlzanwu.setVisibility(View.VISIBLE);
                                }
                                if (isChangPing){
                                    ll_guanzhu_num.setVisibility(View.GONE);
                                }else {
                                    ll_guanzhu_num.setVisibility(View.GONE);
                                }
//                                updateListView();
//                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        } catch (JSONException e) {
//                            WeiboDialogUtils.closeDialog(dialog);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
//                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });

    }

    @OnClick({R.id.iv_shaixuan, R.id.iv_baoming, R.id.iv_youju_focus,R.id.rl_shaixuan,R.id.rl_myhuodong,R.id.rl_ball})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
//            case R.id.iv_shaixuan:
//                intent.setClass(getContext(), YoujuShaixuanActivity.class);
//                startActivityForResult(intent, 1);
//                break;
            case R.id.rl_ball:
                if (downOnBall){
                    break;
                }
                rlzanwu.setVisibility(View.GONE);
                sfv.selectComeBackCard(true);
                Log.d("sadasdsadsa",sfv.getCurPositon()+"");
                if (sfv.getCurPositon()>=0&&sfv.getCurPositon()<mList.size()){
                    bean = mList.get(sfv.getCurPositon());
                    if (bean.getFocusOn().equals("0")) {
                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                    } else {
                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                    }
                }
                break;
            case R.id.rl_shaixuan:
                intent.setClass(getContext(), YoujuShaixuanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_baoming:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent1 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent1);
                } else {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        break;
                    }
                    ViseHttp.POST(NetConfig.isRealNameUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.isRealNameUrl))
                            .addParam("userID", spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.i("123321", data);
                                    IsRealNameModel models = new Gson().fromJson(data, IsRealNameModel.class);
                                    if (models.getCode() == 200) {
                                        if (models.getObj().getOk().equals("2")) {
                                            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherDetailsUrl);
                                            ViseHttp.POST(NetConfig.friendsTogetherDetailsUrl)
                                                    .addParam("app_key", token)
                                                    .addParam("pfID", bean.getPfID())
                                                    .addParam("userID", spImp.getUID())
                                                    .request(new ACallback<String>() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            Log.e("222", data);
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(data);
                                                                if (jsonObject.getInt("code") == 200) {
                                                                    FriendsTogetherDetailsModel model = new Gson().fromJson(data, FriendsTogetherDetailsModel.class);
                                                                    Intent it = new Intent(getContext(), ApplyActivity.class);
                                                                    it.putExtra("if_pay", model.getObj().getIf_pay());
                                                                    it.putExtra("title", model.getObj().getTitle());
                                                                    it.putExtra("begin_time", model.getObj().getBegin_time());
                                                                    it.putExtra("price", model.getObj().getPrice());
                                                                    it.putExtra("pfID", model.getObj().getPfID());
                                                                    it.putExtra("name", model.getObj().getTruename());
                                                                    it.putExtra("sex", model.getObj().getPeoplesex());
                                                                    it.putExtra("age", model.getObj().getAge());
                                                                    it.putExtra("pic", model.getObj().getShow_pic());
                                                                    it.putExtra("issingle", model.getObj().getMarry());
                                                                    it.putExtra("city", model.getObj().getCity());
                                                                    it.putExtra("tel", model.getObj().getUser_tel());
                                                                    it.putExtra("Pfexplain",model.getObj().getPfexplain());
                                                                    startActivity(it);
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFail(int errCode, String errMsg) {

                                                        }
                                                    });
                                        } else if (models.getObj().getOk().equals("1")) {
                                            toToast(getContext(), "请于身份审核通过后报名");
                                        } else {
                                            startActivity(new Intent(getContext(), RealNameActivity.class));
                                        }
                                    } else {
                                        toToast(getContext(), models.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
                break;
//           //fragment关注按钮，已隐藏，在item中实现该功能
            case R.id.iv_youju_focus:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        break;
                    }
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", uid)
                            .addParam("pfID", bean.getPfID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getInt("code") == 200) {
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                if (model.getObj().equals("1")) {
                                                    bean.setFocusOn("1");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                                    Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    bean.setFocusOn("0");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                                    Toast.makeText(getContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else if (jsonObject.getInt("code") == 400) {
                                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_myhuodong:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), AllHuoDongActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
    private void clickTopLabelChangeData(String biaoqianID){
        String token = getToken(NetConfig.BaseUrl + NetConfig.activityAllList);
        ViseHttp.POST(NetConfig.activityAllList)
                .addParam("app_key", token)
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .addParam("sign", biaoqianID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                initSwipe();
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
////                                    while (mList.size()>0 && mList.size()<=4){
////                                        mList.addAll(tList);
////                                    }
                                if (mList.size() > 0) {
                                    if (isChangPing){
                                        ll_guanzhu_num.setVisibility(View.GONE);
                                    }
                                    rlzanwu.setVisibility(View.GONE);
                                    bean = mList.get(0);
                                    if (bean.getFocusOn().equals("0")) {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                    }
                                    //关注数量
                                    initGuanzhuNum();
                                } else {
                                    ll_guanzhu_num.setVisibility(View.GONE);
                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    rlzanwu.setVisibility(View.VISIBLE);
                                }
//                                    updateListView();
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        } catch (JSONException e) {
                            WeiboDialogUtils.closeDialog(dialog);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            HuoDongShaiXuanMode mode = (HuoDongShaiXuanMode) data.getSerializableExtra("shaiXuanMode");
            if (mode == null) return;
            dialog = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
            String token = getToken(NetConfig.BaseUrl + NetConfig.activityAllList);
            ViseHttp.POST(NetConfig.activityAllList)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("price", mode.getJiaGe())
                    .addParam("sign", mode.getBiaoQian())
                    .addParam("shop_name", mode.getShangJiaName())
                    .addParam("shop_recommend",mode.getShangJiaTuiJian())
                    .addParam("city",mode.getCity())
                    .addParam("activity_name",mode.getKeyWord())//关键字
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    initSwipe();
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
////                                    while (mList.size()>0 && mList.size()<=4){
////                                        mList.addAll(tList);
////                                    }
                                    if (mList.size() > 0) {
                                        bean = mList.get(0);
                                        if (bean.getFocusOn().equals("0")) {
                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                        } else {
                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                        }
                                        //关注数量
                                        initGuanzhuNum();
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    }
//                                    updateListView();
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            } catch (JSONException e) {
                                WeiboDialogUtils.closeDialog(dialog);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("读写内存权限,","permissionsSize:"+permissions.length+"///"+"grantResultsSize:"+grantResults.length);
        for (int i = 0;i<permissions.length;i++){
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    preLoadYouJu(mList);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void preLoadYouJu(List<FriendsTogethermodel.ObjBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_activity/youJuWeb?pfID="+list.get(i).getPfID()+"&uid="+uid;
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);
            HashMap mapRp = new HashMap();
            String str_vlue = "http://www.91yiwo.com/ylyy/include/activity_web/js/jquery-3.3.1.min.js;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/css/web_main.css;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/js/builder.js;";
            mapRp.put("sonic-link",str_vlue);
            sessionConfigBuilder.setCustomResponseHeaders(mapRp);
            boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build());
            Log.d("preloadpreloadp",preloadSuccess+""+url);
        }
    }
    private void initGuanzhuNum(){
        //关注数量
        if (bean.getUserimgs().size()<1){
            iv_guanzhu_icon1.setVisibility(View.VISIBLE);
            iv_guanzhu_icon1.setImageResource(R.mipmap.my_head);
            iv_guanzhu_icon2.setVisibility(View.GONE);
            iv_guanzhu_icon3.setVisibility(View.GONE);
            tv_guanzhu_num.setVisibility(View.GONE);
            tv_guanzhu_user_names.setVisibility(View.VISIBLE);
            tv_guanzhu_user_names.setText("还没有人关注");
        }else if (bean.getUserimgs().size()==1){
            Glide.with(getContext()).load(bean.getUserimgs().get(0)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon1);
            tv_guanzhu_user_names.setText(bean.getUsernames()+"已经关注");
            iv_guanzhu_icon1.setVisibility(View.VISIBLE);
            iv_guanzhu_icon2.setVisibility(View.GONE);
            iv_guanzhu_icon3.setVisibility(View.GONE);
            tv_guanzhu_user_names.setVisibility(View.VISIBLE);
            tv_guanzhu_num.setVisibility(View.GONE);
        }else if (bean.getUserimgs().size()==2){
            Glide.with(getContext()).load(bean.getUserimgs().get(0)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon1);
            Glide.with(getContext()).load(bean.getUserimgs().get(1)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon2);
            iv_guanzhu_icon1.setVisibility(View.VISIBLE);
            iv_guanzhu_icon2.setVisibility(View.VISIBLE);
            iv_guanzhu_icon3.setVisibility(View.GONE);
            tv_guanzhu_user_names.setVisibility(View.VISIBLE);
            tv_guanzhu_num.setVisibility(View.VISIBLE);
            tv_guanzhu_user_names.setText(bean.getUsernames());
            tv_guanzhu_num.setText("等"+bean.getAttentionNum()+"人已经关注");
        }else {
            Glide.with(getContext()).load(bean.getUserimgs().get(0)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon1);
            Glide.with(getContext()).load(bean.getUserimgs().get(1)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon2);
            Glide.with(getContext()).load(bean.getUserimgs().get(2)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(iv_guanzhu_icon3);
            iv_guanzhu_icon1.setVisibility(View.VISIBLE);
            iv_guanzhu_icon2.setVisibility(View.VISIBLE);
            iv_guanzhu_icon3.setVisibility(View.VISIBLE);
            tv_guanzhu_user_names.setVisibility(View.VISIBLE);
            tv_guanzhu_num.setVisibility(View.VISIBLE);
            tv_guanzhu_user_names.setText(bean.getUsernames());
            tv_guanzhu_num.setText("等"+bean.getAttentionNum()+"人已经关注");
        }
    }
    private void updateListView() {
//        adapter.data = mList;
//        sfv.refresh_view();//必须使用此方法  更新view
        adapter.notifyDataSetChanged();
    }
    private void initSonicEngine() {
        // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicConfig config = new SonicConfig.Builder()
                    .setMaxPreloadSessionCount(100)
                    .setMaxNumOfDownloadingTasks(10)
                    .build();

            SonicEngine.createInstance(new TBSonicRuntime(getContext()),config);
            Log.d("SonicEngine.create","webPage_aaa");
        }
    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    private int[] getDisplayMetrics(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int W = mDisplayMetrics.widthPixels;
        int H = mDisplayMetrics.heightPixels;
        int array[] = {W, H};
        return array;
    }
    public boolean isShowFloatImage() {
        return isShowFloatImage;
    }
    public void hideFloatImage() {
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
        isShowFloatImage  = false;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                0,//起始x坐标,10表示与初始位置相距10
//                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//结束x坐标
                0 - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.25f + 17.5f),//结束x坐标

                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(1f, 0.5f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        rl_ball.startAnimation(set);
    }
    public void showFloatImage() {
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
        isShowFloatImage  = true;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
//                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//起始x坐标
                0 - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.25f + 17.5f),
                0,//结束x坐标
                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(0.5f, 1f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        rl_ball.startAnimation(set);
    }
}
