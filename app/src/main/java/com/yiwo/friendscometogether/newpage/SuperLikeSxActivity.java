package com.yiwo.friendscometogether.newpage;

import android.os.Bundle;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.widget.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuperLikeSxActivity extends BaseActivity {

    @BindView(R.id.seekbar)
    RangeSeekBar rangeSeekBar;
    @BindView(R.id.tv_age)
    TextView tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like_sx);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(SuperLikeSxActivity.this);

        initData();

    }

    private void initData() {

        rangeSeekBar.setValue(25, 30);
        rangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max) {
                tvAge.setText((int)min+"-"+(int)max);
            }
        });

    }
}
