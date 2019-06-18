package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;
import android.util.Log;

import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.UploadState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by winnie on 2017/5/25.
 */

public class UploadTotalDataAccessor {
    private static UploadTotalDataAccessor instance;
    private List<VideoItem> uploadList = new ArrayList<>(); // 待上传队列
    private Set<VideoItem> waitAddToServerItems = new HashSet<>(); // 已上传nos成功，等待上传至应用服务器数据

    public static UploadTotalDataAccessor getInstance() {
        if (instance == null) {
            instance = new UploadTotalDataAccessor();
        }
        return instance;
    }

    public List<VideoItem> getUploadList() {
        return uploadList;
    }

    public void addUploadList(List<VideoItem> videoItems) {
        uploadList.addAll(videoItems);
    }

    public boolean isFullySuccess(){
        return uploadList.size() == 0;
    }

    public boolean hasWaitTask() {
        for (int i = 0; i < uploadList.size(); i++) {
            if(uploadList.get(i).getState() == UploadState.STATE_WAIT){
                return true;
            }
        }
        return false;
    }

    public Set<VideoItem> getWaitAddToServerItems() {
        return waitAddToServerItems;
    }

    public void uploadSuccess(VideoItem videoItem) {
        Log.d("asdasdsd:","UploadTotalDataAccessor_uploadSuccess");
        if (videoItem.getState() == UploadState.STATE_UPLOAD_COMPLETE) {
            uploadList.remove(videoItem);
        }
    }

    public void clear() {
        uploadList.clear();
    }
}
