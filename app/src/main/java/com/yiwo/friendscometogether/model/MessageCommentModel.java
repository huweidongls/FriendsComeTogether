package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23 0023.
 */

public class MessageCommentModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"pfID":"2","mes_id":"3","mes_title":"泰山30日游","mes_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","mes_message":"吃了回复了你：你也好"},{"pfID":"2","mes_id":"11","mes_title":"泰山30日游","mes_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","mes_message":"我们旅游吧评论了你：干干净净卡拉库里湖"},{"pfID":"2","mes_id":"12","mes_title":"泰山30日游","mes_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","mes_message":"我们旅游吧评论了你：呃呃呃呃"}]
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
         * pfID : 2
         * mes_id : 3
         * mes_title : 泰山30日游
         * mes_pic : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
         * mes_message : 吃了回复了你：你也好
         */

        private String pfID;
        private String mes_id;
        private String mes_title;
        private String mes_pic;
        private String mes_message;

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getMes_id() {
            return mes_id;
        }

        public void setMes_id(String mes_id) {
            this.mes_id = mes_id;
        }

        public String getMes_title() {
            return mes_title;
        }

        public void setMes_title(String mes_title) {
            this.mes_title = mes_title;
        }

        public String getMes_pic() {
            return mes_pic;
        }

        public void setMes_pic(String mes_pic) {
            this.mes_pic = mes_pic;
        }

        public String getMes_message() {
            return mes_message;
        }

        public void setMes_message(String mes_message) {
            this.mes_message = mes_message;
        }
    }
}
