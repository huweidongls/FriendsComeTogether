package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */

public class HomeDataModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"pfID":"248","pftitle":"测试一下","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20181226/0-885a97a939554ec1b14b493918f05a4d2864.jpeg","http://47.92.136.19/uploads/article/20181226/1-5b34000a6bbc170f5ebea7b751d5d9864773.jpg","http://47.92.136.19/uploads/article/20181226/2-405fb6e455d09f04d9a0e810fe43178d1091.jpeg","http://47.92.136.19/uploads/article/20181226/3-1f4af4195b0e731d1f7de82b341563d07912.jpg"],"pfaddress":"NBA","pflook":"1","pftime":"20小时前","follow":"0","fmcomment":"1","headportrait":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","username":"老司机","author":"","comment_list":[{"fcID":"180","fmID":"248","fctitle":"咯哦哦","fctime":"1538184060","userID":"55","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"fcID":"181","fmID":"248","fctitle":"监控","fctime":"1538184065","userID":"55","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}],"commentcount":4,"data_type":"2"}]
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
         * pfID : 248
         * pftitle : 测试一下
         * pfcontent :
         * pfpic : ["http://47.92.136.19/uploads/article/20181226/0-885a97a939554ec1b14b493918f05a4d2864.jpeg","http://47.92.136.19/uploads/article/20181226/1-5b34000a6bbc170f5ebea7b751d5d9864773.jpg","http://47.92.136.19/uploads/article/20181226/2-405fb6e455d09f04d9a0e810fe43178d1091.jpeg","http://47.92.136.19/uploads/article/20181226/3-1f4af4195b0e731d1f7de82b341563d07912.jpg"]
         * pfaddress : NBA
         * pflook : 1
         * pftime : 20小时前
         * follow : 0
         * fmcomment : 1
         * headportrait : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         * username : 老司机
         * author :
         * comment_list : [{"fcID":"180","fmID":"248","fctitle":"咯哦哦","fctime":"1538184060","userID":"55","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"fcID":"181","fmID":"248","fctitle":"监控","fctime":"1538184065","userID":"55","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}]
         * commentcount : 4
         * data_type : 2
         * vurl : ""
         */

        private String pfID;
        private String pftitle;
        private String pfcontent;
        private String pfaddress;
        private String pflook;
        private String pftime;
        private String follow;
        private String fmcomment;
        private String headportrait;
        private String username;
        private String author;
        private String commentcount;
        private String data_type;
        private List<String> pfpic;
        private List<CommentListBean> comment_list;
        private String userID;
        private String fmgood;
        private String pfpwd;
        private String vurl;

        public String getPfpwd() {
            return pfpwd;
        }

        public void setPfpwd(String pfpwd) {
            this.pfpwd = pfpwd;
        }

        public String getFmgood() {
            return fmgood;
        }

        public void setFmgood(String fmgood) {
            this.fmgood = fmgood;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

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

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public String getData_type() {
            return data_type;
        }

        public void setData_type(String data_type) {
            this.data_type = data_type;
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

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public static class CommentListBean {
            /**
             * fcID : 180
             * fmID : 248
             * fctitle : 咯哦哦
             * fctime : 1538184060
             * userID : 55
             * buserID : 0
             * fcreply : 0
             * fcquote : null
             * fcquoteid : null
             * which_fcID : 0
             * greatcomment : 0
             */

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
            private String username;

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
