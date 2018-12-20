package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/20.
 */

public class GuanZhuWoDeModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"45","username":"崔薇","upicurl":"http://127.0.0.1/uploads/header/2018/09/12/dea21f20f53bd3aabfcd1b456c070dd815367125395.jpg","article_num":"11","like_num":"5","is_follow":"1"},{"userID":"55","username":"夜行小飞侠","upicurl":"http://127.0.0.1/uploads/header/2018/09/04/7d42b5336b6dde013ce90802be75d6dd153604679517.jpg","article_num":"11","like_num":"3","is_follow":"1"},{"userID":"61","username":"龙哥","upicurl":"http://127.0.0.1/uploads/header/2018/09/06/70b7d9a123cbf75f2152a84556f64f3d153619876613.jpg","article_num":"11","like_num":"3","is_follow":"0"}]
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
         * userID : 45
         * username : 崔薇
         * upicurl : http://127.0.0.1/uploads/header/2018/09/12/dea21f20f53bd3aabfcd1b456c070dd815367125395.jpg
         * article_num : 11
         * like_num : 5
         * is_follow : 1
         */

        private String userID;
        private String username;
        private String upicurl;
        private String article_num;
        private String like_num;
        private String is_follow;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
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

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }
    }
}
