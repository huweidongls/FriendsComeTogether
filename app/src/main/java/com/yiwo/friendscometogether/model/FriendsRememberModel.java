package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class FriendsRememberModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"fmID":"1","accesspassword":"0","userID":"1","fmtitle":"QQ群","fmcontent":"安达市大所大所大所大","fmpic":"http://47.92.136.19/uploads/activity/20180529/ee77b295487906af3700b1d6b7ab6c38.jpg","fmcomment":"0","fmlook":"0","fmtime":"1974-12-22","userphone":"15244615472","username":"zp","upicurl":"http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","usergrade":"0","look":0}]
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
         * fmID : 1
         * accesspassword : 0
         * userID : 1
         * fmtitle : QQ群
         * fmcontent : 安达市大所大所大所大
         * fmpic : http://47.92.136.19/uploads/activity/20180529/ee77b295487906af3700b1d6b7ab6c38.jpg
         * fmcomment : 0
         * fmlook : 0
         * fmtime : 1974-12-22
         * userphone : 15244615472
         * username : zp
         * upicurl : http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg
         * usergrade : 0
         * look : 0
         */

        private String fmID;
        private String accesspassword;
        private String userID;
        private String fmtitle;
        private String fmcontent;
        private String fmpic;
        private String fmcomment;
        private String fmlook;
        private String fmtime;
        private String userphone;
        private String username;
        private String upicurl;
        private String usergrade;
        private String look;

        public String getFmID() {
            return fmID;
        }

        public void setFmID(String fmID) {
            this.fmID = fmID;
        }

        public String getAccesspassword() {
            return accesspassword;
        }

        public void setAccesspassword(String accesspassword) {
            this.accesspassword = accesspassword;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getFmtitle() {
            return fmtitle;
        }

        public void setFmtitle(String fmtitle) {
            this.fmtitle = fmtitle;
        }

        public String getFmcontent() {
            return fmcontent;
        }

        public void setFmcontent(String fmcontent) {
            this.fmcontent = fmcontent;
        }

        public String getFmpic() {
            return fmpic;
        }

        public void setFmpic(String fmpic) {
            this.fmpic = fmpic;
        }

        public String getFmcomment() {
            return fmcomment;
        }

        public void setFmcomment(String fmcomment) {
            this.fmcomment = fmcomment;
        }

        public String getFmlook() {
            return fmlook;
        }

        public void setFmlook(String fmlook) {
            this.fmlook = fmlook;
        }

        public String getFmtime() {
            return fmtime;
        }

        public void setFmtime(String fmtime) {
            this.fmtime = fmtime;
        }

        public String getUserphone() {
            return userphone;
        }

        public void setUserphone(String userphone) {
            this.userphone = userphone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUpicurl() {
            return upicurl;
        }

        public void setUpicurl(String upicurl) {
            this.upicurl = upicurl;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }
    }
}
