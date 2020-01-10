package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant;

/**
 * Created by hzxuwen on 2016/3/30.
 */
public enum GiftType {
    /**
     * 未知
     */
    UNKNOWN(-1),
    /**
     * 爱心
     */
    AIXIN(0),
    /**
     * 棒棒糖
     */
    BANGBANGTANG(1),

//    /**
//     * 可爱熊
//     */
//    BEAR(2),

    /**
     * 戒指
     */
    JIEZHI(2),

    /**
     * 爱心//饮料
     */
    YINLIAO(3),
    /**
     * //游艇
     */
    YOUTING(4),
    /**
     * 钻石
     */
    ZUANSHI(5),
    /**
     * 魔法棒
     */
    MOFABANG(6),
    /**
     * 心雨
     */
    XINYU(7),
    /**
     * 别墅
     */
    BIESHU(8),
    /**
     * 捧花
     */
    PENGHUA(9),
    /**
     * 小熊
     */
    XIAOXIONG(10),
    /**
     * 糖果雨
     */
    TANGGUOYU(11);
    private int value;

    GiftType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GiftType typeOfValue(int value) {
        for (GiftType e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
