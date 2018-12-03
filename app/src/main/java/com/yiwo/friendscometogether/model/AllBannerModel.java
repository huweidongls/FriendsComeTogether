package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class AllBannerModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"type":"3","title":"这是标题","leftid":"124","pic":"http://47.92.136.19/uploads/banner/20180821/20180821/3d2fdb9a007272eb34a33255d262aa36.jpg","first_type":"1"},{"type":"3","title":"这是标题","leftid":"127","pic":"http://47.92.136.19/uploads/banner/20180821/20180821/d7160332e62e67af87151dfcf0f9bbfc.jpg","first_type":"1"}]
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
         * type : 3
         * title : 这是标题
         * leftid : 124
         * pic : http://47.92.136.19/uploads/banner/20180821/20180821/3d2fdb9a007272eb34a33255d262aa36.jpg
         * first_type : 1
         */

        private String type;
        private String title;
        private String leftid;
        private String pic;
        private String first_type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLeftid() {
            return leftid;
        }

        public void setLeftid(String leftid) {
            this.leftid = leftid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getFirst_type() {
            return first_type;
        }

        public void setFirst_type(String first_type) {
            this.first_type = first_type;
        }
    }
}
