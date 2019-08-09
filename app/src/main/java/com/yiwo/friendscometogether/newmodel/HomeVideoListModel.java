package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/8/7.
 */

public class HomeVideoListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"status":"0","praise_num":"0","comment_num":"0","vID":"88","vname":"测试测试","img":"http://vodsmnjjkoj.nosdn.127.net/d7f290bc-6cca-4252-b427-53902b02b4c6.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/052dcde9-9552-49b0-8ff2-949a269a83c1.mp4","userID":"1","vtime":"2019-07-20","username":"大圣","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"86","vname":"倪敏民工","img":"http://vodsmnjjkoj.nosdn.127.net/0067bdf9-4d08-48cf-8228-c5eea0b5bfe6_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/0067bdf9-4d08-48cf-8228-c5eea0b5bfe6.mp4","userID":"7","vtime":"2019-07-19","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"85","vname":"哦们","img":"http://vodsmnjjkoj.nosdn.127.net/65f045e3-97c1-48a2-bb4e-42ca9ad91a7e_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/65f045e3-97c1-48a2-bb4e-42ca9ad91a7e.mp4","userID":"7","vtime":"2019-07-19","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"84","vname":"萝莉控","img":"http://vodsmnjjkoj.nosdn.127.net/be2d5810-e0e5-471f-b41b-e46600a87d3e.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/2dda6c7f-681a-4be3-9bdb-5955fd66dd70.mp4","userID":"7","vtime":"2019-07-19","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"81","vname":"啦啦啦啦咯哦","img":"http://vodsmnjjkoj.nosdn.127.net/6a44720e-4e97-485c-be83-d6b7a8356e99_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/6a44720e-4e97-485c-be83-d6b7a8356e99.mp4","userID":"7","vtime":"2019-07-18","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"80","vname":"啦啦啦啦","img":"http://vodsmnjjkoj.nosdn.127.net/59cd04e2-b338-4d70-bc25-ca3c81abc65d.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/61998859-5130-4c4a-ac67-8bff3fd2af2e.mp4","userID":"7","vtime":"2019-07-18","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"79","vname":"dhdjjd","img":"http://vodsmnjjkoj.nosdn.127.net/0dc141c8-bfa8-4a8f-b994-0f92ddf54b21_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/0dc141c8-bfa8-4a8f-b994-0f92ddf54b21.mp4","userID":"7","vtime":"2019-07-18","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"77","vname":"rghg","img":"http://vodsmnjjkoj.nosdn.127.net/6754980e-15c0-4713-a250-8c720f45d976_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/6754980e-15c0-4713-a250-8c720f45d976.mp4","userID":"7","vtime":"2019-07-18","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"69","vname":"dhfhgfgg\n","img":"http://vodsmnjjkoj.nosdn.127.net/85b01e4a-4df3-4055-8b0a-bac723d56f7e_1_0_0.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/85b01e4a-4df3-4055-8b0a-bac723d56f7e.mp4","userID":"7","vtime":"2019-07-18","username":"大叔爱健身","userpic":"","type":"0","accesspassword":""},{"status":"0","praise_num":"0","comment_num":"0","vID":"39","vname":"测试声音","img":"http://vodsmnjjkoj.nosdn.127.net/305897a2-602e-4c41-b7f1-48c710460371.jpg","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/911f348f-fc46-434c-9ebf-bdd32cdf3cce.mp4","userID":"1","vtime":"2019-07-16","username":"大圣","userpic":"","type":"0","accesspassword":""}]
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
         * status : 0
         * praise_num : 0
         * comment_num : 0
         * vID : 88
         * vname : 测试测试
         * img : http://vodsmnjjkoj.nosdn.127.net/d7f290bc-6cca-4252-b427-53902b02b4c6.jpg
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/052dcde9-9552-49b0-8ff2-949a269a83c1.mp4
         * userID : 1
         * vtime : 2019-07-20
         * username : 大圣
         * userpic :
         * type : 0
         * accesspassword :
         */

        private String status;
        private String praise_num;
        private String comment_num;
        private String vID;
        private String vname;
        private String img;
        private String vurl;
        private String userID;
        private String vtime;
        private String username;
        private String userpic;
        private String type;
        private String accesspassword;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getVtime() {
            return vtime;
        }

        public void setVtime(String vtime) {
            this.vtime = vtime;
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

        public String getAccesspassword() {
            return accesspassword;
        }

        public void setAccesspassword(String accesspassword) {
            this.accesspassword = accesspassword;
        }
    }
}
