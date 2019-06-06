package com.yiwo.friendscometogether.wangyiyunshipin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.NEVideoControlLayout;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.NEVideoView;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.PlayerContract;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.VideoConstant;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.VideoPlayerController;
import com.yiwo.friendscometogether.wangyiyunshipin.network.NetworkUtils;


/**
 * 拉流界面
 */
public class VideoPlayerActivity extends BaseActivity implements PlayerContract.PlayerUi{

    public final static String EXTRA_URL = "extra_url";

    private String mUrl; // 拉流地址

    /**
     * 视频展示SurfaceView
     */
    NEVideoView mVideoView;

    /**
     * 顶部View
     */
    RelativeLayout mPlayToolbar;
    TextView tv_title;
    View mBackView;

    /**
     * 缓冲View
     */
    View mLoadingView;

    /**
     * 直播状态控制View
     */
    NEVideoControlLayout controlLayout;

    /**
     * 直播控制器
     */
    VideoPlayerController mediaPlayController;


    /**
     * 静态方法 启动直播Activity
     * @param context 上下文
     * @param url 视频播放地址
     */
    public static void startActivity(final Context context, String url) {
        final Intent intent = new Intent();
        intent.setClass(context, VideoPlayerActivity.class);
        intent.putExtra(EXTRA_URL, url);
        DialogMaker.showProgressDialog(context, "加载中", false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                if(NetworkUtils.isNetworkConnected(true)){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                        }
                    });
                }else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "当前无网络,请检查网络后开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                DialogMaker.dismissProgressDialog();
            }
        }).start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAudienceParam();
        VcloudFileUtils.getInstance(getApplicationContext()).init();
    }

    @Override
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected int getContentView() {
        return R.layout.audience_activity;
    }

    @Override
    protected void initView() {
        bindView();
        clickView();
        mPlayToolbar.setVisibility(View.INVISIBLE);
    }

    private void bindView() {
        mVideoView = (NEVideoView) findViewById(R.id.video_view);
        mPlayToolbar = (RelativeLayout) findViewById(R.id.play_toolbar);
        mLoadingView = findViewById(R.id.buffering_prompt);
        mBackView = findViewById(R.id.player_exit);
        tv_title = (TextView)findViewById(R.id.file_name);
        controlLayout = new NEVideoControlLayout(this);
    }

    private void clickView() {
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        controlLayout.setChangeVisibleListener(new NEVideoControlLayout.ChangeVisibleListener() {
            @Override
            public void onShown() {
                mPlayToolbar.setVisibility(View.VISIBLE);
                mPlayToolbar.requestLayout();
                mVideoView.invalidate();
                mPlayToolbar.postInvalidate();
            }

            @Override
            public void onHidden() {
                mPlayToolbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initAudienceParam() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        mediaPlayController = new VideoPlayerController(VideoPlayerActivity.this, this, mVideoView, controlLayout, mUrl, VideoConstant.VIDEO_SCALING_MODE_NONE, false, false);
        mediaPlayController.initVideo();
    }

    @Override
    protected void onResume() {
        // 恢复播放
        if(mediaPlayController != null){
            mediaPlayController.onActivityResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //暂停播放
        if(mediaPlayController != null){
            mediaPlayController.onActivityPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
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
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("视频播放结束");
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });
            builder.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onError(final String errorInfo) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage(errorInfo);
                    builder.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    finish();
                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void setFileName(String name) {
        if(name!=null) {
            tv_title.setText(name);
        }
    }

    @Override
    public void showLoading(boolean show) {
        if(show) {
            mLoadingView.setVisibility(View.VISIBLE);
        }else{
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAudioAnimate(boolean b) {

    }


    @Override
    public void onBufferingUpdate() {
        //todo
    }

    @Override
    public void onSeekComplete() {
        //todo
    }

}
