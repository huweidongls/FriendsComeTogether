package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/6/26.
 */

public class PersonMain_Youji_model {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"Friend":[{"pfID":"1723","pftitle":"jj","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190604/0-9408f5ceb51c6c80d11c63ef7da0a9269903.png","http://www.91yiwo.com/ylyy/uploads/article/20190604/1-9408f5ceb51c6c80d11c63ef7da0a9263866.png","http://www.91yiwo.com/ylyy/uploads/article/20190604/2-9408f5ceb51c6c80d11c63ef7da0a9267787.png"],"userID":"4","fmgood":"0","pfaddress":"天津","pflook":"11","pftime":"3星期前","fmcomment":"2","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[{"username":"花生","fcID":"73","fmID":"1723","fctitle":"饿得很him","fctime":"1561339240","userID":"4","buserID":"4","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"74","fmID":"1723","fctitle":"明明哦","fctime":"1561339281","userID":"4","buserID":"4","fcreply":"73","fcquote":null,"fcquoteid":null,"which_fcID":"73","greatcomment":"73"}],"commentcount":"2"},{"pfID":"1722","pftitle":"123","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190528/0-027c97bacb4e376f917d9d945bb7061a8817.jpg","http://www.91yiwo.com/ylyy/uploads/article/20190528/1-027c97bacb4e376f917d9d945bb7061a5245.png","http://www.91yiwo.com/ylyy/uploads/article/20190528/2-027c97bacb4e376f917d9d945bb7061a5637.jpg"],"userID":"4","fmgood":"0","pfaddress":"北京","pflook":"3","pftime":"4星期前","fmcomment":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"1721","pftitle":"e","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190528/0-9cba51b5b25da486f770e2721f269b569222.jpeg","http://www.91yiwo.com/ylyy/uploads/article/20190528/1-9cba51b5b25da486f770e2721f269b568371.jpeg","http://www.91yiwo.com/ylyy/uploads/article/20190528/2-3cf3f32f9c645d7184d06e223a67a9281516.jpeg","http://www.91yiwo.com/ylyy/uploads/article/20190528/3-3cf3f32f9c645d7184d06e223a67a9286784.jpg"],"userID":"4","fmgood":"0","pfaddress":"北京","pflook":"6","pftime":"4星期前","fmcomment":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"1719","pftitle":"a'da","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190516/0-8192bc2f064e905c942f61c47f7ccef71655.png"],"userID":"4","fmgood":"0","pfaddress":"哈尔滨","pflook":"8","pftime":"1个月前","fmcomment":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"1717","pftitle":"拆吧给","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190507/0-dba85f92686fbb30dcc42c5948f1ee5e5834.png"],"userID":"4","fmgood":"0","pfaddress":"北京","pflook":"13","pftime":"1个月前","fmcomment":"1","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[{"username":"花生","fcID":"70","fmID":"1717","fctitle":"评论","fctime":"1557987312","userID":"4","buserID":"4","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}],"commentcount":"1"},{"pfID":"723","pftitle":"123123","pfcontent":"","pfpic":["http://www.91yiwo.com/ylyy/uploads/article/20190419/0-f6f9370b885aebb131881cfd24d05dfd2823.png"],"userID":"4","fmgood":"1","pfaddress":"哈尔滨","pflook":"43","pftime":"2个月前","fmcomment":"3","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","comment_list":[{"username":"花生","fcID":"69","fmID":"723","fctitle":"把\n","fctime":"1557191515","userID":"4","buserID":"4","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"71","fmID":"723","fctitle":"啊啊啊","fctime":"1558338191","userID":"4","buserID":"4","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}],"commentcount":"3"}]}
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
        private List<FriendBean> Friend;

        public List<FriendBean> getFriend() {
            return Friend;
        }

        public void setFriend(List<FriendBean> Friend) {
            this.Friend = Friend;
        }

        public static class FriendBean {
            /**
             * pfID : 1723
             * pftitle : jj
             * pfcontent :
             * pfpic : ["http://www.91yiwo.com/ylyy/uploads/article/20190604/0-9408f5ceb51c6c80d11c63ef7da0a9269903.png","http://www.91yiwo.com/ylyy/uploads/article/20190604/1-9408f5ceb51c6c80d11c63ef7da0a9263866.png","http://www.91yiwo.com/ylyy/uploads/article/20190604/2-9408f5ceb51c6c80d11c63ef7da0a9267787.png"]
             * userID : 4
             * fmgood : 0
             * pfaddress : 天津
             * pflook : 11
             * pftime : 3星期前
             * fmcomment : 2
             * headportrait : http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png
             * username : 花生
             * comment_list : [{"username":"花生","fcID":"73","fmID":"1723","fctitle":"饿得很him","fctime":"1561339240","userID":"4","buserID":"4","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"74","fmID":"1723","fctitle":"明明哦","fctime":"1561339281","userID":"4","buserID":"4","fcreply":"73","fcquote":null,"fcquoteid":null,"which_fcID":"73","greatcomment":"73"}]
             * commentcount : 2
             */

            private String pfID;
            private String pftitle;
            private String pfcontent;
            private String userID;
            private String fmgood;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String fmcomment;
            private String headportrait;
            private String username;
            private String commentcount;
            private List<String> pfpic;
            private List<CommentListBean> comment_list;

            public String getPfID() {
                return pfID;
            }

            public void setPfID(String pfID) {
                this.pfID = pfID;
            }

            public String getPftitle() {
                return pftitle;
            }

            public void setPftitle(String pftitle) {
                this.pftitle = pftitle;
            }

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getFmgood() {
                return fmgood;
            }

            public void setFmgood(String fmgood) {
                this.fmgood = fmgood;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getFmcomment() {
                return fmcomment;
            }

            public void setFmcomment(String fmcomment) {
                this.fmcomment = fmcomment;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCommentcount() {
                return commentcount;
            }

            public void setCommentcount(String commentcount) {
                this.commentcount = commentcount;
            }

            public List<String> getPfpic() {
                return pfpic;
            }

            public void setPfpic(List<String> pfpic) {
                this.pfpic = pfpic;
            }

            public List<CommentListBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentListBean> comment_list) {
                this.comment_list = comment_list;
            }

            public static class CommentListBean {
                /**
                 * username : 花生
                 * fcID : 73
                 * fmID : 1723
                 * fctitle : 饿得很him
                 * fctime : 1561339240
                 * userID : 4
                 * buserID : 4
                 * fcreply : 0
                 * fcquote : null
                 * fcquoteid : null
                 * which_fcID : 0
                 * greatcomment : 0
                 */

                private String username;
                private String fcID;
                private String fmID;
                private String fctitle;
                private String fctime;
                private String userID;
                private String buserID;
                private String fcreply;
                private Object fcquote;
                private Object fcquoteid;
                private String which_fcID;
                private String greatcomment;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getFcID() {
                    return fcID;
                }

                public void setFcID(String fcID) {
                    this.fcID = fcID;
                }

                public String getFmID() {
                    return fmID;
                }

                public void setFmID(String fmID) {
                    this.fmID = fmID;
                }

                public String getFctitle() {
                    return fctitle;
                }

                public void setFctitle(String fctitle) {
                    this.fctitle = fctitle;
                }

                public String getFctime() {
                    return fctime;
                }

                public void setFctime(String fctime) {
                    this.fctime = fctime;
                }

                public String getUserID() {
                    return userID;
                }

                public void setUserID(String userID) {
                    this.userID = userID;
                }

                public String getBuserID() {
                    return buserID;
                }

                public void setBuserID(String buserID) {
                    this.buserID = buserID;
                }

                public String getFcreply() {
                    return fcreply;
                }

                public void setFcreply(String fcreply) {
                    this.fcreply = fcreply;
                }

                public Object getFcquote() {
                    return fcquote;
                }

                public void setFcquote(Object fcquote) {
                    this.fcquote = fcquote;
                }

                public Object getFcquoteid() {
                    return fcquoteid;
                }

                public void setFcquoteid(Object fcquoteid) {
                    this.fcquoteid = fcquoteid;
                }

                public String getWhich_fcID() {
                    return which_fcID;
                }

                public void setWhich_fcID(String which_fcID) {
                    this.which_fcID = which_fcID;
                }

                public String getGreatcomment() {
                    return greatcomment;
                }

                public void setGreatcomment(String greatcomment) {
                    this.greatcomment = greatcomment;
                }
            }
        }
    }
}
