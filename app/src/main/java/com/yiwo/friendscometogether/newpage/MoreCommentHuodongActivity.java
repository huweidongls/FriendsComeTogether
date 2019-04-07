package com.yiwo.friendscometogether.newpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.AllCommentHuoDongAdapter;
import com.yiwo.friendscometogether.newmodel.AllCommentHuoDongModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MoreCommentHuodongActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private Unbinder unbinder;
    private List<AllCommentHuoDongModel.ObjBean> list = new ArrayList<>();
    private AllCommentHuoDongAdapter allCommentHuoDongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_comment_huodong);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        final LinearLayoutManager manager = new LinearLayoutManager(MoreCommentHuodongActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ViseHttp.POST(NetConfig.commentListAll)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.commentListAll))
                .addParam("pfID", getIntent().getStringExtra("pfID"))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                AllCommentHuoDongModel model = gson.fromJson(data,AllCommentHuoDongModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                allCommentHuoDongAdapter = new AllCommentHuoDongAdapter(list);
                                rv.setLayoutManager(manager);
                                rv.setAdapter(allCommentHuoDongAdapter);
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
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
