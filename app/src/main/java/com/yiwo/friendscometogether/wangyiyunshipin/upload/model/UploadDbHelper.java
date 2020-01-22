package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhukkun on 3/10/17.
 */
public class UploadDbHelper extends SQLiteOpenHelper {

    public static final String TAG = "upload db";

    private static UploadDbHelper helper = new UploadDbHelper(DemoCache.getContext());
    private static final int DATABASE_VERSION = 3;//2020.01.14 版本3
    private static final String UPLOAD_TABLE_NAME = "video_upload";

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ID = "id";
    private static final String KEY_URI = "uriString";
    private static final String KEY_FILE_PATH = "file_path";
    private static final String KEY_FILE_NAME = "file_name";
    private static final String KEY_CONTEXT = "upload_context";
    private static final String KEY_TOKEN = "nos_token";
    private static final String KEY_BUCKET = "nos_bucket";
    private static final String KEY_OBJECT = "nos_object";
    private static final String KEY_VID = "vid";
    private static final String KEY_STATE = "state";
    private static final String KEY_TYPE = "type";

    private static final String KEY_FABU_NAME = "fabu_name";
    private static final String KEY_ADDRESS = "video_address";

    private static final String UPLOAD_TABLE_CREATE =
            "CREATE TABLE " + UPLOAD_TABLE_NAME + " (" +
                    KEY_USER_ID + " TEXT, " +
                    KEY_ID + " TEXT, " +
                    KEY_URI + " TEXT, " +
                    KEY_FILE_PATH + " TEXT, " +
                    KEY_FILE_NAME + " TEXT, " +

                    KEY_FABU_NAME +" TEXT, " +
                    KEY_ADDRESS +" TEXT, " +

                    KEY_CONTEXT + " TEXT, " +
                    KEY_TOKEN + " TEXT, " +
                    KEY_BUCKET + " TEXT, " +
                    KEY_OBJECT + " TEXT, " +
                    KEY_VID + " INTEGER(16), " +
                    KEY_STATE + " INTEGER, " +
                    KEY_TYPE + " INTEGER);";

    public UploadDbHelper(Context context) {
        super(context, UPLOAD_TABLE_NAME + ".db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UPLOAD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2){
            db.execSQL("alter table video_upload add column type integer");
        }
        if (oldVersion<3){
            String sql = "Alter table "+UPLOAD_TABLE_NAME+" add column "+KEY_FABU_NAME+" TEXT ";
            db.execSQL(sql);
            String sql2 = "Alter table "+UPLOAD_TABLE_NAME+" add column "+KEY_ADDRESS+" TEXT ";
            db.execSQL(sql2);
        }
    }


    public static void saveToDb(VideoItem videoItem) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String insertSql = "INSERT INTO " + UPLOAD_TABLE_NAME + " VALUES ('" +
                DemoCache.getAccount() + "', '" +
                videoItem.getId() + "', '" +
                videoItem.getUriString() + "', '" +
                videoItem.getFilePath() + "', '" +
                videoItem.getDisplayName() + "', '" +
                videoItem.getVideoFaBuName() + "', '" +
                videoItem.getVideoAddress() + "', '" +
                videoItem.getUploadContext() + "', '" +
                videoItem.getUploadToken() + "', '" +
                videoItem.getUploadBucket() + "', '" +
                videoItem.getUploadObject() + "', '" +
                videoItem.getVid() + "', '" +
                videoItem.getState() + "', '" +
                videoItem.getType() + "') ";
        database.execSQL(insertSql);
        database.close();
        Log.d(TAG, "saveItem " + videoItem.getId());
    }

    public static void updateToDb(VideoItem videoItem) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String updateSql = "UPDATE " + UPLOAD_TABLE_NAME + " SET " +
                KEY_CONTEXT + " = '" + videoItem.getUploadContext() + "', " +
                KEY_TOKEN + " = '" + videoItem.getUploadToken() + "', " +
                KEY_BUCKET + " = '" + videoItem.getUploadBucket() + "', " +
                KEY_OBJECT + " = '" + videoItem.getUploadObject() + "', " +
                KEY_VID + " = '" + videoItem.getVid() + "', " +
                KEY_STATE + " = '" + videoItem.getState() + "' WHERE " +
                KEY_ID + " =? and " + KEY_USER_ID + " =?";
        database.execSQL(updateSql, new String[]{videoItem.getId(), DemoCache.getAccount()});
        database.close();

        Log.d(TAG, "updateItem " + videoItem.getId());
    }

    public static void removeItemFromDb(VideoItem videoItem) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String deleteSql = "DELETE FROM " + UPLOAD_TABLE_NAME + " WHERE " +
                KEY_ID + " =? and " + KEY_USER_ID + " =?";
        database.execSQL(deleteSql, new String[]{videoItem.getId(), DemoCache.getAccount()});
        database.close();

        Log.d(TAG, "removeItem " + videoItem.getId());
    }

    public static List<VideoItem> queryUploadListFromDbByType(int type) {
        List<VideoItem> videoItems = new ArrayList<>();

        SQLiteDatabase database = helper.getReadableDatabase();
        String querySql = "SELECT * FROM " + UPLOAD_TABLE_NAME + " WHERE " +
                KEY_USER_ID + " =? and " + KEY_TYPE + " =? and "+ KEY_FABU_NAME +" <>? and "+ KEY_ADDRESS + " <>? ";
        Cursor cursor = database.rawQuery(querySql, new String[]{DemoCache.getAccount(), String.valueOf(type),"",""});
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            VideoItem videoItem = new VideoItem();
            videoItem.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            videoItem.setUriString(cursor.getString(cursor.getColumnIndex(KEY_URI)));
            videoItem.setFilePath(cursor.getString(cursor.getColumnIndex(KEY_FILE_PATH)));
            videoItem.setDisplayName(cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
            videoItem.setVideoFaBuName(cursor.getString(cursor.getColumnIndex(KEY_FABU_NAME)));
            videoItem.setVideoAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            videoItem.setUploadContext(!cursor.getString(cursor.getColumnIndex(KEY_CONTEXT)).equals("null") ? cursor.getString(cursor.getColumnIndex(KEY_CONTEXT)) : null);
            videoItem.setUploadToken(!cursor.getString(cursor.getColumnIndex(KEY_TOKEN)).equals("null") ? cursor.getString(cursor.getColumnIndex(KEY_TOKEN)) : null);
            videoItem.setUploadBucket(!cursor.getString(cursor.getColumnIndex(KEY_BUCKET)).equals("null") ? cursor.getString(cursor.getColumnIndex(KEY_BUCKET)) : null);
            videoItem.setUploadObject(!cursor.getString(cursor.getColumnIndex(KEY_OBJECT)).equals("null") ? cursor.getString(cursor.getColumnIndex(KEY_OBJECT)) : null);
            videoItem.setVid(cursor.getInt(cursor.getColumnIndex(KEY_VID)));
            videoItem.setState(cursor.getInt(cursor.getColumnIndex(KEY_STATE)));
            videoItem.setType(cursor.getInt(cursor.getColumnIndex(KEY_TYPE)));

            videoItems.add(videoItem);
        }
        cursor.close();
        database.close();
        return videoItems;
    }
}
