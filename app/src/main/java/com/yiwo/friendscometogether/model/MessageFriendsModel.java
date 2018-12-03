package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class MessageFriendsModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fID":"6","userID":"38","fuserID":"7","ftime":"1534920837","radio":"0","title":"您收到了一位新朋友加好友请求-我们旅游吧","pic":"http://47.92.136.19/woshou.jpg"}]
     */

    private int code;
    private String message;
    private List<ObjBean> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * fID : 6
         * userID : 38
         * fuserID : 7
         * ftime : 1534920837
         * radio : 0
         * title : 您收到了一位新朋友加好友请求-我们旅游吧
         * pic : http://47.92.136.19/woshou.jpg
         */

        private String describe;
        private String fID;
        private String userID;
        private String fuserID;
        private String ftime;
        private String radio;
        private String title;
        private String pic;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getFID() {
            return fID;
        }

        public void setFID(String fID) {
            this.fID = fID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getFuserID() {
            return fuserID;
        }

        public void setFuserID(String fuserID) {
            this.fuserID = fuserID;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
