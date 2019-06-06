package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.VideoUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.VideoGalleryController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhukkun on 2/27/17.
 */
public class VideoGalleryDataAccessor {

    private List<VideoItem> rawVideoItems = new ArrayList<>();
    private List<LineContainer> lineContainerList = new ArrayList<>();
    private List<VideoItem> selectedVideoItems = new ArrayList<>();

    private Cursor videoCursor;
    private int video_column_index;

    private int largest_count = VideoGalleryController.DEFAULT_LARGEST_COUNT;

    public void loadPhoneVideoData(Activity context){
        String[] proj = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.Media.MIME_TYPE
        };

        //videoCursor = new CursorLoader(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null).loadInBackground();

        videoCursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC");
        if(videoCursor == null) return;

        int count = videoCursor.getCount();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < count; i++) {
            VideoItem videoItem = new VideoItem();
            videoItem.setType(UploadType.VIDEO); // 普通视频
            videoCursor.moveToPosition(i);

            String mimeType = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
            if (!TextUtils.isEmpty(mimeType) && (mimeType.equals("video/mp4")
                    || mimeType.equals("video/ext-mp4"))) {

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                videoItem.setId(videoCursor.getLong(video_column_index) + "");
                videoItem.setUriString(Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoItem.getId()).toString());

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
                videoItem.setDisplayName(videoCursor.getString(video_column_index));

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                videoItem.setFilePath(videoCursor.getString(video_column_index));

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION);
                long duration = videoCursor.getLong(video_column_index);
                if (duration > 0) {
                    videoItem.setDuration(VideoUtils.getFormatDuration(duration));
                }

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE);
                videoItem.setSize(videoCursor.getLong(video_column_index));

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.WIDTH);
                videoItem.setWidth(videoCursor.getLong(video_column_index));

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.HEIGHT);
                videoItem.setHeight(videoCursor.getLong(video_column_index));

                video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN);
                calendar.setTimeInMillis(videoCursor.getLong(video_column_index));
                videoItem.setDateTaken(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) < 10 ? "0" + calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH)));
                rawVideoItems.add(videoItem);
            }
        }

        videoCursor.close();
    }

    public List<VideoItem> getRawVideoItems() {
        return rawVideoItems;
    }



    public boolean isSelected(int line, int column) {
        return selectedVideoItems.contains(lineContainerList.get(line).getVideoItems().get(column));
    }

    public void markSelected(int line, int column, boolean select){
        if(select && !isSelected(line, column) && selectedVideoItems.size()< largest_count){
           selectedVideoItems.add(lineContainerList.get(line).getVideoItems().get(column));
        }else if(!select && isSelected(line, column)){
           selectedVideoItems.remove(lineContainerList.get(line).getVideoItems().get(column));
        }
    }

    public VideoItem getSelectedItem(int line, int column) {
        return lineContainerList.get(line).getVideoItems().get(column);
    }

    public int getLargestCount() {
        return largest_count;
    }

    public int getSeletedCount() {
        return selectedVideoItems.size();
    }

    public boolean canSelecteMore() {
        return getSeletedCount()<getLargestCount();
    }

    public ArrayList<VideoItem> getSelectedArray() {
       return (ArrayList<VideoItem>) selectedVideoItems;
    }

    public void setLargestCount(int largest_count) {
        this.largest_count = largest_count;
    }

    public List<LineContainer> getLineContainerList() {
        return lineContainerList;
    }

    public void sortByDate() {
        lineContainerList = new ArrayList<>();

        LineContainer lineContainer = new LineContainer();


        for (int i = 0; i < rawVideoItems.size(); i++) {
            VideoItem videoItem = rawVideoItems.get(i);
            String VideoCreateDate = getCreateDateString(videoItem);

            if(lineContainer.getDate() == null || !lineContainer.getDate().equals(VideoCreateDate)){
                if(i!=0){
                    lineContainerList.add(lineContainer);
                    lineContainer = new LineContainer();
                }

                lineContainer.setDate(VideoCreateDate);
                lineContainer.setDateTip(true);
                lineContainerList.add(lineContainer);

                lineContainer = new LineContainer();
            }

            if(lineContainer.getVideoItems().size()==3){
                lineContainerList.add(lineContainer);
                lineContainer = new LineContainer();
            }
            lineContainer.getVideoItems().add(videoItem);
            lineContainer.setDate(VideoCreateDate);
        }
        lineContainerList.add(lineContainer);
    }

    /**
     *  过滤时长不足的视频
     * @param filterTime 过滤的时长
     */
    public void filterVideoByTime(long filterTime) {
        Iterator<VideoItem> it = rawVideoItems.iterator();
        while(it.hasNext()) {
            VideoItem item = it.next();
            if (TextUtils.isEmpty(item.getDuration()) || TimeUtil.formatTimeToTime(item.getDuration()) < filterTime) {
                it.remove();
            }
        }
    }

    public void filterVideoByResolution(int width, int height) {
        Iterator<VideoItem> it = rawVideoItems.iterator();
        while(it.hasNext()) {
            VideoItem item = it.next();
            if (item.getWidth() < width || item.getHeight() < height) {
                it.remove();
            }
        }
    }

    private String getCreateDateString(VideoItem videoItem) {
        return videoItem.getDateTaken();
    }


    public boolean isMoreThan_1G(int line, int column) {
        return VideoUtils.isSizeMoreThan_1Gb(lineContainerList.get(line).getVideoItems().get(column).getSize());
    }

    public boolean isMoreThan_5G(int line, int column) {
        return VideoUtils.isSizeMoreThan_5Gb(lineContainerList.get(line).getVideoItems().get(column).getSize());
    }
}
