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

public class XieYiDialog extends Dialog {

    private Context context;
    private XieYiDialogListener xieYiDialogListener;

    private TextView tvXieYi,tvZhengCe,sureTv,cancleTv;

    public XieYiDialog(@NonNull Context context,XieYiDialogListener listener) {
        super(context);
        this.context = context;
        this.xieYiDialogListener = listener;
    }
    public interface XieYiDialogListener{
        void agreeBtnListen();
        void disAgreeBtnListen();
        void xieYiTextClickListen();
        void zhengCeTextClickListen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_xieyi, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        sureTv = view.findViewById(R.id.btn_agree);
        cancleTv = view.findViewById(R.id.btn_disagree);
        tvXieYi = view.findViewById(R.id.tv_xieyi);
        tvZhengCe = view.findViewById(R.id.tv_zhengce);

        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.agreeBtnListen();
                dismiss();
            }
        });
        cancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.disAgreeBtnListen();
            }
        });
        tvXieYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.xieYiTextClickListen();
            }
        });
        tvZhengCe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xieYiDialogListener.zhengCeTextClickListen();
            }
        });
    }
}
