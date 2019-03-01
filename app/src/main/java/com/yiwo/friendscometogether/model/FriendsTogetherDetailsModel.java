package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class FriendsTogetherDetailsModel {
    /**
     * code : 200
     * message : 操作成功!
     * obj : {"phase":[{"phase_num":"1","phase_sign_up_time":"2019-03-10 14:01:01","phase_begin_time":"2019-03-12 14:01:09","phase_over_time":"2019-03-30 14:01:14","phase_price":"0.01","phase_list":[{"userID":"71","ujall":"1","join_name":"爱摄影","noname":"0","username":"女侠爱摄影","userpic":"http://localhost/html/uploads/header/2019/01/14/54c391deef1d2febac2ac8b4558ad5dd154745770214.jpg"}]},{"phase_num":"2","phase_sign_up_time":"2019-03-20 14:08:28","phase_begin_time":"2019-04-01 14:08:38","phase_over_time":"2019-04-10 14:08:43","phase_price":"0.02","phase_list":[]}],"real_name":"1","user_tel":"15244615473","truename":"恶魔","wy_accid":"yy15244615473","roomid":"657667923","peoplesex":"无限制","pfID":"2","attention":"1","if_pay":"2","captain":"7","attention_captain":"0","capttain_name":"恶魔","capttain_pic":"http://47.92.136.19/uploads/header/2018/08/15/4360ba24a29f901291b95aec4c855e75153431703614.jpg","level":"0","if_sign":"0","title":"泰山30日游","marry":"是","age":"1-2","pfexplain":"","look":"76","pffavorite":"0","begin_time":"2018-04-08","end_time":"2018-06-05","city":"泰安","price":"1.00","woman":"0","man":"0","person_num":"2","have_num":"1","show_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","text_info":"泰山真好","info_list":[{"title":"泰山游","content":"泰山真好玩","pfpID":"2","image_list":[{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]}],"user_list":[{"userID":"38","username":"我们旅游吧","userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","num":"1"}],"comment_list":[{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","userID":"38","pctitle":"泰山真好玩","username":"我们旅游吧"}]}
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
         * phase : [{"phase_num":"1","phase_sign_up_time":"2019-03-10 14:01:01","phase_begin_time":"2019-03-12 14:01:09","phase_over_time":"2019-03-30 14:01:14","phase_price":"0.01","phase_list":[{"userID":"71","ujall":"1","join_name":"爱摄影","noname":"0","username":"女侠爱摄影","userpic":"http://localhost/html/uploads/header/2019/01/14/54c391deef1d2febac2ac8b4558ad5dd154745770214.jpg"}]},{"phase_num":"2","phase_sign_up_time":"2019-03-20 14:08:28","phase_begin_time":"2019-04-01 14:08:38","phase_over_time":"2019-04-10 14:08:43","phase_price":"0.02","phase_list":[]}]
         * real_name : 1
         * user_tel : 15244615473
         * truename : 恶魔
         * wy_accid : yy15244615473
         * roomid : 657667923
         * peoplesex : 无限制
         * pfID : 2
         * attention : 1
         * if_pay : 2
         * captain : 7
         * attention_captain : 0
         * capttain_name : 恶魔
         * capttain_pic : http://47.92.136.19/uploads/header/2018/08/15/4360ba24a29f901291b95aec4c855e75153431703614.jpg
         * level : 0
         * if_sign : 0
         * title : 泰山30日游
         * marry : 是
         * age : 1-2
         * pfexplain :
         * look : 76
         * pffavorite : 0
         * begin_time : 2018-04-08
         * end_time : 2018-06-05
         * city : 泰安
         * price : 1.00
         * woman : 0
         * man : 0
         * person_num : 2
         * have_num : 1
         * show_pic : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
         * text_info : 泰山真好
         * info_list : [{"title":"泰山游","content":"泰山真好玩","pfpID":"2","image_list":[{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]}]
         * user_list : [{"userID":"38","username":"我们旅游吧","userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","num":"1"}]
         * comment_list : [{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","userID":"38","pctitle":"泰山真好玩","username":"我们旅游吧"}]
         */

        private String real_name;
        private String user_tel;
        private String truename;
        private String wy_accid;
        private String roomid;
        private String peoplesex;
        private String pfID;
        private String attention;
        private String if_pay;
        private String captain;
        private String attention_captain;
        private String capttain_name;
        private String capttain_pic;
        private String level;
        private String if_sign;
        private String title;
        private String marry;
        private String age;
        private String pfexplain;
        private String look;
        private String pffavorite;
        private String begin_time;
        private String end_time;
        private String city;
        private String price;
        private String woman;
        private String man;
        private String person_num;
        private String have_num;
        private String show_pic;
        private String text_info;
        private List<PhaseBean> phase;
        private List<InfoListBean> info_list;
        private List<UserListBean> user_list;
        private List<CommentListBean> comment_list;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getWy_accid() {
            return wy_accid;
        }

        public void setWy_accid(String wy_accid) {
            this.wy_accid = wy_accid;
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
        }

        public String getPeoplesex() {
            return peoplesex;
        }

        public void setPeoplesex(String peoplesex) {
            this.peoplesex = peoplesex;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getAttention() {
            return attention;
        }

        public void setAttention(String attention) {
            this.attention = attention;
        }

        public String getIf_pay() {
            return if_pay;
        }

        public void setIf_pay(String if_pay) {
            this.if_pay = if_pay;
        }

        public String getCaptain() {
            return captain;
        }

        public void setCaptain(String captain) {
            this.captain = captain;
        }

        public String getAttention_captain() {
            return attention_captain;
        }

        public void setAttention_captain(String attention_captain) {
            this.attention_captain = attention_captain;
        }

        public String getCapttain_name() {
            return capttain_name;
        }

        public void setCapttain_name(String capttain_name) {
            this.capttain_name = capttain_name;
        }

        public String getCapttain_pic() {
            return capttain_pic;
        }

        public void setCapttain_pic(String capttain_pic) {
            this.capttain_pic = capttain_pic;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIf_sign() {
            return if_sign;
        }

        public void setIf_sign(String if_sign) {
            this.if_sign = if_sign;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMarry() {
            return marry;
        }

        public void setMarry(String marry) {
            this.marry = marry;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPfexplain() {
            return pfexplain;
        }

        public void setPfexplain(String pfexplain) {
            this.pfexplain = pfexplain;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }

        public String getPffavorite() {
            return pffavorite;
        }

        public void setPffavorite(String pffavorite) {
            this.pffavorite = pffavorite;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getWoman() {
            return woman;
        }

        public void setWoman(String woman) {
            this.woman = woman;
        }

        public String getMan() {
            return man;
        }

        public void setMan(String man) {
            this.man = man;
        }

        public String getPerson_num() {
            return person_num;
        }

        public void setPerson_num(String person_num) {
            this.person_num = person_num;
        }

        public String getHave_num() {
            return have_num;
        }

        public void setHave_num(String have_num) {
            this.have_num = have_num;
        }

        public String getShow_pic() {
            return show_pic;
        }

        public void setShow_pic(String show_pic) {
            this.show_pic = show_pic;
        }

        public String getText_info() {
            return text_info;
        }

        public void setText_info(String text_info) {
            this.text_info = text_info;
        }

        public List<PhaseBean> getPhase() {
            return phase;
        }

        public void setPhase(List<PhaseBean> phase) {
            this.phase = phase;
        }

        public List<InfoListBean> getInfo_list() {
            return info_list;
        }

        public void setInfo_list(List<InfoListBean> info_list) {
            this.info_list = info_list;
        }

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class PhaseBean {
            /**
             * phase_num : 1
             * phase_sign_up_time : 2019-03-10 14:01:01
             * phase_begin_time : 2019-03-12 14:01:09
             * phase_over_time : 2019-03-30 14:01:14
             * phase_price : 0.01
             * phase_list : [{"userID":"71","ujall":"1","join_name":"爱摄影","noname":"0","username":"女侠爱摄影","userpic":"http://localhost/html/uploads/header/2019/01/14/54c391deef1d2febac2ac8b4558ad5dd154745770214.jpg"}]
             */
            private String phase_id;
            private String phase_num;
            private String phase_sign_up_time;
            private String phase_begin_time;
            private String phase_over_time;
            private String phase_price;
            private List<PhaseListBean> phase_list;
            private Boolean isSelected = false;//自定义字段，是否选中
            public String getPhase_num() {
                return phase_num;
            }

            public void setPhase_num(String phase_num) {
                this.phase_num = phase_num;
            }

            public String getPhase_sign_up_time() {
                return phase_sign_up_time;
            }

            public void setPhase_sign_up_time(String phase_sign_up_time) {
                this.phase_sign_up_time = phase_sign_up_time;
            }

            public String getPhase_begin_time() {
                return phase_begin_time;
            }

            public void setPhase_begin_time(String phase_begin_time) {
                this.phase_begin_time = phase_begin_time;
            }

            public String getPhase_over_time() {
                return phase_over_time;
            }

            public void setPhase_over_time(String phase_over_time) {
                this.phase_over_time = phase_over_time;
            }

            public String getPhase_price() {
                return phase_price;
            }

            public void setPhase_price(String phase_price) {
                this.phase_price = phase_price;
            }

            public List<PhaseListBean> getPhase_list() {
                return phase_list;
            }

            public void setPhase_list(List<PhaseListBean> phase_list) {
                this.phase_list = phase_list;
            }

            public Boolean getSelected() {
                return isSelected;
            }

            public void setSelected(Boolean selected) {
                isSelected = selected;
            }

            public String getPhase_id() {
                return phase_id;
            }

            public void setPhase_id(String phase_id) {
                this.phase_id = phase_id;
            }

            public static class PhaseListBean {
                /**
                 * userID : 71
                 * ujall : 1
                 * join_name : 爱摄影
                 * noname : 0
                 * username : 女侠爱摄影
                 * userpic : http://localhost/html/uploads/header/2019/01/14/54c391deef1d2febac2ac8b4558ad5dd154745770214.jpg
                 */

                private String userID;
                private String ujall;
                private String join_name;
                private String noname;
                private String username;
                private String userpic;

                public String getUserID() {
                    return userID;
                }

                public void setUserID(String userID) {
                    this.userID = userID;
                }

                public String getUjall() {
                    return ujall;
                }

                public void setUjall(String ujall) {
                    this.ujall = ujall;
                }

                public String getJoin_name() {
                    return join_name;
                }

                public void setJoin_name(String join_name) {
                    this.join_name = join_name;
                }

                public String getNoname() {
                    return noname;
                }

                public void setNoname(String noname) {
                    this.noname = noname;
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
            }
        }

        public static class InfoListBean {
            /**
             * title : 泰山游
             * content : 泰山真好玩
             * pfpID : 2
             * image_list : [{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]
             */

            private String title;
            private String content;
            private String pfpID;
            private List<ImageListBean> image_list;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPfpID() {
                return pfpID;
            }

            public void setPfpID(String pfpID) {
                this.pfpID = pfpID;
            }

            public List<ImageListBean> getImage_list() {
                return image_list;
            }

            public void setImage_list(List<ImageListBean> image_list) {
                this.image_list = image_list;
            }

            public static class ImageListBean {
                /**
                 * pic : http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg
                 * text_info :
                 */

                private String pic;
                private String text_info;

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getText_info() {
                    return text_info;
                }

                public void setText_info(String text_info) {
                    this.text_info = text_info;
                }
            }
        }

        public static class UserListBean {
            /**
             * userID : 38
             * username : 我们旅游吧
             * userpic : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
             * num : 1
             */

            private String userID;
            private String username;
            private String userpic;
            private String num;

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
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

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class CommentListBean {
            /**
             * userpic : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
             * userID : 38
             * pctitle : 泰山真好玩
             * username : 我们旅游吧
             */

            private String userpic;
            private String userID;
            private String pctitle;
            private String username;

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getPctitle() {
                return pctitle;
            }

            public void setPctitle(String pctitle) {
                this.pctitle = pctitle;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
    /**
     * code : 200
     * message : 操作成功!
     * obj : {"phase":[{"phase_num":"1","phase_sign_up_time":"2019-03-02 15:13:48","phase_begin_time":"2019-03-01 15:13:39","phase_over_time":"2019-03-03 15:13:44","phase_price":"0.01","phase_list":[]},{"phase_num":"2","phase_sign_up_time":"2019-03-03 15:15:00","phase_begin_time":"2019-03-01 15:15:06","phase_over_time":"1970-01-01 08:00:00","phase_price":"0.01","phase_list":[]},{"phase_num":"3","phase_sign_up_time":"2019-03-07 15:15:25","phase_begin_time":"2019-03-09 15:15:29","phase_over_time":"2019-03-10 15:15:38","phase_price":"0.01","phase_list":[]}],"real_name":"1","user_tel":"15244615473","truename":"恶魔","wy_accid":"yy15244615473","roomid":"657667923","peoplesex":"无限制","pfID":"2","attention":"1","if_pay":"2","captain":"7","attention_captain":"0","capttain_name":"恶魔","capttain_pic":"http://47.92.136.19/uploads/header/2018/08/15/4360ba24a29f901291b95aec4c855e75153431703614.jpg","level":"0","if_sign":"0","title":"泰山30日游","marry":"是","age":"1-2","pfexplain":"","look":"76","pffavorite":"0","begin_time":"2018-04-08","end_time":"2018-06-05","city":"泰安","price":"1.00","woman":"0","man":"0","person_num":"2","have_num":"1","show_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","text_info":"泰山真好","info_list":[{"title":"泰山游","content":"泰山真好玩","pfpID":"2","image_list":[{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]}],"user_list":[{"userID":"38","username":"我们旅游吧","userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","num":"1"}],"comment_list":[{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","userID":"38","pctitle":"泰山真好玩","username":"我们旅游吧"}]}
     */


//    /**
//     * code : 200
//     * message : 操作成功!
//     * obj : {"real_name":"1","user_tel":"15244615473","truename":"恶魔","wy_accid":"yy15244615473","roomid":"657667923","peoplesex":"无限制","pfID":"2","attention":"1","if_pay":"2","captain":"7","attention_captain":"0","capttain_name":"恶魔","capttain_pic":"http://47.92.136.19/uploads/header/2018/08/15/4360ba24a29f901291b95aec4c855e75153431703614.jpg","level":"0","if_sign":"0","title":"泰山30日游","marry":"是","age":"1-2","pfexplain":"","look":"76","pffavorite":"0","begin_time":"2018-04-08","end_time":"2018-06-05","city":"泰安","price":"1.00","woman":"0","man":"0","person_num":"2","have_num":"1","show_pic":"http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg","text_info":"泰山真好","info_list":[{"title":"泰山游","content":"泰山真好玩","pfpID":"2","image_list":[{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]}],"user_list":[{"userID":"38","username":"我们旅游吧","userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","num":"1"}],"comment_list":[{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","userID":"38","pctitle":"泰山真好玩","username":"我们旅游吧"}]}
//     */
//
//    private int code;
//    private String message;
//    private ObjBean obj;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public ObjBean getObj() {
//        return obj;
//    }
//
//    public void setObj(ObjBean obj) {
//        this.obj = obj;
//    }
//
//    public static class ObjBean {
//        /**
//         * real_name : 1
//         * user_tel : 15244615473
//         * truename : 恶魔
//         * wy_accid : yy15244615473
//         * roomid : 657667923
//         * peoplesex : 无限制
//         * pfID : 2
//         * attention : 1
//         * if_pay : 2
//         * captain : 7
//         * attention_captain : 0
//         * capttain_name : 恶魔
//         * capttain_pic : http://47.92.136.19/uploads/header/2018/08/15/4360ba24a29f901291b95aec4c855e75153431703614.jpg
//         * level : 0
//         * if_sign : 0
//         * title : 泰山30日游
//         * marry : 是
//         * age : 1-2
//         * pfexplain :
//         * look : 76
//         * pffavorite : 0
//         * begin_time : 2018-04-08
//         * end_time : 2018-06-05
//         * city : 泰安
//         * price : 1.00
//         * woman : 0
//         * man : 0
//         * person_num : 2
//         * have_num : 1
//         * show_pic : http://47.92.136.19/uploads/xingcheng/20180816/20180816/3b70de05b4b083956ef12555ec24d1cc.jpeg
//         * text_info : 泰山真好
//         * info_list : [{"title":"泰山游","content":"泰山真好玩","pfpID":"2","image_list":[{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]}]
//         * user_list : [{"userID":"38","username":"我们旅游吧","userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","num":"1"}]
//         * comment_list : [{"userpic":"http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg","userID":"38","pctitle":"泰山真好玩","username":"我们旅游吧"}]
//         */
//
//        private String real_name;
//        private String user_tel;
//        private String truename;
//        private String wy_accid;
//        private String roomid;
//        private String peoplesex;
//        private String pfID;
//        private String attention;
//        private String if_pay;
//        private String captain;
//        private String attention_captain;
//        private String capttain_name;
//        private String capttain_pic;
//        private String level;
//        private String if_sign;
//        private String title;
//        private String marry;
//        private String age;
//        private String pfexplain;
//        private String look;
//        private String pffavorite;
//        private String begin_time;
//        private String end_time;
//        private String city;
//        private String price;
//        private String woman;
//        private String man;
//        private String person_num;
//        private String have_num;
//        private String show_pic;
//        private String text_info;
//        private List<InfoListBean> info_list;
//        private List<UserListBean> user_list;
//        private List<CommentListBean> comment_list;
//
//        public String getReal_name() {
//            return real_name;
//        }
//
//        public void setReal_name(String real_name) {
//            this.real_name = real_name;
//        }
//
//        public String getUser_tel() {
//            return user_tel;
//        }
//
//        public void setUser_tel(String user_tel) {
//            this.user_tel = user_tel;
//        }
//
//        public String getTruename() {
//            return truename;
//        }
//
//        public void setTruename(String truename) {
//            this.truename = truename;
//        }
//
//        public String getWy_accid() {
//            return wy_accid;
//        }
//
//        public void setWy_accid(String wy_accid) {
//            this.wy_accid = wy_accid;
//        }
//
//        public String getRoomid() {
//            return roomid;
//        }
//
//        public void setRoomid(String roomid) {
//            this.roomid = roomid;
//        }
//
//        public String getPeoplesex() {
//            return peoplesex;
//        }
//
//        public void setPeoplesex(String peoplesex) {
//            this.peoplesex = peoplesex;
//        }
//
//        public String getPfID() {
//            return pfID;
//        }
//
//        public void setPfID(String pfID) {
//            this.pfID = pfID;
//        }
//
//        public String getAttention() {
//            return attention;
//        }
//
//        public void setAttention(String attention) {
//            this.attention = attention;
//        }
//
//        public String getIf_pay() {
//            return if_pay;
//        }
//
//        public void setIf_pay(String if_pay) {
//            this.if_pay = if_pay;
//        }
//
//        public String getCaptain() {
//            return captain;
//        }
//
//        public void setCaptain(String captain) {
//            this.captain = captain;
//        }
//
//        public String getAttention_captain() {
//            return attention_captain;
//        }
//
//        public void setAttention_captain(String attention_captain) {
//            this.attention_captain = attention_captain;
//        }
//
//        public String getCapttain_name() {
//            return capttain_name;
//        }
//
//        public void setCapttain_name(String capttain_name) {
//            this.capttain_name = capttain_name;
//        }
//
//        public String getCapttain_pic() {
//            return capttain_pic;
//        }
//
//        public void setCapttain_pic(String capttain_pic) {
//            this.capttain_pic = capttain_pic;
//        }
//
//        public String getLevel() {
//            return level;
//        }
//
//        public void setLevel(String level) {
//            this.level = level;
//        }
//
//        public String getIf_sign() {
//            return if_sign;
//        }
//
//        public void setIf_sign(String if_sign) {
//            this.if_sign = if_sign;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getMarry() {
//            return marry;
//        }
//
//        public void setMarry(String marry) {
//            this.marry = marry;
//        }
//
//        public String getAge() {
//            return age;
//        }
//
//        public void setAge(String age) {
//            this.age = age;
//        }
//
//        public String getPfexplain() {
//            return pfexplain;
//        }
//
//        public void setPfexplain(String pfexplain) {
//            this.pfexplain = pfexplain;
//        }
//
//        public String getLook() {
//            return look;
//        }
//
//        public void setLook(String look) {
//            this.look = look;
//        }
//
//        public String getPffavorite() {
//            return pffavorite;
//        }
//
//        public void setPffavorite(String pffavorite) {
//            this.pffavorite = pffavorite;
//        }
//
//        public String getBegin_time() {
//            return begin_time;
//        }
//
//        public void setBegin_time(String begin_time) {
//            this.begin_time = begin_time;
//        }
//
//        public String getEnd_time() {
//            return end_time;
//        }
//
//        public void setEnd_time(String end_time) {
//            this.end_time = end_time;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getPrice() {
//            return price;
//        }
//
//        public void setPrice(String price) {
//            this.price = price;
//        }
//
//        public String getWoman() {
//            return woman;
//        }
//
//        public void setWoman(String woman) {
//            this.woman = woman;
//        }
//
//        public String getMan() {
//            return man;
//        }
//
//        public void setMan(String man) {
//            this.man = man;
//        }
//
//        public String getPerson_num() {
//            return person_num;
//        }
//
//        public void setPerson_num(String person_num) {
//            this.person_num = person_num;
//        }
//
//        public String getHave_num() {
//            return have_num;
//        }
//
//        public void setHave_num(String have_num) {
//            this.have_num = have_num;
//        }
//
//        public String getShow_pic() {
//            return show_pic;
//        }
//
//        public void setShow_pic(String show_pic) {
//            this.show_pic = show_pic;
//        }
//
//        public String getText_info() {
//            return text_info;
//        }
//
//        public void setText_info(String text_info) {
//            this.text_info = text_info;
//        }
//
//        public List<InfoListBean> getInfo_list() {
//            return info_list;
//        }
//
//        public void setInfo_list(List<InfoListBean> info_list) {
//            this.info_list = info_list;
//        }
//
//        public List<UserListBean> getUser_list() {
//            return user_list;
//        }
//
//        public void setUser_list(List<UserListBean> user_list) {
//            this.user_list = user_list;
//        }
//
//        public List<CommentListBean> getComment_list() {
//            return comment_list;
//        }
//
//        public void setComment_list(List<CommentListBean> comment_list) {
//            this.comment_list = comment_list;
//        }
//
//        public static class InfoListBean {
//            /**
//             * title : 泰山游
//             * content : 泰山真好玩
//             * pfpID : 2
//             * image_list : [{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg","text_info":""},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/0cc9cf7625b85a5c172a96ab58d5ba5e3234.jpeg","text_info":"泰山的饭真好吃"},{"pic":"http://47.92.136.19/uploads/xingcheng/20180816/9f078da2444b05b5bea1fdd1fa96d2871430.jpeg","text_info":""}]
//             */
//
//            private String title;
//            private String content;
//            private String pfpID;
//            private List<ImageListBean> image_list;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public String getPfpID() {
//                return pfpID;
//            }
//
//            public void setPfpID(String pfpID) {
//                this.pfpID = pfpID;
//            }
//
//            public List<ImageListBean> getImage_list() {
//                return image_list;
//            }
//
//            public void setImage_list(List<ImageListBean> image_list) {
//                this.image_list = image_list;
//            }
//
//            public static class ImageListBean {
//                /**
//                 * pic : http://47.92.136.19/uploads/xingcheng/20180816/ce8884bd149199916250f6ce792359b79741.jpeg
//                 * text_info :
//                 */
//
//                private String look_pic_url;
//                private String pic;
//                private String text_info;
//
//                public String getLook_pic_url() {
//                    return look_pic_url;
//                }
//
//                public void setLook_pic_url(String look_pic_url) {
//                    this.look_pic_url = look_pic_url;
//                }
//
//                public String getPic() {
//                    return pic;
//                }
//
//                public void setPic(String pic) {
//                    this.pic = pic;
//                }
//
//                public String getText_info() {
//                    return text_info;
//                }
//
//                public void setText_info(String text_info) {
//                    this.text_info = text_info;
//                }
//            }
//        }
//
//        public static class UserListBean {
//            /**
//             * userID : 38
//             * username : 我们旅游吧
//             * userpic : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
//             * num : 1
//             */
//
//            private String userID;
//            private String username;
//            private String userpic;
//            private String num;
//
//            public String getUserID() {
//                return userID;
//            }
//
//            public void setUserID(String userID) {
//                this.userID = userID;
//            }
//
//            public String getUsername() {
//                return username;
//            }
//
//            public void setUsername(String username) {
//                this.username = username;
//            }
//
//            public String getUserpic() {
//                return userpic;
//            }
//
//            public void setUserpic(String userpic) {
//                this.userpic = userpic;
//            }
//
//            public String getNum() {
//                return num;
//            }
//
//            public void setNum(String num) {
//                this.num = num;
//            }
//        }
//
//        public static class CommentListBean {
//            /**
//             * userpic : http://47.92.136.19/uploads/header/2018/08/16/e833291d6bf838e3be831414c4346960153439944612.jpg
//             * userID : 38
//             * pctitle : 泰山真好玩
//             * username : 我们旅游吧
//             */
//
//            private String userpic;
//            private String userID;
//            private String pctitle;
//            private String username;
//
//            public String getUserpic() {
//                return userpic;
//            }
//
//            public void setUserpic(String userpic) {
//                this.userpic = userpic;
//            }
//
//            public String getUserID() {
//                return userID;
//            }
//
//            public void setUserID(String userID) {
//                this.userID = userID;
//            }
//
//            public String getPctitle() {
//                return pctitle;
//            }
//
//            public void setPctitle(String pctitle) {
//                this.pctitle = pctitle;
//            }
//
//            public String getUsername() {
//                return username;
//            }
//
//            public void setUsername(String username) {
//                this.username = username;
//            }
//        }
//    }

}
