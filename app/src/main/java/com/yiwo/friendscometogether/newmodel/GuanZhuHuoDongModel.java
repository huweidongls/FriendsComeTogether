package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/20.
 */

public class GuanZhuHuoDongModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"activities_data":"0","pfID":"46","pftitle":"西安游","pfpic":"http://47.92.136.19/uploads/activity/20180904/848ef24479c9ead6bcb98797b0f05ab0.png","userpic":"http://47.92.136.19/01786557e4a6fa0000018c1bf080ca.png","username":"网站管理员","roomid":"1372525725"},{"activities_data":"0","pfID":"41","pftitle":"一个活动","pfpic":"http://47.92.136.19/uploads/xingcheng/20180831/4aa615e0bc80f66e6d9f935757c94570.jpg","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","username":"老司机","roomid":"1365053677"},{"activities_data":"2","pfID":"97","pftitle":"2岁","pfpic":"http://47.92.136.19/uploads/xingcheng/20180927/633ea4542cef84b413fba72e08507385.jpg","userpic":"http://47.92.136.19/uploads/header/2018/09/12/dea21f20f53bd3aabfcd1b456c070dd815367125395.jpg","username":"崔薇","roomid":"1413355569","day":2567},{"activities_data":"2","pfID":"60","pftitle":"测试草稿","pfpic":"http://47.92.136.19/uploads/xingcheng/20180911/ceb1a7ebdc996600b3e838893f0a4f55.jpg","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","username":"老司机","roomid":"1384736510","day":375}]
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
         * activities_data : 0
         * pfID : 46
         * pftitle : 西安游
         * pfpic : http://47.92.136.19/uploads/activity/20180904/848ef24479c9ead6bcb98797b0f05ab0.png
         * userpic : http://47.92.136.19/01786557e4a6fa0000018c1bf080ca.png
         * username : 网站管理员
         * roomid : 1372525725
         * day : 2567
         */

        private String activities_data;
        private String pfID;
        private String pftitle;
        private String pfpic;
        private String userpic;
        private String username;
        private String roomid;
        private int day;

        public String getActivities_data() {
            return activities_data;
        }

        public void setActivities_data(String activities_data) {
            this.activities_data = activities_data;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}
