package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2019/9/18.
 */

public class DuiZhangXuanZeHuoDongModel {
    /**
     * code : 200
     * message : 获取成功
     * obj : [{"phase_num":"第1期","phase_begin_time":"2019-04-12","phase_id":"1","pfpic":"","pfID":"1","pftitle":"二龙山爬山运动会"},{"phase_num":"第2期","phase_begin_time":"2019-04-17","phase_id":"2","pfpic":"","pfID":"1","pftitle":"二龙山爬山运动会"},{"phase_num":"第3期","phase_begin_time":"2019-04-21","phase_id":"3","pfpic":"","pfID":"1","pftitle":"二龙山爬山运动会"},{"phase_num":"第1期","phase_begin_time":"2019-04-25","phase_id":"4","pfpic":"","pfID":"2","pftitle":"二龙山爬山运动会二期升级版"},{"phase_num":"第1期","phase_begin_time":"2019-04-27","phase_id":"7","pfpic":"","pfID":"3","pftitle":"香炉山青春聚会"},{"phase_num":"第2期","phase_begin_time":"2019-04-27","phase_id":"8","pfpic":"","pfID":"3","pftitle":"香炉山青春聚会"},{"phase_num":"第1期","phase_begin_time":"2019-04-14","phase_id":"9","pfpic":"","pfID":"4","pftitle":"香炉山青春聚会"},{"phase_num":"第1期","phase_begin_time":"2019-04-20","phase_id":"10","pfpic":"","pfID":"5","pftitle":"二龙山爬山运动会三期"},{"phase_num":"第1期","phase_begin_time":"2019-04-15","phase_id":"11","pfpic":"","pfID":"6","pftitle":"二龙山爬山运动会二期升级版"},{"phase_num":"第2期","phase_begin_time":"2019-04-17","phase_id":"12","pfpic":"","pfID":"6","pftitle":"二龙山爬山运动会二期升级版"},{"phase_num":"第1期","phase_begin_time":"2020-04-08","phase_id":"13","pfpic":"","pfID":"7","pftitle":"妙途活动"},{"phase_num":"第1期","phase_begin_time":"2019-04-30","phase_id":"14","pfpic":"","pfID":"8","pftitle":"哈尔滨纯玩无自费一日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"15","pfpic":"","pfID":"9","pftitle":"昆明大理丽江版纳半自助八日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-17","phase_id":"16","pfpic":"","pfID":"10","pftitle":"长春+沈阳+大连休闲四日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-18","phase_id":"17","pfpic":"","pfID":"11","pftitle":"【王者荣耀】体验云南精华景点"},{"phase_num":"第1期","phase_begin_time":"2019-05-15","phase_id":"18","pfpic":"","pfID":"12","pftitle":"哈尔滨纯玩无自费一日游 "},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"19","pfpic":"","pfID":"13","pftitle":"【纯玩无自费】哈尔滨一日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"20","pfpic":"","pfID":"14","pftitle":"【王者荣耀】体验云南精华景点\t"},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"21","pfpic":"","pfID":"15","pftitle":"【四个景点】五大连池火山冷泉二日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"22","pfpic":"","pfID":"16","pftitle":"初遇贵州6日"},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"23","pfpic":"","pfID":"17","pftitle":"漠河、北极村、五大连池休闲五日游"},{"phase_num":"第1期","phase_begin_time":"2019-04-28","phase_id":"24","pfpic":"","pfID":"18","pftitle":"山水贵客6日"},{"phase_num":"第1期","phase_begin_time":"2019-04-30","phase_id":"25","pfpic":"","pfID":"19","pftitle":"沈阳千本四日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"26","pfpic":"","pfID":"20","pftitle":"绥芬河海参崴纯轻奢六日游"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"27","pfpic":"","pfID":"21","pftitle":"王牌贵州6日"},{"phase_num":"第1期","phase_begin_time":"2019-05-04","phase_id":"28","pfpic":"","pfID":"22","pftitle":"王者荣耀4飞10日"},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"29","pfpic":"","pfID":"23","pftitle":"悦享时光+版纳3飞8日"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"30","pfpic":"","pfID":"24","pftitle":"悦游经典带版7晚8天"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"31","pfpic":"","pfID":"25","pftitle":"悦游经典带香7晚8天"},{"phase_num":"第1期","phase_begin_time":"2019-05-01","phase_id":"32","pfpic":"","pfID":"26","pftitle":"【绿野·仙踪】昆大丽版3飞8天半自助"},{"phase_num":"第1期","phase_begin_time":"2019-04-27","phase_id":"33","pfpic":"","pfID":"27","pftitle":"田园农庄度假"},{"phase_num":"第1期","phase_begin_time":"2019-04-27","phase_id":"34","pfpic":"","pfID":"28","pftitle":"田园农庄度假"},{"phase_num":"第1期","phase_begin_time":"2019-05-11","phase_id":"36","pfpic":"","pfID":"29","pftitle":"周末湖畔田园农庄智斗欢乐行"},{"phase_num":"第1期","phase_begin_time":"2019-05-18","phase_id":"37","pfpic":"","pfID":"30","pftitle":"湖钓农庄剧本杀两天一夜"},{"phase_num":"第1期","phase_begin_time":"2019-04-30","phase_id":"38","pfpic":"","pfID":"31","pftitle":"周末湖畔田园农庄智斗欢乐行"},{"phase_num":"第1期","phase_begin_time":"2019-05-13","phase_id":"39","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190505/764a6c0be233c8713c55fd9fa2e9e648.jpg","pfID":"32","pftitle":"测试邀请1"},{"phase_num":"第1期","phase_begin_time":"2019-05-08","phase_id":"40","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190506/d86fe320a316be106f7ed00d49f8055c.jpg","pfID":"33","pftitle":"测试邀请2"},{"phase_num":"第1期","phase_begin_time":"2019-05-15","phase_id":"41","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190507/3e40afa64a0b689b7c9dedec43086e20.jpg","pfID":"34","pftitle":"测试邀请订单"},{"phase_num":"第1期","phase_begin_time":"2019-05-07","phase_id":"42","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190507/3e40afa64a0b689b7c9dedec43086e20.jpg","pfID":"35","pftitle":"测试邀请订单"},{"phase_num":"第1期","phase_begin_time":"2019-06-01","phase_id":"43","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfID":"36","pftitle":"测试活动0522"},{"phase_num":"第1期","phase_begin_time":"2019-05-30","phase_id":"44","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190528/6f56de80e50c153180218cd9feb68e77.jpg","pfID":"37","pftitle":"瞳伴活动"},{"phase_num":"第1期","phase_begin_time":"2019-06-04","phase_id":"45","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfID":"38","pftitle":"测试活动0522"},{"phase_num":"第2期","phase_begin_time":"2019-06-19","phase_id":"46","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfID":"38","pftitle":"测试活动0522"},{"phase_num":"第1期","phase_begin_time":"2019-06-29","phase_id":"47","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190605/d3a50b7f6646db95d7be15efe3980d83.png","pfID":"39","pftitle":"测试优惠券"},{"phase_num":"第1期","phase_begin_time":"2019-06-29","phase_id":"48","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190605/af83db05654b0e2804f825f970c09920.png","pfID":"40","pftitle":"测试优惠券2"},{"phase_num":"第3期","phase_begin_time":"2019-07-04","phase_id":"49","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfID":"38","pftitle":"测试活动0522"},{"phase_num":"第4期","phase_begin_time":"2019-08-10","phase_id":"50","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190522/9274caa91ecd3cff4c6a3517779ad0e5.png","pfID":"38","pftitle":"测试活动0522"},{"phase_num":"第1期","phase_begin_time":"2019-08-21","phase_id":"51","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190620/83c40058e319bcacdaaa67ef6d3a6fe7.jpg","pfID":"41","pftitle":"测试活动0620"},{"phase_num":"第1期","phase_begin_time":"2019-07-30","phase_id":"52","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/03ed8cbba9be220894c667ccb2d3ba24.jpg","pfID":"42","pftitle":"测试多期队长活动"},{"phase_num":"第1期","phase_begin_time":"2019-07-26","phase_id":"53","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/280a72c7843fa6078a65e18142cc97fd.jpg","pfID":"43","pftitle":"测试多期队长活动付费"},{"phase_num":"第2期","phase_begin_time":"2019-08-01","phase_id":"54","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/03ed8cbba9be220894c667ccb2d3ba24.jpg","pfID":"42","pftitle":"测试多期队长活动"},{"phase_num":"第3期","phase_begin_time":"2019-08-02","phase_id":"55","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/03ed8cbba9be220894c667ccb2d3ba24.jpg","pfID":"42","pftitle":"测试多期队长活动"},{"phase_num":"第2期","phase_begin_time":"2019-08-03","phase_id":"56","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/280a72c7843fa6078a65e18142cc97fd.jpg","pfID":"43","pftitle":"测试多期队长活动付费"},{"phase_num":"第3期","phase_begin_time":"2019-08-04","phase_id":"57","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190707/280a72c7843fa6078a65e18142cc97fd.jpg","pfID":"43","pftitle":"测试多期队长活动付费"},{"phase_num":"第1期","phase_begin_time":"2019-07-29","phase_id":"58","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190709/0c6151e6ee3889c332fdc6d6697f3d85.jpg","pfID":"44","pftitle":"啊啊啊啊啊"},{"phase_num":"第1期","phase_begin_time":"2019-08-19","phase_id":"59","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190812/5b7167b0744118f6d1bd5d80b83047bb.jpg","pfID":"45","pftitle":"测试测试活动活动"},{"phase_num":"第2期","phase_begin_time":"2019-09-01","phase_id":"60","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190605/af83db05654b0e2804f825f970c09920.png","pfID":"40","pftitle":"测试优惠券2"},{"phase_num":"第2期","phase_begin_time":"2019-09-01","phase_id":"61","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190605/d3a50b7f6646db95d7be15efe3980d83.png","pfID":"39","pftitle":"测试优惠券"},{"phase_num":"第2期","phase_begin_time":"2019-09-01","phase_id":"62","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190507/3e40afa64a0b689b7c9dedec43086e20.jpg","pfID":"34","pftitle":"测试邀请订单"},{"phase_num":"第1期","phase_begin_time":"2019-11-01","phase_id":"63","pfpic":"http://www.91yiwo.com/ylyy/uploads/xingcheng/20190909/86e70c490f2f73489a9dfb602b61497a.jpg","pfID":"46","pftitle":"上海"}]
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

    public static class ObjBean implements Serializable{
        /**
         * phase_num : 第1期
         * phase_begin_time : 2019-04-12
         * phase_id : 1
         * pfpic :
         * pfID : 1
         * pftitle : 二龙山爬山运动会
         */

        private String phase_num;
        private String phase_begin_time;
        private String phase_id;
        private String pfpic;
        private String pfID;
        private String pftitle;

        public String getPhase_num() {
            return phase_num;
        }

        public void setPhase_num(String phase_num) {
            this.phase_num = phase_num;
        }

        public String getPhase_begin_time() {
            return phase_begin_time;
        }

        public void setPhase_begin_time(String phase_begin_time) {
            this.phase_begin_time = phase_begin_time;
        }

        public String getPhase_id() {
            return phase_id;
        }

        public void setPhase_id(String phase_id) {
            this.phase_id = phase_id;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

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
