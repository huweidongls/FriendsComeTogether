package com.yiwo.friendscometogether.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.tencent.sonic.sdk.SonicEngine;
import com.umeng.socialize.UMShareAPI;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.rl_set_return)
    RelativeLayout returnRl;
    @BindView(R.id.headIv)
    ImageView headIv;
    @BindView(R.id.login_phoneEt)
    EditText login_phoneEt;
    @BindView(R.id.login_pwEt)
    EditText login_pwEt;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.login_registerTv)
    TextView login_registerTv;
    @BindView(R.id.login_forgetPwTv)
    TextView login_forgetPwTv;
    @BindView(R.id.tv_XY_TK)
    TextView tv_XY_TK;
    @BindView(R.id.iv_show_pwd)
    ImageView iv_show_pwd;
//    @BindView(R.id.login_wechatIv)
//    ImageView login_wechatIv;
    Context c;
    public SpImp spImp;
    UMShareAPI api;
    private boolean isShowPwd = false;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        c = LoginActivity.this;
        spImp = new SpImp(c);
        api = UMShareAPI.get(this);
    }

    @OnClick({R.id.rl_set_return, R.id.login_btn, R.id.login_registerTv, R.id.login_forgetPwTv,R.id.tv_XY_TK,R.id.tv_TK,R.id.iv_show_pwd})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_return:
                finish();
                break;
            case R.id.login_btn:
                login(login_phoneEt.getText().toString(), login_pwEt.getText().toString());
                break;
            case R.id.login_registerTv:
                Intent it = new Intent(c, RegisterActivity.class);
                startActivity(it);
                finish();
                break;
            case R.id.login_forgetPwTv:
                Intent itf = new Intent(c, ForgetPwActivity.class);
                startActivity(itf);
                break;
            case R.id.tv_XY_TK:
                Intent itA = new Intent(c, UserAgreementActivity.class);
                itA.putExtra("title", "用户协议");
                itA.putExtra("url", NetConfig.userAgreementUrl);
                startActivity(itA);
                break;
            case R.id.tv_TK:
                Intent itTk = new Intent(c, UserAgreementActivity.class);
                itTk.putExtra("title", "隐私政策");
                itTk.putExtra("url", NetConfig.userAgreementUrl1);
                startActivity(itTk);
                break;
            case R.id.iv_show_pwd:
                    if (isShowPwd){
                        isShowPwd = false;
                        iv_show_pwd.setImageResource(R.mipmap.pwd_show_no);
                        login_pwEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                        login_pwEt.setSelection(login_pwEt.getText().length());
                    }else {
                        isShowPwd = true;
                        iv_show_pwd.setImageResource(R.mipmap.pwd_show_yes);
                        login_pwEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        login_pwEt.setSelection(login_pwEt.getText().length());
                    }
                break;
//            case R.id.login_wechatIv:
//                toToast(LoginActivity.this,"调起微信登录失败");
//                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                        toToast(LoginActivity.this,map.toString());
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                        toToast(LoginActivity.this,throwable.toString());
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media, int i) {
//
//                    }
//                });
//                break;
        }
    }

    private void login(String phone, String pwd) {
        if (!StringUtils.isPhoneNumberValid(phone)) {
//        if (false) {
            toToast(this, "请输入正确的手机号");
        } else {
            String token = getToken(NetConfig.BaseUrl + NetConfig.loginUrl);
            ViseHttp.POST(NetConfig.loginUrl)
                    .addParam("app_key", token)
                    .addParam("phone", phone)
                    .addParam("password", pwd)
                    .request(new ACallback<String>() {
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                int code = jsonObject.optInt("code");
                                if (code == 200) {
                                    Log.i("我的UID", jsonObject.optString("obj").toString());
                                    JSONObject js = new JSONObject(jsonObject.optString("obj"));
                                    spImp.setUID(js.optString("uid"));
                                    spImp.setYXID(js.optString("wy_accid"));
                                    spImp.setYXTOKEN(js.optString("token"));
                                    spImp.setWyUpAccid(js.optString("wy_up_accid"));
                                    spImp.setWyUpToken(js.optString("wy_up_token"));
                                    spImp.setIsAdmin(js.getString("isAdmin"));
                                    spImp.setWXUnionID(js.getString("unionid"));
                                    account = js.optString("wy_accid");
                                    //清除vas_sonic缓存
                                    SonicEngine.getInstance().cleanCache();
                                    Intent intent = new Intent();
                                    intent.setAction("android.friendscometogether.HomeFragment.PreLoadWebYouJiBroadcastReceiver");
                                    //发送广播 预加载 web
                                    sendBroadcast(intent);

                                    String token = js.optString("token");
                                    LoginInfo info = new LoginInfo(account, token);
                                    DemoCache.setAccount(account);
                                    RequestCallback<LoginInfo> callback =
                                            new RequestCallback<LoginInfo>() {
                                                @Override
                                                public void onSuccess(LoginInfo loginInfo) {
                                                    NimUIKit.loginSuccess(account);
//                                                    NIMClient.getService(MsgService.class).();
                                                    toToast(LoginActivity.this, "登录成功");

                                                    NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                                                        @Override
                                                        public void onResult(int i, List<RecentContact> recentContacts, Throwable throwable) {
                                                            for (int i1 = 0;i<recentContacts.size();i++){
                                                                NIMClient.getService(MsgService.class).updateRecent(recentContacts.get(i1));
                                                            }
                                                        }
                                                    });
                                                    NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
                                                        @Override
                                                        public boolean shouldIgnore(IMMessage message) {
                                                            return false;
                                                        }
                                                    });

                                                    NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
                                                        @Override
                                                        public boolean shouldIgnore(IMMessage message) {
                                                            return false;
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailed(int i) {
                                                    toToast(LoginActivity.this, "登录失败");
                                                }

                                                @Override
                                                public void onException(Throwable throwable) {
                                                    toToast(LoginActivity.this, "登录出错");
                                                }
                                                // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                                            };
                                    NIMClient.getService(AuthService.class).login(info)
                                            .setCallback(callback);
                                    finish();
                                } else {
                                    toToast(c, jsonObject.optString("message").toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }
    }
}