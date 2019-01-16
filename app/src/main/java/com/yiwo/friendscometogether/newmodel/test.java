package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/16.
 */

public class test {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"content":{"fmID":"285","fmtitle":"带密码的有记","fmpic":[{"pic":"http://47.92.136.19/uploads/article/20190116/0-b725ef375dc5861d4cd6acd2c751acb05836.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/1-e7e7837251875fbe8a59f16b209815863941.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/2-aac387c72ad802408dd72b5fdfe50eed7506.jpeg"},{"pic":"http://47.92.136.19/uploads/article/20190116/3-34cc7fdf98cdb583f761b8f9f805a9c04372.jpeg"}],"fmlook":"17","fmfavorite":"0","fmgotime":"","fmendtime":"","percapitacost":"","fmaddress":"","fmpartyID":"0","insertatext":"1","inserTtext":"0","usergrade":"1","username":"花生","userpic":"http://47.92.136.19/uploads/header/2019/01/14/43d6274beccd9831a3a94c95fe1a6c1715474577172.jpg","uid":"72","give":"0","Collection":"0","follow":"1"},"activityInfo":{"pfID":"","pfcontent":"","pftime":"","pflook":"","pfcomment":"","pfpic":""},"Renew":[],"InserList":[]}
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
         * content : {"fmID":"285","fmtitle":"带密码的有记","fmpic":[{"pic":"http://47.92.136.19/uploads/article/20190116/0-b725ef375dc5861d4cd6acd2c751acb05836.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/1-e7e7837251875fbe8a59f16b209815863941.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/2-aac387c72ad802408dd72b5fdfe50eed7506.jpeg"},{"pic":"http://47.92.136.19/uploads/article/20190116/3-34cc7fdf98cdb583f761b8f9f805a9c04372.jpeg"}],"fmlook":"17","fmfavorite":"0","fmgotime":"","fmendtime":"","percapitacost":"","fmaddress":"","fmpartyID":"0","insertatext":"1","inserTtext":"0","usergrade":"1","username":"花生","userpic":"http://47.92.136.19/uploads/header/2019/01/14/43d6274beccd9831a3a94c95fe1a6c1715474577172.jpg","uid":"72","give":"0","Collection":"0","follow":"1"}
         * activityInfo : {"pfID":"","pfcontent":"","pftime":"","pflook":"","pfcomment":"","pfpic":""}
         * Renew : []
         * InserList : []
         */

        private ContentBean content;
        private ActivityInfoBean activityInfo;
        private List<?> Renew;
        private List<?> InserList;

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public ActivityInfoBean getActivityInfo() {
            return activityInfo;
        }

        public void setActivityInfo(ActivityInfoBean activityInfo) {
            this.activityInfo = activityInfo;
        }

        public List<?> getRenew() {
            return Renew;
        }

        public void setRenew(List<?> Renew) {
            this.Renew = Renew;
        }

        public List<?> getInserList() {
            return InserList;
        }

        public void setInserList(List<?> InserList) {
            this.InserList = InserList;
        }

        public static class ContentBean {
            /**
             * fmID : 285
             * fmtitle : 带密码的有记
             * fmpic : [{"pic":"http://47.92.136.19/uploads/article/20190116/0-b725ef375dc5861d4cd6acd2c751acb05836.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/1-e7e7837251875fbe8a59f16b209815863941.jpg"},{"pic":"http://47.92.136.19/uploads/article/20190116/2-aac387c72ad802408dd72b5fdfe50eed7506.jpeg"},{"pic":"http://47.92.136.19/uploads/article/20190116/3-34cc7fdf98cdb583f761b8f9f805a9c04372.jpeg"}]
             * fmlook : 17
             * fmfavorite : 0
             * fmgotime :
             * fmendtime :
             * percapitacost :
             * fmaddress :
             * fmpartyID : 0
             * insertatext : 1
             * inserTtext : 0
             * usergrade : 1
             * username : 花生
             * userpic : http://47.92.136.19/uploads/header/2019/01/14/43d6274beccd9831a3a94c95fe1a6c1715474577172.jpg
             * uid : 72
             * give : 0
             * Collection : 0
             * follow : 1
             */

            private String fmID;
            private String fmtitle;
            private String fmlook;
            private String fmfavorite;
            private String fmgotime;
            private String fmendtime;
            private String percapitacost;
            private String fmaddress;
            private String fmpartyID;
            private String insertatext;
            private String inserTtext;
            private String usergrade;
            private String username;
            private String userpic;
            private String uid;
            private String give;
            private String Collection;
            private String follow;
            private List<FmpicBean> fmpic;

            public String getFmID() {
                return fmID;
            }

            public void setFmID(String fmID) {
                this.fmID = fmID;
            }

            public String getFmtitle() {
                return fmtitle;
            }

            public void setFmtitle(String fmtitle) {
                this.fmtitle = fmtitle;
            }

            public String getFmlook() {
                return fmlook;
            }

            public void setFmlook(String fmlook) {
                this.fmlook = fmlook;
            }

            public String getFmfavorite() {
                return fmfavorite;
            }

            public void setFmfavorite(String fmfavorite) {
                this.fmfavorite = fmfavorite;
            }

            public String getFmgotime() {
                return fmgotime;
            }

            public void setFmgotime(String fmgotime) {
                this.fmgotime = fmgotime;
            }

            public String getFmendtime() {
                return fmendtime;
            }

            public void setFmendtime(String fmendtime) {
                this.fmendtime = fmendtime;
            }

            public String getPercapitacost() {
                return percapitacost;
            }

            public void setPercapitacost(String percapitacost) {
                this.percapitacost = percapitacost;
            }

            public String getFmaddress() {
                return fmaddress;
            }

            public void setFmaddress(String fmaddress) {
                this.fmaddress = fmaddress;
            }

            public String getFmpartyID() {
                return fmpartyID;
            }

            public void setFmpartyID(String fmpartyID) {
                this.fmpartyID = fmpartyID;
            }

            public String getInsertatext() {
                return insertatext;
            }

            public void setInsertatext(String insertatext) {
                this.insertatext = insertatext;
            }

            public String getInserTtext() {
                return inserTtext;
            }

            public void setInserTtext(String inserTtext) {
                this.inserTtext = inserTtext;
            }

            public String getUsergrade() {
                return usergrade;
            }

            public void setUsergrade(String usergrade) {
                this.usergrade = usergrade;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getGive() {
                return give;
            }

            public void setGive(String give) {
                this.give = give;
            }

            public String getCollection() {
                return Collection;
            }

            public void setCollection(String Collection) {
                this.Collection = Collection;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public List<FmpicBean> getFmpic() {
                return fmpic;
            }

            public void setFmpic(List<FmpicBean> fmpic) {
                this.fmpic = fmpic;
            }

            public static class FmpicBean {
                /**
                 * pic : http://47.92.136.19/uploads/article/20190116/0-b725ef375dc5861d4cd6acd2c751acb05836.jpg
                 */

                private String pic;

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }
            }
        }

        public static class ActivityInfoBean {
            /**
             * pfID :
             * pfcontent :
             * pftime :
             * pflook :
             * pfcomment :
             * pfpic :
             */

            private String pfID;
            private String pfcontent;
            private String pftime;
            private String pflook;
            private String pfcomment;
            private String pfpic;

            public String getPfID() {
                return pfID;
            }

            public void setPfID(String pfID) {
                this.pfID = pfID;
            }

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPfcomment() {
                return pfcomment;
            }

            public void setPfcomment(String pfcomment) {
                this.pfcomment = pfcomment;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
            }
        }
    }
}
