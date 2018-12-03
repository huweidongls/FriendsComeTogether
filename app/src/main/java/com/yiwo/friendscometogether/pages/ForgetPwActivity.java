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

public class ForgetPwActivity extends BaseActivity {
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.rl_set_return)
    RelativeLayout returnRl;
    @BindView(R.id.headIv)
    ImageView headIv;
    @BindView(R.id.getCode_btn)
    Button getCode_btn;
    @BindView(R.id.forgetPw_phoneEt)
    EditText forgetPw_phoneEt;
    @BindView(R.id.forgetPw_pwEt)
    EditText forgetPw_pwEt;
    @BindView(R.id.forgetPw_confirmPwEt)
    EditText forgetPw_confirmPwEt;
    @BindView(R.id.forgetPw_codeEt)
    EditText forgetPw_codeEt;
    @BindView(R.id.forgetPw_btn)
    Button forgetPw_btn;
    private Unbinder unbinder;
    String codeID = "";
    Context c;

    public Button getCode_btn() {
        return getCode_btn;
    }

    public SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);
        c = ForgetPwActivity.this;
        unbinder = ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        MyApplication.ftptimecount
                .setActivity(ForgetPwActivity.this);
        spImp = new SpImp(c);
    }

    @OnClick({R.id.getCode_btn, R.id.forgetPw_btn, R.id.rl_set_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_return:
                finish();
                break;
            case R.id.getCode_btn:
                getCode(forgetPw_phoneEt.getText().toString());
                break;
            case R.id.forgetPw_btn:
                forgetPw(forgetPw_phoneEt.getText().toString(), forgetPw_pwEt.getText().toString(),
                        forgetPw_confirmPwEt.getText().toString(), forgetPw_codeEt.getText().toString());
                break;
        }
    }

    public void getCode(String phone) {
        if (!StringUtils.isPhoneNumberValid(phone)) {
            toToast(c, "请输入正确的手机号");
        } else {
            MyApplication.ftptimecount.start();
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

    public void forgetPw(String phone, String pwd, String cpwd, String code) {
        String token = getToken(NetConfig.BaseUrl + NetConfig.forgetPwUrl);
        if (!StringUtils.isPhoneNumberValid(phone)) {
            toToast(c, "请输入正确的手机号");
        } else if (code.length() != 6) {
            toToast(c, "请输入正确的验证码");
        } else if (StringUtils.isEmpty(pwd) || !pwd.equals(cpwd)) {
            toToast(c, "两次输入的密码不一致");
        } else {
            ViseHttp.POST(NetConfig.forgetPwUrl)
                    .addParam("app_key", token)
                    .addParam("phone", phone)
                    .addParam("CodeId", codeID)
                    .addParam("password", pwd)
                    .addParam("Confirmpassword",cpwd)
                    .addParam("code", code)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                int code = jsonObject.optInt("code");
                                if (code == 200) {
                                    toToast(c, jsonObject.optString("message").toString());
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
