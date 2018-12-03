package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class UserRememberModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"fmID":"15","fmtitle":"6组","fmpic":"http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","fmgotime":"2018-07-25","fmendtime":"2018-07-31","percapitacost":"1.00","fmlook":"0","fmfavorite":"0","fmtime":"2018-07-25 17:24:31"}]
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
         * fmID : 15
         * fmtitle : 6组
         * fmpic : http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg
         * fmgotime : 2018-07-25
         * fmendtime : 2018-07-31
         * percapitacost : 1.00
         * fmlook : 0
         * fmfavorite : 0
         * fmtime : 2018-07-25 17:24:31
         */

        private String fmID;
        private String fmtitle;
        private String fmpic;
        private String fmgotime;
        private String fmendtime;
        private String percapitacost;
        private String fmlook;
        private String fmfavorite;
        private String fmtime;

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

        public String getFmtime() {
            return fmtime;
        }

        public void setFmtime(String fmtime) {
            this.fmtime = fmtime;
        }
    }
}
