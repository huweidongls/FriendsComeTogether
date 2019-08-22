package com.yiwo.friendscometogether.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class SearchListModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"title":"测试",
     *            "id":"94",
     *            "type":"2",
     *            "userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/08/08/54566df64b18017d9c078b6feddea69615652434265.png",
     *            "vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/64524e34-3f56-4e1d-80ea-425271b5c187.mp4",
     *            "vimg":"http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/886e163d-1daa-401a-b03b-20baa8e6280b.jpg"},
     *            {"title":"测试闪退","id":"90","type":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/08/08/54566df64b18017d9c078b6feddea69615652434265.png","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/af965a42-932b-4fd1-b4b9-18d1ce24269e.mp4","vimg":"http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/af965a42-932b-4fd1-b4b9-18d1ce24269e_1_0_0.jpg"},{"title":"测试测试","id":"88","type":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/07/33acc778077a0fcf6482d1c8d00a60a615572233957.png","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/052dcde9-9552-49b0-8ff2-949a269a83c1.mp4","vimg":"http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/d7f290bc-6cca-4252-b427-53902b02b4c6.jpg"},{"title":"测试声音","id":"39","type":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/05/07/33acc778077a0fcf6482d1c8d00a60a615572233957.png","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/911f348f-fc46-434c-9ebf-bdd32cdf3cce.mp4","vimg":"http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/305897a2-602e-4c41-b7f1-48c710460371.jpg"},{"title":"测试结果","id":"8","type":"2","userpic":"http://www.91yiwo.com/ylyy/uploads/header/2019/08/08/54566df64b18017d9c078b6feddea69615652434265.png","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/a9383135-7d05-45c7-a5fa-59b3e305c83f.mp4","vimg":"http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/b4e23112-ceec-4b71-833e-59a3d9deab72.jpg"}]
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

    public static class ObjBean implements Serializable {
        /**
         * title : 测试
         * id : 94
         * type : 2
         * userpic : http://www.91yiwo.com/ylyy/uploads/header/2019/08/08/54566df64b18017d9c078b6feddea69615652434265.png
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/64524e34-3f56-4e1d-80ea-425271b5c187.mp4
         * vimg : http://www.91yiwo.com/ylyy/http://vodsmnjjkoj.nosdn.127.net/886e163d-1daa-401a-b03b-20baa8e6280b.jpg
         */

        private String title;
        private String id;
        private String type;
        private String userpic;
        private String vurl;
        private String vimg;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getVimg() {
            return vimg;
        }

        public void setVimg(String vimg) {
            this.vimg = vimg;
        }
    }
}
