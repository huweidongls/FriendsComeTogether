package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MyFriendAdapter;
import com.yiwo.friendscometogether.custom.MyFriendDialog;
import com.yiwo.friendscometogether.model.MyFriendModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhouzhuo.zzletterssidebar.ZzLetterSideBar;
import me.zhouzhuo.zzletterssidebar.interf.OnLetterTouchListener;

public class MyFriendActivity extends AppCompatActivity {

    @BindView(R.id.activity_my_friend_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_friend_rl_black)
    RelativeLayout rlBlack;

    private ListView listView;
    private ZzLetterSideBar sideBar;
    private TextView dialog;
    private MyFriendAdapter adapter;
    private List<MyFriendModel.ObjBean> mDatas;

    private SpImp spImp;
    private String uid = "";
    private String account = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        ButterKnife.bind(MyFriendActivity.this);
        spImp = new SpImp(MyFriendActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        uid = spImp.getUID();
        account = spImp.getYXID();

        sideBar = (ZzLetterSideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.tv_dialog);
        listView = (ListView) findViewById(R.id.list_view);

//        //optional
//        View header = LayoutInflater.from(this).inflate(R.layout.list_item_head, null);
//        listView.addHeaderView(header);
//
//        //optional
//        View footer = LayoutInflater.from(this).inflate(R.layout.list_item_foot, null);
//        tvFoot = (TextView) footer.findViewById(R.id.tv_foot);
//        listView.addFooterView(footer);

        ViseHttp.POST(NetConfig.MyFriendListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.MyFriendListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyFriendModel model = gson.fromJson(data, MyFriendModel.class);
                                //set adapter
                                mDatas = model.getObj();
                                adapter = new MyFriendAdapter(MyFriendActivity.this, mDatas);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        liaotian(mDatas.get(i).getWy_accid());
                                    }
                                });
                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                        MyFriendDialog friendDialog = new MyFriendDialog(MyFriendActivity.this, 1, new MyFriendDialog.OnMyFriendListener() {
                                            @Override
                                            public void onReturn(int type) {
                                                switch (type){
                                                    case 0:
                                                        //删除好友
                                                        ViseHttp.POST(NetConfig.deleteFriendUrl)
                                                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.deleteFriendUrl))
                                                                .addParam("id", mDatas.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                Toast.makeText(MyFriendActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                                                mDatas.remove(i);
                                                                                adapter.updateListView(mDatas);
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
                                                        //拉黑
                                                        ViseHttp.POST(NetConfig.blackFriendUrl)
                                                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.blackFriendUrl))
                                                                .addParam("id", mDatas.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                Toast.makeText(MyFriendActivity.this, "已加入黑名单", Toast.LENGTH_SHORT).show();
                                                                                mDatas.remove(i);
                                                                                adapter.updateListView(mDatas);
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
                                                        Intent intent = new Intent();
                                                        intent.putExtra("uid", mDatas.get(i).getUid());
                                                        intent.setClass(MyFriendActivity.this, OtherInformationActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                }
                                            }
                                        });
                                        friendDialog.show();
                                        return true;
                                    }
                                });

                                //update data
//                                adapter.updateListView(mDatas);
//        tvFoot.setText(mDatas.size() + "位联系人");
                                //设置右侧触摸监听
                                sideBar.setLetterTouchListener(listView, adapter, dialog, new OnLetterTouchListener() {
                                    @Override
                                    public void onLetterTouch(String letter, int position) {
                                    }

                                    @Override
                                    public void onActionUp() {
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

    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(MyFriendActivity.this, liaotianAccount);
    }

    @OnClick({R.id.activity_my_friend_rl_back, R.id.activity_my_friend_rl_black})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_my_friend_rl_back:
                onBackPressed();
                break;
            case R.id.activity_my_friend_rl_black:
                intent.setClass(MyFriendActivity.this, BlackUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyFriendActivity.this.finish();
    }
}
