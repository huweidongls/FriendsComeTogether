package com.yiwo.friendscometogether.newmodel;

/**
 * Created by ljc on 2019/4/10.
 */

public class AttentionNumModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : {"attentionNum":9,"attentionMe":6,"attentionActivity":6}
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
         * attentionNum : 9
         * attentionMe : 6
         * attentionActivity : 6
         */

        private int attentionNum;
        private int attentionMe;
        private int attentionActivity;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public int getAttentionMe() {
            return attentionMe;
        }

        public void setAttentionMe(int attentionMe) {
            this.attentionMe = attentionMe;
        }

        public int getAttentionActivity() {
            return attentionActivity;
        }

        public void setAttentionActivity(int attentionActivity) {
            this.attentionActivity = attentionActivity;
        }
    }
}
