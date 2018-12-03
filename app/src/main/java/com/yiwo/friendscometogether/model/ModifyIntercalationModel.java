package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class ModifyIntercalationModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"info":{"fftitle":"我的大泰山","ffcontect":"老婆工具不看看就踏踏就送你涂露露就怕就拖拖拉拉图我咯图","ffID":"117"},"imagesArr":[{"ffpID":"173","ffptitle":"你好","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549690.jpg"},{"ffpID":"174","ffptitle":"小孩","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549199.jpg"},{"ffpID":"175","ffptitle":"这是饭","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549288.jpg"},{"ffpID":"176","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549285.jpg"},{"ffpID":"177","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e153386654920.jpg"},{"ffpID":"178","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549379.jpg"},{"ffpID":"179","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549961.jpg"},{"ffpID":"180","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549974.jpg"},{"ffpID":"181","ffptitle":"鸡蛋糕","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549380.jpg"},{"ffpID":"250","ffptitle":"美啊????","ffpurl":"http://47.92.136.19/uploads/header/2018/08/11/2a4dd9b9f141165c710a82635f55eab41533962660538.jpg"}]}
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
         * info : {"fftitle":"我的大泰山","ffcontect":"老婆工具不看看就踏踏就送你涂露露就怕就拖拖拉拉图我咯图","ffID":"117"}
         * imagesArr : [{"ffpID":"173","ffptitle":"你好","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549690.jpg"},{"ffpID":"174","ffptitle":"小孩","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549199.jpg"},{"ffpID":"175","ffptitle":"这是饭","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549288.jpg"},{"ffpID":"176","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549285.jpg"},{"ffpID":"177","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e153386654920.jpg"},{"ffpID":"178","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549379.jpg"},{"ffpID":"179","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549961.jpg"},{"ffpID":"180","ffptitle":"","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549974.jpg"},{"ffpID":"181","ffptitle":"鸡蛋糕","ffpurl":"http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549380.jpg"},{"ffpID":"250","ffptitle":"美啊????","ffpurl":"http://47.92.136.19/uploads/header/2018/08/11/2a4dd9b9f141165c710a82635f55eab41533962660538.jpg"}]
         */

        private InfoBean info;
        private List<ImagesArrBean> imagesArr;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<ImagesArrBean> getImagesArr() {
            return imagesArr;
        }

        public void setImagesArr(List<ImagesArrBean> imagesArr) {
            this.imagesArr = imagesArr;
        }

        public static class InfoBean {
            /**
             * fftitle : 我的大泰山
             * ffcontect : 老婆工具不看看就踏踏就送你涂露露就怕就拖拖拉拉图我咯图
             * ffID : 117
             */

            private String fftitle;
            private String ffcontect;
            private String ffID;

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

            public String getFfID() {
                return ffID;
            }

            public void setFfID(String ffID) {
                this.ffID = ffID;
            }
        }

        public static class ImagesArrBean {
            /**
             * ffpID : 173
             * ffptitle : 你好
             * ffpurl : http://47.92.136.19/uploads/header/2018/08/10/24959d069159b0474f1de178ba86dc5e1533866549690.jpg
             */

            private String ffpID;
            private String ffptitle;
            private String ffpurl;

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
        }
    }
}
