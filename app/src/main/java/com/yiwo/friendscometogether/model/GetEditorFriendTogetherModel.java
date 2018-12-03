package com.yiwo.friendscometogether.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */

public class GetEditorFriendTogetherModel{

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"pfpic":"http://localhost/www/uploads/activity/20180719/20180719/11e73ef1b5e4809649c225afe7eb2501.jpg","pftitle":"泰山七日游","pfcontent":"泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游","pfgotime":"2018-07-27","pfendtime":"2018-09-08","city":"230400","city_name":"山东省","pfspendtype":"3","pfspend":"120.00","pfspendexplain":"吃住玩","min_num":"12","max_num":"123","pfpeoplesex":"0","pfagebegin":"12","pfageend":"123","pfmarry":"0","pfexplain":"爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山","pfwarning":"摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒","pfpwd":"123123","title_list":[{"id":"1","pfptitle":"第一天：爬山","pfpcontent":"太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。","img_list":[{"pfpID":"280","pfpcontent":"云海日出","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354994.jpg"},{"pfpID":"281","pfpcontent":"日出山谷","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354204.jpg"},{"pfpID":"282","pfpcontent":"清晨山顶","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354439.jpg"}]}]}
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

    public static class ObjBean implements Serializable {
        /**
         * pfpic : http://localhost/www/uploads/activity/20180719/20180719/11e73ef1b5e4809649c225afe7eb2501.jpg
         * pftitle : 泰山七日游
         * pfcontent : 泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游泰山七日游
         * pfgotime : 2018-07-27
         * pfendtime : 2018-09-08
         * city : 230400
         * city_name : 山东省
         * pfspendtype : 3
         * pfspend : 120.00
         * pfspendexplain : 吃住玩
         * min_num : 12
         * max_num : 123
         * pfpeoplesex : 0
         * pfagebegin : 12
         * pfageend : 123
         * pfmarry : 0
         * pfexplain : 爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山爬山
         * pfwarning : 摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒摔倒
         * pfpwd : 123123
         * title_list : [{"id":"1","pfptitle":"第一天：爬山","pfpcontent":"太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。","img_list":[{"pfpID":"280","pfpcontent":"云海日出","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354994.jpg"},{"pfpID":"281","pfpcontent":"日出山谷","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354204.jpg"},{"pfpID":"282","pfpcontent":"清晨山顶","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354439.jpg"}]}]
         */

        private String pffavorite;
        private String look_num;
        private String country;
        private String user_join;
        private String pfpic;
        private String pftitle;
        private String pfcontent;
        private String pfgotime;
        private String pfendtime;
        private String city;
        private String city_name;
        private String pfspendtype;
        private String pfspend;
        private String pfspendexplain;
        private String min_num;
        private String max_num;
        private String pfpeoplesex;
        private String pfagebegin;
        private String pfageend;
        private String pfmarry;
        private String pfexplain;
        private String pfwarning;
        private String pfpwd;
        private List<TitleListBean> title_list;

        public String getPffavorite() {
            return pffavorite;
        }

        public void setPffavorite(String pffavorite) {
            this.pffavorite = pffavorite;
        }

        public String getLook_num() {
            return look_num;
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getUser_join() {
            return user_join;
        }

        public void setUser_join(String user_join) {
            this.user_join = user_join;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfcontent() {
            return pfcontent;
        }

        public void setPfcontent(String pfcontent) {
            this.pfcontent = pfcontent;
        }

        public String getPfgotime() {
            return pfgotime;
        }

        public void setPfgotime(String pfgotime) {
            this.pfgotime = pfgotime;
        }

        public String getPfendtime() {
            return pfendtime;
        }

        public void setPfendtime(String pfendtime) {
            this.pfendtime = pfendtime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getPfspendtype() {
            return pfspendtype;
        }

        public void setPfspendtype(String pfspendtype) {
            this.pfspendtype = pfspendtype;
        }

        public String getPfspend() {
            return pfspend;
        }

        public void setPfspend(String pfspend) {
            this.pfspend = pfspend;
        }

        public String getPfspendexplain() {
            return pfspendexplain;
        }

        public void setPfspendexplain(String pfspendexplain) {
            this.pfspendexplain = pfspendexplain;
        }

        public String getMin_num() {
            return min_num;
        }

        public void setMin_num(String min_num) {
            this.min_num = min_num;
        }

        public String getMax_num() {
            return max_num;
        }

        public void setMax_num(String max_num) {
            this.max_num = max_num;
        }

        public String getPfpeoplesex() {
            return pfpeoplesex;
        }

        public void setPfpeoplesex(String pfpeoplesex) {
            this.pfpeoplesex = pfpeoplesex;
        }

        public String getPfagebegin() {
            return pfagebegin;
        }

        public void setPfagebegin(String pfagebegin) {
            this.pfagebegin = pfagebegin;
        }

        public String getPfageend() {
            return pfageend;
        }

        public void setPfageend(String pfageend) {
            this.pfageend = pfageend;
        }

        public String getPfmarry() {
            return pfmarry;
        }

        public void setPfmarry(String pfmarry) {
            this.pfmarry = pfmarry;
        }

        public String getPfexplain() {
            return pfexplain;
        }

        public void setPfexplain(String pfexplain) {
            this.pfexplain = pfexplain;
        }

        public String getPfwarning() {
            return pfwarning;
        }

        public void setPfwarning(String pfwarning) {
            this.pfwarning = pfwarning;
        }

        public String getPfpwd() {
            return pfpwd;
        }

        public void setPfpwd(String pfpwd) {
            this.pfpwd = pfpwd;
        }

        public List<TitleListBean> getTitle_list() {
            return title_list;
        }

        public void setTitle_list(List<TitleListBean> title_list) {
            this.title_list = title_list;
        }

        public static class TitleListBean implements Serializable {
            /**
             * id : 1
             * pfptitle : 第一天：爬山
             * pfpcontent : 太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。太阳出来爬山破， 爬爬爬。
             * img_list : [{"pfpID":"280","pfpcontent":"云海日出","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354994.jpg"},{"pfpID":"281","pfpcontent":"日出山谷","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354204.jpg"},{"pfpID":"282","pfpcontent":"清晨山顶","pfpurl":"http://localhost/www/uploads/xingcheng/20180723/1532314354439.jpg"}]
             */

            private String id;
            private String pfptitle;
            private String pfpcontent;
            private List<ImgListBean> img_list;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPfptitle() {
                return pfptitle;
            }

            public void setPfptitle(String pfptitle) {
                this.pfptitle = pfptitle;
            }

            public String getPfpcontent() {
                return pfpcontent;
            }

            public void setPfpcontent(String pfpcontent) {
                this.pfpcontent = pfpcontent;
            }

            public List<ImgListBean> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<ImgListBean> img_list) {
                this.img_list = img_list;
            }

            public static class ImgListBean implements Serializable {
                /**
                 * pfpID : 280
                 * pfpcontent : 云海日出
                 * pfpurl : http://localhost/www/uploads/xingcheng/20180723/1532314354994.jpg
                 */

                private String pfpID;
                private String pfpcontent;
                private String pfpurl;

                public String getPfpID() {
                    return pfpID;
                }

                public void setPfpID(String pfpID) {
                    this.pfpID = pfpID;
                }

                public String getPfpcontent() {
                    return pfpcontent;
                }

                public void setPfpcontent(String pfpcontent) {
                    this.pfpcontent = pfpcontent;
                }

                public String getPfpurl() {
                    return pfpurl;
                }

                public void setPfpurl(String pfpurl) {
                    this.pfpurl = pfpurl;
                }
            }
        }
    }
}
