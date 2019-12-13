package com.yiwo.friendscometogether.webpages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberActivityChoosePicOrVideos;
import com.yiwo.friendscometogether.newpage.EditorLabelActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyJiFenActivity extends BaseWebActivity {


    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ji_fen);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        spImp = new SpImp(MyJiFenActivity.this);
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initWebView(webView,url);
        webView.addJavascriptInterface(new MyJiFenActivity.AndroidInterface(),"android");//交互
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void addarticle(){//优跳到发布文章
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
//                        intent.setClass(context, CreateFriendRememberActivity.class);
                intent.setClass(MyJiFenActivity.this, CreateFriendRememberActivityChoosePicOrVideos.class);
                startActivity(intent);
            } else {
                intent.setClass(MyJiFenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        @JavascriptInterface
        public void youjilist(){//跳到文章列表
            setResult(1);
            finish();
        }
        @JavascriptInterface
        public void youjulist(){//跳到活动列表
            setResult(2);
            finish();
        }
        @JavascriptInterface
        public void  videolist(){//跳到视频列表
            setResult(3);
            finish();
        }
        @JavascriptInterface
        public void   setsign(){//跳到设置个性标签功能
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                intent.setClass(MyJiFenActivity.this, EditorLabelActivity.class);
                startActivity(intent);
            }else {
                intent.setClass(MyJiFenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
    @OnClick({R.id.rl_back})
    public void OnClickListen(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
