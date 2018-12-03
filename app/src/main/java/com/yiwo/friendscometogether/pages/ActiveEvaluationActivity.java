package com.yiwo.friendscometogether.pages;

import android.annotation.SuppressLint;
import android.app.Service;
import android.graphics.drawable.ColorDrawable;
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

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MyActiveCommentAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.ActiveCommentModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.SoftKeyBoardListener;
import com.yiwo.friendscometogether.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActiveEvaluationActivity extends BaseActivity {
    @BindView(R.id.activity__active_evaluation_rv)
    RecyclerView rv;
    @BindView(R.id.activity_active_evaluation_et_comment)
    EditText et;
    @BindView(R.id.activity_active_evaluation_rl_bottom)
    RelativeLayout rlComment;

    private SpImp spImp;
    MyActiveCommentAdapter adapter;
    /**
     * popupwindow相关
     */
    private Button btn_submit;
    private ImageView btn_back;
    private PopupWindow popupWindowhf;

    private String pfid = "";

    private ActiveCommentModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_evaluation);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(this);
//注册软键盘的监听
        SoftKeyBoardListener.setListener(this,
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

        pfid = getIntent().getStringExtra("pfID");

        ViseHttp.POST(NetConfig.activeEvaluationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeEvaluationListUrl))
                .addParam("userID", spImp.getUID())
                .addParam("pfID", pfid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                model = new Gson().fromJson(data, ActiveCommentModel.class);
                                initList(model.getObj());
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

    private void initList(List<ActiveCommentModel.ObjBean> data) {

        LinearLayoutManager manager = new LinearLayoutManager(ActiveEvaluationActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new MyActiveCommentAdapter(data);
        rv.setAdapter(adapter);
        adapter.setOnReplyListener(new MyActiveCommentAdapter.OnReplyListener() {
            @Override
            public void onReply(int position, String id) {
                if(model.getObj().get(position).getReply().equals("0")){

                }else {
                    showPopupCommnet(1, id);
                }
            }
        });

    }

    @OnClick({R.id.activity_active_evaluation_tv_comment, R.id.activity_active_evaluation_rl_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_active_evaluation_rl_back:
                finish();
                break;
            case R.id.activity_active_evaluation_tv_comment:
                if (StringUtils.isEmpty(et.getText().toString())) {
                    toToast(ActiveEvaluationActivity.this, "请输入评论内容");
                } else {//回复

                }
                break;
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
    private void showPopupCommnet(final int type, final String id) {// pe表示是评论还是举报1.代表评论。2.代表举报
        View view = LayoutInflater.from(ActiveEvaluationActivity.this).inflate(
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
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindowhf.setBackgroundDrawable(cd);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        // 设置popWindow的显示和消失动画
        popupWindowhf.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindowhf.update();
        popupInputMethodWindow();
        popupWindowhf.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputComment.getText().toString())){
                    toToast(ActiveEvaluationActivity.this, "请输入评论内容");
                }else {
                    ViseHttp.POST(NetConfig.activeReturnCommentUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeReturnCommentUrl))
                            .addParam("userID", spImp.getUID())
                            .addParam("comment_id", id)
                            .addParam("content", inputComment.getText().toString())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.getInt("code") == 200){
                                            toToast(ActiveEvaluationActivity.this, "回复成功");
                                            reLoad();
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

    private void reLoad(){
        ViseHttp.POST(NetConfig.activeEvaluationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeEvaluationListUrl))
                .addParam("userID", spImp.getUID())
                .addParam("pfID", pfid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                model = new Gson().fromJson(data, ActiveCommentModel.class);
                                initList(model.getObj());
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
