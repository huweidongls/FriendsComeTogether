package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.SetPasswordDialog;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.JsonBean;
import com.yiwo.friendscometogether.model.ModifyFriendRememberModel;
import com.yiwo.friendscometogether.model.UserActiveListModel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.GetJsonDataUtil;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.widget.CustomDatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class ModifyFriendRememberActivity extends TakePhotoActivity {

    @BindView(R.id.activity_create_friend_remember_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_create_friend_remember_rl_edit_title)
    RelativeLayout rlEditTitle;
    @BindView(R.id.activity_create_friend_remember_rl_edit_content)
    RelativeLayout rlEditContent;
    @BindView(R.id.activity_create_friend_remember_rl_time_start)
    RelativeLayout rlTimeStart;
    @BindView(R.id.activity_create_friend_remember_tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.activity_create_friend_remember_rl_time_end)
    RelativeLayout rlTimeEnd;
    @BindView(R.id.activity_create_friend_remember_tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.activity_create_friend_remember_rl_activity_city)
    RelativeLayout rlSelectCity;
    @BindView(R.id.activity_create_friend_remember_tv_activity_city)
    EditText tvCity;
    @BindView(R.id.activity_create_friend_remember_rl_price)
    RelativeLayout rlPrice;
    @BindView(R.id.activity_create_friend_remember_rl_complete)
    RelativeLayout rlComplete;
    @BindView(R.id.activity_create_friend_remember_rl_set_password)
    RelativeLayout rlPassword;
    @BindView(R.id.activity_create_friend_remember_iv_add)
    ImageView ivAdd;
    @BindView(R.id.activity_create_friend_remember_iv_title)
    ImageView ivTitle;
    @BindView(R.id.activity_create_friend_remember_tv_first_iv)
    TextView tvFirstIv;
    @BindView(R.id.activity_create_friend_remember_iv_delete)
    ImageView ivDelete;
    @BindView(R.id.activity_create_friend_remember_tv_title)
    EditText etTitle;
    @BindView(R.id.activity_create_friend_remember_tv_content)
    EditText etContent;
    @BindView(R.id.activity_create_friend_remember_rl_label)
    RelativeLayout rlLabel;
    @BindView(R.id.activity_create_friend_remember_tv_label)
    TextView tvLabel;
    @BindView(R.id.activity_create_friend_remember_et_price)
    EditText etPrice;
    @BindView(R.id.activity_create_friend_remember_tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.activity_create_friend_remember_tv_content_num)
    TextView tvContentNum;
    @BindView(R.id.activity_create_friend_remember_rl_active_title)
    RelativeLayout rlActiveTitle;
    @BindView(R.id.activity_create_friend_remember_tv_active_title)
    TextView tvActiveTitle;
    @BindView(R.id.activity_create_friend_remember_rl_is_intercalation)
    RelativeLayout rlIsIntercalation;
    @BindView(R.id.activity_create_friend_remember_tv_is_intercalation)
    TextView tvIsIntercalation;
    @BindView(R.id.activity_create_friend_remember_tv_encryption)
    TextView tvPassword;

    private String fmId = "";

    private int mYear;
    private int mMonth;
    private int mDay;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private PopupWindow popupWindow;

    private static final int REQUEST_CODE = 0x00000011;

    private String[] itemId;
    private String[] itemName;
    private String yourChoiceId = "";
    private String yourChoiceName = "";

    private SpImp spImp;
    private String uid = "";

    private String images = "";

    private Dialog dialog;

    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;

    private String password;

    /**
     * 是否替换图片，true为替换
     */
    private boolean isPic = false;

    private CustomDatePicker customDatePicker1, customDatePicker2;
    private String now;

    private List<UserLabelModel.ObjBean> labelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_friend_remember);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(ModifyFriendRememberActivity.this);

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        spImp = new SpImp(ModifyFriendRememberActivity.this);

        init();
        initDatePicker();

    }

    private void init() {

        uid = spImp.getUID();
        Intent intent = getIntent();
        fmId = intent.getStringExtra("id");

        Observable.just("").subscribeOn(Schedulers.newThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                initJsonData();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                labelList = new ArrayList<>();
                                labelList.addAll(userLabelModel.getObj());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        ViseHttp.POST(NetConfig.userActiveListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.userActiveListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserActiveListModel model = gson.fromJson(data, UserActiveListModel.class);
                                activeList = model.getObj();
                                activeId = new String[model.getObj().size()];
                                activeName = new String[model.getObj().size()];
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    activeId[i] = model.getObj().get(i).getPfID();
                                    activeName[i] = model.getObj().get(i).getPftitle();
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

        ViseHttp.POST(NetConfig.modifyFriendRememberUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.modifyFriendRememberUrl))
                .addParam("id", fmId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ModifyFriendRememberModel model = gson.fromJson(data, ModifyFriendRememberModel.class);
                                etTitle.setText(model.getObj().getFmtitle());
                                etContent.setText(model.getObj().getFmcontent());
                                if(!TextUtils.isEmpty(model.getObj().getFmpartyName())){
                                    yourChoiceActiveId = model.getObj().getFmpartyID();
                                    tvActiveTitle.setText(model.getObj().getFmpartyName());
                                }
                                tvTimeStart.setText(model.getObj().getFmgotime());
                                tvTimeEnd.setText(model.getObj().getFmendtime());
                                tvCity.setText(model.getObj().getFmaddress());
                                etPrice.setText(model.getObj().getPercapitacost());
                                yourChoiceId = model.getObj().getFmlable();
                                tvLabel.setText(model.getObj().getFmlableName());
                                if(!TextUtils.isEmpty(model.getObj().getAccesspassword())){
                                    tvPassword.setText("已添加密码("+model.getObj().getAccesspassword()+")");
                                    password = model.getObj().getAccesspassword();
                                }
                                if(model.getObj().getInsertatext().equals("0")){
                                    tvIsIntercalation.setText("是");
                                }else {
                                    tvIsIntercalation.setText("否");
                                }
                                Picasso.with(ModifyFriendRememberActivity.this).load(model.getObj().getFmpic()).into(ivTitle);
                                ivTitle.setVisibility(View.VISIBLE);
                                tvFirstIv.setVisibility(View.VISIBLE);
                                ivDelete.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        etTitle.addTextChangedListener(textTitleWatcher);
        etContent.addTextChangedListener(textContentWatcher);

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
            tvTitleNum.setText(temp.length()+"/30");
            if(temp.length()>=30){
                Toast.makeText(ModifyFriendRememberActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };

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
            tvContentNum.setText(temp.length()+"/2000");
            if(temp.length()>=2000){
                Toast.makeText(ModifyFriendRememberActivity.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTimeStart.setText(time);
            }
        }, now, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTimeEnd.setText(time);
            }
        }, now, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String url = result.getImage().getCompressPath();
        Log.e("222", result.getImage().getCompressPath());
        Glide.with(ModifyFriendRememberActivity.this).load("file://"+url).into(ivTitle);
        images = url;
        ivTitle.setVisibility(View.VISIBLE);
        tvFirstIv.setVisibility(View.VISIBLE);
        ivDelete.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Log.e("222", scList.get(0));
            images = scList.get(0);
            Picasso.with(ModifyFriendRememberActivity.this).load("file://" + scList.get(0)).into(ivTitle);
            ivTitle.setVisibility(View.VISIBLE);
            tvFirstIv.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
        }
    }

    private String yourChoice = "";
    @OnClick({R.id.activity_create_friend_remember_rl_back, R.id.activity_create_friend_remember_rl_edit_title, R.id.activity_create_friend_remember_rl_edit_content,
            R.id.activity_create_friend_remember_rl_time_start, R.id.activity_create_friend_remember_rl_time_end, R.id.activity_create_friend_remember_rl_activity_city,
            R.id.activity_create_friend_remember_rl_price, R.id.activity_create_friend_remember_rl_complete, R.id.activity_create_friend_remember_rl_set_password,
            R.id.activity_create_friend_remember_iv_add, R.id.activity_create_friend_remember_iv_delete, R.id.activity_create_friend_remember_rl_label,
            R.id.activity_create_friend_remember_rl_active_title, R.id.activity_create_friend_remember_rl_is_intercalation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_create_friend_remember_rl_back:
                onBackPressed();
                break;
            case R.id.activity_create_friend_remember_rl_edit_title:
                break;
            case R.id.activity_create_friend_remember_rl_edit_content:
                break;
            case R.id.activity_create_friend_remember_rl_time_start:
//                new DatePickerDialog(ModifyFriendRememberActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                customDatePicker1.show(now);
                break;
            case R.id.activity_create_friend_remember_rl_time_end:
//                new DatePickerDialog(ModifyFriendRememberActivity.this, onDateSetListenerEnd, mYear, mMonth, mDay).show();
                customDatePicker2.show(now);
                break;
            case R.id.activity_create_friend_remember_rl_activity_city:
//                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText() + "-" +
//                                options2Items.get(options1).get(options2) + "-" +
//                                options3Items.get(options1).get(options2).get(options3);
//                        tvCity.setText(tx);
//                    }
//                })
//                        .setTitleText("城市选择")
//                        .setDividerColor(Color.BLACK)
//                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                        .setContentTextSize(20)
//                        .build();
//
//        /*pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
//                pvOptions.show();
                break;
            case R.id.activity_create_friend_remember_rl_price:

                break;
            case R.id.activity_create_friend_remember_rl_complete:
                if(TextUtils.isEmpty(etTitle.getText().toString())||TextUtils.isEmpty(tvLabel.getText().toString())){
                    Toast.makeText(ModifyFriendRememberActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }else {
                    onSave();
                }
                break;
            case R.id.activity_create_friend_remember_rl_set_password:
                SetPasswordDialog setPasswordDialog = new SetPasswordDialog(ModifyFriendRememberActivity.this, new SetPasswordDialog.SetPasswordListener() {
                    @Override
                    public void setActivityText(String s) {
                        if (!StringUtils.isEmpty(s)) {
                            password = s;
                            tvPassword.setText("已添加密码(" + s + ")");
                        }else {
                            password = s;
                            tvPassword.setText("不设密码");
                        }
                    }
                });
                setPasswordDialog.show();
                break;
            case R.id.activity_create_friend_remember_iv_add:
//                //限数量的多选(比喻最多9张)
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(true)  //设置是否单选
//                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
////                        .setSelected(selected) // 把已选的图片传入默认选中。
//                        .start(ModifyFriendRememberActivity.this, REQUEST_CODE); // 打开相册

                // 初始化TakePhoto选取头像的配置
                TakePhoto takePhoto = getTakePhoto();
                CropOptions.Builder builder = new CropOptions.Builder();
                builder.setAspectX(1500).setAspectY(744);
                builder.setWithOwnCrop(true);
                File file = new File(Environment.getExternalStorageDirectory(),
                        "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    boolean mkdirs = file.getParentFile().mkdirs();
                    if (!mkdirs) {
//                        ToastUtil.showShort("文件目录创建失败");
                        Toast.makeText(ModifyFriendRememberActivity.this, "文件目录创建失败", Toast.LENGTH_SHORT).show();
                    }
                }
                Uri imageUri = Uri.fromFile(file);
                CompressConfig config = new CompressConfig.Builder()
                        .setMaxSize(102400)
                        .setMaxPixel(400)
                        .enableReserveRaw(true)
                        .create();
                takePhoto.onEnableCompress(config, true);
                takePhoto.onPickFromDocumentsWithCrop(imageUri, builder.create());

                break;
            case R.id.activity_create_friend_remember_iv_delete:
                ivDelete.setVisibility(View.GONE);
                ivTitle.setVisibility(View.INVISIBLE);
                tvFirstIv.setVisibility(View.INVISIBLE);
                isPic = true;
                break;
            case R.id.activity_create_friend_remember_rl_label:
//                AlertDialog.Builder singleChoiceDialog =
//                        new AlertDialog.Builder(ModifyFriendRememberActivity.this);
//                singleChoiceDialog.setTitle("请选择标签");
//                // 第二个参数是默认选项，此处设置为0
//                singleChoiceDialog.setSingleChoiceItems(itemName, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                yourChoiceName = itemName[which];
//                                yourChoiceId = itemId[which];
//                            }
//                        });
//                singleChoiceDialog.setPositiveButton("确定",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (TextUtils.isEmpty(yourChoiceName)) {
//                                    tvLabel.setText(itemName[0]);
//                                    yourChoiceId = itemId[0];
//                                } else {
//                                    tvLabel.setText(yourChoiceName);
//                                    yourChoiceName = "";
//                                }
//                            }
//                        });
//                singleChoiceDialog.show();

                OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText() + "-" +
//                                options2Items.get(options1).get(options2) + "-" +
//                                options3Items.get(options1).get(options2).get(options3);
                        tvLabel.setText(labelList.get(options1).getPickerViewText());
                        yourChoiceId = labelList.get(options1).getLID();
                    }
                })
                        .setTitleText("标签选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
                pvOptions1.setPicker(labelList);//三级选择器
                pvOptions1.show();

                break;
            case R.id.activity_create_friend_remember_rl_active_title:
                //活动标题
                if(activeList.size()>0){
                    AlertDialog.Builder singleChoiceDialog1 =
                            new AlertDialog.Builder(ModifyFriendRememberActivity.this);
                    singleChoiceDialog1.setTitle("请选择活动标题");
                    // 第二个参数是默认选项，此处设置为0
                    singleChoiceDialog1.setSingleChoiceItems(activeName, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    yourChoiceActiveName = activeName[which];
                                    yourChoiceActiveId = activeId[which];
                                }
                            });
                    singleChoiceDialog1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TextUtils.isEmpty(yourChoiceActiveName)) {
                                        tvActiveTitle.setText(activeName[0]);
                                        yourChoiceActiveId = activeId[0];
                                    } else {
                                        tvActiveTitle.setText(yourChoiceActiveName);
                                        yourChoiceActiveName = "";
                                    }
                                }
                            });
                    singleChoiceDialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    singleChoiceDialog1.show();
                }else {
                    Toast.makeText(ModifyFriendRememberActivity.this, "暂无活动", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_create_friend_remember_rl_is_intercalation:
                final String[] items1 = { "是","否" };

                AlertDialog.Builder singleChoiceDialog1 =
                        new AlertDialog.Builder(ModifyFriendRememberActivity.this);
                singleChoiceDialog1.setTitle("请选择是否允许队友插文");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog1.setSingleChoiceItems(items1, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice = items1[which];
                            }
                        });
                singleChoiceDialog1.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(TextUtils.isEmpty(yourChoice)){
                                    tvIsIntercalation.setText("是");
                                }else {
                                    tvIsIntercalation.setText(yourChoice);
                                    yourChoice = "";
                                }
                            }
                        });
                singleChoiceDialog1.show();
                break;
        }
    }

    /**
     * 保存
     */
    private void onSave() {

        dialog = WeiboDialogUtils.createLoadingDialog(ModifyFriendRememberActivity.this, "请等待...");
        if(isPic){
            Observable<File> observable = Observable.create(new ObservableOnSubscribe<File>() {
                @Override
                public void subscribe(final ObservableEmitter<File> e) throws Exception {
//                        File file = new File(images);
                    Luban.with(ModifyFriendRememberActivity.this)
                            .load(images)
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
                    ViseHttp.UPLOAD(NetConfig.saveFriendRememberUrl)
                            .addHeader("Content-Type","multipart/form-data")
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.saveFriendRememberUrl))
                            .addParam("fmtitle", etTitle.getText().toString())
                            .addParam("fmcontent", etContent.getText().toString())
                            .addParam("fmaddress", tvCity.getText().toString())
                            .addParam("fmlable", yourChoiceId)
                            .addParam("fmgotime", tvTimeStart.getText().toString())
                            .addParam("fmendtime", tvTimeEnd.getText().toString())
                            .addParam("percapitacost", etPrice.getText().toString())
                            .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
                            .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                            .addParam("accesspassword", password)
                            .addParam("id", fmId)
                            .addFile("fmpic", value)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.e("222", data);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            Toast.makeText(ModifyFriendRememberActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            WeiboDialogUtils.closeDialog(dialog);
                                            onBackPressed();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    Log.e("222", "网络请求失败"+errMsg);
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
        }else {
            ViseHttp.POST(NetConfig.saveFriendRememberUrl)
                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.saveFriendRememberUrl))
                    .addParam("fmtitle", etTitle.getText().toString())
                    .addParam("fmcontent", etContent.getText().toString())
                    .addParam("fmaddress", tvCity.getText().toString())
                    .addParam("fmlable", yourChoiceId)
                    .addParam("fmgotime", tvTimeStart.getText().toString())
                    .addParam("fmendtime", tvTimeEnd.getText().toString())
                    .addParam("percapitacost", etPrice.getText().toString())
                    .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
                    .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                    .addParam("accesspassword", password)
                    .addParam("id", fmId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Toast.makeText(ModifyFriendRememberActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    WeiboDialogUtils.closeDialog(dialog);
                                    onBackPressed();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.e("222", "网络请求失败"+errMsg);
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    });
        }

    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            }
            tvTimeStart.setText(days);
        }
    };

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            }
            tvTimeEnd.setText(days);
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ModifyFriendRememberActivity.this.finish();
    }
}
