package com.yiwo.friendscometogether.newfragment;

import android.app.Dialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/12/26.
 */

public class NewPersonMainActivity_PicsFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private YouJiAdapter adapter;
    private List<YouJiListModel.ObjBean> mList;
    private SpImp spImp;
    private String uid = "";
    private int page = 1;
    private String nearby = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpersonmain_pics, null);
        Log.d("33330","oncreate");
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        uid = spImp.getUID();
        initData();
        return view;
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
                                Log.d("3333",data);
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
                                rv.setLayoutManager(mLayoutManager);
                                Collections.shuffle(mList);
                                adapter = new YouJiAdapter(mList);
                                rv.setAdapter(adapter);
                                page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({})
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent();
        switch (view.getId()) {
        }
    }
}
