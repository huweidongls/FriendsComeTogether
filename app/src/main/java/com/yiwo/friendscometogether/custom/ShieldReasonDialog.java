package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ShieldReasonDialog extends Dialog {
    private Context context;
    private EditTitleDialog.OnReturnListener listener;

    private EditText etTitle;
    private RelativeLayout rlClose;
    private Button btnOk;

    public void setOnReturnListener(EditTitleDialog.OnReturnListener listener){
        this.listener = listener;
    }

    public ShieldReasonDialog(@NonNull Context context) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shield_reason, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        etTitle = view.findViewById(R.id.dialog_shield_reason_et_content);
        rlClose = view.findViewById(R.id.dialog_shield_reason_rl_close);
        btnOk = view.findViewById(R.id.dialog_shield_reason_btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etTitle.getText().toString())){
                    listener.onReturn(etTitle.getText().toString());
                }else {
                    Toast.makeText(context, "请输入屏蔽原因", Toast.LENGTH_SHORT).show();
                }
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
