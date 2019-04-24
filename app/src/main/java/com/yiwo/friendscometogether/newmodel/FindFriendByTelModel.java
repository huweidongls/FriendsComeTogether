package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/4/24.
 */

public class FindFriendByTelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"userID":"4","username":"花生","userpic":"http://39.104.102.152/uploads/header/2019/04/11/ca43a78b5290454d022ee3ed6bd46110155496340314.png"}]
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
         * userID : 4
         * username : 花生
         * userpic : http://39.104.102.152/uploads/header/2019/04/11/ca43a78b5290454d022ee3ed6bd46110155496340314.png
         * status  0 不是好友  1是好友
         */

        private String userID;
        private String username;
        private String userpic;
        private String status;
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

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
