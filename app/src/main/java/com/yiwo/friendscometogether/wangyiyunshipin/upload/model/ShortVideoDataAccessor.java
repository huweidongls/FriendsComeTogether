package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnie on 2017/5/25.
 */

public class ShortVideoDataAccessor {
    List<VideoItem> localItemList = new ArrayList<>(); // 本地队列

    public void addLocalItemList(List<VideoItem> videoItems, boolean needSave) {
        localItemList.addAll(videoItems);
        for (VideoItem videoItem : videoItems) {
            //由于产品定义 同一个文件可以反复上传,故使用时间戳来重设ID,用于区分不同的视频文件
            if (!videoItem.getId().startsWith("local")) {
                videoItem.setId("local" + videoItem.getId() + System.currentTimeMillis());
            }
            if (needSave) {
                UploadDbHelper.saveToDb(videoItem);
            }
        }
    }

    public void clear() {
        localItemList.clear();
    }
}
