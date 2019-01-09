package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class EditorLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"t_name":"叫我逗比","t_type":"1","datetype":"1"},{"t_name":"喜欢简单","t_type":"1","datetype":"1"},{"t_name":"选择恐惧症","t_type":"1","datetype":"1"},{"t_name":"文艺","t_type":"1","datetype":"1"},{"t_name":"厚道","t_type":"1","datetype":"0"},{"t_name":"女友永远是对的","t_type":"1","datetype":"0"},{"t_name":"萌萌哒","t_type":"1","datetype":"0"},{"t_name":"女汉子","t_type":"1","datetype":"0"},{"t_name":"强迫症","t_type":"1","datetype":"0"},{"t_name":"拖延症","t_type":"1","datetype":"0"},{"t_name":"极品吃货","t_type":"1","datetype":"0"},{"t_name":"双重人格","t_type":"1","datetype":"0"},{"t_name":"敢爱敢恨","t_type":"1","datetype":"0"},{"t_name":"宅","t_type":"1","datetype":"0"},{"t_name":"靠谱","t_type":"1","datetype":"0"},{"t_name":"局气","t_type":"1","datetype":"0"},{"t_name":"有面","t_type":"1","datetype":"0"},{"t_name":"讲义气","t_type":"1","datetype":"0"},{"t_name":"AJ控","t_type":"1","datetype":"0"},{"t_name":"大叔控","t_type":"1","datetype":"0"},{"t_name":"马甲线","t_type":"1","datetype":"0"},{"t_name":"长发及腰","t_type":"1","datetype":"0"},{"t_name":"安静","t_type":"1","datetype":"0"},{"t_name":"健谈","t_type":"1","datetype":"0"},{"t_name":"随性","t_type":"1","datetype":"0"},{"t_name":"叛逆","t_type":"1","datetype":"0"},{"t_name":"热血","t_type":"1","datetype":"0"},{"t_name":"理想主义","t_type":"1","datetype":"0"},{"t_name":"测试","t_type":"1","datetype":"1"}]
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
         * t_name : 叫我逗比
         * t_type : 1
         * datetype : 1
         */

        private String t_name;
        private String t_type;
        private String datetype;

        public ObjBean(String t_name, String t_type, String datetype) {
            this.t_name = t_name;
            this.t_type = t_type;
            this.datetype = datetype;
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

        public String getDatetype() {
            return datetype;
        }

        public void setDatetype(String datetype) {
            this.datetype = datetype;
        }
    }
}
