package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/7/24.
 */

public class UserModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"headeimg":"http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg","username":"baiducom","sex":"男","useraddress":"黑龙江省-双鸭山-尖山区","userautograph":"dsdsdsa","userbirthday":"1997年-9月-25日","usertime":"2018年-7月-5日","usercodeok":"待审核","usermarry":"是","usergrade":"0","sign":"0"}
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
         * headeimg : http://47.92.136.19/uploads/header/2018/06/27/52b94a60085237df2b0ceb1a7599f91b15300847792.jpg
         * username : baiducom
         * sex : 男
         * useraddress : 黑龙江省-双鸭山-尖山区
         * userautograph : dsdsdsa
         * userbirthday : 1997年-9月-25日
         * usertime : 2018年-7月-5日
         * usercodeok : 待审核
         * usermarry : 是
         * usergrade : 0
         * sign : 0
         */

        private String vip;
        private String headeimg;
        private String username;
        private String sex;
        private String useraddress;
        private String userautograph;
        private String userbirthday;
        private String usertime;
        private String usercodeok;
        private String usermarry;
        private String usergrade;
        private String sign;

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getHeadeimg() {
            return headeimg;
        }

        public void setHeadeimg(String headeimg) {
            this.headeimg = headeimg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUseraddress() {
            return useraddress;
        }

        public void setUseraddress(String useraddress) {
            this.useraddress = useraddress;
        }

        public String getUserautograph() {
            return userautograph;
        }

        public void setUserautograph(String userautograph) {
            this.userautograph = userautograph;
        }

        public String getUserbirthday() {
            return userbirthday;
        }

        public void setUserbirthday(String userbirthday) {
            this.userbirthday = userbirthday;
        }

        public String getUsertime() {
            return usertime;
        }

        public void setUsertime(String usertime) {
            this.usertime = usertime;
        }

        public String getUsercodeok() {
            return usercodeok;
        }

        public void setUsercodeok(String usercodeok) {
            this.usercodeok = usercodeok;
        }

        public String getUsermarry() {
            return usermarry;
        }

        public void setUsermarry(String usermarry) {
            this.usermarry = usermarry;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
