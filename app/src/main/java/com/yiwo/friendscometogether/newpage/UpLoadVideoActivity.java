package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.TakeVideoFragment_new;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.UploadState;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadController;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpLoadVideoActivity extends BaseActivity implements UploadController.UploadUi {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.activity_up_load_video_tv_title)
    EditText editText;
    @BindView(R.id.activity_up_load_video_tv_title_num)
    TextView tv_num;
    @BindView(R.id.activity_create_friend_remember_tv_activity_city)
    EditText tvCity;

    private static final int REQUEST_CODE_GET_CITY = 1;

    private VideoItem videoItem;
    private String url_screenshot;
    private SpImp spImp;
//    private Dialog dialog;
    private ProgressDialog progressDialog;
    private PopupWindow popupWindow;
    public  static void startUpLoadVideoActivity(Context context,VideoItem videoItem,String url_screenshot){
        Intent intent = new Intent(context,UpLoadVideoActivity.class);
        intent.putExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM,videoItem);
        intent.putExtra("screenshot",url_screenshot);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_video);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(UpLoadVideoActivity.this);
        spImp = new SpImp(UpLoadVideoActivity.this);
        tvCity.setText(spImp.getLastCreateVideoAddress());
        editText.addTextChangedListener(textTitleWatcher);
        videoItem = (VideoItem) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
        url_screenshot = getIntent().getStringExtra("screenshot");
        Log.d("asdasd",url_screenshot);
        Log.d("asdasd",videoItem.getUriString()+"|||"+videoItem.getFilePath());
        Glide.with(UpLoadVideoActivity.this).load(videoItem.getUriString()).apply(new RequestOptions().error(R.mipmap.zanwutupian)).into(iv);
        initData();
    }
    @OnClick({R.id.rl_back,R.id.activity_up_load_video_rl_complete,R.id.rl_choose_address})
     public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                close();
                break;
            case R.id.rl_choose_address:
                Intent it = new Intent(UpLoadVideoActivity.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "createYouJi");
                startActivityForResult(it, REQUEST_CODE_GET_CITY);
                break;
            case R.id.activity_up_load_video_rl_complete:

                showActivePopupwindow();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadVideoActivity.this);
//                    builder.setMessage("是否发布并保存到本地？")
//                            .setNegativeButton("仅发布", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    uploadFile(videoItem);
//                                }
//                            }).setPositiveButton("保存", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    uploadFile(videoItem);
//                                }
//                    }).show();
                break;
        }
    }
    private void uploadFile(final VideoItem videoItem) {
         Log.d("asdasdsd:",";;;uploadFile");
        if (!NetworkUtil.isNetAvailable(UpLoadVideoActivity.this)) {
            EasyAlertDialogHelper.showOneButtonDiolag(UpLoadVideoActivity.this, null, getString(R.string.network_is_not_available),
                    "我知道了", false, null);
        }
        doRealUpload(videoItem);
    }
    /**
     * 调用上传接口，上传视频
     */
    private void doRealUpload(VideoItem videoItem) {
//        dialog = WeiboDialogUtils.createLoadingDialog(UpLoadVideoActivity.this,"正在上传...");
        progressDialog.setMessage("正在上传...");
        progressDialog.setMax(100);
        //ProgressDialog.STYLE_SPINNER  默认进度条是转圈
        //ProgressDialog.STYLE_HORIZONTAL  横向进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);
        Log.d("asdasdsd:",";;;doRealUpload");
        videoItem.setType(UploadType.SHORT_VIDEO);
        videoItem.setState(UploadState.STATE_WAIT);
        List<VideoItem> videoItemList = new ArrayList<>(1);
        videoItemList.add(videoItem);
        UploadController.getInstance().uploadLocalItem(videoItemList, UploadType.SHORT_VIDEO, true);
    }
    private void initData() {
        UploadController.getInstance().init(this);
        UploadController.getInstance().attachUi(this);
//        UploadController.getInstance().loadVideoDataFromLocal(UploadType.SHORT_VIDEO);
    }
    TextWatcher textTitleWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tv_num.setText(temp.length()+"/30");
            if(temp.length()>=30){
                Toast.makeText(UpLoadVideoActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        UploadController.getInstance().suspend();
    }

    @Override
    public boolean isUiInit() {
        return false;
    }

    @Override
    public void updateAllItems() {

    }

    @Override
    public void updateUploadState(int state) {
        Log.d("aaaashangchuanSTATE::",state+"");
    }

    @Override
    public void updateItemProgress(String id, int progress, int state) {
        Log.d("aaaashangchuanSTATE::",state+"");
        progressDialog.setProgress(progress);
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onDataSetChanged(List<VideoItem> data) {

    }
    @Override
    public void onAddVideoResult(int code, String id, AddVideoResponseEntity addVideoResponseEntity) {
        Log.d("aaaashangchuan",code+"////  "+addVideoResponseEntity.getVideoInfoEntity().getSnapshotUrl()+"//vid:::"+addVideoResponseEntity.getVideoInfoEntity().getVid());
//        WeiboDialogUtils.closeDialog(dialog);
        progressDialog.dismiss();
        if (code == 200){
            if (addVideoResponseEntity.getVideoInfoEntity()!=null){
                Log.d("asdasdsd",addVideoResponseEntity.getVideoInfoEntity().getOrigUrl());
            }
                ViseHttp.POST(NetConfig.upLoadVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.upLoadVideo))
                        .addParam("userID", spImp.getUID())
                        .addParam("vname",editText.getText().toString())
                        .addParam("vurl",addVideoResponseEntity.getVideoInfoEntity().getOrigUrl())
                        .addParam("img",addVideoResponseEntity.getVideoInfoEntity().getSnapshotUrl())
                        .addParam("wy_vid",addVideoResponseEntity.getVideoInfoEntity().getVid()+"")
                        .addParam("address",tvCity.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        toToast(UpLoadVideoActivity.this,"发布成功");
                                        //删除之前文件夹的视频
                                        deleteSingleFile(videoItem.getFilePath());
                                        finish();
                                    }else {
                                        toToast(UpLoadVideoActivity.this,"发布失败");
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
    }
    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件失败" + filePath$Name + "不存在！");
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        close();
    }
    private void showActivePopupwindow() {

        View view = LayoutInflater.from(UpLoadVideoActivity.this).inflate(R.layout.popupwindow_video_fabu, null);

        TextView btnUp = view.findViewById(R.id.btn_up);
        TextView btnSave = view.findViewById(R.id.btn_save);
        TextView btnCanel = view.findViewById(R.id.btn_cancel);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    toToast(UpLoadVideoActivity.this,"请输入视频名字！");
                }else if (TextUtils.isEmpty(tvCity.getText().toString())){
                    Toast.makeText(UpLoadVideoActivity.this, "请填写地点", Toast.LENGTH_SHORT).show();
                }else {
                    spImp.setLastCreateVideoAddress(tvCity.getText().toString());
//                    VideoUpLoadListActivity.startUpLoadVideoActivity(UpLoadVideoActivity.this,videoItem);
                    videoItem.setVideoFaBuName(editText.getText().toString());
                    videoItem.setVideoAddress(tvCity.getText().toString());
                    uploadFile(videoItem);
                }
                popupWindow.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spImp.setLastCreateVideoAddress(tvCity.getText().toString());
                saveVideo2Phone();
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        //这句话，让pop覆盖在输入法上面
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        //这句话，让pop自适应输入状态
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0,0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GET_CITY && data != null && resultCode == 1) {//选择城市
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            tvCity.setText(model.getName());
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 2) {//重置
            tvCity.setText("");
            tvCity.setHint("请选择或输入活动地点");
        } else if (requestCode == REQUEST_CODE_GET_CITY && resultCode == 3) {//国际城市
            String city = data.getStringExtra("city");
            tvCity.setText(city);
        }
    }
    private void close(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(UpLoadVideoActivity.this);
        dialog.setMessage("是否保存至相册？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveVideo2Phone();
                        finish();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
        }).show();
    }
    private void saveVideo2Phone(){
        // 保存至相册
        File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
        File appDir = new File(pictureFolder, "Camera");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = "瞳伴视频_" + System.currentTimeMillis() + ".MP4";
        File destFile = new File(appDir, fileName);
        FileUtils.copy(new File(videoItem.getFilePath()), destFile);
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(destFile.getPath()))));
        toToast(UpLoadVideoActivity.this,"保存成功！");
    }
}
