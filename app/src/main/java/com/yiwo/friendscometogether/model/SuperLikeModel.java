package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/1/10.
 */

public class SuperLikeModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"userID":"7","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","userbirthday":25,"username":"老司机","Matching_degree":"30%","label":"热血,随性,安静,长发及腰"},{"userID":"31","userpic":"http://47.92.136.19/uploads/header/2018/08/02/dd65ab4c08a39c50897459c02104b6bb153317457712.jpg","userbirthday":23,"username":"明年","Matching_degree":"30%","label":""},{"userID":"33","userpic":"http://47.92.136.19/uploads/header/2018/08/31/f514dfc17ad07ae9b7543101ee8e15b5153570719318.jpg","userbirthday":27,"username":"卡卡罗特","Matching_degree":"30%","label":""}]
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
         * userID : 7
         * userpic : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         * userbirthday : 25
         * username : 老司机
         * Matching_degree : 30%
         * label : 热血,随性,安静,长发及腰
         */

        private String userID;
        private String userpic;
        private int userbirthday;
        private String username;
        private String Matching_degree;
        private String label;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public int getUserbirthday() {
            return userbirthday;
        }

        public void setUserbirthday(int userbirthday) {
            this.userbirthday = userbirthday;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMatching_degree() {
            return Matching_degree;
        }

        public void setMatching_degree(String Matching_degree) {
            this.Matching_degree = Matching_degree;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
