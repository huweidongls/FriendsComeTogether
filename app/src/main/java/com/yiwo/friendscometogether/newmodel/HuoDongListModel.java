package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/19.
 */

public class HuoDongListModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"roomid":"1417471792","pfID":"102","pfpic":"http://127.0.0.1/uploads/xingcheng/20180930/f5ab478ba5edee62102e9b8b499b8d86.jpg","pftitle":"减肥减肥家","pfgotime":"1545287033","pfendtime":"1545719033","username":"老司机","userpic":"http://127.0.0.1/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","activities_data":"距离活动开始还有0天22小时"}]
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
         * roomid : 1417471792
         * pfID : 102
         * pfpic : http://127.0.0.1/uploads/xingcheng/20180930/f5ab478ba5edee62102e9b8b499b8d86.jpg
         * pftitle : 减肥减肥家
         * pfgotime : 1545287033
         * pfendtime : 1545719033
         * username : 老司机
         * userpic : http://127.0.0.1/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         * activities_data : 距离活动开始还有0天22小时
         */

        private String roomid;
        private String pfID;
        private String pfpic;
        private String pftitle;
        private String pfgotime;
        private String pfendtime;
        private String username;
        private String userpic;
        private String activities_data;
        private String ujID;
        private String wy_accid;

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

        public String getUjID() {
            return ujID;
        }

        public void setUjID(String ujID) {
            this.ujID = ujID;
        }

        public String getWy_accid() {
            return wy_accid;
        }

        public void setWy_accid(String wy_accid) {
            this.wy_accid = wy_accid;
        }
    }
}
