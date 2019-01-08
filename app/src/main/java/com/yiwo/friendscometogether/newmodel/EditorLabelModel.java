package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class EditorLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","t_name":"叫我逗比","t_type":"1","uid":"0","datetype":"1"},{"id":"2","t_name":"喜欢简单","t_type":"1","uid":"0","datetype":"1"},{"id":"3","t_name":"选择恐惧症","t_type":"1","uid":"0","datetype":"1"},{"id":"4","t_name":"文艺","t_type":"1","uid":"0","datetype":"1"},{"id":"5","t_name":"厚道","t_type":"1","uid":"0","datetype":"1"},{"id":"6","t_name":"女友永远是对的","t_type":"1","uid":"0","datetype":"1"},{"id":"7","t_name":"萌萌哒","t_type":"1","uid":"0","datetype":"0"},{"id":"8","t_name":"女汉子","t_type":"1","uid":"0","datetype":"0"},{"id":"9","t_name":"强迫症","t_type":"1","uid":"0","datetype":"0"},{"id":"10","t_name":"拖延症","t_type":"1","uid":"0","datetype":"0"},{"id":"11","t_name":"极品吃货","t_type":"1","uid":"0","datetype":"0"},{"id":"12","t_name":"双重人格","t_type":"1","uid":"0","datetype":"0"},{"id":"13","t_name":"敢爱敢恨","t_type":"1","uid":"0","datetype":"0"},{"id":"14","t_name":"宅","t_type":"1","uid":"0","datetype":"0"},{"id":"15","t_name":"靠谱","t_type":"1","uid":"0","datetype":"0"},{"id":"16","t_name":"局气","t_type":"1","uid":"0","datetype":"0"},{"id":"17","t_name":"有面","t_type":"1","uid":"0","datetype":"0"},{"id":"18","t_name":"讲义气","t_type":"1","uid":"0","datetype":"0"},{"id":"19","t_name":"AJ控","t_type":"1","uid":"0","datetype":"0"},{"id":"20","t_name":"大叔控","t_type":"1","uid":"0","datetype":"0"},{"id":"21","t_name":"马甲线","t_type":"1","uid":"0","datetype":"0"},{"id":"22","t_name":"长发及腰","t_type":"1","uid":"0","datetype":"0"},{"id":"23","t_name":"安静","t_type":"1","uid":"0","datetype":"0"},{"id":"24","t_name":"健谈","t_type":"1","uid":"0","datetype":"0"},{"id":"25","t_name":"随性","t_type":"1","uid":"0","datetype":"0"},{"id":"26","t_name":"叛逆","t_type":"1","uid":"0","datetype":"0"},{"id":"27","t_name":"热血","t_type":"1","uid":"0","datetype":"0"},{"id":"28","t_name":"理想主义","t_type":"1","uid":"0","datetype":"0"}]
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
         * t_name : 叫我逗比
         * t_type : 1
         * uid : 0
         * datetype : 1
         */

        private String id;
        private String t_name;
        private String t_type;
        private String uid;
        private String datetype;

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

        public String getDatetype() {
            return datetype;
        }

        public void setDatetype(String datetype) {
            this.datetype = datetype;
        }
    }
}
