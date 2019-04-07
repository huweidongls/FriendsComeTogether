package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vise.xsnow.cache.SpCache;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private String account;
    private SpImp spImp;
    private SpCache spCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        StatusBarUtils.setStatusBarTransparent(WelcomeActivity.this);
        spImp = new SpImp(WelcomeActivity.this);
        spCache = new SpCache(WelcomeActivity.this);
        initData();

    }

    private void initData() {
        Log.d("ljcljc","001");
        account = spImp.getYXID();
        String token = spImp.getYXTOKEN();
        if(TextUtils.isEmpty(account)||account.equals("0")){
            Log.d("ljcljc","002");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    if(TextUtils.isEmpty(spImp.getYd())){
                        intent.setClass(WelcomeActivity.this, GuideActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);

        }else {
            Log.d("ljcljc","003");
            LoginInfo info = new LoginInfo(account, token);
//            LoginInfo info = new LoginInfo(account, "1112");
            RequestCallback<LoginInfo> callback =
                    new RequestCallback<LoginInfo>() {

                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            Log.d("ljcljc","004");
                            NimUIKit.loginSuccess(account);
                            NimUIKit.setAccount(account);
                            toToast(WelcomeActivity.this, "登录成功");
                            NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    Log.d("sdasd001",message.getContent());
                                    return false;
                                }
                            });
                            NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    Log.d("sdasd002",message.getContent());
                                    return false;
                                }
                            });
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
                                Log.d("ljcljc","005");
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }

                        @Override
                        public void onFailed(int i) {
                            Log.d("ljcljc","006");
                            Log.d("dsadsda","登录失败："+i);
                            toToast(WelcomeActivity.this, "登录失败");
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
//                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                spImp.setUID("0");
                                spImp.setYXID("0");
                                spImp.setYXTOKEN("0");
                                spCache.clear();
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            toToast(WelcomeActivity.this, "登录出错");
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    };
            NimUIKit.login(info,callback);
//            NIMClient.getService(AuthService.class).login(info)
//                    .setCallback(callback);
        }

    }
}
