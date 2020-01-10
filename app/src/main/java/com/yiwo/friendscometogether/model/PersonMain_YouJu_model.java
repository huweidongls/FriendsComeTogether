package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/6/26.
 */

public class PersonMain_YouJu_model {


    /**
     * code : 200
     * message : 获取成功!
     * obj : {"activity":[{"pfID":"78","pftitle":"哈尔滨飞越芽庄8日游","pfcomment":"0","pfcontent":"芽庄市是越南中南部沿海城市，以其质朴的海滩和卓越的潜水环境迅速成为受欢迎的国际旅游目的地，依山傍海、林木郁郁葱葱，芽庄湾是世界最美丽的海湾之一。","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20191121/9f8b60c7bc129a2ff0451f763360e3e0.jpg","pfaddress":"越南芽庄","pflook":"38599","pftime":"1个月前","begin_time":"2019-12-18","end_time":"2019-12-25","phase_id":"226","follow":"0","headportrait":"http://www.tongbanapp.com/uploads/header/2019/11/13/2a2e3ab4a156d4b2156340570cf73b9415736186374.jpg","username":"哈尔滨观光国际旅行社有限公司","author":"1"}]}
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
             * pfID : 78
             * pftitle : 哈尔滨飞越芽庄8日游
             * pfcomment : 0
             * pfcontent : 芽庄市是越南中南部沿海城市，以其质朴的海滩和卓越的潜水环境迅速成为受欢迎的国际旅游目的地，依山傍海、林木郁郁葱葱，芽庄湾是世界最美丽的海湾之一。
             * pfpic : http://www.tongbanapp.com/uploads/xingcheng/20191121/9f8b60c7bc129a2ff0451f763360e3e0.jpg
             * pfaddress : 越南芽庄
             * pflook : 38599
             * pftime : 1个月前
             * begin_time : 2019-12-18
             * end_time : 2019-12-25
             * phase_id : 226
             * follow : 0
             * headportrait : http://www.tongbanapp.com/uploads/header/2019/11/13/2a2e3ab4a156d4b2156340570cf73b9415736186374.jpg
             * username : 哈尔滨观光国际旅行社有限公司
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
            private String begin_time;
            private String end_time;
            private String phase_id;
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

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getPhase_id() {
                return phase_id;
            }

            public void setPhase_id(String phase_id) {
                this.phase_id = phase_id;
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
