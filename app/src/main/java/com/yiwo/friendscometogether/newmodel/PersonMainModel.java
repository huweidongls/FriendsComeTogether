package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2019/1/3.
 */

public class PersonMainModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"info":{"wy_accid":"yy15754633415","age":"24岁","address":"黑龙江省-哈尔滨市","userpic":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","autograph":"喵呜～","sex":"0","userlike":"7","GiveCount":0,"friends":"0","follow":"0","fans":"5"},"Friend":[{"pfID":"342","pftitle":"ABC","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190218/0-5ca95ca48643c0a4fe771cc1fc6724a29002.png","http://39.104.102.152/uploads/article/20190218/1-49badd9e0ce0dd82091eab8e570d23016969.png","http://39.104.102.152/uploads/article/20190218/2-654e87c2368f39df67eddec40b19b04f9486.jpg","http://39.104.102.152/uploads/article/20190218/3-773b0f50c081e73641fa32c1a4b2b5867081.jpg"],"userID":"72","fmgood":"0","pfaddress":"","pflook":"3","pftime":"1个月前","fmcomment":"3","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[{"username":"花生","fcID":"410","fmID":"342","fctitle":"评论评论","fctime":"1551762684","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"411","fmID":"342","fctitle":"花生回复:花生:回复回复","fctime":"1551762698","userID":"72","buserID":"72","fcreply":"410","fcquote":null,"fcquoteid":null,"which_fcID":"410","greatcomment":"410"}],"commentcount":"3"},{"pfID":"343","pftitle":"这是一个友记标题","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190218/0-1afaac60fb22fb0cf9c36ce7324d46667516.png","http://39.104.102.152/uploads/article/20190218/1-dc5bdeeee636f3d1b45f8fdb074ed8719343.png","http://39.104.102.152/uploads/article/20190218/2-3837ed4073124275632f0f206e96fa887557.jpg","http://39.104.102.152/uploads/article/20190218/3-8b74716e996f561e9eff11bd673df7a69003.jpg","http://39.104.102.152/uploads/article/20190218/4-20c9dfa4b599a2ffb70b784eb8a311688330.jpeg"],"userID":"72","fmgood":"4","pfaddress":"","pflook":"45","pftime":"1个月前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"358","pftitle":"123","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190225/0-0ff0674cbb57e6d1c4f00e282e3d13813783.jpg"],"userID":"72","fmgood":"5","pfaddress":"","pflook":"47","pftime":"3星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"368","pftitle":"测试插文的友记1111","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190308/0-41a37fc0f8690096e14336cf9239ae497969.jpg","http://39.104.102.152/uploads/article/20190308/1-be3982140e9047b0b839ef03c1d5bd684491.jpg","http://39.104.102.152/uploads/article/20190308/2-2cbc8bf3dbda6d286378b2c1193985e91774.jpg","http://39.104.102.152/uploads/article/20190308/3-6b178cd27717fb2f187582c081f21cca1090.jpg"],"userID":"72","fmgood":"2","pfaddress":"","pflook":"111","pftime":"2星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"377","pftitle":"123","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190318/0-e3ad71ad8a551e9680e8916e4ec3649a8728.jpg","http://39.104.102.152/uploads/article/20190318/1-90cec43a6b1bc15c5528a1fcb830fe7b9608.yiwo.friendscometogether.png","http://39.104.102.152/uploads/article/20190318/2-90cec43a6b1bc15c5528a1fcb830fe7b6980.jpeg","http://39.104.102.152/uploads/article/20190318/3-90cec43a6b1bc15c5528a1fcb830fe7b9550.jpeg"],"userID":"72","fmgood":"2","pfaddress":"","pflook":"25","pftime":"1星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"395","pftitle":"新的热门友记测试","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190319/0-86a685659de8bedf155d86846091112f5170.jpg","http://39.104.102.152/uploads/article/20190319/1-86a685659de8bedf155d86846091112f6715.jpeg"],"userID":"72","fmgood":"1","pfaddress":"","pflook":"24","pftime":"3天前","fmcomment":"3","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[{"username":"花生","fcID":"426","fmID":"395","fctitle":"ooo","fctime":"1553148417","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"428","fmID":"395","fctitle":"mingming","fctime":"1553148813","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}],"commentcount":"3"}],"activity":[{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"}],"Photo":[{"uid":"121","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/13/c40f3043352e4560ffa0d211819516e715500231624.jpg","utime":"2019-02-13 09:59:22","udel":"0"},{"uid":"122","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/13/2509fb3fc9d41d8b7b6e59e6eed284f0155002316613.jpg","utime":"2019-02-13 09:59:26","udel":"0"},{"uid":"126","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/18/7fe6388ebc96f3980faaff819b6639e7155045689310.jpg","utime":"2019-02-18 10:28:13","udel":"0"},{"uid":"142","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/05/7bf853f4c5343974590337eb565f1bfe15517660005.jpg","utime":"2019-03-05 14:06:40","udel":"0"},{"uid":"143","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/06/9635172c343b809329a1d1969c5ca94b155185742920.jpg","utime":"2019-03-06 15:30:29","udel":"0"},{"uid":"149","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/14/81fee857d614fdfe19d3a8032afb79f915525327386.jpg","utime":"2019-03-14 11:05:38","udel":"0"},{"uid":"161","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/b06c4a8e0124ef4e4b0cc2d1b4fce76a155297779216.jpg","utime":"2019-03-19 14:43:12","udel":"0"},{"uid":"162","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/d56daa048f5989b5cc1e33ad969e2691155297788513.jpg","utime":"2019-03-19 14:44:45","udel":"0"},{"uid":"163","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/fcac603dd7484145ed937a00613fe530155297810113.jpg","utime":"2019-03-19 14:48:21","udel":"0"},{"uid":"166","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/7fc92b2fb186bc5efb98fbb4d3829221155297846613.jpg","utime":"2019-03-19 14:54:26","udel":"0"},{"uid":"170","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","utime":"2019-03-20 09:25:04","udel":"0"}],"mytag":{"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠","Travel":"丽江"},"usertag":{"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠","Travel":"丽江","Same":{"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅,","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球,","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚,","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司,","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬,","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠,","Travel":"丽江,","Same":""}}}
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
         * info : {"wy_accid":"yy15754633415","age":"24岁","address":"黑龙江省-哈尔滨市","userpic":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","autograph":"喵呜～","sex":"0","userlike":"7","GiveCount":0,"friends":"0","follow":"0","fans":"5"}
         * Friend : [{"pfID":"342","pftitle":"ABC","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190218/0-5ca95ca48643c0a4fe771cc1fc6724a29002.png","http://39.104.102.152/uploads/article/20190218/1-49badd9e0ce0dd82091eab8e570d23016969.png","http://39.104.102.152/uploads/article/20190218/2-654e87c2368f39df67eddec40b19b04f9486.jpg","http://39.104.102.152/uploads/article/20190218/3-773b0f50c081e73641fa32c1a4b2b5867081.jpg"],"userID":"72","fmgood":"0","pfaddress":"","pflook":"3","pftime":"1个月前","fmcomment":"3","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[{"username":"花生","fcID":"410","fmID":"342","fctitle":"评论评论","fctime":"1551762684","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"411","fmID":"342","fctitle":"花生回复:花生:回复回复","fctime":"1551762698","userID":"72","buserID":"72","fcreply":"410","fcquote":null,"fcquoteid":null,"which_fcID":"410","greatcomment":"410"}],"commentcount":"3"},{"pfID":"343","pftitle":"这是一个友记标题","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190218/0-1afaac60fb22fb0cf9c36ce7324d46667516.png","http://39.104.102.152/uploads/article/20190218/1-dc5bdeeee636f3d1b45f8fdb074ed8719343.png","http://39.104.102.152/uploads/article/20190218/2-3837ed4073124275632f0f206e96fa887557.jpg","http://39.104.102.152/uploads/article/20190218/3-8b74716e996f561e9eff11bd673df7a69003.jpg","http://39.104.102.152/uploads/article/20190218/4-20c9dfa4b599a2ffb70b784eb8a311688330.jpeg"],"userID":"72","fmgood":"4","pfaddress":"","pflook":"45","pftime":"1个月前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"358","pftitle":"123","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190225/0-0ff0674cbb57e6d1c4f00e282e3d13813783.jpg"],"userID":"72","fmgood":"5","pfaddress":"","pflook":"47","pftime":"3星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"368","pftitle":"测试插文的友记1111","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190308/0-41a37fc0f8690096e14336cf9239ae497969.jpg","http://39.104.102.152/uploads/article/20190308/1-be3982140e9047b0b839ef03c1d5bd684491.jpg","http://39.104.102.152/uploads/article/20190308/2-2cbc8bf3dbda6d286378b2c1193985e91774.jpg","http://39.104.102.152/uploads/article/20190308/3-6b178cd27717fb2f187582c081f21cca1090.jpg"],"userID":"72","fmgood":"2","pfaddress":"","pflook":"111","pftime":"2星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"377","pftitle":"123","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190318/0-e3ad71ad8a551e9680e8916e4ec3649a8728.jpg","http://39.104.102.152/uploads/article/20190318/1-90cec43a6b1bc15c5528a1fcb830fe7b9608.yiwo.friendscometogether.png","http://39.104.102.152/uploads/article/20190318/2-90cec43a6b1bc15c5528a1fcb830fe7b6980.jpeg","http://39.104.102.152/uploads/article/20190318/3-90cec43a6b1bc15c5528a1fcb830fe7b9550.jpeg"],"userID":"72","fmgood":"2","pfaddress":"","pflook":"25","pftime":"1星期前","fmcomment":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[],"commentcount":"0"},{"pfID":"395","pftitle":"新的热门友记测试","pfcontent":"","pfpic":["http://39.104.102.152/uploads/article/20190319/0-86a685659de8bedf155d86846091112f5170.jpg","http://39.104.102.152/uploads/article/20190319/1-86a685659de8bedf155d86846091112f6715.jpeg"],"userID":"72","fmgood":"1","pfaddress":"","pflook":"24","pftime":"3天前","fmcomment":"3","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","username":"花生","comment_list":[{"username":"花生","fcID":"426","fmID":"395","fctitle":"ooo","fctime":"1553148417","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"428","fmID":"395","fctitle":"mingming","fctime":"1553148813","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"}],"commentcount":"3"}]
         * activity : [{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"},{"pfID":"126","pftitle":"来滑雪","pfcomment":"0","pfcontent":"","pfpic":"http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg","pfaddress":"太阳岛","pflook":"160","pftime":"3星期前","follow":"0","headportrait":"http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg","username":"哈尔滨友来友约文化旅游传媒有限公司","author":"1"}]
         * Photo : [{"uid":"121","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/13/c40f3043352e4560ffa0d211819516e715500231624.jpg","utime":"2019-02-13 09:59:22","udel":"0"},{"uid":"122","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/13/2509fb3fc9d41d8b7b6e59e6eed284f0155002316613.jpg","utime":"2019-02-13 09:59:26","udel":"0"},{"uid":"126","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/02/18/7fe6388ebc96f3980faaff819b6639e7155045689310.jpg","utime":"2019-02-18 10:28:13","udel":"0"},{"uid":"142","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/05/7bf853f4c5343974590337eb565f1bfe15517660005.jpg","utime":"2019-03-05 14:06:40","udel":"0"},{"uid":"143","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/06/9635172c343b809329a1d1969c5ca94b155185742920.jpg","utime":"2019-03-06 15:30:29","udel":"0"},{"uid":"149","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/14/81fee857d614fdfe19d3a8032afb79f915525327386.jpg","utime":"2019-03-14 11:05:38","udel":"0"},{"uid":"161","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/b06c4a8e0124ef4e4b0cc2d1b4fce76a155297779216.jpg","utime":"2019-03-19 14:43:12","udel":"0"},{"uid":"162","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/d56daa048f5989b5cc1e33ad969e2691155297788513.jpg","utime":"2019-03-19 14:44:45","udel":"0"},{"uid":"163","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/fcac603dd7484145ed937a00613fe530155297810113.jpg","utime":"2019-03-19 14:48:21","udel":"0"},{"uid":"166","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/19/7fc92b2fb186bc5efb98fbb4d3829221155297846613.jpg","utime":"2019-03-19 14:54:26","udel":"0"},{"uid":"170","userID":"72","upicurl":"http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg","utime":"2019-03-20 09:25:04","udel":"0"}]
         * mytag : {"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠","Travel":"丽江"}
         * usertag : {"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠","Travel":"丽江","Same":{"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅,","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球,","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚,","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司,","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬,","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠,","Travel":"丽江,","Same":""}}
         */

        private InfoBean info;
        private MytagBean mytag;
        private UsertagBean usertag;
        private List<FriendBean> Friend;
        private List<ActivityBean> activity;
        private List<PhotoBean> Photo;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public MytagBean getMytag() {
            return mytag;
        }

        public void setMytag(MytagBean mytag) {
            this.mytag = mytag;
        }

        public UsertagBean getUsertag() {
            return usertag;
        }

        public void setUsertag(UsertagBean usertag) {
            this.usertag = usertag;
        }

        public List<FriendBean> getFriend() {
            return Friend;
        }

        public void setFriend(List<FriendBean> Friend) {
            this.Friend = Friend;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public List<PhotoBean> getPhoto() {
            return Photo;
        }

        public void setPhoto(List<PhotoBean> Photo) {
            this.Photo = Photo;
        }

        public static class InfoBean {
            /**
             * wy_accid : yy15754633415
             * age : 24岁
             * address : 黑龙江省-哈尔滨市
             * userpic : http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg
             * username : 花生
             * autograph : 喵呜～
             * sex : 0
             * userlike : 7
             * GiveCount : 0
             * friends : 0
             * follow : 0
             * fans : 5
             * usermarry 1单身 2已婚
             * usergrade 用户等级
             * usercodeok 0未认证 1已认证
             *
             */

            private String wy_accid;
            private String age;
            private String address;
            private String userpic;
            private String username;
            private String autograph;
            private String sex;
            private String userlike;
            private int GiveCount;
            private String friends;
            private String follow;
            private String fans;
            private String otherUserId;
            private String if_kefu;
            private String usermarry;
            private String usergrade;
            private String usercodeok;
            public String getWy_accid() {
                return wy_accid;
            }

            public void setWy_accid(String wy_accid) {
                this.wy_accid = wy_accid;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserlike() {
                return userlike;
            }

            public void setUserlike(String userlike) {
                this.userlike = userlike;
            }

            public int getGiveCount() {
                return GiveCount;
            }

            public void setGiveCount(int GiveCount) {
                this.GiveCount = GiveCount;
            }

            public String getFriends() {
                return friends;
            }

            public void setFriends(String friends) {
                this.friends = friends;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public String getOtherUserId() {
                return otherUserId;
            }

            public void setOtherUserId(String otherUserId) {
                this.otherUserId = otherUserId;
            }

            public String getIf_kefu() {
                return if_kefu;
            }

            public void setIf_kefu(String if_kefu) {
                this.if_kefu = if_kefu;
            }

            public String getUsermarry() {
                return usermarry;
            }

            public void setUsermarry(String usermarry) {
                this.usermarry = usermarry;
            }

            public String getUsergrade() {
                return usergrade;
            }

            public void setUsergrade(String usergrade) {
                this.usergrade = usergrade;
            }

            public String getUsercodeok() {
                return usercodeok;
            }

            public void setUsercodeok(String usercodeok) {
                this.usercodeok = usercodeok;
            }
        }

        public static class MytagBean {
            /**
             * personality : 叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅
             * motion : 跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球
             * Music : 薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚
             * Delicious : 生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司
             * Film : 泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬
             * book : 火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠
             * Travel : 丽江
             */

            private String personality;
            private String motion;
            private String Music;
            private String Delicious;
            private String Film;
            private String book;
            private String Travel;

            public String getPersonality() {
                return personality;
            }

            public void setPersonality(String personality) {
                this.personality = personality;
            }

            public String getMotion() {
                return motion;
            }

            public void setMotion(String motion) {
                this.motion = motion;
            }

            public String getMusic() {
                return Music;
            }

            public void setMusic(String Music) {
                this.Music = Music;
            }

            public String getDelicious() {
                return Delicious;
            }

            public void setDelicious(String Delicious) {
                this.Delicious = Delicious;
            }

            public String getFilm() {
                return Film;
            }

            public void setFilm(String Film) {
                this.Film = Film;
            }

            public String getBook() {
                return book;
            }

            public void setBook(String book) {
                this.book = book;
            }

            public String getTravel() {
                return Travel;
            }

            public void setTravel(String Travel) {
                this.Travel = Travel;
            }
        }

        public static class UsertagBean implements Serializable {
            /**
             * personality : 叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅
             * motion : 跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球
             * Music : 薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚
             * Delicious : 生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司
             * Film : 泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬
             * book : 火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠
             * Travel : 丽江
             * Same : {"personality":"叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅,","motion":"跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球,","Music":"薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚,","Delicious":"生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司,","Film":"泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬,","book":"火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠,","Travel":"丽江,","Same":""}
             */

            private String personality;
            private String motion;
            private String Music;
            private String Delicious;
            private String Film;
            private String book;
            private String Travel;
            private SameBean Same;

            public String getPersonality() {
                return personality;
            }

            public void setPersonality(String personality) {
                this.personality = personality;
            }

            public String getMotion() {
                return motion;
            }

            public void setMotion(String motion) {
                this.motion = motion;
            }

            public String getMusic() {
                return Music;
            }

            public void setMusic(String Music) {
                this.Music = Music;
            }

            public String getDelicious() {
                return Delicious;
            }

            public void setDelicious(String Delicious) {
                this.Delicious = Delicious;
            }

            public String getFilm() {
                return Film;
            }

            public void setFilm(String Film) {
                this.Film = Film;
            }

            public String getBook() {
                return book;
            }

            public void setBook(String book) {
                this.book = book;
            }

            public String getTravel() {
                return Travel;
            }

            public void setTravel(String Travel) {
                this.Travel = Travel;
            }

            public SameBean getSame() {
                return Same;
            }

            public void setSame(SameBean Same) {
                this.Same = Same;
            }

            public static class SameBean implements Serializable {
                /**
                 * personality : 叫我逗比,局气,热血,叛逆,随性,健谈,安静,长发及腰,马甲线,大叔控,AJ控,讲义气,有面,靠谱,喜欢简单,宅,
                 * motion : 跑步,羽毛球,射箭,健身,街舞,台球,高尔夫,网球,
                 * Music : 薛之谦,欧美,金属,嘻哈,R&B,张国荣,电子,摇滚,
                 * Delicious : 生鱼片,汉堡,意大利面,牛排,韩国烤肉,石锅拌饭,日本拉面,寿司,
                 * Film : 泰坦尼克号,倩女幽魂,这个杀手不太冷,蝙蝠侠,熊出没大电影,盗梦空间,教父,霸王别姬,
                 * book : 火影忍者,蜡笔小新,进击的巨人,哆啦A梦,篮球火,大灌篮,名侦探柯南,七龙珠,
                 * Travel : 丽江,
                 * Same :
                 */

                private String personality;
                private String motion;
                private String Music;
                private String Delicious;
                private String Film;
                private String book;
                private String Travel;
                private String Same;

                public String getPersonality() {
                    return personality;
                }

                public void setPersonality(String personality) {
                    this.personality = personality;
                }

                public String getMotion() {
                    return motion;
                }

                public void setMotion(String motion) {
                    this.motion = motion;
                }

                public String getMusic() {
                    return Music;
                }

                public void setMusic(String Music) {
                    this.Music = Music;
                }

                public String getDelicious() {
                    return Delicious;
                }

                public void setDelicious(String Delicious) {
                    this.Delicious = Delicious;
                }

                public String getFilm() {
                    return Film;
                }

                public void setFilm(String Film) {
                    this.Film = Film;
                }

                public String getBook() {
                    return book;
                }

                public void setBook(String book) {
                    this.book = book;
                }

                public String getTravel() {
                    return Travel;
                }

                public void setTravel(String Travel) {
                    this.Travel = Travel;
                }

                public String getSame() {
                    return Same;
                }

                public void setSame(String Same) {
                    this.Same = Same;
                }
            }
        }

        public static class FriendBean {
            /**
             * pfID : 342
             * pftitle : ABC
             * pfcontent :
             * pfpic : ["http://39.104.102.152/uploads/article/20190218/0-5ca95ca48643c0a4fe771cc1fc6724a29002.png","http://39.104.102.152/uploads/article/20190218/1-49badd9e0ce0dd82091eab8e570d23016969.png","http://39.104.102.152/uploads/article/20190218/2-654e87c2368f39df67eddec40b19b04f9486.jpg","http://39.104.102.152/uploads/article/20190218/3-773b0f50c081e73641fa32c1a4b2b5867081.jpg"]
             * userID : 72
             * fmgood : 0
             * pfaddress :
             * pflook : 3
             * pftime : 1个月前
             * fmcomment : 3
             * headportrait : http://39.104.102.152/uploads/header/2019/03/20/81b899ace8f6c5204b27c944116c7bad155304510413.jpg
             * username : 花生
             * comment_list : [{"username":"花生","fcID":"410","fmID":"342","fctitle":"评论评论","fctime":"1551762684","userID":"72","buserID":"0","fcreply":"0","fcquote":null,"fcquoteid":null,"which_fcID":"0","greatcomment":"0"},{"username":"花生","fcID":"411","fmID":"342","fctitle":"花生回复:花生:回复回复","fctime":"1551762698","userID":"72","buserID":"72","fcreply":"410","fcquote":null,"fcquoteid":null,"which_fcID":"410","greatcomment":"410"}]
             * commentcount : 3
             */

            private String pfID;
            private String pftitle;
            private String pfcontent;
            private String userID;
            private String fmgood;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String fmcomment;
            private String headportrait;
            private String username;
            private String commentcount;
            private List<String> pfpic;
            private List<CommentListBean> comment_list;

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

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getFmgood() {
                return fmgood;
            }

            public void setFmgood(String fmgood) {
                this.fmgood = fmgood;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getFmcomment() {
                return fmcomment;
            }

            public void setFmcomment(String fmcomment) {
                this.fmcomment = fmcomment;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCommentcount() {
                return commentcount;
            }

            public void setCommentcount(String commentcount) {
                this.commentcount = commentcount;
            }

            public List<String> getPfpic() {
                return pfpic;
            }

            public void setPfpic(List<String> pfpic) {
                this.pfpic = pfpic;
            }

            public List<CommentListBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentListBean> comment_list) {
                this.comment_list = comment_list;
            }

            public static class CommentListBean {
                /**
                 * username : 花生
                 * fcID : 410
                 * fmID : 342
                 * fctitle : 评论评论
                 * fctime : 1551762684
                 * userID : 72
                 * buserID : 0
                 * fcreply : 0
                 * fcquote : null
                 * fcquoteid : null
                 * which_fcID : 0
                 * greatcomment : 0
                 */

                private String username;
                private String fcID;
                private String fmID;
                private String fctitle;
                private String fctime;
                private String userID;
                private String buserID;
                private String fcreply;
                private Object fcquote;
                private Object fcquoteid;
                private String which_fcID;
                private String greatcomment;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getFcID() {
                    return fcID;
                }

                public void setFcID(String fcID) {
                    this.fcID = fcID;
                }

                public String getFmID() {
                    return fmID;
                }

                public void setFmID(String fmID) {
                    this.fmID = fmID;
                }

                public String getFctitle() {
                    return fctitle;
                }

                public void setFctitle(String fctitle) {
                    this.fctitle = fctitle;
                }

                public String getFctime() {
                    return fctime;
                }

                public void setFctime(String fctime) {
                    this.fctime = fctime;
                }

                public String getUserID() {
                    return userID;
                }

                public void setUserID(String userID) {
                    this.userID = userID;
                }

                public String getBuserID() {
                    return buserID;
                }

                public void setBuserID(String buserID) {
                    this.buserID = buserID;
                }

                public String getFcreply() {
                    return fcreply;
                }

                public void setFcreply(String fcreply) {
                    this.fcreply = fcreply;
                }

                public Object getFcquote() {
                    return fcquote;
                }

                public void setFcquote(Object fcquote) {
                    this.fcquote = fcquote;
                }

                public Object getFcquoteid() {
                    return fcquoteid;
                }

                public void setFcquoteid(Object fcquoteid) {
                    this.fcquoteid = fcquoteid;
                }

                public String getWhich_fcID() {
                    return which_fcID;
                }

                public void setWhich_fcID(String which_fcID) {
                    this.which_fcID = which_fcID;
                }

                public String getGreatcomment() {
                    return greatcomment;
                }

                public void setGreatcomment(String greatcomment) {
                    this.greatcomment = greatcomment;
                }
            }
        }

        public static class ActivityBean {
            /**
             * pfID : 126
             * pftitle : 来滑雪
             * pfcomment : 0
             * pfcontent :
             * pfpic : http://39.104.102.152/uploads/xingcheng/20190220/c5bae37d9db72c0ac0bee33331c33f3a.jpg
             * pfaddress : 太阳岛
             * pflook : 160
             * pftime : 3星期前
             * follow : 0
             * headportrait : http://39.104.102.152/uploads/header/2019/03/20/2eadd6d7716c463f4b992a6715dcb13d15530571036.jpg
             * username : 哈尔滨友来友约文化旅游传媒有限公司
             * author : 1
             */

            private String pfID;
            private String pftitle;
            private String pfcomment;
            private String pfcontent;
            private String pfpic;
            private String pfaddress;
            private String pflook;
            private String pftime;
            private String follow;
            private String headportrait;
            private String username;
            private String author;

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

            public String getPfcomment() {
                return pfcomment;
            }

            public void setPfcomment(String pfcomment) {
                this.pfcomment = pfcomment;
            }

            public String getPfcontent() {
                return pfcontent;
            }

            public void setPfcontent(String pfcontent) {
                this.pfcontent = pfcontent;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getPflook() {
                return pflook;
            }

            public void setPflook(String pflook) {
                this.pflook = pflook;
            }

            public String getPftime() {
                return pftime;
            }

            public void setPftime(String pftime) {
                this.pftime = pftime;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getHeadportrait() {
                return headportrait;
            }

            public void setHeadportrait(String headportrait) {
                this.headportrait = headportrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }

        public static class PhotoBean {
            /**
             * uid : 121
             * userID : 72
             * upicurl : http://39.104.102.152/uploads/header/2019/02/13/c40f3043352e4560ffa0d211819516e715500231624.jpg
             * utime : 2019-02-13 09:59:22
             * udel : 0
             */

            private String uid;
            private String userID;
            private String upicurl;
            private String utime;
            private String udel;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }

            public String getUpicurl() {
                return upicurl;
            }

            public void setUpicurl(String upicurl) {
                this.upicurl = upicurl;
            }

            public String getUtime() {
                return utime;
            }

            public void setUtime(String utime) {
                this.utime = utime;
            }

            public String getUdel() {
                return udel;
            }

            public void setUdel(String udel) {
                this.udel = udel;
            }
        }
    }
}
