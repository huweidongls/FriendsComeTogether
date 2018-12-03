package com.yiwo.friendscometogether.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class SearchListModel {

    /**
     *
     * code : 200
     * message : 操作成功!
     * obj : [{"title":"删除测试","id":"109","type":"1"}]
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

    public static class ObjBean implements Serializable {
        /**
         * title : 删除测试
         * id : 109
         * type : 1
         */

        private String title;
        private String id;
        private String type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
