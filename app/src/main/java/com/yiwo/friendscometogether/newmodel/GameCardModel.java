package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/9/25.
 */

public class GameCardModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : {"title":"上海","group_No":"1","captain":{"user_ID":"130","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/09/24/982b3fd52de418d097bae9449035273d15692913003.png","username":"随机测试","group_No":"1"},"groupInfo":[{"user_ID":"4","game_num":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生"},{"user_ID":"130","game_num":"1","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/09/24/982b3fd52de418d097bae9449035273d15692913003.png","username":"随机测试"}]}
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
         * title : 上海
         * group_No : 1
         * captain : {"user_ID":"130","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/09/24/982b3fd52de418d097bae9449035273d15692913003.png","username":"随机测试","group_No":"1"}
         * groupInfo : [{"user_ID":"4","game_num":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png","username":"花生"},{"user_ID":"130","game_num":"1","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/09/24/982b3fd52de418d097bae9449035273d15692913003.png","username":"随机测试"}]
         */

        private String title;
        private String group_No;
        private String myNo;
        private CaptainBean captain;
        private List<GroupInfoBean> groupInfo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGroup_No() {
            return group_No;
        }

        public void setGroup_No(String group_No) {
            this.group_No = group_No;
        }

        public CaptainBean getCaptain() {
            return captain;
        }

        public void setCaptain(CaptainBean captain) {
            this.captain = captain;
        }

        public List<GroupInfoBean> getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(List<GroupInfoBean> groupInfo) {
            this.groupInfo = groupInfo;
        }

        public String getMyNo() {
            return myNo;
        }

        public void setMyNo(String myNo) {
            this.myNo = myNo;
        }

        public static class CaptainBean {
            /**
             * user_ID : 130
             * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/09/24/982b3fd52de418d097bae9449035273d15692913003.png
             * username : 随机测试
             * group_No : 1
             */

            private String user_ID;
            private String userpic;
            private String username;
            private String group_No;

            public String getUser_ID() {
                return user_ID;
            }

            public void setUser_ID(String user_ID) {
                this.user_ID = user_ID;
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

            public String getGroup_No() {
                return group_No;
            }

            public void setGroup_No(String group_No) {
                this.group_No = group_No;
            }
        }

        public static class GroupInfoBean {
            /**
             * user_ID : 4
             * game_num : 2
             * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/05/17/31b54fac78f7c1f4e91d648dcc24b68f15580576488.png
             * username : 花生
             */

            private String user_ID;
            private String game_num;
            private String userpic;
            private String username;

            public String getUser_ID() {
                return user_ID;
            }

            public void setUser_ID(String user_ID) {
                this.user_ID = user_ID;
            }

            public String getGame_num() {
                return game_num;
            }

            public void setGame_num(String game_num) {
                this.game_num = game_num;
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
        }
    }
}
