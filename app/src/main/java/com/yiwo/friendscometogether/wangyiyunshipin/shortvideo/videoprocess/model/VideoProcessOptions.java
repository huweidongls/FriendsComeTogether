package com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.videoprocess.model;

import com.netease.transcoding.TranscodingAPI;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.model.MediaCaptureOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxuwen on 2017/4/10.
 */



public class VideoProcessOptions {
    /**
     * 输入文件相关参数
     */
    TranscodingAPI.TranSource source;
    /**
     * 水印相关参数
     */
    List<TranscodingAPI.TranWaterMark> waterMarks;
    /**
     * 动态贴图相关参数
     */
    TranscodingAPI.TranDynamicWater dynamicWater;
    /**
     * 裁剪坐标
     */
    TranscodingAPI.TranCrop crop;
    /**
     * 文件截图，按照时长和offset
     */
    TranscodingAPI.TranTimeCut timeCut;
    /**
     * 混音参数
     */
    TranscodingAPI.TranMixAudio mixAudio;
    /**
     * 转码信息：输出码率，是否硬件编码，回调函数
     */
    TranscodingAPI.TranscodePara transcodePara;
    /**
     * 输出文件地址
     */
    TranscodingAPI.TranOut outputFilePara;

    /**
     * 滤镜调节参数
     */
    TranscodingAPI.TranFilter filter;

    public VideoProcessOptions(MediaCaptureOptions mediaCaptureOptions) {
        this.source = new TranscodingAPI.TranSource();
        this.waterMarks = new ArrayList<>();
        this.dynamicWater = new TranscodingAPI.TranDynamicWater();
        this.crop = new TranscodingAPI.TranCrop();
        this.timeCut = new TranscodingAPI.TranTimeCut();
        this.mixAudio = new TranscodingAPI.TranMixAudio();
        this.transcodePara = new TranscodingAPI.TranscodePara();
        this.outputFilePara = new TranscodingAPI.TranOut();
        this.filter = new TranscodingAPI.TranFilter();

        source.setMergeHeight(mediaCaptureOptions.mVideoPreviewHeight);
        source.setMergeWidth(mediaCaptureOptions.mVideoPreviewWidth);
    }

    public TranscodingAPI.TranSource getSource() {
        return source;
    }

    public void setSource(TranscodingAPI.TranSource source) {
        this.source = source;
    }

    public List<TranscodingAPI.TranWaterMark> getWaterMarks() {
        return waterMarks;
    }

    public void setWaterMarks(List<TranscodingAPI.TranWaterMark> waterMarks) {
        this.waterMarks = waterMarks;
    }

    public TranscodingAPI.TranDynamicWater getDynamicWater() {
        return dynamicWater;
    }

    public void setDynamicWater(TranscodingAPI.TranDynamicWater dynamicWater) {
        this.dynamicWater = dynamicWater;
    }

    public TranscodingAPI.TranCrop getCrop() {
        return crop;
    }

    public void setCrop(TranscodingAPI.TranCrop crop) {
        this.crop = crop;
    }

    public TranscodingAPI.TranTimeCut getTimeCut() {
        return timeCut;
    }

    public void setTimeCut(TranscodingAPI.TranTimeCut timeCut) {
        this.timeCut = timeCut;
    }

    public TranscodingAPI.TranMixAudio getMixAudio() {
        return mixAudio;
    }

    public void setMixAudio(TranscodingAPI.TranMixAudio mixAudio) {
        this.mixAudio = mixAudio;
    }

    public TranscodingAPI.TranscodePara getTranscodePara() {
        return transcodePara;
    }

    public void setTranscodePara(TranscodingAPI.TranscodePara transcodePara) {
        this.transcodePara = transcodePara;
    }

    public TranscodingAPI.TranOut getOutputFilePara() {
        return outputFilePara;
    }

    public void setOutputFilePara(TranscodingAPI.TranOut outputFilePara) {
        this.outputFilePara = outputFilePara;
    }

    public TranscodingAPI.TranFilter getFilter() {
        return filter;
    }

    public void setFilter(TranscodingAPI.TranFilter filter) {
        this.filter = filter;
    }
}
