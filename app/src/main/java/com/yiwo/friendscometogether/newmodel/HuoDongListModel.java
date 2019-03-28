package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/19.
 */

public class HuoDongListModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"phase":"1","ujID":"451","kefu":"70","add_type":"2","add_user":"1","roomid":"1636979713","pfID":"157","pfpic":"http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg","pftitle":"活动不可退款，只限男性 且单身","pfgotime":"1553758320","pfendtime":"1554017528","username":"哈尔滨友来友约文化旅游传媒有限公司","wy_accid":"yy13936681816","userpic":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","activities_data":"距离活动开始还有1天"},{"phase":"1","ujID":"446","kefu":"85","add_type":"2","add_user":"1","roomid":"1634293997","pfID":"156","pfpic":"http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg","pftitle":"齐齐哈尔烤肉免费吃了","pfgotime":"1554097606","pfendtime":"1554529608","username":"哈尔滨友来友约文化旅游传媒有限公司","wy_accid":"yykakaluote","userpic":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","activities_data":"距离活动开始还有5天"}]
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
         * phase : 1
         * ujID : 451
         * kefu : 70
         * add_type : 2
         * add_user : 1
         * roomid : 1636979713
         * pfID : 157
         * pfpic : http://39.104.102.152/uploads/xingcheng/20190321/917bbe3500b9249b14184868591d6d92.jpg
         * pftitle : 活动不可退款，只限男性 且单身
         * pfgotime : 1553758320
         * pfendtime : 1554017528
         * username : 哈尔滨友来友约文化旅游传媒有限公司
         * wy_accid : yy13936681816
         * userpic : http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg
         * activities_data : 距离活动开始还有1天
         */

        private String phase;
        private String ujID;
        private String kefu;
        private String add_type;
        private String add_user;
        private String roomid;
        private String pfID;
        private String pfpic;
        private String pftitle;
        private String pfgotime;
        private String pfendtime;
        private String username;
        private String wy_accid;
        private String userpic;
        private String activities_data;

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getUjID() {
            return ujID;
        }

        public void setUjID(String ujID) {
            this.ujID = ujID;
        }

        public String getKefu() {
            return kefu;
        }

        public void setKefu(String kefu) {
            this.kefu = kefu;
        }

        public String getAdd_type() {
            return add_type;
        }

        public void setAdd_type(String add_type) {
            this.add_type = add_type;
        }

        public String getAdd_user() {
            return add_user;
        }

        public void setAdd_user(String add_user) {
            this.add_user = add_user;
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getWy_accid() {
            return wy_accid;
        }

        public void setWy_accid(String wy_accid) {
            this.wy_accid = wy_accid;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getActivities_data() {
            return activities_data;
        }

        public void setActivities_data(String activities_data) {
            this.activities_data = activities_data;
        }
    }
}
