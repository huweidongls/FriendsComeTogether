package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/7/26.
 */

public class UserIntercalationPicModel {
    private String pic;
    private String describe;

    public UserIntercalationPicModel() {
    }

    public UserIntercalationPicModel(String pic, String describe) {
        this.pic = pic;
        this.describe = describe;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
