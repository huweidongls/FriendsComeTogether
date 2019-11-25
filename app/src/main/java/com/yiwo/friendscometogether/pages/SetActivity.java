package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.tencent.sonic.sdk.SonicEngine;
import com.vise.xsnow.cache.SpCache;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.PopDialog;
import com.yiwo.friendscometogether.custom.PopDialog1;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SetActivity extends BaseActivity {
    private SpImp spImp;
    private SpCache spCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(SetActivity.this);
        spCache = new SpCache(SetActivity.this);
    }

    @OnClick({R.id.activity_set_rl_back, R.id.activity_set_rl_upload, R.id.activity_set_rl_feedback,
            R.id.activity_set_rl_clear_cache, R.id.activity_set_rl_user_agreement, R.id.activity_set_rl_user_agreement_1,
            R.id.activity_set_exit_login_bt,R.id.activity_set_rl_check_verson})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_set_rl_back:
                finish();
                break;
            case R.id.activity_set_rl_upload:
//                toToast(this,"检查更新");
//                startActivity(new Intent(SetActivity.this, UpdateActivity.class));
                break;
            case R.id.activity_set_rl_feedback:
//                toToast(this,"意见反馈");
                startActivity(new Intent(SetActivity.this, FeedbackActivity.class));
                break;
            case R.id.activity_set_rl_clear_cache:

//                PopDialog cleardialog = new PopDialog(this, "清除缓存", "清除", "取消",
//                        new PopDialog.PopDialogListener() {
//                            @Override
//                            public void sureBtnListener() {
////                            finish();
//                                toToast(SetActivity.this, "清除缓存");
//                            }
//                        });
//                cleardialog.show();
                PopDialog1 cleardialog = new PopDialog1(this, "清除缓存","缓存可让浏览流畅，确定要清除吗？","算了", "清除",
                        new PopDialog1.PopDialogListener() {

                            @Override
                            public void sureBtnListen() {
                                toToast(SetActivity.this,"清除缓存");
                            }
                        });
                cleardialog.show();
                break;
            case R.id.activity_set_rl_user_agreement:
//                toToast(this,"用户协议");
                Intent it = new Intent(SetActivity.this, UserAgreementActivity.class);
                it.putExtra("title", "用户协议");
                it.putExtra("url", NetConfig.userAgreementUrl);
                startActivity(it);
                break;
            case R.id.activity_set_rl_user_agreement_1:
                Intent itTk = new Intent(SetActivity.this, UserAgreementActivity.class);
                itTk.putExtra("title", "隐私政策");
                itTk.putExtra("url", NetConfig.userAgreementUrl1);
                startActivity(itTk);
                break;
            case R.id.activity_set_exit_login_bt:
//                toToast(this,"退出登录");
//                PopDialog dialog = new PopDialog(this, "提示：为了保证您的信息安全，在注销登录后，应用程序将会关闭。再次使用请手动启动程序。", "确认", "取消",
//                        new PopDialog.PopDialogListener() {
//                            @Override
//                            public void sureBtnListener() {
//                                spImp.setUID("0");
//                                spImp.setYXID("0");
//                                spImp.setYXTOKEN("0");
//                                NimUIKit.logout();
//                                MyApplication.getInstance().exit();
//                            }
//                        });
//                dialog.show();
                PopDialog1 dialog1 = new PopDialog1(this, "提示","为了保证您的信息安全，在注销登录后，应用程序将会关闭。再次使用请手动启动程序。", "取消", "确认",
                        new PopDialog1.PopDialogListener() {

                            @Override
                            public void sureBtnListen() {
                                spImp.setUID("0");
                                spImp.setYXID("0");
                                spImp.setYXTOKEN("0");
                                spImp.setIsAdmin("0");
                                spImp.setWyUpAccid("");
                                spImp.setWyUpToken("");
                                spCache.clear();
                                NimUIKit.logout();
                                //清除vas_sonic缓存
                                SonicEngine.getInstance().cleanCache();
//                                Intent intent = new Intent();
//                                intent.setAction("android.friendscometogether.HomeFragment.PreLoadWebYouJiBroadcastReceiver");
//                                //发送广播 预加载 web
//                                sendBroadcast(intent);
                                MyApplication.getInstance().exit();
                                NIMClient.getService(AuthService.class).logout();
                            }
                        });
                dialog1.show();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("退出登录")
//                        .setMessage("是否退出登录")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                spImp.setUID("0");
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create().show();

                break;
                case R.id.activity_set_rl_check_verson:
                    AppUpdateUtil.checkUpdate(SetActivity.this,false);
                break;
        }
    }
}
