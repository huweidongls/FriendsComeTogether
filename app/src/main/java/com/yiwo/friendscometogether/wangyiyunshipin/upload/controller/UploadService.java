package com.yiwo.friendscometogether.wangyiyunshipin.upload.controller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.cloud.nos.android.core.CallRet;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.vcloudnosupload.UploadBuilder;
import com.netease.vcloudnosupload.VcloudUpload;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberActivityChoosePicOrVideos;
import com.yiwo.friendscometogether.newpage.UpLoadVideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.config.DemoServers;
import com.yiwo.friendscometogether.wangyiyunshipin.server.DemoServerHttpClient;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.TranscodeResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.VideoInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.TranscodeType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadDbHelper;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadTotalDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;

import org.json.JSONException;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhukkun on 3/1/17.
 */
public class UploadService extends Service {
    private final static String TAG = UploadService.class.getSimpleName();

    public static final boolean show_nofitycation = false; //由于开启通知, 用户点击通知时,导航路径未明确,故暂时关闭通知.
    public static final int POLLING_INTERVAL = 30000; //30s一次的轮询,查询转码状态 用户可根据自己需要调整时长间隔

    public static final String UPLAOD_CALL_BACK = "/vod/uploadcallback";
    public static final String TRANSCODE_CALL_BACK = "/vod/transcodecallback";

    public static final int STATE_WAIT = 0;
    public static final int STATE_UPLOADING = 1;
    public static final int STATE_UPLOAD_COMPLETE = 2;
    public static final int STATE_UPLOAD_FAIL = 3;
    public static final int STATE_TRANSCODEING = 4;
    public static final int STATE_TRANSCODE_FAIL = 5;
    public static final int STATE_TRANSCODE_COMPLETE = 6;

    public static final int TRANSCODE_THREAD_STATE_WAIT = 0;
    public static final int TRANSCODE_THREAD_STATE_WORKING = 1;

    private UploadController controller;
    private VcloudUpload vcloudUpload;

    //用于处理上传任务的线程
    private HandlerThread uploadThread;
    //用于轮询转码状态的线程
    private HandlerThread transCodeQueryThread;
    //上传任务发送器, 发送任务后会在上传任务线程处理
    private Handler uploadHandler;
    //轮询任务发送器, 发送任务后会在转码查询线程处理
    private Handler transCodeQueryHandler;

    private UploadBinder mBinder = new UploadBinder();

    private int transcode_thread_state = TRANSCODE_THREAD_STATE_WAIT;

    private VideoItem uploadingItem; //当前正在上传的item
    private boolean needStopUpload; //是否需要停止上传

    private SpImp spImp;

    @Override
    public void onCreate() {
        super.onCreate();
        spImp = new SpImp(this);
        Log.i(TAG, "upload service create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UploadBinder extends Binder {

        public void attachController(UploadController controller) {
            UploadService.this.controller = controller;
        }

        public void startUpload() {
            //使用handler发送任务, 保证上传任务是在上传线程中串行执行,这样即使同时收到多个开启上传任务的请求,也会串行执行,并且由于只有Wait状态的任务才会进入uploadOne(),故可避免任务的重复上传
            needStopUpload = false;
            uploadHandler.post(new Runnable() {
                @Override
                public void run() {
//                    showNotification("正在上传中...", false);
                    UploadService.this.startUpload();
                }
            });
        }

        public void deleteUploadItem(VideoItem videoItem) {
            if (vcloudUpload != null && uploadingItem != null && videoItem.getId().equals(uploadingItem.getId())) {
                vcloudUpload.cancelTask();
            }
        }

        public void stopUpload() {
            if (vcloudUpload != null) {
                transCodeQueryHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        vcloudUpload.cancelTask();
                        //由于uploadThread中执行的uploadOne()sleep了200ms,所以即使任务关闭,此时的uploadItem也不为null.
                        if (uploadingItem != null) {
                            uploadingItem.setState(UploadService.STATE_WAIT);
                            controller.onReceiveUploadCallback(uploadingItem.getId(), uploadingItem.getUploadProgress(), STATE_WAIT);
                            UploadDbHelper.updateToDb(uploadingItem);
                        }
                    }
                });
            }
            needStopUpload = true;
        }

        public void showUploadNotification() {
            showNotification("正在上传中...", false);
        }

        /**
         * 开启转码查询
         */
        public void startTransQuery() {
            List list = controller.getDataAccessor().getTranscodingVidList();
            if (list != null && list.size() > 0 && transcode_thread_state == TRANSCODE_THREAD_STATE_WAIT) {
                transcodeStatePolling();
            }
        }

        public boolean isUploading() {
            return uploadingItem != null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        needStopUpload = false;
        uploadThread = new HandlerThread("UploadService");
        uploadThread.start();
        uploadHandler = new Handler(uploadThread.getLooper());

        transCodeQueryThread = new HandlerThread("transCodeQueryThread");
        transCodeQueryThread.start();
        transCodeQueryHandler = new Handler(transCodeQueryThread.getLooper());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (vcloudUpload != null) {
            vcloudUpload.cancelTask();
        }
        needStopUpload = true;
        uploadThread.quit();
        transCodeQueryThread.quit();
        transCodeQueryHandler.removeCallbacksAndMessages(null);
        uploadHandler.removeCallbacksAndMessages(null);
        return super.onUnbind(intent);
    }


    private void startUpload() {
        if (controller == null) {
            throw new NullPointerException("must attachController() before startUpload()");
        }

        List<VideoItem> waitUploadingItems = new ArrayList<>(UploadTotalDataAccessor.getInstance().getUploadList());
        LogUtil.i(TAG, "wait items:" + waitUploadingItems.size());
        for (int i = 0; i < waitUploadingItems.size(); i++) {
            VideoItem videoItem = waitUploadingItems.get(i);
            //判空,防止待上传项被删除.
            if (videoItem == null) continue;
            if (videoItem.getState() == STATE_WAIT) {
                controller.onUploadStateChanged(STATE_UPLOADING);
                if (!needStopUpload) {
                    uploadOne(videoItem);
                }
            } else if (videoItem.getState() == STATE_UPLOAD_COMPLETE && !UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().contains(videoItem)) {
                //进到此分支为 已上传成功,但未成功添加至应用服务器的数据
                addVideoToDemoServer(videoItem);
            }
        }
        if (UploadTotalDataAccessor.getInstance().isFullySuccess()) {
            controller.onUploadStateChanged(STATE_UPLOAD_COMPLETE);
//            showNotification("上传已完成", true);
        }
    }

    private boolean uploadOne(final VideoItem videoItem) {
        final Object lock = new Object();

        final File mFile = new File(videoItem.getFilePath());

        try {
            LogUtil.d(TAG, "uploadOne()   " + videoItem.getId());
            LogUtil.i(TAG, "upload type：" + videoItem.getType());
            vcloudUpload = getUploadBuild(mFile.getName()).build();
            VcloudUpload.UploadInfo uploadContext = new VcloudUpload.UploadInfo(videoItem.getUploadToken(), videoItem.getUploadBucket(), videoItem.getUploadObject());

            if (checkHasSucUpload(videoItem)) {
                return true;
            }
            uploadingItem = videoItem;
            String nosUrl = vcloudUpload.uploadFile(mFile, videoItem.getUploadContext(), uploadContext, new VcloudUpload.UploadCallBack() {

                @Override
                public void onUploadInit(VcloudUpload.UploadInfo uploadInfo) {
                    videoItem.setUploadToken(uploadInfo.getUploadToken());
                    videoItem.setUploadBucket(uploadInfo.getUploadBucketName());
                    videoItem.setUploadObject(uploadInfo.getUploadObjectName());
                }


                @Override
                public void onUploadContextCreate(Object o, String s, String s1) {
                    videoItem.setUploadContext(s1);
                    UploadDbHelper.updateToDb(videoItem);
                }

                @Override
                public void onProcess(Object o, long current, long mtotal) {
                    int progress = (int) (current * 100 / (float) mtotal);
                    videoItem.setState(STATE_UPLOADING);
                    videoItem.setUploadProgress(progress);
                    controller.onReceiveUploadCallback(videoItem.getId(), progress, STATE_UPLOADING);
                }

                @Override
                public void onSuccess(CallRet callRet) {
                    videoItem.setState(STATE_UPLOAD_COMPLETE);
                    UploadTotalDataAccessor.getInstance().uploadSuccess(videoItem);
                    JSONObject json = JSON.parseObject(callRet.getCallbackRetMsg());
                    Log.d("asdasdsd11",json.toJSONString());
                    if (json != null && json.getInteger("code") == 200) {
                        long vid = json.getJSONObject("ret").getLong("vid");
                        String requestId = json.getString("requestId");

                        videoItem.setVid(vid);
                        UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().add(videoItem);
                        UploadDbHelper.updateToDb(videoItem);
                        Log.d("asdasdsd11","aa2");
                        addVideoToDemoServer(videoItem);
                    }

                    synchronized (lock) {
                        lock.notify();
                    }
                }

                @Override
                public void onFailure(CallRet callRet) {
                    Log.d("asdasdsd11:onFailure",callRet.getCallbackRetMsg());
                    videoItem.setState(STATE_UPLOAD_FAIL);
                    controller.onReceiveUploadCallback(videoItem.getId(), 0, STATE_UPLOAD_FAIL);
                    UploadDbHelper.updateToDb(videoItem);

                    synchronized (lock) {
                        lock.notify();
                    }
                }

                @Override
                public void onCanceled(CallRet callRet) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            });
            Log.d("asdasdsd","疑似URl："+nosUrl);
            synchronized (lock) {
                lock.wait();
            }


            try {
                //sleep 200毫秒  作用: 在断网重连时,让时间片给其他线程进行状态修改等处理
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            uploadingItem = null;

            if (nosUrl != null) {
                //不显示上传成功状态,直接由上传成功到转码中
                //controller.onReceiveUploadCallback(videoItem.getId(), 100, STATE_UPLOAD_COMPLETE);
            }
        } catch (InvalidParameterException e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 检查该任务是否已成功上传, 防止在接近99.99%时,断网或进程被杀,客户端无法感知到已成功上传,故先查询
     *
     * 已上传到nos，未上传到demoserver
     *
     * @param videoItem
     * @return true 已成功上传, false未成功上传
     */
    private boolean checkHasSucUpload(final VideoItem videoItem) {

        final Object lock = new Object();

        if (videoItem.getUploadObject() != null) {
            vcloudUpload.uploadQuery(Arrays.asList(new String[]{videoItem.getUploadObject()}), new VcloudUpload.QueryCallBack() {
                @Override
                public void onSuc(List<Integer> list) {
                    if (list != null && list.size() > 0) {
                        int vid = list.get(0);
                        if (vid != 0) {
                            videoItem.setState(STATE_UPLOAD_COMPLETE);
                            videoItem.setVid(vid);
                            UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().add(videoItem);
                            UploadDbHelper.updateToDb(videoItem);
                            Log.d("asdasdsd11","aa3");
                            addVideoToDemoServer(videoItem);
                        }
                    }
                    synchronized (lock) {
                        lock.notify();
                    }
                }

                @Override
                public void onFail(String s) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            });

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return videoItem.getVid() != 0;

        } else {
            return false;
        }
    }

    private UploadBuilder getUploadBuild(String name) {
        Log.d("aaaaaaaaaaaaaaaaaa","getAppKey::"+getAppKey()+"///getAccount::"+spImp.getWyUpAccid()+"///getVodtoken::"+spImp.getWyUpToken());
        UploadBuilder builder = new UploadBuilder(DemoCache.getContext(), getAppKey(), spImp.getWyUpAccid(),spImp.getWyUpToken())
//        UploadBuilder builder = new UploadBuilder(DemoCache.getContext(), "d49345914660a3af65e7fa287ec90e6b", "15754633415", "e10adc3949ba59abbe56e057f20f883e")
                .setOriginFileName(getValidateFileName(name))
                .setUserFileName(name)
                .setCallbackUrl(DemoServers.apiServer() + TRANSCODE_CALL_BACK)
                .setUploadCallbackUrl(DemoServers.apiServer() + UPLAOD_CALL_BACK);
        return builder;
    }

    /**
     * NOS对原始文件名做了限制,含非法字符将无法上传,故对其进行过滤
     *
     * @param name
     * @return
     */
    private String getValidateFileName(String name) {
        int index_of_p = name.lastIndexOf('.');
        return System.currentTimeMillis() + name.substring(index_of_p);
    }

    /**
     * 在应用服务器 标记 视频上传成功,服务器成功后,自动开始转码.
     *
     * @param videoItem
     */
    private void addVideoToDemoServer(final VideoItem videoItem) {
        transCodeQueryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (videoItem.getType() == UploadType.SHORT_VIDEO) {
                    Log.d("asdasdsd11","vid::"+videoItem.getVid()+"");
                    ViseHttp.POST(NetConfig.videoInfoUpload)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoInfoUpload))
                            .addParam("vname",videoItem.getVideoFaBuName())
                            .addParam("vid",videoItem.getVid()+"")
                            .addParam("uid",spImp.getUID())
                            .addParam("address",videoItem.getVideoAddress())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.d("asdasdsd11",data);
                                    JSONObject jsonObject = JSONObject.parseObject(data);
                                    int resCode = jsonObject.getIntValue("code");
                                    if(resCode == 200) {
                                        Toast.makeText(UploadService.this,"发布成功",Toast.LENGTH_SHORT).show();
                                        AddVideoResponseEntity entity = new AddVideoResponseEntity();
                                        JSONObject videoInfoJson = jsonObject.getJSONObject("obj").getJSONObject("ret");
                                        VideoInfoEntity videoInfoEntity = new VideoInfoEntity();
                                        videoInfoEntity.setCreateTime(videoInfoJson.getLongValue("createTime"));
                                        videoInfoEntity.setOrigUrl(videoInfoJson.getString("origUrl"));
                                        videoInfoEntity.setSnapshotUrl(videoInfoJson.getString("snapshotUrl"));
                                        Log.d("asdasdsd ;code == 200","//00//  "+"\n"+data+"\n"+videoInfoEntity.getSnapshotUrl()+"//////"+videoInfoJson.getString("snapshotUrl"));
                                        videoInfoEntity.setUpdateTime(videoInfoJson.getLongValue("updateTime"));
                                        videoInfoEntity.setVid(videoInfoJson.getLongValue("vid"));
                                        videoInfoEntity.setTypeName(videoInfoJson.getString("typeName"));
                                        videoInfoEntity.setVideoName(videoInfoJson.getString("videoName"));
                                        videoInfoEntity.setDuration(videoInfoJson.getLong("duration"));
                                        videoInfoEntity.setInitialSize(videoInfoJson.getLong("initialSize"));
                                        entity.setVideoInfoEntity(videoInfoEntity);

                                        UploadDbHelper.removeItemFromDb(videoItem);
                                        UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().remove(videoItem);
                                        UploadTotalDataAccessor.getInstance().uploadSuccess(videoItem);

                                        controller.onAddVideoResult(200, videoItem.getId(), entity);
                                    }else {
                                        Toast.makeText(UploadService.this,"发布失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
//                    ViseHttp.POST(NetConfig.videoInfo)
//                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.videoInfo))
//                            .addParam("vid", videoItem.getVid()+"")
//                            .request(new ACallback<String>() {
//                                @Override
//                                public void onSuccess(String data) {
//                                    Log.d("asdasdsd11",data);
//                                    JSONObject jsonObject = JSONObject.parseObject(data);
//                                    int resCode = jsonObject.getIntValue("code");
//                                    if(resCode == 200) {
//                                        AddVideoResponseEntity entity = new AddVideoResponseEntity();
//                                        JSONObject videoInfoJson = jsonObject.getJSONObject("obj").getJSONObject("ret");
//                                        VideoInfoEntity videoInfoEntity = new VideoInfoEntity();
//                                        videoInfoEntity.setCreateTime(videoInfoJson.getLongValue("createTime"));
//                                        videoInfoEntity.setOrigUrl(videoInfoJson.getString("origUrl"));
//                                        videoInfoEntity.setSnapshotUrl(videoInfoJson.getString("snapshotUrl"));
//                                        Log.d("asdasdsd ;code == 200","//00//  "+"\n"+data+"\n"+videoInfoEntity.getSnapshotUrl()+"//////"+videoInfoJson.getString("snapshotUrl"));
//                                        videoInfoEntity.setUpdateTime(videoInfoJson.getLongValue("updateTime"));
//                                        videoInfoEntity.setVid(videoInfoJson.getLongValue("vid"));
//                                        videoInfoEntity.setTypeName(videoInfoJson.getString("typeName"));
//                                        videoInfoEntity.setVideoName(videoInfoJson.getString("videoName"));
//                                        videoInfoEntity.setDuration(videoInfoJson.getLong("duration"));
//                                        videoInfoEntity.setInitialSize(videoInfoJson.getLong("initialSize"));
//                                        entity.setVideoInfoEntity(videoInfoEntity);
//                                        upload2TongBan(videoItem,videoInfoEntity);
//                                        UploadDbHelper.removeItemFromDb(videoItem);
//                                        UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().remove(videoItem);
//                                        UploadTotalDataAccessor.getInstance().uploadSuccess(videoItem);
//
//                                        controller.onAddVideoResult(200, videoItem.getId(), entity);
//                                    }
//                                }
//
//                                @Override
//                                public void onFail(int errCode, String errMsg) {
//                                    controller.onAddVideoResult(errCode, videoItem.getId(), null);
//                                }
//                            });
                    /**
                     *
                     */
//                    不使用demo的上传方法！！！
//                    DemoServerHttpClient.getInstance().addVideo(videoItem.getVid(), videoItem.getDisplayName(), 1, new DemoServerHttpClient.DemoServerHttpCallback<AddVideoResponseEntity>() {
//                        @Override
//                        public void onSuccess(AddVideoResponseEntity addVideoResponseEntity) {
//                            UploadDbHelper.removeItemFromDb(videoItem);
//                            UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().remove(videoItem);
//                            UploadTotalDataAccessor.getInstance().uploadSuccess(videoItem);
//
//                            controller.onAddVideoResult(200, videoItem.getId(), addVideoResponseEntity);
//                        }
//                        @Override
//                        public void onFailed(int code, String errorMsg) {
//                            Log.d("asdasdsd;;"+UploadType.SHORT_VIDEO,code+"||||"+errorMsg);
////                            controller.onAddVideoResult(code, videoItem.getId(), null);
//                            //假装走了上传接口成功
//                            controller.onAddVideoResult(200, videoItem.getId(), null);
//                        }
//                    });
                } else {
                    DemoServerHttpClient.getInstance().addVideo(videoItem.getVid(), videoItem.getDisplayName(), 0, new DemoServerHttpClient.DemoServerHttpCallback<AddVideoResponseEntity>() {
                        @Override
                        public void onSuccess(AddVideoResponseEntity addVideoResponseEntity) {
                            if (addVideoResponseEntity.isTransjobstatus()) {
                                controller.onReceiveUploadCallback(videoItem.getId(), 100, STATE_TRANSCODEING);
                                videoItem.setState(STATE_TRANSCODEING);
                                videoItem.setVid(addVideoResponseEntity.getVideoInfoEntity().getVid());
                                // 1、上传成功
                                // 2、添加到转码
                                UploadTotalDataAccessor.getInstance().uploadSuccess(videoItem);
                                controller.getDataAccessor().addTransCodingList(videoItem);
                                controller.startTranscodeQuery();
                            } else {
                                //服务端发起转码请求失败
                                controller.onReceiveUploadCallback(videoItem.getId(), 100, STATE_TRANSCODE_FAIL);
                                videoItem.setState(STATE_TRANSCODE_FAIL);
                            }
                            UploadTotalDataAccessor.getInstance().getWaitAddToServerItems().remove(videoItem);
                            UploadDbHelper.removeItemFromDb(videoItem);
                            UploadController.getInstance().getDataAccessor().localAddToCloud(videoItem);
                            controller.onAddVideoResult(200, videoItem.getId(), addVideoResponseEntity);
                        }

                        @Override
                        public void onFailed(int code, String errorMsg) {
                            controller.onAddVideoResult(code, videoItem.getId(), null);
                        }
                    });
                }

            }
        });
    }

    private void upload2TongBan(VideoItem videoItem,VideoInfoEntity videoInfoEntity) {
        ViseHttp.POST(NetConfig.upLoadVideo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.upLoadVideo))
                .addParam("userID", spImp.getUID())
                .addParam("vname",videoItem.getVideoFaBuName())
                .addParam("vurl",videoInfoEntity.getOrigUrl())
                .addParam("img",videoInfoEntity.getSnapshotUrl())
                .addParam("wy_vid",videoInfoEntity.getVid()+"")
                .addParam("address",videoItem.getVideoAddress())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Toast.makeText(UploadService.this,"发布成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UploadService.this,"发布失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    /**
     * 当转码等待队列中有任务时,开启60S一次的轮询
     */
    public void transcodeStatePolling() {
        if (controller.getDataAccessor().getTranscodingItems() != null
                && controller.getDataAccessor().getTranscodingItems().size() > 0) {
            transcode_thread_state = TRANSCODE_THREAD_STATE_WORKING;
            transCodeQueryHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startQueryTransCodeState();
                }
            }, POLLING_INTERVAL);
        } else {
            transcode_thread_state = TRANSCODE_THREAD_STATE_WAIT;
        }
    }

    /**
     * 查询转码状态轮询
     */
    private void startQueryTransCodeState() {
        transCodeQueryHandler.post(new Runnable() {
            @Override
            public void run() {
                DemoServerHttpClient.getInstance().videoTransCodeStatus(
                        controller.getDataAccessor().getTranscodingVidList(), new DemoServerHttpClient.DemoServerHttpCallback<List<TranscodeResponseEntity>>() {
                            @Override
                            public void onSuccess(List<TranscodeResponseEntity> transcodeResponseEntities) {
                                Log.d(TAG, transcodeResponseEntities.toString());

                                for (int i = 0; i < transcodeResponseEntities.size(); i++) {
                                    TranscodeResponseEntity entity = transcodeResponseEntities.get(i);
                                    switch (entity.getTranscodestatus()) {
                                        case TranscodeType.TRASNCODE_SUCCESS: //转码成功
                                        {
                                            VideoItem videoItem = controller.getDataAccessor().removeTranscodeItemByVid(entity.getVid());
                                            if (videoItem != null) {
                                                videoItem.setState(STATE_TRANSCODE_COMPLETE);
                                                controller.onReceiveUploadCallback(videoItem.getId(), 100, STATE_TRANSCODE_COMPLETE);
                                            }
                                        }
                                        break;
                                        case TranscodeType.TRANSCODING: //转码中
                                            break;
                                        case TranscodeType.TRANSCODE_FAILED: //转码失败
                                        {
                                            VideoItem videoItem = controller.getDataAccessor().removeTranscodeItemByVid(entity.getVid());
                                            if (videoItem != null) {
                                                videoItem.setState(STATE_TRANSCODE_FAIL);
                                                controller.onReceiveUploadCallback(videoItem.getId(), 100, STATE_TRANSCODE_FAIL);
                                            }
                                            break;
                                        }
                                    }
                                }
                                //必须放在返回回调, 更新状态后,确认仍在转码,才开启轮询
                                transcodeStatePolling();
                            }

                            @Override
                            public void onFailed(int code, String errorMsg) {
                                transcodeStatePolling();
                            }
                        }
                );
            }
        });

    }

    private int mNotificationId = 100;

    private void showNotification(String content, boolean autoCancel) {

        if (!show_nofitycation) return;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("上传视频")
                        .setContentText(content)
                        .setTicker(content)
                        .setAutoCancel(autoCancel);

        Intent intent = new Intent(this, CreateFriendRememberActivityChoosePicOrVideos.class);
        intent.putExtra(CreateFriendRememberActivityChoosePicOrVideos.EXTRA_FROM_UPLOAD_NOTIFY, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }


    private String appKey;

    private String getAppKey() {
        if (appKey == null) {
            appKey = readAppKey();
        }
        return appKey;
    }

    private String readAppKey() {

        try {
            ApplicationInfo appInfo = DemoCache.getContext().getPackageManager().getApplicationInfo(DemoCache.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }
}
