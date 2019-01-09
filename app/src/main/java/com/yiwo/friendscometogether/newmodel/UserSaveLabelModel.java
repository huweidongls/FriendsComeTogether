package com.yiwo.friendscometogether.newmodel;

/**
 * Created by Administrator on 2019/1/8.
 */

public class UserSaveLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"personality":"叫我逗比,喜欢简单,选择恐惧症,文艺,测试","motion":"跑步,单车","Music":"薛之谦,流行","Delicious ":"麻辣烫,火锅","Film":"泰坦尼克号,战狼2","book":"鲁迅,金庸","Travel":"哈尔滨,山西"}
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
         * personality : 叫我逗比,喜欢简单,选择恐惧症,文艺,测试
         * motion : 跑步,单车
         * Music : 薛之谦,流行
         * Delicious  : 麻辣烫,火锅
         * Film : 泰坦尼克号,战狼2
         * book : 鲁迅,金庸
         * Travel : 哈尔滨,山西
         */

        private String personality;
        private String motion;
        private String Music;
        private String Delicious;
        private String Film;
        private String book;
        private String Travel;

        public String getPersonality() {
            return personality;
        }

        public void setPersonality(String personality) {
            this.personality = personality;
        }

        public String getMotion() {
            return motion;
        }

        public void setMotion(String motion) {
            this.motion = motion;
        }

        public String getMusic() {
            return Music;
        }

        public void setMusic(String Music) {
            this.Music = Music;
        }

        public String getDelicious() {
            return Delicious;
        }

        public void setDelicious(String Delicious) {
            this.Delicious = Delicious;
        }

        public String getFilm() {
            return Film;
        }

        public void setFilm(String Film) {
            this.Film = Film;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getTravel() {
            return Travel;
        }

        public void setTravel(String Travel) {
            this.Travel = Travel;
        }
    }
}
