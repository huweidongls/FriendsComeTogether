package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
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
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.IntercalationLocationModel;
import com.yiwo.friendscometogether.model.UserIntercalationPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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

public class InsertIntercalationActivity extends BaseActivity {

    @BindView(R.id.activity_insert_intercalation_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_insert_intercalation_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_insert_intercalation_et_title)
    EditText etTitle;
    @BindView(R.id.activity_insert_intercalation_et_content)
    EditText etContent;
    @BindView(R.id.activity_insert_intercalation_tv_text_num)
    TextView tvContentNum;
    @BindView(R.id.activity_insert_intercalation_rl_complete)
    RelativeLayout rlComplete;
    @BindView(R.id.activity_insert_intercalation_rl_intercalation_location)
    RelativeLayout rlIntercalationLocation;
    @BindView(R.id.activity_insert_intercalation_tv_select)
    TextView tvSelect;

    private IntercalationAdapter adapter;
    private List<UserIntercalationPicModel> mList;

    private static final int REQUEST_CODE = 0x00000011;

    private String[] itemId;
    private String[] itemName;
    private String yourChoiceId = "0";
    private String yourChoiceName = "";

    private String fmID = "";

    private SpImp spImp;
    private String uid = "";

    private List<File> files = new ArrayList<>();

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_intercalation);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(InsertIntercalationActivity.this);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();

        Intent intent = getIntent();
        fmID = intent.getStringExtra("id");

        mList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(InsertIntercalationActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new IntercalationAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new IntercalationAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(InsertIntercalationActivity.this, REQUEST_CODE); // 打开相册
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

        etContent.addTextChangedListener(textContentWatcher);

        ViseHttp.POST(NetConfig.intercalationLocationUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.intercalationLocationUrl))
                .addParam("id", fmID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                IntercalationLocationModel model = gson.fromJson(data, IntercalationLocationModel.class);
                                itemId = new String[model.getObj().size()];
                                itemName = new String[model.getObj().size()];
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    itemId[i] = model.getObj().get(i).getFfID();
                                    itemName[i] = model.getObj().get(i).getFftitle();
                                }
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
                toToast(InsertIntercalationActivity.this, "您输入的字数已经超过了限制");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Log.e("222", pic.get(0));
            for (int i = 0; i < pic.size(); i++) {
                mList.add(new UserIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.activity_insert_intercalation_rl_back, R.id.activity_insert_intercalation_rl_complete, R.id.activity_insert_intercalation_rl_intercalation_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_insert_intercalation_rl_back:
                onBackPressed();
                break;
            case R.id.activity_insert_intercalation_rl_complete:
                if (TextUtils.isEmpty(etTitle.getText().toString()) || TextUtils.isEmpty(etContent.getText().toString())) {
                    toToast(InsertIntercalationActivity.this, "请完善信息");
                } else {
                    if (mList.size() == 0) {
                        toToast(InsertIntercalationActivity.this, "请至少上传一张图片");
                    }else if(TextUtils.isEmpty(etTitle.getText().toString())){
                        toToast(InsertIntercalationActivity.this, "请添加标题");
                    } else {
                        complete();
                    }
                }
                break;
            case R.id.activity_insert_intercalation_rl_intercalation_location:
                if (itemName.length == 0) {
                    toToast(InsertIntercalationActivity.this, "暂无子标题");
                } else {
                    AlertDialog.Builder singleChoiceDialog =
                            new AlertDialog.Builder(InsertIntercalationActivity.this);
                    singleChoiceDialog.setTitle("请选择标签");
                    // 第二个参数是默认选项，此处设置为0
                    singleChoiceDialog.setSingleChoiceItems(itemName, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    yourChoiceName = itemName[which];
                                    yourChoiceId = itemId[which];
                                }
                            });
                    singleChoiceDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TextUtils.isEmpty(yourChoiceName)) {
                                        tvSelect.setText(itemName[0]);
                                        yourChoiceId = itemId[0];
                                    } else {
                                        tvSelect.setText(yourChoiceName);
                                        yourChoiceName = "";
                                    }
                                }
                            });
                    singleChoiceDialog.show();
                }
                break;
        }
    }

    /**
     * 发布监听
     */
    private void complete() {

        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                dialog = WeiboDialogUtils.createLoadingDialog(InsertIntercalationActivity.this, "请等待...");
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPic());
                }
                Luban.with(InsertIntercalationActivity.this)
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
                                if (files.size() == list.size()) {
                                    for (int i = 0; i < files.size(); i++) {
                                        map.put("images[" + i + "]", files.get(i));
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
            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {

                String describe = "";
                for (int i = 0; i < mList.size(); i++) {
                    describe = describe + mList.get(i).getDescribe() + "|";
                }
                Log.e("222", describe);

                ViseHttp.UPLOAD(NetConfig.insertIntercalationUrl)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.insertIntercalationUrl))
                        .addParam("title", etTitle.getText().toString())
                        .addParam("content", etContent.getText().toString())
                        .addParam("id", fmID)
                        .addParam("uid", uid)
                        .addParam("describe", describe)
                        .addParam("position", itemId.length == 0 ? "0" : yourChoiceId)
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(InsertIntercalationActivity.this, "创建成功");
                                        WeiboDialogUtils.closeDialog(dialog);
                                        onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InsertIntercalationActivity.this.finish();
    }
}
