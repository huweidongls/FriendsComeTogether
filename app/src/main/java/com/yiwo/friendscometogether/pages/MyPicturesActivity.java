package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.GridDividerItemDecoration;
import com.yiwo.friendscometogether.adapter.MyPicturesAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MyPicListModel;
import com.yiwo.friendscometogether.model.MyUploadPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class MyPicturesActivity extends BaseActivity {

    @BindView(R.id.activity_my_pictures_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_pictures_rv)
    RecyclerView recyclerView;

    private MyPicturesAdapter adapter;
    private List<MyPicListModel.ObjBean> mList;

    private static final int REQUEST_CODE = 0x00000011;

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pictures);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(MyPicturesActivity.this);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();

        GridLayoutManager manager = new GridLayoutManager(MyPicturesActivity.this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridDividerItemDecoration(10, Color.parseColor("#f2f2f2")));

        ViseHttp.POST(NetConfig.myPictureListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myPictureListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyPicListModel model = gson.fromJson(data, MyPicListModel.class);
                                mList = model.getObj();
                                adapter = new MyPicturesAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new MyPicturesAdapter.onItemClickListener() {
                                    @Override
                                    public void onClick(int type, final int position) {
                                        switch (type) {
                                            case 1:
                                                //限数量的多选(比喻最多9张)
                                                ImageSelector.builder()
                                                        .useCamera(true) // 设置是否使用拍照
                                                        .setSingle(true)  //设置是否单选
                                                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                                                        .start(MyPicturesActivity.this, REQUEST_CODE); // 打开相册
                                                break;
                                            case 2:
                                                ViseHttp.POST(NetConfig.myPictureDeleteUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myPictureDeleteUrl))
                                                        .addParam("listid", mList.get(position-1).getUid())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if(jsonObject1.getInt("code") == 200){
                                                                        toToast(MyPicturesActivity.this, "删除成功");
                                                                        mList.remove(position - 1);
                                                                        adapter.notifyDataSetChanged();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFail(int errCode, String errMsg) {

                                                            }
                                                        });
                                                break;
                                        }
                                    }
                                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            final List<String> list = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Observable<File> observable = Observable.create(new ObservableOnSubscribe<File>() {
                @Override
                public void subscribe(final ObservableEmitter<File> e) throws Exception {
//                    File file = new File(list.get(0));
//                    e.onNext(file);
                    Luban.with(MyPicturesActivity.this)
                            .load(list.get(0))
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
                                    e.onNext(file);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();
                }
            });
            Observer<File> observer = new Observer<File>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(File value) {

                    ViseHttp.UPLOAD(NetConfig.myPictureUploadUrl)
                            .addHeader("Content-Type","multipart/form-data")
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.myPictureUploadUrl))
                            .addParam("uid", uid)
                            .addFile("albumimg", value)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        Log.e("222", data);
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.getInt("code") == 200){
                                            Gson gson = new Gson();
                                            MyUploadPicModel model = gson.fromJson(data, MyUploadPicModel.class);
                                            toToast(MyPicturesActivity.this, "上传成功");
                                            mList.add(new MyPicListModel.ObjBean(model.getObj().getId()+"", model.getObj().getPic()));
                                            adapter.notifyDataSetChanged();
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

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            observable.observeOn(Schedulers.newThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    @OnClick({R.id.activity_my_pictures_rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_pictures_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyPicturesActivity.this.finish();
    }
}
