package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/1.
 */

public class IntercalationLocationModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fftitle":"吃了","ffID":"85"}]
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
         * fftitle : 吃了
         * ffID : 85
         */

        private String fftitle;
        private String ffID;

        public String getFftitle() {
            return fftitle;
        }

        public void setFftitle(String fftitle) {
            this.fftitle = fftitle;
        }

        public String getFfID() {
            return ffID;
        }

        public void setFfID(String ffID) {
            this.ffID = ffID;
        }
    }
}
