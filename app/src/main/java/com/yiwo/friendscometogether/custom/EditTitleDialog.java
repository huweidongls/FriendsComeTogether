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
import com.yiwo.friendscometogether.utils.StringUtils;

/**
 * Created by Administrator on 2018/7/20.
 */

public class EditTitleDialog extends Dialog {

    private Context context;
    private OnReturnListener listener;
    private TextView lengthTv;
    private EditText etTitle;
    private RelativeLayout rlClose;
    private Button btnOk;
    CharSequence ss = "";

    public EditTitleDialog(@NonNull Context context, OnReturnListener onReturnListener) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_title, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        etTitle = view.findViewById(R.id.dialog_edit_title_et_content);
        rlClose = view.findViewById(R.id.dialog_edit_title_rl_close);
        btnOk = view.findViewById(R.id.dialog_edit_title_btn_ok);
        lengthTv = view.findViewById(R.id.dialog_edit_title_tv_text_num);
        setEditTextInputSpace(etTitle);
        etTitle.addTextChangedListener(new TextWatcher() {
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
