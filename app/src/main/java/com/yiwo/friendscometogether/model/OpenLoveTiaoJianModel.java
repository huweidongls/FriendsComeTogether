package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/8/22.
 */

public class OpenLoveTiaoJianModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : {"title":"开启寻爱模式",
     * "item":[{"name":"renzheng","text":"完成实名认证","status":"1"},{"name":"tags","text":"完成个性标签设置","status":"1"}],
     * "info":"模式开启后,可搜索匹配个性,可选择心动男生/女生",
     * "start":"1" //0不可以开启
     * }
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * title : 开启寻爱模式
         * item : [{"name":"renzheng","text":"完成实名认证","status":"1"},{"name":"tags","text":"完成个性标签设置","status":"1"}]
         * info : 模式开启后,可搜索匹配个性,可选择心动男生/女生
         * start : 1
         */

        private String title;
        private String info;
        private String start;
        private List<ItemBean> item;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * name : renzheng
             * text : 完成实名认证
             * status : 1
             */

            private String name;
            private String text;
            private String status;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
