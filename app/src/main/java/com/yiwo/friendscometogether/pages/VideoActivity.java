package com.yiwo.friendscometogether.pages;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.ActivityVideoAdapter;
import com.yiwo.friendscometogether.adapter.ArticleCommentAdapter;
import com.yiwo.friendscometogether.adapter.ArticleCommentVideoAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.emoji.EmotionMainFragment;
import com.yiwo.friendscometogether.model.ActicleCommentVideoModel;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.model.VideoActiveModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.SoftKeyBoardListener;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends FragmentActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.player)
    IjkVideoView ijkVideoView;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.rl_active)
    RelativeLayout rlActive;

    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.tv_zan_num)
    TextView tv_zan_num;
    @BindView(R.id.tv_pinglun_num)
    TextView tv_pinglun_num;

    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.iv_biaoqing)
    ImageView ivBiaoqing;
    @BindView(R.id.ll_edt)
    LinearLayout ll_edt;
    @BindView(R.id.ll_pinglun)
    LinearLayout ll_pinglun;
    @BindView(R.id.activity_video_rv)
    RecyclerView rv_pinglun;
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;
    private PopupWindow popupWindow;
    private String vid;


    private SpImp spImp;
    private String uid = "";

    private boolean isComment = true;//评论  还是回复  true：评论；false 回复
    private String vcID = "";//当前欲回复评论的ID
    private int vPostion;//当前欲回复评论的位置
    private String vPingLunPageVID;//分页ID
    public YouJiListModel.ObjBean mode;//视频mode
    private EmotionMainFragment emotionMainFragment;
    private Dialog dialog;

    //评论列表
    private ArticleCommentVideoAdapter articleCommentVideoAdapter;
    private List<ActicleCommentVideoModel.ObjBean> data_pinglun = new ArrayList<>();
    private int pinglun_page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        spImp = new SpImp(VideoActivity.this);
        uid = spImp.getUID();
        initData();
        initPingLun();
        initEmotionMainFragment(etComment,ivBiaoqing);
    }

    private void initPingLun() {

        LinearLayoutManager manager = new LinearLayoutManager(VideoActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pinglun.setLayoutManager(manager);
        articleCommentVideoAdapter = new ArticleCommentVideoAdapter(data_pinglun);
        articleCommentVideoAdapter.setOnReplyListener(new ArticleCommentVideoAdapter.OnReplyListener() {
            @Override
            public void onReply(int position, String id) {
                vcID = id;
                vPostion = position;
                showKeyboard(etComment);
            }
        });
        rv_pinglun.setAdapter(articleCommentVideoAdapter);
        ViseHttp.POST(NetConfig.videoReviewsList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                .addParam("vID",vid)
                .addParam("page","")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                data_pinglun.clear();
                                data_pinglun.addAll(model.getObj());
                                articleCommentVideoAdapter.notifyDataSetChanged();
                                if (data_pinglun.size()>0){
                                    vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                }else {
                                    vPingLunPageVID = "";
                                }
                                pinglun_page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        refresh_layout.setRefreshHeader(new ClassicsHeader(VideoActivity.this));
        refresh_layout.setRefreshFooter(new ClassicsFooter(VideoActivity.this));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.videoReviewsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                        .addParam("vID",vid)
                        .addParam("page","")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                        data_pinglun.clear();
                                        data_pinglun.addAll(model.getObj());
                                        articleCommentVideoAdapter.notifyDataSetChanged();
                                        if (data_pinglun.size()>0){
                                            vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                        }else {
                                            vPingLunPageVID = "";
                                        }
                                        pinglun_page = 2;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refresh_layout.finishRefresh(1000);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refresh_layout.finishRefresh(1000);
                            }
                        });
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.videoReviewsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviewsList))
                        .addParam("vID",vid)
                        .addParam("page",vPingLunPageVID+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        ActicleCommentVideoModel model = gson.fromJson(data,ActicleCommentVideoModel.class);
                                        data_pinglun.addAll(model.getObj());
                                        articleCommentVideoAdapter.notifyDataSetChanged();
                                        if (data_pinglun.size()>0){
                                            vPingLunPageVID = data_pinglun.get(data_pinglun.size()-1).getVcID();
                                        }else {
                                            vPingLunPageVID = "";
                                        }
                                        pinglun_page++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refresh_layout.finishLoadMore(1000);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refresh_layout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }

    private void initData() {
        Log.d("asasas",getIntent().getSerializableExtra("data").hashCode()+"");
        mode = (YouJiListModel.ObjBean) getIntent().getSerializableExtra("data");
        Log.d("asasas",mode.hashCode()+"");
        String url = mode.getVurl();
        String title = mode.getFmtitle();
        vid = mode.getFmID();
        Log.d("vidvidvid",vid);
        ViseHttp.POST(NetConfig.videoNumInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoNumInfo))
                .addParam("vID",vid)
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Log.d("codecode",data);
                                String status = jsonObject.getJSONObject("obj").getString("status");
                                String zan_num = jsonObject.getJSONObject("obj").getString("praise_num");
                                String pinglun_num = jsonObject.getJSONObject("obj").getString("comment_num");
                                iv_zan.setImageResource(status.equals("0")?R.mipmap.video_zan_border:R.mipmap.video_zan_red);
                                tv_zan_num.setText(zan_num);
                                tv_pinglun_num.setText(pinglun_num);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        tvVideoTitle.setText(title);

        //高级设置（可选，须在start()之前调用方可生效）
        PlayerConfig playerConfig = new PlayerConfig.Builder()
                .enableCache() //启用边播边缓存功能
//                .autoRotate() //启用重力感应自动进入/退出全屏功能
//                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭AudioFocusChange监听
                .setLooping() //循环播放当前正在播放的视频
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);

        ijkVideoView.setUrl(url); //设置视频地址
//        ijkVideoView.setTitle(title); //设置视频标题
        StandardVideoController controller = new StandardVideoController(this);
        ijkVideoView.setVideoController(controller); //设置控制器，如需定制可继承BaseVideoController
        ijkVideoView.start(); //开始播放，不调用则不自动播放

    }
    public void initEmotionMainFragment(EditText etComment,ImageView ivBiaoqing){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT,false);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN,true);
        //替换fragment
        //创建修改实例
        emotionMainFragment =EmotionMainFragment.newInstance(EmotionMainFragment.class,bundle);
        emotionMainFragment.bindToContentView(etComment, ivBiaoqing);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @OnClick({R.id.iv_back, R.id.rl_active,R.id.iv_zan,R.id.iv_pinglun,R.id.iv_fenxiang,R.id.iv_xiangguanhuodong,R.id.iv_close,R.id.tv_comment})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_active:
//                showActivePopupwindow();
                break;
            case R.id.iv_zan:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(VideoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                toZan();
                break;
            case R.id.iv_pinglun:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(VideoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                startPingLunShowAnim();
                break;
            case R.id.iv_fenxiang:
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    intent.setClass(VideoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                new ShareAction(VideoActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(VideoActivity.this, mode.getVurl(), mode.getFmtitle(),
                                        "瞳伴app视频分享", mode.getFmpic(), share_media);
                            }
                        }).open();
                break;
            case R.id.iv_xiangguanhuodong:
                break;
            case R.id.iv_close:
                startPingLunHideAnim();
                break;
            case R.id.tv_comment:
                Log.d("asssss",isComment+"");
                if (isComment){
                    toComment(etComment);
                }else {
                    toReplay(etComment,vcID,vPostion);
                }
                break;
        }
    }
    private void toZan() {
        dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
        ViseHttp.POST(NetConfig.videoPraise)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoPraise))
                .addParam("id", vid)
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("aaaaaa",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String status = jsonObject.getJSONObject("obj").getString("status");
                                String num = jsonObject.getJSONObject("obj").getString("num");
                                iv_zan.setImageResource(status.equals("0")?R.mipmap.video_zan_border:R.mipmap.video_zan_red);
                                tv_zan_num.setText(num);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        WeiboDialogUtils.closeDialog(dialog);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                        toToast(VideoActivity.this,"操作失败！");
                    }
                });
    }
    private void showActivePopupwindow() {

        View view = LayoutInflater.from(VideoActivity.this).inflate(R.layout.popupwindow_video_active, null);

        ImageView tvCancel = view.findViewById(R.id.iv_close);
        ImageView iv_biaoqing = view.findViewById(R.id.iv_biaoqing);
//        iv_biaoqing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.update(0,emotionMainFragment.);
//            }
//        });
        final EditText editText = view.findViewById(R.id.et_comment);
        final RecyclerView recyclerView = view.findViewById(R.id.activity_video_rv);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                emotionMainFragment.hideEmotionKeyboard();
                return false;
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(VideoActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.articleCommentListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentListUrl))
                .addParam("fmID", vid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
//                                Gson gson = new Gson();
//                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
//                                mList = model.getObj();
//                                adapter = new ArticleCommentAdapter(mList);
//                                recyclerView.setAdapter(adapter);
//                                adapter.setOnReplyListener(new ArticleCommentAdapter.OnReplyListener() {
//                                    @Override
//                                    public void onReply(int position, String id) {
////                                        showPopupCommnet(1, id, mList.get(position).getFcID());
//                                        userid = id;
//                                        fcid = mList.get(position).getFcID();
//                                        showKeyboard(etComment);
//                                    }
//                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        //这句话，让pop覆盖在输入法上面
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        //这句话，让pop自适应输入状态
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0,0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
                emotionMainFragment.hideEmotionKeyboard();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    private void startPingLunShowAnim(){
        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(500l);     //设置动画的过渡时间
        ll_pinglun.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_pinglun.setVisibility(View.VISIBLE);
                ll_pinglun.startAnimation(ctrlAnimation);
            }
        }, 200);

    }
    private void startPingLunHideAnim(){
        //关闭键盘 和表情fragment
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        emotionMainFragment.hideEmotionKeyboard();
        etComment.setText(null);
        isComment = true;
        etComment.setHint("输入评论...");
//        /设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
        ctrlAnimation.setDuration(500l);     //设置动画的过渡时间
        ll_pinglun.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_pinglun.setVisibility(View.GONE);
                ll_pinglun.startAnimation(ctrlAnimation);
            }
        }, 200);
    }
    //弹出软键盘（点击 评论列表时弹出 回复用途）
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
            isComment = false;
            etComment.setHint("输入回复...");
        }
    }
    /**
     * 提交评论
     */
    private void toComment(final EditText etComment) {
        if (TextUtils.isEmpty(etComment.getText().toString())){
            toToast(VideoActivity.this,"请输入内容");
            return;
        }
            dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
            ViseHttp.POST(NetConfig.videoReviews)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoReviews))
                    .addParam("id", vid)
                    .addParam("fctitle", etComment.getText().toString())
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            WeiboDialogUtils.closeDialog(dialog);
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    toToast(VideoActivity.this, "评论成功");
                                    emotionMainFragment.hideEmotionKeyboard();
                                    etComment.setText(null);
                                    Gson gson = new Gson();

                                    ActicleCommentVideoModel.ObjBean model = gson.fromJson(jsonObject.getJSONObject("obj").toString(),ActicleCommentVideoModel.ObjBean.class);
                                    data_pinglun.add(0,model);
                                    articleCommentVideoAdapter.notifyDataSetChanged();
                                    rv_pinglun.scrollToPosition(0);
                                }else {
                                    toToast(VideoActivity.this, "评论失败");
                                }
                            } catch (JSONException e) {
                                toToast(VideoActivity.this, "评论失败");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                            toToast(VideoActivity.this, "评论失败"+errCode+":"+errMsg);
                        }
                    });
    }
    //回复评论
    private void toReplay(EditText editText, String vcID, final int pingLunPostion){
        if (TextUtils.isEmpty(editText.getText().toString())){
            toToast(VideoActivity.this,"请输入内容");
            return;
        }
        dialog = WeiboDialogUtils.createLoadingDialog(VideoActivity.this,"");
        ViseHttp.POST(NetConfig.replyVideoReviews)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.replyVideoReviews))
                .addParam("userID", uid)
                .addParam("vcID", vcID)
                .addParam("vinfo", editText.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        WeiboDialogUtils.closeDialog(dialog);
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                toToast(VideoActivity.this, "回复成功");
                                emotionMainFragment.hideEmotionKeyboard();
                                etComment.setText(null);
                                Gson gson = new Gson();
                                ActicleCommentVideoModel.ObjBean.ReplyListBean bean = gson.fromJson(jsonObject.getJSONObject("obj").toString(),ActicleCommentVideoModel.ObjBean.ReplyListBean.class);
                                data_pinglun.get(pingLunPostion).getReplyList().add(bean);
                                articleCommentVideoAdapter.notifyDataSetChanged();
                                rv_pinglun.scrollToPosition(pingLunPostion);
                                isComment = true;
                                etComment.setHint("输入评论...");
                            }else {
                                toToast(VideoActivity.this, "回复失败");
                            }
                        } catch (JSONException e) {
                            toToast(VideoActivity.this, "回复失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                        isComment = true;
                        etComment.setText(null);
                        etComment.setHint("输入评论....");
                        toToast(VideoActivity.this, "回复失败"+errCode+":"+errMsg);
                    }
                });
    }
    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN&&ll_pinglun.isShown()&&!isTouchView(new View[]{ll_pinglun},event)){
            startPingLunHideAnim();
        }
        return super.dispatchTouchEvent(event);
    }

    //是否触摸在指定view上面,对某个控件过滤
     public boolean isTouchView(View[] views, MotionEvent ev) {
        if(views ==null|| views.length==0)
            return false;
        int[] location =new int[2];
        for(View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if(ev.getX() > x && ev.getX() < (x + view.getWidth())&& ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }return false;
    }
    public void toToast(Context c, String content){
        Toast.makeText(c,content,Toast.LENGTH_SHORT).show();
    }
    public String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }

}
