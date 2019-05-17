package com.yiwo.friendscometogether.webpages;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.dbmodel.LookHistoryDbModel;
import com.yiwo.friendscometogether.dbmodel.UserGiveModel;
import com.yiwo.friendscometogether.emoji.EmotionMainFragment;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.LookHistoryDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.UserGiveModelDao;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MuLuItemYouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiWebModel;
import com.yiwo.friendscometogether.newpage.FindFriendByTelActivity;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.pages.BlackUserActivity;
import com.yiwo.friendscometogether.pages.InsertIntercalationActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsOfFriendsWebActivity1 extends BaseWebActivity {

    @BindView(R.id.activity_details_of_friends_iv_praise)
    ImageView ivPraise;
//    @BindView(R.id.activity_details_of_friends_tv_praise)
//    TextView tvPraise;
    @BindView(R.id.activity_details_of_friends_iv_star)
    ImageView ivStar;
//    @BindView(R.id.activity_details_of_friends_tv_star)
//    TextView tvStar;
    @BindView(R.id.activity_details_of_friends_ll_intercalation)
    LinearLayout llIntercalation;
    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.rl_show_more)
    RelativeLayout rlShowMore;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.comment_et_comment)
    EditText comment_et_comment;
    @BindView(R.id.ll_xiaolian)
    LinearLayout ll_xiaolian;
    @BindView(R.id.comment_tv_comment)
    TextView tvComment;
    @BindView(R.id.iv_biaoqing)
    ImageView ivBiaoqing;
    private String fmID;
    private String uid;
    private SpImp spImp;
    private String url;
    private boolean isFocus = false;
    private boolean isPraise = false;
    private boolean isStar = false;

    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    UserGiveModelDao userGiveModelDao;
    LookHistoryDbModelDao lookHistoryDbModelDao;

    private YouJiWebModel model;
    private YouJiWebModel.ObjBean.TitleBean[] arrMulu;
    private List<YouJiWebModel.ObjBean.TitleBean> listMuLu = new ArrayList<>();
    private String[] arrMuLuTitle;
    private PopupWindow popupWindow;
    private MuLuItemYouJiAdapter muLuItemAdapter;

    private EmotionMainFragment emotionMainFragment;


    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friends_web_1);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        fmID= getIntent().getStringExtra("fmid");
        spImp = new SpImp(DetailsOfFriendsWebActivity1.this);
        uid = spImp.getUID();
        setDatabase();
        userGiveModelDao =  mDaoSession.getUserGiveModelDao();
        lookHistoryDbModelDao = mDaoSession.getLookHistoryDbModelDao();
        url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+fmID+"&uid="+uid;
        Log.d("aaaa",url);
//        url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+fmID;
        initWebView(webView,url);
        webView.addJavascriptInterface(new AndroidInterface(),"android");//交互
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rl_bottom.setVisibility(View.VISIBLE);
                ll_comment.setVisibility(View.GONE);
                emotionMainFragment.hideEmotionKeyboard();
                return false;
            }
        });
        initData();
        initEmotionMainFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT,false);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN,true);
        //替换fragment
        //创建修改实例
        emotionMainFragment =EmotionMainFragment.newInstance(EmotionMainFragment.class,bundle);
        emotionMainFragment.bindToContentView(comment_et_comment, ll_xiaolian);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }
    private void toComment() {
        ViseHttp.POST(NetConfig.articleCommentUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentUrl))
                .addParam("id", fmID)
                .addParam("fctitle", comment_et_comment.getText().toString())
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                toToast(DetailsOfFriendsWebActivity1.this, "评论成功");
                                emotionMainFragment.hideEmotionKeyboard();
                                comment_et_comment.setText(null);
                                ll_comment.setVisibility(View.GONE);
                                rl_bottom.setVisibility(View.VISIBLE);
                                webView.loadUrl("javascript:jumpPage()");
//                                    reload();////////////////////
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        comment_et_comment.setText(null);
                    }
                });
    }
    private void initData() {
        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.getInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getInfo))
                .addParam("id",fmID)
                .addParam("uid",uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code")==200){
                                Gson gson = new Gson();
                                model = gson.fromJson(data,YouJiWebModel.class);
//                                // 查询处理数据库（浏览历史）------------------------------------
//                                LookHistoryDbModel historyModel = new LookHistoryDbModel();
//                                historyModel.setLook_time(new Date().toLocaleString());
//                                historyModel.setUser_id(spImp.getUID());
//                                historyModel.setType("1");
//                                historyModel.setLook_id(fmID);
//                                historyModel.setTitle(model.getObj().getShare_info());
//                                historyModel.setPic_url(model.getObj().getShare_pic());
//                                lookHistoryDbModelDao.insertOrReplace(historyModel);
                                if (lookHistoryDbModelDao.queryBuilder()
                                        .where(LookHistoryDbModelDao.Properties.Type.eq("1"),
                                                LookHistoryDbModelDao.Properties.Look_id.eq(fmID),
                                                LookHistoryDbModelDao.Properties.User_id.eq(spImp.getUID())).build()
                                        .list().size()>0){
                                    LookHistoryDbModel model = lookHistoryDbModelDao.queryBuilder()
                                            .where(LookHistoryDbModelDao.Properties.Type.eq("1"),
                                                    LookHistoryDbModelDao.Properties.Look_id.eq(fmID),
                                                    LookHistoryDbModelDao.Properties.User_id.eq(spImp.getUID())).build()
                                            .list().get(0);
                                    model.setLook_time(new Date().toLocaleString());
                                    lookHistoryDbModelDao.update(model);
                                }else {
                                    LookHistoryDbModel historyModel = new LookHistoryDbModel();
                                    historyModel.setLook_time(new Date().toLocaleString());
                                    historyModel.setUser_id(spImp.getUID());
                                    historyModel.setType("1");
                                    historyModel.setLook_id(fmID);
                                    historyModel.setTitle(model.getObj().getShare_info());
                                    historyModel.setPic_url(model.getObj().getShare_pic());
                                    lookHistoryDbModelDao.insert(historyModel);
                                }
//                              ---------------------------------------------------------------
                                listMuLu.clear();
                                listMuLu.addAll(model.getObj().getTitle());
                                arrMulu = new YouJiWebModel.ObjBean.TitleBean[model.getObj().getTitle().size()];
                                arrMuLuTitle = new String[model.getObj().getTitle().size()];
                                for (int i = 0;i<model.getObj().getTitle().size();i++){
                                    arrMulu[i] = model.getObj().getTitle().get(i);
                                    arrMuLuTitle[i] = model.getObj().getTitle().get(i).getFftitle();
                                }
                                //底部按钮
                                //点赞
                                if (userGiveModelDao.queryBuilder()
                                        .where(UserGiveModelDao.Properties.UserId.eq(uid),
                                              UserGiveModelDao.Properties.ArticleId.eq(fmID))
                                        .build().list().size()<=0) {
                                    isPraise = false;
                                    Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_zan_gray).into(ivPraise);
//                                    tvPraise.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    isPraise = true;
                                    Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_zan_red).into(ivPraise);
//                                    tvPraise.setTextColor(Color.parseColor("#d84c37"));
                                }
                                //收藏
                                if (model.getObj().getCollect().equals("0")) {
                                    isStar = false;
                                    Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_star_gray).into(ivStar);
//                                    tvStar.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    isStar = true;
                                    Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_star_red).into(ivStar);
//                                    tvStar.setTextColor(Color.parseColor("#d84c37"));
                                }
                                //插文
                                if (model.getObj().getAdd().equals("1")) {
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
    public void setDatabase(){
//        通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//    可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//    注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//    所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        此处sport-db表示数据库名称 可以任意填写
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        Log.d("SessionmDaoSession11",mDaoSession.toString());
        Log.d("SessionmDaoSession11",mDaoSession.getUserGiveModelDao().toString());
    }
    @OnClick({R.id.activity_details_of_friends_rl_back,R.id.activity_details_of_friends_ll_comment,
            R.id.activity_details_of_friends_ll_share,R.id.activity_details_of_friends_ll_praise,
            R.id.activity_details_of_friends_ll_star,R.id.rl_show_more,R.id.activity_details_of_friends_ll_intercalation,
            R.id.btn_pinglun,R.id.comment_tv_comment})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_show_more:
                showMore(rlShowMore);
                break;
            case R.id.activity_details_of_friends_rl_back:
                onBackPressed();
                break;
            case R.id.btn_pinglun:
                rl_bottom.setVisibility(View.GONE);
                ll_comment.setVisibility(View.VISIBLE);
                showKeyboard(comment_et_comment);
                break;
            case R.id.comment_tv_comment:
                if (TextUtils.isEmpty(comment_et_comment.getText().toString())) {
                    toToast(DetailsOfFriendsWebActivity1.this, "请输入评论内容");
                } else {
                    toComment();
                }
                break;
            case R.id.activity_details_of_friends_ll_intercalation://插文
                intent.setClass(DetailsOfFriendsWebActivity1.this, InsertIntercalationActivity.class);
                intent.putExtra("id", fmID);
                startActivity(intent);
                break;
            case R.id.activity_details_of_friends_ll_comment://评论
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(DetailsOfFriendsWebActivity1.this, ArticleCommentActivity.class);
                    intent.putExtra("id", fmID);
                    startActivity(intent);
                }
                break;
            case R.id.activity_details_of_friends_ll_share://分享
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
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
                                    Log.d("asdasd",data);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            Gson gson = new Gson();
                                            final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                            new ShareAction(DetailsOfFriendsWebActivity1.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                                    .setShareboardclickCallback(new ShareBoardlistener() {
                                                        @Override
                                                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                            ShareUtils.shareWeb(DetailsOfFriendsWebActivity1.this, shareModel.getObj().getUrl(), model.getObj().getWho()+"的友记",
                                                                    shareModel.getObj().getTitle(), shareModel.getObj().getImages(), share_media);
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
            case R.id.activity_details_of_friends_ll_praise://点赞
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isPraise) {
                        dialog = WeiboDialogUtils.createLoadingDialog(DetailsOfFriendsWebActivity1.this,"");
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
                                                Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_zan_red).into(ivPraise);
//                                                tvPraise.setTextColor(Color.parseColor("#d84c37"));
                                                UserGiveModel model = new UserGiveModel();
                                                model.setUserId(uid);
                                                model.setArticleId(fmID);
                                                model.setRemarkState("1");
                                                userGiveModelDao.insert(model);
                                                isPraise = true;
                                                toToast(DetailsOfFriendsWebActivity1.this, "点赞成功");
                                                WeiboDialogUtils.closeDialog(dialog);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            toToast(DetailsOfFriendsWebActivity1.this, "点赞失败");
                                            WeiboDialogUtils.closeDialog(dialog);
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        toToast(DetailsOfFriendsWebActivity1.this, "点赞失败");
                                        WeiboDialogUtils.closeDialog(dialog);
                                    }
                                });
                    }else {
                        dialog = WeiboDialogUtils.createLoadingDialog(DetailsOfFriendsWebActivity1.this,"");
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
                                                Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_zan_gray).into(ivPraise);
//                                                tvPraise.setTextColor(Color.parseColor("#333333"));
                                                if (userGiveModelDao.queryBuilder()
                                                        .where(UserGiveModelDao.Properties.UserId.eq(uid),
                                                                UserGiveModelDao.Properties.ArticleId.eq(fmID))
                                                        .build().list().size()>0){
                                                    UserGiveModel model = userGiveModelDao.queryBuilder().where(UserGiveModelDao.Properties.UserId.eq(uid),UserGiveModelDao.Properties.ArticleId.eq(fmID)).build().list().get(0);
                                                    userGiveModelDao.deleteByKey(model.getId());
                                                    Log.d("asdsadas","进来了");
                                                }
                                                isPraise = false;
                                                toToast(DetailsOfFriendsWebActivity1.this, "已取消");
                                                WeiboDialogUtils.closeDialog(dialog);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            WeiboDialogUtils.closeDialog(dialog);
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        WeiboDialogUtils.closeDialog(dialog);
                                    }
                                });
                    }
                }
                break;
            case R.id.activity_details_of_friends_ll_star://收藏
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isStar) {
                        Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_star_red).into(ivStar);
//                        tvStar.setTextColor(Color.parseColor("#d84c37"));
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
                                                toToast(DetailsOfFriendsWebActivity1.this, "收藏成功");
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
                        Glide.with(DetailsOfFriendsWebActivity1.this).load(R.mipmap.friend_web_star_gray).into(ivStar);
//                        tvStar.setTextColor(Color.parseColor("#333333"));
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
                                                toToast(DetailsOfFriendsWebActivity1.this, "取消收藏成功");
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
        }
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void lookpostpicture(String json,String index){
//            [{"id":"958","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad155365614111.jpg","desc":""},
//              {"id":"959","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141238.jpg","desc":""},
//              {"id":"960","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141899.jpg","desc":""}]
            String strJson = new String(Base64.decode(json,Base64.DEFAULT));
            Log.d("交互了","json::"+json+"////\r\nindex::"+index);
            int position = Integer.parseInt(index);
            Log.d("交互解码了","json::"+strJson+"////\r\nindex::"+index);
            List<String> listPics = new ArrayList<>();
            List<String> listContent = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listPics.add(jsonObject.getString("imgurl"));
                    listContent.add(jsonObject.getString("desc"));
                }
                Intent intent1 = new Intent();
                intent1.setClass(DetailsOfFriendsWebActivity1.this, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
//                isHasImageContent = getIntent().getBooleanExtra("hasImageContent",false);
//                if (isHasImageContent){
//                    contentList = getIntent().getStringArrayListExtra("imageContenList");
//                }
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject();

        }
        @JavascriptInterface
        public void userinfo(String uid){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendsWebActivity1.this, PersonMainActivity.class);
            intent.putExtra("person_id", uid);
            startActivity(intent);
        }
        @JavascriptInterface
        public void aboutactivity(String pfID){//相关活动
            //相关活动跳转
            Intent intent = new Intent();
            intent.putExtra("pfID", pfID);
            intent.setClass(DetailsOfFriendsWebActivity1.this, DetailsOfFriendTogetherWebActivity.class);
            startActivity(intent);
        }
        @JavascriptInterface
        public void pinglun(){//评论跳转
            Intent intent = new Intent();
            if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent.setClass(DetailsOfFriendsWebActivity1.this, ArticleCommentActivity.class);
                intent.putExtra("id", fmID);
                startActivity(intent);
            }
        }
        @JavascriptInterface
        public void reportuser(String uId,String pId){//举报  评论人 的ID，评论ID
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendsWebActivity1.this, JuBaoActivity.class);
            intent.putExtra("pfID",pId);
            intent.putExtra("reportUserID",uId);
            intent.putExtra("type","3");
            startActivity(intent);
        }
        @JavascriptInterface
        public void jumpyouji(String fmID){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendsWebActivity1.this, DetailsOfFriendsWebActivity1.class);
            intent.putExtra("fmid", fmID);
            startActivity(intent);
        }
    }
    private void showMuLuPopupwindow(View p_view) {

        View view = LayoutInflater.from(DetailsOfFriendsWebActivity1.this).inflate(R.layout.popupwindow_web_mulu, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView recyclerView = view.findViewById(R.id.rv_mulu);
        LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendsWebActivity1.this);
        recyclerView.setLayoutManager(manager);
        muLuItemAdapter= new MuLuItemYouJiAdapter(listMuLu);
        muLuItemAdapter.setListener(new MuLuItemYouJiAdapter.ChooseListen() {
            @Override
            public void chooseItemId(String ID) {
                webView.loadUrl("javascript:jumptitle('"+ID+"')");
                popupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(muLuItemAdapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(p_view,0,0);
//        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.popwindow_anim_left_in_out);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1f;
        getWindow().setAttributes(params);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
    //弹出软键盘
    public void showKeyboard(EditText editText) {
        //其中editText为dialog中的输入框的 EditText
        if (editText != null) {
            emotionMainFragment.hideEmotionKeyboard();
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }
    private void showMore(final View view_p) {

        View view = LayoutInflater.from(DetailsOfFriendsWebActivity1.this).inflate(R.layout.popupwindow_detail_of_friend_web_activity_show_more, null);
        final PopupWindow popupWindow;
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        LinearLayout ll_show_more = view.findViewById(R.id.ll_show_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_show_more.getLayoutParams();
        LinearLayout llMuLu = view.findViewById(R.id.ll_mulu);

        if (arrMulu.length>0){
            llMuLu.setVisibility(View.VISIBLE);
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }else {
            llMuLu.setVisibility(View.GONE);
            params.height = 180;
        }
        ll_show_more.setLayoutParams(params);
        ScreenAdapterTools.getInstance().loadView(view);//确定后dp
        llMuLu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showMuLuPopupwindow(view_p);
            }
        });
        LinearLayout ll_share = view.findViewById(R.id.ll_share);
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(DetailsOfFriendsWebActivity1.this, LoginActivity.class);
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
                                    Log.d("asdasd",data);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            Gson gson = new Gson();
                                            final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                            new ShareAction(DetailsOfFriendsWebActivity1.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                                    .setShareboardclickCallback(new ShareBoardlistener() {
                                                        @Override
                                                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                            ShareUtils.shareWeb(DetailsOfFriendsWebActivity1.this, shareModel.getObj().getUrl(), model.getObj().getWho()+"的友记",
                                                                    shareModel.getObj().getTitle(), shareModel.getObj().getImages(), share_media);
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
            }
        });
        LinearLayout llJuBao = view.findViewById(R.id.ll_jubao);
        llJuBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(DetailsOfFriendsWebActivity1.this, JuBaoActivity.class);
                intent.putExtra("pfID",fmID);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(view_p,0,0);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });


    }
}
