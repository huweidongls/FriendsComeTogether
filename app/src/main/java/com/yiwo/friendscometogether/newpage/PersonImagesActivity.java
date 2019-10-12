package com.yiwo.friendscometogether.newpage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.PersonMain_Pics_model;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_Pics_Adapter;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonImagesActivity extends BaseActivity {


    @BindView(R.id.rl_pics)
    RelativeLayout rl_pics;
    @BindView(R.id.rv_pic)
    RecyclerView rv_pic;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;


    private SpImp spImp;
    private String person_id;
    private String status;
    private int page = 1;
    private PersonMainActivity_Pics_Adapter picsAdapter;
    private List<PersonMain_Pics_model.ObjBean.PhotoBean> picsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_images);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonImagesActivity.this);
        spImp = new SpImp(PersonImagesActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        status = getIntent().getStringExtra("status");
        refreshLayout.setRefreshHeader(new ClassicsHeader(PersonImagesActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(PersonImagesActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                //----------照片----------  已经去掉照片选项卡
                ViseHttp.POST(NetConfig.homepagePartFour)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartFour))
                        .addParam("uid", spImp.getUID())
                        .addParam("tid", person_id)
                        .addParam("status",status)
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.d("asdasfsada_zhaopain",data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PersonMain_Pics_model model = gson.fromJson(data,PersonMain_Pics_model.class);
                                        picsList.addAll(model.getObj().getPhoto());
                                        picsAdapter.notifyDataSetChanged();
                                        page ++;
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
        StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_pic.setLayoutManager(mLayoutManager1);
        picsAdapter = new PersonMainActivity_Pics_Adapter(picsList);
        rv_pic.setAdapter(picsAdapter);
        initData();
    }

    private void initData() {
        //----------照片----------
        ViseHttp.POST(NetConfig.homepagePartFour)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartFour))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.d("asdasfsada_zhaopain",data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_Pics_model model = gson.fromJson(data,PersonMain_Pics_model.class);
                                picsList.clear();
                                picsList.addAll(model.getObj().getPhoto());
                                picsAdapter.notifyDataSetChanged();
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
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
