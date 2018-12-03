package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ArticleCommentListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fcID":"6","fmID":"62","fctitle":"测试评论1","fctime":"2018-08-02 15:08:42","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[{"fcID":"18","fmID":"62","fctitle":"0492_33回复:吃了:你好菜啊!","fctime":"2018-08-02 16:24:54","userID":"33","buserID":"7","fcreply":"6","which_fcID":"6","username":"0492_33"}]},{"fcID":"7","fmID":"62","fctitle":"测试评论2","fctime":"2018-08-02 15:08:45","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"8","fmID":"62","fctitle":"测试评论3","fctime":"2018-08-02 15:08:46","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"9","fmID":"62","fctitle":"测试评论4","fctime":"2018-08-02 15:08:47","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"10","fmID":"62","fctitle":"测试评论5","fctime":"2018-08-02 15:08:47","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"11","fmID":"62","fctitle":"测试评论6","fctime":"2018-08-02 15:08:48","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"12","fmID":"62","fctitle":"测试评论7","fctime":"2018-08-02 15:08:49","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"13","fmID":"62","fctitle":"测试评论8","fctime":"2018-08-02 15:08:49","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"14","fmID":"62","fctitle":"测试评论9","fctime":"2018-08-02 15:08:49","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"15","fmID":"62","fctitle":"测试评论10","fctime":"2018-08-02 15:08:50","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]},{"fcID":"16","fmID":"62","fctitle":"测试评论11","fctime":"2018-08-02 15:08:50","userID":"7","buserID":"0","fcreply":"0","which_fcID":"0","newsTile":"恶魔","username":"吃了","userpic":"http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg","pic":[]}]
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
         * fcID : 6
         * fmID : 62
         * fctitle : 测试评论1
         * fctime : 2018-08-02 15:08:42
         * userID : 7
         * buserID : 0
         * fcreply : 0
         * which_fcID : 0
         * newsTile : 恶魔
         * username : 吃了
         * userpic : http://47.92.136.19/uploads/header/2018/08/01/8071aac5eae40520f0e103fc4e40c18115330957024.jpg
         * pic : [{"fcID":"18","fmID":"62","fctitle":"0492_33回复:吃了:你好菜啊!","fctime":"2018-08-02 16:24:54","userID":"33","buserID":"7","fcreply":"6","which_fcID":"6","username":"0492_33"}]
         */

        private String fcID;
        private String fmID;
        private String fctitle;
        private String fctime;
        private String userID;
        private String buserID;
        private String fcreply;
        private String which_fcID;
        private String newsTile;
        private String username;
        private String userpic;
        private List<PicBean> pic;

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

        public String getWhich_fcID() {
            return which_fcID;
        }

        public void setWhich_fcID(String which_fcID) {
            this.which_fcID = which_fcID;
        }

        public String getNewsTile() {
            return newsTile;
        }

        public void setNewsTile(String newsTile) {
            this.newsTile = newsTile;
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

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean {
            /**
             * fcID : 18
             * fmID : 62
             * fctitle : 0492_33回复:吃了:你好菜啊!
             * fctime : 2018-08-02 16:24:54
             * userID : 33
             * buserID : 7
             * fcreply : 6
             * which_fcID : 6
             * username : 0492_33
             */

            private String fcID;
            private String fmID;
            private String fctitle;
            private String fctime;
            private String userID;
            private String buserID;
            private String fcreply;
            private String which_fcID;
            private String username;

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

            public String getWhich_fcID() {
                return which_fcID;
            }

            public void setWhich_fcID(String which_fcID) {
                this.which_fcID = which_fcID;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
