package com.yiwo.friendscometogether.model;

import java.util.List;

import me.zhouzhuo.zzletterssidebar.anotation.Letter;
import me.zhouzhuo.zzletterssidebar.entity.SortModel;

/**
 * Created by Administrator on 2018/9/4 0004.
 */

public class MyFriendModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"username":"一样一样","id":"151","uid":"38","userpic":"http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg","wy_accid":"yy18346038613"}]
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

    public static class ObjBean extends SortModel {
        /**
         * username : 一样一样
         * id : 151
         * uid : 38
         * userpic : http://47.92.136.19/uploads/header/2018/09/07/0f66d4f7a98dc1bf8a0cb3a80f846a3815363073806.jpg
         * wy_accid : yy18346038613
         */

        @Letter(isSortField = true)
        private String username;
        private String id;
        private String uid;
        private String userpic;
        private String wy_accid;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getWy_accid() {
            return wy_accid;
        }

        public void setWy_accid(String wy_accid) {
            this.wy_accid = wy_accid;
        }
    }
}
