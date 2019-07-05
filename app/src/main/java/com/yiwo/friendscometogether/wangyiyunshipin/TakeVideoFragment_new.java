package com.yiwo.friendscometogether.wangyiyunshipin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.fulive.EffectAndFilterSelectAdapter;
import com.netease.fulive.FuVideoEffect;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.transcoding.record.VideoCallback;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.newpage.UpLoadVideoActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.MediaCaptureController;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.model.MediaCaptureOptions;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.model.ResolutionType;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.model.VideoCaptureParams;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.videoprocess.VideoProcessController;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;
import com.yiwo.friendscometogether.wangyiyunshipin.utils.FileUtil;
import com.yiwo.friendscometogether.wangyiyunshipin.widget.CircleProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by ljc on 2019/5/29.
 */

public class TakeVideoFragment_new extends BaseFragment implements MediaCaptureController.MediaCaptureControllerCallback,
        View.OnClickListener, VideoProcessController.VideoProcessCallback {


    // constant
    private static final int DEFAULT_VIDEO_COUNTS = 3;
    private static final long DEFAULT_VIDEO_TIME = 30 * 1000;
    public static final String EXTRA_VIDEO_ITEM = "video_item";
    public static final int REQUEST_CODE = 1000;
    private static final String TAG = TakeVideoFragment_new.class.getSimpleName();
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    // view
    private NeteaseView videoView;
    private RelativeLayout startLayout; // 开始录制布局
    private RelativeLayout recodingLayout; // 正在录制布局
    private TextView startRecodingBtn; // 开始录制按钮
    private TextView importVideoBtn; // 导入视频按钮
    private RelativeLayout paramLayout; // 录制参数layout
    private RelativeLayout paramSelectionLayout; // 参数选择layout
    private RelativeLayout paramWrapperLayout; // 参数选择layout的底部透明层，用于点击空白处消失
    private RadioGroup clarityRadioGroup; // 清晰度按钮选择
    private RadioButton sdRadio; // 流畅按钮
    private RadioButton hdRadio; // 高清按钮
    private RadioGroup countsRadioGroup; // 分段数按钮选择
    private RadioGroup timeRadioGroup; // 总时长按钮选择
    private TextView modeText; // 清晰度显示
    private TextView timeText; // 时间显示
    private TextView countsText; // 分段数显示
    private ImageView takeCountsImage; // 分段数显示进度条
    private CircleProgressView circleProgressView; // 录制时间进度条

    /**
     * 录制相关按钮
     */
    private TextView previousBtn; // 上一步按钮
    private ImageView importAddVideoBtn; // 已经有录制的视频时，导入视频按钮
    private TextView finishBtn; // 完成按钮
    /**
     * 顶部控制条
     */
    private ImageButton filterBtn; // 滤镜按钮
    private ImageButton cameraSwitchBtn; // 摄像头转换按钮
    private RadioGroup filterGroup; // 滤镜按钮
    private RelativeLayout filterLayout; // 滤镜布局
//    private View closeBtn; // 关闭按钮
    private ImageButton faceuBtn; // faceu按钮
    /**
     * faceU 布局
     */
    private RecyclerView effectRecyclerView; // 道具

    // data
    private MediaCaptureController mediaCaptureController; // 录制视频控制器
    private MediaCaptureOptions mediaCaptureOptions; // 视频录制参数配置
    private VideoCaptureParams videoCaptureParams; // 录制视频的参数（界面显示，用户操作配置的），分几段，时间等
    private int currentCount = 0; // 当前录制哪段视频
    private List<String> videoPathList = new ArrayList<>(); // 录制的分段视频地址
    private String outputPath; // 拼接后的视频地址
    private VideoItem videoItem; // 拼接后的video
    private String displayName; // 视频名称
    private SparseArray<VideoEffect.FilterType> filterArray;
    private float time; // 录制计时
    private Timer timer;
    private TimerTask timerTask;
    private boolean isFinish = false;
    private boolean hasDone = false;

    //第三方滤镜
    private FuVideoEffect mFuEffect; //FU的滤镜

    private static Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_video_new, null);
        ButterKnife.bind(this, view);
        findViews(view);
        setListener(view);
        initVideoParams();
        initData();
        initMediaCapture();
//        requestBasicPermission();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFinish = true;
        if (mediaCaptureController != null) {
            doneRecording();
            mediaCaptureController = null;
            mediaCaptureOptions = null;
        }
        getHandler().removeCallbacksAndMessages(null);
        stopCounting();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResulton","requestCode:"+requestCode+"///"+"resultCode:"+resultCode);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == VideoImportActivity.EXTRA_REQUEST_CODE) {
            // 导入视频成功
            currentCount++;
            if (currentCount == videoCaptureParams.getCount()) {
                doneRecording();
            } else {
                initCaptureOptions();
            }
            updateStopRecodingUI();

        } else if (requestCode == ShortVideoEditActivity.EXTRA_REQUEST_CODE) {
            hasDone = false;
            if (data.getBooleanExtra(ShortVideoEditActivity.EXTRA_EDIT_DONE, false)) {
                // 视频编辑完成
                VideoItem videoItem = (VideoItem)  data.getSerializableExtra(EXTRA_VIDEO_ITEM);
                if (videoItem != null) {
                    UpLoadVideoActivity.startUpLoadVideoActivity(getContext(), (VideoItem) data.getSerializableExtra(EXTRA_VIDEO_ITEM),videoPathList.get(0));
//                    Intent intent = new Intent(getContext(),UpLoadVideoActivity.class);
//                    intent.putExtra(EXTRA_VIDEO_ITEM, data.getSerializableExtra(EXTRA_VIDEO_ITEM));
//                    getContext().startActivity(intent);
//                    getActivity().setResult(Activity.RESULT_OK, intent);
                }
                getActivity().finish();
            } else {
                if (currentCount == videoCaptureParams.getCount()) {
                    // 视频编辑返回，录制满了视频，则显示
                    showVideoDoneToast();
                } else {
                    // 没有录满数量视频
                    reInitMediaCapture();
                    updateStopRecodingUI();
                }
            }
        }
    }

    /**
     * ****************** permission ****************
     */

    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, getActivity(), BASIC_PERMISSIONS);
        MPermission.with(getActivity())
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(getContext(), "授权成功", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, getActivity(), BASIC_PERMISSIONS);
//        findViews(getView());
//        setListener(getView());
//        initVideoParams();
////        initData();
//        initMediaCapture();
    }


    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(getContext(), "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, getActivity(), BASIC_PERMISSIONS);
        getActivity().finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_recoding_btn:
                // 开始录制
                Log.d("asdasd","开始录制");
                paramLayout.setVisibility(View.GONE);
                startRecording();
                break;
            case R.id.import_add_btn:
            case R.id.import_video:
                // 打开导入视频列表
                VideoImportActivity.startActivityForResult(getContext(),
                        videoCaptureParams.getTime() / videoCaptureParams.getCount(),
                        videoPathList.get(videoPathList.size() - 1));
                break;
            case R.id.param_layout:
                showParamSelectionLayout(true);
                break;
            case R.id.param_wrapper_layout:
                // 先清空列表，再重新初始化，顺序不能反
                videoPathList.clear();
                // 重新初始化
                doneRecording();
                updateParamLayout();
                showParamSelectionLayout(false);
                break;
            case R.id.faceu_btn:
                // faceu布局显示
                showOrHideFaceULayout(true,getView());
                break;
            case R.id.filter_btn:
                filterLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.switch_btn:
                if (mediaCaptureController != null) {
                    mediaCaptureController.switchCamera();
                }
                break;
            case R.id.shortvideo_filter_layout:
                filterLayout.setVisibility(filterGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.previous_btn:
                // 删除上一分段视频
                deletePreviousVideo();
                break;
            case R.id.finish_btn:
                // 完成按钮，释放faceu和mediaRecord
                hasDone = true;
                doneRecording();
                break;
        }
    }

    @Override
    public void onPreviewInited() {
        videoView.setVisibility(View.VISIBLE);
        startRecodingBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPreviewSize(int videoPreviewWidth, int videoPreviewHeight) {

    }

    @Override
    public SurfaceView getSurfaceView() {
        return videoView;
    }

    @Override
    public void onStartRecording() {
        updateStartRecordingUI();
        getHandler().postDelayed(stopRecodingRunnable, videoCaptureParams.getTime() / videoCaptureParams.getCount());
    }

    @Override
    public void onRelease() {
        if (isFinish) {
            return;
        }
        videoView.setVisibility(View.GONE);
        mediaCaptureController = null;
        if (hasDone) {
            // 点击了完成，进入编辑界面
            enterEdit(true);
            return;
        }
        if (currentCount == videoCaptureParams.getCount()) {
            // 最后一段视频录制完成, 跳转到编辑界面
            enterEdit(false);
        } else {
            // 参数选择后，重新初始化
            initMediaCapture();
        }
    }

    @Override
    public void onError(int code) {
        if (code == lsMessageHandler.MSG_AUDIO_RECORD_ERROR) {
            Toast.makeText(getContext(), "录音模块异常", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "录制异常:" + code, Toast.LENGTH_SHORT).show();
        }
//        finish();
    }

    @Override
    public void onVideoProcessSuccess() {

    }

    @Override
    public void onVideoProcessFailed(int code) {

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
    private void findViews(View view) {

        videoView = view.findViewById(R.id.camerasurfaceview);
        startLayout = view.findViewById(R.id.start_layout);
        startLayout.setVisibility(View.VISIBLE);
        recodingLayout = view.findViewById(R.id.recording_layout);
        startRecodingBtn = view.findViewById(R.id.start_recoding_btn);
        importVideoBtn = view.findViewById(R.id.import_video);
        paramLayout = view.findViewById(R.id.param_layout);
        paramSelectionLayout = view.findViewById(R.id.param_selection_layout);
        paramWrapperLayout = view.findViewById(R.id.param_wrapper_layout);
        clarityRadioGroup = view.findViewById(R.id.clarity_radio_group);
        sdRadio = view.findViewById(R.id.sd_btn);
        hdRadio = view.findViewById(R.id.hd_btn);
        countsRadioGroup = view.findViewById(R.id.counts_radio_group);
        timeRadioGroup = view.findViewById(R.id.time_radio_group);
        modeText = view.findViewById(R.id.mode);
        timeText = view.findViewById(R.id.time);
        countsText = view.findViewById(R.id.counts);
        takeCountsImage = view.findViewById(R.id.take_counts_image);
        circleProgressView = view.findViewById(R.id.progress_view);

        // 5.0以下，不支持hd
        if (Build.VERSION.SDK_INT < 21) {
            hdRadio.setVisibility(View.GONE);
            sdRadio.setChecked(true);
            modeText.setText("流畅");
        } else {
            hdRadio.setVisibility(View.VISIBLE);
            hdRadio.setChecked(true);
            modeText.setText("高清");
        }

        // 顶部控制条按钮
        filterBtn = view.findViewById(R.id.filter_btn);
        faceuBtn = view.findViewById(R.id.faceu_btn);
        cameraSwitchBtn = view.findViewById(R.id.switch_btn);
        filterGroup = view.findViewById(R.id.filter_group);
        filterLayout = view.findViewById(R.id.shortvideo_filter_layout);
//        closeBtn = view.findViewById(R.id.close_btn);
        // 录制相关按钮
        previousBtn = view.findViewById(R.id.previous_btn);
        importAddVideoBtn = view.findViewById(R.id.import_add_btn);
        finishBtn = view.findViewById(R.id.finish_btn);
        // 初始化faceu布局
        initFaceULayout(view);
    }
    private void initFaceULayout(View view) {
        effectRecyclerView = view.findViewById(R.id.effect_recycle_view);
        effectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        EffectAndFilterSelectAdapter effectAndFilterSelectAdapter = new EffectAndFilterSelectAdapter(effectRecyclerView,
                EffectAndFilterSelectAdapter.VIEW_TYPE_EFFECT);
        effectAndFilterSelectAdapter.setOnItemSelectedListener(new EffectAndFilterSelectAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                if (mFuEffect != null) {
                    mFuEffect.onEffectItemSelected(itemPosition);
                }
            }
        });
        effectRecyclerView.setAdapter(effectAndFilterSelectAdapter);
        showOrHideFaceULayout(false,view);
    }
    private void showOrHideFaceULayout(boolean show,View view) {
        ViewGroup vp = view.findViewById(R.id.faceu_layout);
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
    private void setListener(final View view) {
        startRecodingBtn.setOnClickListener(this);
        importVideoBtn.setOnClickListener(this);
        paramLayout.setOnClickListener(this);
        paramWrapperLayout.setOnClickListener(this);
        clarityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int resId = radioGroup.getCheckedRadioButtonId();
                RadioButton button = view.findViewById(resId);
                if (button.getText().equals("高清")) {
                    videoCaptureParams.setResolutionType(ResolutionType.HD);
                } else {
                    videoCaptureParams.setResolutionType(ResolutionType.FLUENT);
                }
            }
        });
        countsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int resId = radioGroup.getCheckedRadioButtonId();
                RadioButton button = view.findViewById(resId);
                if (button.getText().equals("1段")) {
                    videoCaptureParams.setCount(1);
                } else if (button.getText().equals("2段")) {
                    videoCaptureParams.setCount(2);
                } else {
                    videoCaptureParams.setCount(3);
                }
            }
        });
        timeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int resId = radioGroup.getCheckedRadioButtonId();
                RadioButton button = view.findViewById(resId);
                if (button.getText().equals("15s")) {
                    videoCaptureParams.setTime(15 * 1000);
                } else if (button.getText().equals("30s")) {
                    videoCaptureParams.setTime(30 * 1000);
                } else {
                    videoCaptureParams.setTime(45 * 1000);
                }
            }
        });
        // 顶部控制条
        filterBtn.setOnClickListener(this);
        faceuBtn.setOnClickListener(this);
        cameraSwitchBtn.setOnClickListener(this);
        filterLayout.setOnClickListener(this);
        filterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int resId = radioGroup.getCheckedRadioButtonId();
                if (mediaCaptureController != null) {
                    mediaCaptureController.getMediaRecord().setFilterType(filterArray.get(resId));
                }
            }
        });
//        closeBtn.setOnClickListener(this);
        // 录制相关按钮
        previousBtn.setOnClickListener(this);
        importAddVideoBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
    }
    private void initVideoParams() {
        videoCaptureParams = new VideoCaptureParams(DEFAULT_VIDEO_COUNTS, DEFAULT_VIDEO_TIME, ResolutionType.HD);
        videoCaptureParams.setResolutionType(hdRadio.isChecked() ? ResolutionType.HD : ResolutionType.FLUENT);
    }
    private void initData() {
        filterArray = new SparseArray<>();
        filterArray.put(R.id.no_filter_btn, VideoEffect.FilterType.none);
        filterArray.put(R.id.filter_a_btn, VideoEffect.FilterType.pixar);
        filterArray.put(R.id.filter_b_btn, VideoEffect.FilterType.fairytale);
        filterArray.put(R.id.filter_c_btn, VideoEffect.FilterType.calm);
        filterArray.put(R.id.filter_d_btn, VideoEffect.FilterType.brooklyn);
    }
    private void initMediaCapture() {
        mediaCaptureOptions = new MediaCaptureOptions();
        initCaptureOptions();
        setResolution();
        mediaCaptureController = new MediaCaptureController(getContext(), this, mediaCaptureOptions);
        // faceU要在startPreview之前初始化
//        fuLiveEffect();
        mediaCaptureController.startPreview(videoView);
    }
    private void initCaptureOptions() {
        mediaCaptureOptions.mFilePath = StorageUtil.getWritePath(System.currentTimeMillis() + ".mp4", StorageType.TYPE_VIDEO);
        videoPathList.add(mediaCaptureOptions.mFilePath);
    }
    private void setResolution() {
        if (videoCaptureParams.getResolutionType() == ResolutionType.HD) {
            mediaCaptureOptions.mVideoPreviewWidth = 720;
            mediaCaptureOptions.mVideoPreviewHeight = 1280;
            mediaCaptureOptions.resolutionType = ResolutionType.HD;
        } else if (videoCaptureParams.getResolutionType() == ResolutionType.FLUENT) {
            mediaCaptureOptions.mVideoPreviewWidth = 480;
            mediaCaptureOptions.mVideoPreviewHeight = 640;
            mediaCaptureOptions.resolutionType = ResolutionType.FLUENT;
        } else {
            mediaCaptureOptions.mVideoPreviewWidth = 240;
            mediaCaptureOptions.mVideoPreviewHeight = 320;
            mediaCaptureOptions.resolutionType = ResolutionType.SD;
        }
    }
    //FU的滤镜
    private void fuLiveEffect(){
        mediaCaptureController.getMediaRecord().setCaptureRawDataCB(new VideoCallback() {
            @Override
            public int onVideoCapture(byte[] bytes, int i, int i1, int i2) {
                //SDK回调的线程已经创建了GLContext
                if(mFuEffect == null){
                    mFuEffect = new FuVideoEffect();
                    mFuEffect.filterInit(getContext());
                }
                int result = mFuEffect.ifilterNV21Image(bytes, i, i1);
                return result;
            }
        });
    }
    private void startRecording() {
        currentCount++;
        mediaCaptureController.startRecording();
    }
    private void showParamSelectionLayout(boolean isShow) {
        paramWrapperLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    private void doneRecording() {
        if (mediaCaptureController != null) {
            // 顺序不能错
            mediaCaptureController.stopRecording();
            mediaCaptureController.stopPreview();
            releaseFuEffect();
            mediaCaptureController.release();
        }
    }
    // 更新参数显示
    private void updateParamLayout() {
        LogUtil.i(TAG, "update param layout");
        if (videoCaptureParams.getResolutionType().equals(ResolutionType.HD)) {
            modeText.setText("高清");
        } else if (videoCaptureParams.getResolutionType().equals(ResolutionType.SD)) {
            modeText.setText("标清");
        } else {
            modeText.setText("流畅");
        }
        timeText.setText(String.format("%d秒", videoCaptureParams.getTime() / 1000));
        countsText.setText(String.format("%d段", videoCaptureParams.getCount()));
        // 分段数进度条
        takeCountsImage.setBackgroundResource(0);
        if (videoCaptureParams.getCount() == 1) {
            takeCountsImage.setImageResource(R.mipmap.ic_one_take);
        } else if (videoCaptureParams.getCount() == 2) {
            takeCountsImage.setImageResource(R.mipmap.ic_two_take);
        } else {
            takeCountsImage.setImageResource(R.mipmap.ic_three_take);
        }
    }
    private void releaseFuEffect(){
        if(mFuEffect != null){
            mediaCaptureController.getMediaRecord().postOnGLThread(new Runnable() {
                @Override
                public void run() {
                    mFuEffect.filterUnInit();
                    mFuEffect = null;
                }
            });
        }
    }
    // 更新开始录制界面展示
    private void updateStartRecordingUI() {
        LogUtil.i(TAG, "update start recoding ui");
        startLayout.setVisibility(View.GONE);
        recodingLayout.setVisibility(View.VISIBLE);
        takeCountsImage.setVisibility(View.GONE);
        circleProgressView.setFormat("");
        circleProgressView.setMaxProgress(videoCaptureParams.getTime() / videoCaptureParams.getCount() / 1000);
        time = 0;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        time += 0.1;
                        float temp = (float) (Math.round(time * 10)) / 10;
                        if (temp > videoCaptureParams.getTime() / videoCaptureParams.getCount() / 1000) {
                            return;
                        }
                        circleProgressView.setProgress(temp);
                        circleProgressView.setContent(String.valueOf(temp));
                    }
                });
            }
        };
        timer.schedule(timerTask, 1, 100);
    }
    // 删除上一分段视频
    private void deletePreviousVideo() {
        if (currentCount > 0) {
            currentCount--;
        }
        if (videoPathList.size() > 0 && videoPathList.size() > currentCount) {
            // 上一步操作, 删除刚刚录制的文件
            FileUtil.deleteFile(videoPathList.get(currentCount));
            videoPathList.remove(currentCount);
        }
        updateStopRecodingUI();
    }
    // 更新录制间，界面展示
    private void updateStopRecodingUI() {
        LogUtil.i(TAG, "update stop recoding ui, currentCount:" + currentCount
                + ", total count:" + videoCaptureParams.getCount());
        startLayout.setVisibility(View.VISIBLE);
        recodingLayout.setVisibility(View.GONE);
        startRecodingBtn.setText(currentCount == 0 ? "开始\n录制" : "继续\n录制");
        previousBtn.setVisibility(currentCount != 0 ? View.VISIBLE : View.GONE);
        finishBtn.setVisibility(currentCount != 0 ? View.VISIBLE : View.GONE);
        importVideoBtn.setVisibility(currentCount == 0 ? View.VISIBLE : View.GONE);
        importAddVideoBtn.setVisibility(currentCount != 0 ? View.VISIBLE : View.GONE);

        paramLayout.setVisibility(currentCount == 0 ? View.VISIBLE : View.GONE);

        // 拍摄了几段视频的显示
        takeCountsImage.setVisibility(View.VISIBLE);
        takeCountsImage.setBackgroundResource(0);
        if (videoCaptureParams.getCount() == 2) {
            takeCountsImage.setImageResource(currentCount == 1 ? R.mipmap.ic_take_two_one : R.mipmap.ic_two_take);
        } else if (videoCaptureParams.getCount() == 3) {
            if (currentCount == 0) {
                takeCountsImage.setImageResource(R.mipmap.ic_three_take);
            } else if (currentCount == 1) {
                takeCountsImage.setImageResource(R.mipmap.ic_take_three_one);
            } else if (currentCount == 2) {
                takeCountsImage.setImageResource(R.mipmap.ic_take_three_two);
            }
        }
    }
    private Runnable stopRecodingRunnable = new Runnable() {
        @Override
        public void run() {
            // 录制3段，则release，否则stop
            if (mediaCaptureController != null && currentCount != videoCaptureParams.getCount()) {
                initCaptureOptions();
                mediaCaptureController.stopRecording();
            } else if (mediaCaptureController != null) {
                doneRecording();
            }
            stopCounting();
            updateStopRecodingUI();
        }
    };
    // 停止计时器
    private void stopCounting() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getContext().getMainLooper());
        }
        return handler;
    }
    /******************************
     * 视频拼接
     ********************************/

    // 显示拍摄已完成
    private void showVideoDoneToast() {
        EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {

            @Override
            public void doCancelAction() {
                // 删除上一分段
                // 重新初始化视频录制界面
                deletePreviousVideo();
                reInitMediaCapture();
            }

            @Override
            public void doOkAction() {
                enterEdit(false);
            }
        };

        final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(getContext(), null, "拍摄已完成！",
                "下一步", "删除上分段", false, listener);
        dialog.show();
    }

    private void reInitMediaCapture() {
        initMediaCapture();
        updateStopRecodingUI();
    }


    // 进入编辑页面
    private void enterEdit(boolean needRemove) {
        if (videoPathList.size() > 0 && needRemove) {
            videoPathList.remove(videoPathList.size() - 1);
        }
        ShortVideoEditActivity.startActivityForResult(getContext(), videoPathList,
                videoCaptureParams.getTime() / videoCaptureParams.getCount() * videoPathList.size(),
                mediaCaptureOptions);
    }
}
