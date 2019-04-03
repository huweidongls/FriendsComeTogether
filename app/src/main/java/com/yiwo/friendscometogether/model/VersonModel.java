package com.yiwo.friendscometogether.model;

/**
 * Created by ljc on 2019/4/3.
 */

public class VersonModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"an_version":"100","an_download":"www.baidu.com","ios_version":"100","ios_download":"www.qidian.com"}
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
         * an_version : 100
         * an_download : www.baidu.com
         * ios_version : 100
         * ios_download : www.qidian.com
         */

        private String an_version;
        private String an_download;
        private String ios_version;
        private String ios_download;

        public String getAn_version() {
            return an_version;
        }

        public void setAn_version(String an_version) {
            this.an_version = an_version;
        }

        public String getAn_download() {
            return an_download;
        }

        public void setAn_download(String an_download) {
            this.an_download = an_download;
        }

        public String getIos_version() {
            return ios_version;
        }

        public void setIos_version(String ios_version) {
            this.ios_version = ios_version;
        }

        public String getIos_download() {
            return ios_download;
        }

        public void setIos_download(String ios_download) {
            this.ios_download = ios_download;
        }
    }
}
