package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class AllOrderFragmentModel {
    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"72","pfid":"157","opayuserid":"72","oID":"354","title":"活动不可退款，只限男性 且单身","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","opaymoney":"0.01","opaytype":"1","opayout":"0","allow_refund":"1","noname":"0","phase":"1","if_comment":true,"price_type":"自费","time_info":"2019.03.28-2019.03.31","begin_time":"2019.03.28","end_time":"2019.03.31","status":"待支付","order_type":"1"},{"userID":"72","pfid":"157","opayuserid":"72","oID":"353","title":"活动不可退款，只限男性 且单身","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","opaymoney":"0.01","opaytype":"1","opayout":"0","allow_refund":"1","noname":"0","phase":"1","if_comment":true,"price_type":"自费","time_info":"2019.03.28-2019.03.31","begin_time":"2019.03.28","end_time":"2019.03.31","status":"待支付","order_type":"1"},{"userID":"72","pfid":"157","opayuserid":"72","oID":"352","title":"活动不可退款，只限男性 且单身","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"1","noname":"0","phase":"1","if_comment":true,"price_type":"自费","time_info":"2019.03.28-2019.03.31","begin_time":"2019.03.28","end_time":"2019.03.31","status":"已结束","order_type":"3"},{"userID":"72","pfid":"151","opayuserid":"72","oID":"340","title":"不允许退款的活动123123","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190315/348d65e4f54c809d6fe99524c1d2b8c6.jpg","opaymoney":"0.01","opaytype":"2","opayout":"1","allow_refund":"1","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.04.25-2019.04.30","begin_time":"2019.04.25","end_time":"2019.04.30","status":"退款中","order_type":"4"},{"userID":"72","pfid":"146","opayuserid":"72","oID":"338","title":"有其他要求的活动","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190313/5546d4892ca39f381539b77fd4b6202f.jpg","opaymoney":"0.01","opaytype":"5","opayout":"1","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.04.01-2019.04.05","begin_time":"2019.04.01","end_time":"2019.04.05","status":"退款中","order_type":"4"},{"userID":"72","pfid":"139","opayuserid":"72","oID":"311","title":"活动测试2允许退款","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/40386171b001e18d93d1bde7ee77cdac.jpg","opaymoney":"0.10","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","phase":"8","if_comment":false,"price_type":"自费","time_info":"2019.04.01-2019.04.30","begin_time":"2019.04.01","end_time":"2019.04.30","status":"待支付","order_type":"1"},{"userID":"72","pfid":"139","opayuserid":"72","oID":"310","title":"活动测试2允许退款","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/40386171b001e18d93d1bde7ee77cdac.jpg","opaymoney":"0.10","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","phase":"2","if_comment":false,"price_type":"自费","time_info":"2019.03.21-2019.03.23","begin_time":"2019.03.21","end_time":"2019.03.23","status":"待支付","order_type":"1"},{"userID":"72","pfid":"139","opayuserid":"72","oID":"307","title":"活动测试2允许退款","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/40386171b001e18d93d1bde7ee77cdac.jpg","opaymoney":"0.10","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","phase":"2","if_comment":false,"price_type":"自费","time_info":"2019.03.21-2019.03.23","begin_time":"2019.03.21","end_time":"2019.03.23","status":"待支付","order_type":"1"},{"userID":"72","pfid":"139","opayuserid":"72","oID":"306","title":"活动测试2允许退款","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/40386171b001e18d93d1bde7ee77cdac.jpg","opaymoney":"0.10","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","phase":"2","if_comment":false,"price_type":"自费","time_info":"2019.03.21-2019.03.23","begin_time":"2019.03.21","end_time":"2019.03.23","status":"待支付","order_type":"1"},{"userID":"72","pfid":"139","opayuserid":"72","oID":"303","title":"活动测试2允许退款","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/40386171b001e18d93d1bde7ee77cdac.jpg","opaymoney":"0.10","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"8","if_comment":false,"price_type":"自费","time_info":"2019.04.01-2019.04.30","begin_time":"2019.04.01","end_time":"2019.04.30","status":"待开始","order_type":"2"}]
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
         * userID : 72
         * pfid : 157
         * opayuserid : 72
         * oID : 354
         * title : 活动不可退款，只限男性 且单身
         * info :
         * join_num : 1
         * pf_pic : http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg
         * opaymoney : 0.01
         * opaytype : 1
         * opayout : 0
         * allow_refund : 1
         * noname : 0
         * phase : 1
         * if_comment : true
         * price_type : 自费
         * time_info : 2019.03.28-2019.03.31
         * begin_time : 2019.03.28
         * end_time : 2019.03.31
         * status : 待支付
         * order_type : 1
         * "orderStatus": "2",// 0正常订单  1 我是被邀请的人  2 我是邀请的人
         "User": "0912_130", //User 邀请人名字
         "BUser": "花生", // Buser被邀请人名字
         del_type  0未删除 1邀请人已删除
         */

        private String userID;
        private String pfid;
        private String opayuserid;
        private String oID;
        private String title;
        private String info;
        private String join_num;
        private String pf_pic;
        private String opaymoney;
        private String opaytype;
        private String opayout;
        private String allow_refund;
        private String noname;
        private String phase;
        private boolean if_comment;
        private String price_type;
        private String time_info;
        private String begin_time;
        private String end_time;
        private String status;
        private String order_type;
        private String orderStatus;
        private String User;
        private String BUser;
        private String del_type;
        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getPfid() {
            return pfid;
        }

        public void setPfid(String pfid) {
            this.pfid = pfid;
        }

        public String getOpayuserid() {
            return opayuserid;
        }

        public void setOpayuserid(String opayuserid) {
            this.opayuserid = opayuserid;
        }

        public String getOID() {
            return oID;
        }

        public void setOID(String oID) {
            this.oID = oID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getJoin_num() {
            return join_num;
        }

        public void setJoin_num(String join_num) {
            this.join_num = join_num;
        }

        public String getPf_pic() {
            return pf_pic;
        }

        public void setPf_pic(String pf_pic) {
            this.pf_pic = pf_pic;
        }

        public String getOpaymoney() {
            return opaymoney;
        }

        public void setOpaymoney(String opaymoney) {
            this.opaymoney = opaymoney;
        }

        public String getOpaytype() {
            return opaytype;
        }

        public void setOpaytype(String opaytype) {
            this.opaytype = opaytype;
        }

        public String getOpayout() {
            return opayout;
        }

        public void setOpayout(String opayout) {
            this.opayout = opayout;
        }

        public String getAllow_refund() {
            return allow_refund;
        }

        public void setAllow_refund(String allow_refund) {
            this.allow_refund = allow_refund;
        }

        public String getNoname() {
            return noname;
        }

        public void setNoname(String noname) {
            this.noname = noname;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public boolean isIf_comment() {
            return if_comment;
        }

        public void setIf_comment(boolean if_comment) {
            this.if_comment = if_comment;
        }

        public String getPrice_type() {
            return price_type;
        }

        public void setPrice_type(String price_type) {
            this.price_type = price_type;
        }

        public String getTime_info() {
            return time_info;
        }

        public void setTime_info(String time_info) {
            this.time_info = time_info;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
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
    }
}
