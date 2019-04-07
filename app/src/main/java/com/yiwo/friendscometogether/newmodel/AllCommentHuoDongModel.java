package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/4/7.
 */

public class AllCommentHuoDongModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"pcID":"13","pfID":"165","pctitle":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊","userID":"72","buserID":"1","parent_id":"0","fctime":"2019-04-04 14:12","type":"1","reply_type":"0","is_del":"0","is_read":"1","user_ID":"72","username":"花生","userpic":"http://39.104.102.152/uploads/header/2019/04/02/280c8ad6853b61d72bbd34485f8d0c50155418448218.jpg"}]
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
         * pcID : 13
         * pfID : 165
         * pctitle : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊
         * userID : 72
         * buserID : 1
         * parent_id : 0
         * fctime : 2019-04-04 14:12
         * type : 1
         * reply_type : 0
         * is_del : 0
         * is_read : 1
         * user_ID : 72
         * username : 花生
         * userpic : http://39.104.102.152/uploads/header/2019/04/02/280c8ad6853b61d72bbd34485f8d0c50155418448218.jpg
         */

        private String pcID;
        private String pfID;
        private String pctitle;
        private String userID;
        private String buserID;
        private String parent_id;
        private String fctime;
        private String type;
        private String reply_type;
        private String is_del;
        private String is_read;
        private String user_ID;
        private String username;
        private String userpic;

        public String getPcID() {
            return pcID;
        }

        public void setPcID(String pcID) {
            this.pcID = pcID;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPctitle() {
            return pctitle;
        }

        public void setPctitle(String pctitle) {
            this.pctitle = pctitle;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getBuserID() {
            return buserID;
        }

        public void setBuserID(String buserID) {
            this.buserID = buserID;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getFctime() {
            return fctime;
        }

        public void setFctime(String fctime) {
            this.fctime = fctime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getReply_type() {
            return reply_type;
        }

        public void setReply_type(String reply_type) {
            this.reply_type = reply_type;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getUser_ID() {
            return user_ID;
        }

        public void setUser_ID(String user_ID) {
            this.user_ID = user_ID;
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
