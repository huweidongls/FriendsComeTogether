package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class EditorFriendRememberModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : {"FriendsList":{"fmID":"17","fmtitle":"旅途","fmpic":"http://47.92.136.19/http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","fmgotime":"2018-07-26","fmendtime":"2018-07-31","percapitacost":"123.00","fmlook":"0","fmfavorite":"0"},"RenewList":[{"ffID":"4","fftitle":"恶魔猎手"}]}
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
         * FriendsList : {"fmID":"17","fmtitle":"旅途","fmpic":"http://47.92.136.19/http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","fmgotime":"2018-07-26","fmendtime":"2018-07-31","percapitacost":"123.00","fmlook":"0","fmfavorite":"0"}
         * RenewList : [{"ffID":"4","fftitle":"恶魔猎手"}]
         */

        private FriendsListBean FriendsList;
        private List<RenewListBean> RenewList;

        public FriendsListBean getFriendsList() {
            return FriendsList;
        }

        public void setFriendsList(FriendsListBean FriendsList) {
            this.FriendsList = FriendsList;
        }

        public List<RenewListBean> getRenewList() {
            return RenewList;
        }

        public void setRenewList(List<RenewListBean> RenewList) {
            this.RenewList = RenewList;
        }

        public static class FriendsListBean {
            /**
             * fmID : 17
             * fmtitle : 旅途
             * fmpic : http://47.92.136.19/http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg
             * fmgotime : 2018-07-26
             * fmendtime : 2018-07-31
             * percapitacost : 123.00
             * fmlook : 0
             * fmfavorite : 0
             */

            private String fmID;
            private String fmtitle;
            private String fmpic;
            private String fmgotime;
            private String fmendtime;
            private String percapitacost;
            private String fmlook;
            private String fmfavorite;

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

            public String getFmpic() {
                return fmpic;
            }

            public void setFmpic(String fmpic) {
                this.fmpic = fmpic;
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
        }

        public static class RenewListBean {
            /**
             * ffID : 4
             * fftitle : 恶魔猎手
             */

            private String ffID;
            private String fftitle;

            public String getFfID() {
                return ffID;
            }

            public void setFfID(String ffID) {
                this.ffID = ffID;
            }

            public String getFftitle() {
                return fftitle;
            }

            public void setFftitle(String fftitle) {
                this.fftitle = fftitle;
            }
        }
    }
}
