package com.yiwo.friendscometogether.model;

import java.io.Serializable;

/**
 * Created by ljc on 2019/1/14.
 */

public class SuperLikeSXMode implements Serializable {
    private int sex ;//0为男1为女
    private int address ;//0为同城 1为随便
    private String ages ;// 年龄筛选字符串 格式：10-30
    private int type_expand_search;//自动扩大搜索范围 //0 关闭 ，1 开启
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public int getType_expand_search() {
        return type_expand_search;
    }

    public void setType_expand_search(int type_expand_search) {
        this.type_expand_search = type_expand_search;
    }
}
