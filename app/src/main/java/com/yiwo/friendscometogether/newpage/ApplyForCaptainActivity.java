package com.yiwo.friendscometogether.newpage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ApplyForCaptainActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView imageView;
    private SpImp spImp;
    private Dialog dialog;
    private String imageUrl = "";
    private static final int REQUEST_CODE = 0x00000011;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_captain);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(this);

    }
    @OnClick({R.id.iv, R.id.btn,R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.iv:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(ApplyForCaptainActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.btn:
                if (!imageUrl.equals("")){
                    upLoad();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ApplyForCaptainActivity.this);
                    builder.setMessage("请选要上传的图片")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                break;
        }

    }

    private void upLoad() {
        Observable<List<File>> observable = Observable.create(new ObservableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<File>> e) throws Exception {
                dialog = WeiboDialogUtils.createLoadingDialog(ApplyForCaptainActivity.this, "请等待...");
                List<String> list = new ArrayList<>();
                list.add(imageUrl);
                final List<File> files = new ArrayList<>();
                Luban.with(ApplyForCaptainActivity.this)
                        .load(list)
                        .ignoreBy(100)
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                files.add(file);
                                if (files.size() == 1) {
                                    e.onNext(files);
                                }else {
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                WeiboDialogUtils.closeDialog(dialog);
                                toToast(ApplyForCaptainActivity.this,"上传失败，请重试");

                            }
                        }).launch();
            }
        });
        Observer<List<File>> observer = new Observer<List<File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<File> value) {
                ViseHttp.UPLOAD(NetConfig.pleaseCaptain)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.pleaseCaptain))
                        .addParam("uid", spImp.getUID())
                        .addFile("captainImg", value.get(0))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data + "::::");
                                Log.e("22222::", data +"");
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    if (!TextUtils.isEmpty(data)) {
                                        jsonObject = new JSONObject(data);
                                    }
                                    if (jsonObject.getInt("code") == 200) {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ApplyForCaptainActivity.this);
                                        builder.setMessage("已提交审核")
                                                .setNegativeButton("好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        onBackPressed();
                                                    }
                                                }).show();
                                    }
                                    if (jsonObject.getInt("code") == 400) {
                                        toToast(ApplyForCaptainActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                WeiboDialogUtils.closeDialog(dialog);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("22222", errMsg+errCode);
                                WeiboDialogUtils.closeDialog(dialog);
                                toToast(ApplyForCaptainActivity.this,"提交失败："+errMsg);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
//            Glide.with(ApplyForCaptainActivity.this).load("file://" + scList.get(0)).into(imageView);
            imageUrl = scList.get(0);
            imageView.setImageBitmap(getLoacalBitmap("file://" + scList.get(0)));
        }
    }
    public  Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
