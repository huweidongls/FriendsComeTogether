package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/1/10.
 */

public class PrivateMessageModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"79","uid":"79","bid":"85","times":"2019-03-30 16:23:20","radio":"0","type":"1","helloMessage":"王洋入群申请","group_id":"1634293997","group_name":"","titleusername":"Wy","sex":"0","yxuser":"yy17645780912","label":"叫我逗比,局气","userpic":"http://39.104.102.152/uploads/header/2019/03/19/51bdce1916eb96077b968a6bd6f9a11f155297747620.jpg"},{"id":"78","uid":"72","bid":"85","times":"2019-03-30 16:15:57","radio":"0","type":"1","helloMessage":"史蒂夫","group_id":"1634293997","group_name":"","titleusername":"花生","sex":"0","yxuser":"yy15754633415","label":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","userpic":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg"},{"id":"77","uid":"72","bid":"85","times":"2019-03-30 16:15:53","radio":"0","type":"1","helloMessage":"史蒂夫","group_id":"1634293997","group_name":"","titleusername":"花生","sex":"0","yxuser":"yy15754633415","label":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","userpic":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg"}]
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
         * id : 79
         * uid : 79
         * bid : 85
         * times : 2019-03-30 16:23:20
         * radio : 0
         * type : 1
         * helloMessage : 王洋入群申请
         * group_id : 1634293997
         * group_name :
         * titleusername : Wy
         * sex : 0
         * yxuser : yy17645780912
         * label : 叫我逗比,局气
         * userpic : http://39.104.102.152/uploads/header/2019/03/19/51bdce1916eb96077b968a6bd6f9a11f155297747620.jpg
         */

        private String id;
        private String uid;
        private String bid;
        private String times;
        private String radio;
        private String type;
        private String helloMessage;
        private String group_id;
        private String group_name;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHelloMessage() {
            return helloMessage;
        }

        public void setHelloMessage(String helloMessage) {
            this.helloMessage = helloMessage;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
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
