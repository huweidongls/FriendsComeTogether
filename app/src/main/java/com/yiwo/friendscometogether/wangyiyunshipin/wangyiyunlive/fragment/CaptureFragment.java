package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.BaseFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.CapturePreviewContract;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.CapturePreviewController;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.PublishParam;
import com.yiwo.friendscometogether.wangyiyunshipin.network.NetworkUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.widget.MixAudioLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhukkun on 1/5/17.
 * 直播采集推流Fragment
 */
public class CaptureFragment extends BaseFragment implements CapturePreviewContract.CapturePreviewUi{

    /**
     * Ui 基础控件
     */
    private ImageView btnFlash;
    private ImageView btnCancel;
    private ImageView btnAudio;
    private ImageView btnVideo;
    private ImageView btnCamSwitch;
    private Button btnStartLive;
    private View audioAnimate;
    private SeekBar focusSeekBar;
    private SeekBar filterSeekBar;

    MixAudioLayout mixAudioLayout = null; //伴音控制布局

    boolean canUse4GNetwork = false; //是否能使用4G网络进行直播

    /**
     * 滤镜模式的SurfaceView
     */
    private NeteaseView filterSurfaceView;

    /**
     * 普通模式的SurfaceView
     */
    private NeteaseView normalSurfaceView;

    /**
     * 控制器
     */
    CapturePreviewController controller;

    LiveRoomActivity liveActivity;

    LiveBottomBar liveBottomBar;

    private long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_capture, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        controller = getCaptureController();
        controller.handleIntent(getActivity().getIntent());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LiveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onPause();
    }

    /**
     * 初始化Ui
     */
    private void initView() {
        bindView();
        clickView();
    }

    /**
     * 绑定Ui控件
     */
    private void bindView() {
        btnFlash = (ImageView)findViewById(R.id.live_flash);
        btnCancel = (ImageView)findViewById(R.id.live_cancel);
        btnAudio = (ImageView)findViewById(R.id.live_audio_btn);
        btnVideo = (ImageView)findViewById(R.id.live_video_btn);
        btnCamSwitch = (ImageView)findViewById(R.id.live_camera_btn);
        focusSeekBar = (SeekBar)findViewById(R.id.live_seekbar_focus);
        filterSeekBar = (SeekBar)findViewById(R.id.live_seekbar_filter);
        btnStartLive = (Button) findViewById(R.id.btn_star_live);
        audioAnimate = findViewById(R.id.layout_audio_animate);
        filterSurfaceView = (NeteaseView) findViewById(R.id.live_filter_view);
        normalSurfaceView = (NeteaseView) findViewById(R.id.live_normal_view);
    }

    public void attachBottomBarToFragment(final LiveBottomBar liveBottomBar){

        this.liveBottomBar = liveBottomBar;

        liveBottomBar.setMusicClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mixAudioLayout == null){
                    mixAudioLayout = new MixAudioLayout(getContext());
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    liveBottomBar.addView(mixAudioLayout, layoutParams);
                }else{
                    mixAudioLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        liveBottomBar.setMsgClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                liveActivity.showInputPanel();
            }
        });

        liveBottomBar.setCaptureClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                controller.screenShot();
            }
        });

        liveBottomBar.getBeautyFilterBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveBottomBar.getNoFilterBtn().setSelected(false);
                liveBottomBar.getBeautyFilterBtn().setSelected(true);
                controller.switchFilterTo(VideoEffect.FilterType.brooklyn);
            }
        });

        liveBottomBar.getNoFilterBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveBottomBar.getNoFilterBtn().setSelected(true);
                liveBottomBar.getBeautyFilterBtn().setSelected(false);
                controller.switchFilterTo(VideoEffect.FilterType.none);
            }
        });

    }

    /**
     * 设置Ui点击事件
     */
    private void clickView() {

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchFlash();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchAudio();
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchVideo();
            }
        });

        btnStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetworkUtils.isNetworkConnected(true)) {
                    showToast("无网络,请检查网络设置后重新直播");
                    return;
                }
                if(!canUse4GNetwork && NetworkUtils.getNetworkType() == NetworkUtils.TYPE_MOBILE){
                    showConfirmDialog(null, "正在使用手机流量,是否开始直播?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            controller.liveStartStop();
                            controller.canUse4GNetwork(true);
                            canUse4GNetwork = true;
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else {
                    controller.liveStartStop();
                }
            }
        });

        btnCamSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastClickTime> 1000) {
                    controller.switchCam();
                    lastClickTime = currentTime;
                }
            }
        });

        focusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                controller.setZoom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                controller.setFilterStrength(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * 设置显示的SurfaceView
     * @param hasFilter 是否带滤镜功能
     */
    @Override
    public void setSurfaceView(boolean hasFilter) {
        if(hasFilter){
            filterSurfaceView.setVisibility(View.VISIBLE);
            normalSurfaceView.setVisibility(View.GONE);
        }else{
            normalSurfaceView.setVisibility(View.VISIBLE);
            filterSurfaceView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置预览画面 大小
     * @param hasFilter 是否有滤镜
     */
    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {
//        if(hasFilter){
//            filterSurfaceView.setPreviewSize(previewWidth, previewHeight);
//        }else{
//            normalSurfaceView.setPreviewSize(previewWidth, previewHeight);
//        }
    }

    /**
     * 获取正在显示的SurfaceView
     * @return
     */
    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        if(hasFilter){
            return filterSurfaceView;
        }else{
            return normalSurfaceView;
        }
    }

    /**
     * 设置直播开始按钮, 是否可点击
     * @param clickable
     */
    @Override
    public void setStartBtnClickable(boolean clickable) {
        btnStartLive.setClickable(clickable);
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {
        btnAudio.setVisibility(View.VISIBLE);
        btnStartLive.setVisibility(View.GONE);
        btnStartLive.setText("开始直播");
        if(liveActivity!=null) {
            liveActivity.onStartLivingFinished();
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {
        //btnRestart.setVisibility(View.GONE);
    }

    /**
     * 设置audio按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {
        if(isPlay){
            btnAudio.setImageResource(R.drawable.btn_audio_on_n);
        }else{
            btnAudio.setImageResource(R.drawable.btn_audio_off_n);
        }
    }

    /**
     * 设置Video按钮状态
     * @param isPlay 是否正在开启
     */
    @Override
    public void setVideoBtnState(boolean isPlay) {
        if(isPlay){
            btnVideo.setImageResource(R.drawable.btn_camera_on_n);
        }else{
            btnVideo.setImageResource(R.drawable.btn_camera_off_n);
        }
    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {
        if(visible){
            //fixme 如要显示滤镜强度进度条,解除注释
            //filterSeekBar.setVisibility(View.VISIBLE);
        }else{
            filterSeekBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {
        if(mPublishParam.openVideo){
            btnCamSwitch.setVisibility(View.VISIBLE);
            if(liveBottomBar!=null) {
                liveBottomBar.getFilterView().setVisibility(View.VISIBLE);
                liveBottomBar.getCaptureView().setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     *  获取Ui对应的controller
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }

    /**
     * 按下返回键
     */
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void showAudioAnimate(boolean b) {
        if(audioAnimate!=null){
            if(b){
                audioAnimate.setVisibility(View.VISIBLE);
            }else{
                audioAnimate.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDisconnect() {
        btnAudio.setVisibility(View.GONE);
        btnStartLive.setVisibility(View.VISIBLE);

        //liveActivity为空,则已关闭直播页面
        if(liveActivity!=null) {
            liveActivity.onLiveDisconnect();
            controller.liveStartStop();
        }
    }

    @Override
    public void normalFinish() {
        if(liveActivity!=null) {
            liveActivity.normalFinishLive();
        }
    }

    @Override
    public void onStartInit() {
        btnStartLive.setText("准备中...");
    }

    @Override
    public void onCameraPermissionError() {
        showConfirmDialog("无法打开相机", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, null);
    }

    @Override
    public void onAudioPermissionError() {
        showConfirmDialog("无法开启录音", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, null);
    }

    public void destroyController() {
        controller.tryToStopLivingStreaming();
        controller.onDestroy();
    }
}
