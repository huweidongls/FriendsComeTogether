package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/8/24 0024.
 */

public class InvitationOkModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : {"id":"3","username":"恶魔","pic":"http://47.92.136.19/uploads/xingcheng/20180817/20180817/fd21c17ebfb3e16d863ef22403df6986.jpeg","pftitle":"三亚一月游","pfgotime":"2018-08-16","pfaddress":"三亚市","pfpeoplesex":"无限制","pfagebegin":"2~6","pfmarry":"是","pfspend":"369.00","pfspendtype":"2","typename":"线上收取"}
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
         * id : 3
         * username : 恶魔
         * pic : http://47.92.136.19/uploads/xingcheng/20180817/20180817/fd21c17ebfb3e16d863ef22403df6986.jpeg
         * pftitle : 三亚一月游
         * pfgotime : 2018-08-16
         * pfaddress : 三亚市
         * pfpeoplesex : 无限制
         * pfagebegin : 2~6
         * pfmarry : 是
         * pfspend : 369.00
         * pfspendtype : 2
         * typename : 线上收取
         */

        private String phone;
        private String id;
        private String username;
        private String pic;
        private String pftitle;
        private String pfgotime;
        private String pfaddress;
        private String pfpeoplesex;
        private String pfagebegin;
        private String pfmarry;
        private String pfspend;
        private String pfspendtype;
        private String typename;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfgotime() {
            return pfgotime;
        }

        public void setPfgotime(String pfgotime) {
            this.pfgotime = pfgotime;
        }

        public String getPfaddress() {
            return pfaddress;
        }

        public void setPfaddress(String pfaddress) {
            this.pfaddress = pfaddress;
        }

        public String getPfpeoplesex() {
            return pfpeoplesex;
        }

        public void setPfpeoplesex(String pfpeoplesex) {
            this.pfpeoplesex = pfpeoplesex;
        }

        public String getPfagebegin() {
            return pfagebegin;
        }

        public void setPfagebegin(String pfagebegin) {
            this.pfagebegin = pfagebegin;
        }

        public String getPfmarry() {
            return pfmarry;
        }

        public void setPfmarry(String pfmarry) {
            this.pfmarry = pfmarry;
        }

        public String getPfspend() {
            return pfspend;
        }

        public void setPfspend(String pfspend) {
            this.pfspend = pfspend;
        }

        public String getPfspendtype() {
            return pfspendtype;
        }

        public void setPfspendtype(String pfspendtype) {
            this.pfspendtype = pfspendtype;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }
    }
}
