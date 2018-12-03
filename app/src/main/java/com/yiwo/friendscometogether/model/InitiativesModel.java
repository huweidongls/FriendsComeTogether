package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 */

public class InitiativesModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"roomid":"657667923","pfexamine":"2","pfID":"2","pfpic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","pftitle":"泰山30日游","pfgotime":"2018-08-02","pfendtime":"2018-09-29","pfspend":"1.00","pflook":"15","focusOn":"0","join_num":"1"}]
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
         * roomid : 657667923
         * pfexamine : 2
         * pfID : 2
         * pfpic : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
         * pftitle : 泰山30日游
         * pfgotime : 2018-08-02
         * pfendtime : 2018-09-29
         * pfspend : 1.00
         * pflook : 15
         * focusOn : 0
         * join_num : 1
         */

        private String roomradio;
        private String if_over;
        private String roomid;
        private String pfexamine;
        private String pfID;
        private String pfpic;
        private String pftitle;
        private String pfgotime;
        private String pfendtime;
        private String pfspend;
        private String pflook;
        private String focusOn;
        private String join_num;

        public String getRoomradio() {
            return roomradio;
        }

        public void setRoomradio(String roomradio) {
            this.roomradio = roomradio;
        }

        public String getIf_over() {
            return if_over;
        }

        public void setIf_over(String if_over) {
            this.if_over = if_over;
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
        }

        public String getPfexamine() {
            return pfexamine;
        }

        public void setPfexamine(String pfexamine) {
            this.pfexamine = pfexamine;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
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

        public String getPfendtime() {
            return pfendtime;
        }

        public void setPfendtime(String pfendtime) {
            this.pfendtime = pfendtime;
        }

        public String getPfspend() {
            return pfspend;
        }

        public void setPfspend(String pfspend) {
            this.pfspend = pfspend;
        }

        public String getPflook() {
            return pflook;
        }

        public void setPflook(String pflook) {
            this.pflook = pflook;
        }

        public String getFocusOn() {
            return focusOn;
        }

        public void setFocusOn(String focusOn) {
            this.focusOn = focusOn;
        }

        public String getJoin_num() {
            return join_num;
        }

        public void setJoin_num(String join_num) {
            this.join_num = join_num;
        }
    }
}
