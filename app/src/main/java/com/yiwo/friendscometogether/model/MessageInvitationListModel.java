package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class MessageInvitationListModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"id":"2","tid":"3","type":"1","pftitle":"您的好友--吃了邀请您参加活动--三亚一月游","pfcontent":"哈哈哈","pfpic":"http://47.92.136.19/uploads/xingcheng/20180817/20180817/fd21c17ebfb3e16d863ef22403df6986.jpeg"}]
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
         * id : 2
         * tid : 3
         * type : 1
         * pftitle : 您的好友--吃了邀请您参加活动--三亚一月游
         * pfcontent : 哈哈哈
         * pfpic : http://47.92.136.19/uploads/xingcheng/20180817/20180817/fd21c17ebfb3e16d863ef22403df6986.jpeg
         */

        private String id;
        private String tid;
        private String type;
        private String pftitle;
        private String pfcontent;
        private String pfpic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfcontent() {
            return pfcontent;
        }

        public void setPfcontent(String pfcontent) {
            this.pfcontent = pfcontent;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }
    }
}
