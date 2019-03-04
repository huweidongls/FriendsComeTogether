package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/3/4.
 */

public class ApplyChooseDateModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"phase_id":"5","phase_num":"1","phase_sign_up_time":"2019-03-20 12:56","phase_begin_time":"2019-03-25 12:57","phase_over_time":"2019-03-29 12:57","pfID":"130","phase_price":"0.10"}]
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
         * phase_id : 5
         * phase_num : 1
         * phase_sign_up_time : 2019-03-20 12:56
         * phase_begin_time : 2019-03-25 12:57
         * phase_over_time : 2019-03-29 12:57
         * pfID : 130
         * phase_price : 0.10
         */

        private String phase_id;
        private String phase_num;
        private String phase_sign_up_time;
        private String phase_begin_time;
        private String phase_over_time;
        private String pfID;
        private String phase_price;
        private Boolean isSelected = false;//自定义字段，是否选中
        public String getPhase_id() {
            return phase_id;
        }

        public void setPhase_id(String phase_id) {
            this.phase_id = phase_id;
        }

        public String getPhase_num() {
            return phase_num;
        }

        public void setPhase_num(String phase_num) {
            this.phase_num = phase_num;
        }

        public String getPhase_sign_up_time() {
            return phase_sign_up_time;
        }

        public void setPhase_sign_up_time(String phase_sign_up_time) {
            this.phase_sign_up_time = phase_sign_up_time;
        }

        public String getPhase_begin_time() {
            return phase_begin_time;
        }

        public void setPhase_begin_time(String phase_begin_time) {
            this.phase_begin_time = phase_begin_time;
        }

        public String getPhase_over_time() {
            return phase_over_time;
        }

        public void setPhase_over_time(String phase_over_time) {
            this.phase_over_time = phase_over_time;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPhase_price() {
            return phase_price;
        }

        public void setPhase_price(String phase_price) {
            this.phase_price = phase_price;
        }

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }
    }
}
