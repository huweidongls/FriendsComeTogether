package com.yiwo.friendscometogether.services;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.utils.NougatUntil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/***
 * 更新版本
 */
public class UpdateService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "4655";
    private int notification_id = 96;
    private static final int TIMEOUT = 10 * 1000;// 超时
    private static final int DOWN_OK = 1;
    private static final int DOWN_ERROR = 0;
    private NotificationManager notificationManager;
    // android O以下
//    private RemoteViews contentView;//自定义通知栏样式
    private NotificationCompat.Builder notificationCompat;

    //android O以上
    private NotificationChannel channel;
    private Notification.Builder builder;

    public File updateFile = null;


    private String app_name;
    private String down_url;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        app_name = intent.getStringExtra("app_name");
        down_url = intent.getStringExtra("down_url");
        createFile(app_name);
        createNotification();
        createThread();
        return super.onStartCommand(intent, flags, startId);

    }

    /***
     * 在sd卡根目录下创建文件
     */
    private void createFile(String name) {

        updateFile = new File(MyApplication.genPath + name + ".apk");
//        updateFile = new File(Environment.getExternalStorageDirectory(), app_name+".apk");
        Log.d("LJC_DEBUG",MyApplication.genPath + name + ".apk");
        try {
            if (!updateFile.exists()) {
//                try {
//                    updateFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                updateFile.createNewFile();
            } else {
                updateFile.delete();
                updateFile.createNewFile();
            }
        } catch (IOException e) {

        }
    }



    public void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, app_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID); //与channelId对应
            builder.setSmallIcon(R.mipmap.logo_gray).setNumber(3);
        } else {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationCompat = new NotificationCompat.Builder(getApplicationContext());
            notificationCompat.setAutoCancel(false);
            notificationCompat.setSmallIcon(R.mipmap.logo_gray);
            notificationCompat.setTicker("开始下载");
//            notificationCompat.setContentIntent(pendingIntent);
            notificationManager.notify(notification_id, notificationCompat.build());
        }

        showNotification("正在下载", "0%", 0, View.VISIBLE);
    }

    //更新notification
    private void showNotification(String title, String content, int progress, int visible) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setContentTitle(title)
                    .setContentText(content)
                    .setProgress(100, progress, false).setVisibility(visible);
            notificationManager.notify(notification_id, builder.build());
        } else {
            notificationCompat.setContentTitle(title)
                    .setContentText(content)
                    .setProgress(100, progress, false).setVisibility(visible);
            notificationCompat.setTicker(title);
            notificationManager.notify(notification_id, notificationCompat.build());
        }
    }

    //判断通知是不是偏向黑色背景
    public boolean isDarkNotificationTheme(Context context) {
        return !isSimilarColor(Color.BLACK, getNotificationColor(context));
    }

    /**
     * 获取通知栏颜色
     *
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        int layoutId = notification.contentView.getLayoutId();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title) != null) {
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue);
        if (value < 180.0) {
            return true;
        }
        return false;
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color = Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups = new LinkedList<ViewGroup>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size() > 0) {
            ViewGroup viewGroup1 = viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                } else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor() != -1) {
                        color = ((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }

    /***
     * 开线程下载
     */
    public void createThread() {
        /***
         * 更新UI
         */
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWN_OK:
                        notificationManager.cancel(notification_id);
                        showNotification("下载完成", "", 100, View.GONE);
                        installApk();
                        stopSelf();
                        break;
                    case DOWN_ERROR:
                        showNotification("下载失败", "", 100, View.GONE);
                        break;
                    default:
                        stopSelf();
                        break;
                }
            }

        };

        final Message message = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    long downloadSize = downloadUpdateFile(down_url, updateFile.toString());
                    if (downloadSize > 0) {
                        // 下载成功
                        message.what = DOWN_OK;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = DOWN_ERROR;
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    /***
     * 下载文件
     *
     * @return
     * @throws MalformedURLException
     */
    public long downloadUpdateFile(String down_url, String file) throws Exception {
        int down_step = 2;//每增长2% 更新一次通知
        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int progress = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
//        String url_str = "http://"+down_url;
        Log.d("LJC_Debug","update_url:"+down_url);
        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("下载地址不存在！");
        }
        inputStream = httpURLConnection.getInputStream();
        outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;// 时时获取下载到的大小
            if (progress == 0 || ((((float) downloadCount / totalSize) * 100) - down_step) >= progress) {
                progress += down_step;
                showNotification("正在下载", progress + "%", progress, View.VISIBLE);
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();
        return downloadCount;

    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = updateFile;
        if (!apkfile.exists()) {
            Log.d("LJC_Debug","return_install");
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = NougatUntil.formatFileProviderUri(getApplicationContext(), apkfile);
        i = NougatUntil.formatFileProviderPicIntent(i);
        i.setDataAndType(uri, "application/vnd.android.package-archive");

        this.startActivity(i);
    }
}
