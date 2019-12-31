package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/12/30.
 */

public class PayRankTongBiModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"orderSn":"2019122515560545421","payMoney":"6","buyIntegral":"42","addTime":"2019-12-25 15:56","ifPay":"未付款","payType":"苹果支付"},{"orderSn":"2019122416550045935","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 16:55","ifPay":"未付款","payType":"微信支付"},{"orderSn":"2019122413521846062","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 13:52","ifPay":"已付款","payType":"微信支付"},{"orderSn":"2019122413264745935","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 13:26","ifPay":"已付款","payType":"支付宝支付"},{"orderSn":"2019122413144942339","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 13:14","ifPay":"已付款","payType":"支付宝支付"},{"orderSn":"2019122411352242575","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 11:35","ifPay":"未付款","payType":"支付宝支付"},{"orderSn":"2019122411304944146","payMoney":"0","buyIntegral":"10","addTime":"2019-12-24 11:30","ifPay":"未付款","payType":"支付宝支付"},{"orderSn":"2019122409271749691","payMoney":"0","buyIntegral":"0","addTime":"2019-12-24 09:27","ifPay":"未付款","payType":"支付宝支付"},{"orderSn":"2019122409253543040","payMoney":"0","buyIntegral":"0","addTime":"2019-12-24 09:25","ifPay":"未付款","payType":"微信支付"}]
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
         * orderSn : 2019122515560545421
         * payMoney : 6
         * buyIntegral : 42
         * addTime : 2019-12-25 15:56
         * ifPay : 未付款
         * payType : 苹果支付
         */

        private String orderSn;
        private String payMoney;
        private String buyIntegral;
        private String addTime;
        private String ifPay;
        private String payType;

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public String getBuyIntegral() {
            return buyIntegral;
        }

        public void setBuyIntegral(String buyIntegral) {
            this.buyIntegral = buyIntegral;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getIfPay() {
            return ifPay;
        }

        public void setIfPay(String ifPay) {
            this.ifPay = ifPay;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }
    }
}
