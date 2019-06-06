package com.yiwo.friendscometogether.wangyiyunshipin.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnie on 2017/7/13.
 */

public class SnapShotHelper {
    private final static String TAG = SnapShotHelper.class.getSimpleName();

    private static SnapShotCallback snapShotCallback;

    private static SnapShotHelper instance;

    public interface SnapShotCallback {
        void onSnapShotSuccess(List<Bitmap> snaps);
    }

    public static SnapShotHelper getInstance() {
        synchronized (SnapShotHelper.class) {
            if (instance == null) {
                instance = new SnapShotHelper();
            }
        }
        return instance;
    }

    /**
     * 视频截图API
     *
     * @param filePath  原始视频地址
     * @param start     开始截图时间（单位统一为 ms）
     * @param interval  时间间隔
     * @param duration  截图持续时间
     * @param outWidth  截图宽
     * @param outHeight 截图高
     */
    public void snapshoot(String filePath, float start, float interval,
                          long duration, int outWidth, int outHeight, SnapShotCallback snapShotCallback) {
        SnapShotTask snapShotTask = new SnapShotTask();
        snapShotTask.execute(filePath, start, interval, duration, outWidth, outHeight);
        this.snapShotCallback = snapShotCallback;
    }

    private static class SnapShotTask extends AsyncTask<Object, Integer, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(Object... params) {
            String filePath = (String) params[0];
            float start = (float) params[1];
            float interval = (float) params[2];
            long duration = (long) params[3];
            int outWidth = (int) params[4];
            int outHeight = (int) params[5];

            List<Bitmap> snaps = new ArrayList<>();
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            try {
                Log.i(TAG, "snapshoot start ");
                long startTime = System.currentTimeMillis();
                metadataRetriever.setDataSource(filePath);
                String time = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                Log.i(TAG, "videoTime: " + time);
                int intTime = Integer.parseInt(time);
                long endTimeUs = duration * 1000;
                if (duration <= 0 || duration > intTime) {
                    Log.w(TAG, "duration <= 0 || duration > videoTime so set duration = " + time);
                    duration = intTime;
                    endTimeUs = duration * 1000;
                }
                if (start >= intTime) {
                    Log.w(TAG, "start >= videoTime so set return");
                    return null;
                }
                long intervalTimeUs = (long) interval * 1000;
                long startTimeUs = (long) start * 1000;
                if (startTimeUs == 0) {
                    startTimeUs = intervalTimeUs;
                }

                Log.i(TAG, "start time:" + startTimeUs + ", endtime:" + endTimeUs);
                for (long i = startTimeUs; i < endTimeUs; i += intervalTimeUs) {
                    Bitmap bitmap = metadataRetriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    Log.i(TAG, "time  " + i);
                    Log.i(TAG, "bitmap---i: " + i / 1000);
                    if (bitmap == null) {
                        continue;
                    }
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if (width > outWidth || height > outHeight) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
                    }
                    snaps.add(bitmap);
                }
                long endTime = System.currentTimeMillis();
                Log.i(TAG, "snapshoot end use time =  " + (endTime - startTime));
                return snaps;

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    metadataRetriever.release();
                } catch (RuntimeException ex) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            snapShotCallback.onSnapShotSuccess(bitmaps);
        }
    }
}
