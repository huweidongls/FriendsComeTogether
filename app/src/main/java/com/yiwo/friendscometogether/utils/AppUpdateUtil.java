package com.yiwo.friendscometogether.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/1/11.
 */

public class AppUpdateUtil {
    static ProgressDialog mProDialog = null;
    public static void checkUpdate(final Context context, final boolean auto) {

//        AppAction.getInstance().postUpdateApp(context, new HttpResponseHandler() {
//
//            @Override
//            public void onResponeseStart() {
//                super.onResponeseStart();
//                if(!auto) {
//                    mProDialog = ProgressDialog.show(context, null,"正在检查版本，请稍后...", false, true);
//                }
//            }
//
//            @Override
//            public void onResponeseSucess(int code, BaseJsonEntity baseJson) {
//
//                final AppVersionEntity versionEntity = FastJsonUtils.parseObject(baseJson.getObj(), AppVersionEntity.class);
//                if(parseCode(versionEntity.getAndroid_version(), String.valueOf(getVersionCode(context)))) {
//                    Dialog alertDialog = new AlertDialog.Builder(context).
//                            setTitle("提示").setMessage("检测到有新版本")
//                            .setPositiveButton("下载", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String app_name = "yiwo-person";
//                                    Intent down = new Intent(context, UpdateService.class);
//                                    down.putExtra("down_url", versionEntity.getAndroid_download());
//                                    down.putExtra("app_name", app_name);
//                                    context.startService(down);
//                                }
//                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).create();
//                    alertDialog.show();
//                } else {
//                    if(!auto) {
//                        new AlertDialog.Builder(context).
//                                setTitle("提示").setMessage("当前已经是最新版本")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onResponesefinish() {
//                super.onResponesefinish();
//                if(!auto) {
//                    mProDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onResponeseFail(BaseJsonEntity baseJson) {
//                super.onResponeseFail(baseJson);
//            }
//        });
    }

    //服务端版本号和本地版本号做比较
    private static boolean parseCode(String serverVersionCode, String localVersionCode) {
        int tempLength = serverVersionCode.length() - localVersionCode.length();
        String tempLongString = tempLength > 0 ? serverVersionCode : localVersionCode;
        String tempShortString = tempLength > 0 ? localVersionCode : serverVersionCode;
        for(int i = 0; i < Math.abs(tempLength); i++) {
            tempShortString += "0";
        }
        boolean result = false;
        if(tempLength > 0) {
            result = Integer.parseInt(tempLongString) > Integer.parseInt(tempShortString) ? true : false;
        } else {
            result = Integer.parseInt(tempShortString) > Integer.parseInt(tempLongString) ? true : false;
        }

        return result;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
