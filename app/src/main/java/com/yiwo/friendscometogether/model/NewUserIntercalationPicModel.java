package com.yiwo.friendscometogether.model;

/**
 * Created by ljc on 2019/9/10.
 */

public class NewUserIntercalationPicModel {
    private String pic;
    private String describe;
    private Boolean isFirstPic = false;
    public NewUserIntercalationPicModel() {
    }

    public NewUserIntercalationPicModel(String pic, String describe) {
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

    public Boolean getFirstPic() {
        return isFirstPic;
    }

    public void setFirstPic(Boolean firstPic) {
        isFirstPic = firstPic;
    }
}
