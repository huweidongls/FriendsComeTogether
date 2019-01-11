package com.yiwo.friendscometogether.newfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private YouJiAdapter adapter;
    private List<YouJiListModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";
    private int page = 1;
    private String nearby = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youji, null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        uid = spImp.getUID();

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            Log.e("123123", "hidden");
        }else {
            Log.e("123123", "nohidden");
            initData();
        }
    }

    private void initData() {

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
                                        mList.addAll(model.getObj());
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

    @OnClick({R.id.searchLl, R.id.rl_nearby})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.searchLl:
                intent.setClass(getContext(), SearchActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.rl_nearby:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    if(nearby.equals("0")){
                        nearby = "1";
                        Toast.makeText(getContext(), "开启搜索附近", Toast.LENGTH_SHORT).show();
                        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.youji_rotate);
                        LinearInterpolator interpolator = new LinearInterpolator();
                        rotateAnimation.setInterpolator(interpolator);
                        ivNearby.startAnimation(rotateAnimation);
                        nearby();
                    }else {
                        nearby = "0";
                        Toast.makeText(getContext(), "关闭搜索附近", Toast.LENGTH_SHORT).show();
                        ivNearby.clearAnimation();
                        nearby();
                    }
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void nearby(){
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
                                adapter.notifyDataSetChanged();
                                page = 2;
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
