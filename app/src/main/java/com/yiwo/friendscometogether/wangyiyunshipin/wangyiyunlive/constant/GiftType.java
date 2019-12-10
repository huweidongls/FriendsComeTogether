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
     * 玫瑰
     */
    ROSE(0),
    /**
     * 巧克力
     */
    CHOCOLATE(1),

//    /**
//     * 可爱熊
//     */
//    BEAR(2),

    /**
     * 冰淇淋
     */
    ICECREAM(3),

    /**
     * 爱心//后添加
     */
    HEART(2);
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
