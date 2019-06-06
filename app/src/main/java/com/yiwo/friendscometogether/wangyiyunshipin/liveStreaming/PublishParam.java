package com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming;

import java.io.Serializable;

/**
 * Created by zhukkun on 12/20/16.
 */
public class PublishParam implements Serializable {
    public String pushUrl = null;
    public String definition = "SD"; // HD: 高清   SD: 标清    LD: 流畅
    public boolean useFilter = true; //是否开启滤镜
    public boolean faceBeauty = false; //是否开启美颜
    public boolean openVideo = true;
    public boolean openAudio = true;
}
