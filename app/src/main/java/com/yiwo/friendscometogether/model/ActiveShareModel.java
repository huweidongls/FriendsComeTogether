package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/20.
 */

public class ActiveShareModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"title":"泰山30日游","url":"http://47.92.136.19/wxweb/wx_index/activity_info?pfID=2","images":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","desc":"泰山真好"}
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
         * title : 泰山30日游
         * url : http://47.92.136.19/wxweb/wx_index/activity_info?pfID=2
         * images : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
         * desc : 泰山真好
         */

        private String title;
        private String url;
        private String images;
        private String desc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
