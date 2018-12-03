package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/3.
 */

public class FocusOnLeaderModel {

    /**
     * code : 200
     * message : 关注成功
     * obj : {"attention":1}
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
         * attention : 1
         */

        private String attention;

        public String getAttention() {
            return attention;
        }

        public void setAttention(String attention) {
            this.attention = attention;
        }
    }
}
