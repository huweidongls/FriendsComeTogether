package com.yiwo.friendscometogether.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.ArticleCommentAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.SoftKeyBoardListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleCommentActivity extends BaseActivity {

    @BindView(R.id.activity_article_comment_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_article_comment_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_article_comment_et_comment)
    EditText etComment;
    @BindView(R.id.activity_article_comment_tv_comment)
    TextView tvComment;

    private ArticleCommentAdapter adapter;
    private List<ArticleCommentListModel.ObjBean> mList;

    private String fmID = "";

    private SpImp spImp;
    private String uid = "";

    private boolean isComment = true;

    /**
     * popupwindow相关
     */
    private Button btn_submit;
    private ImageView btn_back;
    private PopupWindow popupWindowhf;

    private String userid = "";
    private String fcid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_comment);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(ArticleCommentActivity.this);

        //注册软键盘的监听
        SoftKeyBoardListener.setListener(ArticleCommentActivity.this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void keyBoardHide(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                        if (popupWindowhf != null) {
                            popupWindowhf.dismiss();
                        }
                    }
                });

        initData();

    }

    private void initData() {

        uid = spImp.getUID();

        Intent intent = getIntent();
        fmID = intent.getStringExtra("id");

        LinearLayoutManager manager = new LinearLayoutManager(ArticleCommentActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.articleCommentListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentListUrl))
                .addParam("fmID", fmID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                mList = model.getObj();
                                adapter = new ArticleCommentAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnReplyListener(new ArticleCommentAdapter.OnReplyListener() {
                                    @Override
                                    public void onReply(int position, String id) {
//                                        showPopupCommnet(1, id, mList.get(position).getFcID());
                                        userid = id;
                                        fcid = mList.get(position).getFcID();
                                        showKeyboard(etComment);
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

    @OnClick({R.id.activity_article_comment_rl_back, R.id.activity_article_comment_tv_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_article_comment_rl_back:
                onBackPressed();
                break;
            case R.id.activity_article_comment_tv_comment:
                if (TextUtils.isEmpty(etComment.getText().toString())) {
                    toToast(ArticleCommentActivity.this, "请输入评论内容");
                } else {
                    toComment();
                }
                break;
        }
    }

    /**
     * 提交评论
     */
    private void toComment() {

        if(isComment){
            ViseHttp.POST(NetConfig.articleCommentUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentUrl))
                    .addParam("id", fmID)
                    .addParam("fctitle", etComment.getText().toString())
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    toToast(ArticleCommentActivity.this, "评论成功");
                                    etComment.setText(null);
                                    reload();
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            etComment.setText(null);
                        }
                    });
        }else {
            ViseHttp.POST(NetConfig.replyCommentUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.replyCommentUrl))
                    .addParam("commentid", userid)
                    .addParam("first_fcID", userid)
                    .addParam("ArticleId", fmID)
                    .addParam("fctitle", etComment.getText().toString())
                    .addParam("uid", uid)
                    .addParam("oneID", fcid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    toToast(ArticleCommentActivity.this, "回复成功");
                                    etComment.setText(null);
                                    reload();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isComment = true;
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            isComment = true;
                            etComment.setText(null);
                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArticleCommentActivity.this.finish();
    }

    //弹出软键盘
    public void showKeyboard(EditText editText) {
        //其中editText为dialog中的输入框的 EditText
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
            isComment = false;
        }
    }

    /**
     * show soft input
     */
    private void popupInputMethodWindow() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }.start();
        //
    }

    /**
     * show comment popupwindow（弹出发表评论的popupWindow）
     */
    @SuppressLint("WrongConstant")
    private void showPopupCommnet(final int type, final String id, final String fcid) {// pe表示是评论还是举报1.代表评论。2.代表举报
        View view = LayoutInflater.from(ArticleCommentActivity.this).inflate(
                R.layout.comment_popupwindow, null);
        ScreenAdapterTools.getInstance().loadView(view);
        final EditText inputComment = view
                .findViewById(R.id.comment);

        btn_submit = view.findViewById(R.id.submit_comment);
        btn_back = view.findViewById(R.id.btn_back);
        if (type == 1) {
            btn_submit.setText("提交");
            inputComment.setHint("请输入你的评论...");
        }
        popupWindowhf = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindowhf.setTouchable(true);
        popupWindowhf.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindowhf.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindowhf.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.bg_activity_fb_dt_zt));

        // 设置弹出窗体需要软键盘
        popupWindowhf.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindowhf.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindowhf.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        ColorDrawable cd = new ColorDrawable(0x000000);
//        popupWindow.setBackgroundDrawable(cd);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.4f;
//        getWindow().setAttributes(params);
        // 设置popWindow的显示和消失动画
        popupWindowhf.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindowhf.update();
        popupInputMethodWindow();
        popupWindowhf.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputComment.getText().toString())){
                    toToast(ArticleCommentActivity.this, "请输入评论内容");
                }else {
                    ViseHttp.POST(NetConfig.replyCommentUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.replyCommentUrl))
                            .addParam("commentid", id)
                            .addParam("first_fcID", id)
                            .addParam("ArticleId", fmID)
                            .addParam("fctitle", inputComment.getText().toString())
                            .addParam("uid", uid)
                            .addParam("oneID", fcid)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.getInt("code") == 200){
                                            toToast(ArticleCommentActivity.this, "回复成功");
                                            reload();
                                            popupWindowhf.dismiss();
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
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowhf.dismiss();
            }
        });
    }

    private void reload (){
        ViseHttp.POST(NetConfig.articleCommentListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentListUrl))
                .addParam("fmID", fmID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                Log.e("222", "刷新页面");
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
