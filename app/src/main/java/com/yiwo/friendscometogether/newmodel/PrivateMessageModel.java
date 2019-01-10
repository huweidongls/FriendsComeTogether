package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/1/10.
 */

public class PrivateMessageModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"2","uid":"38","bid":"7","times":"2019-01-10 16:28:42","radio":"0","titleusername":"一样一样","sex":"1","yxuser":"yy18346038613","label":"叫我逗比,叛逆","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg"}]
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
         * id : 2
         * uid : 38
         * bid : 7
         * times : 2019-01-10 16:28:42
         * radio : 0
         * titleusername : 一样一样
         * sex : 1
         * yxuser : yy18346038613
         * label : 叫我逗比,叛逆
         * userpic : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         */

        private String id;
        private String uid;
        private String bid;
        private String times;
        private String radio;
        private String titleusername;
        private String sex;
        private String yxuser;
        private String label;
        private String userpic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getTitleusername() {
            return titleusername;
        }

        public void setTitleusername(String titleusername) {
            this.titleusername = titleusername;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getYxuser() {
            return yxuser;
        }

        public void setYxuser(String yxuser) {
            this.yxuser = yxuser;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }
    }
}
