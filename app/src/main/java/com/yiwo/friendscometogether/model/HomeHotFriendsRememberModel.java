package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/20.
 */

public class HomeHotFriendsRememberModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"info":[{"usergrade":"0","userID":"39","userphone":"13945060493","upicurl":"http://47.92.136.19/uploads/header/2018/08/11/750ed5b31e9e6c2d4186feabcc569b58153395189511.jpg","fmhot":"1","accesspassword":"","fmID":"99","fmtitle":"大理","fmcontent":"","fmpic":"http://47.92.136.19/uploads/article/20180815/20180815/bdbc0953977be10d666325b274a6bb96.png","fmtime":"2018-08-15","fmlook":"103","fmcomment":"0","username":"旅行家","follow":"1","type":"1"},{"usergrade":"0","userID":"43","userphone":"15244753606","upicurl":"http://47.92.136.19/uploads/header/2018/08/17/a8e2ff24698ad929ced37d4a0a55d9ea15344694853.jpg","fmhot":"1","accesspassword":"","fmID":"98","fmtitle":"杭州，一个你来了还想来的魅力之城","fmcontent":"","fmpic":"http://47.92.136.19/uploads/article/20180814/20180814/21b0058249022c55741d17a5916a2b91.jpeg","fmtime":"2018-08-14","fmlook":"103","fmcomment":"0","username":"3606_43","follow":"1","type":"2"},{"usergrade":"0","userID":"7","userphone":"15244615473","upicurl":"http://47.92.136.19/uploads/header/2018/08/28/b467dae87d8731602eb8386df9dbf172153544049715.jpg","fmhot":"0","accesspassword":"","fmID":"85","fmtitle":"泰山七日游","fmcontent":"那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我那里好好玩啊，下次有谁一起去吗？请告诉我","fmpic":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","fmtime":"2018-08-10","fmlook":"365","fmcomment":"0","username":"吃了","follow":"0","type":"1"}],"video":[{"vID":"11","vname":"神秘岛","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","img":"http://47.92.136.19/uploads/videos/20180830/65a5afaca09024f3a575ae1b712e7dee.jpg","look_num":"2"},{"vID":"10","vname":"向日葵小镇","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","img":"http://47.92.136.19/uploads/videos/20180830/f48561675ba33b52521a2b7f0f061e37.jpg","look_num":"1"},{"vID":"9","vname":"巴厘岛","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","img":"http://47.92.136.19/uploads/videos/20180830/a43868d4d5c68eba21a3ebc80d3142a7.jpg","look_num":"1"},{"vID":"8","vname":"瀑布","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/bd2fd1100d99429c83256f652f6fef14_1520990405221_1520990705541_257737373-00000.flv","img":"http://47.92.136.19/uploads/videos/20180830/1fe920df00aa277241adae8d6199fd82.jpg","look_num":"0"},{"vID":"7","vname":"视频测试","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","img":"http://47.92.136.19/uploads/videos/20180830/2e24d88777cf9271bb5620b2d45841ff.jpg","look_num":"3"},{"vID":"5","vname":"看视频来爱奇艺","vurl":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","img":"http://47.92.136.19/uploads/videos/20180723/20180723/603dc9e676722de754ed9f5dcf3029ac.jpg","look_num":"39"},{"vID":"4","vname":"看视频来优酷","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/bd2fd1100d99429c83256f652f6fef14_1520990405221_1520990705541_257737373-00000.flv","img":"http://47.92.136.19/uploads/videos/20180723/20180723/ec202c25a5914855399f018f9d50752c.jpg","look_num":"6"}]}
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
        private List<InfoBean> info;
        private List<VideoBean> video;

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class InfoBean {
            /**
             * usergrade : 0
             * userID : 39
             * userphone : 13945060493
             * upicurl : http://47.92.136.19/uploads/header/2018/08/11/750ed5b31e9e6c2d4186feabcc569b58153395189511.jpg
             * fmhot : 1
             * accesspassword :
             * fmID : 99
             * fmtitle : 大理
             * fmcontent :
             * fmpic : http://47.92.136.19/uploads/article/20180815/20180815/bdbc0953977be10d666325b274a6bb96.png
             * fmtime : 2018-08-15
             * fmlook : 103
             * fmcomment : 0
             * username : 旅行家
             * follow : 1
             * type : 1
             */

            private String usergrade;
            private String userID;
            private String userphone;
            private String upicurl;
            private String fmhot;
            private String accesspassword;
            private String fmID;
            private String fmtitle;
            private String fmcontent;
            private String fmpic;
            private String fmtime;
            private String fmlook;
            private String fmcomment;
            private String username;
            private String follow;
            private String type;

            public String getUsergrade() {
                return usergrade;
            }

            public void setUsergrade(String usergrade) {
                this.usergrade = usergrade;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getUserphone() {
                return userphone;
            }

            public void setUserphone(String userphone) {
                this.userphone = userphone;
            }

            public String getUpicurl() {
                return upicurl;
            }

            public void setUpicurl(String upicurl) {
                this.upicurl = upicurl;
            }

            public String getFmhot() {
                return fmhot;
            }

            public void setFmhot(String fmhot) {
                this.fmhot = fmhot;
            }

            public String getAccesspassword() {
                return accesspassword;
            }

            public void setAccesspassword(String accesspassword) {
                this.accesspassword = accesspassword;
            }

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

            public String getFmcontent() {
                return fmcontent;
            }

            public void setFmcontent(String fmcontent) {
                this.fmcontent = fmcontent;
            }

            public String getFmpic() {
                return fmpic;
            }

            public void setFmpic(String fmpic) {
                this.fmpic = fmpic;
            }

            public String getFmtime() {
                return fmtime;
            }

            public void setFmtime(String fmtime) {
                this.fmtime = fmtime;
            }

            public String getFmlook() {
                return fmlook;
            }

            public void setFmlook(String fmlook) {
                this.fmlook = fmlook;
            }

            public String getFmcomment() {
                return fmcomment;
            }

            public void setFmcomment(String fmcomment) {
                this.fmcomment = fmcomment;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class VideoBean {
            /**
             * vID : 11
             * vname : 神秘岛
             * vurl : http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4
             * img : http://47.92.136.19/uploads/videos/20180830/65a5afaca09024f3a575ae1b712e7dee.jpg
             * look_num : 2
             */

            private String vID;
            private String vname;
            private String vurl;
            private String img;
            private String look_num;

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
        }
    }
}
