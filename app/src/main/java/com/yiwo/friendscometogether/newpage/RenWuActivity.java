package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.webpages.RenWuWebActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenWuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_wu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
    }
    @OnClick({R.id.activity_ren_wu_rl_back,R.id.ll_1,R.id.ll_2,R.id.ll_3,R.id.ll_4,R.id.ll_5,R.id.ll_6})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_ren_wu_rl_back:
                onBackPressed();
                break;
            case R.id.ll_1:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_2:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_3:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_4:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_5:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_6:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/questionAnswerGame");//知识问答链接
                break;
        }
    }
    private void startWeb(String string){
        Intent intent = new Intent();
        intent.setClass(RenWuActivity.this,RenWuWebActivity.class);
        intent.putExtra("url",string);
        startActivity(intent);
    }
}
