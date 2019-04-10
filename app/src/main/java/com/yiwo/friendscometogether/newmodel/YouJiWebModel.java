package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/27.
 */

public class YouJiWebModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"add":"1","collect":"1","title":[{"ffID":"299","fftitle":"1231231"}]}
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * add : 1
         * collect : 1
         * title : [{"ffID":"299","fftitle":"1231231"}]
         * "share_pic": "http://39.104.102.152/uploads/article/20190410/0-0be57cc6a725b202da556d340c902ac93770.jpeg",
         "share_url": "http://39.104.102.152/index.php/action/ac_article/youJiWebShare?id=476",
         "share_info": "自摸LOL了😐😠😟😟\n金融我上晚自习你莫😎😎😎😎\nOP你们医院"
         */

        private String add;
        private String collect;
        private List<TitleBean> title;
        private String share_pic;
        private String share_url;
        private String share_info;
        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public List<TitleBean> getTitle() {
            return title;
        }

        public void setTitle(List<TitleBean> title) {
            this.title = title;
        }

        public String getShare_pic() {
            return share_pic;
        }

        public void setShare_pic(String share_pic) {
            this.share_pic = share_pic;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare_info() {
            return share_info;
        }

        public void setShare_info(String share_info) {
            this.share_info = share_info;
        }

        public static class TitleBean {
            /**
             * ffID : 299
             * fftitle : 1231231
             */

            private String ffID;
            private String fftitle;

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
        }
    }
}
