package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/7/5.
 */

public class MyVideosModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"vID":"5","vname":"我的一个小视频","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/11e094b0-5c5a-4912-bec6-50488201f974.mp4","vtime":"2019-07-05 14:02","img":"http://vodsmnjjkoj.nosdn.127.net/11e094b0-5c5a-4912-bec6-50488201f974_1_0_0.jpg"},{"vID":"4","vname":"来了啊","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/0e198947-9c88-4f6d-897a-575563bc773c.mp4","vtime":"2019-07-04 16:59","img":"http://vodsmnjjkoj.nosdn.127.net/0e198947-9c88-4f6d-897a-575563bc773c_1_0_0.jpg"},{"vID":"2","vname":"五秒～","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/54018f06-9b52-4028-8857-1727b3c7dff9.mp4","vtime":"2019-07-04 16:07","img":"http://vodsmnjjkoj.nosdn.127.net/67f4ca3f-f2ff-44f9-9d0e-ab2fcef8f4de.jpg"}]
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
         * vID : 5
         * vname : 我的一个小视频
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/11e094b0-5c5a-4912-bec6-50488201f974.mp4
         * vtime : 2019-07-05 14:02
         * img : http://vodsmnjjkoj.nosdn.127.net/11e094b0-5c5a-4912-bec6-50488201f974_1_0_0.jpg
         */

        private String vID;
        private String vname;
        private String vurl;
        private String vtime;
        private String img;

        public String getVID() {
            return vID;
        }

        public void setVID(String vID) {
            this.vID = vID;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getVtime() {
            return vtime;
        }

        public void setVtime(String vtime) {
            this.vtime = vtime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
