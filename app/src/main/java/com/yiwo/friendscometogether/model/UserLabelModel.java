package com.yiwo.friendscometogether.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */

public class UserLabelModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"lID":"1","lname":"旅游","luse":"6"},{"lID":"2","lname":"美食","luse":"1"},{"lID":"3","lname":"美酒","luse":"2"},{"lID":"4","lname":"美女","luse":"3"},{"lID":"5","lname":"烧烤","luse":"12"},{"lID":"6","lname":"蹦极","luse":"1"},{"lID":"7","lname":"漂流","luse":"12"},{"lID":"8","lname":"游轮远航","luse":"112"},{"lID":"9","lname":"潜水","luse":"21"},{"lID":"15","lname":"摄影","luse":"312"},{"lID":"16","lname":"篝火","luse":"221"},{"lID":"17","lname":"晒太阳","luse":"33"},{"lID":"18","lname":"吹海风","luse":"22"}]
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

    public static class ObjBean implements IPickerViewData {
        /**
         * lID : 1
         * lname : 旅游
         * luse : 6
         */

        private String lID;
        private String lname;
        private String luse;

        public ObjBean(String lID, String lname, String luse) {
            this.lID = lID;
            this.lname = lname;
            this.luse = luse;
        }

        public String getLID() {
            return lID;
        }

        public void setLID(String lID) {
            this.lID = lID;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getLuse() {
            return luse;
        }

        public void setLuse(String luse) {
            this.luse = luse;
        }

        @Override
        public String getPickerViewText() {
            return this.lname;
        }
    }
}
