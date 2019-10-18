package com.yiwo.friendscometogether;

/**
 * Created by Administrator on 2018/7/13.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.http.ViseHttp;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.pages.WelcomeActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FTPTimeCount;
import com.yiwo.friendscometogether.utils.TimeCount;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import com.yiwo.friendscometogether.location.NimDemoLocationProvider;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;


public class MyApplication extends Application {
    public static String versionCode = "V2.1.0";
    public static String sign = "";
    // 注册获取验证码倒计时
    public static TimeCount timecount;
    // 修改密码获取验证码倒计时
    public static FTPTimeCount ftptimecount;
    // log开关
    public static Boolean logSwitch = true;
    public static String pak_Name;
    public static String Version_Name;
    public static String Phone_model;
    public static String Phone_system_version;

    private List<Activity> mList = new LinkedList<Activity>();
    private static MyApplication instance;

    private SpCache spCache;
    private SpImp spImp;

    public static String genPath;
//    //数据库
    private MigrationHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDatabase();
        spCache = new SpCache(this);
        spImp = new SpImp(this);
        ScreenAdapterTools.init(this);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//SD卡已挂载
            genPath = getExternalCacheDir().getAbsolutePath().toString() + "/";
        } else {
            genPath = getCacheDir().getAbsolutePath() + "/";
        }
//        // 此处 certificate 请传入为开发者配置好的小米证书名称
        MixPushConfig xm_config = new MixPushConfig();
        xm_config.xmAppId = "2882303761517874789";
        xm_config.xmAppKey = "5911787488789";
        xm_config.xmCertificateName = "tongbanxiaomizhengshu";

        xm_config.hwCertificateName = "tongbanhuaweizhengshu";
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持
        NIMClient.init(this, loginInfo(), options(xm_config));
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        UMShareAPI.get(this);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5b5579fbb27b0a608200000d"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        {
            PlatformConfig.setWeixin(UMConfig.WECHAT_APPID, UMConfig.WECHAT_APPSECRET);
        }
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(NetConfig.BaseUrl);

        //oncreate方法中写
        timecount = new TimeCount(60000, 1000);
        ftptimecount = new FTPTimeCount(60000, 1000);
        CrashReport.initCrashReport(getApplicationContext(), "20d02c310e", false);

        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            // 初始化
            NimUIKit.init(this);
            // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
            // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
            NimUIKit.setLocationProvider(new NimDemoLocationProvider());

            //关闭消息提醒。
            NIMClient.toggleNotification(false);
        }
        DemoCache.setContext(this);
    }

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取当前客户端版本信息
     */
    private void getCurrentVersion() {

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), 0);
            Version_Name = info.versionName;
            versionCode = "V" + Version_Name;
            Phone_model = Build.MODEL;// 手机型号
            Phone_system_version = "android " + Build.VERSION.RELEASE;// android系统版本信息
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    // 如果返回值为 null，则全部使用默认参数。xn_config为小米
    private SDKOptions options(MixPushConfig xm_config) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.logo_gray;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        options.mixPushConfig = xm_config;
        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ${Screen.width} / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        Log.d("getgetwangyitoken","aaaa|||"+spImp.getYXID()+"|||"+spImp.getYXTOKEN());
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = spImp.getYXID();
        String token = spImp.getYXTOKEN();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
//        String account = spImp.getYXID();
////        NimUIKit.loginSuccess(account);
//        LoginInfo loginInfo = (LoginInfo) spCache.get("LoginInfo");
//        if(loginInfo == null){
//            return null;
//        }else {
//            return loginInfo;
//        }
    }
    public DaoSession getDaoSession() {
        return this.mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }
    public void setDatabase(){
//        通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//    可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//    注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//    所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        此处sport-db表示数据库名称 可以任意填写
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mHelper = new MigrationHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        Log.d("SessionmDaoSession11",mDaoSession.toString());
        Log.d("SessionmDaoSession11",mDaoSession.getUserGiveModelDao().toString());
    }
}
