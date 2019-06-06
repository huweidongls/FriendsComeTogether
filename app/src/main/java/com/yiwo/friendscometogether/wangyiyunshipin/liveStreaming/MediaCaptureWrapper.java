package com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming;

import android.content.Context;
import android.hardware.Camera;
import android.os.Looper;

import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 推流参数包装类
 * Created by zhukkun on 12/9/16.
 */
public class MediaCaptureWrapper {

    public static final int HAVE_AUDIO = 0;
    public static final int HAVE_VIDEO = 1;
    public static final int HAVE_AV = 2;

    public static final int CAMERA_POSITION_BACK = 0;
    public static final int CAMERA_POSITION_FRONT = 1;

    public static final int HD_WIDTH = 960;
    public static final int HD_HEIGHT = 540;
    public static final int SD_WIDTH = 640;
    public static final int SD_HEIGHT = 480;
    public static final int LD_WIDTH = 320;
    public static final int LD_HEIGHT = 240;

    private int mVideoPreviewWidth;
    private int mVideoPreviewHeight;

    private int mVideoDecodeWidth;
    private int mVideoDecodeHeight;

    private String mVideoResolution;
    private int outputStreamType = HAVE_AV;

    private lsMediaCapture mLSMediaCapture = null;
    private lsMediaCapture.LiveStreamingPara mLiveStreamingPara;

    public MediaCaptureWrapper(Context context, lsMessageHandler messageHandler, PublishParam param){

        mVideoResolution = param.definition;

        if(!param.openVideo){
            outputStreamType = HAVE_AUDIO;
        }


        if(mVideoResolution.equals("HD")) {
            //Demo高清档编码分辨率采用960 * 540.此处为设置预览分辨率,首先检测相机是否支持960*540预览,若不支持则预览分辨率设置为主流手机皆支持的1280*720分辨率
            if(CameraHelper.isSupportSize(HD_WIDTH, HD_HEIGHT)){//检测相机是否支持 960 * 540预览
                //高清档 预览分辨率960*540, 与编码分辨率960*540 大小一致
                mVideoPreviewWidth = HD_WIDTH;
                mVideoPreviewHeight = HD_HEIGHT;
            }else{
                //高清档 预览分辨率1280*720, 与编码分辨率960*540 比例一致
                mVideoPreviewWidth = 1280;
                mVideoPreviewHeight = 720;
            }
        }
        else if(mVideoResolution.equals("SD")){
            mVideoPreviewWidth = SD_WIDTH;
            mVideoPreviewHeight = SD_HEIGHT;
        }
        else {
            mVideoPreviewWidth = LD_WIDTH;
            mVideoPreviewHeight = LD_HEIGHT;
        }

        //如下是编码分辨率等信息的设置
        if(mVideoResolution.equals("HD")) {
            mVideoDecodeWidth = HD_WIDTH;
            mVideoDecodeHeight = HD_HEIGHT;
        }
        else if(mVideoResolution.equals("SD")) {
            mVideoDecodeWidth = SD_WIDTH;
            mVideoDecodeHeight = SD_HEIGHT;
        }
        else {
            mVideoDecodeWidth = LD_WIDTH;
            mVideoDecodeHeight = LD_HEIGHT;
        }

        //1、创建直播实例
        lsMediaCapture.LsMediaCapturePara lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
        lsMediaCapturePara.setContext(context.getApplicationContext()); //设置SDK上下文（建议使用ApplicationContext）
        lsMediaCapturePara.setMessageHandler(messageHandler); //设置SDK消息回调
        lsMediaCapturePara.setLogLevel(lsLogUtil.LogLevel.INFO); //日志级别
        lsMediaCapturePara.setUploadLog(true);//是否上传SDK日志
        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara);
        //2、设置直播参数
        paraSet();
    }

    //音视频参数设置
    public void paraSet(){

        mLiveStreamingPara = new lsMediaCapture.LiveStreamingPara();
        mLiveStreamingPara.setStreamType(isAudio() ? lsMediaCapture.StreamType.AUDIO : lsMediaCapture.StreamType.AV); // 推流类型 AV、AUDIO、VIDEO
        mLiveStreamingPara.setFormatType(lsMediaCapture.FormatType.RTMP); // 推流格式 RTMP、MP4、RTMP_AND_MP4
        mLiveStreamingPara.setRecordPath("/sdcard/media.flv");//formatType 为 MP4 或 RTMP_AND_MP4 时有效
        mLiveStreamingPara.setQosOn(true);
    }

    public lsMediaCapture.LiveStreamingPara getmLiveStreamingPara() {
        return mLiveStreamingPara;
    }

    public lsMediaCapture getmLSMediaCapture() {
        return mLSMediaCapture;
    }

    public boolean isVideo(){
        return outputStreamType == HAVE_AV || outputStreamType == HAVE_VIDEO;
    }

    public boolean isAudio(){
        return outputStreamType== HAVE_AUDIO;
    }

    public int getmVideoPreviewWidth() {
        return mVideoPreviewWidth;
    }

    public int getmVideoPreviewHeight() {
        return mVideoPreviewHeight;
    }

    public int getmVideoDecodeWidth(){
        return mVideoDecodeWidth;
    }

    public int getmVideoDecodeHeight(){
        return mVideoDecodeHeight;
    }

    public boolean getCameraPosition() {
        return true;
    }

    public boolean isHD() {
        return mVideoResolution.equals("HD");
    }

    public boolean isSD() {
        return mVideoResolution.equals("SD");
    }

    public boolean isLD() {
        return mVideoResolution.equals("LD");
    }

    public static class CameraHelper {

        public static CameraHelper instance;
        private static List<Camera.Size> backCameraSupportSize;
        private static List<Camera.Size> frontCameraSupportSize;

        //查询摄像头支持的采集分辨率信息相关变量
        private Thread mCameraThread;
        private Looper mCameraLooper;
        private int mCameraID = CAMERA_POSITION_BACK;//默认查询的是后置摄像头
        private Camera mCamera;

        private CameraHelper(){

        }

        public static CameraHelper getInstance(){
            if(instance == null){
                instance = new CameraHelper();
            }
            return instance;
        }

        //查询Android摄像头支持的采样分辨率相关方法（1）
        public void openCamera(final int cameraID) {
            final Semaphore lock = new Semaphore(0);
            final RuntimeException[] exception = new RuntimeException[1];
            mCameraThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    mCameraLooper = Looper.myLooper();
                    try {
                        mCamera = Camera.open(cameraID);
                    } catch (RuntimeException e) {
                        exception[0] = e;
                    } finally {
                        lock.release();
                        Looper.loop();
                    }
                }
            });
            mCameraThread.start();
            lock.acquireUninterruptibly();
        }

        //查询Android摄像头支持的采样分辨率相关方法（2）
        public void lockCamera() {
            try {
                mCamera.reconnect();
            } catch (Exception e) {
            }
        }

        //查询Android摄像头支持的采样分辨率相关方法（3）
        public void releaseCamera() {
            if (mCamera != null) {
                lockCamera();
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }

        //查询Android摄像头支持的采样分辨率相关方法（4）
        public List<Camera.Size> getCameraSupportSize(int cameraID) {
            openCamera(cameraID);
            if (mCamera != null) {
                Camera.Parameters param = mCamera.getParameters();
                List<Camera.Size> previewSizes = param.getSupportedPreviewSizes();
                releaseCamera();
                return previewSizes;
            }
            return null;
        }

        public static List<Camera.Size> staticGetCameraSupportSize(int mCameraID){
            return getInstance().getCameraSupportSize(mCameraID);
        }

        /**
         * 是否支持特定的预览尺寸
         * @param width
         * @param height
         * @return
         */
        public static boolean isSupportSize(int width, int height) {
            if(backCameraSupportSize == null){
                backCameraSupportSize = staticGetCameraSupportSize(CAMERA_POSITION_BACK);
            }
            if(frontCameraSupportSize == null){
                frontCameraSupportSize = staticGetCameraSupportSize(CAMERA_POSITION_FRONT);
            }

            boolean frontSupport= false, backSupport = false;

            for (int i = 0; i < backCameraSupportSize.size(); i++) {
                Camera.Size size = backCameraSupportSize.get(i);
                if(size.width == width && size.height == height){
                    backSupport = true;
                }
            }

            for (int i = 0; i < frontCameraSupportSize.size(); i++) {
                Camera.Size size = frontCameraSupportSize.get(i);
                if(size.width == width && size.height == height){
                    frontSupport = true;
                }
            }
            return backSupport && frontSupport;
        }
    }
}
