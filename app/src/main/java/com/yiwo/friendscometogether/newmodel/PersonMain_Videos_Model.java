package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/10/9.
 */

public class PersonMain_Videos_Model {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"vID":"121","vname":"Android测试视频","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/616371f8-1670-427f-ace3-0efe9401711d.mp4","vtime":"2019-10-09 10:16","img":"http://vodsmnjjkoj.nosdn.127.net/616371f8-1670-427f-ace3-0efe9401711d_1_0_0.jpg","look_num":"0","wy_vid":"2729841243"},{"vID":"106","vname":"669","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/dea2463b-38d1-412d-88ba-84b5d8fe6100.mp4","vtime":"2019-09-02 11:10","img":"http://vodsmnjjkoj.nosdn.127.net/dea2463b-38d1-412d-88ba-84b5d8fe6100_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"105","vname":"333","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/2e409a51-3729-4d24-a4fd-2fc0816872f8.mp4","vtime":"2019-08-30 11:21","img":"http://vodsmnjjkoj.nosdn.127.net/2e409a51-3729-4d24-a4fd-2fc0816872f8_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"99","vname":"啊啊啊","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/e8835240-df04-4d33-9476-9aa6c4b384e6.mp4","vtime":"2019-08-27 14:23","img":"http://vodsmnjjkoj.nosdn.127.net/e8835240-df04-4d33-9476-9aa6c4b384e6_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"98","vname":"kkk","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/343ebf91-6521-4d05-8b1b-2d397d8625f6.mp4","vtime":"2019-08-27 14:17","img":"http://vodsmnjjkoj.nosdn.127.net/343ebf91-6521-4d05-8b1b-2d397d8625f6_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"97","vname":"进度条2","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/8a7dd4d4-a232-4286-a2f3-ffcffa646629.mp4","vtime":"2019-08-27 09:24","img":"http://vodsmnjjkoj.nosdn.127.net/8a7dd4d4-a232-4286-a2f3-ffcffa646629_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"95","vname":"22","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/a9efe9e6-7280-4294-99d9-e9322adf8879.mp4","vtime":"2019-08-27 08:44","img":"http://vodsmnjjkoj.nosdn.127.net/fbf4b61b-8593-4ec5-b3e9-cca148e2c6c4.jpg","look_num":"0","wy_vid":""},{"vID":"93","vname":"123eee","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/e6a54e14-4b9e-4b5f-8676-7a2cf5cc2550.mp4","vtime":"2019-08-21 09:13","img":"http://vodsmnjjkoj.nosdn.127.net/e6a54e14-4b9e-4b5f-8676-7a2cf5cc2550_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"92","vname":"mo","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/aed6bc48-3995-4434-85ae-2f8dbebef861.mp4","vtime":"2019-08-21 08:59","img":"http://vodsmnjjkoj.nosdn.127.net/aed6bc48-3995-4434-85ae-2f8dbebef861_1_0_0.jpg","look_num":"0","wy_vid":""},{"vID":"91","vname":"噢噢噢哦哦默默地","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/0e6e8d25-57a0-4a82-b170-0d5427817292.mp4","vtime":"2019-08-13 16:32","img":"http://vodsmnjjkoj.nosdn.127.net/0e6e8d25-57a0-4a82-b170-0d5427817292_1_0_0.jpg","look_num":"0","wy_vid":""}]
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
         * vID : 121
         * vname : Android测试视频
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/616371f8-1670-427f-ace3-0efe9401711d.mp4
         * vtime : 2019-10-09 10:16
         * img : http://vodsmnjjkoj.nosdn.127.net/616371f8-1670-427f-ace3-0efe9401711d_1_0_0.jpg
         * look_num : 0
         * wy_vid : 2729841243
         */

        private String vID;
        private String vname;
        private String vurl;
        private String vtime;
        private String img;
        private String look_num;
        private String wy_vid;

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

        public String getLook_num() {
            return look_num;
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }

        public String getWy_vid() {
            return wy_vid;
        }

        public void setWy_vid(String wy_vid) {
            this.wy_vid = wy_vid;
        }
    }
}
