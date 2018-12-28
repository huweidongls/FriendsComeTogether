package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class ModifyFriendRememberModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"fmID":"253","fmtitle":"测试2","fmcontent":"","fmpic":[{"id":"14","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/0-2c289a933b288b4b8b6d7c0252ea4ebc3660.jpg"},{"id":"15","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/1-77fd8566e0537861146d3ecbc36761cd5176.jpg"},{"id":"16","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/2-18ed8e8f95a437f4bd10c78d6bb491bb7861.jpeg"},{"id":"17","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/3-18de54cef62efdf13747bb8cafacaafa5643.jpeg"}],"fmpartyID":"0","fmpartyName":"","fmgotime":"","fmendtime":"","fmaddress":"","percapitacost":"","accesspassword":"","insertatext":"1","fmlable":"6","fmlableName":"蹦极"}
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
         * fmID : 253
         * fmtitle : 测试2
         * fmcontent :
         * fmpic : [{"id":"14","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/0-2c289a933b288b4b8b6d7c0252ea4ebc3660.jpg"},{"id":"15","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/1-77fd8566e0537861146d3ecbc36761cd5176.jpg"},{"id":"16","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/2-18ed8e8f95a437f4bd10c78d6bb491bb7861.jpeg"},{"id":"17","fid":"253","pic":"http://47.92.136.19/uploads/article/20181228/3-18de54cef62efdf13747bb8cafacaafa5643.jpeg"}]
         * fmpartyID : 0
         * fmpartyName :
         * fmgotime :
         * fmendtime :
         * fmaddress :
         * percapitacost :
         * accesspassword :
         * insertatext : 1
         * fmlable : 6
         * fmlableName : 蹦极
         */

        private String fmID;
        private String fmtitle;
        private String fmcontent;
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

        public String getFmcontent() {
            return fmcontent;
        }

        public void setFmcontent(String fmcontent) {
            this.fmcontent = fmcontent;
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

        public List<FmpicBean> getFmpic() {
            return fmpic;
        }

        public void setFmpic(List<FmpicBean> fmpic) {
            this.fmpic = fmpic;
        }

        public static class FmpicBean {
            /**
             * id : 14
             * fid : 253
             * pic : http://47.92.136.19/uploads/article/20181228/0-2c289a933b288b4b8b6d7c0252ea4ebc3660.jpg
             */

            private String id;
            private String fid;
            private String pic;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
