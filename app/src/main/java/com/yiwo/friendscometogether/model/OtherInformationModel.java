package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/11.
 */

public class OtherInformationModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"info":{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","username":"我们旅游吧","wy_accid":"yy18346038613","wy_token":"9a9311810bbe4e185c99b29f20a6b8c0","usergrade":"1","userautograph":"啊分经济区w\u2006y\u2006y","age":"19岁","address":"北京","constellation":"处女座","follow":"1","friends":"1","leader":"0","userlike":"0","userbelike":"1","uid":"38","GiveCount":0},"ListPicNews":[{"fmID":"147","fmtitle":"yyy","fmlook":"1","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180905/0fd4c74377b3846611082ed7558a3784.png","fmcomment":0},{"fmID":"146","fmtitle":"一只喵","fmlook":"5","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180905/4039b2ce333519b8ebfa8521c4456286.png","fmcomment":0},{"fmID":"143","fmtitle":"他天天","fmlook":"15","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180904/b1335b84feb3e1bf259b5a2d46b14133.png","fmcomment":0},{"fmID":"109","fmtitle":"fff","fmlook":"3","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180827/89d863240ab6c7b44b26fd313b82d546.png","fmcomment":0},{"fmID":"106","fmtitle":"00","fmlook":"13","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180824/2018bc18aaf1de2a33bedc0322a60955.png","fmcomment":0},{"fmID":"102","fmtitle":"uuu","fmlook":"15","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180824/e9a9eb2e50a55b7691c7044b567a74bd.png","fmcomment":0},{"fmID":"101","fmtitle":"兔兔兔兔头","fmlook":"32","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180827/dad9994c1fa1ce720b707fbb098019a3.png","fmcomment":0},{"fmID":"100","fmtitle":"哈尔滨","fmlook":"13","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180823/9c2ca633c5212bcc34798897f753039c.png","fmcomment":0}],"ListActiviy":[{"pfID":"35","pftitle":"bab","pfpic":"http://47.92.136.19/uploads/xingcheng/20180831/32fae1d5a04b7cf68d6df3f625d8f35c.png","pflook":"18","pfcomment":"0","pffavorite":"1"},{"pfID":"17","pftitle":"邀请活动","pfpic":"http://47.92.136.19/uploads/xingcheng/20180824/b1182096d06b02b2d0781d0e03088b26.jpg","pflook":"23","pfcomment":"0","pffavorite":"0"},{"pfID":"16","pftitle":"七台河7天乐","pfpic":"http://47.92.136.19/uploads/xingcheng/20180824/3f8967756e8d4617caf884748d740a00.jpeg","pflook":"8","pfcomment":"0","pffavorite":"0"},{"pfID":"15","pftitle":"这是一个测试","pfpic":"http://47.92.136.19/uploads/xingcheng/20180823/d863429653e806c73207f4c3efb15874.jpeg","pflook":"16","pfcomment":"0","pffavorite":"0"},{"pfID":"13","pftitle":"活动","pfpic":"http://47.92.136.19/uploads/xingcheng/20180822/20180822/65ec430f285c5517ca1bb7c2c1fc8533.png","pflook":"31","pfcomment":"0","pffavorite":"0"},{"pfID":"12","pftitle":"粑粑","pfpic":"http://47.92.136.19/","pflook":"11","pfcomment":"0","pffavorite":"0"},{"pfID":"11","pftitle":"粑粑","pfpic":"http://47.92.136.19/","pflook":"5","pfcomment":"0","pffavorite":"0"},{"pfID":"10","pftitle":"哈哈哈哈哈哈哈","pfpic":"http://47.92.136.19/","pflook":"2","pfcomment":"0","pffavorite":"0"},{"pfID":"9","pftitle":"哈哈哈哈哈哈哈","pfpic":"http://47.92.136.19/","pflook":"2","pfcomment":"0","pffavorite":"0"},{"pfID":"7","pftitle":"炒白菜吃不吃","pfpic":"http://47.92.136.19/uploads/xingcheng/20180821/20180821/da99cff29dd6e753c17b6d1616d635ec.png","pflook":"12","pfcomment":"0","pffavorite":"0"},{"pfID":"6","pftitle":"哈哈哈哈哈哈","pfpic":"http://47.92.136.19/uploads/xingcheng/20180822/20180822/490e842863fcb405ae59116dbf8cbd5d.png","pflook":"8","pfcomment":"0","pffavorite":"0"},{"pfID":"4","pftitle":"大海啊全是水","pfpic":"http://47.92.136.19/uploads/xingcheng/20180820/20180820/96d3a1fd92b7f9c83636971631119579.png","pflook":"83","pfcomment":"0","pffavorite":"0"}]}
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
         * info : {"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","username":"我们旅游吧","wy_accid":"yy18346038613","wy_token":"9a9311810bbe4e185c99b29f20a6b8c0","usergrade":"1","userautograph":"啊分经济区w\u2006y\u2006y","age":"19岁","address":"北京","constellation":"处女座","follow":"1","friends":"1","leader":"0","userlike":"0","userbelike":"1","uid":"38","GiveCount":0}
         * ListPicNews : [{"fmID":"147","fmtitle":"yyy","fmlook":"1","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180905/0fd4c74377b3846611082ed7558a3784.png","fmcomment":0},{"fmID":"146","fmtitle":"一只喵","fmlook":"5","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180905/4039b2ce333519b8ebfa8521c4456286.png","fmcomment":0},{"fmID":"143","fmtitle":"他天天","fmlook":"15","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180904/b1335b84feb3e1bf259b5a2d46b14133.png","fmcomment":0},{"fmID":"109","fmtitle":"fff","fmlook":"3","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180827/89d863240ab6c7b44b26fd313b82d546.png","fmcomment":0},{"fmID":"106","fmtitle":"00","fmlook":"13","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180824/2018bc18aaf1de2a33bedc0322a60955.png","fmcomment":0},{"fmID":"102","fmtitle":"uuu","fmlook":"15","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180824/e9a9eb2e50a55b7691c7044b567a74bd.png","fmcomment":0},{"fmID":"101","fmtitle":"兔兔兔兔头","fmlook":"32","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180827/dad9994c1fa1ce720b707fbb098019a3.png","fmcomment":0},{"fmID":"100","fmtitle":"哈尔滨","fmlook":"13","fmgood":"0","fmfavorite":"0","fmpic":"http://47.92.136.19/uploads/article/20180823/9c2ca633c5212bcc34798897f753039c.png","fmcomment":0}]
         * ListActiviy : [{"pfID":"35","pftitle":"bab","pfpic":"http://47.92.136.19/uploads/xingcheng/20180831/32fae1d5a04b7cf68d6df3f625d8f35c.png","pflook":"18","pfcomment":"0","pffavorite":"1"},{"pfID":"17","pftitle":"邀请活动","pfpic":"http://47.92.136.19/uploads/xingcheng/20180824/b1182096d06b02b2d0781d0e03088b26.jpg","pflook":"23","pfcomment":"0","pffavorite":"0"},{"pfID":"16","pftitle":"七台河7天乐","pfpic":"http://47.92.136.19/uploads/xingcheng/20180824/3f8967756e8d4617caf884748d740a00.jpeg","pflook":"8","pfcomment":"0","pffavorite":"0"},{"pfID":"15","pftitle":"这是一个测试","pfpic":"http://47.92.136.19/uploads/xingcheng/20180823/d863429653e806c73207f4c3efb15874.jpeg","pflook":"16","pfcomment":"0","pffavorite":"0"},{"pfID":"13","pftitle":"活动","pfpic":"http://47.92.136.19/uploads/xingcheng/20180822/20180822/65ec430f285c5517ca1bb7c2c1fc8533.png","pflook":"31","pfcomment":"0","pffavorite":"0"},{"pfID":"12","pftitle":"粑粑","pfpic":"http://47.92.136.19/","pflook":"11","pfcomment":"0","pffavorite":"0"},{"pfID":"11","pftitle":"粑粑","pfpic":"http://47.92.136.19/","pflook":"5","pfcomment":"0","pffavorite":"0"},{"pfID":"10","pftitle":"哈哈哈哈哈哈哈","pfpic":"http://47.92.136.19/","pflook":"2","pfcomment":"0","pffavorite":"0"},{"pfID":"9","pftitle":"哈哈哈哈哈哈哈","pfpic":"http://47.92.136.19/","pflook":"2","pfcomment":"0","pffavorite":"0"},{"pfID":"7","pftitle":"炒白菜吃不吃","pfpic":"http://47.92.136.19/uploads/xingcheng/20180821/20180821/da99cff29dd6e753c17b6d1616d635ec.png","pflook":"12","pfcomment":"0","pffavorite":"0"},{"pfID":"6","pftitle":"哈哈哈哈哈哈","pfpic":"http://47.92.136.19/uploads/xingcheng/20180822/20180822/490e842863fcb405ae59116dbf8cbd5d.png","pflook":"8","pfcomment":"0","pffavorite":"0"},{"pfID":"4","pftitle":"大海啊全是水","pfpic":"http://47.92.136.19/uploads/xingcheng/20180820/20180820/96d3a1fd92b7f9c83636971631119579.png","pflook":"83","pfcomment":"0","pffavorite":"0"}]
         */

        private InfoBean info;
        private List<ListPicNewsBean> ListPicNews;
        private List<ListActiviyBean> ListActiviy;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<ListPicNewsBean> getListPicNews() {
            return ListPicNews;
        }

        public void setListPicNews(List<ListPicNewsBean> ListPicNews) {
            this.ListPicNews = ListPicNews;
        }

        public List<ListActiviyBean> getListActiviy() {
            return ListActiviy;
        }

        public void setListActiviy(List<ListActiviyBean> ListActiviy) {
            this.ListActiviy = ListActiviy;
        }

        public static class InfoBean {
            /**
             * userpic : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
             * username : 我们旅游吧
             * wy_accid : yy18346038613
             * wy_token : 9a9311810bbe4e185c99b29f20a6b8c0
             * usergrade : 1
             * userautograph : 啊分经济区w y y
             * age : 19岁
             * address : 北京
             * constellation : 处女座
             * follow : 1
             * friends : 1
             * leader : 0
             * userlike : 0
             * userbelike : 1
             * uid : 38
             * GiveCount : 0
             */

            private String usermarry;
            private String userpic;
            private String username;
            private String wy_accid;
            private String wy_token;
            private String usergrade;
            private String userautograph;
            private String age;
            private String address;
            private String constellation;
            private String follow;
            private String friends;
            private String leader;
            private String userlike;
            private String userbelike;
            private String uid;
            private int GiveCount;

            public String getUsermarry() {
                return usermarry;
            }

            public void setUsermarry(String usermarry) {
                this.usermarry = usermarry;
            }

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getWy_accid() {
                return wy_accid;
            }

            public void setWy_accid(String wy_accid) {
                this.wy_accid = wy_accid;
            }

            public String getWy_token() {
                return wy_token;
            }

            public void setWy_token(String wy_token) {
                this.wy_token = wy_token;
            }

            public String getUsergrade() {
                return usergrade;
            }

            public void setUsergrade(String usergrade) {
                this.usergrade = usergrade;
            }

            public String getUserautograph() {
                return userautograph;
            }

            public void setUserautograph(String userautograph) {
                this.userautograph = userautograph;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getConstellation() {
                return constellation;
            }

            public void setConstellation(String constellation) {
                this.constellation = constellation;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getFriends() {
                return friends;
            }

            public void setFriends(String friends) {
                this.friends = friends;
            }

            public String getLeader() {
                return leader;
            }

            public void setLeader(String leader) {
                this.leader = leader;
            }

            public String getUserlike() {
                return userlike;
            }

            public void setUserlike(String userlike) {
                this.userlike = userlike;
            }

            public String getUserbelike() {
                return userbelike;
            }

            public void setUserbelike(String userbelike) {
                this.userbelike = userbelike;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public int getGiveCount() {
                return GiveCount;
            }

            public void setGiveCount(int GiveCount) {
                this.GiveCount = GiveCount;
            }
        }

        public static class ListPicNewsBean {
            /**
             * fmID : 147
             * fmtitle : yyy
             * fmlook : 1
             * fmgood : 0
             * fmfavorite : 0
             * fmpic : http://47.92.136.19/uploads/article/20180905/0fd4c74377b3846611082ed7558a3784.png
             * fmcomment : 0
             */

            private String fmID;
            private String fmtitle;
            private String fmlook;
            private String fmgood;
            private String fmfavorite;
            private String fmpic;
            private int fmcomment;

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

            public String getFmlook() {
                return fmlook;
            }

            public void setFmlook(String fmlook) {
                this.fmlook = fmlook;
            }

            public String getFmgood() {
                return fmgood;
            }

            public void setFmgood(String fmgood) {
                this.fmgood = fmgood;
            }

            public String getFmfavorite() {
                return fmfavorite;
            }

            public void setFmfavorite(String fmfavorite) {
                this.fmfavorite = fmfavorite;
            }

            public String getFmpic() {
                return fmpic;
            }

            public void setFmpic(String fmpic) {
                this.fmpic = fmpic;
            }

            public int getFmcomment() {
                return fmcomment;
            }

            public void setFmcomment(int fmcomment) {
                this.fmcomment = fmcomment;
            }
        }

        public static class ListActiviyBean {
            /**
             * pfID : 35
             * pftitle : bab
             * pfpic : http://47.92.136.19/uploads/xingcheng/20180831/32fae1d5a04b7cf68d6df3f625d8f35c.png
             * pflook : 18
             * pfcomment : 0
             * pffavorite : 1
             */

            private String pfID;
            private String pftitle;
            private String pfpic;
            private String pflook;
            private String pfcomment;
            private String pffavorite;

            public String getPfID() {
                return pfID;
            }

            public void setPfID(String pfID) {
                this.pfID = pfID;
            }

            public String getPftitle() {
                return pftitle;
            }

            public void setPftitle(String pftitle) {
                this.pftitle = pftitle;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPfcomment() {
                return pfcomment;
            }

            public void setPfcomment(String pfcomment) {
                this.pfcomment = pfcomment;
            }

            public String getPffavorite() {
                return pffavorite;
            }

            public void setPffavorite(String pffavorite) {
                this.pffavorite = pffavorite;
            }
        }
    }
}
