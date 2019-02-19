package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.DetailsOfFriendsIntercalation1Adapter;
import com.yiwo.friendscometogether.adapter.DetailsOfFriendsIntercalationAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.dbmodel.UserGiveModel;
import com.yiwo.friendscometogether.greendao.gen.UserGiveModelDao;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.model.DetailsRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsOfFriendsActivity extends BaseActivity {

    @BindView(R.id.activity_details_of_friends_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_details_of_friends_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_details_of_friends_ll_intercalation)
    LinearLayout llIntercalation;
    @BindView(R.id.activity_details_of_friends_ll_comment)
    LinearLayout llComment;
    @BindView(R.id.activity_details_of_friends_ll_share)
    LinearLayout llShare;
    @BindView(R.id.activity_details_of_friends_ll_praise)
    LinearLayout llPraise;
    @BindView(R.id.activity_details_of_friends_iv_praise)
    ImageView ivPraise;
    @BindView(R.id.activity_details_of_friends_tv_praise)
    TextView tvPraise;
    @BindView(R.id.activity_details_of_friends_ll_star)
    LinearLayout llStar;
    @BindView(R.id.activity_details_of_friends_iv_star)
    ImageView ivStar;
    @BindView(R.id.activity_details_of_friends_tv_star)
    TextView tvStar;
    @BindView(R.id.activity_details_of_friends_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_details_of_friends_tv_look_num)
    TextView tvLookNum;
    @BindView(R.id.activity_details_of_friends_tv_focus_num)
    TextView tvFocusNum;
    @BindView(R.id.activity_details_of_friends_tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.activity_details_of_friends_tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.activity_details_of_friends_tv_price)
    TextView tvPrice;
    @BindView(R.id.activity_details_of_friends_tv_city)
    TextView tvCity;
    @BindView(R.id.activity_details_of_friends_iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.activity_details_of_friends_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.activity_details_of_friends_tv_level)
    TextView tvLevel;
    @BindView(R.id.activity_details_of_friends_tv_related_activity)
    TextView tvRelatedActive;
    @BindView(R.id.activity_details_of_friends_tv_related_c_time)
    TextView tvRelatedCTime;
    @BindView(R.id.activity_details_of_friends_tv_related_look_num)
    TextView tvRelatedLookNum;
    @BindView(R.id.activity_details_of_friends_tv_related_comment_num)
    TextView tvRelatedCommentNum;
    @BindView(R.id.activity_details_of_friends_iv_activity_title)
    ImageView ivActiveTitle;
    @BindView(R.id.activity_details_of_friends_rv_intercalation)
    RecyclerView recyclerView1;
    @BindView(R.id.activity_details_of_friends_btn_focus)
    Button btnTopFocus;
    @BindView(R.id.activity_details_of_friends_rl_activity)
    RelativeLayout rlActiveContent;
    @BindView(R.id.start)
    LinearLayout llStart;
    @BindView(R.id.end)
    LinearLayout llEnd;
    @BindView(R.id.money)
    LinearLayout llMoney;
    @BindView(R.id.city)
    LinearLayout llCity;
    @BindView(R.id.rl_info_content)
    RelativeLayout rlInfoContent;
    @BindView(R.id.banner)
    Banner banner;

    private DetailsOfFriendsIntercalationAdapter adapter;
    private DetailsOfFriendsIntercalation1Adapter adapter1;
    private DetailsRememberModel model;

    private boolean isFocus = false;
    private boolean isPraise = false;
    private boolean isStar = false;

    private SpImp spImp;
    private String uid = "";
    private String fmID = "";
    private String articleUid = "";

    private boolean start = true;
    private boolean end = true;
    private boolean price = true;
    private boolean place = true;

    UserGiveModelDao userGiveModelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friends);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(DetailsOfFriendsActivity.this);
        userGiveModelDao =  MyApplication.getInstance().getDaoSession().getUserGiveModelDao();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    private void initData() {

        uid = spImp.getUID();

        final Intent intent = getIntent();
        final String fmid = intent.getStringExtra("fmid");

        ViseHttp.POST(NetConfig.detailsOfFriendsUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.detailsOfFriendsUrl))
                .addParam("id", fmid)
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, DetailsRememberModel.class);
                                fmID = model.getObj().getContent().getFmID();
                                articleUid = model.getObj().getContent().getUid();

                                final List<String> list = new ArrayList<>();
                                for (int i = 0; i < model.getObj().getContent().getFmpic().size(); i++) {
                                    list.add(model.getObj().getContent().getFmpic().get(i).getPic());
                                }
                                init(banner, list);
                                banner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        //查看图片
                                        Intent intent1 = new Intent();
                                        intent1.setClass(DetailsOfFriendsActivity.this, ImagePreviewActivity.class);
                                        intent1.putStringArrayListExtra("imageList", (ArrayList<String>) list);
                                        intent1.putExtra(Consts.START_ITEM_POSITION, position);
                                        intent1.putExtra(Consts.START_IAMGE_POSITION, position);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                                        startActivity(intent1);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
                                    }
                                });

                                tvTitle.setText(model.getObj().getContent().getFmtitle());
                                tvLookNum.setText("浏览: " + model.getObj().getContent().getFmlook());
                                tvFocusNum.setText("收藏: " + model.getObj().getContent().getFmfavorite());
                                if (TextUtils.isEmpty(model.getObj().getContent().getFmgotime())) {
                                    llStart.setVisibility(View.GONE);
                                    start = false;
                                } else {
                                    tvStartTime.setText("开始时间: " + model.getObj().getContent().getFmgotime());
                                }
                                if (TextUtils.isEmpty(model.getObj().getContent().getFmendtime())) {
                                    llEnd.setVisibility(View.GONE);
                                    end = false;
                                } else {
                                    tvEndTime.setText("结束时间: " + model.getObj().getContent().getFmendtime());
                                }
                                if (TextUtils.isEmpty(model.getObj().getContent().getPercapitacost()) || model.getObj().getContent().getPercapitacost().equals("0.00")) {
                                    llMoney.setVisibility(View.GONE);
                                    price = false;
                                } else {
                                    tvPrice.setText("参加费用: ¥" + model.getObj().getContent().getPercapitacost());
                                }
                                if (TextUtils.isEmpty(model.getObj().getContent().getFmaddress())) {
                                    llCity.setVisibility(View.GONE);
                                    place = false;
                                } else {
                                    tvCity.setText("活动地点: " + model.getObj().getContent().getFmaddress());
                                }

                                if (!start && !end && !price && !place) {
                                    rlInfoContent.setVisibility(View.GONE);
                                }

                                Picasso.with(DetailsOfFriendsActivity.this).load(model.getObj().getContent().getUserpic()).into(ivAvatar);
                                tvNickname.setText(model.getObj().getContent().getUsername());
                                tvLevel.setText("LV" + model.getObj().getContent().getUsergrade());

                                if (!TextUtils.isEmpty(model.getObj().getActivityInfo().getPfID())) {
                                    tvRelatedActive.setText(model.getObj().getActivityInfo().getPfcontent());
                                    tvRelatedCTime.setText(model.getObj().getActivityInfo().getPftime());
                                    tvRelatedLookNum.setText(model.getObj().getActivityInfo().getPflook());
                                    tvRelatedCommentNum.setText(model.getObj().getActivityInfo().getPfcomment());
                                    Picasso.with(DetailsOfFriendsActivity.this).load(model.getObj().getActivityInfo().getPfpic()).into(ivActiveTitle);
                                } else {
                                    rlActiveContent.setVisibility(View.GONE);
                                }

                                LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendsActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                adapter = new DetailsOfFriendsIntercalationAdapter(model.getObj().getRenew());
                                recyclerView.setAdapter(adapter);
                                LinearLayoutManager manager1 = new LinearLayoutManager(DetailsOfFriendsActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager1.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView1.setLayoutManager(manager1);
                                adapter1 = new DetailsOfFriendsIntercalation1Adapter(model.getObj().getInserList());
                                recyclerView1.setAdapter(adapter1);

                                //点赞
//                                if (model.getObj().getContent().getGive() == 0) {
//                                    isPraise = false;
//                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.details_praise_b).into(ivPraise);
//                                    tvPraise.setTextColor(Color.parseColor("#333333"));
//                                } else {
//                                    isPraise = true;
//                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.praise_y).into(ivPraise);
//                                    tvPraise.setTextColor(Color.parseColor("#FF9D00"));
//                                }
                                if (userGiveModelDao.queryBuilder().where(UserGiveModelDao.Properties.UserId.eq(uid),UserGiveModelDao.Properties.ArticleId.eq(fmID)).build().list().size()<=0) {
                                    isPraise = false;
                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.details_praise_b).into(ivPraise);
                                    tvPraise.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    isPraise = true;
                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.praise_y).into(ivPraise);
                                    tvPraise.setTextColor(Color.parseColor("#FF9D00"));
                                }
                                if (model.getObj().getContent().getCollection() == 0) {
                                    isStar = false;
                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.details_star_b).into(ivStar);
                                    tvStar.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    isStar = true;
                                    Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.star_y).into(ivStar);
                                    tvStar.setTextColor(Color.parseColor("#FF9D00"));
                                }

                                if (model.getObj().getContent().getFollow() == 0) {
                                    isFocus = false;
                                    btnTopFocus.setText("+关注");
                                } else {
                                    isFocus = true;
                                    btnTopFocus.setText("已关注");
                                }

                                if (model.getObj().getContent().getInserTtext().equals("0")) {
                                    //不允许插文
                                    llIntercalation.setVisibility(View.GONE);
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

    @OnClick({R.id.activity_details_of_friends_rl_back, R.id.activity_details_of_friends_ll_intercalation, R.id.activity_details_of_friends_ll_comment,
            R.id.activity_details_of_friends_ll_share, R.id.activity_details_of_friends_ll_praise, R.id.activity_details_of_friends_ll_star,
            R.id.activity_details_of_friends_iv_avatar, R.id.activity_details_of_friends_btn_focus, R.id.activity_details_of_friends_rl_activity})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_details_of_friends_rl_back:
                onBackPressed();
                break;
            case R.id.activity_details_of_friends_ll_intercalation:
                intent.setClass(DetailsOfFriendsActivity.this, InsertIntercalationActivity.class);
                intent.putExtra("id", fmID);
                startActivity(intent);
                break;
            case R.id.activity_details_of_friends_ll_comment:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(DetailsOfFriendsActivity.this, ArticleCommentActivity.class);
                    intent.putExtra("id", fmID);
                    startActivity(intent);
                }
                break;
            case R.id.activity_details_of_friends_ll_share:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ViseHttp.POST(NetConfig.activeShareUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeShareUrl))
                            .addParam("id", fmID)
                            .addParam("type", "1")
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            Gson gson = new Gson();
                                            final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                            new ShareAction(DetailsOfFriendsActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                                    .setShareboardclickCallback(new ShareBoardlistener() {
                                                        @Override
                                                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                            ShareUtils.shareWeb(DetailsOfFriendsActivity.this, shareModel.getObj().getUrl(), shareModel.getObj().getTitle(),
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
                }
                break;
            case R.id.activity_details_of_friends_ll_praise:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isPraise) {
                        Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.praise_y).into(ivPraise);
                        tvPraise.setTextColor(Color.parseColor("#FF9D00"));
                        ViseHttp.POST(NetConfig.articlePraiseUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articlePraiseUrl))
                                .addParam("id", fmID)
                                .addParam("uid", uid)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(DetailsOfFriendsActivity.this, "点赞成功");
                                                UserGiveModel model = new UserGiveModel();
                                                model.setUserId(uid);
                                                model.setArticleId(fmID);
                                                model.setRemarkState("1");
                                                userGiveModelDao.insert(model);
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
                break;
            case R.id.activity_details_of_friends_ll_star:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isStar) {
                        Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.star_y).into(ivStar);
                        tvStar.setTextColor(Color.parseColor("#FF9D00"));
                        isStar = !isStar;
                        ViseHttp.POST(NetConfig.articleCollectionUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCollectionUrl))
                                .addParam("id", fmID)
                                .addParam("uid", uid)
                                .addParam("type", "0")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(DetailsOfFriendsActivity.this, "收藏成功");
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
                        Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.details_star_b).into(ivStar);
                        tvStar.setTextColor(Color.parseColor("#333333"));
                        isStar = !isStar;
                        ViseHttp.POST(NetConfig.articleCollectionUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCollectionUrl))
                                .addParam("id", fmID)
                                .addParam("uid", uid)
                                .addParam("type", "1")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(DetailsOfFriendsActivity.this, "取消收藏成功");
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
                break;
            case R.id.activity_details_of_friends_iv_avatar:
                intent.setClass(DetailsOfFriendsActivity.this, PersonMainActivity.class);
                intent.putExtra("person_id", model.getObj().getContent().getUid());
                startActivity(intent);
                break;
//            case R.id.activity_details_of_friends_ll_top_focus:
//                //添加关注
//                if(!isFocus){
//                    ViseHttp.POST(NetConfig.userFocusUrl)
//                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userFocusUrl))
//                            .addParam("uid", uid)
//                            .addParam("likeId", articleUid)
//                            .request(new ACallback<String>() {
//                                @Override
//                                public void onSuccess(String data) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(data);
//                                        if(jsonObject.getInt("code") == 200){
//                                            toToast(DetailsOfFriendsActivity.this, "关注成功");
//                                            Picasso.with(DetailsOfFriendsActivity.this).load(R.mipmap.focus_on_y).into(ivFocus);
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFail(int errCode, String errMsg) {
//
//                                }
//                            });
//                }
//                break;
            case R.id.activity_details_of_friends_btn_focus:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isFocus) {
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                .addParam("uid", uid)
                                .addParam("likeId", articleUid)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                toToast(DetailsOfFriendsActivity.this, "关注成功");
                                                btnTopFocus.setText("已关注");
                                                isFocus = true;
                                            }else {
                                                toToast(DetailsOfFriendsActivity.this, jsonObject.getString("message"));
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
                break;
            case R.id.activity_details_of_friends_rl_activity:
                //相关活动跳转
                intent.putExtra("pfID", model.getObj().getActivityInfo().getPfID());
                intent.setClass(DetailsOfFriendsActivity.this, DetailsOfFriendTogetherActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailsOfFriendsActivity.this.finish();
    }
}
