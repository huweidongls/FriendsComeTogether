package com.yiwo.friendscometogether.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.vas_sonic.SonicJavaScriptInterface;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;
import com.yiwo.friendscometogether.vas_sonic.TBSonicSessionClient;

import java.util.HashMap;

public class BaseSonicWebActivity extends BaseActivity {

    private Dialog dialogLoading;
    private SonicSession sonicSession;
    private String url;
    private Intent intent;
    private WebView webView;
    private SpImp spImp;
    public static final String URL = "url";
    public static final String WEB_VIEW_ID = "wedViewId";
    private TBSonicSessionClient sonicSessionClient = null;
    private SonicJavaScriptInterface sonicJavaScriptInterface = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        spImp = new SpImp(BaseSonicWebActivity.this);
    }

    public void initWebView(WebView webView,String url) {
//        WebSettings webSettings = webView.getSettings();
//        // 设置与Js交互的权限
//        webSettings.setJavaScriptEnabled(true);
//        // 设置允许JS弹窗
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        //        //添加客户端支持
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                WeiboDialogUtils.closeDialog(dialogLoading);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////                super.onReceivedSslError(view, handler, error);
//                handler.proceed();
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//
//        }
//        webView.loadUrl(url);
//        dialogLoading = WeiboDialogUtils.createLoadingDialog(this,"加载中...");
    }
    public void initIntentSonic(String url,WebView webView){
        this.webView = webView;
        Intent intent = new Intent();
        intent.putExtra(URL, url);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        this.intent = intent;
        url = intent.getStringExtra(URL);
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new TBSonicRuntime(getApplication()), new SonicConfig.Builder().build());
            Log.d("SonicEngine.create","webPage_bbb");
        }
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
        HashMap mapRp = new HashMap();
        String str_vlue = "http://www.91yiwo.com/ylyy/include/activity_web/js/jquery-3.3.1.min.js;"
                +"http://www.91yiwo.com/ylyy/include/activity_web/css/web_main.css;"
                +"http://www.91yiwo.com/ylyy/include/activity_web/js/builder.js;";
        mapRp.put("sonic-link",str_vlue);
        sessionConfigBuilder.setCustomResponseHeaders(mapRp);
//        sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
//            @Override
//            public String getCacheData(SonicSession session) {
//                return null; // offline pkg does not need cache
//            }
//        });
//
//        sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
//            @Override
//            public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
//                return new OfflinePkgSessionConnection(BrowserActivity.this, session, intent);
//            }
//        });
        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSessionClient = new TBSonicSessionClient();
            if (sonicSession.bindClient(sonicSessionClient)){
//                Toast.makeText(this, "create sonic session 成功!", Toast.LENGTH_LONG).show();
            }
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
//            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
        // init webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = webView.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        sonicJavaScriptInterface = new SonicJavaScriptInterface(sonicSessionClient, intent);
        webView.addJavascriptInterface(sonicJavaScriptInterface, "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sonicJavaScriptInterface = null;
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
    }

//    public static void startBrowserActivity(String url, Context context, Activity activity, int wedViewId) {
//        Intent intent = new Intent(context,activity.getClass());
//        intent.putExtra(URL, url);
//        intent.putExtra(WEB_VIEW_ID,wedViewId);
//        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
//        context.startActivity(intent);
//    }
}
