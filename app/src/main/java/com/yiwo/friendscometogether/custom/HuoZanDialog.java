package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by ljc on 2019/2/26.
 */

public class HuoZanDialog extends Dialog{
    private  Context context;
    private TextView tv_username,tv_huozan_num;
    private Button btn_ok;
    private String str_username,str_huozan_num;
    public HuoZanDialog(@NonNull Context context,String str_username,String str_huozan_num) {
        super(context);
        this.context =context;
        this.str_username = str_username;
        this.str_huozan_num = str_huozan_num;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_huozan, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        tv_username = view.findViewById(R.id.tv_user_name);
        tv_huozan_num = view.findViewById(R.id.tv_huozan_num);
        btn_ok = view.findViewById(R.id.btn_ok);
        SpannableString spannableString = new SpannableString(str_huozan_num);
        ForegroundColorSpan span_color = new ForegroundColorSpan(Color.parseColor("#d84c37"));
        AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(60,true);
        spannableString.setSpan(span_color,0,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span_size,0,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_huozan_num.setText("共获得");
        tv_huozan_num.append(spannableString);
        tv_huozan_num.append("个赞");
        tv_username.setText(str_username);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
