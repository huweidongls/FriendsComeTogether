package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_update_rl_back,R.id.activity_update_update_bt})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.activity_update_rl_back:
                finish();
                break;
            case R.id.activity_update_update_bt:
                toToast(this,"检查更新");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                //此处填写更新apk地址
                Uri apk_url = Uri.parse("http://static-app.gangjianwang.com/static/app/xinyonggong.apk");
                intent.setData(apk_url);
                startActivity(intent);
                break;
        }
    }
}
