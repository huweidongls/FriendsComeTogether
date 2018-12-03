package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2018/7/20.
 */

public class LookPasswordDialog extends Dialog {

    private Context context;
    private TextView submitTv;
    private RelativeLayout closeRl;
    private EditText pwdEt;
    private SetPasswordListener listener;

    public interface SetPasswordListener{
        void setActivityText(String s);
    }
    public LookPasswordDialog(@NonNull Context context, SetPasswordListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_look_password, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        submitTv = (TextView) view.findViewById(R.id.dialog_set_password_tv_submit);
        pwdEt = (EditText) view.findViewById(R.id.dialog_set_password_et_pwd);
        closeRl = (RelativeLayout) view.findViewById(R.id.dialog_set_password_rl_close);
        closeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setActivityText(pwdEt.getText().toString());
                dismiss();
            }
        });
    }

}
