package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.widget.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperLikeSxActivity extends BaseActivity {

    private Context context = SuperLikeSxActivity.this;
    private int sex = 1;
    private int address = 1;
    private String ages = "25-30";
    @BindView(R.id.seekbar)
    RangeSeekBar rangeSeekBar;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.iv_expand_search)
    ImageView iv_expand_search;

    private int type_expand_search = 1;
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

    @OnClick({R.id.rl_back,R.id.rl_sure,R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,R.id.iv_expand_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                setActivityResultIntent();
                finish();
                break;
            case R.id.rl_sure:
                setActivityResultIntent();
                finish();
                break;
            case R.id.tv1:
                sex = 0;
                tv1.setTextColor(Color.parseColor("#ffffff"));
                tv1.setBackgroundResource(R.drawable.bg_red_20px);
                tv2.setTextColor(Color.parseColor("#333333"));
                tv2.setBackgroundResource(R.drawable.bg_gray_20px);
                break;
            case R.id.tv2:
                sex = 1;
                tv1.setTextColor(Color.parseColor("#333333"));
                tv1.setBackgroundResource(R.drawable.bg_gray_20px);
                tv2.setTextColor(Color.parseColor("#ffffff"));
                tv2.setBackgroundResource(R.drawable.bg_red_20px);
                break;
            case R.id.tv3:
                address = 0;
                tv3.setTextColor(Color.parseColor("#ffffff"));
                tv3.setBackgroundResource(R.drawable.bg_red_20px);
                tv4.setTextColor(Color.parseColor("#333333"));
                tv4.setBackgroundResource(R.drawable.bg_gray_20px);
                break;
            case R.id.tv4:
                address = 1;
                tv3.setTextColor(Color.parseColor("#333333"));
                tv3.setBackgroundResource(R.drawable.bg_gray_20px);
                tv4.setTextColor(Color.parseColor("#ffffff"));
                tv4.setBackgroundResource(R.drawable.bg_red_20px);
                break;
            case R.id.iv_expand_search:
                if (type_expand_search == 1){
                    type_expand_search = 0;
                    iv_expand_search.setImageResource(R.mipmap.switch_off);
                }else {
                    type_expand_search = 1;
                    iv_expand_search.setImageResource(R.mipmap.switch_on);
                }
                break;
        }
    }

    private void setActivityResultIntent() {
        ages = tvAge.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("age",ages);
        intent.putExtra("sex",sex);
        intent.putExtra("address",address);
        setResult(2,intent);
    }

}
