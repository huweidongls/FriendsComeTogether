package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by ljc on 2019/9/26.
 */

public class DuiZhangShowDialog extends Dialog {

    private Context context;
    private String info;
    private String userIcon;
    private OnGuanzhuListenner listenner;
    private boolean isFollow;
    public DuiZhangShowDialog(@NonNull Context context, String info,String userIcon,OnGuanzhuListenner listenner,boolean isfollow) {
        super(context);
        this.context = context;
        this.info = info;
        this.userIcon = userIcon;
        this.listenner = listenner;
        this.isFollow = isfollow;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_duizhang_show, null);
        setContentView(view);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ScreenAdapterTools.getInstance().loadView(view);
        TextView textView = view.findViewById(R.id.tv_info);
        RelativeLayout rl = view.findViewById(R.id.rl);
        ImageView icon = view.findViewById(R.id.iv_icon);
        RelativeLayout btnGuanzhu = view.findViewById(R.id.btn_guanzhu);
        TextView tvGuanzhu = view.findViewById(R.id.tv_guanzhu);
        tvGuanzhu.setText(isFollow ? "已关注":"关 注");
        Glide.with(context).load(userIcon).into(icon);
        textView.setText(info);
        btnGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.guanZhuListen(DuiZhangShowDialog.this);
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public interface OnGuanzhuListenner{
        void guanZhuListen(Dialog dialog);
    }
}
