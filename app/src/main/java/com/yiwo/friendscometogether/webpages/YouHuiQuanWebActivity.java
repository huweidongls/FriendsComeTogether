package com.yiwo.friendscometogether.webpages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class YouHuiQuanWebActivity extends BaseWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initWebView(webView,url);
        webView.addJavascriptInterface(new YouHuiQuanWebActivity.AndroidInterface(),"Android");//交互
    }
    @OnClick({R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void usecoupon(String youhui_id,String youhui_price){//优惠券ID、优惠金额
            Intent intent  =new Intent();
            intent.putExtra("youhui_id",youhui_id);
            intent.putExtra("youhui_price",youhui_price);
            Log.d("sadasdas","sddasdasd");
            setResult(1,intent);
            finish();
        }
    }
}
