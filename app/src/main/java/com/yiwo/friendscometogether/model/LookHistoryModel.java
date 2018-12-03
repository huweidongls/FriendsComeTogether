package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/1.
 */

public class LookHistoryModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"id":"1171","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 14:24:22","type":"1","look_id":"90"},{"id":"1172","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:24:28","type":"1","look_id":"85"},{"id":"1174","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 14:24:52","type":"1","look_id":"90"},{"id":"1175","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:24:57","type":"1","look_id":"85"},{"id":"1182","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:25:37","type":"1","look_id":"85"},{"id":"1188","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:28:39","type":"1","look_id":"85"},{"id":"1189","title":"莫日格勒河","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/eb5466368d3bf75aedbea11a21d03942.png","look_time":"2018-08-10 14:29:17","type":"1","look_id":"91"},{"id":"1190","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:29:23","type":"1","look_id":"85"},{"id":"1191","title":"中央大街一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/29a3cec55bfe8e9b776128c5a1f8a5ca.png","look_time":"2018-08-10 14:29:36","type":"1","look_id":"88"},{"id":"1192","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 14:29:38","type":"1","look_id":"90"},{"id":"1193","title":"中央大街一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/29a3cec55bfe8e9b776128c5a1f8a5ca.png","look_time":"2018-08-10 14:30:05","type":"1","look_id":"88"},{"id":"1194","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:30:15","type":"1","look_id":"85"},{"id":"1198","title":"莫日格勒河","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/eb5466368d3bf75aedbea11a21d03942.png","look_time":"2018-08-10 14:33:24","type":"1","look_id":"91"},{"id":"1199","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 14:33:27","type":"1","look_id":"90"},{"id":"1200","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:33:31","type":"1","look_id":"85"},{"id":"1201","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:33:44","type":"1","look_id":"85"},{"id":"1205","title":"哈尔滨雪乡","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/cbf0621f4ed56834f7b728f410a893c7.png","look_time":"2018-08-10 14:37:29","type":"1","look_id":"92"},{"id":"1206","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:37:38","type":"1","look_id":"85"},{"id":"1207","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:38:51","type":"1","look_id":"85"},{"id":"1208","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:39:35","type":"1","look_id":"85"},{"id":"1209","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:43:29","type":"1","look_id":"85"},{"id":"1210","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 14:46:02","type":"1","look_id":"85"},{"id":"1211","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 14:46:10","type":"1","look_id":"90"},{"id":"1212","title":"中央大街一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/29a3cec55bfe8e9b776128c5a1f8a5ca.png","look_time":"2018-08-10 14:46:14","type":"1","look_id":"88"},{"id":"1239","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 15:13:36","type":"1","look_id":"93"},{"id":"1240","title":"哈尔滨雪乡","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/cbf0621f4ed56834f7b728f410a893c7.png","look_time":"2018-08-10 15:13:41","type":"1","look_id":"92"},{"id":"1265","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 15:18:45","type":"1","look_id":"93"},{"id":"1266","title":"哈尔滨雪乡","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/cbf0621f4ed56834f7b728f410a893c7.png","look_time":"2018-08-10 15:18:48","type":"1","look_id":"92"},{"id":"1286","title":"美丽的伊春河风光","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/3497699915a8b48d5fe188665bc1dc34.png","look_time":"2018-08-10 15:21:56","type":"1","look_id":"94"},{"id":"1287","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 15:22:09","type":"1","look_id":"93"},{"id":"1288","title":"哈尔滨雪乡","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/cbf0621f4ed56834f7b728f410a893c7.png","look_time":"2018-08-10 15:22:12","type":"1","look_id":"92"},{"id":"1289","title":"美丽的伊春河风光","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/3497699915a8b48d5fe188665bc1dc34.png","look_time":"2018-08-10 15:23:09","type":"1","look_id":"94"},{"id":"1290","title":"哈尔滨雪乡","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/cbf0621f4ed56834f7b728f410a893c7.png","look_time":"2018-08-10 15:30:29","type":"1","look_id":"92"},{"id":"1291","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 15:30:55","type":"1","look_id":"93"},{"id":"1292","title":"美丽的伊春河风光","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/3497699915a8b48d5fe188665bc1dc34.png","look_time":"2018-08-10 15:31:00","type":"1","look_id":"94"},{"id":"1293","title":"美丽的伊春河风光","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/3497699915a8b48d5fe188665bc1dc34.png","look_time":"2018-08-10 15:31:05","type":"1","look_id":"94"},{"id":"1294","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 15:32:01","type":"1","look_id":"90"},{"id":"1295","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 15:32:04","type":"1","look_id":"93"},{"id":"1296","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 15:46:02","type":"1","look_id":"90"},{"id":"1297","title":"我去泰山玩了","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 15:46:10","type":"1","look_id":"85"},{"id":"1298","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 15:46:13","type":"1","look_id":"90"},{"id":"1299","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 15:46:18","type":"1","look_id":"90"},{"id":"1300","title":"莫日格勒河","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/eb5466368d3bf75aedbea11a21d03942.png","look_time":"2018-08-10 15:46:23","type":"1","look_id":"91"},{"id":"1301","title":"太阳岛一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png","look_time":"2018-08-10 15:46:40","type":"1","look_id":"90"},{"id":"1302","title":"中央大街一日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/29a3cec55bfe8e9b776128c5a1f8a5ca.png","look_time":"2018-08-10 16:00:07","type":"1","look_id":"88"},{"id":"1303","title":"泰山七日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 16:00:11","type":"1","look_id":"85"},{"id":"1307","title":"泰山七日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 16:28:55","type":"1","look_id":"85"},{"id":"1308","title":"泰山七日游","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/a5fba7bdbdef8beb653129c024d56cb8.jpeg","look_time":"2018-08-10 16:32:29","type":"1","look_id":"85"},{"id":"1309","title":"五大连池火山","pic_url":"http://47.92.136.19/uploads/article/20180810/20180810/7060e0722623c2dab803f9f2f2499d92.png","look_time":"2018-08-10 16:45:28","type":"1","look_id":"93"},{"id":"1310","title":"删除测试","pic_url":"http://47.92.136.19/uploads/xingcheng/20180807/20180807/63d44c3ce98387efa730d40e25a17133.jpg","look_time":"2018-08-10 16:46:13","type":"0","look_id":"109"},{"id":"1311","title":"提莫","pic_url":"http://47.92.136.19/uploads/xingcheng/20180806/20180806/61f7d9d9231780ce764b512b7ca57738.jpeg","look_time":"2018-08-10 16:47:05","type":"0","look_id":"106"},{"id":"1312","title":"提莫","pic_url":"http://47.92.136.19/uploads/xingcheng/20180806/20180806/61f7d9d9231780ce764b512b7ca57738.jpeg","look_time":"2018-08-10 16:47:13","type":"0","look_id":"106"}]
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
         * id : 1171
         * title : 太阳岛一日游
         * pic_url : http://47.92.136.19/uploads/article/20180810/20180810/96058625e90a9b4f26004ea1c3a87f04.png
         * look_time : 2018-08-10 14:24:22
         * type : 1
         * look_id : 90
         */

        private String id;
        private String title;
        private String pic_url;
        private String look_time;
        private String type;
        private String look_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getLook_time() {
            return look_time;
        }

        public void setLook_time(String look_time) {
            this.look_time = look_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLook_id() {
            return look_id;
        }

        public void setLook_id(String look_id) {
            this.look_id = look_id;
        }
    }
}
