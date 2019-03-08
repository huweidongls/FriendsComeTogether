package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/8.
 */

public class ChaWenGuanLiModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"ffID":"260","fftitle":"自己插文","ffcontect":"5粒考虑考虑","username":"薇薇薇","userpic":"http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg","ffptime":"2019-02-25 12:39:24","radio":"允许展示!","reason":"","piclist":[{"pictitle":"哈哈","picurl":"uploads/header/2019/02/25/0c48335c19e43cdd5194482711a538b01551069564320.jpg"},{"pictitle":"嘿嘿","picurl":"uploads/header/2019/02/25/0c48335c19e43cdd5194482711a538b01551069564736.jpg"}]},{"ffID":"261","fftitle":"测试活动队友参与插文","ffcontect":"1234567676868588656583857586575676","username":"薇薇薇","userpic":"http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg","ffptime":"2019-02-25 12:41:23","radio":"允许展示!","reason":"","piclist":[{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e91551069683305.jpg"},{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e91551069683847.jpg"},{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e9155106968365.jpg"},{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e91551069683760.jpg"},{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e91551069683682.jpg"},{"pictitle":"","picurl":"uploads/header/2019/02/25/4675dd069aa5ae9c1e1da70d8b62b9e91551069683109.jpg"}]},{"ffID":"262","fftitle":"队友插文 2","ffcontect":"嗯，活动挺不错","username":"薇薇薇","userpic":"http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg","ffptime":"2019-02-25 12:42:57","radio":"允许展示!","reason":"","piclist":[{"pictitle":"","picurl":"uploads/header/2019/02/25/048ed947cff5e1d25a4236c2e2f33e831551069777153.jpg"}]},{"ffID":"263","fftitle":"iOS插文","ffcontect":"哈哈哈","username":"薇薇薇","userpic":"http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg","ffptime":"2019-02-25 12:53:48","radio":"允许展示!","reason":"","piclist":[{"pictitle":"","picurl":"uploads/header/2019/02/25/6c8c04c407f27a4e76edae7f81b882851551070428215.jpg"}]},{"ffID":"264","fftitle":"插文0","ffcontect":"7728","username":"薇薇薇","userpic":"http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg","ffptime":"2019-02-25 12:59:53","radio":"允许展示!","reason":"","piclist":[{"pictitle":"","picurl":"uploads/header/2019/02/25/c598a6af2292eface1c580186a65e8e91551070793316.jpg"}]}]
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
         * ffID : 260
         * fftitle : 自己插文
         * ffcontect : 5粒考虑考虑
         * username : 薇薇薇
         * userpic : http://47.92.136.19/uploads/header/2019/01/29/754f4542fd5bc535800a9adfca0313de15487464417.jpg
         * ffptime : 2019-02-25 12:39:24
         * radio : 允许展示!
         * reason :
         * piclist : [{"pictitle":"哈哈","picurl":"uploads/header/2019/02/25/0c48335c19e43cdd5194482711a538b01551069564320.jpg"},{"pictitle":"嘿嘿","picurl":"uploads/header/2019/02/25/0c48335c19e43cdd5194482711a538b01551069564736.jpg"}]
         */

        private String ffID;
        private String fftitle;
        private String ffcontect;
        private String username;
        private String userpic;
        private String ffptime;
        private String radio;
        private String reason;
        private List<PiclistBean> piclist;

        public String getFfID() {
            return ffID;
        }

        public void setFfID(String ffID) {
            this.ffID = ffID;
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

        public String getFfptime() {
            return ffptime;
        }

        public void setFfptime(String ffptime) {
            this.ffptime = ffptime;
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

        public List<PiclistBean> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<PiclistBean> piclist) {
            this.piclist = piclist;
        }

        public static class PiclistBean {
            /**
             * pictitle : 哈哈
             * picurl : uploads/header/2019/02/25/0c48335c19e43cdd5194482711a538b01551069564320.jpg
             */

            private String pictitle;
            private String picurl;

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
