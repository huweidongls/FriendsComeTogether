package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by ljc on 2019/1/9.
 */

public class PopDialog1 extends Dialog {

    private Context context;
    private String title,message,canelBtn,sureBtn;
    private PopDialogListener popDialogListener;

    private TextView titleTv,messageTv,sureTv,cancleTv;
    private RelativeLayout sureRl,cancleRl;

    public PopDialog1(@NonNull Context context, String title, String message,
                      String cancleBtn, String sureBtn, PopDialogListener listener) {
        super(context);
        this.context = context;
        this.canelBtn = cancleBtn;
        this.sureBtn = sureBtn;
        this.title = title;
        this.message = message;
        this.popDialogListener = listener;
    }
    public interface PopDialogListener{
        void sureBtnListen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.pop_dialog_1, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        titleTv = view.findViewById(R.id.pop_dialog_title);
        messageTv = view.findViewById(R.id.pop_dialog_message);
        sureTv = view.findViewById(R.id.pop_dialog_sure_btn);
        cancleTv = view.findViewById(R.id.pop_dialog_cancel_btn);
        cancleRl = view.findViewById(R.id.rl_pop_dialog_cancel);
        sureRl = view.findViewById(R.id.rl_pop_dialog_sure);
        titleTv.setText(title);
        messageTv.setText(message);
        sureTv.setText(sureBtn);
        sureRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialogListener.sureBtnListen();
                dismiss();
            }
        });
        cancleTv.setText(canelBtn);
        cancleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
