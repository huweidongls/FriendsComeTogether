package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.TakeVideoFragment_new;
import com.yiwo.friendscometogether.wangyiyunshipin.VideoUpLoadListActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.UploadState;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadController;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddVideoTitleActivity extends BaseActivity{

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
        Intent intent = new Intent(context,AddVideoTitleActivity.class);
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
        progressDialog = new ProgressDialog(AddVideoTitleActivity.this);
        spImp = new SpImp(AddVideoTitleActivity.this);
        tvCity.setText(spImp.getLastCreateVideoAddress());
        editText.addTextChangedListener(textTitleWatcher);
        videoItem = (VideoItem) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
        url_screenshot = getIntent().getStringExtra("screenshot");
        Log.d("asdasd",url_screenshot);
        Log.d("asdasd",videoItem.getUriString()+"|||"+videoItem.getFilePath());
        Glide.with(AddVideoTitleActivity.this).load(videoItem.getUriString()).apply(new RequestOptions().error(R.mipmap.zanwutupian)).into(iv);
    }
    @OnClick({R.id.rl_back,R.id.activity_up_load_video_rl_complete,R.id.rl_choose_address})
     public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                close();
                break;
            case R.id.rl_choose_address:
                Intent it = new Intent(AddVideoTitleActivity.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "createYouJi");
                startActivityForResult(it, REQUEST_CODE_GET_CITY);
                break;
            case R.id.activity_up_load_video_rl_complete:

                showActivePopupwindow();
                break;
        }
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
                Toast.makeText(AddVideoTitleActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        close();
    }
    private void showActivePopupwindow() {

        View view = LayoutInflater.from(AddVideoTitleActivity.this).inflate(R.layout.popupwindow_video_fabu, null);

        TextView btnUp = view.findViewById(R.id.btn_up);
        TextView btnSave = view.findViewById(R.id.btn_save);
        TextView btnCanel = view.findViewById(R.id.btn_cancel);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    toToast(AddVideoTitleActivity.this,"请输入视频名字！");
                }else if (TextUtils.isEmpty(tvCity.getText().toString())){
                    Toast.makeText(AddVideoTitleActivity.this, "请填写地点", Toast.LENGTH_SHORT).show();
                }else {
                    spImp.setLastCreateVideoAddress(tvCity.getText().toString());
                    videoItem.setVideoFaBuName(editText.getText().toString());
                    videoItem.setVideoAddress(tvCity.getText().toString());

                    VideoUpLoadListActivity.startVideoUpLoadListActivity(AddVideoTitleActivity.this,videoItem);
                    popupWindow.dismiss();
                    finish();
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddVideoTitleActivity.this);
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
        toToast(AddVideoTitleActivity.this,"保存成功！");
    }
}
