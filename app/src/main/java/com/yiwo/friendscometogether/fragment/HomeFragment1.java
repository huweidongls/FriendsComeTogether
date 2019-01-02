package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.AllBannerModel;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.HomeTogetherModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeDataAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment1 extends BaseFragment {

    private Context context = getContext();
    View rootView;

    @BindView(R.id.fragment_home_banner)
    Banner banner;
    @BindView(R.id.rv_home)
    RecyclerView recyclerView;
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

    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String latLongString = "";

    private SpImp spImp;
    private String uid = "";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getCity();
                    break;
            }
        }

    };

    private HomeDataAdapter adapter;
    private List<HomeDataModel.ObjBean> mList;

    private String cityId = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home1, null);

        ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());

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
        }
    }

    private void initData() {

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
                                        if (bannerModel.getObj().get(position).getFirst_type().equals("0")) {
                                            Intent intent = new Intent(getContext(), DetailsOfFriendTogetherActivity.class);
                                            intent.putExtra("pfID", bannerModel.getObj().get(position).getLeftid());
                                            startActivity(intent);
                                        } else if (bannerModel.getObj().get(position).getFirst_type().equals("1")) {
                                            Intent intent = new Intent(getContext(), DetailsOfFriendsActivity.class);
                                            intent.putExtra("fmid", bannerModel.getObj().get(position).getLeftid());
                                            startActivity(intent);
                                        }
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

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.newHomeData)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                .addParam("type", "1")
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                mList = model.getObj();
                                adapter = new HomeDataAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
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
                    R.id.ll_home_youji_meishi,R.id.ll_home_youji_tandian,R.id.ll_home_youji_lvxing, R.id.locationRl, R.id.ll_search, R.id.iv_msg})
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_home_youji_all:
                mainActivity.switchFragment(2);
                mainActivity.startYouji();
                break;
            case R.id.ll_home_youji_lvxing:
                break;
            case R.id.ll_home_youji_meishi:
                break;
            case R.id.ll_home_youji_tandian:
                break;
            case R.id.ll_home_youji_gonglue:
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && resultCode == 1) {
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            cityTv.setText(model.getName());
            cityId = model.getId();
            ViseHttp.POST(NetConfig.newHomeData)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                    .addParam("type", "1")
                    .addParam("uid", uid)
                    .addParam("city", cityId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else if (requestCode == 1 && resultCode == 2) {
            cityId = "";
            cityTv.setText(latLongString);
            ViseHttp.POST(NetConfig.newHomeData)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                    .addParam("type", "1")
                    .addParam("uid", uid)
                    .addParam("city", cityId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else if (requestCode == 1 && resultCode == 3) {
            String city = data.getStringExtra("city");
            cityId = data.getStringExtra("cityid");
            cityTv.setText(city);
            ViseHttp.POST(NetConfig.newHomeData)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newHomeData))
                    .addParam("type", "1")
                    .addParam("uid", uid)
                    .addParam("city", cityId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    HomeDataModel model = gson.fromJson(data, HomeDataModel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    adapter.notifyDataSetChanged();
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
