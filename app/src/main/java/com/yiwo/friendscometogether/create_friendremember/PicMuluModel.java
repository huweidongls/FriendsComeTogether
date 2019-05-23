package com.yiwo.friendscometogether.create_friendremember;

import java.util.List;

/**
 * Created by ljc on 2019/4/29.
 */

public class PicMuluModel {
    private String muluName;
    private List<PicBean> list;
    public String getMuluName() {
        return muluName;
    }

    public void setMuluName(String muluName) {
        this.muluName = muluName;
    }

    public List<PicBean> getList() {
        return list;
    }

    public void setList(List<PicBean> list) {
        this.list = list;
    }
}
