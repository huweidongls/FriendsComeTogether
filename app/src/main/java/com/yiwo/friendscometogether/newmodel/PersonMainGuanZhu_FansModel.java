package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/11.
 */

public class PersonMainGuanZhu_FansModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"username":"","userpic":"","num":"4","userID":"58","status":"1"},{"username":"卡卡罗特","userpic":"uploads/header/2018/08/31/f514dfc17ad07ae9b7543101ee8e15b5153570719318.jpg","num":"6","userID":"33","status":"0"},{"username":"","userpic":"","num":"5","userID":"45","status":"0"},{"username":"","userpic":"","num":"3","userID":"55","status":"0"},{"username":"","userpic":"","num":"1","userID":"43","status":"0"},{"username":"老司机","userpic":"uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","num":"5","userID":"7","status":"0"},{"username":"一样一样","userpic":"uploads/header/2019/01/11/08a23d8d83981ff28836141eefc9a75e15471856113.jpg","num":"4","userID":"38","status":"0"}]
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
         * username :
         * userpic :
         * num : 4
         * userID : 58
         * status : 1
         */

        private String username;
        private String userpic;
        private String num;
        private String userID;
        private String status = "";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
