package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/6.
 */

public class DetailsOrderModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"title":"登峨眉山！","content":"登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！","picture":"http://47.92.136.19/uploads/activity/20180720/20180720/73cc839014b7516c8d975ec3aa52e382.jpg","time":"2018.07.30-2018.08.01","go_num":"1","price":"1.00","order_sn":"2018080116145072947","paycode":"","create_time":"","pay_time":"2018-08-01 16:14:50","over_time":"","status":"退款中","pay_type":"0","order_type":"4","price_type":"自费"}
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
         * title : 登峨眉山！
         * content : 登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！
         * picture : http://47.92.136.19/uploads/activity/20180720/20180720/73cc839014b7516c8d975ec3aa52e382.jpg
         * time : 2018.07.30-2018.08.01
         * go_num : 1
         * price : 1.00
         * order_sn : 2018080116145072947
         * paycode :
         * create_time :
         * pay_time : 2018-08-01 16:14:50
         * over_time :
         * status : 退款中
         * pay_type : 0
         * order_type : 4
         * price_type : 自费
         */

        private String title;
        private String content;
        private String picture;
        private String time;
        private String go_num;
        private String price;
        private String order_sn;
        private String paycode;
        private String create_time;
        private String pay_time;
        private String over_time;
        private String status;
        private String pay_type;
        private String order_type;
        private String price_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGo_num() {
            return go_num;
        }

        public void setGo_num(String go_num) {
            this.go_num = go_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getPaycode() {
            return paycode;
        }

        public void setPaycode(String paycode) {
            this.paycode = paycode;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getPrice_type() {
            return price_type;
        }

        public void setPrice_type(String price_type) {
            this.price_type = price_type;
        }
    }
}
