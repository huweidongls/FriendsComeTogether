package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/12/12.
 */

public class MyGiftsModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : [{"presentName":"钻石","num":"5","state":"5"},{"presentName":"游艇","num":"5","state":"4"},{"presentName":"戒指","num":"5","state":"2"},{"presentName":"棒棒糖","num":"5","state":"1"},{"presentName":"爱心","num":"5","state":"0"},{"presentName":"饮料","num":"5","state":"3"}]
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
         * presentName : 钻石
         * num : 5
         * state : 5
         */

        private String presentName;
        private String num;
        private String state;
        private String integral;//礼物价格

        public String getPresentName() {
            return presentName;
        }

        public void setPresentName(String presentName) {
            this.presentName = presentName;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
