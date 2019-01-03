package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/1/3.
 */

public class PersonMainModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"info":{"age":"19岁","address":"上海","userpic":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","autograph":"与嘿嘿嘿","sex":"1","userlike":"6","GiveCount":0,"friends":"1","follow":"1","fans":"2"},"Friend":[{"pfID":"255","pftitle":"测试1","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-0e0b140da0a63100da9b1d232f75634b5528.jpeg","http://47.92.136.19/uploads/article/20190102/1-3df8f46d81c343fb9c28c64c295c5afd5398.jpeg","http://47.92.136.19/uploads/article/20190102/2-bdb8edca03f1c22a38a7bb2dbde9f79a3775.jpeg","http://47.92.136.19/uploads/article/20190102/3-8dd6d7e4c2fbf5da736b98995813835f8150.jpeg"],"userID":"38","pfaddress":"","pflook":"7","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"},{"pfID":"256","pftitle":"测试2","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-8c3978afb9c26ce9ec36077fa6129b352911.jpeg","http://47.92.136.19/uploads/article/20190102/1-a03590c7e11f13f13c72073851b9d5ca9691.jpeg","http://47.92.136.19/uploads/article/20190102/2-35f897e48347866abcb46477e9539b095082.jpeg","http://47.92.136.19/uploads/article/20190102/3-9765b714be3404b6c193192e547a921a6047.jpeg","http://47.92.136.19/uploads/article/20190102/4-182513ed25fd537421d19da1c4deb4be4228.jpeg","http://47.92.136.19/uploads/article/20190102/5-d18db348b7d2e9cc3eab7d1fb656fbdb6769.jpeg"],"userID":"38","pfaddress":"","pflook":"8","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"},{"pfID":"257","pftitle":"测试3","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-5a522a353191fcb453880be7b11d92645621.jpeg","http://47.92.136.19/uploads/article/20190102/1-e183eccb97a142e007c33fab37c047b88391.jpeg","http://47.92.136.19/uploads/article/20190102/2-e2a7babb799c5054767abf36df888f883609.jpeg","http://47.92.136.19/uploads/article/20190102/3-5246fc9ed4204e7f4845918862d169ba4652.jpeg","http://47.92.136.19/uploads/article/20190102/4-e5d837fd19e812005384aac91ca1d1361966.jpeg","http://47.92.136.19/uploads/article/20190102/5-7f1db6e048a73f1737e29fe0758e11522211.jpeg"],"userID":"38","pfaddress":"","pflook":"9","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"}],"activity":[{"pfID":"111","pftitle":"这是一个测试活动","pfcontent":"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","pfpic":["http://47.92.136.19/uploads/xingcheng/20181225/1558b898215c6a63fa2fba4523880e94.png"],"pfaddress":"故宫","pflook":"97","pftime":"1星期前","follow":"1","headportrait":"http://47.92.136.19/uploads/header/2018/12/25/03b037823c230dd7aea6a34123aed9e7154571847112.jpg","username":"哈尔滨精纳科技有限公司","author":"1"}],"Photo":[{"uid":"66","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","utime":"2018-08-16 14:04:06","udel":"0"},{"uid":"74","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/08/27/dbd10defbc78146ac28d044c611230df153536143412.jpg","utime":"2018-08-27 17:17:14","udel":"0"},{"uid":"89","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/09/03/9ba011e84ec5e52f41d837ae403a3e59153595464016.jpg","utime":"2018-09-03 14:04:00","udel":"0"}]}
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
         * info : {"age":"19岁","address":"上海","userpic":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","autograph":"与嘿嘿嘿","sex":"1","userlike":"6","GiveCount":0,"friends":"1","follow":"1","fans":"2"}
         * Friend : [{"pfID":"255","pftitle":"测试1","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-0e0b140da0a63100da9b1d232f75634b5528.jpeg","http://47.92.136.19/uploads/article/20190102/1-3df8f46d81c343fb9c28c64c295c5afd5398.jpeg","http://47.92.136.19/uploads/article/20190102/2-bdb8edca03f1c22a38a7bb2dbde9f79a3775.jpeg","http://47.92.136.19/uploads/article/20190102/3-8dd6d7e4c2fbf5da736b98995813835f8150.jpeg"],"userID":"38","pfaddress":"","pflook":"7","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"},{"pfID":"256","pftitle":"测试2","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-8c3978afb9c26ce9ec36077fa6129b352911.jpeg","http://47.92.136.19/uploads/article/20190102/1-a03590c7e11f13f13c72073851b9d5ca9691.jpeg","http://47.92.136.19/uploads/article/20190102/2-35f897e48347866abcb46477e9539b095082.jpeg","http://47.92.136.19/uploads/article/20190102/3-9765b714be3404b6c193192e547a921a6047.jpeg","http://47.92.136.19/uploads/article/20190102/4-182513ed25fd537421d19da1c4deb4be4228.jpeg","http://47.92.136.19/uploads/article/20190102/5-d18db348b7d2e9cc3eab7d1fb656fbdb6769.jpeg"],"userID":"38","pfaddress":"","pflook":"8","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"},{"pfID":"257","pftitle":"测试3","pfcontent":"","pfpic":["http://47.92.136.19/uploads/article/20190102/0-5a522a353191fcb453880be7b11d92645621.jpeg","http://47.92.136.19/uploads/article/20190102/1-e183eccb97a142e007c33fab37c047b88391.jpeg","http://47.92.136.19/uploads/article/20190102/2-e2a7babb799c5054767abf36df888f883609.jpeg","http://47.92.136.19/uploads/article/20190102/3-5246fc9ed4204e7f4845918862d169ba4652.jpeg","http://47.92.136.19/uploads/article/20190102/4-e5d837fd19e812005384aac91ca1d1361966.jpeg","http://47.92.136.19/uploads/article/20190102/5-7f1db6e048a73f1737e29fe0758e11522211.jpeg"],"userID":"38","pfaddress":"","pflook":"9","pftime":"23小时前","fmcomment":"0","headportrait":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","username":"一样一样","comment_list":[],"commentcount":"0"}]
         * activity : [{"pfID":"111","pftitle":"这是一个测试活动","pfcontent":"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","pfpic":["http://47.92.136.19/uploads/xingcheng/20181225/1558b898215c6a63fa2fba4523880e94.png"],"pfaddress":"故宫","pflook":"97","pftime":"1星期前","follow":"1","headportrait":"http://47.92.136.19/uploads/header/2018/12/25/03b037823c230dd7aea6a34123aed9e7154571847112.jpg","username":"哈尔滨精纳科技有限公司","author":"1"}]
         * Photo : [{"uid":"66","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","utime":"2018-08-16 14:04:06","udel":"0"},{"uid":"74","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/08/27/dbd10defbc78146ac28d044c611230df153536143412.jpg","utime":"2018-08-27 17:17:14","udel":"0"},{"uid":"89","userID":"38","upicurl":"http://47.92.136.19/uploads/header/2018/09/03/9ba011e84ec5e52f41d837ae403a3e59153595464016.jpg","utime":"2018-09-03 14:04:00","udel":"0"}]
         */

        private InfoBean info;
        private List<FriendBean> Friend;
        private List<ActivityBean> activity;
        private List<PhotoBean> Photo;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<FriendBean> getFriend() {
            return Friend;
        }

        public void setFriend(List<FriendBean> Friend) {
            this.Friend = Friend;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public List<PhotoBean> getPhoto() {
            return Photo;
        }

        public void setPhoto(List<PhotoBean> Photo) {
            this.Photo = Photo;
        }

        public static class InfoBean {
            /**
             * age : 19岁
             * address : 上海
             * userpic : http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg
             * username : 一样一样
             * autograph : 与嘿嘿嘿
             * sex : 1
             * userlike : 6
             * GiveCount : 0
             * friends : 1
             * follow : 1
             * fans : 2
             */

            private String age;
            private String address;
            private String userpic;
            private String username;
            private String autograph;
            private String sex;
            private String userlike;
            private int GiveCount;
            private String friends;
            private String follow;
            private String fans;

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

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserlike() {
                return userlike;
            }

            public void setUserlike(String userlike) {
                this.userlike = userlike;
            }

            public int getGiveCount() {
                return GiveCount;
            }

            public void setGiveCount(int GiveCount) {
                this.GiveCount = GiveCount;
            }

            public String getFriends() {
                return friends;
            }

            public void setFriends(String friends) {
                this.friends = friends;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }
        }

        public static class FriendBean {
            /**
             * pfID : 255
             * pftitle : 测试1
             * pfcontent :
             * pfpic : ["http://47.92.136.19/uploads/article/20190102/0-0e0b140da0a63100da9b1d232f75634b5528.jpeg","http://47.92.136.19/uploads/article/20190102/1-3df8f46d81c343fb9c28c64c295c5afd5398.jpeg","http://47.92.136.19/uploads/article/20190102/2-bdb8edca03f1c22a38a7bb2dbde9f79a3775.jpeg","http://47.92.136.19/uploads/article/20190102/3-8dd6d7e4c2fbf5da736b98995813835f8150.jpeg"]
             * userID : 38
             * pfaddress :
             * pflook : 7
             * pftime : 23小时前
             * fmcomment : 0
             * headportrait : http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg
             * username : 一样一样
             * comment_list : []
             * commentcount : 0
             */

            private String pfID;
            private String pftitle;
            private String pfcontent;
            private String userID;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String fmcomment;
            private String headportrait;
            private String username;
            private String commentcount;
            private List<String> pfpic;
            private List<?> comment_list;

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

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getFmcomment() {
                return fmcomment;
            }

            public void setFmcomment(String fmcomment) {
                this.fmcomment = fmcomment;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCommentcount() {
                return commentcount;
            }

            public void setCommentcount(String commentcount) {
                this.commentcount = commentcount;
            }

            public List<String> getPfpic() {
                return pfpic;
            }

            public void setPfpic(List<String> pfpic) {
                this.pfpic = pfpic;
            }

            public List<?> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<?> comment_list) {
                this.comment_list = comment_list;
            }
        }

        public static class ActivityBean {
            /**
             * pfID : 111
             * pftitle : 这是一个测试活动
             * pfcontent : 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
             * pfpic : ["http://47.92.136.19/uploads/xingcheng/20181225/1558b898215c6a63fa2fba4523880e94.png"]
             * pfaddress : 故宫
             * pflook : 97
             * pftime : 1星期前
             * follow : 1
             * headportrait : http://47.92.136.19/uploads/header/2018/12/25/03b037823c230dd7aea6a34123aed9e7154571847112.jpg
             * username : 哈尔滨精纳科技有限公司
             * author : 1
             */

            private String pfID;
            private String pftitle;
            private String pfcontent;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String follow;
            private String headportrait;
            private String username;
            private String author;
            private List<String> pfpic;

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

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<String> getPfpic() {
                return pfpic;
            }

            public void setPfpic(List<String> pfpic) {
                this.pfpic = pfpic;
            }
        }

        public static class PhotoBean {
            /**
             * uid : 66
             * userID : 38
             * upicurl : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
             * utime : 2018-08-16 14:04:06
             * udel : 0
             */

            private String uid;
            private String userID;
            private String upicurl;
            private String utime;
            private String udel;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getUpicurl() {
                return upicurl;
            }

            public void setUpicurl(String upicurl) {
                this.upicurl = upicurl;
            }

            public String getUtime() {
                return utime;
            }

            public void setUtime(String utime) {
                this.utime = utime;
            }

            public String getUdel() {
                return udel;
            }

            public void setUdel(String udel) {
                this.udel = udel;
            }
        }
    }
}
