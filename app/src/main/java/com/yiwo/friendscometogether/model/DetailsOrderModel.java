package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/6.
 */

public class DetailsOrderModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"title":"活动不可退款，只限男性 且单身","content":"","picture":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","time":"2019.03.28-2019.03.31","go_num":"1","price":"0.01","order_sn":"20190325151424550072","paycode":"2019032522001464860551910963","create_time":"","pay_time":"2019-03-25 15:14:24","over_time":"","status":"活动结束","pay_type":"1","order_type":"3","price_type":"自费","begin_time":"2019.03.28","end_time":"2019.03.31","noname":"0","allow_refund":"1","phase":"1","comment":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊","if_comment":true}
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
         * title : 活动不可退款，只限男性 且单身
         * content :
         * picture : http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg
         * time : 2019.03.28-2019.03.31
         * go_num : 1
         * price : 0.01
         * order_sn : 20190325151424550072
         * paycode : 2019032522001464860551910963
         * create_time :
         * pay_time : 2019-03-25 15:14:24
         * over_time :
         * status : 活动结束
         * pay_type : 1
         * order_type : 3
         * price_type : 自费
         * begin_time : 2019.03.28
         * end_time : 2019.03.31
         * noname : 0
         * allow_refund : 1
         * phase : 1
         * comment : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊
         * if_comment : true
         * 订单详情 refundInfo 退款说明
         * refundWhy 退款原因
         *  refund_money 退款金额
         "orderStatus": "1",
         "User": "神秘人",
         "BUser": "花生"
         opaytype: 1.我自己的订单,未付款 2.我自己的订单,已付款 3.邀请人未付款 4.邀请人已付款 5.未付款取消订单
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
        private String begin_time;
        private String end_time;
        private String noname;
        private String allow_refund;
        private String phase;
        private String comment;
        private String comment_ID;
        private boolean if_comment;
        private String refundInfo;
        private String refundWhy;
        private String refund_money;
        private String opaytype;

        private String orderStatus;
        private String User;
        private String BUser;

        private String del_type;//0未删除 1邀请人已删除



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

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getNoname() {
            return noname;
        }

        public void setNoname(String noname) {
            this.noname = noname;
        }

        public String getAllow_refund() {
            return allow_refund;
        }

        public void setAllow_refund(String allow_refund) {
            this.allow_refund = allow_refund;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public boolean isIf_comment() {
            return if_comment;
        }

        public void setIf_comment(boolean if_comment) {
            this.if_comment = if_comment;
        }

        public String getComment_ID() {
            return comment_ID;
        }

        public void setComment_ID(String comment_ID) {
            this.comment_ID = comment_ID;
        }

        public String getRefundInfo() {
            return refundInfo;
        }

        public void setRefundInfo(String refundInfo) {
            this.refundInfo = refundInfo;
        }

        public String getRefundWhy() {
            return refundWhy;
        }

        public void setRefundWhy(String refundWhy) {
            this.refundWhy = refundWhy;
        }

        public String getRefund_money() {
            return refund_money;
        }

        public void setRefund_money(String refund_money) {
            this.refund_money = refund_money;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getUser() {
            return User;
        }

        public void setUser(String user) {
            User = user;
        }

        public String getBUser() {
            return BUser;
        }

        public void setBUser(String BUser) {
            this.BUser = BUser;
        }

        public String getDel_type() {
            return del_type;
        }

        public void setDel_type(String del_type) {
            this.del_type = del_type;
        }

        public String getOpaytype() {
            return opaytype;
        }

        public void setOpaytype(String opaytype) {
            this.opaytype = opaytype;
        }
    }
}
