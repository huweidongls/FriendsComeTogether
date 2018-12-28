package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeDataAdapter;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment1 extends BaseFragment {

    private Context context = getContext();
    View rootView;

    @BindView(R.id.rv_home)
    RecyclerView recyclerView;
    @BindView(R.id.tv_city_name)
    TextView cityTv;

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

}
