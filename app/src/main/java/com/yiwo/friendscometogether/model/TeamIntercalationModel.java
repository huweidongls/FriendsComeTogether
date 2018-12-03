package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class TeamIntercalationModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"ffpID":"130","ffptitle":"111213","ffpurl":"http://47.92.136.19/uploads/header/2018/08/01/992c2967a399d16904e8036fc35079e81533111928380.jpg","ffptime":"2018-08-01 16:25:28","userID":"7","radio":"待审核","reason":"","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg"}]
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
         * ffpID : 130
         * ffptitle : 111213
         * ffpurl : http://47.92.136.19/uploads/header/2018/08/01/992c2967a399d16904e8036fc35079e81533111928380.jpg
         * ffptime : 2018-08-01 16:25:28
         * userID : 7
         * radio : 待审核
         * reason :
         * username : 吃了
         * userpic : http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg
         */

        private String ffpID;
        private String ffptitle;
        private String ffpurl;
        private String ffptime;
        private String userID;
        private String radio;
        private String reason;
        private String username;
        private String userpic;

        public String getFfpID() {
            return ffpID;
        }

        public void setFfpID(String ffpID) {
            this.ffpID = ffpID;
        }

        public String getFfptitle() {
            return ffptitle;
        }

        public void setFfptitle(String ffptitle) {
            this.ffptitle = ffptitle;
        }

        public String getFfpurl() {
            return ffpurl;
        }

        public void setFfpurl(String ffpurl) {
            this.ffpurl = ffpurl;
        }

        public String getFfptime() {
            return ffptime;
        }

        public void setFfptime(String ffptime) {
            this.ffptime = ffptime;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }
    }
}
