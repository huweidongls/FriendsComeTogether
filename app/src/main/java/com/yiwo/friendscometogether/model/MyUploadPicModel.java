package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/1.
 */

public class MyUploadPicModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : {"pic":"http://47.92.136.19/uploads/header/2018/08/01/e76dd1d604aaeef88d8fa60318cb89b0153309218613.jpg","id":41}
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
         * pic : http://47.92.136.19/uploads/header/2018/08/01/e76dd1d604aaeef88d8fa60318cb89b0153309218613.jpg
         * id : 41
         */

        private String pic;
        private int id;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
