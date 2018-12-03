package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/9/13.
 */

public class GetFriendActiveListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"pfID":"43","pftitle":"泰国深度5日游"},{"pfID":"47","pftitle":"京东风云"},{"pfID":"51","pftitle":"丹东"},{"pfID":"51","pftitle":"丹东"},{"pfID":"51","pftitle":"丹东"},{"pfID":"51","pftitle":"丹东"},{"pfID":"53","pftitle":"2018.9.7"},{"pfID":"53","pftitle":"2018.9.7"},{"pfID":"2","pftitle":"泰山30日游"},{"pfID":"3","pftitle":"三亚一月游"},{"pfID":"5","pftitle":"黄山三日游"},{"pfID":"8","pftitle":"哈尔滨十日游"},{"pfID":"14","pftitle":"海口十日游"},{"pfID":"18","pftitle":"北海三日游"},{"pfID":"19","pftitle":"黄海三日游"},{"pfID":"20","pftitle":"测试1"},{"pfID":"21","pftitle":"测试图片2"},{"pfID":"22","pftitle":"测试密码"},{"pfID":"31","pftitle":"测试密码"},{"pfID":"40","pftitle":"活动"},{"pfID":"41","pftitle":"一个活动"},{"pfID":"42","pftitle":"10.1厦门7日游，有没有人一起参加组团去！"},{"pfID":"44","pftitle":"10.1Beijing"},{"pfID":"45","pftitle":"男同志单身来"},{"pfID":"52","pftitle":"地膜路"},{"pfID":"54","pftitle":"啦啦"},{"pfID":"59","pftitle":"测试"},{"pfID":"60","pftitle":"测试草稿"},{"pfID":"61","pftitle":"测试"},{"pfID":"62","pftitle":"阿菊"}]
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
         * pfID : 43
         * pftitle : 泰国深度5日游
         */

        private String pfID;
        private String pftitle;

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }
    }
}
