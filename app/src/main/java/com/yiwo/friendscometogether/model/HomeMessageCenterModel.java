package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/20.
 */

public class HomeMessageCenterModel {


    /**
     * code : 200
     * message : 获取成功!
     * obj : {"comment":{"type":"0","message":"暂无评论消息","time":""},"hot":{"type":"0","message":"暂无热门消息","time":""},"system":{"type":"0","message":"暂无系统新消息","time":""},"yq":{"type":"0","message":"暂无邀请消息","time":""},"friends":{"type":"0","message":"暂无好友消息","time":""},"Private":{"type":"1","message":"一样一样跟你打了个招呼!","time":"2019-01-10"}}
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
         * comment : {"type":"0","message":"暂无评论消息","time":""}
         * hot : {"type":"0","message":"暂无热门消息","time":""}
         * system : {"type":"0","message":"暂无系统新消息","time":""}
         * yq : {"type":"0","message":"暂无邀请消息","time":""}
         * friends : {"type":"0","message":"暂无好友消息","time":""}
         * Private : {"type":"1","message":"一样一样跟你打了个招呼!","time":"2019-01-10"}
         */

        private CommentBean comment;
        private HotBean hot;
        private SystemBean system;
        private YqBean yq;
        private FriendsBean friends;
        private PrivateBean Private;

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

        public PrivateBean getPrivate() {
            return Private;
        }

        public void setPrivate(PrivateBean Private) {
            this.Private = Private;
        }

        public static class CommentBean {
            /**
             * type : 0
             * message : 暂无评论消息
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

        public static class HotBean {
            /**
             * type : 0
             * message : 暂无热门消息
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

        public static class SystemBean {
            /**
             * type : 0
             * message : 暂无系统新消息
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
             * message : 暂无好友消息
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

        public static class PrivateBean {
            /**
             * type : 1
             * message : 一样一样跟你打了个招呼!
             * time : 2019-01-10
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
