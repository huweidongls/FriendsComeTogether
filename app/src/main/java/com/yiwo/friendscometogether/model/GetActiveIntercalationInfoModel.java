package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class GetActiveIntercalationInfoModel {

    /**
     * code : 200
     * message : 修改成功!
     * obj : {"id":"5","pfptitle":"三亚真好玩","pfpcontent":"哈哈","imgs":[{"pfpID":"16","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180816/6eb9626ef41298a1570a1096ff888fca9772.jpeg"},{"pfpID":"17","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180816/49ada04856a4e1bef2c5d46c1b3d02c09442.jpeg"},{"pfpID":"19","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180817/f9f8254eb2bbd6c604b1bc641e19b8199876.jpeg"}]}
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
         * id : 5
         * pfptitle : 三亚真好玩
         * pfpcontent : 哈哈
         * imgs : [{"pfpID":"16","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180816/6eb9626ef41298a1570a1096ff888fca9772.jpeg"},{"pfpID":"17","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180816/49ada04856a4e1bef2c5d46c1b3d02c09442.jpeg"},{"pfpID":"19","pfpcontent":"","pfpurl":"http://47.92.136.19/uploads/xingcheng/20180817/f9f8254eb2bbd6c604b1bc641e19b8199876.jpeg"}]
         */

        private String id;
        private String pfptitle;
        private String pfpcontent;
        private List<ImgsBean> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPfptitle() {
            return pfptitle;
        }

        public void setPfptitle(String pfptitle) {
            this.pfptitle = pfptitle;
        }

        public String getPfpcontent() {
            return pfpcontent;
        }

        public void setPfpcontent(String pfpcontent) {
            this.pfpcontent = pfpcontent;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            /**
             * pfpID : 16
             * pfpcontent :
             * pfpurl : http://47.92.136.19/uploads/xingcheng/20180816/6eb9626ef41298a1570a1096ff888fca9772.jpeg
             */

            private String pfpID;
            private String pfpcontent;
            private String pfpurl;

            public String getPfpID() {
                return pfpID;
            }

            public void setPfpID(String pfpID) {
                this.pfpID = pfpID;
            }

            public String getPfpcontent() {
                return pfpcontent;
            }

            public void setPfpcontent(String pfpcontent) {
                this.pfpcontent = pfpcontent;
            }

            public String getPfpurl() {
                return pfpurl;
            }

            public void setPfpurl(String pfpurl) {
                this.pfpurl = pfpurl;
            }
        }
    }
}
