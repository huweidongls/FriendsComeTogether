package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class CommentFragmentModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"oID":"56","title":"登峨眉山！","info":"登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！","join_num":"1","pf_pic":"http://47.92.136.19/uploads/activity/20180720/20180720/73cc839014b7516c8d975ec3aa52e382.jpg","opaymoney":"1.00","opaytype":"2","opayout":"0","price_type":"自费","time_info":"2018.07.30-2018.08.01","status":"行程结束","order_type":"3"},{"oID":"54","title":"登峨眉山！","info":"登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！","join_num":"1","pf_pic":"http://47.92.136.19/uploads/activity/20180720/20180720/73cc839014b7516c8d975ec3aa52e382.jpg","opaymoney":"1.00","opaytype":"2","opayout":"0","price_type":"自费","time_info":"2018.07.30-2018.08.01","status":"行程结束","order_type":"3"}]
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
         * oID : 56
         * title : 登峨眉山！
         * info : 登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！登峨眉山！
         * join_num : 1
         * pf_pic : http://47.92.136.19/uploads/activity/20180720/20180720/73cc839014b7516c8d975ec3aa52e382.jpg
         * opaymoney : 1.00
         * opaytype : 2
         * opayout : 0
         * price_type : 自费
         * time_info : 2018.07.30-2018.08.01
         * status : 行程结束
         * order_type : 3
         */

        private String oID;
        private String title;
        private String info;
        private String join_num;
        private String pf_pic;
        private String opaymoney;
        private String opaytype;
        private String opayout;
        private String price_type;
        private String time_info;
        private String status;
        private String order_type;

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
