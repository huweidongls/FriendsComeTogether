package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class IndexLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"lID":"1","lname":"旅游","img":"http://47.92.136.19/lvxing.png"},{"lID":"2","lname":"美食","img":"http://47.92.136.19/meishi.png"},{"lID":"20","lname":"探店","img":"http://47.92.136.19/tandian.png"},{"lID":"21","lname":"攻略","img":"http://47.92.136.19/gonglue.png"}]
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
         * lID : 1
         * lname : 旅游
         * img : http://47.92.136.19/lvxing.png
         */

        private String lID;
        private String lname;
        private String img;

        public String getLID() {
            return lID;
        }

        public void setLID(String lID) {
            this.lID = lID;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
