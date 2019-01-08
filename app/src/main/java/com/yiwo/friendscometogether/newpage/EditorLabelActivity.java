package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorLabelActivity extends BaseActivity {

    private Context context = EditorLabelActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_label);

        StatusBarUtils.setStatusBar(EditorLabelActivity.this, Color.parseColor("#D84C37"));
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(EditorLabelActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.rl1})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:

                break;
            case R.id.rl1:
                intent.setClass(this, EditorLabelListActivity.class);
                startActivity(intent);
                break;
        }
    }

}
