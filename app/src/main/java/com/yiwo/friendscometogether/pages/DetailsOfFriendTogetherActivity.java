package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.DetailsOfFriendsTogetherAdapter;
import com.yiwo.friendscometogether.adapter.FriendTogetherCommentListAdapter;
import com.yiwo.friendscometogether.adapter.FriendTogetherUpDataAdapter;
import com.yiwo.friendscometogether.adapter.ParticipantsItemAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.model.FocusOnLeaderModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsOfFriendTogetherActivity extends BaseActivity {
    @BindView(R.id.activity_details_of_friends_together_iv_title)
    ImageView titleIv;
    @BindView(R.id.headIv)
    ImageView headIv;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.viewsTv)
    TextView viewsTv;
    @BindView(R.id.levelTv)
    TextView levelTv;
    @BindView(R.id.time_start_tv)
    TextView time_start_tv;
    @BindView(R.id.time_end_tv)
    TextView time_end_tv;
    @BindView(R.id.focus_onTv)
    TextView focus_onTv;
    @BindView(R.id.city_tv)
    TextView city_tv;
    @BindView(R.id.priceTv)
    TextView priceTv;
    @BindView(R.id.participantsTv)
    TextView participantsTv;
    @BindView(R.id.item_levelBg)
    RelativeLayout item_levelBg;
    @BindView(R.id.details_friend_together_rv)
    RecyclerView recyclerViewP;
    @BindView(R.id.details_content_friend_together_rv)
    RecyclerView contentRv;
    @BindView(R.id.details_applyTv)
    TextView details_applyTv;
    @BindView(R.id.activity_details_of_friends_together_ll_share)
    LinearLayout details_shareLl;
    @BindView(R.id.activity_details_of_friends_together_btn_top_focus)
    Button focusOnBtn;
    @BindView(R.id.activity_details_of_friends_together_iv_focus_on)
    ImageView focusOnIv;
    @BindView(R.id.activity_details_of_friends_together_rl_back)
    RelativeLayout activity_details_of_friends_together_rl_back;
    @BindView(R.id.consult_leaderLl)
    LinearLayout llLeader;
    @BindView(R.id.tv_is_marry)
    TextView tvIsMarry;
    @BindView(R.id.tv_age_info)
    TextView tvAgeInfo;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_other_info)
    TextView tvOtherInfo;
    @BindView(R.id.comment_tv)
    TextView tvComment;
    @BindView(R.id.comment_rv)
    RecyclerView rvComment;
    @BindView(R.id.comment_more)
    TextView tvCommentMore;
    @BindView(R.id.username)
    TextView tvUsername;
    @BindView(R.id.iv_sign)
    ImageView ivSign;

    private Unbinder unbinder;
    private ParticipantsItemAdapter adapter;
    private DetailsOfFriendsTogetherAdapter detailsOfFriendsTogetherAdapter;
    SpImp spImp;
    FriendsTogetherDetailsModel model;
    String pfID;
    String leaderID = "";

    private FriendTogetherCommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friend_together);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        spImp = new SpImp(DetailsOfFriendTogetherActivity.this);
        initData();
    }

    public void initData() {
        Intent intent = getIntent();
        pfID = intent.getStringExtra("pfID");
        String userID = spImp.getUID();
        final String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherDetailsUrl);
        ViseHttp.POST(NetConfig.friendsTogetherDetailsUrl)
                .addParam("app_key", token)
                .addParam("pfID", pfID)
                .addParam("userID", userID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                model = new Gson().fromJson(data, FriendsTogetherDetailsModel.class);
                                initView(model.getObj());
                                initCommentList(model.getObj().getComment_list());
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

    /**
     * 加载底部评论列表
     */
    private void initCommentList(List<FriendsTogetherDetailsModel.ObjBean.CommentListBean> data) {

        if (data.size() == 0) {
            tvComment.setVisibility(View.GONE);
            rvComment.setVisibility(View.GONE);
            tvCommentMore.setVisibility(View.GONE);
        } else if (data.size() > 0 && data.size() < 5) {
            LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherActivity.this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvComment.setLayoutManager(manager);
            commentListAdapter = new FriendTogetherCommentListAdapter(data);
            rvComment.setAdapter(commentListAdapter);
            tvCommentMore.setVisibility(View.GONE);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherActivity.this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvComment.setLayoutManager(manager);
            commentListAdapter = new FriendTogetherCommentListAdapter(data);
            rvComment.setAdapter(commentListAdapter);
        }

    }

    public void initView(FriendsTogetherDetailsModel.ObjBean model) {
        if (!StringUtils.isEmpty(model.getCaptain()))
            leaderID = model.getCaptain();
        if (!StringUtils.isEmpty(model.getShow_pic())) {
            Glide.with(DetailsOfFriendTogetherActivity.this).load(model.getShow_pic()).into(titleIv);
        }
        if (model.getIf_sign().equals("1")) {
            Glide.with(DetailsOfFriendTogetherActivity.this).load(R.mipmap.sign_yellow).into(ivSign);
        } else {
            Glide.with(DetailsOfFriendTogetherActivity.this).load(R.mipmap.sign_gray).into(ivSign);
        }
        titleTv.setText(model.getTitle());
        viewsTv.setText("浏览量: " + model.getLook());
        focus_onTv.setText("关注: " + model.getPffavorite());
        tvUsername.setText(model.getCapttain_name());

        levelTv.setText("LV" + model.getLevel());

        if (!model.getCaptain().equals("0")) {
            if (!StringUtils.isEmpty(model.getCapttain_pic())) {
                Picasso.with(DetailsOfFriendTogetherActivity.this).load(model.getCapttain_pic()).into(headIv);
//                levelTv.setText(model.getIf_sign().equals("1") ? "签约领队" : "普通领队");
            }
        }
        Log.i("qwe", model.getAttention());
        focusOnIv.setImageResource(model.getAttention().equals("0") ? R.mipmap.focus_on_empty_y : R.mipmap.focus_on_y);
        time_start_tv.setText("开始时间: " + model.getBegin_time());
        time_end_tv.setText("结束时间: " + model.getEnd_time());
        city_tv.setText("活动地点: " + model.getCity());
        priceTv.setText("参加费用: " + model.getPrice());
        tvIsMarry.setText("要求单身: " + model.getMarry());
        if(model.getAge().equals("无要求")){
            tvAgeInfo.setText("年龄要求: 无要求");
        }else {
            tvAgeInfo.setText("年龄要求: " + model.getAge() + "岁");
        }
        tvSex.setText("性别要求: " + model.getPeoplesex());
        if (TextUtils.isEmpty(model.getPfexplain())) {
            tvOtherInfo.setText("其他要求: 无");
        } else {
            tvOtherInfo.setText("其他要求: " + model.getPfexplain());
        }
//        if(model.getHave_num().equals("0")){
//            participantsTv.setText("*暂无报名信息");
//            recyclerViewP.setVisibility(View.GONE);
//        } else {
        participantsTv.setText("参加人员（" + model.getHave_num() + "/" + model.getPerson_num() + ")");
        initPerson(model.getUser_list());
//        }

        focusOnBtn.setText(model.getAttention_captain().equals("0") ? "+ 关注" : "已关注");
//        if(!StringUtils.isEmpty(leaderID)&&!leaderID.equals("0")){
//            levelTv.setText(model.getIf_sign().equals("0")?"普通领队":"签约领队");
//        } else {
//            levelTv.setText("暂无领队");
//        }

        initList(model.getInfo_list());
    }

    private void initPerson(List<FriendsTogetherDetailsModel.ObjBean.UserListBean> data) {

        LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewP.setLayoutManager(manager);
        adapter = new ParticipantsItemAdapter(data);
        recyclerViewP.setAdapter(adapter);

    }

    private void initList(List<FriendsTogetherDetailsModel.ObjBean.InfoListBean> data) {
        LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        contentRv.setLayoutManager(manager);
        detailsOfFriendsTogetherAdapter = new DetailsOfFriendsTogetherAdapter(data);
        contentRv.setAdapter(detailsOfFriendsTogetherAdapter);
    }

    @OnClick({R.id.details_applyTv, R.id.activity_details_of_friends_together_rl_back, R.id.activity_details_of_friends_together_ll_share,
            R.id.activity_details_of_friends_together_ll_focus_on, R.id.activity_details_of_friends_together_btn_top_focus,
            R.id.headIv, R.id.consult_leaderLl, R.id.comment_more, R.id.activity_details_of_friends_together_iv_title})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_details_of_friends_together_rl_back:
                finish();
                break;
            case R.id.details_applyTv:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent = new Intent(DetailsOfFriendTogetherActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ViseHttp.POST(NetConfig.isRealNameUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.isRealNameUrl))
                            .addParam("userID", spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.i("123321", data);
                                    IsRealNameModel models = new Gson().fromJson(data, IsRealNameModel.class);
                                    if (models.getCode() == 200) {
                                        if (models.getObj().getOk().equals("2")) {
                                            Intent it = new Intent(DetailsOfFriendTogetherActivity.this, ApplyActivity.class);
                                            it.putExtra("if_pay", model.getObj().getIf_pay());
                                            it.putExtra("title", model.getObj().getTitle());
                                            it.putExtra("begin_time", model.getObj().getBegin_time());
                                            it.putExtra("price", model.getObj().getPrice());
                                            it.putExtra("pfID", model.getObj().getPfID());
                                            it.putExtra("name", model.getObj().getTruename());
                                            it.putExtra("sex", model.getObj().getPeoplesex());
                                            it.putExtra("age", model.getObj().getAge());
                                            it.putExtra("pic", model.getObj().getShow_pic());
                                            it.putExtra("issingle", model.getObj().getMarry());
                                            it.putExtra("city", model.getObj().getCity());
                                            it.putExtra("tel", model.getObj().getUser_tel());
                                            startActivity(it);
                                        } else if (models.getObj().getOk().equals("1")) {
                                            toToast(DetailsOfFriendTogetherActivity.this, "请于身份审核通过后报名");
                                        } else {
                                            startActivity(new Intent(DetailsOfFriendTogetherActivity.this, RealNameActivity.class));
                                        }
                                    } else {
                                        toToast(DetailsOfFriendTogetherActivity.this, model.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
                break;
            case R.id.activity_details_of_friends_together_ll_share:

                ViseHttp.POST(NetConfig.activeShareUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeShareUrl))
                        .addParam("id", pfID)
                        .addParam("type", "0")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                        new ShareAction(DetailsOfFriendTogetherActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                                .setShareboardclickCallback(new ShareBoardlistener() {
                                                    @Override
                                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                        ShareUtils.shareWeb(DetailsOfFriendTogetherActivity.this, shareModel.getObj().getUrl(), shareModel.getObj().getTitle(),
                                                                shareModel.getObj().getDesc(), shareModel.getObj().getImages(), share_media);
                                                    }
                                                }).open();
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
            case R.id.activity_details_of_friends_together_ll_focus_on:
                String userID = spImp.getUID();
                if (spImp.getUID().equals("0") || TextUtils.isEmpty(spImp.getUID())) {
                    Intent intent1 = new Intent(DetailsOfFriendTogetherActivity.this, LoginActivity.class);
                    startActivity(intent1);
                } else {
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", userID)
                            .addParam("pfID", pfID)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getInt("code") == 200){
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                if (model.getObj().equals("1")) {
                                                    focusOnIv.setImageResource(R.mipmap.focus_on_y);
                                                    toToast(DetailsOfFriendTogetherActivity.this, "关注成功");
                                                } else {
                                                    focusOnIv.setImageResource(R.mipmap.focus_on_empty_y);
                                                    toToast(DetailsOfFriendTogetherActivity.this, "取消成功");
                                                }
                                            }
                                        }else if(jsonObject.getInt("code") == 400) {
                                            toToast(DetailsOfFriendTogetherActivity.this, jsonObject.getString("message"));
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
                break;
            case R.id.activity_details_of_friends_together_btn_top_focus:
                if (spImp.getUID().equals("0") || TextUtils.isEmpty(spImp.getUID())) {
                    Intent intent1 = new Intent(DetailsOfFriendTogetherActivity.this, LoginActivity.class);
                    startActivity(intent1);
                } else {
                    if (!StringUtils.isEmpty(leaderID) && !leaderID.equals("0")) {
                        ViseHttp.POST(NetConfig.focusOnLeaderUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.focusOnLeaderUrl))
                                .addParam("userID", spImp.getUID())
                                .addParam("attention_userID", leaderID)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                FocusOnLeaderModel model = new Gson().fromJson(data, FocusOnLeaderModel.class);
                                                if (model.getCode() == 200) {
                                                    if (model.getObj().getAttention().equals("0")) {
                                                        focusOnBtn.setText("+ 关注");
                                                    } else {
                                                        focusOnBtn.setText("已关注");
                                                    }
                                                }
                                            }else if(jsonObject.getInt("code") == 400){
                                                toToast(DetailsOfFriendTogetherActivity.this, jsonObject.getString("message"));
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
                        toToast(DetailsOfFriendTogetherActivity.this, "暂无领队");
                    }
                }
                break;
            case R.id.headIv:
//                toToast(this, "这是领队的ID" + model.getObj().getCaptain());
                Intent it = new Intent(this, OtherInformationActivity.class);
                it.putExtra("uid", model.getObj().getCaptain());
                startActivity(it);
                break;
            case R.id.consult_leaderLl:
                String uid = spImp.getUID();
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(DetailsOfFriendTogetherActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    liaotian(model.getObj().getWy_accid());
                }
                break;
            case R.id.comment_more:
                Intent intent1 = new Intent();
                intent1.setClass(DetailsOfFriendTogetherActivity.this, ActiveEvaluationActivity.class);
                intent1.putExtra("pfID", pfID);
                startActivity(intent1);
                break;
            case R.id.activity_details_of_friends_together_iv_title:
                List<String> urlList = new ArrayList<>();
                urlList.add(model.getObj().getShow_pic());
                Intent intent = new Intent(DetailsOfFriendTogetherActivity.this, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, 0);
                intent.putExtra(Consts.START_IAMGE_POSITION, 0);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
                break;
        }
    }

    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(DetailsOfFriendTogetherActivity.this, liaotianAccount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
