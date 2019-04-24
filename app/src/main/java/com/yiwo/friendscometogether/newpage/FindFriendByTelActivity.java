package com.yiwo.friendscometogether.newpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.FriendDescribeDialog;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FindFriendByTelAdapter;
import com.yiwo.friendscometogether.newmodel.FindFriendByTelModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindFriendByTelActivity extends BaseActivity {

    @BindView(R.id.edt_search)
    EditText edt_search;
    @BindView(R.id.rv_person)
    RecyclerView rv_person;
    private SpImp spImp;
    FindFriendByTelAdapter adapter;
    private List<FindFriendByTelModel.ObjBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_by_tel);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(FindFriendByTelActivity.this);
        spImp = new SpImp(FindFriendByTelActivity.this);
        initRv();
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(FindFriendByTelActivity.this);
        rv_person.setLayoutManager(manager);
        adapter = new FindFriendByTelAdapter(list);
        adapter.setAddFriendListenner(new FindFriendByTelAdapter.AddFriendListenner() {
            @Override
            public void addFriendListen(final String userID) {
                FriendDescribeDialog dialog = new FriendDescribeDialog(FindFriendByTelActivity.this);
                dialog.show();
                dialog.setOnReturnListener(new FriendDescribeDialog.OnReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        ViseHttp.POST(NetConfig.addFriendsUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addFriendsUrl))
                                .addParam("uid", spImp.getUID())
                                .addParam("friendId", userID)
                                .addParam("describe", title)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(FindFriendByTelActivity.this, "好友请求已发送");
                                            } else {
                                                toToast(FindFriendByTelActivity.this, jsonObject.getString("message"));
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
                });
            }
        });
        rv_person.setAdapter(adapter);
    }

    @OnClick({R.id.rl_back,R.id.tv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                    finish();
                break;
            case R.id.tv_search:
                refresh();
                break;
        }
    }

    private void refresh() {
        ViseHttp.POST(NetConfig.searchUser)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.searchUser))
                .addParam("userLogin", edt_search.getText().toString())
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                FindFriendByTelModel model = gson.fromJson(data,FindFriendByTelModel.class);
                                list.clear();
                                list.addAll(model.getObj());
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
    }
}
