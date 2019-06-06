package com.yiwo.friendscometogether.wangyiyunshipin.server.entity;

/**
 * Created by zhukkun on 3/8/17.
 */
public class AddVideoResponseEntity {

    int videoCount; //成功上传的数量,根据此字段来告诉客户端还可上传多少个视频
    long vid;
    VideoInfoEntity videoInfoEntity;
    boolean transjobstatus;  //转码状态, 0为转码提交成功, 否则转码提交失败

    public boolean isTransjobstatus() {
        return transjobstatus;
    }

    public void setTransjobstatus(boolean transjobstatus) {
        this.transjobstatus = transjobstatus;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }


    public VideoInfoEntity getVideoInfoEntity() {
        return videoInfoEntity;
    }

    public void setVideoInfoEntity(VideoInfoEntity videoInfoEntity) {
        this.videoInfoEntity = videoInfoEntity;
    }
}
