package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.BlackUserAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.MyFriendDialog;
import com.yiwo.friendscometogether.model.BlackUserModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlackUserActivity extends BaseActivity {

    @BindView(R.id.activity_black_user_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_black_user_lv)
    ListView listView;

    private BlackUserAdapter adapter;
    private List<BlackUserModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_user);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(BlackUserActivity.this);
        spImp = new SpImp(BlackUserActivity.this);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.blackUserListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.blackUserListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                BlackUserModel model = gson.fromJson(data, BlackUserModel.class);
                                mList = model.getObj();
                                adapter = new BlackUserAdapter(BlackUserActivity.this, mList);
                                listView.setAdapter(adapter);
                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                        MyFriendDialog friendDialog = new MyFriendDialog(BlackUserActivity.this, 2, new MyFriendDialog.OnMyFriendListener() {
                                            @Override
                                            public void onReturn(int type) {
                                                switch (type){
                                                    case 0:
                                                        //彻底删除
                                                        ViseHttp.POST(NetConfig.deleteBlackUserUrl)
                                                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteBlackUserUrl))
                                                                .addParam("id", mList.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        Log.e("22222", data);
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                mList.remove(i);
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
                                                        //移出黑名单
                                                        ViseHttp.POST(NetConfig.userCancelBlackUrl)
                                                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userCancelBlackUrl))
                                                                .addParam("id", mList.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        Log.e("22222", data);
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                mList.remove(i);
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
                                        friendDialog.show();
                                        return true;
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

    @OnClick({R.id.activity_black_user_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_black_user_rl_back:
                finish();
                break;
        }
    }

}
