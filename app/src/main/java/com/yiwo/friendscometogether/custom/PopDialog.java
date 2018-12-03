package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2018/8/5.
 */

public class PopDialog extends Dialog {
    private Context context;
    private String title;
    private String sureBtn;
    private String cancleBtn;
    private TextView titleTv,sureTv,cancleTv;
    private RelativeLayout sureRl,cancleRl;
    private PopDialogListener listener;
    public PopDialog(@NonNull Context context,String title,String sureBtn,String cancleBtn,PopDialogListener listener) {
        super(context);
        this.context = context;
        this.title = title;
        this.sureBtn = sureBtn;
        this.cancleBtn = cancleBtn;
        this.listener = listener;
    }
    public interface PopDialogListener{
        void sureBtnListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.pop_dialog, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        titleTv = (TextView) view.findViewById(R.id.tv_pop_dialog_0_content);
        sureTv = (TextView) view.findViewById(R.id.tv_pop_dialog_0_sure);
        cancleTv = (TextView) view.findViewById(R.id.tv_pop_dialog_0_cancel);

        sureRl = (RelativeLayout) view.findViewById(R.id.rl_pop_dialog_0_sure);
        cancleRl = (RelativeLayout) view.findViewById(R.id.rl_pop_dialog_0_cancel);

        titleTv.setText(title);
        sureTv.setText(sureBtn);
        cancleTv.setText(cancleBtn);

        sureRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sureBtnListener();
                dismiss();
            }
        });
        cancleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
