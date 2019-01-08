package com.yiwo.friendscometogether.newpage;

import android.graphics.Color;
import android.os.Bundle;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;

import butterknife.ButterKnife;

public class EditorLabelListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_label_list);

        StatusBarUtils.setStatusBar(EditorLabelListActivity.this, Color.parseColor("#D84C37"));
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(EditorLabelListActivity.this);

    }
}
