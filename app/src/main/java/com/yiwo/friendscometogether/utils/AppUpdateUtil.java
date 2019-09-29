package com.yiwo.friendscometogether.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.model.VersonModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.services.UpdateService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by Administrator on 2018/1/11.
 */

public class AppUpdateUtil {
    static ProgressDialog mProDialog = null;
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static void checkUpdate(final Context context, final boolean auto) {
        if (!isNetworkConnected(context))
        {
            return;
        }
        if(!auto) {
            mProDialog = ProgressDialog.show(context, null,"正在检查版本，请稍后...", false, true);
        }
        ViseHttp.POST(NetConfig.checkVersion)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.checkVersion))
                .request(new ACallback<String>() {

                    @Override
                    public void onSuccess(String data) {
                        Log.d("VERSONVERSON:data:",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                final VersonModel versonModel = gson.fromJson(data,VersonModel.class);
                                if(parseCode(versonModel.getObj().getAn_version(), String.valueOf(getVersionCode(context)))) {
                                    Dialog alertDialog = new AlertDialog.Builder(context).
                                            setTitle("提示").setMessage("检测到有新版本")
                                            .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
//                                                    //下载路径，如果路径无效了，可换成你的下载路径
//                                                    String url = versonModel.getObj().getAn_download();
//                                                    //创建下载任务,downloadUrl就是下载链接
//                                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                                                    //指定下载路径和下载文件名
//                                                    request.setDestinationInExternalPublicDir("", url.substring(url.lastIndexOf("/") + 1));
//                                                    //获取下载管理器
//                                                    DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                                                    //将下载任务加入下载队列，否则不会进行下载
//                                                    downloadManager.enqueue(request);
                                                    String app_name = "tongban_app";
                                                    Intent down = new Intent(context, UpdateService.class);
                                                    down.putExtra("down_url", versonModel.getObj().getAn_download());
                                                    down.putExtra("app_name", app_name);
                                                    context.startService(down);
                                                }
                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).create();
                                    alertDialog.show();
                                } else {
                                    if(!auto) {
                                        new AlertDialog.Builder(context).
                                                setTitle("提示").setMessage("当前已经是最新版本")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!auto)
                        mProDialog.dismiss();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        if(!auto)
                        mProDialog.dismiss();
                        Toast.makeText(context,errMsg,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //服务端版本号和本地版本号做比较
    private static boolean parseCode(String serverVersionCode, String localVersionCode) {
        Log.d("VERSONVERSON:ser:",serverVersionCode+"///loc"+localVersionCode);
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
