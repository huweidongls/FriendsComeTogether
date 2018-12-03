package com.yiwo.friendscometogether.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/8/7.
 */

public class OrderToPayModel {

    /**
     * code : 200
     * message : 付款成功!
     * obj : {"appId":"wx77762a8eab365cf1","partnerId":"1509630191","prepayId":"wx07114415234368de8ff759483977065775","package":"Sign=WXPay","nonceStr":"WcYbQNhNEm17UG5P","timestamp":1533613455,"sign":"9EEC656068356484FDF6C03EDECB884A"}
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
         * appId : wx77762a8eab365cf1
         * partnerId : 1509630191
         * prepayId : wx07114415234368de8ff759483977065775
         * package : Sign=WXPay
         * nonceStr : WcYbQNhNEm17UG5P
         * timestamp : 1533613455
         * sign : 9EEC656068356484FDF6C03EDECB884A
         */

        private String appId;
        private String partnerId;
        private String prepayId;
        @SerializedName("package")
        private String packageX;
        private String nonceStr;
        private int timestamp;
        private String sign;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
