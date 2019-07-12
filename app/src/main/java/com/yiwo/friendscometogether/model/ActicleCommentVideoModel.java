package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/7/12.
 */

public class ActicleCommentVideoModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"vcID":"1","vcontent":"sdfsdf","vctime":"1970-01-01 08:00","user_ID":"1","buserID":"0","parent_id":"0","username":"大圣","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/07/33acc778077a0fcf6482d1c8d00a60a615572233957.png","vname":"兴冲冲","replyList":[{"vcID":"1","vcontent":"uuuuu","vctime":"1970-01-01 08:00","user_ID":"1","buserID":"0","username":"花生","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","parent_id":"0"}]},{"vcID":"2","vcontent":"撒打发第三方","vctime":"1970-01-01 08:00","user_ID":"2","buserID":"0","parent_id":"0","username":"0912_2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/16/edd332a24aa184d451e60918fdebc93115553941530.png","vname":"兴冲冲","replyList":[{"vcID":"2","vcontent":"黑衣人突然","vctime":"1970-01-01 08:00","user_ID":"2","buserID":"0","username":"小楠","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/24ce704c41a6f886dc474f970817496315549635215.png","parent_id":"0"},{"vcID":"2","vcontent":"核桃仁让他","vctime":"1970-01-01 08:00","user_ID":"2","buserID":"0","username":null,"userpic":"http://www.91yiwo.com/ylyy/","parent_id":"0"}]},{"vcID":"3","vcontent":"违法未www","vctime":"1970-01-01 08:00","user_ID":"3","buserID":"0","parent_id":"0","username":"1164_3","userpic":"http://www.91yiwo.com/ylyy//01786557e4a6fa0000018c1bf080ca.png","vname":"兴冲冲","replyList":[]},{"vcID":"4","vcontent":"uuuuu","vctime":"1970-01-01 08:00","user_ID":"4","buserID":"1","parent_id":"1","username":"花生","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","vname":"兴冲冲","replyList":[]},{"vcID":"5","vcontent":"黑衣人突然","vctime":"1970-01-01 08:00","user_ID":"5","buserID":"2","parent_id":"2","username":"小楠","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/24ce704c41a6f886dc474f970817496315549635215.png","vname":"兴冲冲","replyList":[]},{"vcID":"6","vcontent":"核桃仁让他","vctime":"1970-01-01 08:00","user_ID":"6","buserID":"2","parent_id":"2","username":null,"userpic":"http://www.91yiwo.com/ylyy/","vname":"兴冲冲","replyList":[]}]
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
         * vcID : 1
         * vcontent : sdfsdf
         * vctime : 1970-01-01 08:00
         * user_ID : 1
         * buserID : 0
         * parent_id : 0
         * username : 大圣
         * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/05/07/33acc778077a0fcf6482d1c8d00a60a615572233957.png
         * vname : 兴冲冲
         * replyList : [{"vcID":"1","vcontent":"uuuuu","vctime":"1970-01-01 08:00","user_ID":"1","buserID":"0","username":"花生","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","parent_id":"0"}]
         */

        private String vcID;
        private String vcontent;
        private String vctime;
        private String user_ID;
        private String buserID;
        private String parent_id;
        private String username;
        private String userpic;
        private String vname;
        private List<ReplyListBean> replyList;

        public String getVcID() {
            return vcID;
        }

        public void setVcID(String vcID) {
            this.vcID = vcID;
        }

        public String getVcontent() {
            return vcontent;
        }

        public void setVcontent(String vcontent) {
            this.vcontent = vcontent;
        }

        public String getVctime() {
            return vctime;
        }

        public void setVctime(String vctime) {
            this.vctime = vctime;
        }

        public String getUser_ID() {
            return user_ID;
        }

        public void setUser_ID(String user_ID) {
            this.user_ID = user_ID;
        }

        public String getBuserID() {
            return buserID;
        }

        public void setBuserID(String buserID) {
            this.buserID = buserID;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            /**
             * vcID : 1
             * vcontent : uuuuu
             * vctime : 1970-01-01 08:00
             * user_ID : 1
             * buserID : 0
             * username : 花生
             * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png
             * parent_id : 0
             */

            private String vcID;
            private String vcontent;
            private String vctime;
            private String user_ID;
            private String buserID;
            private String username;
            private String userpic;
            private String parent_id;

            public String getVcID() {
                return vcID;
            }

            public void setVcID(String vcID) {
                this.vcID = vcID;
            }

            public String getVcontent() {
                return vcontent;
            }

            public void setVcontent(String vcontent) {
                this.vcontent = vcontent;
            }

            public String getVctime() {
                return vctime;
            }

            public void setVctime(String vctime) {
                this.vctime = vctime;
            }

            public String getUser_ID() {
                return user_ID;
            }

            public void setUser_ID(String user_ID) {
                this.user_ID = user_ID;
            }

            public String getBuserID() {
                return buserID;
            }

            public void setBuserID(String buserID) {
                this.buserID = buserID;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }
        }
    }
}
