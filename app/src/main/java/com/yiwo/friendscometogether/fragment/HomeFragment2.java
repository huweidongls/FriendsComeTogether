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
import com.yiwo.friendscometogether.newadapter.HomeListVideoAdapter;
import com.yiwo.friendscometogether.newadapter.HomeListYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newmodel.HomeVideoListModel;
import com.yiwo.friendscometogether.newmodel.IndexLabelModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.UserUtils;
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
    @BindView(R.id.rv_home_1)
    RecyclerView recyclerView1;//推荐
    @BindView(R.id.rv_home_2)
    RecyclerView recyclerView2;//关注
    @BindView(R.id.rv_home_3)
    RecyclerView recyclerView3;//友记
    @BindView(R.id.rv_home_4)
    RecyclerView recyclerView4;//小视频
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

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
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

    private HomeDataAdapter1 adapter1;//推荐列表适配器
    private HomeDataAdapter1 adapter2;//关注列表适配器
    private HomeListYouJiAdapter adapter3;//友记列表适配器
    private HomeListVideoAdapter adapter4;//视频列表适配器
    private List<HomeDataModel.ObjBean> mList1;
    private List<HomeDataModel.ObjBean> mList2;
    private List<HomeDataModel.ObjBean> mList3;
    private List<HomeVideoListModel.ObjBean> mList4;
    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private int page4 = 1;

    private String cityId = "";
    private String type = "1";
    private String cityName = "";
    private IndexLabelModel labelModel;

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
        return rootView;
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
        ClassicsHeader header = new ClassicsHeader(getContext());
        header.setAccentColor(Color.WHITE);
        header.setPrimaryColor(Color.parseColor("#d84c37"));
        refreshLayout.setRefreshHeader(header);
        ClassicsFooter footer = new ClassicsFooter(getContext());
        footer.setPrimaryColor(Color.parseColor("#F8F8F8"));
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
                                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
//                                        mList.clear();
                                                if (model.getObj().size()>0){
                                                    mList1.addAll(model.getObj());
                                                    adapter1.notifyDataSetChanged();
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
                ViseHttp.POST(NetConfig.newHomeData)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                        .addParam("type", type)
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
                                        HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
//                                        mList.clear();
                                        if (model.getObj().size()>0){
                                            mList1.addAll(model.getObj());
                                            adapter1.notifyDataSetChanged();
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
                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                page1 = 2;
                                mList1 = model.getObj();
                                adapter1 = new HomeDataAdapter1(mList1);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView1.setLayoutManager(manager);
                                recyclerView1.setAdapter(adapter1);
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
    R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
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
            case R.id.rl1:
                tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                tvRl1.setTextColor(Color.parseColor("#333333"));
//                tvRl2.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl3.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl4.setTextColor(Color.parseColor("#9e9e9e"));
                tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                type = "1";
//                refresh("1");
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                recyclerView4.setVisibility(View.GONE);
                break;
            case R.id.rl2:
                tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                tvRl1.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl2.setTextColor(Color.parseColor("#333333"));
//                tvRl3.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl4.setTextColor(Color.parseColor("#9e9e9e"));
                tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                type = "2";
//                refresh("2");
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.GONE);
                recyclerView4.setVisibility(View.GONE);
                break;
            case R.id.rl3:
                tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//                tvRl1.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl2.setTextColor(Color.parseColor("#9e9e9e"));
//                tvRl3.setTextColor(Color.parseColor("#333333"));
//                tvRl4.setTextColor(Color.parseColor("#9e9e9e"));
                tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.GONE);
                type = "3";
//                refresh("3");
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                recyclerView4.setVisibility(View.GONE);
                break;
            case R.id.rl4:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tvRl4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                    tvRl1.setTextColor(Color.parseColor("#9e9e9e"));
//                    tvRl2.setTextColor(Color.parseColor("#9e9e9e"));
//                    tvRl3.setTextColor(Color.parseColor("#9e9e9e"));
//                    tvRl4.setTextColor(Color.parseColor("#333333"));
                    tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    v1.setVisibility(View.GONE);
                    v2.setVisibility(View.GONE);
                    v3.setVisibility(View.GONE);
                    v4.setVisibility(View.VISIBLE);
                    type = "4";
//                    refresh("4");
                    recyclerView1.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);
                    recyclerView3.setVisibility(View.GONE);
                    recyclerView4.setVisibility(View.VISIBLE);

                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
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
                ViseHttp.POST(NetConfig.newHomeData)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                        .addParam("type", type)
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
                                        HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                        mList1.clear();
                                        mList1.addAll(model.getObj());
                                        adapter1.notifyDataSetChanged();
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
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList1.clear();
                                    mList1.addAll(model.getObj());
                                    adapter1.notifyDataSetChanged();
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
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList1.clear();
                                    mList1.addAll(model.getObj());
                                    adapter1.notifyDataSetChanged();
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
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList1.clear();
                                    mList1.addAll(model.getObj());
                                    adapter1.notifyDataSetChanged();
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
        scrollView.fullScroll(View.FOCUS_UP);
    }
}
