package com.yiwo.friendscometogether.newpage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.TongBiPayRankAdapter;
import com.yiwo.friendscometogether.newmodel.PayRankTongBiModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GiftSendListActivity extends BaseActivity {


    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout ;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private List<PayRankTongBiModel.ObjBean> mList;
    private TongBiPayRankAdapter adapter;
    private int page = 1;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_send_list);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(GiftSendListActivity.this);
        initData();
    }
    private void initData() {
        mList = new ArrayList<>();

        adapter = new TongBiPayRankAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(GiftSendListActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        refreshLayout.setRefreshHeader(new ClassicsHeader(GiftSendListActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(GiftSendListActivity.this));

        ViseHttp.POST(NetConfig.buyIntegralList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                .addParam("page", "1")
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                page = 2 ;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.buyIntegralList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                        .addParam("page", "1")
                        .addParam("uid",spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2 ;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refreshLayout.finishRefresh(1000);
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
                ViseHttp.POST(NetConfig.buyIntegralList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                        .addParam("page", ""+page)
                        .addParam("uid",spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page ++ ;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refreshLayout.finishLoadMore(1000);
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }
    @OnClick({R.id.rl_back})
    public void onClick(View v ){
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
