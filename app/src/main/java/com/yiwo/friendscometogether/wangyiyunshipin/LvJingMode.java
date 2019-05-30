package com.yiwo.friendscometogether.wangyiyunshipin;

import com.netease.vcloud.video.effect.VideoEffect;

/**
 * Created by ljc on 2019/5/30.
 */

public class LvJingMode {
    private String name;
    private VideoEffect.FilterType type;
    public LvJingMode(String name, VideoEffect.FilterType type){
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VideoEffect.FilterType getType() {
        return type;
    }

    public void setType(VideoEffect.FilterType type) {
        this.type = type;
    }
}
