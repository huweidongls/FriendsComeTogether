package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/4/24.
 */

public class PersonImpressonModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","label_name":"帅呆了"},{"id":"2","label_name":"有责任感"},{"id":"3","label_name":"渣男"},{"id":"4","label_name":"暖男"},{"id":"5","label_name":"勤劳"},{"id":"12","label_name":"诚实"},{"id":"13","label_name":"自私自利"}]
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
         * id : 1
         * label_name : 帅呆了
         */

        private String id;
        private String label_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }
    }
}
