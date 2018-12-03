package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */

public class UserFocusModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"lID":"2","likeuserID":"38","userphone":"18346038613","username":"18346038613","usersex":"0","upicurl":"","article_num":"0","like_num":"1","activity_id":"0","status":"0"}]
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
         * lID : 2
         * likeuserID : 38
         * userphone : 18346038613
         * username : 18346038613
         * usersex : 0
         * upicurl :
         * article_num : 0
         * like_num : 1
         * activity_id : 0
         * status : 0
         */

        private String lID;
        private String likeuserID;
        private String userphone;
        private String username;
        private String usersex;
        private String upicurl;
        private String article_num;
        private String like_num;
        private String activity_id;
        private String status;

        public String getLID() {
            return lID;
        }

        public void setLID(String lID) {
            this.lID = lID;
        }

        public String getLikeuserID() {
            return likeuserID;
        }

        public void setLikeuserID(String likeuserID) {
            this.likeuserID = likeuserID;
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

        public String getUsersex() {
            return usersex;
        }

        public void setUsersex(String usersex) {
            this.usersex = usersex;
        }

        public String getUpicurl() {
            return upicurl;
        }

        public void setUpicurl(String upicurl) {
            this.upicurl = upicurl;
        }

        public String getArticle_num() {
            return article_num;
        }

        public void setArticle_num(String article_num) {
            this.article_num = article_num;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
