package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.UserActiveListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitationActivity extends BaseActivity {

    @BindView(R.id.activity_invitation_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_create_friend_remember_rl_active_title)
    RelativeLayout rlActiveTitle;
    @BindView(R.id.activity_create_friend_remember_tv_active_title)
    TextView tvActiveTitle;
    @BindView(R.id.activity_invitation_rl_ok)
    RelativeLayout rlOk;
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.et)
    EditText et;

    private String[] block;
    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;

    private SpImp spImp;
    private String uid = "";
    private String otherUid = "";

    private int type = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(InvitationActivity.this);
        spImp = new SpImp(InvitationActivity.this);

        initData();

    }

    private void initData() {

        otherUid = getIntent().getStringExtra("otheruid");
        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.activeInvitationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserActiveListModel model = gson.fromJson(data, UserActiveListModel.class);
                                activeList = model.getObj();
                                activeId = new String[model.getObj().size()];
                                activeName = new String[model.getObj().size()];
                                block = new String[model.getObj().size()];
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    activeId[i] = model.getObj().get(i).getPfID();
                                    activeName[i] = model.getObj().get(i).getPftitle();
                                    block[i] = model.getObj().get(i).getBlock();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb1:
                        toToast(InvitationActivity.this, "我请客");
                        type = 0;
                        break;
                    case R.id.rb2:
                        toToast(InvitationActivity.this, "自费");
                        type = 1;
                        break;
                }
            }
        });

    }

    @OnClick({R.id.activity_invitation_rl_back, R.id.activity_create_friend_remember_rl_active_title, R.id.activity_invitation_rl_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_invitation_rl_back:
                finish();
                break;
            case R.id.activity_create_friend_remember_rl_active_title:
                //活动标题
                if (activeList.size() > 0) {
                    AlertDialog.Builder singleChoiceDialog1 =
                            new AlertDialog.Builder(InvitationActivity.this);
                    singleChoiceDialog1.setTitle("请选择活动标题");
                    // 第二个参数是默认选项，此处设置为0
                    singleChoiceDialog1.setSingleChoiceItems(activeName, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    yourChoiceActiveName = activeName[which];
                                    yourChoiceActiveId = activeId[which];
                                    if(block[which].equals("0")){
                                        radioGroup.setVisibility(View.GONE);
                                        type = 3;
                                    }else {
                                        radioGroup.setVisibility(View.VISIBLE);
                                        type = 0;
                                    }
                                }
                            });
                    singleChoiceDialog1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TextUtils.isEmpty(yourChoiceActiveName)) {
                                        tvActiveTitle.setText(activeName[0]);
                                        yourChoiceActiveId = activeId[0];
                                    } else {
                                        tvActiveTitle.setText(yourChoiceActiveName);
                                        yourChoiceActiveName = "";
                                    }
                                }
                            });
                    singleChoiceDialog1.show();
                } else {
                    toToast(InvitationActivity.this, "暂无活动");
                }
                break;
            case R.id.activity_invitation_rl_ok:
                //确定
                ViseHttp.POST(NetConfig.activeInvitationUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationUrl))
                        .addParam("uid", uid)
                        .addParam("bid", otherUid)
                        .addParam("tid", yourChoiceActiveId)
                        .addParam("type", type + "")
                        .addParam("text", et.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(InvitationActivity.this, "邀请成功");
                                        finish();
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
        InvitationActivity.this.finish();
    }
}
