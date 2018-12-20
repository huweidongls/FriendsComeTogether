package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/20.
 */

public class WoGuanZhuDeModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"likeuserID":"1","username":"heihei","usersex":"0","upicurl":"http://localhost/www/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","article_num":"1","like_num":"1","activity_id":"1","status":0}]
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
         * likeuserID : 1
         * username : heihei
         * usersex : 0
         * upicurl : http://localhost/www/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg
         * article_num : 1
         * like_num : 1
         * activity_id : 1
         * status : 0
         */

        private String likeuserID;
        private String username;
        private String usersex;
        private String upicurl;
        private String article_num;
        private String like_num;
        private String activity_id;
        private int status;

        public String getLikeuserID() {
            return likeuserID;
        }

        public void setLikeuserID(String likeuserID) {
            this.likeuserID = likeuserID;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
