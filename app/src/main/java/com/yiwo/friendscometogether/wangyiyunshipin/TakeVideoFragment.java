package com.yiwo.friendscometogether.wangyiyunshipin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.netease.transcoding.record.MediaRecord;
import com.netease.transcoding.record.MessageHandler;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.create_friendremember.PicsMuLuAdapter;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Created by ljc on 2019/5/29.
 */

public class TakeVideoFragment extends BaseFragment implements MessageHandler {

    private static final String TAG = "LiveStreamingActivity";
    private DateFormat formatter_file_name = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
    //Demo控件
    private ImageView mRecordBtn;
    //视频缩放相关变量
    private int mMaxZoomValue = 0;
    private int mCurrentZoomValue = 0;
    private float mCurrentDistance;
    private float mLastDistance = -1;

    //伴音广播
    private audioMixVolumeMsgReceiver audioMixVolumeMsgReceiver;

    /**
     * SDK 相关参数
     **/
    private MediaRecord mMediaRecord = null;
    private volatile boolean mRecording = false;

    //第三方滤镜
//    private FuVideoEffect mFuEffect; //FU的滤镜
//    private Effect mSenseEffect; //商汤的滤镜

    long clickTime = 0L;

    //滤镜 pop
    private PopupWindow popupWindow;
    private  Video_lvjing_Adapter lvjing_adapter;
    private List<LvJingMode> datas = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_video, null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        //应用运行时，保持屏幕高亮，不锁屏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getActivity().getWindow().setAttributes(params);

        //以下为SDK调用主要步骤，请用户参考使用
        //1、创建录制实例
        MediaRecord.MediaRecordPara mediaRecordPara = new MediaRecord.MediaRecordPara();
        mediaRecordPara.setAppKey("dc4285450493b9851169ad13a64b4cd8");  //APPKEY
        mediaRecordPara.setContext(getContext()); //APP上下文
        mediaRecordPara.setMessageHandler(this); //消息回调
        mMediaRecord = new MediaRecord(mediaRecordPara);

        boolean frontCamera = true; // 是否前置摄像头
        boolean scale_16x9 = false; //是否强制16:9

        //设置第三方滤镜，需要在开启相机前设置，即在startVideoPreview 前设置
//        fuLiveEffect(); //FU滤镜
//        senseEffect();  //商汤滤镜

        //麦克风采集回调，需在startRecord之前设置
//        audioEffect();

        //2、 预览参数设置
        NeteaseView videoView = (NeteaseView) view.findViewById(R.id.videoview);

        MediaRecord.VideoQuality videoQuality = MediaRecord.VideoQuality.SUPER_HIGH; //视频模板（SUPER_HIGH 1280*720、SUPER 960*540、HIGH 640*480、MEDIUM 480*360）
        mMediaRecord.startVideoPreview(videoView, frontCamera, videoQuality, scale_16x9);
        mMediaRecord.setBeautyLevel(5); //磨皮强度为5,共5档，0为关闭
        mMediaRecord.setFilterStrength(0.5f); //滤镜强度
        mMediaRecord.setFilterType(VideoEffect.FilterType.clean);

        // SDK 默认提供 /** 标清 480*360 */MEDIUM, /** 高清 640*480 */HIGH,
        // /** 超清 960*540 */SUPER,/** 超高清 (1280*720) */SUPER_HIGH  五个模板，
        // 用户如果需要自定义分辨率可以调用startVideoPreviewEx 接口并参考以下参数
        // 码率计算参考公式为 bitrate = width * height * fps * 11 /100;
//		MediaRecord.VideoPara para = new MediaRecord.VideoPara();
//		para.setHeight(720);
//		para.setWidth(1280);
//		para.setFps(30);
//		para.setBitrate(1200 * 720 * 30 * 11 /100);
//		mMediaRecord.startVideoPreviewEx(videoView,frontCamera,useFilter,para);


        //Demo控件的初始化（Demo层实现，用户不需要添加该操作）
        UIInit(view);

        audioMixVolumeMsgReceiver = new audioMixVolumeMsgReceiver();
        IntentFilter audioMixVolumeIntentFilter = new IntentFilter();
        audioMixVolumeIntentFilter.addAction("AudioMixVolume");
        audioMixVolumeIntentFilter.addAction("AudioMix");
        getContext().registerReceiver(audioMixVolumeMsgReceiver, audioMixVolumeIntentFilter);
        initLvjingData();
        return view;
    }

    private void initLvjingData() {
        datas.add(new LvJingMode("怀旧",VideoEffect.FilterType.brooklyn));
        datas.add(new LvJingMode("干净",VideoEffect.FilterType.clean));
        datas.add(new LvJingMode("自然",VideoEffect.FilterType.nature));
        datas.add(new LvJingMode("健康",VideoEffect.FilterType.healthy));
        datas.add(new LvJingMode("复古",VideoEffect.FilterType.pixar));
        datas.add(new LvJingMode("温柔",VideoEffect.FilterType.tender));
        datas.add(new LvJingMode("美白",VideoEffect.FilterType.whiten));
    }

    @Override
    public void onDestroyView() {
        if(mMediaRecord != null){
            if(mRecording){
                mMediaRecord.stopRecord();
            }
            mMediaRecord.stopVideoPreview();
//            //消耗第三方滤镜
//            releaseFuEffect();
//            releaseSenseEffect();

            mMediaRecord.destroyVideoPreview();
            mMediaRecord.unInit();
        }
        getContext().unregisterReceiver(audioMixVolumeMsgReceiver);
        super.onDestroyView();
    }

    //按钮初始化
    private void UIInit(View view) {

        //开始直播按钮初始化
        mRecordBtn = (ImageView) view.findViewById(R.id.live_start_btn);
        mRecordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                if(time - clickTime < 1000){
                    return;
                }
                clickTime = time;
                mRecordBtn.setClickable(false);
                if (!mRecording) {
                    mMediaRecord.startRecord(Environment.getExternalStorageDirectory() + "/transcode/" + formatter_file_name.format(new Date()) + ".mp4");
                    mRecordBtn.setImageResource(R.mipmap.take_videos_stop);
                } else {
                    mMediaRecord.stopRecord();
                    mRecordBtn.setImageResource(R.mipmap.take_videos_restart);
                }
            }
        });

//        View capture = view.findViewById(R.id.live_capture_btn);
//        capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startCapture();
//            }
//        });

        //切换前后摄像头按钮初始化
        View switchBtn = view.findViewById(R.id.live_camera_btn);
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        View flashBtn = view.findViewById(R.id.live_flash_btn);
        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashCamera();
            }
        });

//        //伴音按钮初始化
//        View mix_audio_button = view.findViewById(R.id.live_music_btn);
//        mix_audio_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                showMixAudioDialog();
//            }
//        });

//        View change = view.findViewById(R.id.live_camera_change);
//        change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeFormat();
//            }
//        });
//
        View filterBtn = view.findViewById(R.id.live_filter_btn);
        filterBtn.setVisibility(View.GONE);

        //滤镜
        filterBtn.setVisibility(View.VISIBLE);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_pop(v);
            }
        });
//
//        View brooklyn = findViewById(R.id.brooklyn);
//        brooklyn.setOnClickListener(this);
//
//        View calm = findViewById(R.id.clean);
//        calm.setOnClickListener(this);
//
//        View nature = findViewById(R.id.nature);
//        nature.setOnClickListener(this);
//
//        View healthy = findViewById(R.id.healthy);
//        healthy.setOnClickListener(this);
//
//        View pixar = findViewById(R.id.pixar);
//        pixar.setOnClickListener(this);
//
//        View tender = findViewById(R.id.tender);
//        tender.setOnClickListener(this);
//
//        View whiten = findViewById(R.id.whiten);
//        whiten.setOnClickListener(this);
//
//        SeekBar filterSeekBar = ((SeekBar) findViewById(R.id.live_filter_seekbar));
//        filterSeekBar.setVisibility(View.VISIBLE);
//        filterSeekBar.setProgress(50);
//        filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                if (mMediaRecord != null) {
//                    float param;
//                    param = (float) progress / 100;
//                    mMediaRecord.setFilterStrength(param);
//                }
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
//
//        SeekBar beautySeekBar = ((SeekBar) findViewById(R.id.live_beauty_seekbar));
//        beautySeekBar.setVisibility(View.VISIBLE);
//        beautySeekBar.setProgress(100);
//        beautySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (mMediaRecord != null) {
//                    int param;
//                    param = progress / 20;
//                    mMediaRecord.setBeautyLevel(param);
//                }
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
//
//        SeekBar SeekbarExposure = (SeekBar) findViewById(R.id.live_Exposure_seekbar);
//        SeekbarExposure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(mMediaRecord != null){
//                    int max = mMediaRecord.getMaxExposureCompensation();
//                    mMediaRecord.setExposureCompensation((progress-50) * max /50);
//                }
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });

    }


    private void showToast(String s){
        toToast(getContext(),s);
    }
    @Override
    public void handleMessage(int msg, Object object) {
        switch (msg) {
            case MSG_INIT_RECORD_VERIFY_ERROR:
                showToast("鉴权失败，请检查APPkey");
                getActivity().finish();
                break;
            case MSG_START_PREVIEW_FINISHED:
                Log.d(TAG,"开启预览成功");
                break;
            case MSG_START_CAMERA_ERROR:
                showToast("开启相机失败，请检查相机权限");
                getActivity().finish();
                break;
            case MSG_START_AUDIO_ERROR:
                showToast("开启录音失败，请检查麦克风权限");
                getActivity().finish();
                break;
            case MSG_START_RECORD_ERROR:
                showToast("开启录制失败");
                break;
            case MSG_START_RECORD_FINISHED:
                showToast("录制已开启");
                mRecording = true;
                mRecordBtn.setClickable(true);
                break;
            case MSG_STOP_RECORD_FINISHED:
                if(!(Boolean) object){
                    showToast("录制停止失败，删除录制文件");
                }else {
                    showToast("录制已停止");
                }
                mRecording = false;
                mRecordBtn.setClickable(true);
                break;
            case MSG_SWITCH_CAMERA_FINISHED:
                showToast("相机切换成功");
                break;
            case MSG_CAMERA_NOT_SUPPORT_FLASH:
                showToast("不支持闪光灯");
                break;
            case MSG_START_CAPTURE_FINISHED:
                final Bitmap bitmap = (Bitmap) object;
                new Thread() {
                    public void run(){
                        if(bitmap != null){
                            FileOutputStream outStream = null;
                            String screenShotFilePath = Environment.getExternalStorageDirectory() + "/transcode/" +
                                    formatter_file_name.format(new Date()) + "_" +
                                    bitmap.getWidth() + "x" + bitmap.getHeight() +
                                    ".png";
                            try {
                                outStream = new FileOutputStream(String.format(screenShotFilePath));
                                bitmap.compress(Bitmap.CompressFormat.PNG,100,outStream);
                                showToast("截图已保存到：" + screenShotFilePath);
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                if(outStream != null){
                                    try {
                                        outStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }.start();

                break;
            default:
                break;
        }
    }
    //切换前后摄像头
    private void switchCamera() {
        if (mMediaRecord != null) {
            mMediaRecord.switchCamera();
        }
    }
    private boolean mFlashOn = false;
    private void flashCamera(){
        if(mMediaRecord != null){
            mFlashOn = !mFlashOn;
            mMediaRecord.setCameraFlashPara(mFlashOn);
        }
    }
    //弹出滤镜类型
    private void show_pop(View view_positon){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_video_lvjing, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView rv = view.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(manager);
        lvjing_adapter = new Video_lvjing_Adapter(datas);
        lvjing_adapter.setListenner(new Video_lvjing_Adapter.OnItemClickListenner() {
            @Override
            public void onClick(int postion) {
                mMediaRecord.setFilterType(datas.get(postion).getType());
            }
        });
        rv.setAdapter(lvjing_adapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(view_positon);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {

            }
        });
    }
    //用于接收Service发送的消息，伴音音量
    public class audioMixVolumeMsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //音量
            float audioMixVolumeMsg = intent.getFloatExtra("AudioMixVolumeMSG", -1);
            if(audioMixVolumeMsg != -1 && mMediaRecord != null){
                mMediaRecord.setMusicVolume(audioMixVolumeMsg);
            }

            //伴音文件
            int audioMixMsg = intent.getIntExtra("AudioMixMSG", 0);
            String fileName = intent.getStringExtra("AudioMixFilePathMSG");

            //伴音开关的控制
            if(audioMixMsg == 1)
            {
                if(mMediaRecord != null) {
                    try{
                        AssetFileDescriptor descriptor = context.getAssets().openFd("mixAudio/"+ fileName);
                        FileDescriptor fd = descriptor.getFileDescriptor();
                        mMediaRecord.startPlayMusic(fd,descriptor.getStartOffset(),descriptor.getLength(),false);
                        mMediaRecord.setMusicVolume(0.2f);
//                        mMediaRecord.musicSeekTo(1000); //伴音seek
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            else if (audioMixMsg == 2)
            {
                if(mMediaRecord != null){
                    mMediaRecord.resumePlayMusic();
                }
            }
            else if(audioMixMsg == 3)
            {
                if(mMediaRecord != null){
                    mMediaRecord.pausePlayMusic();
                }
            }
            else if(audioMixMsg == 4)
            {
                if(mMediaRecord != null){
                    mMediaRecord.stopPlayMusic();
                }
            }
        }
    }
}
