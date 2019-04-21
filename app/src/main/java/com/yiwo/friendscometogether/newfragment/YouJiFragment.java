package com.yiwo.friendscometogether.newfragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.CreateIntercalationActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/12/26.
 */

public class YouJiFragment extends Fragment {

    @BindView(R.id.rv_youji)
    RecyclerView rv_youji;
    @BindView(R.id.fragment_youji_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.iv_nearby)
    ImageView ivNearby;

    private final int LOCATION_CODE = 2;
    private YouJiAdapter adapter;
    private List<YouJiListModel.ObjBean> mList;

    private LocationManager locationManager;

    private SpImp spImp;
    private String uid = "";
    private int page = 1;
    private String nearby = "0";

    private Timer timer;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youji, null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        uid = spImp.getUID();
        timer = new Timer();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            Log.e("123123", "hidden");
        }else {
            Log.e("123123", "nohidden");
            nearby = "0";
            initData();
        }
    }

    private void initData() {
        dialog = WeiboDialogUtils.createLoadingDialog(getContext(), "正在加载...");
        uid = spImp.getUID();
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.newYoujiData)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.newYoujiData))
                        .addParam("page", "1")
                        .addParam("sign", MyApplication.sign)
                        .addParam("uid", spImp.getUID())
                        .addParam("nearby", nearby)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        YouJiListModel model = gson.fromJson(data, YouJiListModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        Collections.shuffle(mList);
                                        adapter.notifyDataSetChanged();
                                        page = 2;
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

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.newYoujiData)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.newYoujiData))
                        .addParam("page", page+"")
                        .addParam("sign", MyApplication.sign)
                        .addParam("uid", spImp.getUID())
                        .addParam("nearby", nearby)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        YouJiListModel model = gson.fromJson(data, YouJiListModel.class);
                                        List<YouJiListModel.ObjBean> listTemp = model.getObj();
                                        Collections.shuffle(listTemp);
                                        mList.addAll(listTemp);
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
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
            }
        });

        ViseHttp.POST(NetConfig.newYoujiData)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.newYoujiData))
                .addParam("page", "1")
                .addParam("sign", MyApplication.sign)
                .addParam("uid", spImp.getUID())
                .addParam("nearby", nearby)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                YouJiListModel model = gson.fromJson(data, YouJiListModel.class);
                                mList = model.getObj();
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                rv_youji.setLayoutManager(mLayoutManager);
                                Collections.shuffle(mList);
                                adapter = new YouJiAdapter(mList);
                                rv_youji.setAdapter(adapter);
                                page = 2;
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @OnClick({R.id.searchLl, R.id.rl_nearby,R.id.rl_back})
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.searchLl:
                intent.setClass(getContext(), SearchActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.rl_nearby:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    nearby = "1";
                    Toast.makeText(getContext(), "开启搜索附近", Toast.LENGTH_SHORT).show();
                    Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.youji_rotate);
                    LinearInterpolator interpolator = new LinearInterpolator();
                    rotateAnimation.setInterpolator(interpolator);
                    ivNearby.startAnimation(rotateAnimation);
                    getLocation();
//                    if(nearby.equals("0")){
//                        nearby = "1";
//                        Toast.makeText(getContext(), "开启搜索附近", Toast.LENGTH_SHORT).show();
//                        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.youji_rotate);
//                        LinearInterpolator interpolator = new LinearInterpolator();
//                        rotateAnimation.setInterpolator(interpolator);
//                        ivNearby.startAnimation(rotateAnimation);
//                        nearby();
//                    }else {
//                        nearby = "0";
//                        Toast.makeText(getContext(), "关闭搜索附近", Toast.LENGTH_SHORT).show();
//                        ivNearby.clearAnimation();
//                        nearby();
//                    }
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_back:
                mainActivity.exitYouji();
                break;
        }
    }

    private void nearby(final Double lat, final Double lng){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ViseHttp.POST(NetConfig.newYoujiData)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.newYoujiData))
                        .addParam("page", "1")
                        .addParam("sign", MyApplication.sign)
                        .addParam("uid", spImp.getUID())
                        .addParam("nearby", nearby)
                        .addParam("lat",lat+"")
                        .addParam("lng",lng+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        YouJiListModel model = gson.fromJson(data, YouJiListModel.class);
                                        if(mList != null){
                                            Toast.makeText(getContext(),"附近搜索成功",Toast.LENGTH_SHORT).show();
                                            mList.clear();
                                            mList.addAll(model.getObj());
                                            adapter.notifyDataSetChanged();
                                            page = 2;
                                        }
                                    }
                                    ivNearby.clearAnimation();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                                    ivNearby.clearAnimation();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                                ivNearby.clearAnimation();
                            }
                        });
            }
        },1500);
    }
    public void getLocation() {
        Log.i("查找城市", "哈尔滨");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        new Thread() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ivNearby.clearAnimation();
                    Message message = new Message();
                    message.arg1 = 1;
                    mhandler.sendMessage(message);
                    return;
                }
                @SuppressLint("MissingPermission") Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude(); // 经度
                    double longitude = location.getLongitude(); // 纬度
                    Log.d("经纬度：","精度："+latitude+";;纬度："+longitude);
                    nearby(latitude,longitude);
                }
            }
        }.start();
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d("asdasdas","请求权限回ass调");
//        switch (requestCode) {
//
//            case LOCATION_CODE:
//                Log.d("asdasdas","请求权限回调");
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("asdasdas","请求权限回调00");
//                    // 权限被用户同意。
//                    // 执形我们想要的操作
//                    @SuppressLint("MissingPermission") Location location = locationManager
//                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    if (location != null) {
//                        Log.d("asdasdas","请求权限回调11");
//                        double latitude = location.getLatitude(); // 经度
//                        double longitude = location.getLongitude(); // 纬度
//                        Log.d("经纬度：","精度："+latitude+";;纬度："+longitude);
//                        nearby(latitude,longitude);
//                    }
//                } else {
//                    Log.d("asdasdas","请求权限回调22");
//                    // 权限被用户拒绝了。
//                    //若是点击了拒绝和不再提醒
//                    //关于shouldShowRequestPermissionRationale
//                    // 1、当用户第一次被询问是否同意授权的时候，返回false
//                    // 2、当之前用户被询问是否授权，点击了false,并且点击了不在询问（第一次询问不会出现“不再询问”的选项），
//                    // 之后便会返回false
//                    // 3、当用户被关闭了app的权限，该app不允许授权的时候，返回false
//                    // 4、当用户上一次不同意授权，没有点击“不再询问”的时候，下一次返回true
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                            || !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                        //提示用户前往设置界面自己打开权限
//                        Log.d("asdasdas","请求权限回调33");
//                        Toast.makeText(getContext(), "请前往设置界面打开地理权限", Toast.LENGTH_SHORT).show();
//                        ivNearby.clearAnimation();
//                    }
//                }
//                break;
//        }
//    }
    Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 1){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("请开启app定位权限后重试")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
            return false;
        }
    });
}
