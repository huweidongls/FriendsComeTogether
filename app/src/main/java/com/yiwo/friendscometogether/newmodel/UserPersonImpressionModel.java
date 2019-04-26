package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/4/25.
 */

public class UserPersonImpressionModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"label_name":"帅呆了","num":"2"}]
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
         * label_name : 帅呆了
         * num : 2
         * labelID
         */

        private String label_name;
        private String num;
        private String labelID;

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getLabelID() {
            return labelID;
        }

        public void setLabelID(String labelID) {
            this.labelID = labelID;
        }
    }
}
