package com.yiwo.friendscometogether.webpages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.network.NetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ApplyActivityAgreementWebActivity extends BaseWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_agree)
    Button btn_agree;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_agreement_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        initWebView(webView, NetConfig.joinDealUrl);
    }
    @OnClick({R.id.activity_apply_agreement__back,R.id.btn_agree,R.id.btn_cancel})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.activity_apply_agreement__back:
                finish();
                break;
            case R.id.btn_agree:
                setResult(1);
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
