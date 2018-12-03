package com.yiwo.friendscometogether.pages;

import android.content.DialogInterface;
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
import com.yiwo.friendscometogether.adapter.TeamIntercalationAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.EditTitleDialog;
import com.yiwo.friendscometogether.custom.ShieldReasonDialog;
import com.yiwo.friendscometogether.model.TeamIntercalationModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamIntercalationActivity extends BaseActivity {

    @BindView(R.id.activity_team_intercalation_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_team_intercalation_rv)
    RecyclerView recyclerView;

    private TeamIntercalationAdapter adapter;
    private List<TeamIntercalationModel.ObjBean> mList;

    private String id = "";

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_intercalation);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(TeamIntercalationActivity.this);

        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        uid = spImp.getUID();

        LinearLayoutManager manager = new LinearLayoutManager(TeamIntercalationActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.teamIntercalationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.teamIntercalationListUrl))
                .addParam("formId", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                TeamIntercalationModel model = gson.fromJson(data, TeamIntercalationModel.class);
                                mList = model.getObj();
                                adapter = new TeamIntercalationAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new TeamIntercalationAdapter.OnItemClickListener() {
                                    @Override
                                    public void onClick(int type, final int position) {
                                        switch (type){
                                            case 1:
                                                toDialog(TeamIntercalationActivity.this, "提示", "是否允许展示", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Log.e("222", mList.get(position).getFfpID()+"");
                                                        ViseHttp.POST(NetConfig.intercalationShowUrl)
                                                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.intercalationShowUrl))
                                                                .addParam("id", mList.get(position).getFfpID())
                                                                .addParam("type", "0")
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        Log.e("222", data);
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                toToast(TeamIntercalationActivity.this, "已展示");
                                                                            }else {
                                                                                toToast(TeamIntercalationActivity.this, jsonObject1.getString("message"));
                                                                            }
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFail(int errCode, String errMsg) {
                                                                        toToast(TeamIntercalationActivity.this, "请检查网络");
                                                                    }
                                                                });
                                                    }
                                                }, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                                break;
                                            case 2:
                                                final ShieldReasonDialog dialog = new ShieldReasonDialog(TeamIntercalationActivity.this);
                                                dialog.show();
                                                dialog.setOnReturnListener(new EditTitleDialog.OnReturnListener() {
                                                    @Override
                                                    public void onReturn(String title) {
                                                        ViseHttp.POST(NetConfig.sheildIntercalationUrl)
                                                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.sheildIntercalationUrl))
                                                                .addParam("id", mList.get(position).getFfpID())
                                                                .addParam("content", title)
                                                                .addParam("uid", uid)
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        Log.e("222", data);
                                                                        try {
                                                                            JSONObject jsonObject2 = new JSONObject(data);
                                                                            if(jsonObject2.getInt("code") == 200){
                                                                                toToast(TeamIntercalationActivity.this, "屏蔽成功");
                                                                                dialog.dismiss();
                                                                            }
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFail(int errCode, String errMsg) {
                                                                        toToast(TeamIntercalationActivity.this, "请检查网络");
                                                                    }
                                                                });
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

    @OnClick({R.id.activity_team_intercalation_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_team_intercalation_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TeamIntercalationActivity.this.finish();
    }
}
