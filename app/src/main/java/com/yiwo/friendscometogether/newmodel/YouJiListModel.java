package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class YouJiListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fmID":"253","fmtitle":"测试2","fmpic":"http://47.92.136.19/uploads/article/20181228/0-5ded6dfcd730eb43378db1c3bb105ee22987.jpeg","userID":"7","fmgood":"0","fmtime":"5天前","username":"老司机","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","type":"1","vurl":""},{"fmID":"252","fmtitle":"测试1","fmpic":"http://47.92.136.19/uploads/article/20181228/0-3c112b4d7a13b67809ff39a410e9185f6816.jpeg","userID":"7","fmgood":"0","fmtime":"5天前","username":"老司机","userpic":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","type":"1","vurl":""},{"fmID":"4","fmtitle":"看视频来优酷","fmpic":"http://47.92.136.19/uploads/videos/20180723/20180723/ec202c25a5914855399f018f9d50752c.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/bd2fd1100d99429c83256f652f6fef14_1520990405221_1520990705541_257737373-00000.flv","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"5","fmtitle":"看视频来爱奇艺","fmpic":"http://47.92.136.19/uploads/videos/20180723/20180723/603dc9e676722de754ed9f5dcf3029ac.jpg","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"7","fmtitle":"视频测试","fmpic":"http://47.92.136.19/uploads/videos/20180830/2e24d88777cf9271bb5620b2d45841ff.jpg","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"8","fmtitle":"瀑布","fmpic":"http://47.92.136.19/uploads/videos/20180830/1fe920df00aa277241adae8d6199fd82.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/bd2fd1100d99429c83256f652f6fef14_1520990405221_1520990705541_257737373-00000.flv","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"9","fmtitle":"巴厘岛","fmpic":"http://47.92.136.19/uploads/videos/20180830/a43868d4d5c68eba21a3ebc80d3142a7.jpg","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"10","fmtitle":"向日葵小镇","fmpic":"http://47.92.136.19/uploads/videos/20180830/f48561675ba33b52521a2b7f0f061e37.jpg","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"},{"fmID":"11","fmtitle":"神秘岛","fmpic":"http://47.92.136.19/uploads/videos/20180830/65a5afaca09024f3a575ae1b712e7dee.jpg","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","userID":"","fmgood":"","fmtime":"","username":"","userpic":"","type":"0"}]
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
         * fmID : 253
         * fmtitle : 测试2
         * fmpic : http://47.92.136.19/uploads/article/20181228/0-5ded6dfcd730eb43378db1c3bb105ee22987.jpeg
         * userID : 7
         * fmgood : 0
         * fmtime : 5天前
         * username : 老司机
         * userpic : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         * type : 1
         * vurl :
         */

        private String fmID;
        private String fmtitle;
        private String fmpic;
        private String userID;
        private String fmgood;
        private String fmtime;
        private String username;
        private String userpic;
        private String type;
        private String vurl;

        public String getFmID() {
            return fmID;
        }

        public void setFmID(String fmID) {
            this.fmID = fmID;
        }

        public String getFmtitle() {
            return fmtitle;
        }

        public void setFmtitle(String fmtitle) {
            this.fmtitle = fmtitle;
        }

        public String getFmpic() {
            return fmpic;
        }

        public void setFmpic(String fmpic) {
            this.fmpic = fmpic;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getFmgood() {
            return fmgood;
        }

        public void setFmgood(String fmgood) {
            this.fmgood = fmgood;
        }

        public String getFmtime() {
            return fmtime;
        }

        public void setFmtime(String fmtime) {
            this.fmtime = fmtime;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }
    }
}
