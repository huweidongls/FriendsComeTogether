package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorMyLabelActivity extends BaseActivity {

    @BindView(R.id.edit_lable)
    EditText edit_lable;
    @BindView(R.id.rl_clear)
    RelativeLayout rl_clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_my_label);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(EditorMyLabelActivity.this);
        edit_lable.addTextChangedListener(textWatcher);
        rl_clear.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_back,R.id.rl_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear:
                edit_lable.setText("");
                break;
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")){
                rl_clear.setVisibility(View.VISIBLE);
            }else {
                rl_clear.setVisibility(View.GONE);
            }
        }
    };
}
