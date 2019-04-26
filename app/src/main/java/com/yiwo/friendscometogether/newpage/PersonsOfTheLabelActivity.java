package com.yiwo.friendscometogether.newpage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
import com.yiwo.friendscometogether.newadapter.PersonGroupAdapter;
import com.yiwo.friendscometogether.newmodel.PersonGroupModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonsOfTheLabelActivity extends BaseActivity {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private PersonGroupAdapter adapter;
    private SpImp spImp;
    private String person_id;
    private String label_id;
    private int page = 1;
    private List<PersonGroupModel.ObjBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_of_the_label);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonsOfTheLabelActivity.this);
        spImp = new SpImp(PersonsOfTheLabelActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        label_id = getIntent().getStringExtra("label_id");
        tv_title.setText(getIntent().getStringExtra("title"));
        initRv();
        initRefresh();
        initData();
    }

    private void initRefresh() {
        refresh_layout.setRefreshHeader(new ClassicsHeader(PersonsOfTheLabelActivity.this));
        refresh_layout.setRefreshFooter(new ClassicsFooter(PersonsOfTheLabelActivity.this));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.clickUserList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.clickUserList))
                        .addParam("userID",person_id)
                        .addParam("labelID",label_id)
                        .addParam("page","1")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PersonGroupModel model = gson.fromJson(data,PersonGroupModel.class);
                                        list.clear();
                                        list.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                        refreshLayout.finishRefresh(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    refreshLayout.finishRefresh(1000);
                                }

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.clickUserList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.clickUserList))
                        .addParam("userID",person_id)
                        .addParam("labelID",label_id)
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PersonGroupModel model = gson.fromJson(data,PersonGroupModel.class);
                                        list.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page++;
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    refreshLayout.finishLoadMore(1000);
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }

    private void initRv() {
        GridLayoutManager manager = new GridLayoutManager(PersonsOfTheLabelActivity.this,4);
        rv.setLayoutManager(manager);
        adapter = new PersonGroupAdapter(list);
        rv.setAdapter(adapter);
    }

    private void initData() {
        ViseHttp.POST(NetConfig.clickUserList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.clickUserList))
                .addParam("userID",person_id)
                .addParam("labelID",label_id)
                .addParam("page","1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonGroupModel model = gson.fromJson(data,PersonGroupModel.class);
                                list.clear();
                                list.addAll(model.getObj());
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

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
