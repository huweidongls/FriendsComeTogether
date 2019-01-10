package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MessageInvitationAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MessageInvitationListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PrivateMessageAdapter;
import com.yiwo.friendscometogether.newmodel.PrivateMessageModel;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.MessageInvitationActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivateMessageActivity extends BaseActivity {

    @BindView(R.id.rv_private_message)
    RecyclerView rv_private_message;

    private Context context;
    private SpImp spImp;
    private List<PrivateMessageModel.ObjBean> list = new ArrayList<>();
    private PrivateMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_message);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PrivateMessageActivity.this);
        context = PrivateMessageActivity.this;
        spImp =new SpImp(context);
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.privateLetterList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.privateLetterList))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("22222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PrivateMessageModel model = gson.fromJson(data, PrivateMessageModel.class);
                                LinearLayoutManager manager = new LinearLayoutManager(PrivateMessageActivity.this);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv_private_message.setLayoutManager(manager);
                                list = model.getObj();
                                adapter = new PrivateMessageAdapter(list);
                                rv_private_message.setAdapter(adapter);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
