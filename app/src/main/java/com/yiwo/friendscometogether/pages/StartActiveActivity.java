package com.yiwo.friendscometogether.pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.StartActiveAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.InitiativesModel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActiveActivity extends BaseActivity {

    @BindView(R.id.activity_start_active_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_start_active_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_start_active_refreshLayout)
    RefreshLayout refreshLayout;

    private StartActiveAdapter adapter;
    private SpImp spImp;
    private List<InitiativesModel.ObjBean> mList;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_active);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(this);
        init();

    }

    public void init() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(StartActiveActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(StartActiveActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.initiativesListUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.initiativesListUrl))
                        .addParam("page", "1")
                        .addParam("user_id", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.i("98521", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        page = 2;
                                        InitiativesModel model = new Gson().fromJson(data, InitiativesModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        refreshlayout.finishRefresh(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.initiativesListUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.initiativesListUrl))
                        .addParam("page", page + "")
                        .addParam("user_id", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.i("98521", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        page = page + 1;
                                        InitiativesModel model = new Gson().fromJson(data, InitiativesModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        refreshlayout.finishLoadMore(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishLoadMore(1000);
                            }
                        });
            }
        });

        Log.i("id", spImp.getUID());
        ViseHttp.POST(NetConfig.initiativesListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.initiativesListUrl))
                .addParam("page", "1")
                .addParam("user_id", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i("98521", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                page = 2;
                                InitiativesModel model = new Gson().fromJson(data, InitiativesModel.class);
                                initData(model.getObj());
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

    private void initData(List<InitiativesModel.ObjBean> data) {
        mList = data;
        LinearLayoutManager manager = new LinearLayoutManager(StartActiveActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new StartActiveAdapter(mList);
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.activity_start_active_rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_start_active_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StartActiveActivity.this.finish();
    }
}
