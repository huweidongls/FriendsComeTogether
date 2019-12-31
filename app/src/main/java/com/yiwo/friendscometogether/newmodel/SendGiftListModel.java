package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/12/31.
 */

public class SendGiftListModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"10","num":"1","username":"随便","addTime":"2019-12-30 15:41"},{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"10","num":"1","username":"随便","addTime":"2019-12-30 13:48"},{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"10","num":"1","username":"随便","addTime":"2019-12-30 13:48"},{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"20","num":"2","username":"随便","addTime":"2019-12-30 13:38"},{"userID":"130","presentName":"钻石","presentImg":"http://www.tongbanapp.com/uploads/present/钻石大.png","integral":"12","num":"2","username":"随便","addTime":"2019-12-30 13:38"},{"userID":"130","presentName":"游艇","presentImg":"http://www.tongbanapp.com/uploads/present/游艇大.png","integral":"5","num":"1","username":"随便","addTime":"2019-12-30 13:38"},{"userID":"130","presentName":"棒棒糖","presentImg":"http://www.tongbanapp.com/uploads/present/棒棒糖大.png","integral":"80","num":"10","username":"随便","addTime":"2019-12-30 13:38"},{"userID":"130","presentName":"棒棒糖","presentImg":"http://www.tongbanapp.com/uploads/present/棒棒糖大.png","integral":"80","num":"10","username":"随便","addTime":"2019-12-30 13:37"},{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"100","num":"10","username":"随便","addTime":"2019-12-30 13:37"},{"userID":"130","presentName":"爱心","presentImg":"http://www.tongbanapp.com/uploads/present/爱心大.png","integral":"100","num":"10","username":"随便","addTime":"2019-12-30 13:37"}]
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
         * userID : 130
         * presentName : 爱心
         * presentImg : http://www.tongbanapp.com/uploads/present/爱心大.png
         * integral : 10
         * num : 1
         * username : 随便
         * addTime : 2019-12-30 15:41
         */

        private String userID;
        private String presentName;
        private String presentImg;
        private String integral;
        private String num;
        private String username;
        private String addTime;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getPresentName() {
            return presentName;
        }

        public void setPresentName(String presentName) {
            this.presentName = presentName;
        }

        public String getPresentImg() {
            return presentImg;
        }

        public void setPresentImg(String presentImg) {
            this.presentImg = presentImg;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
    }
}
