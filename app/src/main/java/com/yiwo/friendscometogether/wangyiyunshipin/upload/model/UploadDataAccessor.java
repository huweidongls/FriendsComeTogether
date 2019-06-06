package com.yiwo.friendscometogether.wangyiyunshipin.upload.model;



import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.VideoInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.TranscodeType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadService;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhukkun on 2/22/17.
 */
public class UploadDataAccessor {

    /**
     * 从服务端拉取的数据
     */
    List<VideoItem> cloudItemList = new ArrayList<>();
    /**
     * 本地数据
     * 注意: 当某项上传成功后,再次拉取全部云端数据时,会将该项从localItems中移除,在cloudItems中添加
     */
    List<VideoItem> localItemList = new ArrayList<>();

    /**
     * 转码中的数据列表, 用于轮询转码状态
     */
    List<VideoItem> transCodingItems = new ArrayList<>();

    /**
     * 获取 云端数据 + 本地数据
     *
     * @return 全部的列表数据, 可用于展示
     */
    public List<VideoItem> getTotalData() {
        List<VideoItem> total = new ArrayList<>(cloudItemList);
        total.addAll(localItemList);
        return total;
    }

    public int getTotalCount() {
        return cloudItemList.size() + localItemList.size();
    }

    public void addLocalVideoItems(List<VideoItem> videoItems, boolean needSave) {
        this.localItemList.addAll(videoItems);
        init(videoItems, true, needSave);
    }

    public void setCloudVideoItems(List<VideoItem> videoItems) {
        //支持多端登录,防止在本端查询转码的视频已在他端删除, 故重新拉取时,移除转码查询列表中的数据
//        this.transCodingItems.removeAll(cloudItemList);
        cloudItemList.addAll(videoItems);
        init(videoItems, false, false);
    }

    public List<VideoItem> getCloudItemList() {
        return cloudItemList;
    }

    public List<VideoItem> getLocalItemList() {
        return localItemList;
    }

    public void clear() {
        localItemList.clear();
        cloudItemList.clear();
    }

    /**
     * @param videoItems
     * @param isLocal    是否为本地数据
     */
    public void init(List<VideoItem> videoItems, boolean isLocal, boolean needSave) {

        for (VideoItem videoItem : videoItems) {

            if (!isLocal) {
                //VideoInfoEntity 为上传成功后的项 数据从云端拉取
                VideoInfoEntity entity = videoItem.getEntity();
                switch (entity.getStatus()) {
                    case TranscodeType.TRANSCODE_FAILED:
                        videoItem.setState(UploadService.STATE_TRANSCODE_FAIL);
                        break;
                    case TranscodeType.TRANSCODING:
                        videoItem.setState(UploadService.STATE_TRANSCODEING);
                        break;
                    case TranscodeType.TRASNCODE_SUCCESS:
                        videoItem.setState(UploadService.STATE_TRANSCODE_COMPLETE);
                        break;
                }
                videoItem.setVid(entity.getVid());
                videoItem.setId("remote" + entity.getVid());
            } else if (!videoItem.getId().startsWith("local")) {
                //由于产品定义 同一个文件可以反复上传,故使用时间戳来重设ID,用于区分不同的视频文件
                videoItem.setId("local" + videoItem.getId() + System.currentTimeMillis());
                if (needSave) {
                    UploadDbHelper.saveToDb(videoItem);
                }
            }
        }
    }

    public void addTransCodingList(VideoItem videoItem) {
        if (videoItem.getState() == UploadService.STATE_TRANSCODEING) {
            transCodingItems.add(videoItem);
        }
    }

    public List<Long> getTranscodingVidList() {
        List<Long> vidList = new ArrayList<>();

        for (VideoItem items : transCodingItems) {
            vidList.add(items.getVid());
        }
        return vidList;
    }

    public List<VideoItem> getTranscodingItems() {
        return transCodingItems;
    }


    public VideoItem removeTranscodeItemByVid(long vid) {
        for (int i = 0; i < transCodingItems.size(); i++) {
            if (vid == transCodingItems.get(i).getVid()) {
                return transCodingItems.remove(i);
            }
        }
        return null;
    }

    public void removeItem(VideoItem videoItem) {

        localItemList.remove(videoItem);
        cloudItemList.remove(videoItem);

        if (videoItem.getVid() != 0) {
            //尝试从转码查询队列中移除
            removeTranscodeItemByVid(videoItem.getVid());
        }

        if (videoItem.getId().startsWith("local")) {
            switch (videoItem.getState()) {
                case UploadService.STATE_UPLOAD_COMPLETE:
                case UploadService.STATE_TRANSCODEING:
                case UploadService.STATE_TRANSCODE_COMPLETE:
                case UploadService.STATE_TRANSCODE_FAIL:
//                    sucNumber--;
                    break;
            }
            UploadDbHelper.removeItemFromDb(videoItem);
        }
    }

    public void localAddToCloud(VideoItem videoItem) {
        localItemList.remove(videoItem);
        cloudItemList.add(videoItem);
    }
}
