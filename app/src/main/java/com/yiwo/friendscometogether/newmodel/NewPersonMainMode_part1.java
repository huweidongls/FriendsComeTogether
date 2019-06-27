package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;

/**
 * Created by ljc on 2019/6/25.
 */

public class NewPersonMainMode_part1 {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"info":{"wy_accid":"tongban15754633415","age":"24岁","address":"黑龙江省-哈尔滨市","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","autograph":"嗷呜～","sex":"0","userlike":"5","GiveCount":0,"friends":"0","follow":"0","fans":"2","usermarry":"1","usergrade":"1","usercodeok":"1","otherUserId":"4","if_kefu":"0"},"mytag":{"personality":"","motion":"","Music":"","Delicious":"","Film":"","book":"","Travel":""},"usertag":{"personality":"","motion":"台球,健身,篮球","Music":"民谣","Delicious":"","Film":"","book":"火影忍者","Travel":"","Same":{"personality":"","motion":"","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}},"otherUserId":"4","if_kefu":"0"}
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
         * info : {"wy_accid":"tongban15754633415","age":"24岁","address":"黑龙江省-哈尔滨市","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生","autograph":"嗷呜～","sex":"0","userlike":"5","GiveCount":0,"friends":"0","follow":"0","fans":"2","usermarry":"1","usergrade":"1","usercodeok":"1","otherUserId":"4","if_kefu":"0"}
         * mytag : {"personality":"","motion":"","Music":"","Delicious":"","Film":"","book":"","Travel":""}
         * usertag : {"personality":"","motion":"台球,健身,篮球","Music":"民谣","Delicious":"","Film":"","book":"火影忍者","Travel":"","Same":{"personality":"","motion":"","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}}
         * otherUserId : 4
         * if_kefu : 0
         */

        private InfoBean info;
        private MytagBean mytag;
        private UsertagBean usertag;
        private String otherUserId;
        private String if_kefu;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public MytagBean getMytag() {
            return mytag;
        }

        public void setMytag(MytagBean mytag) {
            this.mytag = mytag;
        }

        public UsertagBean getUsertag() {
            return usertag;
        }

        public void setUsertag(UsertagBean usertag) {
            this.usertag = usertag;
        }

        public String getOtherUserId() {
            return otherUserId;
        }

        public void setOtherUserId(String otherUserId) {
            this.otherUserId = otherUserId;
        }

        public String getIf_kefu() {
            return if_kefu;
        }

        public void setIf_kefu(String if_kefu) {
            this.if_kefu = if_kefu;
        }

        public static class InfoBean {
            /**
             * wy_accid : tongban15754633415
             * age : 24岁
             * address : 黑龙江省-哈尔滨市
             * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png
             * username : 花生
             * autograph : 嗷呜～
             * sex : 0
             * userlike : 5
             * GiveCount : 0
             * friends : 0
             * follow : 0
             * fans : 2
             * usermarry : 1
             * usergrade : 1
             * usercodeok : 1
             * otherUserId : 4
             * if_kefu : 0
             */

            private String wy_accid;
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
            private String usermarry;
            private String usergrade;
            private String usercodeok;
            private String otherUserId;
            private String if_kefu;

            public String getWy_accid() {
                return wy_accid;
            }

            public void setWy_accid(String wy_accid) {
                this.wy_accid = wy_accid;
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

            public String getUsercodeok() {
                return usercodeok;
            }

            public void setUsercodeok(String usercodeok) {
                this.usercodeok = usercodeok;
            }

            public String getOtherUserId() {
                return otherUserId;
            }

            public void setOtherUserId(String otherUserId) {
                this.otherUserId = otherUserId;
            }

            public String getIf_kefu() {
                return if_kefu;
            }

            public void setIf_kefu(String if_kefu) {
                this.if_kefu = if_kefu;
            }
        }

        public static class MytagBean {
            /**
             * personality :
             * motion :
             * Music :
             * Delicious :
             * Film :
             * book :
             * Travel :
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

        public static class UsertagBean  implements Serializable {
            /**
             * personality :
             * motion : 台球,健身,篮球
             * Music : 民谣
             * Delicious :
             * Film :
             * book : 火影忍者
             * Travel :
             * Same : {"personality":"","motion":"","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}
             */

            private String personality;
            private String motion;
            private String Music;
            private String Delicious;
            private String Film;
            private String book;
            private String Travel;
            private SameBean Same;

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

            public SameBean getSame() {
                return Same;
            }

            public void setSame(SameBean Same) {
                this.Same = Same;
            }

            public static class SameBean implements Serializable {
                /**
                 * personality :
                 * motion :
                 * Music :
                 * Delicious :
                 * Film :
                 * book :
                 * Travel :
                 * Same :
                 */

                private String personality;
                private String motion;
                private String Music;
                private String Delicious;
                private String Film;
                private String book;
                private String Travel;
                private String Same;

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

                public String getSame() {
                    return Same;
                }

                public void setSame(String Same) {
                    this.Same = Same;
                }
            }
        }
    }
}
