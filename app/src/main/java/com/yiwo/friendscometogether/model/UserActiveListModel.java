package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class UserActiveListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"pftitle":"泰山30日游","pfID":"2","pfspendtype":"2","block":"1"},{"pftitle":"三亚一月游","pfID":"3","pfspendtype":"2","block":"1"},{"pftitle":"黄山三日游","pfID":"5","pfspendtype":"0","block":"0"},{"pftitle":"哈尔滨十日游","pfID":"8","pfspendtype":"2","block":"1"},{"pftitle":"海口十日游","pfID":"14","pfspendtype":"0","block":"0"}]
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
         * pftitle : 泰山30日游
         * pfID : 2
         * pfspendtype : 2
         * block : 1
         */

        private String pftitle;
        private String pfID;
        private String pfspendtype;
        private String block;

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPfspendtype() {
            return pfspendtype;
        }

        public void setPfspendtype(String pfspendtype) {
            this.pfspendtype = pfspendtype;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }
    }
}
