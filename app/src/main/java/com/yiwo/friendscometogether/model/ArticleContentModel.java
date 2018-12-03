package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class ArticleContentModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"title":"测试测试测试测试","leftid":"1","pic":"http://47.92.136.19/uploads/slide/56353656sdgfdgdfgdfg.jpg","first_type":"0"},{"title":"1111111111111","leftid":"1","pic":"http://47.92.136.19/uploads/slide/qwewerewhgjgh.jpg","first_type":"0"},{"title":"34t3t3t34","leftid":"1","pic":"http://47.92.136.19/uploads/banner/20180717/20180717/2275e8c527b00f2bf5df81eb1432971b.jpg","first_type":"1"}]
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
         * title : 测试测试测试测试
         * leftid : 1
         * pic : http://47.92.136.19/uploads/slide/56353656sdgfdgdfgdfg.jpg
         * first_type : 0
         */

        private String title;
        private String leftid;
        private String pic;
        private String first_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLeftid() {
            return leftid;
        }

        public void setLeftid(String leftid) {
            this.leftid = leftid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getFirst_type() {
            return first_type;
        }

        public void setFirst_type(String first_type) {
            this.first_type = first_type;
        }
    }
}
