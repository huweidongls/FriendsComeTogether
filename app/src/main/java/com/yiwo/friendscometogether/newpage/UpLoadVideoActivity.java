package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.storage.StorageType;
import com.netease.nim.uikit.common.util.storage.StorageUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.utils.InsertMediaToSystem;
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
import java.util.HashMap;
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
    private VideoItem videoItem;
    private String url_screenshot;
    private SpImp spImp;
    private Dialog dialog;
    private Boolean save2SdCard = false;
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
        spImp = new SpImp(UpLoadVideoActivity.this);
        editText.addTextChangedListener(textTitleWatcher);
        videoItem = (VideoItem) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
        url_screenshot = getIntent().getStringExtra("screenshot");
        Log.d("asdasd",url_screenshot);
        Log.d("asdasd",videoItem.getUriString()+"|||"+videoItem.getFilePath());
        Glide.with(UpLoadVideoActivity.this).load(videoItem.getUriString()).apply(new RequestOptions().error(R.mipmap.zanwutupian)).into(iv);
        initData();
    }
    @OnClick({R.id.rl_back,R.id.activity_up_load_video_rl_complete})
     public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                AlertDialog.Builder dialog = new AlertDialog.Builder(UpLoadVideoActivity.this);
                dialog.setMessage("确定退出并取消发布视频？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.activity_up_load_video_rl_complete:
                if (editText.getText().toString().equals("")){
                    toToast(UpLoadVideoActivity.this,"请输入视频名字！");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadVideoActivity.this);
                    builder.setMessage("是否发布并保存到本地？")
                            .setNegativeButton("仅发布", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    save2SdCard = false;
                                    uploadFile(videoItem);
                                }
                            }).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    save2SdCard = true;
                                    uploadFile(videoItem);
                                }
                    }).show();
                }
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
        dialog = WeiboDialogUtils.createLoadingDialog(UpLoadVideoActivity.this,"正在上传...");
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
            tv_num.setText(temp.length()+"/200");
            if(temp.length()>=200){
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

    }

    @Override
    public void updateItemProgress(String id, int progress, int state) {

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
        Log.d("asdasdsd",code+"////  "+addVideoResponseEntity.getVideoInfoEntity().getSnapshotUrl());
        WeiboDialogUtils.closeDialog(dialog);
        if (code == 200){
//            if (addVideoResponseEntity.getVideoInfoEntity()!=null){
//                Log.d("asdasdsd",addVideoResponseEntity.getVideoInfoEntity().getOrigUrl());
//            }
                ViseHttp.POST(NetConfig.upLoadVideo)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.upLoadVideo))
                        .addParam("userID", spImp.getUID())
                        .addParam("vname",editText.getText().toString())
                        .addParam("vurl",addVideoResponseEntity.getVideoInfoEntity().getOrigUrl())
                        .addParam("img",addVideoResponseEntity.getVideoInfoEntity().getSnapshotUrl())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        // 保存至相册
                                        if (save2SdCard){
                                            File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
                                            File appDir = new File(pictureFolder ,"Camera");
                                            if (!appDir.exists()) {
                                                appDir.mkdirs();
                                            }
                                            String fileName = "瞳伴视频_"+System.currentTimeMillis() + ".MP4";
                                            File destFile = new File(appDir, fileName);
                                            FileUtils.copy(new File(videoItem.getFilePath()), destFile);
                                            // 最后通知图库更新
                                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                                    Uri.fromFile(new File(destFile.getPath()))));
                                        }
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(UpLoadVideoActivity.this);
        dialog.setMessage("确定退出并取消发布视频？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
