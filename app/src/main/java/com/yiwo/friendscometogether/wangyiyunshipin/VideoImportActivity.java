package com.yiwo.friendscometogether.wangyiyunshipin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.transcoding.TranscodingAPI;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.liveplayer.NEVideoView;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.adapter.ShortVideoGalleryAdapter;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.adapter.ThumbAdapter;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.model.MediaCaptureOptions;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.videoprocess.VideoProcessController;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.videoprocess.model.VideoProcessOptions;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoGalleryDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;
import com.yiwo.friendscometogether.wangyiyunshipin.utils.SnapShotHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 导入视频
 * Created by hzxuwen on 2017/6/13.
 */

public class VideoImportActivity extends BaseActivity implements ShortVideoGalleryAdapter.GalleryUiListener, View.OnClickListener, VideoProcessController.VideoProcessCallback {
    public static final int EXTRA_REQUEST_CODE = 1001;
    public static final String EXTRA_FILTER_TIME = "filter_time";
    public static final String EXTRA_OUTPUT_PATH = "output_file";
    private static final int DEFAULT_FRAMES = 4;
    private static final int DEFAULT_FRAME_WIDTH = ScreenUtil.dip2px(40);
    // view
    private ViewGroup titleBar;
    private RecyclerView recyclerView;
    private NEVideoView videoView; // 预览画面
    private MediaPlayer mediaPlayer;
    private TextView startTimeText; // 裁剪起始时间
    private TextView borderText; // 裁剪时间框
    private RelativeLayout thumbLayout; // 缩略图布局
    // 下面裁剪布局
    private RecyclerView thumbRecyclerView; // 缩略图列表
    private ImageView finishClipBtn; // 返回视频列表按钮
    private ImageView doneBtn; // 完成裁剪按钮
    // data
    private VideoProcessController videoProcessController;
    private ShortVideoGalleryAdapter adapter;
    private VideoGalleryDataAccessor dataAccessor;
    private long filterTime;
    private ThumbAdapter thumbAdapter;
    private List<Bitmap> thumbItems;
    private float startTime;
    private int dxTotal = 0;
    private float perPixelTime; // 每个像素的时长
    private int totalFrames; // 总帧数
    private VideoItem videoItem;
    private String outputPath; // 裁剪后输出的文件地址

    public static void startActivityForResult(Context context, long filterTime, String path) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILTER_TIME, filterTime);
        intent.putExtra(EXTRA_OUTPUT_PATH, path);
        intent.setClass(context, VideoImportActivity.class);
        ((Activity) context).startActivityForResult(intent, EXTRA_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        videoProcessController = new VideoProcessController(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    protected void handleIntent(Intent intent) {
        filterTime = intent.getLongExtra(EXTRA_FILTER_TIME, -1);
        outputPath = intent.getStringExtra(EXTRA_OUTPUT_PATH);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_import;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.title_bar);
        TextView title = findView(R.id.tv_title);
        ImageView back = findView(R.id.iv_back);

        title.setText("选择视频");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initVideoView();

        initRecyclerView();

        initClipView();
    }

    /**
     * 初始化预览画面
     */
    private void initVideoView() {
        videoView = findView(R.id.video_view);
    }

    private void initMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            videoView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            videoView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setDisplay(videoView.getHolder());
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        }
    }

    /**
     * 初始化视频选择列表
     */
    private void initRecyclerView(){
        recyclerView = findView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAccessor = new VideoGalleryDataAccessor();
        dataAccessor.loadPhoneVideoData(this);
        dataAccessor.filterVideoByTime(filterTime);
//        dataAccessor.filterVideoByResolution(352, 288);
        dataAccessor.sortByDate();
        recyclerView.setAdapter(adapter = new ShortVideoGalleryAdapter(this, dataAccessor, this));
    }

    /**
     * 初始化下方裁剪截图预览布局
     */
    private void initClipView() {
        startTimeText = findView(R.id.start_time_Text);
        thumbLayout = findView(R.id.thumb_layout);
        borderText = findView(R.id.border_text);
        borderText.setText(TimeUtil.secondToTime((int) filterTime));
        finishClipBtn = findView(R.id.finish_clip_btn);
        doneBtn = findView(R.id.done_btn);

        finishClipBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);

        thumbRecyclerView = findView(R.id.thumb_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        thumbRecyclerView.setLayoutManager(linearLayoutManager);
        thumbItems = new ArrayList<>();
        thumbAdapter = new ThumbAdapter(this, thumbItems);
        thumbRecyclerView.setAdapter(thumbAdapter);
        setHeader(thumbRecyclerView);
        setFooter(thumbRecyclerView);
        thumbRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                dxTotal += dx;
                startTime = dxTotal * perPixelTime;
                startTimeText.setText(TimeUtil.secondToTime((int) startTime));
            }
        });
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.video_thumb_header, view, false);
        thumbAdapter.setHeaderView(header);
    }

    private void setFooter(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.video_thumb_header, view, false);
        thumbAdapter.setFooterView(footer);
    }

    private void loadData() {
        adapter.setDatas(dataAccessor.getLineContainerList());
        adapter.notifyDataSetChanged();
    }


    /**
     * ************* GalleryUiListener ****************
     */

    @Override
    public void onItemClick(VideoItem videoItem) {
        titleBar.setVisibility(View.GONE);
        this.videoItem = videoItem;

        // 选中视频，切换成视频剪切界面
        videoView.setVisibility(View.VISIBLE);
        thumbLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        initMediaPlayer();
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPlay();
            }
        }, 500);
        startSnapShot();
    }

    private void startPlay() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(videoItem.getFilePath());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
            videoView.setVisibility(View.GONE);
            thumbLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            titleBar.setVisibility(View.VISIBLE);
        }
    }

    private void startSnapShot() {
        long duration = TimeUtil.formatTimeToTime(videoItem.getDuration());
        // DEFAULT_FRAMES / filterTime = totalFrames / duration
        totalFrames = (int) Math.ceil((float) (DEFAULT_FRAMES * duration) / filterTime);
        float interval = (float) duration / totalFrames / 1000;
        float totalFrameWidth;
        totalFrameWidth = DEFAULT_FRAME_WIDTH * totalFrames;
        // totalFrameWidth - ScreenUtil.dip2px(158)表示可移动的距离
        perPixelTime = (duration) / (totalFrameWidth - DEFAULT_FRAME_WIDTH);

        SnapShotHelper.getInstance().snapshoot(videoItem.getFilePath(), 0, interval * 1000, duration,
                240, 320,
                new SnapShotHelper.SnapShotCallback() {
                    @Override
                    public void onSnapShotSuccess(List<Bitmap> snaps) {
                        if (snaps != null) {
                            thumbRecyclerView.setVisibility(View.VISIBLE);
                            thumbItems.clear();
                            thumbItems.addAll(snaps);
                            thumbAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    HashMap<String, String> headers;

    /**
     * 完成裁剪时间选择
     */
    private void finishCutFile() {
        DialogMaker.showProgressDialog(this, "视频载入中");
        MediaCaptureOptions mediaCaptureOptions = new MediaCaptureOptions();
        mediaCaptureOptions.mFilePath = videoItem.getFilePath();
        VideoProcessOptions videoProcessOptions = new VideoProcessOptions(mediaCaptureOptions);
        // 设置待拼接的文件
        TranscodingAPI.TranSource inputFilePara = videoProcessOptions.getSource();
        String[] arr = new String[1];
        arr[0] = mediaCaptureOptions.mFilePath;
        inputFilePara.setFilePaths(arr);
        // sdk bug，只有一个视频的时候，参数无效。只能添加这个规避一下
        inputFilePara.setVideoFadeDuration(0);
        inputFilePara.setAudioVolume(0.3f);
        videoProcessOptions.setSource(inputFilePara);
        // 设置文件剪切参数
        TranscodingAPI.TranTimeCut fileCutPara = videoProcessOptions.getTimeCut();
        int duration = (int) filterTime;
        int offset = (int) Math.rint(startTime);
        fileCutPara.setDuration(duration);
        fileCutPara.setStart(offset);
        videoProcessOptions.setTimeCut(fileCutPara);
        // 设置文件输出参数
        TranscodingAPI.TranOut outputFilePara = videoProcessOptions.getOutputFilePara();
        outputFilePara.setFilePath(outputPath);
        videoProcessOptions.setOutputFilePara(outputFilePara);
        // 设置文件输出大小
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(mediaCaptureOptions.mFilePath);
        String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        LogUtil.i(TAG, "import cut file, width:" + width + ", height:" + height);
        if (Integer.parseInt(width) > 1280 && Integer.parseInt(height) > 720) {
            //原始视频的真正宽高是 1080*1920
            TranscodingAPI.TranCrop cropPara = videoProcessOptions.getCrop();
            cropPara.setWidth(720);
            cropPara.setHeight(1280);
            videoProcessOptions.setCrop(cropPara);
            LogUtil.i(TAG, "crop para. width:720, height:1280");
        }
        LogUtil.i(TAG, "cut param. duration:" + duration + ", start:" + offset);
        videoProcessController.startCombination(videoProcessOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
                finishCutFile();
                break;
            case R.id.finish_clip_btn:
                onBackPressed();
                break;
        }
    }


    /**
     * ******************* VideoProcessCallback ***************
     */

    @Override
    public void onVideoProcessSuccess() {
        DialogMaker.dismissProgressDialog();
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onVideoProcessFailed(int code) {
        DialogMaker.dismissProgressDialog();
        if (code == 11) {
            Toast.makeText(this, "不支持该视频格式", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVideoSnapshotSuccess(Bitmap bitmap) {

    }

    @Override
    public void onVideoSnapshotFailed(int code) {

    }

    @Override
    public void onVideoProcessUpdate(int process, int total) {

    }
}
