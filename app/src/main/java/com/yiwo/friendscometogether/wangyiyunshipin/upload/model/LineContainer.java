package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnie on 2017/6/14.
 */

public class LineContainer {
    boolean isDateTip;
    String Date;
    List<VideoItem> videoItems;

    public LineContainer(){
        videoItems = new ArrayList<>();
    }

    public boolean isDateTip() {
        return isDateTip;
    }

    public void setDateTip(boolean dateTip) {
        isDateTip = dateTip;
    }

    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
