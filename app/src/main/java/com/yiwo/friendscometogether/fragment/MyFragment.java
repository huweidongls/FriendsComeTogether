package com.yiwo.friendscometogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.UserModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.CreateFriendRememberActivity;
import com.yiwo.friendscometogether.pages.CreateFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.JoinActiveActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.LookHistoryActivity;
import com.yiwo.friendscometogether.pages.MessageCenterActivity;
import com.yiwo.friendscometogether.pages.MyCollectionActivity;
import com.yiwo.friendscometogether.pages.MyCommentActivity;
import com.yiwo.friendscometogether.pages.MyDraftActivity;
import com.yiwo.friendscometogether.pages.MyFocusActiveActivity;
import com.yiwo.friendscometogether.pages.MyFocusActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.pages.MyFriendRememberActivity;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.pages.MyIntercalationActivity;
import com.yiwo.friendscometogether.pages.MyOrderActivity;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.pages.SetActivity;
import com.yiwo.friendscometogether.pages.StartActiveActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/16.
 */

public class MyFragment extends BaseFragment {
    View rootView;

    @BindView(R.id.fragment_my_ll_look_more)
    LinearLayout llLookMore;
//    @BindView(R.id.fragment_my_ll_to_pay)
//    LinearLayout llToPay;
//    @BindView(R.id.fragment_my_ll_to_trip)
//    LinearLayout llToTrip;
//    @BindView(R.id.fragment_my_ll_to_comment)
//    LinearLayout llComment;
//    @BindView(R.id.fragment_my_ll_return_price)
//    LinearLayout llReturnPrice;
    @BindView(R.id.fragment_my_rl_focus)
    RelativeLayout rlFocus;
    @BindView(R.id.fragment_my_rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.fragment_my_rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.fragment_my_rl_history)
    RelativeLayout rlHistory;
//    @BindView(R.id.fragment_my_ll_draft)
//    LinearLayout llDraft;
//    @BindView(R.id.fragment_my_ll_create_friend_remember)
//    LinearLayout llFriendRemember;
    @BindView(R.id.fragment_my_person_set)
    RelativeLayout rlPersonSet;
//    @BindView(R.id.fragment_my_ll_my_friend_remember)
//    LinearLayout llMyFriendRemember;
//    @BindView(R.id.fragment_my_ll_my_intercalation)
//    LinearLayout llMyIntercalation;
    @BindView(R.id.fragment_my_rl_initiating_activities)
    RelativeLayout rlInitiating;
    @BindView(R.id.fragment_my_rl_join_activitys)
    RelativeLayout rlJoin;
    @BindView(R.id.fragment_my_rl_picture)
    RelativeLayout rlPicture;
    @BindView(R.id.fragment_my_tv_not_login)
    TextView tvNotLogin;
    @BindView(R.id.fragment_my_rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.fragment_my_iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.fragment_my_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.fragment_my_iv_sex)
    ImageView ivSex;
    @BindView(R.id.fragment_my_sign_team)
    RelativeLayout rlSignTeam;
    @BindView(R.id.fragment_my_rl_create_activity)
    RelativeLayout rlCreateActivity;
    @BindView(R.id.fragment_my_set)
    RelativeLayout rlMessage;
    @BindView(R.id.fragment_my_rl_my_friend)
    RelativeLayout rlFriend;
    @BindView(R.id.fragment_my_rl_focus_activitys)
    RelativeLayout rlMyFocusActive;
    @BindView(R.id.fragment_my_tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_is_sign)
    ImageView ivIsSign;
    @BindView(R.id.fragment_my_rl_set)
    RelativeLayout rlSet;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my, null);
        ScreenAdapterTools.getInstance().loadView(rootView);

        ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());

        return rootView;
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            onStart();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            onStart();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
        Log.e("222", uid);
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
            tvNotLogin.setVisibility(View.GONE);
            rlContent.setVisibility(View.VISIBLE);
            ViseHttp.POST(NetConfig.userInformation)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userInformation))
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Gson gson = new Gson();
                                    UserModel userModel = gson.fromJson(data, UserModel.class);
                                    if (TextUtils.isEmpty(userModel.getObj().getHeadeimg())) {
                                        Picasso.with(getContext()).load(R.mipmap.my_head).into(ivAvatar);
                                    } else {
                                        Picasso.with(getContext()).load(userModel.getObj().getHeadeimg()).into(ivAvatar);
                                    }
                                    tvNickname.setText("昵称: " + userModel.getObj().getUsername());
                                    if (userModel.getObj().getSex().equals("0")) {
                                        Picasso.with(getContext()).load(R.mipmap.nan).into(ivSex);
                                    } else {
                                        Picasso.with(getContext()).load(R.mipmap.nv).into(ivSex);
                                    }
                                    //等级部分
                                    rlSignTeam.setVisibility(View.VISIBLE);
                                    tvLevel.setText("LV" + userModel.getObj().getUsergrade());
                                    if (userModel.getObj().getSign().equals("0")) {
                                        Glide.with(getContext()).load(R.mipmap.sign_gray).into(ivIsSign);
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.sign_yellow).into(ivIsSign);
                                    }
                                    //是否为vip
                                    if(userModel.getObj().getVip().equals("1")){
                                        rlCreateActivity.setVisibility(View.VISIBLE);
                                        rlInitiating.setVisibility(View.VISIBLE);
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
        } else {
            tvNotLogin.setVisibility(View.VISIBLE);
            rlContent.setVisibility(View.GONE);
            Picasso.with(getContext()).load("null").into(ivAvatar);
        }
    }

    @OnClick({R.id.fragment_my_ll_look_more, R.id.fragment_my_ll_to_pay, R.id.fragment_my_ll_to_trip, R.id.fragment_my_ll_to_comment, R.id.fragment_my_ll_return_price,
            R.id.fragment_my_rl_focus, R.id.fragment_my_rl_collection, R.id.fragment_my_rl_comment, R.id.fragment_my_rl_history, R.id.fragment_my_ll_draft,
            R.id.fragment_my_ll_create_friend_remember, R.id.fragment_my_person_set, R.id.fragment_my_ll_my_friend_remember, R.id.fragment_my_ll_my_intercalation,
            R.id.fragment_my_rl_initiating_activities, R.id.fragment_my_rl_join_activitys, R.id.fragment_my_rl_picture, R.id.fragment_my_rl_create_activity,
            R.id.fragment_my_set, R.id.fragment_my_rl_my_friend, R.id.fragment_my_rl_focus_activitys, R.id.fragment_my_rl_set})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.fragment_my_ll_look_more:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_to_pay:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_to_trip:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_to_comment:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 3);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_return_price:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 4);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_focus:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFocusActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_collection:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyCollectionActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_comment:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyCommentActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_history:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), LookHistoryActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_draft:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyDraftActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_create_friend_remember:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), CreateFriendRememberActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_person_set:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyInformationActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_my_friend_remember:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFriendRememberActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_ll_my_intercalation:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyIntercalationActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_initiating_activities:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), StartActiveActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_join_activitys:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), JoinActiveActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_picture:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyPicturesActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_create_activity:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), CreateFriendTogetherActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_set:
                //跳转设置页面
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MessageCenterActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_my_friend:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFriendActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_focus_activitys:
                //我关注的活动
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFocusActiveActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_my_rl_set:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), SetActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
