package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
import com.yiwo.friendscometogether.newadapter.HomeDataAdapter1;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendAdapter1;
import com.yiwo.friendscometogether.newadapter.HomeDataRecommendAdapter2;
import com.yiwo.friendscometogether.newadapter.HomeListVideoAdapter;
import com.yiwo.friendscometogether.newadapter.HomeListYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.newmodel.HomeVideoListModel;
import com.yiwo.friendscometogether.newmodel.IndexLabelModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.yiwo.friendscometogether.widget.ViewPagerForScrollView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment2 extends BaseFragment {

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

//    @BindView(R.id.scroll_view)
//    ScrollView scrollView;
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
    RecyclerView recyclerView2;//关注
    RecyclerView recyclerView3;//友记
    RecyclerView recyclerView4;//小视频

    private HomeDataRecommendAdapter1 adapter1_1;//推荐列表youji适配器
    private HomeDataRecommendAdapter2 adapter1_2;//推荐列表友聚适配器
    private HomeDataAdapter1 adapter2;//关注列表适配器
    private HomeListYouJiAdapter adapter3;//友记列表适配器
    private HomeListVideoAdapter adapter4;//视频列表适配器
    private List<HomeDataModel1.ObjBean.ArticleBean> mList1 = new ArrayList<>();
    private List<HomeDataModel1.ObjBean.ActivityBean> mlist1_youju = new ArrayList<>();
    private List<HomeDataModel.ObjBean> mList2 = new ArrayList<>();
    private List<HomeDataModel.ObjBean> mList3 = new ArrayList<>();
    private List<HomeVideoListModel.ObjBean> mList4 = new ArrayList<>();
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
    private TextView tv_text_youji,tv_text_youju;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home2, null);

        ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());
//        AppUpdateUtil.checkUpdate(getActivity(),true);
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
        recyclerView1_1 = view1.findViewById(R.id.rv_home_1_2);
        tv_text_youji = view1.findViewById(R.id.tv_text_youji);
        tv_text_youju = view1.findViewById(R.id.tv_text_youju);
        recyclerView1_2 = view1.findViewById(R.id.rv_home_1_1);
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
                if ((mList2.size() == 0&&position == 1)){//关注无数据时
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
                        v1.setVisibility(View.VISIBLE);
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
        if (!isNetConnect()){
            toToast(getContext(),"当前无网络！");
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
            return;
        }
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (type){
                    case "1":
                        ViseHttp.POST(NetConfig.homeRecommend)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                                    mList1.addAll(model.getObj().getArticle());
                                                    mlist1_youju.addAll(model.getObj().getActivity());
                                                    adapter1_1.notifyDataSetChanged();
                                                    adapter1_2.notifyDataSetChanged();
                                                    if (mlist1_youju.size()>0){
                                                        tv_text_youju.setVisibility(View.VISIBLE);
                                                    }else {
                                                        tv_text_youju.setVisibility(View.GONE);
                                                    }
                                                    if (mList1.size()>0){
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
                        ViseHttp.POST(NetConfig.homeGuanZhu)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu))
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
                                                    mList2.addAll(model.getObj());
                                                    adapter2.notifyDataSetChanged();
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
                                                    mList3.addAll(model.getObj());
                                                    adapter3.notifyDataSetChanged();
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
                                                    mList4.addAll(model.getObj());
                                                    adapter4.notifyDataSetChanged();
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
        ViseHttp.POST(NetConfig.homeRecommend)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                page1 = 2;
                                mList1 = model.getObj().getArticle();
                                adapter1_1 = new HomeDataRecommendAdapter1(mList1);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView1_1.setLayoutManager(manager);
                                recyclerView1_1.setAdapter(adapter1_1);
                                mlist1_youju = model.getObj().getActivity();
                                adapter1_2 = new HomeDataRecommendAdapter2(mlist1_youju);
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
                                recyclerView1_2.setAdapter(adapter1_2);
                                if (mlist1_youju.size()>0){
                                    tv_text_youju.setVisibility(View.VISIBLE);
                                }else {
                                    tv_text_youju.setVisibility(View.GONE);
                                }
                                if (mList1.size()>0){
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
        ViseHttp.POST(NetConfig.homeGuanZhu)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu))
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
                                mList2 = model.getObj();
                                adapter2= new HomeDataAdapter1(mList2);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView2.setLayoutManager(manager);
                                recyclerView2.setAdapter(adapter2);
                            }else {
                                mList2 = new ArrayList<>();
                                adapter2= new HomeDataAdapter1(mList2);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView2.setLayoutManager(manager);
                                recyclerView2.setAdapter(adapter2);
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
                                mList3 = model.getObj();
                                adapter3 = new HomeListYouJiAdapter(mList3);
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView3.setLayoutManager(mLayoutManager);
                                recyclerView3.setAdapter(adapter3);
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
                                mList4 = model.getObj();
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView4.setLayoutManager(mLayoutManager);
                                adapter4 = new HomeListVideoAdapter(mList4);
                                recyclerView4.setAdapter(adapter4);
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
    R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4,R.id.ll_xiaoxi})
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
        }
    }

    private void refresh(){
        if (!isNetConnect()){
            toToast(getContext(),"当前无网络！");
            return;
        }
        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
        switch (type){
            case "1":
                ViseHttp.POST(NetConfig.homeRecommend)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                        mList1.clear();
                                        mList1.addAll(model.getObj().getArticle());
                                        mlist1_youju.clear();
                                        mlist1_youju.addAll(model.getObj().getActivity());
                                        adapter1_1.notifyDataSetChanged();
                                        adapter1_2.notifyDataSetChanged();
                                        if (mlist1_youju.size()>0){
                                            tv_text_youju.setVisibility(View.VISIBLE);
                                        }else {
                                            tv_text_youju.setVisibility(View.GONE);
                                        }
                                        if (mList1.size()>0){
                                            tv_text_youji.setVisibility(View.VISIBLE);
                                        }else {
                                            tv_text_youji.setVisibility(View.GONE);
                                        }
                                        page1 = 2;
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
                break;
            case "2":
                ViseHttp.POST(NetConfig.homeGuanZhu)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGuanZhu))
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
                                        mList2.clear();
                                        mList2.addAll( model.getObj());
                                        adapter2.notifyDataSetChanged();
                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog_loading);
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
                                        mList3.clear();
                                        mList3.addAll(model.getObj());
                                        adapter3.notifyDataSetChanged();
                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog_loading);
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
                                        mList4.clear();
                                        mList4.addAll(model.getObj());
                                        adapter4.notifyDataSetChanged();
                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog_loading);
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
        if (requestCode == 1 && data != null && resultCode == 1) {
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            cityTv.setText(model.getName());
            cityName = model.getName();
            cityId = model.getId();
            dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(), "正在加载...");
            ViseHttp.POST(NetConfig.homeRecommend)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                    mList1.clear();
                                    mList1.addAll(model.getObj().getArticle());
                                    mlist1_youju.clear();
                                    mlist1_youju.addAll(model.getObj().getActivity());
                                    adapter1_1.notifyDataSetChanged();
                                    adapter1_2.notifyDataSetChanged();
                                    if (mlist1_youju.size()>0){
                                        tv_text_youju.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youju.setVisibility(View.GONE);
                                    }
                                    if (mList1.size()>0){
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
            ViseHttp.POST(NetConfig.homeRecommend)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                    mList1.clear();
                                    mList1.addAll(model.getObj().getArticle());
                                    mlist1_youju.clear();
                                    mlist1_youju.addAll(model.getObj().getActivity());
                                    adapter1_1.notifyDataSetChanged();
                                    adapter1_2.notifyDataSetChanged();
                                    if (mlist1_youju.size()>0){
                                        tv_text_youju.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youju.setVisibility(View.GONE);
                                    }
                                    if (mList1.size()>0){
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
            ViseHttp.POST(NetConfig.homeRecommend)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeRecommend))
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
                                    mList1.clear();
                                    mList1.addAll(model.getObj().getArticle());
                                    mlist1_youju.clear();
                                    mlist1_youju.addAll(model.getObj().getActivity());
                                    adapter1_1.notifyDataSetChanged();
                                    adapter1_2.notifyDataSetChanged();
                                    if (mlist1_youju.size()>0){
                                        tv_text_youju.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_text_youju.setVisibility(View.GONE);
                                    }
                                    if (mList1.size()>0){
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
    }
    public void scroll2top(){
//        scrollView.fullScroll(View.FOCUS_UP);
    }
}
