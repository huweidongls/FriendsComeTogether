package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class MyFocusActiveModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"pfID":"29","pftitle":"伪满皇宫博物院","pfpic":"http://47.92.136.19/uploads/activity/20180829/37179b0274ac12718c1f79d7df54dc8c.png","pfgotime":"2018-09-01 15:37:46","pfpwd":""},{"pfID":"30","pftitle":"1友聚","pfpic":"http://47.92.136.19/uploads/xingcheng/20180829/b2d5368c1297202692f43e7442addaba.jpg","pfgotime":"2018-08-29 17:13:00","pfpwd":""},{"pfID":"21","pftitle":"测试图片2","pfpic":"http://47.92.136.19/uploads/xingcheng/20180828/3542960e3bf795744813f486daa2a4cb.jpg","pfgotime":"2018-09-01 00:00:00","pfpwd":""},{"pfID":"22","pftitle":"测试密码","pfpic":"http://47.92.136.19/uploads/xingcheng/20180828/77e5f1ac76fb54b49a85fb3b09eaaada.jpg","pfgotime":"2018-09-01 00:00:00","pfpwd":""},{"pfID":"20","pftitle":"测试1","pfpic":"http://47.92.136.19/uploads/xingcheng/20180828/ab0bca496637adc6acc080da15a40954.jpeg","pfgotime":"2018-09-01 00:00:00","pfpwd":""}]
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
         * pfID : 29
         * pftitle : 伪满皇宫博物院
         * pfpic : http://47.92.136.19/uploads/activity/20180829/37179b0274ac12718c1f79d7df54dc8c.png
         * pfgotime : 2018-09-01 15:37:46
         * pfpwd :
         */

        private String pfID;
        private String pftitle;
        private String pfpic;
        private String pfgotime;
        private String pfpwd;

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getPfgotime() {
            return pfgotime;
        }

        public void setPfgotime(String pfgotime) {
            this.pfgotime = pfgotime;
        }

        public String getPfpwd() {
            return pfpwd;
        }

        public void setPfpwd(String pfpwd) {
            this.pfpwd = pfpwd;
        }
    }
}
