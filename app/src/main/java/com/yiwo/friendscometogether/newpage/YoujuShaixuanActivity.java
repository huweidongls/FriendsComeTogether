package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.SuperLikeSXMode;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.LabelAdapter;
import com.yiwo.friendscometogether.newmodel.HuoDongShaiXuanMode;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.CreateFriendRememberActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoujuShaixuanActivity extends BaseActivity {

    private Context context = YoujuShaixuanActivity.this;

    @BindView(R.id.rv_biaoqian)
    RecyclerView recyclerView;
    @BindView(R.id.et_min)
    EditText etMin;
    @BindView(R.id.et_max)
    EditText etMax;
    @BindView(R.id.et_shangjia)
    EditText etShangJia;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.iv_expand_search)
    ImageView ivTuiJian;
    @BindView(R.id.tv_choose_city)
    TextView tvChooseCity;
    private Boolean bTuiJian = false;
    private LabelAdapter adapter;
    private List<UserLabelModel.ObjBean> mList;
    private SpCache spCache;
    private List<String> labelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youju_shaixuan);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(YoujuShaixuanActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        spCache = new SpCache(context);
        initData();

    }

    private void initData() {
        HuoDongShaiXuanMode sxMode = (HuoDongShaiXuanMode) spCache.get("huoDongShaiXuan");
        if (sxMode != null){
            Log.d("youJuShaiXuan!nul","!!!");
            etCity.setText(sxMode.getCity());
        }
        if (bTuiJian){
            ivTuiJian.setImageResource(R.mipmap.switch_on);
        }else {
            ivTuiJian.setImageResource(R.mipmap.switch_off);
        }
        labelList = new ArrayList<>();
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                mList = userLabelModel.getObj();
                                GridLayoutManager manager = new GridLayoutManager(context, 3){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                recyclerView.setLayoutManager(manager);
                                adapter = new LabelAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setListener(new LabelAdapter.OnSelectLabelListener() {
                                    @Override
                                    public void onSelete(int i) {
                                        labelList.add(mList.get(i).getLID());
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

    @OnClick({R.id.rl_back, R.id.tv_reset, R.id.tv_sure,R.id.iv_expand_search,R.id.tv_choose_city})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_reset:
                etMin.setText(null);
                etMax.setText(null);
                etShangJia.setText(null);
                etCity.setText(null);
                bTuiJian = false;
                ivTuiJian.setImageResource(R.mipmap.switch_off);
                labelList.clear();
                adapter.reset();
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_expand_search:
                if (bTuiJian){
                    bTuiJian = false;
                }else {
                    bTuiJian = true;
                }
                if (bTuiJian){
                    ivTuiJian.setImageResource(R.mipmap.switch_on);
                }else {
                    ivTuiJian.setImageResource(R.mipmap.switch_off);
                }
                break;
            case R.id.tv_choose_city:
                Intent it = new Intent(YoujuShaixuanActivity.this, CityActivity.class);
                it.putExtra(ActivityConfig.ACTIVITY, "youJuShaiXuan");
                startActivityForResult(it, 1);
                break;
            case R.id.tv_sure:
                Intent intent = new Intent();
                HuoDongShaiXuanMode mode = new HuoDongShaiXuanMode();
                String min = etMin.getText().toString();
                String max = etMax.getText().toString();
                String shangJiaNAme = etShangJia.getText().toString();
                String city = etCity.getText().toString();
                String label = "";
                 for (int i = 0; i<labelList.size(); i++){
                     if(i == labelList.size()-1){
                         label = label+labelList.get(i);
                     }else {
                         label = label+labelList.get(i)+",";
                     }
                 }
                String price = "";
                if (!TextUtils.isEmpty(min)&&TextUtils.isEmpty(max)){
                    toToast(context,"请填写最高价");
                    break;
                }
                if (!TextUtils.isEmpty(max)&&TextUtils.isEmpty(min)){
                    toToast(context,"请填写最低价");
                    break;
                }
                if (!max.equals("")&&!min.equals("")){
                    int mi = Integer.valueOf(min);
                    int ma = Integer.valueOf(max);
                    if (mi<0||ma<0){
                        toToast(context,"请填写正确价格");
                        break;
                    }else {
                        if(mi >= ma){
                            toToast(context, "最低价需小于最高价");
                            break;
                        }else {
                            price = min + "," + max;
                        }
                    }
                }
                mode.setShangJiaName(shangJiaNAme);
                if (bTuiJian){
                    mode.setShangJiaTuiJian("1");
                }else {
                    mode.setShangJiaTuiJian("0");
                }
                mode.setBiaoQian(label);
                mode.setJiaGe(price);
                mode.setCity(city);
                intent.putExtra("shaiXuanMode",mode);
                spCache.put("huoDongShaiXuan",mode);
                setResult(1, intent);
                finish();
//                if(!TextUtils.isEmpty(min)&&!TextUtils.isEmpty(max)&&labelList.size() == 0){
//                    int mi = Integer.valueOf(min);
//                    int ma = Integer.valueOf(max);
//                    if(mi < ma){
//                        intent.putExtra("min", min);
//                        intent.putExtra("max", max);
//                        setResult(2, intent);
//                        finish();
//                    }else {
//                        toToast(context, "最低价需小于最高价");
//                    }
//                }else if(!TextUtils.isEmpty(min)&&!TextUtils.isEmpty(max)&&labelList.size() > 0){
//                    int mi = Integer.valueOf(min);
//                    int ma = Integer.valueOf(max);
//                    if(mi < ma){
//                        String label = "";
//                        for (int i = 0; i<labelList.size(); i++){
//                            if(i == labelList.size()-1){
//                                label = label+labelList.get(i);
//                            }else {
//                                label = label+labelList.get(i)+",";
//                            }
//                        }
//                        intent.putExtra("min", min);
//                        intent.putExtra("max", max);
//                        intent.putExtra("label", label);
//                        setResult(3, intent);
//                        finish();
//                    }else {
//                        toToast(context, "最低价需小于最高价");
//                    }
//                }else if((TextUtils.isEmpty(min)||TextUtils.isEmpty(max))&&labelList.size() > 0){
//                    String label = "";
//                    for (int i = 0; i<labelList.size(); i++){
//                        if(i == labelList.size()-1){
//                            label = label+labelList.get(i);
//                        }else {
//                            label = label+labelList.get(i)+",";
//                        }
//                    }
//                    intent.putExtra("label", label);
//                    setResult(5, intent);
//                    finish();
//                }else {
////                    toToast(context, "请选择筛选条件");
//                    setResult(7, intent);
//                    finish();
//                }
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && resultCode == 1) {//选择城市
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            etCity.setText(model.getName());
        } else if (requestCode == 1 && resultCode == 2) {//重置
            etCity.setText("");
            etCity.setHint("请选择或输入活动地点");
        } else if (requestCode == 1 && resultCode == 3) {//国际城市
            String city = data.getStringExtra("city");
            etCity.setText(city);
        }
    }
}
