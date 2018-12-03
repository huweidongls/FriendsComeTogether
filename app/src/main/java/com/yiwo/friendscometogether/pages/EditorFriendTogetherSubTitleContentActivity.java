package com.yiwo.friendscometogether.pages;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.IntercalationAdapter;
import com.yiwo.friendscometogether.adapter.ModifyFriendTogetherIntercalationPicAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.PicDescribeDialog;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.GetActiveIntercalationInfoModel;
import com.yiwo.friendscometogether.model.UserIntercalationPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

public class EditorFriendTogetherSubTitleContentActivity extends BaseActivity {

    @BindView(R.id.activity_editor_friend_together_sub_title_content_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_editor_friend_together_sub_title_content_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_editor_friend_together_sub_title_content_rl_complete)
    RelativeLayout rlComplete;
    @BindView(R.id.activity_editor_friend_together_sub_title_content_et_title)
    EditText etTitle;
    @BindView(R.id.activity_editor_friend_together_sub_title_content_et_content)
    EditText etContent;
    @BindView(R.id.activity_editor_friend_together_sub_title_content_tv_text_num)
    TextView tvContentNum;
    @BindView(R.id.activity_create_intercalation_rv1)
    RecyclerView recyclerView1;

    private IntercalationAdapter adapter;
    private List<UserIntercalationPicModel> mList;

    private static final int REQUEST_CODE = 0x00000011;

    private SpImp spImp;
    private String uid = "";
    private String id = "", title = "", content = "";

    private List<File> files = new ArrayList<>();

    private Dialog dialog;

    private ModifyFriendTogetherIntercalationPicAdapter picAdapter;
    private List<GetActiveIntercalationInfoModel.ObjBean.ImgsBean> mList1;

    private int oldPicNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_friend_together_sub_title_content);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(EditorFriendTogetherSubTitleContentActivity.this);

        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.getActiveIntercalationInfoUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getActiveIntercalationInfoUrl))
                .addParam("title_id", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                GetActiveIntercalationInfoModel model = gson.fromJson(data, GetActiveIntercalationInfoModel.class);
                                etTitle.setText(model.getObj().getPfptitle());
                                etContent.setText(model.getObj().getPfpcontent());
                                GridLayoutManager manager1 = new GridLayoutManager(EditorFriendTogetherSubTitleContentActivity.this, 3) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView1.setLayoutManager(manager1);
                                mList1 = model.getObj().getImgs();
                                oldPicNum = mList1.size();
                                picAdapter = new ModifyFriendTogetherIntercalationPicAdapter(mList1);
                                recyclerView1.setAdapter(picAdapter);

                                mList = new ArrayList<>();
                                GridLayoutManager manager = new GridLayoutManager(EditorFriendTogetherSubTitleContentActivity.this, 3);
                                recyclerView.setLayoutManager(manager);
                                adapter = new IntercalationAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setPicNum(oldPicNum);
                                adapter.setListener(new IntercalationAdapter.OnAddImgListener() {
                                    @Override
                                    public void onAddImg() {
                                        if (9 - mList.size() - oldPicNum > 0) {
                                            //限数量的多选(比喻最多9张)
                                            ImageSelector.builder()
                                                    .useCamera(true) // 设置是否使用拍照
                                                    .setSingle(false)  //设置是否单选
                                                    .setMaxSelectCount(9 - mList.size() - oldPicNum) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                                                    .start(EditorFriendTogetherSubTitleContentActivity.this, REQUEST_CODE); // 打开相册
                                        }else {
                                            toToast(EditorFriendTogetherSubTitleContentActivity.this, "最多只能添加9张图片");
                                        }
                                    }
                                }, new IntercalationAdapter.OnDeleteImgListener() {
                                    @Override
                                    public void onDeleteImg(int i) {
                                        mList.remove(i);
                                        adapter.notifyDataSetChanged();
                                    }
                                }, new IntercalationAdapter.OnAddDescribeListener() {
                                    @Override
                                    public void onAddDescribe(int i, String s) {
                                        mList.get(i).setDescribe(s);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                                picAdapter.setOnModifyListener(new ModifyFriendTogetherIntercalationPicAdapter.OnModifyListener() {
                                    @Override
                                    public void onModify(int type, final int position) {
                                        switch (type) {
                                            case 1:
                                                //删除图片
                                                toDialog(EditorFriendTogetherSubTitleContentActivity.this, "提示", "是否删除图片",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                ViseHttp.POST(NetConfig.delActivePicUrl)
                                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.delActivePicUrl))
                                                                        .addParam("image_id", mList1.get(position).getPfpID())
                                                                        .request(new ACallback<String>() {
                                                                            @Override
                                                                            public void onSuccess(String data) {
                                                                                try {
                                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                                        mList1.remove(position);
                                                                                        picAdapter.notifyDataSetChanged();
                                                                                        oldPicNum = oldPicNum - 1;
                                                                                        adapter.setPicNum(oldPicNum);
                                                                                        adapter.notifyDataSetChanged();
                                                                                        toToast(EditorFriendTogetherSubTitleContentActivity.this, "删除图片成功");
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
                                                        }, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        });
                                                break;
                                            case 2:
                                                //修改描述
                                                PicDescribeDialog dialog = new PicDescribeDialog(EditorFriendTogetherSubTitleContentActivity.this);
                                                dialog.show();
                                                dialog.setOnReturnListener(new PicDescribeDialog.OnReturnListener() {
                                                    @Override
                                                    public void onReturn(final String title) {
                                                        ViseHttp.POST(NetConfig.modifyActivePicInfoUrl)
                                                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.modifyActivePicInfoUrl))
                                                                .addParam("pfpID", mList1.get(position).getPfpID())
                                                                .addParam("img_info", title + "")
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if (jsonObject1.getInt("code") == 200) {
                                                                                mList1.get(position).setPfpcontent(title + "");
                                                                                picAdapter.notifyDataSetChanged();
                                                                                toToast(EditorFriendTogetherSubTitleContentActivity.this, "修改成功");
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

        etContent.addTextChangedListener(textContentWatcher);
        etTitle.setText(title);
        etContent.setText(content);
        tvContentNum.setText(title.length() + "/2000");
    }

    TextWatcher textContentWatcher = new TextWatcher() {

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
            tvContentNum.setText(temp.length() + "/2000");
            if (temp.length() >= 2000) {
                toToast(EditorFriendTogetherSubTitleContentActivity.this, "您输入的字数已经超过了限制");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new UserIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.activity_editor_friend_together_sub_title_content_rl_back, R.id.activity_editor_friend_together_sub_title_content_rl_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_editor_friend_together_sub_title_content_rl_back:
                onBackPressed();
                break;
            case R.id.activity_editor_friend_together_sub_title_content_rl_complete:
//                if (mList.size() == 0) {
//                    toToast(EditorFriendTogetherSubTitleContentActivity.this, "请至少上传一张图片");
//                }else if(TextUtils.isEmpty(etTitle.getText().toString())||TextUtils.isEmpty(etContent.getText().toString())){
//                    toToast(EditorFriendTogetherSubTitleContentActivity.this, "请添加标题或内容");
//                } else {
                    complete();
//                }
                break;
        }
    }

    /**
     * 发布
     */
    private void complete() {

        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                dialog = WeiboDialogUtils.createLoadingDialog(EditorFriendTogetherSubTitleContentActivity.this, "请等待...");
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                if(mList.size()>0){
                    for (int i = 0; i < mList.size(); i++) {
                        list.add(mList.get(i).getPic());
                    }
                    Luban.with(EditorFriendTogetherSubTitleContentActivity.this)
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
                                    Log.e("222", list.size() + "..." + files.size());
                                    if (files.size() == list.size()) {
                                        for (int i = 0; i < files.size(); i++) {
                                            map.put("activity_files[" + i + "]", files.get(i));
                                        }
                                        Log.e("222", map.size() + "");
                                        e.onNext(map);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();
                }else {
                    e.onNext(map);
                }
            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {

                Map<String, String> describe = new HashMap<>();
                for (int i = 0; i < mList.size(); i++) {
                    describe.put("textarea_img[" + i + "]", mList.get(i).getDescribe());
                }

                ViseHttp.UPLOAD(NetConfig.editorFriendTogetherSubtitleContentUrl)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.editorFriendTogetherSubtitleContentUrl))
                        .addParam("title", etTitle.getText().toString())
                        .addParam("content", etContent.getText().toString())
                        .addParam("title_id", id)
                        .addParam("user_id", uid)
                        .addParams(describe)
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(EditorFriendTogetherSubTitleContentActivity.this, "保存成功");
                                        onBackPressed();
                                    }
                                    WeiboDialogUtils.closeDialog(dialog);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EditorFriendTogetherSubTitleContentActivity.this.finish();
    }
}
