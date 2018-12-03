package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class FriendsTogethermodel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"usergrade":0,"username":"","pfID":"44","pfpeople":"0","captain":"0","pftitle":"泰山三日游","pfcontent":"12额2","pfpic":"http://47.92.136.19/uploads/activity/20180708/20180708/c5b4c5d45e30a3471a9056c02cc22b44.jpg","upicurl":"","have_num":"1","all_u_pic":["http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg"],"focusOn":0},{"usergrade":0,"username":"","pfID":"18","pfpeople":"22","captain":"0","pftitle":"","pfcontent":"","pfpic":"http://47.92.136.19/uploads/xingcheng/20180709/20180709/01eae6bec86d65c445f32e73b31051c3.jpg","upicurl":"","have_num":"0","all_u_pic":[],"focusOn":0},{"usergrade":0,"username":"","pfID":"8","pfpeople":"100","captain":"0","pftitle":"飞","pfcontent":"飞翔","pfpic":"http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","upicurl":"","have_num":"1","all_u_pic":["http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg"],"focusOn":0},{"usergrade":0,"username":"zp","pfID":"1","pfpeople":"100","captain":"1","pftitle":"飘","pfcontent":"飘摇","pfpic":"http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","upicurl":"http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","have_num":"3","all_u_pic":["http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg","http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg"],"focusOn":0}]
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
         * usergrade : 0
         * username :
         * pfID : 44
         * pfpeople : 0
         * captain : 0
         * pftitle : 泰山三日游
         * pfcontent : 12额2
         * pfpic : http://47.92.136.19/uploads/activity/20180708/20180708/c5b4c5d45e30a3471a9056c02cc22b44.jpg
         * upicurl :
         * have_num : 1
         * all_u_pic : ["http://47.92.136.19/uploads/activity/20180529/20180529/a51b266af3785bd0bd0a1794dbf234cf.jpg"]
         * focusOn : 0
         */

        private String pfgotime;
        private String pfpwd;
        private String wy_accid;
        private String usergrade;
        private String username;
        private String pfID;
        private String pfpeople;
        private String captain;
        private String pftitle;
        private String pfcontent;
        private String pfpic;
        private String upicurl;
        private String have_num;
        private String focusOn;
        private String sign;
        private String captain_focusOn;

        public String getPfgotime() {
            return pfgotime;
        }

        public void setPfgotime(String pfgotime) {
            this.pfgotime = pfgotime;
        }

        public String getPfpwd() {
            return pfpwd;
        }

        public void setPfpwd(String pfpwd) {
            this.pfpwd = pfpwd;
        }

        public String getWy_accid() {
            return wy_accid;
        }

        public void setWy_accid(String wy_accid) {
            this.wy_accid = wy_accid;
        }

        public String getCaptain_focusOn() {
            return captain_focusOn;
        }

        public void setCaptain_focusOn(String captain_focusOn) {
            this.captain_focusOn = captain_focusOn;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        private List<String> all_u_pic;

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPfpeople() {
            return pfpeople;
        }

        public void setPfpeople(String pfpeople) {
            this.pfpeople = pfpeople;
        }

        public String getCaptain() {
            return captain;
        }

        public void setCaptain(String captain) {
            this.captain = captain;
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

        public String getUpicurl() {
            return upicurl;
        }

        public void setUpicurl(String upicurl) {
            this.upicurl = upicurl;
        }

        public String getHave_num() {
            return have_num;
        }

        public void setHave_num(String have_num) {
            this.have_num = have_num;
        }

        public String getFocusOn() {
            return focusOn;
        }

        public void setFocusOn(String focusOn) {
            this.focusOn = focusOn;
        }

        public List<String> getAll_u_pic() {
            return all_u_pic;
        }

        public void setAll_u_pic(List<String> all_u_pic) {
            this.all_u_pic = all_u_pic;
        }
    }
}
