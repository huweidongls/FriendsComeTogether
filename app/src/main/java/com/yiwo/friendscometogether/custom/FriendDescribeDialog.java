package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2018/8/31 0031.
 */

public class FriendDescribeDialog extends Dialog {

    private Context context;
    private OnReturnListener listener;

    private EditText etTitle;
    private RelativeLayout rlClose;
    private Button btnOk;

    public void setOnReturnListener(OnReturnListener listener){
        this.listener = listener;
    }

    public FriendDescribeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_friend_describe, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        etTitle = view.findViewById(R.id.dialog_pic_describe_et_content);
        rlClose = view.findViewById(R.id.dialog_pic_describe_rl_close);
        btnOk = view.findViewById(R.id.dialog_pic_describe_btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReturn(etTitle.getText().toString());
                dismiss();
            }
        });
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public interface OnReturnListener {
        void onReturn(String title);
    }

}
