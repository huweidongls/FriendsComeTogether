package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/20.
 */

public class HomeMessageCenterModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"comment":{"type":"1","message":"吃了回复了你：你也好","time":"2018-08-18-"},"hot":{"type":"1","message":"奥巴马来了","time":"1970-01-15"},"system":{"type":"1","message":"奥巴马来了","time":"1970-01-15"},"yq":{"type":"0","message":"暂无邀请消息","time":""},"friends":{"type":"0","message":"暂无好友信息","time":"2018-08-20"}}
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
         * comment : {"type":"1","message":"吃了回复了你：你也好","time":"2018-08-18-"}
         * hot : {"type":"1","message":"奥巴马来了","time":"1970-01-15"}
         * system : {"type":"1","message":"奥巴马来了","time":"1970-01-15"}
         * yq : {"type":"0","message":"暂无邀请消息","time":""}
         * friends : {"type":"0","message":"暂无好友信息","time":"2018-08-20"}
         */

        private CommentBean comment;
        private HotBean hot;
        private SystemBean system;
        private YqBean yq;
        private FriendsBean friends;

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public HotBean getHot() {
            return hot;
        }

        public void setHot(HotBean hot) {
            this.hot = hot;
        }

        public SystemBean getSystem() {
            return system;
        }

        public void setSystem(SystemBean system) {
            this.system = system;
        }

        public YqBean getYq() {
            return yq;
        }

        public void setYq(YqBean yq) {
            this.yq = yq;
        }

        public FriendsBean getFriends() {
            return friends;
        }

        public void setFriends(FriendsBean friends) {
            this.friends = friends;
        }

        public static class CommentBean {
            /**
             * type : 1
             * message : 吃了回复了你：你也好
             * time : 2018-08-18-
             */

            private String type;
            private String message;
            private String time;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class HotBean {
            /**
             * type : 1
             * message : 奥巴马来了
             * time : 1970-01-15
             */

            private String type;
            private String message;
            private String time;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class SystemBean {
            /**
             * type : 1
             * message : 奥巴马来了
             * time : 1970-01-15
             */

            private String type;
            private String message;
            private String time;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class YqBean {
            /**
             * type : 0
             * message : 暂无邀请消息
             * time :
             */

            private String type;
            private String message;
            private String time;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class FriendsBean {
            /**
             * type : 0
             * message : 暂无好友信息
             * time : 2018-08-20
             */

            private String type;
            private String message;
            private String time;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
