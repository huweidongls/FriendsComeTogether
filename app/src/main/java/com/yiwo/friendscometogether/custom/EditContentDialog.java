package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2018/7/20.
 */

public class EditContentDialog extends Dialog {

    private Context context;
    private OnReturnListener listener;

    private RelativeLayout rlClose;
    private EditText etContent;
    private Button btnOk;
    private TextView lengthTv;
    CharSequence ss = "";
    public EditContentDialog(@NonNull Context context, OnReturnListener listener) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_content, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        rlClose = view.findViewById(R.id.dialog_edit_content_rl_close);
        etContent = view.findViewById(R.id.dialog_edit_content_et_content);
        btnOk = view.findViewById(R.id.dialog_edit_content_btn_ok);
        lengthTv = view.findViewById(R.id.dialog_edit_title_tv_text_num);
        setEditTextInputSpace(etContent);
        etContent.addTextChangedListener(new TextWatcher() {
            //            CharSequence cs;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ss = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lengthTv.setText(s.length()+"/200");
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReturn(etContent.getText().toString());
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

    public interface OnReturnListener{
        void onReturn(String content);
    }
    public static void setEditTextInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
