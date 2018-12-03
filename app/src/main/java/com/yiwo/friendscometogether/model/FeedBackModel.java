package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 */

public class FeedBackModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"fID":"1","userID":"1","ftitle":"哈哈","ftime":"123","backtitle":"哼哼哼哈哈哈哈哈","backtime":"1528176723","backID":"1","backradio":"0"}]
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
         * fID : 1
         * userID : 1
         * ftitle : 哈哈
         * ftime : 123
         * backtitle : 哼哼哼哈哈哈哈哈
         * backtime : 1528176723
         * backID : 1
         * backradio : 0
         */

        private String fID;
        private String userID;
        private String ftitle;
        private String ftime;
        private String backtitle;
        private String backtime;
        private String backID;
        private String backradio;

        public String getFID() {
            return fID;
        }

        public void setFID(String fID) {
            this.fID = fID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getFtitle() {
            return ftitle;
        }

        public void setFtitle(String ftitle) {
            this.ftitle = ftitle;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getBacktitle() {
            return backtitle;
        }

        public void setBacktitle(String backtitle) {
            this.backtitle = backtitle;
        }

        public String getBacktime() {
            return backtime;
        }

        public void setBacktime(String backtime) {
            this.backtime = backtime;
        }

        public String getBackID() {
            return backID;
        }

        public void setBackID(String backID) {
            this.backID = backID;
        }

        public String getBackradio() {
            return backradio;
        }

        public void setBackradio(String backradio) {
            this.backradio = backradio;
        }
    }
}
