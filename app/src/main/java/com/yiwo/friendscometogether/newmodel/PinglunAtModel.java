package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/28.
 */

public class PinglunAtModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","uid":"72","fid":"286","bid":"73","username":"花生","userpic":"http://47.92.136.19/uploads/header/2019/01/14/43d6274beccd9831a3a94c95fe1a6c1715474577172.jpg","fmtitle":"小辣鸡"}]
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
         * id : 1
         * uid : 72
         * fid : 286
         * bid : 73
         * username : 花生
         * userpic : http://47.92.136.19/uploads/header/2019/01/14/43d6274beccd9831a3a94c95fe1a6c1715474577172.jpg
         * fmtitle : 小辣鸡
         */

        private String id;
        private String uid;
        private String fid;
        private String bid;
        private String username;
        private String userpic;
        private String fmtitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
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

        public String getFmtitle() {
            return fmtitle;
        }

        public void setFmtitle(String fmtitle) {
            this.fmtitle = fmtitle;
        }
    }
}
