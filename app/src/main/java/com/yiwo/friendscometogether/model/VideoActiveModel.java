package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class VideoActiveModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"pfID":"1","pftitle":"登山啦啦","pfcontent":"威风威风威风娃娃威风威风威风娃娃威风威风威风娃娃威风威风威风娃娃","pfpic":"http://47.92.136.19/uploads/activity/20180814/20180814/61b1115f9882e0781bfed418c9feeab9.jpg","pflook":"8","pfcomment":"0","pftime":"2018-08-16"}]
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
         * pfID : 1
         * pftitle : 登山啦啦
         * pfcontent : 威风威风威风娃娃威风威风威风娃娃威风威风威风娃娃威风威风威风娃娃
         * pfpic : http://47.92.136.19/uploads/activity/20180814/20180814/61b1115f9882e0781bfed418c9feeab9.jpg
         * pflook : 8
         * pfcomment : 0
         * pftime : 2018-08-16
         */

        private String pfID;
        private String pftitle;
        private String pfcontent;
        private String pfpic;
        private String pflook;
        private String pfcomment;
        private String pftime;

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

        public String getPfcontent() {
            return pfcontent;
        }

        public void setPfcontent(String pfcontent) {
            this.pfcontent = pfcontent;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getPflook() {
            return pflook;
        }

        public void setPflook(String pflook) {
            this.pflook = pflook;
        }

        public String getPfcomment() {
            return pfcomment;
        }

        public void setPfcomment(String pfcomment) {
            this.pfcomment = pfcomment;
        }

        public String getPftime() {
            return pftime;
        }

        public void setPftime(String pftime) {
            this.pftime = pftime;
        }
    }
}
