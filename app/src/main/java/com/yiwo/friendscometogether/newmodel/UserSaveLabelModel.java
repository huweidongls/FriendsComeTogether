package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class UserSaveLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [[{"id":"1","t_name":"叫我逗比","t_type":"1","uid":"0"},{"id":"2","t_name":"喜欢简单","t_type":"1","uid":"0"},{"id":"3","t_name":"选择恐惧症","t_type":"1","uid":"0"},{"id":"4","t_name":"文艺","t_type":"1","uid":"0"},{"id":"5","t_name":"厚道","t_type":"1","uid":"0"},{"id":"6","t_name":"女友永远是对的","t_type":"1","uid":"0"}],[{"id":"47","t_name":"跑步","t_type":"2","uid":"0"},{"id":"48","t_name":"单车","t_type":"2","uid":"0"},{"id":"49","t_name":"篮球","t_type":"2","uid":"0"},{"id":"50","t_name":"爬山","t_type":"2","uid":"0"},{"id":"51","t_name":"游泳","t_type":"2","uid":"0"},{"id":"52","t_name":"瑜伽","t_type":"2","uid":"0"}],[{"id":"65","t_name":"薛之谦","t_type":"3","uid":"0"},{"id":"66","t_name":"流行","t_type":"3","uid":"0"},{"id":"67","t_name":"古典","t_type":"3","uid":"0"},{"id":"68","t_name":"周杰伦","t_type":"3","uid":"0"},{"id":"69","t_name":"陈奕迅","t_type":"3","uid":"0"},{"id":"70","t_name":"萧敬腾","t_type":"3","uid":"0"},{"id":"71","t_name":"林俊杰","t_type":"3","uid":"0"}],[{"id":"83","t_name":"火锅","t_type":"4","uid":"0"},{"id":"84","t_name":"麻辣烫","t_type":"4","uid":"0"},{"id":"86","t_name":"卤肉饭","t_type":"4","uid":"0"},{"id":"87","t_name":"北京烤鸭","t_type":"4","uid":"0"},{"id":"88","t_name":"早茶","t_type":"4","uid":"0"}],[{"id":"101","t_name":"泰坦尼克号","t_type":"5","uid":"0"},{"id":"102","t_name":"乱世佳人","t_type":"5","uid":"0"},{"id":"103","t_name":"战狼2","t_type":"5","uid":"0"},{"id":"104","t_name":"赌神","t_type":"5","uid":"0"},{"id":"105","t_name":"赌侠","t_type":"5","uid":"0"},{"id":"106","t_name":"赌圣","t_type":"5","uid":"0"}],[{"id":"119","t_name":"鲁迅","t_type":"6","uid":"0"},{"id":"120","t_name":"金庸","t_type":"6","uid":"0"},{"id":"121","t_name":"古龙","t_type":"6","uid":"0"},{"id":"122","t_name":"韩寒","t_type":"6","uid":"0"},{"id":"123","t_name":"郭敬明","t_type":"6","uid":"0"},{"id":"124","t_name":"三毛","t_type":"6","uid":"0"}],[{"id":"138","t_name":"丽江","t_type":"7","uid":"0"},{"id":"139","t_name":"三亚","t_type":"7","uid":"0"},{"id":"140","t_name":"广州","t_type":"7","uid":"0"},{"id":"141","t_name":"哈尔滨","t_type":"7","uid":"0"},{"id":"142","t_name":"山西","t_type":"7","uid":"0"},{"id":"143","t_name":"青海","t_type":"7","uid":"0"}]]
     */

    private int code;
    private String message;
    private List<List<ObjBean>> obj;

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

    public List<List<ObjBean>> getObj() {
        return obj;
    }

    public void setObj(List<List<ObjBean>> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 1
         * t_name : 叫我逗比
         * t_type : 1
         * uid : 0
         */

        private String id;
        private String t_name;
        private String t_type;
        private String uid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getT_name() {
            return t_name;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public String getT_type() {
            return t_type;
        }

        public void setT_type(String t_type) {
            this.t_type = t_type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
