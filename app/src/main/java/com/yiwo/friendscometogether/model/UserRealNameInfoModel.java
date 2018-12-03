package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/9.
 */

public class UserRealNameInfoModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"usercodeok":"2","usercodeback":"http://47.92.136.19/uploads/header/2018/08/01/cd6aa68aacf393a743566c32268f156e15331037667.jpg","usercode":"http://47.92.136.19/uploads/header/2018/08/01/cd6aa68aacf393a743566c32268f156e15331037660.jpg","usercodenum":"236586555955655536","usertruename":"恶魔"}
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * usercodeok : 2
         * usercodeback : http://47.92.136.19/uploads/header/2018/08/01/cd6aa68aacf393a743566c32268f156e15331037667.jpg
         * usercode : http://47.92.136.19/uploads/header/2018/08/01/cd6aa68aacf393a743566c32268f156e15331037660.jpg
         * usercodenum : 236586555955655536
         * usertruename : 恶魔
         */

        private String usercodeok;
        private String usercodeback;
        private String usercode;
        private String usercodenum;
        private String usertruename;

        public String getUsercodeok() {
            return usercodeok;
        }

        public void setUsercodeok(String usercodeok) {
            this.usercodeok = usercodeok;
        }

        public String getUsercodeback() {
            return usercodeback;
        }

        public void setUsercodeback(String usercodeback) {
            this.usercodeback = usercodeback;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getUsercodenum() {
            return usercodenum;
        }

        public void setUsercodenum(String usercodenum) {
            this.usercodenum = usercodenum;
        }

        public String getUsertruename() {
            return usertruename;
        }

        public void setUsertruename(String usertruename) {
            this.usertruename = usertruename;
        }
    }
}
