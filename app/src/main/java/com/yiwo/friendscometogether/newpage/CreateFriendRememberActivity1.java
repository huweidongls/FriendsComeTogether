package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.model.JsonBean;
import com.yiwo.friendscometogether.model.NewUserIntercalationPicModel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.model.UserReleaseModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.LabelAdapter;
import com.yiwo.friendscometogether.newadapter.LabelChooseOneAdapter;
import com.yiwo.friendscometogether.newadapter.NewCreateFriendRemberIntercalationAdapter;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.CreateIntercalationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.GetJsonDataUtil;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.widget.CustomDatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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

public class CreateFriendRememberActivity1 extends TakePhotoActivity {

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
    @BindView(R.id.rl_more)
    RelativeLayout rlMore;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.activity_create_intercalation_rv)
    RecyclerView recyclerView;

    private NewCreateFriendRemberIntercalationAdapter adapter;
    private LabelChooseOneAdapter labelAdapter;
    private List<NewUserIntercalationPicModel> mList;

    private int mYear;
    private int mMonth;
    private int mDay;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private PopupWindow popupWindow;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int REQUEST_CODE1 = 0x00000012;
    private static final int REQUEST_CODE_GET_CITY = 1;
    private static final int REQUEST_CODE_SUO_SHU_HUO_DONG = 2;
    private List<File> files = new ArrayList<>();

    /**
     * 标签id
     */
    private String yourChoiceId = "";

    private SpImp spImp;
    private String uid = "";

    private String images = "";

    private Dialog dialog;

    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<GetFriendActiveListModel.ObjBean> activeList;

    private String password;

    private List<UserLabelModel.ObjBean> labelList;

    private CustomDatePicker customDatePicker1, customDatePicker2;
    private String now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend_remember);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        spImp = new SpImp(CreateFriendRememberActivity1.this);
        tvCity.setText(spImp.getLastCreateYouJiAddress());
        tvLabel.setText(spImp.getLastCreateYouJiLabelText());
        yourChoiceId = spImp.getLastCreateYouJiLabelId();
        init();
        initDatePicker();
        initUpData();
        mList.clear();
        List<String> list_path = getIntent().getStringArrayListExtra("paths");
        for (int i = 0;i<list_path.size();i++){
            Log.d("ddaad",list_path.get(i));
            NewUserIntercalationPicModel model = new NewUserIntercalationPicModel(list_path.get(i),"");
            if (i==0){
                model.setFirstPic(true);
            }else {
                model.setFirstPic(false);
            }
            mList.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化上传图片
     */
    private void initUpData() {

        mList = new ArrayList<>();
//        GridLayoutManager manager = new GridLayoutManager(CreateFriendRememberActivity1.this, 3);
//        recyclerView.setLayoutManager(manager);
        LinearLayoutManager manager = new LinearLayoutManager(CreateFriendRememberActivity1.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new NewCreateFriendRemberIntercalationAdapter(mList);
        adapter.setDescribe(false);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new NewCreateFriendRemberIntercalationAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(CreateFriendRememberActivity1.this, REQUEST_CODE1); // 打开相册
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnAddDescribeListener() {
            @Override
            public void onAddDescribe(int i, String s) {
                mList.get(i).setDescribe(s);
                adapter.notifyDataSetChanged();
            }
        }, new NewCreateFriendRemberIntercalationAdapter.OnSetFirstPicListienner() {
            @Override
            public void onSetFirst(final int postion) {
               final AlertDialog.Builder builder = new AlertDialog.Builder(CreateFriendRememberActivity1.this);
               builder.setMessage("设置为首图？")
                       .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               for (int i = 0; i<mList.size();i++){
                                   if (i == postion){
//                                       NewUserIntercalationPicModel model = mList.get(postion);
//                                       model.setFirstPic(true);
                                       mList.get(i).setFirstPic(true);
                                   }else {
                                       mList.get(i).setFirstPic(false);
                                   }
                               }
                               adapter.notifyDataSetChanged();
                           }
                       })
                       .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                           }
                       }).show();
            }
        });

    }

    private void init() {

        uid = spImp.getUID();

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

        ViseHttp.POST(NetConfig.getFriendActiveListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.getFriendActiveListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GetFriendActiveListModel model = gson.fromJson(data, GetFriendActiveListModel.class);
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
            tvTitleNum.setText(temp.length()+"/300");
            if(temp.length()>=300){
                Toast.makeText(CreateFriendRememberActivity1.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CreateFriendRememberActivity1.this, "您输入的字数已经超过了限制", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTimeStart.setText(time.substring(0,10));
            }
        }, "1900-01-01 00:00", "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTimeEnd.setText(time.substring(0,10));
            }
        }, "1900-01-01 00:00", "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    private String yourChoice = "";
    @OnClick({R.id.activity_create_friend_remember_rl_back, R.id.activity_create_friend_remember_rl_edit_title, R.id.activity_create_friend_remember_rl_edit_content,
            R.id.activity_create_friend_remember_rl_time_start, R.id.activity_create_friend_remember_rl_time_end, R.id.activity_create_friend_remember_rl_activity_city,
            R.id.activity_create_friend_remember_rl_price, R.id.activity_create_friend_remember_rl_complete, R.id.activity_create_friend_remember_rl_set_password,
            R.id.activity_create_friend_remember_iv_add, R.id.activity_create_friend_remember_iv_delete, R.id.activity_create_friend_remember_rl_label,
            R.id.activity_create_friend_remember_rl_active_title, R.id.activity_create_friend_remember_rl_is_intercalation, R.id.rl_more,R.id.rl_choose_address})
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
//                new DatePickerDialog(CreateFriendRememberActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                customDatePicker1.show(now);
                break;
            case R.id.activity_create_friend_remember_rl_time_end:
//                new DatePickerDialog(CreateFriendRememberActivity.this, onDateSetListenerEnd, mYear, mMonth, mDay).show();
                customDatePicker2.show(now);
                break;
            case R.id.rl_choose_address:
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
                Intent it = new Intent(CreateFriendRememberActivity1.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "createYouJi");
                startActivityForResult(it, REQUEST_CODE_GET_CITY);
                break;
            case R.id.activity_create_friend_remember_rl_price:

                break;
            case R.id.activity_create_friend_remember_rl_complete:
                if(TextUtils.isEmpty(etTitle.getText().toString())){
                    Toast.makeText(CreateFriendRememberActivity1.this, "请填写标题", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(tvLabel.getText().toString())){
                    Toast.makeText(CreateFriendRememberActivity1.this, "请选择标签", Toast.LENGTH_SHORT).show();
                }
                //20190225 限制友记上传图片数量 1
                else if(mList.size()<1){
                    Toast.makeText(CreateFriendRememberActivity1.this, "请至少上传1张照片", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(tvCity.getText().toString())){
                    Toast.makeText(CreateFriendRememberActivity1.this, "请填写地点", Toast.LENGTH_SHORT).show();
                }
                else {
                    //判断如果填写开始时间和结束时间   结束时间必须大于开始时间
                    if (!tvTimeStart.getText().toString().equals("")&&!tvTimeEnd.getText().toString().equals("")){
                        if (StringUtils.getTimeCompareSize(tvTimeStart.getText().toString(),tvTimeEnd.getText().toString())==1){
                            Toast.makeText(CreateFriendRememberActivity1.this, "活动开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    showCompletePopupwindow();
                }
                break;
            case R.id.activity_create_friend_remember_rl_set_password:
                SetPasswordDialog setPasswordDialog = new SetPasswordDialog(CreateFriendRememberActivity1.this, new SetPasswordDialog.SetPasswordListener() {
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
//                        .start(CreateFriendRememberActivity.this, REQUEST_CODE); // 打开相册

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
                        Toast.makeText(CreateFriendRememberActivity1.this, "文件目录创建失败", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.activity_create_friend_remember_rl_label:
                if (labelList.size()==0){
                    Toast.makeText(CreateFriendRememberActivity1.this,"暂无标签",Toast.LENGTH_SHORT).show();
                    break;
                }
                showFriendRememberLabelPop();
//                OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        //返回的分别是三个级别的选中位置
////                        String tx = options1Items.get(options1).getPickerViewText() + "-" +
////                                options2Items.get(options1).get(options2) + "-" +
////                                options3Items.get(options1).get(options2).get(options3);
//                        tvLabel.setText(labelList.get(options1).getPickerViewText());
//                        yourChoiceId = labelList.get(options1).getLID();
//                    }
//                })
//                        .setTitleText("标签选择")
//                        .setDividerColor(Color.BLACK)
//                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                        .setContentTextSize(20)
//                        .build();
//
//        /*pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//                pvOptions1.setPicker(labelList);//三级选择器
//                pvOptions1.show();
                break;
            case R.id.activity_create_friend_remember_rl_active_title:
                //活动标题
                if(activeList.size()>0){
                    Intent it_suoshu = new Intent(CreateFriendRememberActivity1.this, SuoShuHuoDongActivity.class);
                    it_suoshu.putExtra("list", (Serializable) activeList);
                    startActivityForResult(it_suoshu, REQUEST_CODE_SUO_SHU_HUO_DONG);
                }else {
                    Toast.makeText(CreateFriendRememberActivity1.this, "暂无活动", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_create_friend_remember_rl_is_intercalation:
                final String[] items1 = { "是","否" };

                AlertDialog.Builder singleChoiceDialog1 =
                        new AlertDialog.Builder(CreateFriendRememberActivity1.this);
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
            case R.id.rl_more:
                rlMore.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showFriendRememberLabelPop() {
        View view = LayoutInflater.from(CreateFriendRememberActivity1.this).inflate(R.layout.popupwindow_rv_choose_label, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RelativeLayout rvCancel = view.findViewById(R.id.rv_cancel);
        RelativeLayout rvSure = view.findViewById(R.id.rv_sure);
        RecyclerView recyclerView = view.findViewById(R.id.rv);

        GridLayoutManager manager = new GridLayoutManager(CreateFriendRememberActivity1.this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        labelAdapter = new LabelChooseOneAdapter(labelList);
        recyclerView.setAdapter(labelAdapter);
        final String[] tmpLabelText = {""};
        final String[] tmpLabelId = {""};
        labelAdapter.setListener(new LabelChooseOneAdapter.OnSelectLabelListener() {
            @Override
            public void onSelete(int i) {
                for (UserLabelModel.ObjBean bean : labelList){
                    bean.setChoose(false);
                }
                labelList.get(i).setChoose(true);
                tmpLabelText[0] = labelList.get(i).getLname();
                tmpLabelId[0] = labelList.get(i).getLID();
                labelAdapter.notifyDataSetChanged();
            }
        });
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
        rvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            popupWindow.dismiss();
            }
        });
        rvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLabel.setText(tmpLabelText[0]);
                yourChoiceId = tmpLabelId[0];
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String url = result.getImage().getCompressPath();
        Log.e("222", result.getImage().getCompressPath());

//        String oldName = url;
//        String newName = url.substring(0, url.lastIndexOf("."))+".png";
//        Log.e("222", newName);
//        renameFile(oldName, newName);

        Picasso.with(CreateFriendRememberActivity1.this).load("file://"+url).into(ivTitle);
        images = url;
        ivTitle.setVisibility(View.VISIBLE);
        tvFirstIv.setVisibility(View.VISIBLE);
        ivDelete.setVisibility(View.VISIBLE);
    }

    public void renameFile(String file, String toFile) {
        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件   
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            System.out.println("File does not exist: " + file);
            return;
        }
        File newFile = new File(toFile);
        //修改文件名   
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Log.e("222", scList.get(0));
            images = scList.get(0);
            Picasso.with(CreateFriendRememberActivity1.this).load("file://" + scList.get(0)).into(ivTitle);
            ivTitle.setVisibility(View.VISIBLE);
            tvFirstIv.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
        }
        if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new NewUserIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
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
        if (requestCode == REQUEST_CODE_SUO_SHU_HUO_DONG && resultCode == 1){
            GetFriendActiveListModel.ObjBean bean = (GetFriendActiveListModel.ObjBean) data.getSerializableExtra("suoshuhuodong");
            yourChoiceActiveName = bean.getPftitle();
            yourChoiceActiveId = bean.getPfID();
            tvActiveTitle.setText(yourChoiceActiveName);
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateFriendRememberActivity1.this);
        builder.setMessage("是否保存至草稿")
                .setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //判断如果填写开始时间和结束时间   结束时间必须大于开始时间
                        if (!tvTimeStart.getText().toString().equals("")&&!tvTimeEnd.getText().toString().equals("")){
                            if (StringUtils.getTimeCompareSize(tvTimeStart.getText().toString(),tvTimeEnd.getText().toString())==1){
                                Toast.makeText(CreateFriendRememberActivity1.this, "活动开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        next_tocaogao();
                    }
                }).setPositiveButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CreateFriendRememberActivity1.this.finish();
            }
        });
        if(TextUtils.isEmpty(etTitle.getText().toString())){
            CreateFriendRememberActivity1.this.finish();
        }
        //20190225 限制友记上传图片数量 1
        else if(mList.size()<1){
            CreateFriendRememberActivity1.this.finish();
        }else {
            builder.show();
        }

    }

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

    private void showCompletePopupwindow() {

        View view = LayoutInflater.from(CreateFriendRememberActivity1.this).inflate(R.layout.popupwindow_complete, null);
        ScreenAdapterTools.getInstance().loadView(view);

        TextView tvRelease = view.findViewById(R.id.popupwindow_complete_tv_release);
//        TextView tvSave = view.findViewById(R.id.popupwindow_complete_tv_save);
        TextView tvNext = view.findViewById(R.id.popupwindow_complete_tv_next);
        TextView tvCancel = view.findViewById(R.id.popupwindow_complete_tv_cancel);

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        tvRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = WeiboDialogUtils.createLoadingDialog(CreateFriendRememberActivity1.this, "请等待...");
                spImp.setLastCreateYouJiLabelText(tvLabel.getText().toString());
                spImp.setLastCreateYouJiLabelId(yourChoiceId);
                spImp.setLastCreateYouJiAddress(tvCity.getText().toString());
                Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
                    @Override
                    public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                        final Map<String, File> map = new LinkedHashMap<>();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < mList.size(); i++) {
                            if (mList.get(i).getFirstPic()){
                                list.add(0,mList.get(i).getPic());
                            }else {
                                list.add(mList.get(i).getPic());
                            }
                        }
                        Luban.with(CreateFriendRememberActivity1.this)
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
                                                map.put("fmpic[" + i + "]", files.get(i));
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
                        ViseHttp.UPLOAD(NetConfig.userRelease)
                                .addHeader("Content-Type","multipart/form-data")
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userRelease))
                                .addParam("fmtitle", etTitle.getText().toString())
                                .addParam("fmcontent", etContent.getText().toString())
                                .addParam("fmaddress", tvCity.getText().toString())
                                .addParam("uid", uid)
                                .addParam("fmlable", yourChoiceId)
                                .addParam("fmgotime", tvTimeStart.getText().toString())
                                .addParam("fmendtime", tvTimeEnd.getText().toString())
                                .addParam("percapitacost", etPrice.getText().toString())
                                .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
                                .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                                .addParam("accesspassword", password)
                                .addParam("type", "0")
                                .addFiles(value)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("222", data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                Toast.makeText(CreateFriendRememberActivity1.this, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                                                WeiboDialogUtils.closeDialog(dialog);
                                                finish();
                                            }else {
                                                WeiboDialogUtils.closeDialog(dialog);
                                                Toast.makeText(CreateFriendRememberActivity1.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            WeiboDialogUtils.closeDialog(dialog);
                                            Toast.makeText(CreateFriendRememberActivity1.this, "上传失败", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        Log.e("222", errMsg);
                                        WeiboDialogUtils.closeDialog(dialog);
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        WeiboDialogUtils.closeDialog(dialog);
                    }

                    @Override
                    public void onComplete() {
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                };
                observable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

    }
    private void next(){//保存草稿并跳转续写
        dialog = WeiboDialogUtils.createLoadingDialog(CreateFriendRememberActivity1.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
//                        File file = new File(images);
//                        Luban.with(CreateFriendRememberActivity.this)
//                                .load(images)
//                                .ignoreBy(100)
//                                .filter(new CompressionPredicate() {
//                                    @Override
//                                    public boolean apply(String path) {
//                                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                                    }
//                                })
//                                .setCompressListener(new OnCompressListener() {
//                                    @Override
//                                    public void onStart() {
//                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                    }
//
//                                    @Override
//                                    public void onSuccess(File file) {
//                                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                                        e.onNext(file);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        // TODO 当压缩过程出现问题时调用
//                                    }
//                                }).launch();

                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPic());
                }
                Luban.with(CreateFriendRememberActivity1.this)
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
                                        map.put("fmpic[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                WeiboDialogUtils.closeDialog(dialog);
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
                ViseHttp.UPLOAD(NetConfig.userRelease)
                        .addHeader("Content-Type","multipart/form-data")
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userRelease))
                        .addParam("fmtitle", etTitle.getText().toString())
                        .addParam("fmcontent", etContent.getText().toString())
                        .addParam("fmaddress", tvCity.getText().toString())
                        .addParam("uid", uid)
                        .addParam("fmlable", yourChoiceId)
                        .addParam("fmgotime", tvTimeStart.getText().toString())
                        .addParam("fmendtime", tvTimeEnd.getText().toString())
                        .addParam("percapitacost", etPrice.getText().toString())
                        .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
                        .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                        .addParam("accesspassword", password)
                        .addParam("type", "1")
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        UserReleaseModel userReleaseModel = gson.fromJson(data, UserReleaseModel.class);
                                        Intent intent = new Intent();
                                        intent.putExtra("id", userReleaseModel.getObj().getId()+"");
                                        intent.putExtra("type", "0");
                                        intent.setClass(CreateFriendRememberActivity1.this, CreateIntercalationActivity.class);
                                        WeiboDialogUtils.closeDialog(dialog);
                                        startActivity(intent);
                                        CreateFriendRememberActivity1.this.finish();
                                    }else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(CreateFriendRememberActivity1.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(CreateFriendRememberActivity1.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {
                WeiboDialogUtils.closeDialog(dialog);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private void next_tocaogao(){//仅保存至草稿
        dialog = WeiboDialogUtils.createLoadingDialog(CreateFriendRememberActivity1.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
//                        File file = new File(images);
//                        Luban.with(CreateFriendRememberActivity.this)
//                                .load(images)
//                                .ignoreBy(100)
//                                .filter(new CompressionPredicate() {
//                                    @Override
//                                    public boolean apply(String path) {
//                                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                                    }
//                                })
//                                .setCompressListener(new OnCompressListener() {
//                                    @Override
//                                    public void onStart() {
//                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                    }
//
//                                    @Override
//                                    public void onSuccess(File file) {
//                                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                                        e.onNext(file);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        // TODO 当压缩过程出现问题时调用
//                                    }
//                                }).launch();

                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPic());
                }
                Luban.with(CreateFriendRememberActivity1.this)
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
                                        map.put("fmpic[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                WeiboDialogUtils.closeDialog(dialog);
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
                ViseHttp.UPLOAD(NetConfig.userRelease)
                        .addHeader("Content-Type","multipart/form-data")
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userRelease))
                        .addParam("fmtitle", etTitle.getText().toString())
                        .addParam("fmcontent", etContent.getText().toString())
                        .addParam("fmaddress", tvCity.getText().toString())
                        .addParam("uid", uid)
                        .addParam("fmlable", yourChoiceId)
                        .addParam("fmgotime", tvTimeStart.getText().toString())
                        .addParam("fmendtime", tvTimeEnd.getText().toString())
                        .addParam("percapitacost", etPrice.getText().toString())
                        .addParam("activity_id", TextUtils.isEmpty(tvActiveTitle.getText().toString())?"0":yourChoiceActiveId)
                        .addParam("insertatext", tvIsIntercalation.getText().toString().equals("是")?"0":"1")
                        .addParam("accesspassword", password)
                        .addParam("type", "1")
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        CreateFriendRememberActivity1.this.finish();
                                        Toast.makeText(CreateFriendRememberActivity1.this,"已保存至草稿箱",Toast.LENGTH_SHORT).show();
                                    }else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(CreateFriendRememberActivity1.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(CreateFriendRememberActivity1.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {
                WeiboDialogUtils.closeDialog(dialog);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
