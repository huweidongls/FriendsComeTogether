package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class ActiveCommentModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"join":"1","reply":"0","pfID":"2","pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","title":"泰山30日游","time":"2018.04.08-2018.06.05","list":[{"comment_id":"1","username":"我们旅游吧","content":"泰山真好玩"}]}]
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
         * join : 1
         * reply : 0
         * pfID : 2
         * pic : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
         * title : 泰山30日游
         * time : 2018.04.08-2018.06.05
         * list : [{"comment_id":"1","username":"我们旅游吧","content":"泰山真好玩"}]
         */

        private String join;
        private String reply;
        private String pfID;
        private String pic;
        private String title;
        private String time;
        private List<ListBean> list;

        public String getJoin() {
            return join;
        }

        public void setJoin(String join) {
            this.join = join;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * comment_id : 1
             * username : 我们旅游吧
             * content : 泰山真好玩
             */

            private String comment_id;
            private String username;
            private String content;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
