package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;

/**
 * Created by ljc on 2019/3/29.
 */

public class HuoDongShaiXuanMode implements Serializable {
    private String shangJiaName;
    private String shangJiaTuiJian;
    private String biaoQian;
    private String jiaGe;
    private String city;
    private String keyWord;
    public String getShangJiaName() {
        return shangJiaName;
    }

    public void setShangJiaName(String shangJiaName) {
        this.shangJiaName = shangJiaName;
    }

    public String getShangJiaTuiJian() {
        return shangJiaTuiJian;
    }

    public void setShangJiaTuiJian(String shangJiaTuiJian) {
        this.shangJiaTuiJian = shangJiaTuiJian;
    }

    public String getBiaoQian() {
        return biaoQian;
    }

    public void setBiaoQian(String biaoQian) {
        this.biaoQian = biaoQian;
    }

    public String getJiaGe() {
        return jiaGe;
    }

    public void setJiaGe(String jiaGe) {
        this.jiaGe = jiaGe;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
