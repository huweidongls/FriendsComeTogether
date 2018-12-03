package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class UserIntercalationListModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"ffpID":"2","ffptitle":"E路通","ffpurl":"http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","ffptime":"2018-07-26 16:07:29","position":"暂无位置","state":"待审核","reason":"","activity_name":"暂无活动"},{"ffpID":"3","ffptitle":"牛","ffpurl":"http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","ffptime":"2018-07-26 16:17:34","position":"暂无位置","state":"待审核","reason":"","activity_name":"暂无活动"}]
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
         * ffpID : 2
         * ffptitle : E路通
         * ffpurl : http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg
         * ffptime : 2018-07-26 16:07:29
         * position : 暂无位置
         * state : 待审核
         * reason :
         * activity_name : 暂无活动
         */

        private String newstitle;
        private String ffpID;
        private String ffptitle;
        private String ffpurl;
        private String ffptime;
        private String position;
        private String state;
        private String reason;
        private String activity_name;

        public String getNewstitle() {
            return newstitle;
        }

        public void setNewstitle(String newstitle) {
            this.newstitle = newstitle;
        }

        public String getFfpID() {
            return ffpID;
        }

        public void setFfpID(String ffpID) {
            this.ffpID = ffpID;
        }

        public String getFfptitle() {
            return ffptitle;
        }

        public void setFfptitle(String ffptitle) {
            this.ffptitle = ffptitle;
        }

        public String getFfpurl() {
            return ffpurl;
        }

        public void setFfpurl(String ffpurl) {
            this.ffpurl = ffpurl;
        }

        public String getFfptime() {
            return ffptime;
        }

        public void setFfptime(String ffptime) {
            this.ffptime = ffptime;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }
    }
}
