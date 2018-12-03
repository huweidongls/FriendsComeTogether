package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/7/31.
 */

public class IsRealNameModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"ok":"2"}
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
         * ok : 2
         */

        private String ok;

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }
    }
}
