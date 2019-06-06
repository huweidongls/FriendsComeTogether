package com.yiwo.friendscometogether.wangyiyunshipin.upload.controller;

import android.app.Activity;
import android.content.Intent;


import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.LineContainer;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoGalleryDataAccessor;

import java.util.List;

/**
 * Created by zhukkun on 2/27/17.
 */
public class VideoGalleryController extends BaseUiController<VideoGalleryController.VideoSelectUi>{

    public static final String EXTRAL_LARGEST_COUNT = "largest_count";
    public static final String EXTRA_FILTER_TIME = "filter_time";
    public static final int DEFAULT_LARGEST_COUNT = 5;

    private int largest_count = DEFAULT_LARGEST_COUNT;
    private int selected_count = 0;

    VideoGalleryDataAccessor dataAccesser;
    private Activity context;

    public VideoGalleryController(Activity context){
        this.context = context;
        dataAccesser = new VideoGalleryDataAccessor();
    }

    @Override
    public void handleIntent(Intent intent) {
        largest_count = intent.getIntExtra(EXTRAL_LARGEST_COUNT, DEFAULT_LARGEST_COUNT);
        dataAccesser.setLargestCount(largest_count);

        dataAccesser.loadPhoneVideoData(context);
        long time = intent.getLongExtra(EXTRA_FILTER_TIME, -1);
        if (time != -1) {
            dataAccesser.filterVideoByTime(time);
        }
        dataAccesser.sortByDate();
    }

    @Override
    protected void onUiDetached(VideoSelectUi ui) {

    }

    @Override
    protected void populateUi(VideoSelectUi ui) {
        ui.populateVideoData(dataAccesser.getLineContainerList());
        ui.initBottomBar(selected_count, largest_count);
    }

    @Override
    protected void onUiAttached(VideoSelectUi ui) {
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onSuspend() {

    }

    public interface VideoSelectUi extends Ui{

        void populateVideoData(List<LineContainer> datas);

        void initBottomBar(int selected_count, int largest_count);
    }

    public VideoGalleryDataAccessor getDataAccessor(){
        return dataAccesser;
    }

}
