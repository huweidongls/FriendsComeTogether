package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class MyPicListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"uid":"31","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/358ea15141405ff799074ea587225d14153024363115.jpg","utime":"2018-06-29 11:40:31","udel":"0"},{"uid":"32","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/3b1fd362d2754101c57d6f729c51cfab15302436467.jpg","utime":"2018-06-29 11:40:46","udel":"0"},{"uid":"33","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/c10697ec95baf148ca6f8474c5a2764315302436583.jpg","utime":"2018-06-29 11:40:58","udel":"0"},{"uid":"34","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/ea5e22d82fd789c2c98158ced680ac0d153024366918.jpg","utime":"2018-06-29 11:41:09","udel":"0"},{"uid":"35","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/00787297e2a8e576e949481a9dcb87ea153024367913.jpg","utime":"2018-06-29 11:41:19","udel":"0"},{"uid":"36","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/06/29/adbf003b524a335df78b9e5a9eb41bb915302523956.jpg","utime":"2018-06-29 14:06:35","udel":"0"},{"uid":"38","userID":"7","upicurl":"http://47.92.136.19/uploads/header/2018/07/05/21cc1075432a6b1aa51446642308202315307704427.jpg","utime":"2018-07-05 14:00:42","udel":"0"}]
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
         * uid : 31
         * userID : 7
         * upicurl : http://47.92.136.19/uploads/header/2018/06/29/358ea15141405ff799074ea587225d14153024363115.jpg
         * utime : 2018-06-29 11:40:31
         * udel : 0
         */

        private String uid;
        private String upicurl;

        public ObjBean(String uid, String upicurl) {
            this.uid = uid;
            this.upicurl = upicurl;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpicurl() {
            return upicurl;
        }

        public void setUpicurl(String upicurl) {
            this.upicurl = upicurl;
        }

    }
}
