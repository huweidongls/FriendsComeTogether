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
import com.yiwo.friendscometogether.adapter.JoinActiveAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.JoinActivemodel;
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

public class JoinActiveActivity extends BaseActivity {

    @BindView(R.id.activity_join_active_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_join_active_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_join_active_refreshLayout)
    RefreshLayout refreshLayout;

    private JoinActiveAdapter adapter;
    private SpImp spImp;
    private List<JoinActivemodel.ObjBean> mList;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_active);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initData();

    }

    private void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(JoinActiveActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(JoinActiveActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.activitiesAttendedUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activitiesAttendedUrl))
                        .addParam("page", "1")
                        .addParam("user_id", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.i("147852", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        JoinActivemodel model = new Gson().fromJson(data, JoinActivemodel.class);
                                        page = 2;
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
                ViseHttp.POST(NetConfig.activitiesAttendedUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activitiesAttendedUrl))
                        .addParam("page", page + "")
                        .addParam("user_id", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.i("147852", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        JoinActivemodel model = new Gson().fromJson(data, JoinActivemodel.class);
                                        page = page + 1;
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

        ViseHttp.POST(NetConfig.activitiesAttendedUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activitiesAttendedUrl))
                .addParam("page", "1")
                .addParam("user_id", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i("147852", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                JoinActivemodel model = new Gson().fromJson(data, JoinActivemodel.class);
                                page = page + 1;
                                initList(model.getObj());
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

    private void initList(List<JoinActivemodel.ObjBean> data) {

        mList = data;
        LinearLayoutManager manager = new LinearLayoutManager(JoinActiveActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new JoinActiveAdapter(mList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.activity_join_active_rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_join_active_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        JoinActiveActivity.this.finish();
    }
}
