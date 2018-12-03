package com.yiwo.friendscometogether.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
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
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.rl_set_return)
    RelativeLayout returnRl;
    @BindView(R.id.headIv)
    ImageView headIv;
    @BindView(R.id.getCode_btn)
    Button getCode_btn;
    @BindView(R.id.register_phoneEt)
    EditText register_phoneEt;
    @BindView(R.id.register_pwEt)
    EditText register_pwEt;
    @BindView(R.id.register_confirmPwEt)
    EditText register_confirmPwEt;
    @BindView(R.id.register_codeEt)
    EditText register_codeEt;
    @BindView(R.id.register_btn)
    Button register_btn;
    private Unbinder unbinder;
    String codeID = "";
    Context c;
    public Button getCode_btn(){
        return getCode_btn;
    }
    public SpImp spImp;
    boolean state = true;

    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        c = RegisterActivity.this;
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        MyApplication.timecount
                .setActivity(RegisterActivity.this);
        spImp = new SpImp(c);
    }
    @OnClick({R.id.getCode_btn,R.id.register_btn,R.id.rl_set_return})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_set_return:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.getCode_btn:
                getCode(register_phoneEt.getText().toString());
                break;
            case R.id.register_btn:
                register(register_phoneEt.getText().toString(),register_pwEt.getText().toString(),
                        register_confirmPwEt.getText().toString(),register_codeEt.getText().toString());
                break;
        }
    }
    public void getCode(String phone) {
        if (!StringUtils.isPhoneNumberValid(phone)) {
            toToast(c, "请输入正确的手机号");
        } else {
            MyApplication.timecount.start();
            String token = getToken(NetConfig.BaseUrl + NetConfig.getCodeUrl);
            ViseHttp.POST(NetConfig.getCodeUrl)
                    .addParam("app_key", token)
                    .addParam("phone", phone)
                    .request(new ACallback<String>() {
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                int code = jsonObject.optInt("code");
                                if (code == 200) {
                                    String a = jsonObject.optString("obj");
                                    JSONObject js = new JSONObject(a);
                                    codeID = js.optString("codeID");
                                    Log.i("我的codeID", js.optString("codeID").toString());
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
    public void register(String phone, String pwd, String cpwd, String code) {
        String token = getToken(NetConfig.BaseUrl + NetConfig.registerUrl);
        if (!StringUtils.isPhoneNumberValid(phone)) {
            toToast(c, "请输入正确的手机号");
        } else if (code.length() != 6) {
            toToast(c, "请输入正确的验证码");
        } else if (StringUtils.isEmpty(pwd) || !pwd.equals(cpwd)) {
            toToast(c, "两次输入的密码不一致");
        } else {
            ViseHttp.POST(NetConfig.registerUrl)
                    .addParam("app_key", token)
                    .addParam("phone", phone)
                    .addParam("CodeId", codeID)
                    .addParam("password", pwd)
//                    .addParams("confirm_password",cpwd)
                    .addParam("code", code)
                    .request(new ACallback<String>() {
                        @Override
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
                                    account = js.optString("wy_accid");
                                    String token = js.optString("token");
                                    LoginInfo info = new LoginInfo(account, token);
                                    RequestCallback<LoginInfo> callback =
                                            new RequestCallback<LoginInfo>() {
                                                @Override
                                                public void onSuccess(LoginInfo loginInfo) {
                                                    NimUIKit.loginSuccess(account);
                                                    toToast(RegisterActivity.this, "登录成功");
                                                    finish();
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
                                                    toToast(RegisterActivity.this, "登录失败");
                                                }

                                                @Override
                                                public void onException(Throwable throwable) {
                                                    toToast(RegisterActivity.this, "登录出错");
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
