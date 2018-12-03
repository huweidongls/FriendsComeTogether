package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/7/26.
 */

public class CreateFriendsTogetherModel {

    /**
     * code : 200
     * message : 添加成功!
     * obj : {"activity_id":66}
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
         * activity_id : 66
         */

        private int activity_id;

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }
    }
}
