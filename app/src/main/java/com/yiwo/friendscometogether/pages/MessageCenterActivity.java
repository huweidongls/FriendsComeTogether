package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.HomeMessageCenterModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity {

    @BindView(R.id.activity_message_center_rl_back)
    RelativeLayout backRl;
    @BindView(R.id.contentTv)
    TextView tvHotContent;
    @BindView(R.id.timeTv)
    TextView tvHotTime;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    @BindView(R.id.set_contentTv)
    TextView tvSystemContent;
    @BindView(R.id.set_timeTv)
    TextView tvSystemTime;
    @BindView(R.id.iv_system)
    ImageView ivSystem;
    @BindView(R.id.invitation_contentTv)
    TextView tvInvitationContent;
    @BindView(R.id.invitation_timeTv)
    TextView tvInvitationTime;
    @BindView(R.id.iv_invitation)
    ImageView ivInvitation;
    @BindView(R.id.comment_contentTv)
    TextView tvCommentContent;
    @BindView(R.id.comment_timeTv)
    TextView tvCommentTime;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.friend_contentTv)
    TextView tvFriendContent;
    @BindView(R.id.friend_timeTv)
    TextView tvFriendTime;
    @BindView(R.id.iv_friend)
    ImageView ivFriend;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;

    private SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(MessageCenterActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        ViseHttp.POST(NetConfig.homeMessageCenterUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homeMessageCenterUrl))
                .addParam("user_id", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("22222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomeMessageCenterModel model = gson.fromJson(data, HomeMessageCenterModel.class);
                                tvHotContent.setText(model.getObj().getHot().getMessage());
                                tvHotTime.setText(model.getObj().getHot().getTime());
                                if (model.getObj().getHot().getType().equals("1")) {
                                    ivHot.setVisibility(View.VISIBLE);
                                }
                                tvSystemContent.setText(model.getObj().getSystem().getMessage());
                                tvSystemTime.setText(model.getObj().getSystem().getTime());
                                if (model.getObj().getSystem().getType().equals("1")) {
                                    ivSystem.setVisibility(View.VISIBLE);
                                }
                                tvInvitationContent.setText(model.getObj().getYq().getMessage());
                                tvInvitationTime.setText(model.getObj().getYq().getTime());
                                if (model.getObj().getYq().getType().equals("1")) {
                                    ivInvitation.setVisibility(View.VISIBLE);
                                }
                                tvCommentContent.setText(model.getObj().getComment().getMessage());
                                tvCommentTime.setText(model.getObj().getComment().getTime());
                                if (model.getObj().getComment().getType().equals("1")) {
                                    ivComment.setVisibility(View.VISIBLE);
                                }
                                tvFriendContent.setText(model.getObj().getFriends().getMessage());
                                tvFriendTime.setText(model.getObj().getFriends().getTime());
                                if (model.getObj().getFriends().getType().equals("1")) {
                                    ivFriend.setVisibility(View.VISIBLE);
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

    }

    @OnClick({R.id.hot_message_rl, R.id.set_message_rl, R.id.activity_message_center_rl_back, R.id.invitation_message_rl, R.id.comment_message_rl, R.id.friend_message_rl,
            R.id.rl_clean})
    public void OnClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.activity_message_center_rl_back:
                finish();
                break;
            case R.id.hot_message_rl:
                ivHot.setVisibility(View.GONE);
                intent.setClass(MessageCenterActivity.this, MessageViewActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.set_message_rl:
                ivSystem.setVisibility(View.GONE);
                intent.setClass(MessageCenterActivity.this, MessageViewActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.invitation_message_rl:
                ivInvitation.setVisibility(View.GONE);
                intent.setClass(MessageCenterActivity.this, MessageInvitationActivity.class);
                startActivity(intent);
                break;
            case R.id.comment_message_rl:
                ivComment.setVisibility(View.GONE);
                intent.setClass(MessageCenterActivity.this, MessageCommentActivity.class);
                startActivity(intent);
                break;
            case R.id.friend_message_rl:
                ivFriend.setVisibility(View.GONE);
                intent.setClass(MessageCenterActivity.this, MessageFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clean:
                ViseHttp.POST(NetConfig.deleteMessageUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.deleteMessageUrl))
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "5")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(MessageCenterActivity.this, "已清空");
                                        onStart();
                                        ivHot.setVisibility(View.GONE);
                                        ivSystem.setVisibility(View.GONE);
                                        ivInvitation.setVisibility(View.GONE);
                                        ivComment.setVisibility(View.GONE);
                                        ivFriend.setVisibility(View.GONE);
                                    } else {
                                        toToast(MessageCenterActivity.this, jsonObject.getString("message"));
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

}
