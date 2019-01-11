package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class ZAndScModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"5","uid":"7","fid":"279","type":"0","bid":"33","username":"老司机","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg"}]
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
         * id : 5
         * uid : 7
         * fid : 279
         * type : 0
         * bid : 33
         * username : 老司机
         * userpic : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         */

        private String id;
        private String uid;
        private String fid;
        private String type;
        private String bid;
        private String username;
        private String userpic;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
