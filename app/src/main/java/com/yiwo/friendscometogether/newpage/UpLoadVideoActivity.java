package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
                    uploadFile(videoItem);
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
                                        toToast(UpLoadVideoActivity.this,"发布成功");
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
