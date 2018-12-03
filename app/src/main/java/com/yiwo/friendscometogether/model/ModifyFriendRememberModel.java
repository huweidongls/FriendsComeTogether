package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/10.
 */

public class ModifyFriendRememberModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"fmID":"85","fmtitle":"我去泰山玩了","fmcontent":"那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我","fmpic":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","fmpartyID":"56","fmpartyName":"登峨眉山！","fmgotime":"2018-08-10","fmendtime":"2018-08-31","fmaddress":"山东省-泰安市-泰山区","percapitacost":"150.00","accesspassword":"2616","insertatext":"1","fmlable":"5","fmlableName":"烧烤"}
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
         * fmID : 85
         * fmtitle : 我去泰山玩了
         * fmcontent : 那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我
         * fmpic : http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg
         * fmpartyID : 56
         * fmpartyName : 登峨眉山！
         * fmgotime : 2018-08-10
         * fmendtime : 2018-08-31
         * fmaddress : 山东省-泰安市-泰山区
         * percapitacost : 150.00
         * accesspassword : 2616
         * insertatext : 1
         * fmlable : 5
         * fmlableName : 烧烤
         */

        private String fmID;
        private String fmtitle;
        private String fmcontent;
        private String fmpic;
        private String fmpartyID;
        private String fmpartyName;
        private String fmgotime;
        private String fmendtime;
        private String fmaddress;
        private String percapitacost;
        private String accesspassword;
        private String insertatext;
        private String fmlable;
        private String fmlableName;

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

        public String getFmcontent() {
            return fmcontent;
        }

        public void setFmcontent(String fmcontent) {
            this.fmcontent = fmcontent;
        }

        public String getFmpic() {
            return fmpic;
        }

        public void setFmpic(String fmpic) {
            this.fmpic = fmpic;
        }

        public String getFmpartyID() {
            return fmpartyID;
        }

        public void setFmpartyID(String fmpartyID) {
            this.fmpartyID = fmpartyID;
        }

        public String getFmpartyName() {
            return fmpartyName;
        }

        public void setFmpartyName(String fmpartyName) {
            this.fmpartyName = fmpartyName;
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

        public String getFmaddress() {
            return fmaddress;
        }

        public void setFmaddress(String fmaddress) {
            this.fmaddress = fmaddress;
        }

        public String getPercapitacost() {
            return percapitacost;
        }

        public void setPercapitacost(String percapitacost) {
            this.percapitacost = percapitacost;
        }

        public String getAccesspassword() {
            return accesspassword;
        }

        public void setAccesspassword(String accesspassword) {
            this.accesspassword = accesspassword;
        }

        public String getInsertatext() {
            return insertatext;
        }

        public void setInsertatext(String insertatext) {
            this.insertatext = insertatext;
        }

        public String getFmlable() {
            return fmlable;
        }

        public void setFmlable(String fmlable) {
            this.fmlable = fmlable;
        }

        public String getFmlableName() {
            return fmlableName;
        }

        public void setFmlableName(String fmlableName) {
            this.fmlableName = fmlableName;
        }
    }
}
