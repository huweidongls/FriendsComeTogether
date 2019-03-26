package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class MessageFriendsModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fID":"235","userID":"72","fuserID":"79","ftime":"1553562416","radio":"2","describe":"1231231","read_type":"1","message":"拒绝添加好友","type":"2","title":"Wy拒绝了你的好友邀请","username":"Wy","pic":"http://39.104.102.152/uploads/header/2019/03/20/957c5c4abb4d1167ddd314d56eeea1a415530440331.jpg"},{"fID":"237","userID":"79","fuserID":"72","ftime":"1553562676","radio":"0","describe":"","read_type":"1","message":"","type":"0","title":"您收到了一位新朋友加好友请求-Wy","pic":"http://39.104.102.152/uploads/header/2019/03/20/957c5c4abb4d1167ddd314d56eeea1a415530440331.jpg","username":"Wy"}]
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
         * fID : 235
         * userID : 72
         * fuserID : 79
         * ftime : 1553562416
         * radio : 2
         * describe : 1231231
         * read_type : 1
         * message : 拒绝添加好友
         * type : 2
         * title : Wy拒绝了你的好友邀请
         * username : Wy
         * pic : http://39.104.102.152/uploads/header/2019/03/20/957c5c4abb4d1167ddd314d56eeea1a415530440331.jpg
         */

        private String fID;
        private String userID;
        private String fuserID;
        private String ftime;
        private String radio;
        private String describe;
        private String read_type;
        private String message;
        private String type;
        private String title;
        private String username;
        private String pic;

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

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getRead_type() {
            return read_type;
        }

        public void setRead_type(String read_type) {
            this.read_type = read_type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
