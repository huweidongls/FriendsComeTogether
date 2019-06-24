package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/28.
 */

public class YouJuWebModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"collect":"1","title":[{"ffID":"105","fftitle":"吃烤肉，来齐齐哈尔"},{"ffID":"109","fftitle":"额威风威风为"}],"share_pic":"http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg","share_url":"www.baidu.com","share_info":"齐齐哈尔烤肉免费吃了","phase":[{"phase_num":"1","phase_price":"0.00"},{"phase_num":"2","phase_price":"0.00"}],"info":{"wy_accid":"yykakaluote","pfspendtype":"1","pftitle":"齐齐哈尔烤肉免费吃了","pfID":"156","truename":"刘继昌","peoplesex":"无限制","age":"12-30","pfpic":"http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg","marry":"否","address":"齐齐哈尔","tel":"15754633415","others":"吃不了兜着走"}}
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
         * collect : 1
         * title : [{"ffID":"105","fftitle":"吃烤肉，来齐齐哈尔"},{"ffID":"109","fftitle":"额威风威风为"}]
         * share_pic : http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg
         * share_url : www.baidu.com
         * share_info : 齐齐哈尔烤肉免费吃了
         * phase : [{"phase_num":"1","phase_price":"0.00"},{"phase_num":"2","phase_price":"0.00"}]
         * info : {"wy_accid":"yykakaluote","pfspendtype":"1","pftitle":"齐齐哈尔烤肉免费吃了","pfID":"156","truename":"刘继昌","peoplesex":"无限制","age":"12-30","pfpic":"http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg","marry":"否","address":"齐齐哈尔","tel":"15754633415","others":"吃不了兜着走"}
         */

        private String collect;
        private String share_pic;
        private String share_url;
        private String share_info;
        private InfoBean info;
        private List<TitleBean> title;
        private List<PhaseBean> phase;

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public String getShare_pic() {
            return share_pic;
        }

        public void setShare_pic(String share_pic) {
            this.share_pic = share_pic;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare_info() {
            return share_info;
        }

        public void setShare_info(String share_info) {
            this.share_info = share_info;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<TitleBean> getTitle() {
            return title;
        }

        public void setTitle(List<TitleBean> title) {
            this.title = title;
        }

        public List<PhaseBean> getPhase() {
            return phase;
        }

        public void setPhase(List<PhaseBean> phase) {
            this.phase = phase;
        }

        public static class InfoBean {
            /**
             * wy_accid : yykakaluote
             * pfspendtype : 1
             * pftitle : 齐齐哈尔烤肉免费吃了
             * pfID : 156
             * truename : 刘继昌
             * peoplesex : 无限制
             * age : 12-30
             * pfpic : http://39.104.102.152/uploads/xingcheng/20190319/5db6902c84c751ec8dd12e2982cd8b13.jpg
             * marry : 否
             * address : 齐齐哈尔
             * tel : 15754633415
             * others : 吃不了兜着走
             * pfcontent:详情简介
             */

            private String wy_accid;
            private String pfspendtype;
            private String pftitle;
            private String pfID;
            private String truename;
            private String peoplesex;
            private String age;
            private String pfpic;
            private String marry;
            private String address;
            private String tel;
            private String others;
            private String pfcontent;
            public String getWy_accid() {
                return wy_accid;
            }

            public void setWy_accid(String wy_accid) {
                this.wy_accid = wy_accid;
            }

            public String getPfspendtype() {
                return pfspendtype;
            }

            public void setPfspendtype(String pfspendtype) {
                this.pfspendtype = pfspendtype;
            }

            public String getPftitle() {
                return pftitle;
            }

            public void setPftitle(String pftitle) {
                this.pftitle = pftitle;
            }

            public String getPfID() {
                return pfID;
            }

            public void setPfID(String pfID) {
                this.pfID = pfID;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public String getPeoplesex() {
                return peoplesex;
            }

            public void setPeoplesex(String peoplesex) {
                this.peoplesex = peoplesex;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
            }

            public String getMarry() {
                return marry;
            }

            public void setMarry(String marry) {
                this.marry = marry;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getOthers() {
                return others;
            }

            public void setOthers(String others) {
                this.others = others;
            }

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }
        }

        public static class TitleBean {
            /**
             * ffID : 105
             * fftitle : 吃烤肉，来齐齐哈尔
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

        public static class PhaseBean {
            /**
             * phase_num : 1
             * phase_price : 0.00
             */

            private String phase_num;
            private String phase_price;

            public String getPhase_num() {
                return phase_num;
            }

            public void setPhase_num(String phase_num) {
                this.phase_num = phase_num;
            }

            public String getPhase_price() {
                return phase_price;
            }

            public void setPhase_price(String phase_price) {
                this.phase_price = phase_price;
            }
        }
    }
}
