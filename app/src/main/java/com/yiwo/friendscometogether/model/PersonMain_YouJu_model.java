package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/6/26.
 */

public class PersonMain_YouJu_model {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"activity":[{"pfID":"38","pftitle":"测试活动0522","pfcomment":"0","pfcontent":"","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfaddress":"科技大厦","pflook":"81","pftime":"3星期前","follow":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg","username":"哈尔滨友来友约文化传媒有限公司","author":"1"},{"pfID":"38","pftitle":"测试活动0522","pfcomment":"0","pfcontent":"","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfaddress":"科技大厦","pflook":"81","pftime":"3星期前","follow":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg","username":"哈尔滨友来友约文化传媒有限公司","author":"1"},{"pfID":"37","pftitle":"瞳伴活动","pfcomment":"0","pfcontent":"","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190528/6f56de80e50c153180218cd9feb68e77.jpg","pfaddress":"哈尔滨","pflook":"9","pftime":"4星期前","follow":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg","username":"哈尔滨友来友约文化传媒有限公司","author":"1"},{"pfID":"36","pftitle":"测试活动0522","pfcomment":"0","pfcontent":"","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfaddress":"科技大厦","pflook":"8","pftime":"1个月前","follow":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg","username":"哈尔滨友来友约文化传媒有限公司","author":"1"},{"pfID":"34","pftitle":"测试邀请订单","pfcomment":"0","pfcontent":"离开家离开家离开家；离开了就； ","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190507/3e40afa64a0b689b7c9dedec43086e20.jpg","pfaddress":"；离开家；离开家； ；离开家；离开家；离开家；离开家","pflook":"71","pftime":"1个月前","follow":"0","headportrait":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg","username":"哈尔滨友来友约文化传媒有限公司","author":"1"}]}
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
        private List<ActivityBean> activity;

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class ActivityBean {
            /**
             * pfID : 38
             * pftitle : 测试活动0522
             * pfcomment : 0
             * pfcontent :
             * pfpic : http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png
             * pfaddress : 科技大厦
             * pflook : 81
             * pftime : 3星期前
             * follow : 0
             * headportrait : http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/3cf035192d93327b9a6e522a1362b16e155496723011.jpg
             * username : 哈尔滨友来友约文化传媒有限公司
             * author : 1
             */

            private String pfID;
            private String pftitle;
            private String pfcomment;
            private String pfcontent;
            private String pfpic;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String follow;
            private String headportrait;
            private String username;
            private String author;

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

            public String getPfcomment() {
                return pfcomment;
            }

            public void setPfcomment(String pfcomment) {
                this.pfcomment = pfcomment;
            }

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
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
        }
    }
}
