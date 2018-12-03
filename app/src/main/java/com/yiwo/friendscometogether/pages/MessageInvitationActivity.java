package com.yiwo.friendscometogether.pages;

import android.content.Intent;
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
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageInvitationActivity extends BaseActivity {

    @BindView(R.id.activity_message_invitation_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_message_invitation_rv)
    RecyclerView recyclerView;

    private String uid = "";
    private SpImp spImp;

    private MessageInvitationAdapter adapter;
    private List<MessageInvitationListModel.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_invitation);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(MessageInvitationActivity.this);
        spImp = new SpImp(MessageInvitationActivity.this);

        initData();

    }

    private void initData() {

        ViseHttp.POST(NetConfig.messageInvitationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageInvitationListUrl))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("22222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MessageInvitationListModel model = gson.fromJson(data, MessageInvitationListModel.class);
                                LinearLayoutManager manager = new LinearLayoutManager(MessageInvitationActivity.this);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                mList = model.getObj();
                                adapter = new MessageInvitationAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnApplyListener(new MessageInvitationAdapter.OnApplyListener() {
                                    @Override
                                    public void onApply(int type, final int position) {
                                        Intent intent = new Intent();
                                        switch (type){
                                            case 0:
                                                ViseHttp.POST(NetConfig.invitationNoUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.invitationNoUrl))
                                                        .addParam("id", mList.get(position).getId())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if(jsonObject1.getInt("code") == 200){
                                                                        mList.remove(position);
                                                                        adapter.notifyDataSetChanged();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFail(int errCode, String errMsg) {

                                                            }
                                                        });
                                                break;
                                            case 1:
                                                intent.setClass(MessageInvitationActivity.this, ApplyActivity.class);
                                                intent.putExtra("id", mList.get(position).getId());
                                                intent.putExtra("tid", mList.get(position).getTid());
                                                startActivity(intent);
                                                finish();
                                                break;
                                        }
                                    }
                                });
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

    @OnClick({R.id.activity_message_invitation_rl_back, R.id.rl_clean})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_message_invitation_rl_back:
                MessageInvitationActivity.this.finish();
                break;
            case R.id.rl_clean:
                ViseHttp.POST(NetConfig.deleteMessageUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteMessageUrl))
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "4")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(MessageInvitationActivity.this, "已清空");
                                        mList.clear();
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        toToast(MessageInvitationActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MessageInvitationActivity.this.finish();
    }
}
