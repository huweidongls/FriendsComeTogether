package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/4/26.
 */

public class PersonGroupModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"userID":"130","username":"0912_130","userpic":"http://39.104.102.152/uploads/header/2019/04/18/650d4f4aff68881ccbeb904459e65e6e155555103718.png"}]
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
         * username : 0912_130
         * userpic : http://39.104.102.152/uploads/header/2019/04/18/650d4f4aff68881ccbeb904459e65e6e155555103718.png
         */

        private String userID;
        private String username;
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

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }
    }
}
