package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by ljc on 2019/6/26.
 */

public class PersonMain_Pics_model {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"Photo":[{"uid":"3","utime":"2019-04-11 14:16:43","upicurl":"http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/ca43a78b5290454d022ee3ed6bd46110155496340314.png","img_width":"","img_height":""},{"uid":"264","utime":"2019-05-06 15:14:34","upicurl":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/06/4d1cdbeacef8b7ac35665144ea03bb8a155712687419.png","img_width":"1080","img_height":"1080"},{"uid":"269","utime":"2019-05-17 09:43:08","upicurl":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/fc9d2a72b5c4f01a7b4ec16ab6ee8df6155805738811.jpeg","img_width":"1512","img_height":"1512"},{"uid":"270","utime":"2019-05-17 09:47:28","upicurl":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","img_width":"1080","img_height":"1080"}]}
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
        private List<PhotoBean> Photo;

        public List<PhotoBean> getPhoto() {
            return Photo;
        }

        public void setPhoto(List<PhotoBean> Photo) {
            this.Photo = Photo;
        }

        public static class PhotoBean {
            /**
             * uid : 3
             * utime : 2019-04-11 14:16:43
             * upicurl : http://www.91yiwo.com/ylyy/uploads/header/2019/04/11/ca43a78b5290454d022ee3ed6bd46110155496340314.png
             * img_width :
             * img_height :
             */

            private String uid;
            private String utime;
            private String upicurl;
            private String img_width;
            private String img_height;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUtime() {
                return utime;
            }

            public void setUtime(String utime) {
                this.utime = utime;
            }

            public String getUpicurl() {
                return upicurl;
            }

            public void setUpicurl(String upicurl) {
                this.upicurl = upicurl;
            }

            public String getImg_width() {
                return img_width;
            }

            public void setImg_width(String img_width) {
                this.img_width = img_width;
            }

            public String getImg_height() {
                return img_height;
            }

            public void setImg_height(String img_height) {
                this.img_height = img_height;
            }
        }
    }
}
