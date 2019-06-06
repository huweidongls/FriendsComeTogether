package com.yiwo.friendscometogether.wangyiyunshipin.upload;

import android.content.Context;
import android.text.ClipboardManager;
import android.widget.Toast;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;

/**
 * Created by zhukkun on 3/13/17.
 */
public class VideoUtils {

    public static final long _1KB = 1024;
    public static final long _1MB = 1024 * _1KB;
    public static final long _1GB = 1024 * _1MB;


    public static void shareUrl(String url) {
        try {
            Context context = DemoCache.getContext();
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(url);
            Toast.makeText(context,"视频地址已复制, 请到播放器中打开, 或分享给好友", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getFormateSize(long size) {

        if(size > _1GB){
            return String.format("%.2f", ((double)size)/_1GB )+ "GB";
        }else if (size > _1MB){
            return String.format("%.2f", ((double)size)/_1MB ) + "MB";
        }else if (size > _1KB){
            return String.format("%.2f", ((double)size)/_1KB ) + "KB";
        }else {
            return ((double)size) + "B";
        }
    }

    public static String getFormatDuration(long duration){
        int _1Min = 60;
        long seconds = duration / 1000;
        return String.format("%02d:%02d", seconds/_1Min ,seconds%_1Min);
    }

    public static String getVideoFormate(String url){
        int pos = url.lastIndexOf(".");
        return url.substring(pos + 1).toUpperCase();
    }

    public static boolean isSizeMoreThan_1Gb(long size){
        return size >= _1GB;
    }

    public static boolean isSizeMoreThan_5Gb(long size){
        return size >= 5 * _1GB;
    }
}
