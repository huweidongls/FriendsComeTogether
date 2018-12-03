package com.yiwo.friendscometogether.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.FeedBackAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.FeedBackModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    SpImp spImp;
    @BindView(R.id.activity_feedback_et_content)
    EditText contentEt;
    @BindView(R.id.activity_feedback_bt_submit)
    Button submitBt;
    @BindView(R.id.activity_feedback_rv)
    RecyclerView feedbackRv;
    FeedBackAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp =new SpImp(this);

        initData();
    }

    @OnClick({R.id.activity_feedback_rl_back,R.id.activity_feedback_bt_submit})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.activity_feedback_rl_back:
                finish();
                break;
            case R.id.activity_feedback_bt_submit:
//                toToast(this,"提交");
                submit();
                break;
        }
    }

    public void initData(){
        ViseHttp.POST(NetConfig.historicalFeedBackUrl)
                .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.historicalFeedBackUrl))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i("101101",data);
                        FeedBackModel model = new Gson().fromJson(data,FeedBackModel.class);
                        if (model.getCode()==200){
                            initList(model.getObj());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    public void initList(List<FeedBackModel.ObjBean> data){
        LinearLayoutManager manager = new LinearLayoutManager(FeedbackActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        feedbackRv.setLayoutManager(manager);
        adapter = new FeedBackAdapter(data);
        feedbackRv.setAdapter(adapter);
    }

    public void submit(){
        if(!StringUtils.isEmpty(contentEt.getText().toString())){
            ViseHttp.POST(NetConfig.submitFeedBackUrl)
                    .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.submitFeedBackUrl))
                    .addParam("uid",spImp.getUID())
                    .addParam("content",contentEt.getText().toString())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.i("110011",data);
                            FocusOnToFriendTogetherModel model = new Gson().fromJson(data,FocusOnToFriendTogetherModel.class);
                            if (model.getCode()==200){
                                toToast(FeedbackActivity.this,model.getMessage());
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
            toToast(FeedbackActivity.this,"内容不能为空");
        }

    }
}
