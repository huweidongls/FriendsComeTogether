package com.yiwo.friendscometogether.wangyiyunshipin.upload.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.netease.nim.uikit.common.util.log.LogUtil;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.network.NetworkChangeBroadcastReceiver;
import com.yiwo.friendscometogether.wangyiyunshipin.network.NetworkUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.ShortVideoDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadDbHelper;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadTotalDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;

import java.util.List;


/**
 * Created by zhukkun on 2/22/17.
 */
public class UploadController extends BaseUiController<UploadController.UploadUi> {
    public static final String TAG = "uploadtest";

    private static UploadController instance;
    private UploadService.UploadBinder mBinder;
    boolean mBound = false;
    private int uploadState = UploadService.STATE_WAIT;

    private UploadDataAccessor dataAccessor;
    private ShortVideoDataAccessor shortVideoDataAccessor;
    private Handler handler;

    private AlertDialog MobileNetworkDialog; //移动网络的提示框
    private boolean needResumeUpload; //是否需要恢复上传
    private boolean allowMobileNetwork = false; //是否允许移动网络进行上传
    private boolean needToAlert = true;

    public static UploadController getInstance() {
        synchronized (UploadController.class) {
            if (instance == null) {
                instance = new UploadController();
            }
        }
        return instance;
    }

    NetworkChangeBroadcastReceiver receiver = new NetworkChangeBroadcastReceiver(new NetworkChangeBroadcastReceiver.NetworkChangeCallBack() {
        @Override
        public void onNetworkChanged(boolean connected, int type) {
            Log.d(TAG, "NeedResume:" + needResumeUpload + ",con:" + connected + ",type:" + type + ",allow mobile:" + allowMobileNetwork);
            if (needResumeUpload) {
                if (connected) {
                    startUploadIfAllow();
                }
            } else if (mBound && mBinder.isUploading() && !allowMobileNetwork && type == NetworkUtils.TYPE_MOBILE) {
                mBinder.stopUpload();
                needResumeUpload = true;
                Toast.makeText(mContext.getApplicationContext(), "已切换至蜂窝网络, 暂停上传", Toast.LENGTH_SHORT).show();
                startUploadIfAllow();
            }

        }
    });

    @Override
    protected void onUiAttached(UploadUi ui) {

    }

    @Override
    protected void populateUi(UploadUi ui) {
        ui.updateAllItems();
        ui.updateUploadState(uploadState);
    }

    @Override
    protected void onUiDetached(UploadUi ui) {
        if (mBound && uploadState == UploadService.STATE_UPLOADING) {
            //防止上传过程中,用户手动取消了通知 ,再次提醒
            mBinder.showUploadNotification();
        }
    }

    @Override
    public void handleIntent(Intent intent) {

    }

    @Override
    protected void onInit() {
        handler = new Handler(Looper.getMainLooper());

        receiver.registReceiver(mContext);

        if (dataAccessor == null) {
            dataAccessor = new UploadDataAccessor();
        }
        if (shortVideoDataAccessor == null) {
            shortVideoDataAccessor = new ShortVideoDataAccessor();
        }
    }

    @Override
    protected void onSuspend() {
        unbindService();
        receiver.unregist(mContext);
        if (shortVideoDataAccessor != null) {
            shortVideoDataAccessor.clear();
        }
        if (dataAccessor != null) {
            dataAccessor.clear();
        }
        shortVideoDataAccessor = null;
        dataAccessor = null;
    }

    /**
     * 上传本地数据
     *
     * @param videoItems
     */
    public void uploadLocalItem(List<VideoItem> videoItems, int type, boolean needSave) {
        Log.d("asdasdsd:",";;;uploadLocalItem");
        if (type == UploadType.SHORT_VIDEO) {
            shortVideoDataAccessor.addLocalItemList(videoItems, needSave);
        } else {
            dataAccessor.addLocalVideoItems(videoItems, needSave);
        }

        UploadTotalDataAccessor.getInstance().addUploadList(videoItems);

        startUploadIfAllow();
        notifyDataSetChanged(videoItems);
    }

    /**
     * 通知界面，数据集发生了变化
     *
     * @param videoItems
     */
    public void notifyDataSetChanged(List<VideoItem> videoItems) {
        if (mUi != null) {
            mUi.onDataSetChanged(videoItems);
        }
    }

    /**
     * 本地未上传的数据
     */
    public void loadVideoDataFromLocal(final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<VideoItem> items = UploadDbHelper.queryUploadListFromDbByType(type);
                if (items.size() > 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            uploadLocalItem(items, type, false);
                        }
                    });
                }
            }
        }).start();
    }

    public void startUploadIfAllow() {
        Log.d("asdasdsd:",";;;startUploadIfAllow");
        if (!UploadTotalDataAccessor.getInstance().isFullySuccess() && UploadTotalDataAccessor.getInstance().hasWaitTask()) {

            if (!allowMobileNetwork && NetworkUtils.getNetworkType() == NetworkUtils.TYPE_MOBILE) {

                if (!needToAlert) return;

                needResumeUpload = true;
                try {
                    //Context传入DemoCache.getVisibleActivity(), 则在其他页面也可弹窗提示
                    AlertDialog.Builder builder = new AlertDialog.Builder(DemoCache.getVisibleActivity() == null ? mContext : DemoCache.getVisibleActivity());
                    builder.setMessage("正在使用手机流量上传, 是否继续?");
                    builder.setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allowMobileNetwork = true;
                                    startUpload();
                                }
                            });
                    builder.setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //若提示且用户不上传, 则依赖后续网络切换至WIFI自动上传
                                    allowMobileNetwork = false;
                                    needToAlert = false;
                                    Toast.makeText(mContext.getApplicationContext(), "待连接至WIFI网络后,继续上传", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                }
                            });
                    builder.setCancelable(false);

                    if (MobileNetworkDialog == null || !MobileNetworkDialog.isShowing()) {
                        MobileNetworkDialog = builder.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                LogUtil.i(TAG, "start upload if allowed");
                startUpload();

                //4G弹窗时, 切换到WIFI的情况
                if (MobileNetworkDialog != null && MobileNetworkDialog.isShowing()) {
                    MobileNetworkDialog.dismiss();
                    Toast.makeText(mContext.getApplicationContext(), "已连接至WIFI网络, 继续上传", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startUpload() {
        Log.d("asdasdsd:",";;;startUpload");
        if (!mBound) {
            LogUtil.i(TAG, "bind service");
            bindService();
        } else {
            LogUtil.i(TAG, "binder.startUpload");
            Log.d("asdasdsd:",";;;binder.startUpload");
            mBinder.startUpload();
        }
        needResumeUpload = false;
    }

    /**
     * 开始转码查询
     */
    public void startTranscodeQuery() {
        if (!mBound) {
            bindService();
        } else {
            mBinder.startTransQuery();
        }
    }

    private void bindService() {
        Log.d("asdasdsd:",";;;bindService");
        Intent intent = new Intent(mContext, UploadService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        if (mBound) {
            mContext.unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (UploadService.UploadBinder) service;
            mBinder.attachController(UploadController.this);
            mBinder.startTransQuery();
            mBound = true;
            //由于有两处绑定server的入口,所以绑定成功后,再次判断网络后,再开启上传任务
            startUploadIfAllow();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void onReceiveUploadCallback(final String id, final int progress, final int state) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (state == UploadService.STATE_UPLOAD_FAIL && (receiver.justNetworkChanged() || !NetworkUtils.isNetworkConnected(false))) {
                    //进到此分支,为网络中断造成失败,故停止上传,避免后续任务继续失败
                    if (mBound) {
                        mBinder.stopUpload();
                    }
                    needResumeUpload = true;

                    if (!NetworkUtils.isNetworkConnected(false)) {
                        Toast.makeText(mContext.getApplicationContext(), "网络异常, 恢复后将继续上传", Toast.LENGTH_SHORT).show();
                    } else if (NetworkUtils.getNetworkType() == NetworkUtils.TYPE_MOBILE) {
                        Toast.makeText(mContext.getApplicationContext(), "已切换至蜂窝网络, 暂停上传", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (mUi != null) {
                        mUi.updateItemProgress(id, progress, state);
                    }
                }
            }
        });
    }

    public void onUploadStateChanged(final int state) {
        uploadState = state;

        final UploadUi ui = mUi;
        if (ui != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ui.updateUploadState(state);
                }
            });
        }
    }


    public void onAddVideoResult(final int code, final String id, final AddVideoResponseEntity addVideoResponseEntity) {
        if (mUi != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mUi.onAddVideoResult(code, id, addVideoResponseEntity);
                }
            });
        }
    }

    public void deleteUploadItem(VideoItem videoItem) {
        if (mBound && mBinder != null) {
            mBinder.deleteUploadItem(videoItem);
        }
        //由于可能存在4G网络下未绑定上传服务的情况, 所以删除数据需要放在条件语句外
        dataAccessor.removeItem(videoItem);
    }

    public UploadDataAccessor getDataAccessor() {
        return dataAccessor;
    }

    public interface UploadUi extends Ui {

        void updateAllItems();

        void updateUploadState(int state);

        void updateItemProgress(String id, int progress, int state);

        Context getContext();

        void onDataSetChanged(List<VideoItem> data);

        void onAddVideoResult(int code, String id, AddVideoResponseEntity addVideoResponseEntity);
    }

}
