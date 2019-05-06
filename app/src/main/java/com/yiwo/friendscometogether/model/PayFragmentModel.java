package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class PayFragmentModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"opayuserid":"72","oID":"239","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"6","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"768.00","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.04.01-2019.04.10","begin_time":"2019.04.01","end_time":"2019.04.10","status":"待支付","order_type":"1"},{"opayuserid":"72","oID":"238","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"6","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"768.00","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.04.01-2019.04.10","begin_time":"2019.04.01","end_time":"2019.04.10","status":"待支付","order_type":"1"},{"opayuserid":"72","oID":"235","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"7","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"896.00","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"待支付","order_type":"1"},{"opayuserid":"72","oID":"234","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"15","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"1920.00","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"待支付","order_type":"1"},{"opayuserid":"72","oID":"229","title":"少女时代演唱会","info":"","join_num":"1","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190228/b5985305073fb0d42a32d2e02fbb289a.png","opaymoney":"0.01","opaytype":"1","opayout":"0","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.03.12-2019.03.31","begin_time":"2019.03.12","end_time":"2019.03.31","status":"待支付","order_type":"1"},{"opayuserid":"72","oID":"224","title":"王洋测试期数","info":"","join_num":"1","pf_pic":"http://47.92.136.19/uploads/xingcheng/20190301/8a62c7ff6b6fdc4c090a99620f079b3a.png","opaymoney":"0.01","opaytype":"1","opayout":"1","allow_refund":"0","noname":"0","price_type":"自费","time_info":"2019.03.01-2019.03.03","begin_time":"2019.03.01","end_time":"2019.03.03","status":"退款中","order_type":"4"}]
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
         * opayuserid : 72
         * oID : 239
         * title : 带你游览东北雪乡
         * info : 没啥可说的，走就完了
         * join_num : 6
         * pf_pic : http://47.92.136.19/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg
         * opaymoney : 768.00
         * opaytype : 1
         * opayout : 0
         * allow_refund : 0
         * noname : 0
         * price_type : 自费
         * time_info : 2019.04.01-2019.04.10
         * begin_time : 2019.04.01
         * end_time : 2019.04.10
         * status : 待支付
         * order_type : 1
         */

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
        private String price_type;
        private String time_info;
        private String begin_time;
        private String end_time;
        private String status;
        private String order_type;

        private String orderStatus;
        private String User;
        private String BUser;

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
    }
}
