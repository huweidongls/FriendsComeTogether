package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.xsnow.cache.SpCache;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.SuperLikeModel;
import com.yiwo.friendscometogether.model.SuperLikeSXMode;
import com.yiwo.friendscometogether.widget.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperLikeSxActivity extends BaseActivity {


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

    private Context context = SuperLikeSxActivity.this;
    private SuperLikeSXMode sxMode;
    private int sex,address,type_expand_search;
    private SpCache spCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like_sx);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(SuperLikeSxActivity.this);
        spCache = new SpCache(context);
        initData();
    }

    private void initData() {
        sxMode = (SuperLikeSXMode) spCache.get("SuperLikeSXMode");
        if (sxMode == null){
            Log.d("SuperLikeSXMode_isNUll","!!!");
            sxMode = new SuperLikeSXMode();
            sxMode.setAges("25-30");
            sxMode.setSex(1);
            sxMode.setAddress(1);
            sxMode.setType_expand_search(1);
        }

        sex = sxMode.getSex();
        address = sxMode.getAddress();
        type_expand_search =sxMode.getType_expand_search();
        tvAge.setText(sxMode.getAges());
        String []ages = sxMode.getAges().split("-");
        rangeSeekBar.setValue(Float.parseFloat(ages[0]),Float.parseFloat(ages[1]));
        rangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max) {
                tvAge.setText((int)min+"-"+(int)max);
            }
        });
        if ( sex == 0){
            sex = 0;
            tv1.setTextColor(Color.parseColor("#ffffff"));
            tv1.setBackgroundResource(R.drawable.bg_red_20px);
            tv2.setTextColor(Color.parseColor("#333333"));
            tv2.setBackgroundResource(R.drawable.bg_gray_20px);
        }else if (sex == 1){
            tv1.setTextColor(Color.parseColor("#333333"));
            tv1.setBackgroundResource(R.drawable.bg_gray_20px);
            tv2.setTextColor(Color.parseColor("#ffffff"));
            tv2.setBackgroundResource(R.drawable.bg_red_20px);
        }
        if (address == 0){
            tv3.setTextColor(Color.parseColor("#ffffff"));
            tv3.setBackgroundResource(R.drawable.bg_red_20px);
            tv4.setTextColor(Color.parseColor("#333333"));
            tv4.setBackgroundResource(R.drawable.bg_gray_20px);
        }else if (address == 1){
            tv3.setTextColor(Color.parseColor("#333333"));
            tv3.setBackgroundResource(R.drawable.bg_gray_20px);
            tv4.setTextColor(Color.parseColor("#ffffff"));
            tv4.setBackgroundResource(R.drawable.bg_red_20px);
        }
        if (type_expand_search == 0){
            iv_expand_search.setImageResource(R.mipmap.switch_off);
        }else {
            iv_expand_search.setImageResource(R.mipmap.switch_on);
        }
    }

    @OnClick({R.id.rl_back,R.id.rl_sure,R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,R.id.iv_expand_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_sure:
                sxMode.setAges(tvAge.getText().toString());
                sxMode.setSex(sex);
                sxMode.setType_expand_search(type_expand_search);
                sxMode.setAddress(address);
                spCache.put("SuperLikeSXMode",sxMode);
                setResult(2);
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
        setResult(2);
    }

}
