package com.yiwo.friendscometogether.pages;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
import com.yiwo.friendscometogether.adapter.MessageFriendsAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MessageFriendsModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageFriendsActivity extends BaseActivity {

    @BindView(R.id.activity_message_friends_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_message_friends_rv)
    RecyclerView recyclerView;

    private String uid = "";
    private SpImp spImp;

    private MessageFriendsAdapter adapter;
    private List<MessageFriendsModel.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_friends);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(MessageFriendsActivity.this);
        spImp = new SpImp(MessageFriendsActivity.this);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.messageFriendsUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageFriendsUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("22222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MessageFriendsModel model = gson.fromJson(data, MessageFriendsModel.class);
                                LinearLayoutManager manager = new LinearLayoutManager(MessageFriendsActivity.this);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.addItemDecoration(new DividerItemDecoration(MessageFriendsActivity.this, DividerItemDecoration.VERTICAL));
                                mList = model.getObj();
                                adapter = new MessageFriendsAdapter(mList);
                                recyclerView.setAdapter(adapter);

                                adapter.setOnFriendsListener(new MessageFriendsAdapter.OnFriendsListener() {
                                    @Override
                                    public void onFriend(int type, final int position) {
                                        switch (type){
                                            case 1:
                                                ViseHttp.POST(NetConfig.messageFriendsOkUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageFriendsOkUrl))
                                                        .addParam("id", mList.get(position).getFID())
                                                        .addParam("type", "1")
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                Log.e("222", data);
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
                                            case 2:
                                                ViseHttp.POST(NetConfig.messageFriendsOkUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageFriendsOkUrl))
                                                        .addParam("id", mList.get(position).getFID())
                                                        .addParam("type", "0")
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                Log.e("222", data);
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if(jsonObject1.getInt("code") == 200){
                                                                        toToast(MessageFriendsActivity.this, "添加成功");
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

    @OnClick({R.id.activity_message_friends_rl_back, R.id.rl_clean})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_message_friends_rl_back:
                finish();
                break;
            case R.id.rl_clean:
                ViseHttp.POST(NetConfig.deleteMessageUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteMessageUrl))
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "3")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(MessageFriendsActivity.this, "已清空");
                                        mList.clear();
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        toToast(MessageFriendsActivity.this, jsonObject.getString("message"));
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
        MessageFriendsActivity.this.finish();
    }
}
