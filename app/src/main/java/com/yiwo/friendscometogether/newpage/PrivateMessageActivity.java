package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
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
                                LinearLayoutManager manager = new LinearLayoutManager(PrivateMessageActivity.this){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv_private_message.setLayoutManager(manager);
                                list = model.getObj();
                                adapter = new PrivateMessageAdapter(list);
                                adapter.setMessageListioner(messageListioner);
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
    @OnClick({R.id.rl_back,R.id.rl_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_clear:
                toDialog(context, "提示", "确定清空私信消息？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViseHttp.POST(NetConfig.delFriendInfo)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.delFriendInfo))
                                .addParam("uid", spImp.getUID())
                                .addParam("type","1")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                list.clear();
                                                adapter.notifyDataSetChanged();
                                                toToast(context,"已清空");
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
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                break;
        }
    }
    PrivateMessageAdapter.MessageListioner messageListioner = new PrivateMessageAdapter.MessageListioner() {

        @Override
        public void agreeListion(final int position, final String messageId, final String name, final String YX_id) {
            if (list.get(position).getType().equals("0")){
                ViseHttp.POST(NetConfig.agreeOrRefuse)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.agreeOrRefuse))
                        .addParam("id", messageId)
                        .addForm("type","0")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
//                        try {
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Log.d("2222",data);
                                        toToast(context,"开始和"+name+"聊天");
                                        list.get(position).setRadio("1");
                                        adapter.notifyDataSetChanged();
                                        liaotian(YX_id);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(context,"接受聊天失败");
                            }
                        });
            }else if(list.get(position).getType().equals("1")){
                ViseHttp.POST(NetConfig.agreeIngroup)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.agreeIngroup))
                        .addParam("id", messageId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {

                                Log.d("2222",data+"///"+messageId);
                                toToast(context,"已同意");
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }

        }

        @Override
        public void disAgreeListion(final int position, String messageId) {
            if (list.get(position).getType().equals("0")){
                ViseHttp.POST(NetConfig.agreeOrRefuse)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.agreeOrRefuse))
                        .addParam("id", messageId)
                        .addForm("type","1")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("2222",data);
                                toToast(context,"已拒绝");
                                list.get(position).setRadio("2");
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }else if (list.get(position).getType().equals("1")){
                ViseHttp.POST(NetConfig.noInGroup)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.noInGroup))
                        .addParam("id", messageId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("2222",data);
                                toToast(context,"已拒绝");
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }

        }

        @Override
        public void liaotianListion(String Yx_id) {
            liaotian(Yx_id);
        }
    };
    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(spImp.getYXID());
        NimUIKit.startP2PSession(context, liaotianAccount);
    }
}
