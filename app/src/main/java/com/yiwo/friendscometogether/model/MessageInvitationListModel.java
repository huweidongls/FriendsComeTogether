package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class MessageInvitationListModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"id":"38","tid":"102","type":"1","yqtime":"2018-12-21 16:58:12","text":"","read_type":"1","pftitle":"减肥减肥家","username":"一样一样","pfpic":"http://47.92.136.19/uploads/xingcheng/20180930/f5ab478ba5edee62102e9b8b499b8d86.jpg","sex":"1"}]
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
         * id : 38
         * tid : 102
         * type : 1
         * yqtime : 2018-12-21 16:58:12
         * text :
         * read_type : 1
         * pftitle : 减肥减肥家
         * username : 一样一样
         * pfpic : http://47.92.136.19/uploads/xingcheng/20180930/f5ab478ba5edee62102e9b8b499b8d86.jpg
         * sex : 1
         */

        private String id;
        private String tid;
        private String type;
        private String yqtime;
        private String text;
        private String read_type;
        private String pftitle;
        private String username;
        private String pfpic;
        private String sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getYqtime() {
            return yqtime;
        }

        public void setYqtime(String yqtime) {
            this.yqtime = yqtime;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getRead_type() {
            return read_type;
        }

        public void setRead_type(String read_type) {
            this.read_type = read_type;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
