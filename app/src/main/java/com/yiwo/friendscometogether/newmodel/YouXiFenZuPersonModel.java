package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/9/19.
 */

public class YouXiFenZuPersonModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"130","username":"130","user_ID":"130","gameNum":"1"},{"userID":"4","username":"花生","user_ID":"4","gameNum":"2"}]
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
         * userID : 130
         * username : 130
         * user_ID : 130
         * gameNum : 1
         */

        private String userID;
        private String username;
        private String user_ID;
        private String gameNum;
        private String userpic;

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

        public String getUser_ID() {
            return user_ID;
        }

        public void setUser_ID(String user_ID) {
            this.user_ID = user_ID;
        }

        public String getGameNum() {
            return gameNum;
        }

        public void setGameNum(String gameNum) {
            this.gameNum = gameNum;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }
    }
}
