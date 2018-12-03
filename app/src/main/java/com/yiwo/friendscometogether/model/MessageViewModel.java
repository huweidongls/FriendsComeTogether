package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class MessageViewModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"mes_id":"1","mes_title":"奥巴马来了","mes_message":"奥巴马哈哈哈大笑，对武松说：\"我认识你，你是张翼德\"","mes_pic":"","mes_time":"1970-01-15"}]
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
         * mes_id : 1
         * mes_title : 奥巴马来了
         * mes_message : 奥巴马哈哈哈大笑，对武松说："我认识你，你是张翼德"
         * mes_pic :
         * mes_time : 1970-01-15
         */

        private String mes_id;
        private String mes_title;
        private String mes_message;
        private String mes_pic;
        private String mes_time;

        public String getMes_id() {
            return mes_id;
        }

        public void setMes_id(String mes_id) {
            this.mes_id = mes_id;
        }

        public String getMes_title() {
            return mes_title;
        }

        public void setMes_title(String mes_title) {
            this.mes_title = mes_title;
        }

        public String getMes_message() {
            return mes_message;
        }

        public void setMes_message(String mes_message) {
            this.mes_message = mes_message;
        }

        public String getMes_pic() {
            return mes_pic;
        }

        public void setMes_pic(String mes_pic) {
            this.mes_pic = mes_pic;
        }

        public String getMes_time() {
            return mes_time;
        }

        public void setMes_time(String mes_time) {
            this.mes_time = mes_time;
        }
    }
}
