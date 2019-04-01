package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class CommentFragmentModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"72","pfid":"157","opayuserid":"72","oID":"352","title":"活动不可退款，只限男性 且单身","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"1","noname":"0","phase":"1","if_comment":true,"price_type":"自费","time_info":"2019.03.28-2019.03.31","begin_time":"2019.03.28","end_time":"2019.03.31","status":"已结束","order_type":"3"},{"userID":"72","pfid":"144","opayuserid":"72","oID":"271","title":"评价测试","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190307/3b492e6c9ad0cbca779731193d549cfb.png","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"0","noname":"1","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.15-2019.03.30","begin_time":"2019.03.15","end_time":"2019.03.30","status":"已结束","order_type":"3"},{"userID":"72","pfid":"143","opayuserid":"72","oID":"266","title":"不可退款","info":"考拉","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190306/8b39edb13b417d6aeaab85484d77ff76.jpg","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"1","noname":"0","phase":"1","if_comment":true,"price_type":"自费","time_info":"2019.03.08-2019.03.09","begin_time":"2019.03.08","end_time":"2019.03.09","status":"已结束","order_type":"3"},{"userID":"72","pfid":"144","opayuserid":"72","oID":"263","title":"评价测试","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190307/3b492e6c9ad0cbca779731193d549cfb.png","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"2","if_comment":false,"price_type":"自费","time_info":"2019.03.07-2019.03.07","begin_time":"2019.03.07","end_time":"2019.03.07","status":"已结束","order_type":"3"},{"userID":"72","pfid":"133","opayuserid":"72","oID":"261","title":"王洋测试期数","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190301/8a62c7ff6b6fdc4c090a99620f079b3a.png","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"3","if_comment":false,"price_type":"自费","time_info":"2019.03.09-2019.03.10","begin_time":"2019.03.09","end_time":"2019.03.10","status":"已结束","order_type":"3"},{"userID":"72","pfid":"129","opayuserid":"72","oID":"246","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"128.00","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"已结束","order_type":"3"},{"userID":"72","pfid":"129","opayuserid":"72","oID":"244","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"128.00","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"已结束","order_type":"3"},{"userID":"72","pfid":"129","opayuserid":"72","oID":"233","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"128.00","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"已结束","order_type":"3"},{"userID":"72","pfid":"129","opayuserid":"72","oID":"230","title":"带你游览东北雪乡","info":"没啥可说的，走就完了","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190227/ddec87da3e2a62b6dc2867bc15156b53.jpg","opaymoney":"128.00","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.12-2019.03.30","begin_time":"2019.03.12","end_time":"2019.03.30","status":"已结束","order_type":"3"},{"userID":"72","pfid":"133","opayuserid":"72","oID":"225","title":"王洋测试期数","info":"","join_num":"1","pf_pic":"http://39.104.102.152/uploads/xingcheng/20190301/8a62c7ff6b6fdc4c090a99620f079b3a.png","opaymoney":"0.01","opaytype":"2","opayout":"0","allow_refund":"0","noname":"0","phase":"1","if_comment":false,"price_type":"自费","time_info":"2019.03.01-2019.03.03","begin_time":"2019.03.01","end_time":"2019.03.03","status":"已结束","order_type":"3"}]
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
         * oID : 352
         * title : 活动不可退款，只限男性 且单身
         * info :
         * join_num : 1
         * pf_pic : http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg
         * opaymoney : 0.01
         * opaytype : 2
         * opayout : 0
         * allow_refund : 1
         * noname : 0
         * phase : 1
         * if_comment : true
         * price_type : 自费
         * time_info : 2019.03.28-2019.03.31
         * begin_time : 2019.03.28
         * end_time : 2019.03.31
         * status : 已结束
         * order_type : 3
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
    }
}
