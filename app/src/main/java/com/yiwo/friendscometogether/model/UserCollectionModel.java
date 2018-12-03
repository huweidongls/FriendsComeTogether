package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */

public class UserCollectionModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fID":"60","ftype":"2","ftableid":"93","ftitle":"五大连池火山","fpic":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","ftime":"2018-08-10 15:18:46"}]
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
         * fID : 60
         * ftype : 2
         * ftableid : 93
         * ftitle : 五大连池火山
         * fpic : http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png
         * ftime : 2018-08-10 15:18:46
         */

        private String fID;
        private String ftype;
        private String ftableid;
        private String ftitle;
        private String fpic;
        private String ftime;

        public String getFID() {
            return fID;
        }

        public void setFID(String fID) {
            this.fID = fID;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFtableid() {
            return ftableid;
        }

        public void setFtableid(String ftableid) {
            this.ftableid = ftableid;
        }

        public String getFtitle() {
            return ftitle;
        }

        public void setFtitle(String ftitle) {
            this.ftitle = ftitle;
        }

        public String getFpic() {
            return fpic;
        }

        public void setFpic(String fpic) {
            this.fpic = fpic;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
