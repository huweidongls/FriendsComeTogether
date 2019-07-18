package com.yiwo.friendscometogether.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by ljc on 2019/7/17.
 */

public class InsertMediaToSystem {
    //针对非系统影音资源文件夹
    public static void insertIntoMediaStore(Context context, boolean isVideo, File saveFile, long createTime) {
        ContentResolver mContentResolver = context.getContentResolver();
        if (createTime == 0)
            createTime = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        //值一样，但是还是用常量区分对待
        values.put(isVideo ? MediaStore.Video.VideoColumns.DATE_TAKEN
                : MediaStore.Images.ImageColumns.DATE_TAKEN, createTime);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
        if (!isVideo)
            values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
        values.put(MediaStore.MediaColumns.MIME_TYPE, isVideo ? getVideoMimeType(saveFile.getAbsolutePath()): "image/jpeg");
        //插入
        mContentResolver.insert(isVideo
                ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    // 获取video的mine_type,暂时只支持mp4,3gp
    private static String getVideoMimeType(String path) {
        String lowerPath = path.toLowerCase();
        if (lowerPath.endsWith("mp4") || lowerPath.endsWith("mpeg4")) {
            return "video/mp4";
        } else if (lowerPath.endsWith("3gp")) {
            return "video/3gp";
        }
        return "video/mp4";
    }
}
