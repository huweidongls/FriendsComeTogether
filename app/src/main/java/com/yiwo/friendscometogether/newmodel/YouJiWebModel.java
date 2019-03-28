package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/27.
 */

public class YouJiWebModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"add":"1","collect":"1","title":[{"ffID":"299","fftitle":"1231231"}]}
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
         * add : 1
         * collect : 1
         * title : [{"ffID":"299","fftitle":"1231231"}]
         */

        private String add;
        private String collect;
        private List<TitleBean> title;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public List<TitleBean> getTitle() {
            return title;
        }

        public void setTitle(List<TitleBean> title) {
            this.title = title;
        }

        public static class TitleBean {
            /**
             * ffID : 299
             * fftitle : 1231231
             */

            private String ffID;
            private String fftitle;

            public String getFfID() {
                return ffID;
            }

            public void setFfID(String ffID) {
                this.ffID = ffID;
            }

            public String getFftitle() {
                return fftitle;
            }

            public void setFftitle(String fftitle) {
                this.fftitle = fftitle;
            }
        }
    }
}
