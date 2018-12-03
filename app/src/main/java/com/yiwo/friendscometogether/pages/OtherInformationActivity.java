package com.yiwo.friendscometogether.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.OtherInformationActiveAdapter;
import com.yiwo.friendscometogether.adapter.OtherInformationWorksAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.FriendDescribeDialog;
import com.yiwo.friendscometogether.custom.HgInfoDialog;
import com.yiwo.friendscometogether.model.OtherInformationModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherInformationActivity extends BaseActivity {

    @BindView(R.id.activity_other_information_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_other_information_tv_send_message)
    TextView tvSendMessage;
    @BindView(R.id.activity_other_information_iv_add_friend)
    ImageView ivAddFriend;
    @BindView(R.id.activity_other_information_iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.activity_other_information_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.activity_other_information_tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.activity_other_information_ll_mypics)
    LinearLayout llMyPic;
    @BindView(R.id.activity_other_information_tv_sign)
    TextView tvSign;
    @BindView(R.id.activity_other_information_tv_age)
    TextView tvAge;
    @BindView(R.id.activity_other_information_tv_city)
    TextView tvCity;
    @BindView(R.id.activity_other_information_tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.activity_other_information_tv_focus_num)
    TextView tvFocusNum;
    @BindView(R.id.activity_other_information_tv_fans_num)
    TextView tvFansNum;
    @BindView(R.id.activity_other_information_tv_praise_num)
    TextView tvPraiseNum;
    @BindView(R.id.activity_other_information_tv_works)
    TextView tvWorks;
    @BindView(R.id.activity_other_information_tv_active)
    TextView tvActive;
    @BindView(R.id.activity_other_information_rv_works)
    RecyclerView rvWorks;
    @BindView(R.id.activity_other_information_rv_active)
    RecyclerView rvActive;
    @BindView(R.id.iv_sign_yellow)
    ImageView ivSign;
    @BindView(R.id.activity_other_information_tv_is_single)
    TextView tvIsSingle;

    private String otherUid = "";

    private OtherInformationWorksAdapter worksAdapter;
    private OtherInformationActiveAdapter activeAdapter;
    private List<OtherInformationModel.ObjBean.ListPicNewsBean> data1;
    private List<OtherInformationModel.ObjBean.ListActiviyBean> data2;

    private String account;
    private SpImp spImp;
    private OtherInformationModel model;

    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_information);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(OtherInformationActivity.this);

        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        otherUid = intent.getStringExtra("uid");
        account = spImp.getYXID();
        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.otherUserInformationUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.otherUserInformationUrl))
                .addParam("uid", otherUid)
                .addParam("cid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, OtherInformationModel.class);
                                if (!TextUtils.isEmpty(model.getObj().getInfo().getUserpic())) {
                                    Picasso.with(OtherInformationActivity.this).load(model.getObj().getInfo().getUserpic()).into(ivAvatar);
                                }
                                tvNickname.setText(model.getObj().getInfo().getUsername());
                                tvUserLevel.setText("LV" + model.getObj().getInfo().getUsergrade());
                                tvSign.setText("个性签名: " + model.getObj().getInfo().getUserautograph());
                                tvAge.setText(model.getObj().getInfo().getAge());
                                tvCity.setText(model.getObj().getInfo().getAddress());
                                tvConstellation.setText(model.getObj().getInfo().getConstellation());
                                tvFocusNum.setText(model.getObj().getInfo().getUserlike());
                                tvFansNum.setText(model.getObj().getInfo().getUserbelike());
                                tvPraiseNum.setText(model.getObj().getInfo().getGiveCount() + "");
                                tvIsSingle.setText(model.getObj().getInfo().getUsermarry());

                                if (model.getObj().getInfo().getFriends().equals("1")) {
                                    Glide.with(OtherInformationActivity.this).load(R.mipmap.other_send_msg).into(ivAddFriend);
                                } else if (model.getObj().getInfo().getFriends().equals("0")) {
                                    Glide.with(OtherInformationActivity.this).load(R.mipmap.add_friend).into(ivAddFriend);
                                }

                                if (model.getObj().getInfo().getFollow().equals("1")) {
                                    tvSendMessage.setText("已关注");
                                } else if (model.getObj().getInfo().getFollow().equals("0")) {
                                    tvSendMessage.setText("加关注");
                                }

                                Log.e("123123", model.getObj().getInfo().getLeader());
                                if (model.getObj().getInfo().getLeader().equals("1")) {
                                    ivSign.setVisibility(View.VISIBLE);
                                }

                                LinearLayoutManager manager = new LinearLayoutManager(OtherInformationActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                rvWorks.setLayoutManager(manager);
                                data1 = model.getObj().getListPicNews();
                                worksAdapter = new OtherInformationWorksAdapter(data1);
                                rvWorks.setAdapter(worksAdapter);
                                if (data1.size() == 0) {
                                    rvWorks.setVisibility(View.GONE);
                                }
                                LinearLayoutManager manager1 = new LinearLayoutManager(OtherInformationActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager1.setOrientation(LinearLayoutManager.VERTICAL);
                                rvActive.setLayoutManager(manager1);
                                data2 = model.getObj().getListActiviy();
                                activeAdapter = new OtherInformationActiveAdapter(data2);
                                rvActive.setAdapter(activeAdapter);
                                rvActive.setVisibility(View.GONE);
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

    @OnClick({R.id.activity_other_information_rl_back, R.id.activity_other_information_tv_works, R.id.activity_other_information_tv_active, R.id.activity_other_information_ll_mypics,
            R.id.activity_other_information_tv_send_message, R.id.activity_other_information_iv_add_friend, R.id.iv_sign_yellow})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_other_information_rl_back:
                onBackPressed();
                break;
            case R.id.activity_other_information_tv_works:
                tvWorks.setBackgroundColor(Color.parseColor("#FF9D00"));
                tvActive.setBackgroundColor(Color.parseColor("#000000"));
                if (data1.size() == 0) {
                    rvWorks.setVisibility(View.GONE);
                    rvActive.setVisibility(View.GONE);
                } else {
                    rvWorks.setVisibility(View.VISIBLE);
                    rvActive.setVisibility(View.GONE);
                }
                break;
            case R.id.activity_other_information_tv_active:
                tvWorks.setBackgroundColor(Color.parseColor("#000000"));
                tvActive.setBackgroundColor(Color.parseColor("#FF9D00"));
                if (data2.size() == 0) {
                    rvWorks.setVisibility(View.GONE);
                    rvActive.setVisibility(View.GONE);
                } else {
                    rvWorks.setVisibility(View.GONE);
                    rvActive.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.activity_other_information_ll_mypics:
                intent.putExtra("otheruid", otherUid);
                intent.setClass(OtherInformationActivity.this, OtherPicActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_other_information_tv_send_message:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(OtherInformationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {

                    ViseHttp.POST(NetConfig.userFocusUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                            .addParam("uid", uid)
                            .addParam("likeId", otherUid)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            toToast(OtherInformationActivity.this, "关注成功");
                                            tvSendMessage.setText("已关注");
                                        } else {
                                            toToast(OtherInformationActivity.this, jsonObject.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });

//                    if(model.getObj().getInfo().getFriends().equals("1")){
//                        liaotian(model.getObj().getInfo().getWy_accid());
//                    }else if(model.getObj().getInfo().getFriends().equals("0")){
//                        FriendDescribeDialog dialog = new FriendDescribeDialog(OtherInformationActivity.this);
//                        dialog.show();
//                        dialog.setOnReturnListener(new FriendDescribeDialog.OnReturnListener() {
//                            @Override
//                            public void onReturn(String title) {
//                                ViseHttp.POST(NetConfig.addFriendsUrl)
//                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addFriendsUrl))
//                                        .addParam("uid", uid)
//                                        .addParam("friendId", otherUid)
//                                        .addParam("describe", title)
//                                        .request(new ACallback<String>() {
//                                            @Override
//                                            public void onSuccess(String data) {
//                                                try {
//                                                    JSONObject jsonObject = new JSONObject(data);
//                                                    if (jsonObject.getInt("code") == 200) {
//                                                        toToast(OtherInformationActivity.this, "好友请求已发送");
//                                                    }else {
//                                                        toToast(OtherInformationActivity.this, jsonObject.getString("message"));
//                                                    }
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFail(int errCode, String errMsg) {
//
//                                            }
//                                        });
//                            }
//                        });
//                    }
//                    team();
                }
                break;
            case R.id.activity_other_information_iv_add_friend:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(OtherInformationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (model.getObj().getInfo().getFriends().equals("1")) {
                        liaotian(model.getObj().getInfo().getWy_accid());
                    } else if (model.getObj().getInfo().getFriends().equals("0")) {
                        FriendDescribeDialog dialog = new FriendDescribeDialog(OtherInformationActivity.this);
                        dialog.show();
                        dialog.setOnReturnListener(new FriendDescribeDialog.OnReturnListener() {
                            @Override
                            public void onReturn(String title) {
                                ViseHttp.POST(NetConfig.addFriendsUrl)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addFriendsUrl))
                                        .addParam("uid", uid)
                                        .addParam("friendId", otherUid)
                                        .addParam("describe", title)
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200) {
                                                        toToast(OtherInformationActivity.this, "好友请求已发送");
                                                    } else {
                                                        toToast(OtherInformationActivity.this, jsonObject.getString("message"));
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
                }
                break;
            case R.id.iv_sign_yellow:
                //皇冠提示信息
//                toDialog(OtherInformationActivity.this, "提示", "官网认证指定队长，活动由官网审核", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
                HgInfoDialog infoDialog = new HgInfoDialog(OtherInformationActivity.this);
                infoDialog.show();
                break;
        }
    }

    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(OtherInformationActivity.this, liaotianAccount);
    }

    private void team() {
        String liaotianAccount = "656460193";
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(OtherInformationActivity.this, liaotianAccount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OtherInformationActivity.this.finish();
    }
}
