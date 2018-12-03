package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.GridDividerItemDecoration;
import com.yiwo.friendscometogether.adapter.OtherPicAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MyPicListModel;
import com.yiwo.friendscometogether.network.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherPicActivity extends BaseActivity {

    @BindView(R.id.activity_other_pic_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_other_pic_rv)
    RecyclerView recyclerView;

    private String otherUid = "";

    private OtherPicAdapter adapter;
    private List<MyPicListModel.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_pic);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(OtherPicActivity.this);

        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {

        Intent intent = getIntent();
        otherUid = intent.getStringExtra("otheruid");

//        GridLayoutManager manager = new GridLayoutManager(OtherPicActivity.this, 2);
        LinearLayoutManager manager = new LinearLayoutManager(OtherPicActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new GridDividerItemDecoration(1, Color.parseColor("#f2f2f2")));

        ViseHttp.POST(NetConfig.myPictureListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myPictureListUrl))
                .addParam("uid", otherUid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyPicListModel model = gson.fromJson(data, MyPicListModel.class);
                                mList = model.getObj();
                                adapter = new OtherPicAdapter(mList);
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

    @OnClick({R.id.activity_other_pic_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_other_pic_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OtherPicActivity.this.finish();
    }
}
