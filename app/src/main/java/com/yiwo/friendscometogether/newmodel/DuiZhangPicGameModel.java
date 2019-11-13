package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/11/8.
 */

public class DuiZhangPicGameModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","picUrl":"http://www.91yiwo.com/ylyy/uploads/picgame/20191108/d3802939b1d143ab543710e1e44574af.jpg","title":"啦啦啦啦啦","answer":"啦啦"}]
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
         * id : 1
         * picUrl : http://www.91yiwo.com/ylyy/uploads/picgame/20191108/d3802939b1d143ab543710e1e44574af.jpg
         * title : 啦啦啦啦啦
         * answer : 啦啦
         * (增加字段)：shareText;1//1为分享文字 0 为不分享
         */

        private String id;
        private String picUrl;
        private String title;
        private String answer;
        private String shareText = "1";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getShareText() {
            return shareText;
        }

        public void setShareText(String shareText) {
            this.shareText = shareText;
        }
    }
}
