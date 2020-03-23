package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.library.banner.BannerLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.AllBannerModel;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeDataAdapter;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendHuoDongLiveAdapter;
import com.yiwo.friendscometogether.newadapter.HomeDataYouJiVideoAdapter;
import com.yiwo.friendscometogether.newadapter.HomeFragmentFirstBannerAdapter1;
import com.yiwo.friendscometogether.newadapter.HomeListVideoAdapter;
import com.yiwo.friendscometogether.newadapter.HomeListYouJiAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataBannerHuoDongLiveModel;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newmodel.HomeDataYouJiVideoModel;
import com.yiwo.friendscometogether.newmodel.HomeVideoListModel;
import com.yiwo.friendscometogether.newmodel.IndexLabelModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AndTools;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
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

public class HomeFragment3 extends BaseFragment {

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
    ImageView v1;
    @BindView(R.id.v2)
    ImageView v2;
    @BindView(R.id.v3)
    ImageView v3;
    @BindView(R.id.v4)
    ImageView v4;
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

    @BindView(R.id.tv_weiduxiaoxi)
    TextView tvWeiduxiaoxi;

    @BindView(R.id.rl_ball)
    RelativeLayout rl_ball;
    @BindView(R.id.rl_xiaoxi_num)
    RelativeLayout rl_xiaoxi_num;

    //第一个推荐的友聚
     BannerLayout recyclerbanner;
     RelativeLayout rlTuiJianHuodongFirst;
    private HomeFragmentFirstBannerAdapter1 firstBannerAdapter;
    private List<HomeDataBannerHuoDongLiveModel.ObjBean.BannerListBean> mListTuiJian_first = new ArrayList<>();//轮播图list


    //直播列表
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

    RecyclerView recyclerViewTuiJianYouJi;//推荐_youji
    RecyclerView recyclerViewTuiJianYouJu;//推荐_youju

    RecyclerView recyclerView2;//关注
    RecyclerView recyclerView3;//友记
    RecyclerView recyclerView4;//小视频


    //----------------------推荐活动------------------------------------------------------------------
    private HomeDataRecommendHuoDongLiveAdapter homeDataRecommendHuoDongLiveAdapter; //推荐活动，直播，
    private List<HomeDataBannerHuoDongLiveModel.ObjBean.ActivityBean> dataHuoDongLiveModelList = new ArrayList<>();
    private HomeDataYouJiVideoAdapter adapterTuiJian_youji;//推荐友记适配器
    private List<HomeDataYouJiVideoModel.ObjBean> mListTuiJian_youji = new ArrayList<>();//推荐友记list

    private HomeDataAdapter adapterGuanzhu;//关注列表适配器
    private HomeListYouJiAdapter adapterYouji;//友记列表适配器
    private HomeListVideoAdapter adapterVideos;//视频列表适配器

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
//    tv_text_youju;
    private NotifyAdatpterBroadcastReceiver broadcastReceiver = new NotifyAdatpterBroadcastReceiver();
    private PreLoadWebYouJiBroadcastReceiver preLoadWebYouJiBroadcastReceiver = new PreLoadWebYouJiBroadcastReceiver();

    private boolean  isShowFloatImage = true  ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home3, null);
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
        view1 = getLayoutInflater().inflate(R.layout.layout_home_tuijian_1, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_home_guanzhu, null);
        view3 = getLayoutInflater().inflate(R.layout.layout_home_youji, null);
        view4 = getLayoutInflater().inflate(R.layout.layout_home_shipin, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        ScreenAdapterTools.getInstance().loadView(view1);
        //初始化第一个首位推荐友聚活动
        rlTuiJianHuodongFirst = view1.findViewById(R.id.rl_tuijian_huodong_first);
        recyclerbanner = view1.findViewById(R.id.recyclerbanner);


        recyclerViewTuiJianYouJi = view1.findViewById(R.id.rv_home_1_2);
        tv_text_youji = view1.findViewById(R.id.tv_text_youji);
//        tv_text_youju = view1.findViewById(R.id.tv_text_youju);
        recyclerViewTuiJianYouJu = view1.findViewById(R.id.rv_home_1_1);

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
        //初始化 tvRl
        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl1.setTextColor(Color.parseColor("#333333"));
        tvRl2.setTextColor(Color.parseColor("#999999"));
        tvRl3.setTextColor(Color.parseColor("#999999"));
        tvRl4.setTextColor(Color.parseColor("#999999"));
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        type = "1";

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
                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl1.setTextColor(Color.parseColor("#333333"));
                        tvRl2.setTextColor(Color.parseColor("#999999"));
                        tvRl3.setTextColor(Color.parseColor("#999999"));
                        tvRl4.setTextColor(Color.parseColor("#999999"));
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        v1.setVisibility(View.VISIBLE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                        v4.setVisibility(View.GONE);

                        type = "1";
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                            tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                            tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl1.setTextColor(Color.parseColor("#999999"));
                            tvRl2.setTextColor(Color.parseColor("#333333"));
                            tvRl3.setTextColor(Color.parseColor("#999999"));
                            tvRl4.setTextColor(Color.parseColor("#999999"));
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
                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl1.setTextColor(Color.parseColor("#999999"));
                        tvRl2.setTextColor(Color.parseColor("#999999"));
                        tvRl3.setTextColor(Color.parseColor("#333333"));
                        tvRl4.setTextColor(Color.parseColor("#999999"));
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
                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                        tvRl1.setTextColor(Color.parseColor("#999999"));
                        tvRl2.setTextColor(Color.parseColor("#999999"));
                        tvRl3.setTextColor(Color.parseColor("#999999"));
                        tvRl4.setTextColor(Color.parseColor("#333333"));
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
                                tvWeiduxiaoxi.setText(""+s1+"");
                                if (s1.equals("0")){
                                    rl_xiaoxi_num.setVisibility(View.GONE);
                                }else {
                                    rl_xiaoxi_num.setVisibility(View.VISIBLE);
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
        ViseHttp.POST(NetConfig.homePage)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePage))
                .addParam("uid", uid)
                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeDataBannerHuoDongLiveModel model = gson.fromJson(data, HomeDataBannerHuoDongLiveModel.class);
                                //轮播图
                                if (model.getObj().getBannerList().size()>0){
//                                    tv_text_youju.setVisibility(View.VISIBLE);
                                    rlTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                    initTuiJianFirstHuoDong(model.getObj().getBannerList());
                                }else {
//                                    tv_text_youju.setVisibility(View.GONE);
                                    rlTuiJianHuodongFirst.setVisibility(View.GONE);
                                }


                                //友聚
                                dataHuoDongLiveModelList.clear();
                                dataHuoDongLiveModelList.addAll(model.getObj().getActivity());
                                //友聚列表
                                homeDataRecommendHuoDongLiveAdapter = new HomeDataRecommendHuoDongLiveAdapter(dataHuoDongLiveModelList);
                                homeDataRecommendHuoDongLiveAdapter.setListener(new HomeDataRecommendHuoDongLiveAdapter.LiveListAdapterListener() {
                                    @Override
                                    public void onCLickListen(int pos) {
                                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                                            enterLiveRoom(dataHuoDongLiveModelList.get(pos).getCaptain());
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
                                recyclerViewTuiJianYouJu.setLayoutManager(manager2);
                                recyclerViewTuiJianYouJu.setAdapter(homeDataRecommendHuoDongLiveAdapter);
                                //悬浮球
                                if (model.getObj().getStatus2().equals("1")){
                                    rl_ball.setVisibility(View.VISIBLE);
                                }else {
                                    rl_ball.setVisibility(View.GONE);
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

        ViseHttp.POST(NetConfig.homeYouJiVideo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJiVideo))
                .addParam("uid", uid)
                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomeDataYouJiVideoModel model = gson.fromJson(data, HomeDataYouJiVideoModel.class);
                                page1 = 2;
                                //友记
                                mListTuiJian_youji = model.getObj();
                                adapterTuiJian_youji = new HomeDataYouJiVideoAdapter(mListTuiJian_youji);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerViewTuiJianYouJi.setLayoutManager(manager);
                                recyclerViewTuiJianYouJi.setAdapter(adapterTuiJian_youji);
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
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (type){
                    case "1":
                        ViseHttp.POST(NetConfig.homeYouJiVideo)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJiVideo))
                                .addParam("uid", uid)
                                .addParam("city", cityName)
                                .addParam("page",page1+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeDataYouJiVideoModel model = gson.fromJson(data, HomeDataYouJiVideoModel.class);
//                                        mList.clear();
                                                if (model.getObj().size()>0){
                                                    mListTuiJian_youji.addAll(model.getObj());
                                                    preLoadYouJi_tuijain(model.getObj());
                                                    if (mListTuiJian_youji!=null && adapterTuiJian_youji!=null){
                                                        adapterTuiJian_youji.notifyDataSetChanged();
                                                    }
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

    }

    private void enterLiveRoom(final HomeDataBannerHuoDongLiveModel.ObjBean.ActivityBean.CaptainBean zhiboBean) {
        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"进入房间中");
        Log.d("asdasdas","UID:"+zhiboBean.getUid()+"///"+zhiboBean.getChannel_id());
        if (zhiboBean.getChannel_id() == null || TextUtils.isEmpty(zhiboBean.getChannel_id())){
            Intent intent = new Intent();
            intent.setClass(getContext(), PersonMainActivity1.class);
            intent.putExtra("person_id", zhiboBean.getUid());
            startActivity(intent);
            WeiboDialogUtils.closeDialog(dialog_loading);
        }else {
            ViseHttp.POST(NetConfig.zhiBoInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.zhiBoInfo))
                    .addParam("uid", zhiboBean.getUid())
                    .addParam("cid",zhiboBean.getChannel_id())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    String liveStatus = jsonObject.getJSONObject("obj").getString("zhibostatus");
                                    String start_time = jsonObject.getJSONObject("obj").getString("start_time");
//                                if (true){
                                    if (liveStatus.equals("1")){
                                        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
                                        roomInfoEntity.setCid(zhiboBean.getChannel_id());
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
                                        intent.setClass(getContext(), PersonMainActivity1.class);
                                        intent.putExtra("is_by_live",true);
                                        intent.putExtra("next_on_live_time",start_time);
                                        intent.putExtra("person_id", zhiboBean.getUid());
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
    }

    private void initTuiJianFirstHuoDong(List<HomeDataBannerHuoDongLiveModel.ObjBean.BannerListBean> datas) {
//        private ImageView iv_first_tuijian;
//        private TextView tv_first_tuijian_title,tv_first_tuijian_content,tv_youji_look_num;
//        private TextView tv_tuyijian_first_tab_1,tv_tuyijian_first_tab_2;
        mListTuiJian_first.clear();
        mListTuiJian_first.addAll(datas);
        if (firstBannerAdapter == null){
            firstBannerAdapter = new HomeFragmentFirstBannerAdapter1(mListTuiJian_first);
        }else {
            firstBannerAdapter.notifyDataSetChanged();
        }
        recyclerbanner.setAdapter(firstBannerAdapter);
        recyclerbanner.setMoveSpeed(1f);
        recyclerbanner.setCenterScale(1f);
        recyclerbanner.setAutoPlaying(true);
        recyclerbanner.setAutoPlayDuration(2500);
        recyclerbanner.setShowIndicator(false);
        recyclerbanner.setOnCurrentChangeListenner(new BannerLayout.OnCurrentChangeListenner() {
            @Override
            public void onCurrentChange(int position) {

            }
        });
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
                                    cityTv.setText("所在位置："+latLongString);
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
    R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4,R.id.rl_xiaoxi,R.id.rl_ball})
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
            case R.id.rl_xiaoxi:
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
                ViseHttp.POST(NetConfig.homePage)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePage))
                        .addParam("uid", uid)
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", type+"--------"+uid);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeDataBannerHuoDongLiveModel model = gson.fromJson(data, HomeDataBannerHuoDongLiveModel.class);

                                        if (mListTuiJian_youji.size()>0){
                                            preLoadYouJi_tuijain(mListTuiJian_youji);
                                            tv_text_youji.setVisibility(View.VISIBLE);
                                        }else {
                                            tv_text_youji.setVisibility(View.GONE);
                                        }
                                        //轮播图
                                        initTuiJianFirstHuoDong(model.getObj().getBannerList());
                                        if (model.getObj().getActivity().size()>0){
                                            rlTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        }else {
                                            rlTuiJianHuodongFirst.setVisibility(View.GONE);
                                        }
                                        dataHuoDongLiveModelList.clear();
                                        dataHuoDongLiveModelList.addAll(model.getObj().getActivity());
                                        homeDataRecommendHuoDongLiveAdapter.notifyDataSetChanged();
                                        if (model.getObj().getBannerList().size()>0){
//                                            tv_text_youju.setVisibility(View.VISIBLE);
                                            rlTuiJianHuodongFirst.setVisibility(View.VISIBLE);
                                        }else {
//                                            tv_text_youju.setVisibility(View.GONE);
                                            rlTuiJianHuodongFirst.setVisibility(View.GONE);
                                        }
                                        if (model.getObj().getStatus2().equals("1")){
                                            rl_ball.setVisibility(View.VISIBLE);
                                        }else {
                                            rl_ball.setVisibility(View.GONE);
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
                ViseHttp.POST(NetConfig.homeYouJiVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeYouJiVideo))
                        .addParam("uid", uid)
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data+"--------");
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeDataYouJiVideoModel model = gson.fromJson(data,HomeDataYouJiVideoModel.class);
                                        page1 = 2;
                                        mListTuiJian_youji.clear();
                                        mListTuiJian_youji.addAll(model.getObj());
                                        adapterTuiJian_youji.notifyDataSetChanged();
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
        if (requestCode == 1){
            if (data != null && resultCode == 1) {
                CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
                cityTv.setText("所在位置："+model.getName());
                cityName = model.getName();
                cityId = model.getId();
            } else if (resultCode == 2) {
                cityId = "";
                cityName = "";
//            cityTv.setText(latLongString);
                cityTv.setText("所在位置：选择城市");
            } else if (resultCode == 3) {
                String city = data.getStringExtra("city");
                cityId = data.getStringExtra("cityid");
                cityName = city;
                cityTv.setText("所在位置："+city);
            }
            refresh();
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
                    for (int i =0;i<dataHuoDongLiveModelList.size();i++){
                        if (dataHuoDongLiveModelList.get(i).getPfID().equals(id)){
                            dataHuoDongLiveModelList.remove(i);
                            homeDataRecommendHuoDongLiveAdapter.notifyDataSetChanged();
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
    private void preLoadYouJi_tuijain(List<HomeDataYouJiVideoModel.ObjBean> list) {
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
        Log.d("adasds",position+"");
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
            if (dataHuoDongLiveModelList.get(position).getCaptain().getFollow().equals("0")){//未关注
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", uid)
                        .addParam("likeId", dataHuoDongLiveModelList.get(position).getCaptain().getUid())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("adasds",result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("code") == 200) {
                                        dataHuoDongLiveModelList.get(position).getCaptain().setFollow("1");
                                        homeDataRecommendHuoDongLiveAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                    }else if(jsonObject.getInt("code") == 400){

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.d("adasds",errMsg);
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
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
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
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
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
