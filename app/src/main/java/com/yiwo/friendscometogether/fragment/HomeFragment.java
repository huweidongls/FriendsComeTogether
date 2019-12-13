package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.custom.XieYiDialog;
import com.yiwo.friendscometogether.model.AllBannerModel;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeDataAdapter;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendAdapter1;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendAdapter2;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendLiveListAdapter;
import com.yiwo.friendscometogether.newadapter.HomeListVideoAdapter;
import com.yiwo.friendscometogether.newadapter.HomeListYouJiAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.newmodel.HomeVideoListModel;
import com.yiwo.friendscometogether.newmodel.IndexLabelModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.newpage.NotOnLiveActivity;
import com.yiwo.friendscometogether.newpage.TestSGVAActivity;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.pages.UserAgreementActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.yiwo.friendscometogether.utils.ViewUtil;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.MyJiFenActivity;
import com.yiwo.friendscometogether.widget.ViewPagerForScrollView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment extends BaseFragment {

    View rootView;

    @BindView(R.id.home_refreshlayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.fragment_home_banner)
    Banner banner;
    @BindView(R.id.vp)
    ViewPagerForScrollView viewPager;
    @BindView(R.id.tv_city_name)
    TextView cityTv;
    @BindView(R.id.ll_home_youji_all)
    LinearLayout youji_all;
    @BindView(R.id.ll_home_youji_lvxing)
    LinearLayout youji_lvxing;
    @BindView(R.id.ll_home_youji_meishi)
    LinearLayout youji_meishi;
    @BindView(R.id.ll_home_youji_tandian)
    LinearLayout youji_tandian;
    @BindView(R.id.ll_home_youji_gonglue)
    LinearLayout youji_gonglue;
    @BindView(R.id.tv_rl1)
    TextView tvRl1;
    @BindView(R.id.tv_rl2)
    TextView tvRl2;
    @BindView(R.id.tv_rl3)
    TextView tvRl3;
    @BindView(R.id.tv_rl4)
    TextView tvRl4;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @BindView(R.id.tv_date_info)
    TextView tvDateInfo;
    @BindView(R.id.tv_weiduxiaoxi)
    TextView tvWeiduxiaoxi;

    @BindView(R.id.rl_ball)
    RelativeLayout rl_ball;

    //第一个推荐的友聚
    private ImageView iv_first_tuijian;
    private TextView tv_first_tuijian_title,tv_first_tuijian_content,tv_youji_look_num;
    private TextView tv_tuyijian_first_tab_1,tv_tuyijian_first_tab_2;
    private LinearLayout llTuiJianHuodongFirst;
    //直播列表
    private LinearLayout ll_live;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1001;

    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String latLongString = "";

    private SpImp spImp;
    private String uid = "";
    private Dialog dialog_loading;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getCity();
                    break;
            }
        }

    };

    RecyclerView recyclerView1_1;//推荐_youji
    RecyclerView recyclerView1_2;//推荐_youju
    RecyclerView recyclerView_live;//直播列表
    RecyclerView recyclerView2;//关注
    RecyclerView recyclerView3;//友记
    RecyclerView recyclerView4;//小视频

    private HomeDataRecommendAdapter1 adapterTuiJian_youji;//推荐列表友记适配器
    private HomeDataRecommendAdapter2 adapterTuiJian_youju;//推荐列表友聚适配器
    private HomeDataRecommendLiveListAdapter adapterLiveList;//直播列表
    private HomeDataAdapter adapterGuanzhu;//关注列表适配器
    private HomeListYouJiAdapter adapterYouji;//友记列表适配器
    private HomeListVideoAdapter adapterVideos;//视频列表适配器
    private List<HomeDataModel1.ObjBean.ArticleBean> mListTuiJian_youji = new ArrayList<>();//推荐友记list
    private List<HomeDataModel1.ObjBean.ActivityBean> mListTuiJian_youju = new ArrayList<>();//推荐友聚list
    private List<HomeDataModel1.ObjBean.ZhiboBean> mlistLive = new ArrayList<>();//直播list
    private List<HomeDataModel.ObjBean> mListGuanzhu = new ArrayList<>();//关注列表list
    private List<HomeDataModel.ObjBean> mListYouJi = new ArrayList<>();//友记列表list
    private List<HomeVideoListModel.ObjBean> mListVideos = new ArrayList<>();//视频列表list
    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private int page4 = 1;

    private String cityId = "";
    private String type = "1";
    private String cityName = "";
    private IndexLabelModel labelModel;

    private List<View> viewList = new ArrayList<>();
    private View view1,view2,view3,view4;
    private TextView tv_text_youji;
    private boolean cancelEnterRoom;
//    tv_text_youju;
    private NotifyAdatpterBroadcastReceiver broadcastReceiver = new NotifyAdatpterBroadcastReceiver();
    private PreLoadWebYouJiBroadcastReceiver preLoadWebYouJiBroadcastReceiver = new PreLoadWebYouJiBroadcastReceiver();

    private boolean  isShowFloatImage = true  ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home2, null);
        ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());
        AppUpdateUtil.checkUpdate(getActivity(),true);
        registerBroadCaset();
        initSonicEngine();
        getLocation();
        initData();
        initRv_Vp();
        return rootView;
    }

    private void initRv_Vp() {
        view1 = getLayoutInflater().inflate(R.layout.layout_home_tuijian, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_home_guanzhu, null);
        view3 = getLayoutInflater().inflate(R.layout.layout_home_youji, null);
        view4 = getLayoutInflater().inflate(R.layout.layout_home_shipin, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        //初始化第一个首位推荐友聚活动
        llTuiJianHuodongFirst = view1.findViewById(R.id.ll_tuijian_huodong_first);
        iv_first_tuijian = view1.findViewById(R.id.iv_tuijian_first);
        ScreenAdapterTools.getInstance().loadView(view1);
        final ViewGroup.LayoutParams layoutParams = iv_first_tuijian.getLayoutParams();
        ViewUtil.getViewWidth(iv_first_tuijian, new ViewUtil.OnViewListener() {
            @Override
            public void onView(int width, int height) {
                layoutParams.width = width;
                layoutParams.height = (int) (width * 0.6);
                Log.d("asdasdasd2",layoutParams.width+"////"+layoutParams.height);
                iv_first_tuijian.setLayoutParams(layoutParams);
            }
        });

        tv_first_tuijian_title = view1.findViewById(R.id.tv_first_tuijian_title);
        tv_first_tuijian_content = view1.findViewById(R.id.tv_first_tuijian_content);
        tv_youji_look_num = view1.findViewById(R.id.tv_youji_look_num);
        tv_tuyijian_first_tab_1 = view1.findViewById(R.id.tv_tuyijian_first_tab_1);
        tv_tuyijian_first_tab_2 = view1.findViewById(R.id.tv_tuyijian_first_tab_2);

        recyclerView1_1 = view1.findViewById(R.id.rv_home_1_2);
        tv_text_youji = view1.findViewById(R.id.tv_text_youji);
//        tv_text_youju = view1.findViewById(R.id.tv_text_youju);
        recyclerView1_2 = view1.findViewById(R.id.rv_home_1_1);

        ll_live = view1.findViewById(R.id.ll_live);
        recyclerView_live = view1.findViewById(R.id.rv_live);

        //ceshi
        Button button = view1.findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), TestSGVAActivity.class);
                startActivity(intent);
            }
        });
        recyclerView2 = view2.findViewById(R.id.rv_home_2);
        recyclerView3 = view3.findViewById(R.id.rv_home_3);
        recyclerView4 = view4.findViewById(R.id.rv_home_4);

        PagerAdapter pagerAdapter = new PagerAdapter() {

                    @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                                // TODO Auto-generated method stub
                                return arg0 == arg1;
                            }

                    @Override
            public int getCount() {
                                // TODO Auto-generated method stub
                                return viewList.size();
                            }

                    @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                                // TODO Auto-generated method stub
                                container.removeView(viewList.get(position));
                            }

                    @Override
            public Object instantiateItem(ViewGroup container, int position) {
                                // TODO Auto-generated method stub
                                container.addView(viewList.get(position));
                                viewPager.setObjectForPosition(viewList.get(position),position);
                                return viewList.get(position);
                            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ((mListGuanzhu.size() == 0&&position == 1)){//关注无数据时
                    Log.d("aaaaa","guanzhuwushuju");
                }else {
                    viewPager.resetHeight(position);
                    Log.d("aaaaa","guanzhuyoushuju");
                }
                switch (position){
                    case 0:
//                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                        v4.setVisibility(View.GONE);

                        type = "1";
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
//                            tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                            tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                            tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                            tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                            tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                            tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                            tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                            tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.GONE);
                            v4.setVisibility(View.GONE);
                            type = "2";
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getContext(), LoginActivity.class);
                            startActivity(intent);
                            viewPager.setCurrentItem(0);
                        }
                        break;
                    case 2:
//                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.VISIBLE);
                        v4.setVisibility(View.GONE);
                        type = "3";
                        break;
                    case 3:
//                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                        v4.setVisibility(View.VISIBLE);
                        type = "4";
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            getLocation();
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            getLocation();
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
            if (labelModel == null){
                youji_all.setVisibility(View.GONE);
                youji_gonglue.setVisibility(View.GONE);
                youji_lvxing.setVisibility(View.GONE);
                youji_meishi.setVisibility(View.GONE);
                youji_tandian.setVisibility(View.GONE);
            }else {
                youji_all.setVisibility(View.VISIBLE);
                youji_gonglue.setVisibility(View.VISIBLE);
                youji_lvxing.setVisibility(View.VISIBLE);
                youji_meishi.setVisibility(View.VISIBLE);
                youji_tandian.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initData() {

        /*
           首页顶部日期  未读消息数
         */
        ViseHttp.POST(NetConfig.dataInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.dataInfo))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String s1 = jsonObject.getJSONObject("obj").getString("news");
                                String s2 = jsonObject.getJSONObject("obj").getString("day");
                                tvWeiduxiaoxi.setText("您有"+s1+"条消息");
                                tvDateInfo.setText(s2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        ClassicsHeader header = new ClassicsHeader(getContext());
//        header.setAccentColor(Color.WHITE);
//        header.setPrimaryColor(Color.parseColor("#d84c37"));
        refreshLayout.setRefreshHeader(header);
        ClassicsFooter footer = new ClassicsFooter(getContext());
//        footer.setPrimaryColor(Color.parseColor("#F8F8F8"));
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(1000);
            }
        });
//        if (!isNetConnect()){
//            toToast(getContext(),"当前无网络！");
//            if (labelModel == null){
//                youji_all.setVisibility(View.GONE);
//                youji_gonglue.setVisibility(View.GONE);
//                youji_lvxing.setVisibility(View.GONE);
//                youji_meishi.setVisibility(View.GONE);
//                youji_tandian.setVisibility(View.GONE);
//            }else {
//                youji_all.setVisibility(View.VISIBLE);
//                youji_gonglue.setVisibility(View.VISIBLE);
//                youji_lvxing.setVisibility(View.VISIBLE);
//                youji_meishi.setVisibility(View.VISIBLE);
//                youji_tandian.setVisibility(View.VISIBLE);
//            }
//            return;
//        }
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (type){
                    case "1":
                        ViseHttp.POST(NetConfig.homeRecommend2)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                                .addParam("uid", uid)
                                .addParam("city", cityName)
                                .addParam("page",page1+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        Log.e("123123", data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
//                                        mList.clear();
                                                if (model.getObj().getArticle().size()>0){
                                                    mListTuiJian_youji.addAll(model.getObj().getArticle());
                                                    preLoadYouJi_tuijain(model.getObj().getArticle());
//                                                    mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
//                                                    initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                                    adapterTuiJian_youji.notifyDataSetChanged();
//                                                    adapterTuiJian_youju.notifyDataSetChanged();
//                                                    if (mListTuiJian_youju.size()>0){
////                                                        tv_text_youju.setVisibility(View.VISIBLE);
//                                                        llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
//                                                    }else {
//                                                        llTuiJianHuodongFirst.setVisibility(View.GONE);
////                                                        tv_text_youju.setVisibility(View.GONE);
//                                                    }
                                                    if (mListTuiJian_youji.size()>0){
                                                        tv_text_youji.setVisibility(View.VISIBLE);
                                                    }else {
                                                        tv_text_youji.setVisibility(View.GONE);
                                                    }
                                                    page1++;
                                                }
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
                                        toToast(getContext(),"加载失败");
                                    }
                                });
                        break;
                    case "2":
                        ViseHttp.POST(NetConfig.homeGuanZhu2)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu2))
                                .addParam("uid", uid)
                                .addParam("page",page2+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        Log.e("123123", data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
//                                        mList.clear();
                                                if (model.getObj().size()>0){
                                                    mListGuanzhu.addAll(model.getObj());
                                                    preLoadYouJi_youji_and_guanzhu(model.getObj());
                                                    adapterGuanzhu.notifyDataSetChanged();
                                                    page2++;
                                                }
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
                                        toToast(getContext(),"加载失败");
                                    }
                                });
                        break;
                    case "3":
                        ViseHttp.POST(NetConfig.homeYouJi)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJi))
                                .addParam("uid", uid)
//                                .addParam("city", cityName)
                                .addParam("page",page3+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        Log.e("123123", data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
//                                        mList.clear();
                                                if (model.getObj().size()>0){
                                                    mListYouJi.addAll(model.getObj());
                                                    preLoadYouJi_youji_and_guanzhu(model.getObj());
                                                    adapterYouji.notifyDataSetChanged();
                                                    page3++;
                                                }
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
                                        toToast(getContext(),"加载失败");
                                    }
                                });
                        break;
                    case "4":
                        ViseHttp.POST(NetConfig.homeVideo)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeVideo))
                                .addParam("uid", uid)
                                .addParam("page",page4+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        Log.e("123123", data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeVideoListModel model = gson.fromJson(data, HomeVideoListModel.class);
                                                if (model.getObj().size()>0){
                                                    mListVideos.addAll(model.getObj());
                                                    adapterVideos.notifyDataSetChanged();
                                                    page4++;
                                                }
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
                                        toToast(getContext(),"加载失败");
                                    }
                                });
                        break;
                }
            }
        });
        ViseHttp.POST(NetConfig.allBannerUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.allBannerUrl))
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                final AllBannerModel bannerModel = gson.fromJson(data, AllBannerModel.class);
                                List<String> list = new ArrayList<>();
                                for (int i = 0; i < bannerModel.getObj().size(); i++) {
                                    list.add(bannerModel.getObj().get(i).getPic());
                                }
                                init(banner, list);
                                banner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
//                                        if (bannerModel.getObj().get(position).getFirst_type().equals("0")) {
//                                            Intent intent = new Intent(getContext(), DetailsOfFriendTogetherWebActivity.class);
//                                            intent.putExtra("pfID", bannerModel.getObj().get(position).getLeftid());
//                                            startActivity(intent);
//                                        } else if (bannerModel.getObj().get(position).getFirst_type().equals("1")) {
//                                            Intent intent = new Intent(getContext(), DetailsOfFriendsWebActivity1.class);
//                                            intent.putExtra("fmid", bannerModel.getObj().get(position).getLeftid());
//                                            startActivity(intent);
//                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        ViseHttp.POST(NetConfig.indexLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.indexLabel))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                labelModel = gson.fromJson(data, IndexLabelModel.class);
                                Glide.with(getContext()).load(labelModel.getObj().get(0).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(iv1);
                                tv1.setText(labelModel.getObj().get(0).getLname());
                                Glide.with(getContext()).load(labelModel.getObj().get(1).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(iv2);
                                tv2.setText(labelModel.getObj().get(1).getLname());
                                Glide.with(getContext()).load(labelModel.getObj().get(2).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(iv3);
                                tv3.setText(labelModel.getObj().get(2).getLname());
                                Glide.with(getContext()).load(labelModel.getObj().get(3).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(iv4);
                                tv4.setText(labelModel.getObj().get(3).getLname());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        uid = spImp.getUID();
        //推荐
        ViseHttp.POST(NetConfig.homeRecommend2)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                .addParam("uid", uid)
                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
                                page1 = 2;
                                //友记
                                mListTuiJian_youji = model.getObj().getArticle();
                                adapterTuiJian_youji = new HomeDataRecommendAdapter1(mListTuiJian_youji);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView1_1.setLayoutManager(manager);
                                recyclerView1_1.setAdapter(adapterTuiJian_youji);
                                //友聚
                                mListTuiJian_youju.clear();
                                //第一条友聚
                                if (model.getObj().getActivity().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                    llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                    initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                    if (model.getObj().getActivity().size()>1){
                                        mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
                                    }
                                }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                    llTuiJianHuodongFirst.setVisibility(View.GONE);
                                }
                                //友聚列表
                                adapterTuiJian_youju = new HomeDataRecommendAdapter2(mListTuiJian_youju);
                                LinearLayoutManager manager2 = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }

                                    @Override
                                    public boolean canScrollHorizontally() {
                                        return true;
                                    }
                                };
                                manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView1_2.setLayoutManager(manager2);
                                recyclerView1_2.setAdapter(adapterTuiJian_youju);
                                //直播
                                mlistLive = model.getObj().getZhibo();
                                adapterLiveList = new HomeDataRecommendLiveListAdapter(mlistLive);
                                adapterLiveList.setListener(new HomeDataRecommendLiveListAdapter.LiveListAdapterListener() {
                                    @Override
                                    public void onCLickListen(int pos) {
                                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                                            enterLiveRoom(mlistLive.get(pos));
                                        } else {
                                            Intent intent = new Intent();
                                            intent.setClass(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onGuanZhuListen(int pos) {
                                        guanZhuLivePerson(pos);
                                    }
                                });
                                LinearLayoutManager manager3 = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }

                                    @Override
                                    public boolean canScrollHorizontally() {
                                        return true;
                                    }
                                };
                                manager3.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView_live.setLayoutManager(manager3);
                                recyclerView_live.setAdapter(adapterLiveList);
                                if (mlistLive.size()>0 && model.getObj().getStatus().equals("1")){
//                                if (mlistLive.size()>0){
                                    ll_live.setVisibility(View.VISIBLE);
                                }else {
                                    ll_live.setVisibility(View.GONE);
                                }
                                if (mListTuiJian_youji.size()>0){
                                    if (hasPermission()){
                                        preLoadYouJi_tuijain(mListTuiJian_youji);
                                    }else {
                                        requestPermission();
                                    }
                                    tv_text_youji.setVisibility(View.VISIBLE);
                                }else {
                                    tv_text_youji.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //关注
        ViseHttp.POST(NetConfig.homeGuanZhu2)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu2))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                page2 = 2;
                                mListGuanzhu = model.getObj();
                                if (mListGuanzhu.size()>0){
                                    if (hasPermission()){
                                        preLoadYouJi_youji_and_guanzhu(mListGuanzhu);
                                    }else {
                                        requestPermission();
                                    }
                                }
                                adapterGuanzhu= new HomeDataAdapter(mListGuanzhu);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView2.setLayoutManager(manager);
                                recyclerView2.setAdapter(adapterGuanzhu);
                            }else {
                                mListGuanzhu = new ArrayList<>();
                                adapterGuanzhu= new HomeDataAdapter(mListGuanzhu);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView2.setLayoutManager(manager);
                                recyclerView2.setAdapter(adapterGuanzhu);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //友记
        ViseHttp.POST(NetConfig.homeYouJi)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJi))
                .addParam("uid", uid)
//                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                page3 = 2;
                                mListYouJi = model.getObj();
                                if (mListYouJi.size()>0){
                                    if (hasPermission()){
                                        preLoadYouJi_youji_and_guanzhu(mListYouJi);
                                    }else {
                                        requestPermission();
                                    }
                                }
                                adapterYouji = new HomeListYouJiAdapter(mListYouJi);
                                adapterYouji.setOnLongClickListener(new HomeListYouJiAdapter.OnLongClickListener() {
                                    @Override
                                    public void OnLongClick(int position) {
                                    }
                                });
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView3.setLayoutManager(mLayoutManager);
                                recyclerView3.setAdapter(adapterYouji);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //小视频
        ViseHttp.POST(NetConfig.homeVideo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeVideo))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeVideoListModel model = gson.fromJson(data, HomeVideoListModel.class);
                                page4 = 2;
                                mListVideos = model.getObj();
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView4.setLayoutManager(mLayoutManager);
                                adapterVideos = new HomeListVideoAdapter(mListVideos);
                                recyclerView4.setAdapter(adapterVideos);
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

    private void enterLiveRoom(final HomeDataModel1.ObjBean.ZhiboBean zhiboBean) {
        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"进入房间中");
        ViseHttp.POST(NetConfig.zhiBoInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.zhiBoInfo))
                .addParam("uid", zhiboBean.getUserID())
                .addParam("cid",zhiboBean.getCid())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("sdasdaas",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String liveStatus = jsonObject.getJSONObject("obj").getString("zhibostatus");
                                String start_time = jsonObject.getJSONObject("obj").getString("start_time");
//                                if (true){
                                if (liveStatus.equals("1")){
                                    RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
                                    roomInfoEntity.setCid(zhiboBean.getCid());
                                    roomInfoEntity.setOwner(zhiboBean.getUsername());
                                    roomInfoEntity.setHlsPullUrl(zhiboBean.getHlsPullUrl());
                                    roomInfoEntity.setHttpPullUrl(zhiboBean.getHttpPullUrl());
                                    roomInfoEntity.setRtmpPullUrl(zhiboBean.getRtmpPullUrl());
                                    roomInfoEntity.setPushUrl(zhiboBean.getPushUrl());
                                    roomInfoEntity.setRoomid(Integer.parseInt(zhiboBean.getRoom_id()));
                                    DemoCache.setRoomInfoEntity(roomInfoEntity);
                                    LiveRoomActivity.startAudience(getContext(), zhiboBean.getRoom_id() + "", zhiboBean.getRtmpPullUrl(), true);
                                    WeiboDialogUtils.closeDialog(dialog_loading);
                                }else {
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), NotOnLiveActivity.class);
                                    intent.putExtra("start_time",start_time);
                                    intent.putExtra("person_id", zhiboBean.getUserID());
                                    intent.putExtra("liver_name",zhiboBean.getUsername());
                                    intent.putExtra("liver_icon",zhiboBean.getUserpic());
                                    intent.putExtra("guanzhuLiver",zhiboBean.getLike());
                                    startActivity(intent);
                                    WeiboDialogUtils.closeDialog(dialog_loading);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            WeiboDialogUtils.closeDialog(dialog_loading);
                        }

                    }


                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog_loading);
                        toToast(getContext(),"进入房间失败！");
                    }
                });

//        cancelEnterRoom = false;
//        DialogMaker.showProgressDialog(getContext(), null, "进入房间中", true, new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                cancelEnterRoom = true;
//            }
//        }).setCanceledOnTouchOutside(false);
    }

    private void initTuiJianFirstHuoDong(final HomeDataModel1.ObjBean.ActivityBean bean) {
//        private ImageView iv_first_tuijian;
//        private TextView tv_first_tuijian_title,tv_first_tuijian_content,tv_youji_look_num;
//        private TextView tv_tuyijian_first_tab_1,tv_tuyijian_first_tab_2;
        llTuiJianHuodongFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                if (TextUtils.isEmpty(bean.getPfpwd())) {
                    intent.setClass(getContext(), DetailsOfFriendTogetherWebActivity.class);
                    intent.putExtra("pfID", bean.getPfID());
                    getContext().startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(getContext(), new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public boolean setActivityText(String s) {
                            if (s.equals(bean.getPfpwd())) {
                                intent.setClass(getContext(), DetailsOfFriendTogetherWebActivity.class);
                                intent.putExtra("pfID", bean.getPfID());
                                getContext().startActivity(intent);
                                return true;
                            }else {
                                Toast.makeText(getContext(),"密码错误",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });
        Glide.with(getContext()).load(bean.getPfpic().get(0)).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(iv_first_tuijian);
        tv_first_tuijian_title.setText(bean.getPftitle());
        tv_first_tuijian_content.setText(bean.getPfcontent());
        tv_youji_look_num.setText(bean.getPflook());
        if (bean.getActivity_label().size()>0){
            tv_tuyijian_first_tab_1.setText(bean.getActivity_label().get(0).getName());
            if (bean.getActivity_label().size()>1){
                tv_tuyijian_first_tab_2.setText(bean.getActivity_label().get(1).getName());
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    public void getLocation() {
        Log.i("查找城市", "哈尔滨");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        new Thread() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("请求权限", "请求了");
                    return;
                }
                @SuppressLint("MissingPermission") Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude(); // 经度
                    longitude = location.getLongitude(); // 纬度
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public void getCity() {
        try {
            // 去谷歌的地理位置获取中去解析经纬度对应的地理位置
//            String url = "http://maps.google.cn/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&language=zh-CN";
            String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latitude + "," + longitude + "&key=8dDPAEEMwPNZgxg4YhNUXqWoV8GNItO1";

            ViseHttp.GET(url)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getString("status").equals("OK")) {
                                    Gson gson = new Gson();
                                    BaiduCityModel model = gson.fromJson(data, BaiduCityModel.class);
                                    latLongString = model.getResult().getAddressComponent().getCity();
                                    cityTv.setText(latLongString);
                                    cityName = latLongString;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick({R.id.ll_home_youji_gonglue,R.id.ll_home_youji_all,
                    R.id.ll_home_youji_meishi,R.id.ll_home_youji_tandian,R.id.ll_home_youji_lvxing, R.id.locationRl, R.id.ll_search, R.id.iv_msg,
    R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4,R.id.ll_xiaoxi,R.id.rl_ball})
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_home_youji_all:
                MyApplication.sign = "";
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.ll_home_youji_lvxing:
                MyApplication.sign = labelModel.getObj().get(0).getLID();
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.ll_home_youji_meishi:
                MyApplication.sign = labelModel.getObj().get(1).getLID();
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.ll_home_youji_tandian:
                MyApplication.sign = labelModel.getObj().get(2).getLID();
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.ll_home_youji_gonglue:
                MyApplication.sign = labelModel.getObj().get(3).getLID();
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.locationRl:
                Intent it = new Intent(getActivity(), CityActivity.class);
                CityModel model = new CityModel();
                model.setName("哈尔滨");
                model.setId("-1");
                UserUtils.saveCity(getActivity(), model);
                it.putExtra(ActivityConfig.ACTIVITY, "home");
//                it.putExtra("model",model);
                startActivityForResult(it, 1);
                break;
            case R.id.ll_search:
                intent.setClass(getContext(), SearchActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.iv_msg:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_xiaoxi:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl1:
                type = "1";
                viewPager.setCurrentItem(0);
                break;
            case R.id.rl2:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    viewPager.setCurrentItem(1);
                    type = "2";
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl3:
                viewPager.setCurrentItem(2);
                type = "3";
                break;
            case R.id.rl4:
                viewPager.setCurrentItem(3);
                type = "4";
                break;
            case R.id.rl_ball:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyJiFenActivity.class);
                    intent.putExtra("url","http://www.tongbanapp.com/action/ac_mission/mission?uid="+spImp.getUID());
                    startActivityForResult(intent,2);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void refresh(){
//        if (!isNetConnect()){
//            toToast(getContext(),"当前无网络！");
//            return;
//        }
//        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
        switch (type){
            case "1":
                ViseHttp.POST(NetConfig.homeRecommend2)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                        .addParam("uid", uid)
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", type+"--------"+uid);
                                Log.e("123123", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
                                        mListTuiJian_youji.clear();
                                        mListTuiJian_youji.addAll(model.getObj().getArticle());
                                        mListTuiJian_youju.clear();

                                        if (model.getObj().getActivity().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                            llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                            initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                            if (model.getObj().getActivity().size()>1){
                                                mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
                                            }
                                        }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                            llTuiJianHuodongFirst.setVisibility(View.GONE);
                                        }
                                        //直播
                                        mlistLive.clear();
                                        mlistLive.addAll(model.getObj().getZhibo());

                                        adapterTuiJian_youji.notifyDataSetChanged();
                                        adapterTuiJian_youju.notifyDataSetChanged();
                                        adapterLiveList.notifyDataSetChanged();
                                        if (mlistLive.size()>0 && model.getObj().getStatus().equals("1")){
//                                        if (mlistLive.size()>0){
                                            ll_live.setVisibility(View.VISIBLE);
                                        }else {
                                            ll_live.setVisibility(View.GONE);
                                        }
                                        if (mListTuiJian_youju.size()>0){
//                                            tv_text_youju.setVisibility(View.VISIBLE);
                                            llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        }else {
//                                            tv_text_youju.setVisibility(View.GONE);
                                            llTuiJianHuodongFirst.setVisibility(View.GONE);
                                        }
                                        if (mListTuiJian_youji.size()>0){
                                            preLoadYouJi_tuijain(mListTuiJian_youji);
                                            tv_text_youji.setVisibility(View.VISIBLE);
                                        }else {
                                            tv_text_youji.setVisibility(View.GONE);
                                        }
                                        page1 = 2;
                                    }
//                                    WeiboDialogUtils.closeDialog(dialog_loading);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(getContext(),"加载失败:"+"errCode:"+errCode+"///"+errMsg);
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                break;
            case "2":
                ViseHttp.POST(NetConfig.homeGuanZhu2)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu2))
                        .addParam("uid", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                        page2 = 2;
                                        mListGuanzhu.clear();
                                        mListGuanzhu.addAll( model.getObj());
                                        preLoadYouJi_youji_and_guanzhu(mListGuanzhu);
                                        adapterGuanzhu.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                break;
            case "3":
                ViseHttp.POST(NetConfig.homeYouJi)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJi))
                        .addParam("uid", uid)
//                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                        page3 = 2;
                                        mListYouJi.clear();
                                        mListYouJi.addAll(model.getObj());
                                        if (hasPermission()){
                                            preLoadYouJi_youji_and_guanzhu(mListYouJi);
                                        }else {
                                            requestPermission();
                                        }
                                        adapterYouji.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                break;
            case "4":
                ViseHttp.POST(NetConfig.homeVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeVideo))
                        .addParam("uid", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeVideoListModel model = gson.fromJson(data, HomeVideoListModel.class);
                                        page4 = 2;
                                        mListVideos.clear();
                                        mListVideos.addAll(model.getObj());
                                        adapterVideos.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                break;
        }
        ViseHttp.POST(NetConfig.allBannerUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.allBannerUrl))
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                final AllBannerModel bannerModel = gson.fromJson(data, AllBannerModel.class);
                                List<String> list = new ArrayList<>();
                                for (int i = 0; i < bannerModel.getObj().size(); i++) {
                                    list.add(bannerModel.getObj().get(i).getPic());
                                }
                                init(banner, list);
                                banner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
//                                        if (bannerModel.getObj().get(position).getFirst_type().equals("0")) {
//                                            Intent intent = new Intent(getContext(), DetailsOfFriendTogetherWebActivity.class);
//                                            intent.putExtra("pfID", bannerModel.getObj().get(position).getLeftid());
//                                            startActivity(intent);
//                                        } else if (bannerModel.getObj().get(position).getFirst_type().equals("1")) {
//                                            Intent intent = new Intent(getContext(), DetailsOfFriendsWebActivity1.class);
//                                            intent.putExtra("fmid", bannerModel.getObj().get(position).getLeftid());
//                                            startActivity(intent);
//                                        }
                                    }
                                });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode == 1 城市页面 请求码 // 2 任务 页面请求码
        if (requestCode == 1 && data != null && resultCode == 1) {
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            cityTv.setText(model.getName());
            cityName = model.getName();
            cityId = model.getId();
            dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(), "正在加载...");
            ViseHttp.POST(NetConfig.homeRecommend2)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                    .addParam("uid", uid)
                    .addParam("city", cityName)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
                                    mListTuiJian_youji.clear();
                                    mListTuiJian_youji.addAll(model.getObj().getArticle());
                                    mListTuiJian_youju.clear();
                                    if (model.getObj().getActivity().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                        llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                        if (model.getObj().getActivity().size()>1){
                                            mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
                                        }
                                    }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                        llTuiJianHuodongFirst.setVisibility(View.GONE);
                                    }
                                    adapterTuiJian_youji.notifyDataSetChanged();
                                    adapterTuiJian_youju.notifyDataSetChanged();
                                    if (mListTuiJian_youji.size()>0){
                                        tv_text_youji.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youji.setVisibility(View.GONE);
                                    }
                                }
                                WeiboDialogUtils.closeDialog(dialog_loading);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            toToast(getContext(),"加载失败");
                            WeiboDialogUtils.closeDialog(dialog_loading);
                        }
                    });
        } else if (requestCode == 1 && resultCode == 2) {
            cityId = "";
            cityName = "";
//            cityTv.setText(latLongString);
            cityTv.setText("选择城市");
            dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"正在加载...");
            ViseHttp.POST(NetConfig.homeRecommend2)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                    .addParam("uid", uid)
                    .addParam("city", cityName)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
                                    mListTuiJian_youji.clear();
                                    mListTuiJian_youji.addAll(model.getObj().getArticle());
                                    mListTuiJian_youju.clear();
                                    if (model.getObj().getActivity().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                        llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                        if (model.getObj().getActivity().size()>1){
                                            mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
                                        }
                                    }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                        llTuiJianHuodongFirst.setVisibility(View.GONE);
                                    }
                                    adapterTuiJian_youji.notifyDataSetChanged();
                                    adapterTuiJian_youju.notifyDataSetChanged();
                                    if (mListTuiJian_youji.size()>0){
                                        tv_text_youji.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youji.setVisibility(View.GONE);
                                    }
                                }
                                WeiboDialogUtils.closeDialog(dialog_loading);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            toToast(getContext(),"加载失败");
                            WeiboDialogUtils.closeDialog(dialog_loading);
                        }
                    });
        } else if (requestCode == 1 && resultCode == 3) {
            String city = data.getStringExtra("city");
            cityId = data.getStringExtra("cityid");
            cityName = city;
            cityTv.setText(city);
            dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"正在加载...");
            ViseHttp.POST(NetConfig.homeRecommend2)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend2))
                    .addParam("uid", uid)
                    .addParam("city", cityName)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel1 model = gson.fromJson(data, HomeDataModel1.class);
                                    mListTuiJian_youji.clear();
                                    mListTuiJian_youji.addAll(model.getObj().getArticle());
                                    mListTuiJian_youju.clear();
                                    if (model.getObj().getActivity().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                        llTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        initTuiJianFirstHuoDong(model.getObj().getActivity().get(0));
                                        if (model.getObj().getActivity().size()>1){
                                            mListTuiJian_youju.addAll(model.getObj().getActivity().subList(1,model.getObj().getActivity().size()));
                                        }
                                    }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                        llTuiJianHuodongFirst.setVisibility(View.GONE);
                                    }
                                    adapterTuiJian_youji.notifyDataSetChanged();
                                    adapterTuiJian_youju.notifyDataSetChanged();
                                    if (mListTuiJian_youji.size()>0){
                                        tv_text_youji.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youji.setVisibility(View.GONE);
                                    }
                                }
                                WeiboDialogUtils.closeDialog(dialog_loading);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override

                        public void onFail(int errCode, String errMsg) {
                            toToast(getContext(),"加载失败...");
                            WeiboDialogUtils.closeDialog(dialog_loading);
                        }
                    });
        }
        if (requestCode == 2){//任务
            switch (resultCode){
                case 1://跳转友记
                    viewPager.setCurrentItem(2);
                    type = "3";
                    break;
                case 2://跳转友聚
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.switchFragment(1);
                    mainActivity.startHuoDong();
                    break;
                case 3://跳转视频
                    viewPager.setCurrentItem(3);
                    type = "4";
                    break;
            }
        }
    }
    public void scroll2top(){
//        scrollView.fullScroll(View.FOCUS_UP);
    }
    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(broadcastReceiver);
        getContext().unregisterReceiver(preLoadWebYouJiBroadcastReceiver);
        SonicEngine.getInstance().cleanCache();

        super.onDestroy();
    }

    private void registerBroadCaset() {
        IntentFilter filter =new IntentFilter();
        filter.addAction("android.friendscometogether.HomeFragment.TuiJian_Youji");
        filter.addAction("android.friendscometogether.HomeFragment.TuiJian_Youju");
        filter.addAction("android.friendscometogether.HomeFragment.GuanZhu");
        filter.addAction("android.friendscometogether.HomeFragment.YouJi");
        filter.addAction("android.friendscometogether.HomeFragment.Video");
        getContext().registerReceiver(broadcastReceiver, filter);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.friendscometogether.HomeFragment.PreLoadWebYouJiBroadcastReceiver");
        getContext().registerReceiver(preLoadWebYouJiBroadcastReceiver,filter1);
    }

    public boolean isShowFloatImage() {
        return isShowFloatImage;
    }

    private class NotifyAdatpterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub
            String action = intent.getAction();
            String id = intent.getStringExtra("deleteID");
            switch (action){
                case "android.friendscometogether.HomeFragment.TuiJian_Youji":
                    for (int i =0;i<mListTuiJian_youji.size();i++){
                        if (mListTuiJian_youji.get(i).getPfID().equals(id)){
                            mListTuiJian_youji.remove(i);
                            adapterTuiJian_youji.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case "android.friendscometogether.HomeFragment.TuiJian_Youju":
                    for (int i =0;i<mListTuiJian_youju.size();i++){
                        if (mListTuiJian_youju.get(i).getPfID().equals(id)){
                            mListTuiJian_youju.remove(i);
                            adapterTuiJian_youju.notifyDataSetChanged();
                            break;
                        }
                    }
                        break;
                case "android.friendscometogether.HomeFragment.GuanZhu":
                    for (int i =0;i<mListGuanzhu.size();i++){
                        if (mListGuanzhu.get(i).getPfID().equals(id)){
                            mListGuanzhu.remove(i);
                            adapterGuanzhu.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case "android.friendscometogether.HomeFragment.YouJi":
                    for (int i =0;i<mListYouJi.size();i++){
                        if (mListYouJi.get(i).getPfID().equals(id)){
                            mListYouJi.remove(i);
                            adapterYouji.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case "android.friendscometogether.HomeFragment.Video":
                    for (int i =0;i<mListVideos.size();i++){
                        if (mListVideos.get(i).getVID().equals(id)){
                            mListVideos.remove(i);
                            adapterVideos.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
            }
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("读写内存权限,","permissionsSize:"+permissions.length+"///"+"grantResultsSize:"+grantResults.length);
        for (int i = 0;i<permissions.length;i++){
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    preLoadYouJi_youji_and_guanzhu(mListYouJi);
                    preLoadYouJi_youji_and_guanzhu(mListGuanzhu);
                    preLoadYouJi_tuijain(mListTuiJian_youji);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void preLoadYouJi_youji_and_guanzhu(List<HomeDataModel.ObjBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            if (list.get(i).getData_type().equals("2")){//为友记  的情况
                            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getPfID()+"&uid="+uid;
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
    }
    private void preLoadYouJi_tuijain(List<HomeDataModel1.ObjBean.ArticleBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getPfID()+"&uid="+uid;
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
    private class PreLoadWebYouJiBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            uid = spImp.getUID();
            if (hasPermission()){
                preLoadYouJi_tuijain(mListTuiJian_youji);
                preLoadYouJi_youji_and_guanzhu(mListGuanzhu);
                preLoadYouJi_youji_and_guanzhu(mListYouJi);
            }else {
                requestPermission();
            }
        }
    }
    // 直播列表关注
    private void guanZhuLivePerson(final int position){
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
            if (mlistLive.get(position).getLike().equals("0")){//未关注
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", uid)
                        .addParam("likeId", mlistLive.get(position).getUserID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("code") == 200) {
                                        mlistLive.get(position).setLike("1");
                                        adapterLiveList.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
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
        } else {
            Intent intent = new Intent();
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);
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
    public void hideFloatImage() {
        isShowFloatImage  = false;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                0,//起始x坐标,10表示与初始位置相距10
                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//结束x坐标
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
        isShowFloatImage  = true;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//起始x坐标
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
