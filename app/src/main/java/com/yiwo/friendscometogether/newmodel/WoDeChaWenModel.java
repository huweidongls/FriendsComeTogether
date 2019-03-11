package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/11.
 */

public class WoDeChaWenModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"ffID":"269","fmID":"368","fftitle":"插文1","ffcontect":"插文插文插文","ffptime":"2019-03-08 15:40:15","position":"","radio":"2","reason":"","piclist":[{"ffpID":"887","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/0ef07016821440e535ac4a00bbcb75711552007380487.jpg"}],"newstitle":"测试插文的友记1111","activity_name":"测试退款取消活动"},{"ffID":"270","fmID":"368","fftitle":"插文2插入了5张图片","ffcontect":"啊啊啊","ffptime":"2019-03-08 15:40:15","position":"269","radio":"1","reason":"","piclist":[{"ffpID":"889","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/6eb1d811e793117509f47ae707f3548d1552007526885.jpg"},{"ffpID":"890","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/6eb1d811e793117509f47ae707f3548d1552007526841.jpg"},{"ffpID":"891","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/6eb1d811e793117509f47ae707f3548d1552007526245.jpg"},{"ffpID":"892","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/6eb1d811e793117509f47ae707f3548d1552007526554.jpg"}],"newstitle":"测试插文的友记1111","activity_name":"测试退款取消活动"},{"ffID":"272","fmID":"368","fftitle":"12","ffcontect":"12","ffptime":"2019-03-08 15:40:15","position":"","radio":"1","reason":"把","piclist":[{"ffpID":"895","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/7085c01964736b12518f5efa4dfd1f5c1552030815820.jpg"}],"newstitle":"测试插文的友记1111","activity_name":"测试退款取消活动"}]
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
         * ffID : 269
         * fmID : 368
         * fftitle : 插文1
         * ffcontect : 插文插文插文
         * ffptime : 2019-03-08 15:40:15
         * position :
         * radio : 2
         * reason :
         * piclist : [{"ffpID":"887","pictitle":"","picurl":"http://47.92.136.19/uploads/header/2019/03/08/0ef07016821440e535ac4a00bbcb75711552007380487.jpg"}]
         * newstitle : 测试插文的友记1111
         * activity_name : 测试退款取消活动
         */

        private String ffID;
        private String fmID;
        private String fftitle;
        private String ffcontect;
        private String ffptime;
        private String position;
        private String radio;
        private String reason;
        private String newstitle;
        private String activity_name;
        private List<PiclistBean> piclist;

        public String getFfID() {
            return ffID;
        }

        public void setFfID(String ffID) {
            this.ffID = ffID;
        }

        public String getFmID() {
            return fmID;
        }

        public void setFmID(String fmID) {
            this.fmID = fmID;
        }

        public String getFftitle() {
            return fftitle;
        }

        public void setFftitle(String fftitle) {
            this.fftitle = fftitle;
        }

        public String getFfcontect() {
            return ffcontect;
        }

        public void setFfcontect(String ffcontect) {
            this.ffcontect = ffcontect;
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

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getNewstitle() {
            return newstitle;
        }

        public void setNewstitle(String newstitle) {
            this.newstitle = newstitle;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public List<PiclistBean> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<PiclistBean> piclist) {
            this.piclist = piclist;
        }

        public static class PiclistBean {
            /**
             * ffpID : 887
             * pictitle :
             * picurl : http://47.92.136.19/uploads/header/2019/03/08/0ef07016821440e535ac4a00bbcb75711552007380487.jpg
             */

            private String ffpID;
            private String pictitle;
            private String picurl;

            public String getFfpID() {
                return ffpID;
            }

            public void setFfpID(String ffpID) {
                this.ffpID = ffpID;
            }

            public String getPictitle() {
                return pictitle;
            }

            public void setPictitle(String pictitle) {
                this.pictitle = pictitle;
            }

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }
        }
    }
}
