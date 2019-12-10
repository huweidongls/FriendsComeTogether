package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.BaseFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.LivePlayerController;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.NEVideoControlLayout;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.NEVideoView;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.PlayerContract;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.VideoConstant;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;


/**
 * Created by zhukkun on 1/5/17.
 * 直播观众拉流Fragment
 */
public class AudienceFragment extends BaseFragment implements PlayerContract.PlayerUi{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_audience, container, false);
    }

    public static final String EXTRA_URL = "extra_url";
    public static final String IS_LIVE = "is_live";
    public static final String IS_SOFT_DECODE = "is_soft_decode";

    private String mUrl; // 拉流地址

    private LiveRoomActivity liveActivity;

    /**
     * 视频展示SurfaceView
     */
    NEVideoView mVideoView;

    /**
     * 顶部View
     */
    View mBackView;

    /**
     * 缓冲View
     */
    View mLoadingView;

    /**
     * 纯音频动画
     */
    View audioAnimate;

    /**
     * 直播状态控制View
     */
    NEVideoControlLayout controlLayout;

    /**
     * 直播控制器
     */
    LivePlayerController mediaPlayController;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAudienceParam();
    }


    private void initView() {
        bindView();
        clickView();
    }

    private void bindView() {
        mVideoView = (NEVideoView) findViewById(R.id.video_view);
        mLoadingView = findViewById(R.id.buffering_prompt);
        mBackView = findViewById(R.id.player_exit);
        audioAnimate = findViewById(R.id.layout_audio_animate);
        controlLayout = new NEVideoControlLayout(getActivity());
    }

    private void clickView() {
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        controlLayout.setChangeVisibleListener(new NEVideoControlLayout.ChangeVisibleListener() {
            @Override
            public void onShown() {
                mVideoView.invalidate();
            }

            @Override
            public void onHidden() {

            }
        });
    }

    public void attachBottomBarToFragment(final LiveBottomBar liveBottomBar){
        liveBottomBar.setMsgClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                liveActivity.showInputPanel();
            }
        });

        liveBottomBar.setCaptureClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mediaPlayController.getSnapshot();
            }
        });

    }

    private void initAudienceParam() {
        Intent intent = getActivity().getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        boolean isLive = intent.getBooleanExtra(IS_LIVE, true);
        boolean isSoftDecode = intent.getBooleanExtra(IS_SOFT_DECODE, true);
        mediaPlayController = new LivePlayerController(getActivity(), this, mVideoView, null, mUrl, VideoConstant.VIDEO_SCALING_MODE_FILL_BLACK, isLive, !isSoftDecode);
        mediaPlayController.initVideo();
    }

    @Override
    public void onResume() {
        // 恢复播放
        if(mediaPlayController != null){
            mediaPlayController.onActivityResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //暂停播放
        if(mediaPlayController != null){
            mediaPlayController.onActivityPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 释放资源
        if(mediaPlayController != null){
            mediaPlayController.onActivityDestroy();
        }
        super.onDestroy();
    }


    /**
     *  显示视频推流结束
     */
    @Override
    public boolean onCompletion() {
        //由于设计了客户端重连机制,故认为源站发送的直播结束信号不可靠.转由云信SDK聊天室的直播状态来判断直播是否结束
        //此处收到直播完成时,进行重启直播处理
        showLoading(true);
        mediaPlayController.restart();
        return true;
    }

    @Override
    public boolean onError(final String errorInfo) {
        if(getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    liveActivity.onChatRoomFinished(errorInfo);
                }
            });
        }
        return true;
    }

    @Override
    public void setFileName(String name) {
        if(name!=null) {

        }
    }

    @Override
    public void showLoading(final boolean show) {
        if(getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (show) {
                        mLoadingView.setVisibility(View.VISIBLE);
                    } else {
                        mLoadingView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public void showAudioAnimate(final boolean show) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(audioAnimate!=null){
                        if(show) {
                            audioAnimate.setVisibility(View.VISIBLE);
                        }else{
                            audioAnimate.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBufferingUpdate() {
    }

    @Override
    public void onSeekComplete() {
    }

    public void stopWatching() {
        mediaPlayController.stopPlayback();
        showLoading(false);
    }
}
