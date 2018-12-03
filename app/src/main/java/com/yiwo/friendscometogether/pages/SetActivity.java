package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netease.nim.uikit.api.NimUIKit;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.PopDialog;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SetActivity extends BaseActivity {
    SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(SetActivity.this);
    }

    @OnClick({R.id.activity_set_rl_back, R.id.activity_set_rl_upload, R.id.activity_set_rl_feedback,
            R.id.activity_set_rl_clear_cache, R.id.activity_set_rl_user_agreement, R.id.activity_set_exit_login_bt})
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

                PopDialog cleardialog = new PopDialog(this, "清除缓存", "清除", "取消",
                        new PopDialog.PopDialogListener() {
                            @Override
                            public void sureBtnListener() {
//                            finish();
                                toToast(SetActivity.this, "清除缓存");
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
            case R.id.activity_set_exit_login_bt:
//                toToast(this,"退出登录");
                PopDialog dialog = new PopDialog(this, "提示：为了保证您的信息安全，在注销登录后，应用程序将会关闭。再次使用请手动启动程序。", "确认", "取消",
                        new PopDialog.PopDialogListener() {
                            @Override
                            public void sureBtnListener() {
                                spImp.setUID("0");
                                spImp.setYXID("0");
                                spImp.setYXTOKEN("0");
                                NimUIKit.logout();
                                MyApplication.getInstance().exit();
                            }
                        });
                dialog.show();
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
        }
    }
}
