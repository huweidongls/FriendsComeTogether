package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model;

/**
 * Created by ljc on 2019/11/28.
 */

public class ZhiBoModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : {"cid":"bfbf54f2bdaa4a01b35106104db69a2d","creator":"tongban15754633415","hlsPullUrl":"http://pullhlsc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d/playlist.m3u8","httpPullUrl":"http://flvc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d.flv?netease=flvc2dc64c9.live.126.net","pushUrl":"rtmp://pc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d?wsSecret=ee4d8eb0d58a4c825f9e153c5840d1ea&wsTime=1574907295","roomId":"138982456","rtmpPullUrl":"rtmp://vc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d"}
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
         * cid : bfbf54f2bdaa4a01b35106104db69a2d
         * creator : tongban15754633415
         * hlsPullUrl : http://pullhlsc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d/playlist.m3u8
         * httpPullUrl : http://flvc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d.flv?netease=flvc2dc64c9.live.126.net
         * pushUrl : rtmp://pc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d?wsSecret=ee4d8eb0d58a4c825f9e153c5840d1ea&wsTime=1574907295
         * roomId : 138982456
         * rtmpPullUrl : rtmp://vc2dc64c9.live.126.net/live/bfbf54f2bdaa4a01b35106104db69a2d
         */

        private String cid;
        private String creator;
        private String hlsPullUrl;
        private String httpPullUrl;
        private String pushUrl;
        private String roomId;
        private String rtmpPullUrl;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getHlsPullUrl() {
            return hlsPullUrl;
        }

        public void setHlsPullUrl(String hlsPullUrl) {
            this.hlsPullUrl = hlsPullUrl;
        }

        public String getHttpPullUrl() {
            return httpPullUrl;
        }

        public void setHttpPullUrl(String httpPullUrl) {
            this.httpPullUrl = httpPullUrl;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRtmpPullUrl() {
            return rtmpPullUrl;
        }

        public void setRtmpPullUrl(String rtmpPullUrl) {
            this.rtmpPullUrl = rtmpPullUrl;
        }
    }
}
