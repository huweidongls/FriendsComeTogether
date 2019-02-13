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
import android.widget.EditText;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.LabelAdapter;

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

    private LabelAdapter adapter;
    private List<UserLabelModel.ObjBean> mList;

    private List<String> labelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youju_shaixuan);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(YoujuShaixuanActivity.this);

        initData();

    }

    private void initData() {

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
                                GridLayoutManager manager = new GridLayoutManager(context, 3);
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

    @OnClick({R.id.rl_back, R.id.tv_reset, R.id.tv_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_reset:
                etMin.setText(null);
                etMax.setText(null);
                labelList.clear();
                adapter.reset();
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_sure:
                Intent intent = new Intent();
                String min = etMin.getText().toString();
                String max = etMax.getText().toString();
                if(!TextUtils.isEmpty(min)&&!TextUtils.isEmpty(max)&&labelList.size() == 0){
                    int mi = Integer.valueOf(min);
                    int ma = Integer.valueOf(max);
                    if(mi < ma){
                        intent.putExtra("min", min);
                        intent.putExtra("max", max);
                        setResult(2, intent);
                        finish();
                    }else {
                        toToast(context, "最低价需小于最高价");
                    }
                }else if(!TextUtils.isEmpty(min)&&!TextUtils.isEmpty(max)&&labelList.size() > 0){
                    int mi = Integer.valueOf(min);
                    int ma = Integer.valueOf(max);
                    if(mi < ma){
                        String label = "";
                        for (int i = 0; i<labelList.size(); i++){
                            if(i == labelList.size()-1){
                                label = label+labelList.get(i);
                            }else {
                                label = label+labelList.get(i)+",";
                            }
                        }
                        intent.putExtra("min", min);
                        intent.putExtra("max", max);
                        intent.putExtra("label", label);
                        setResult(3, intent);
                        finish();
                    }else {
                        toToast(context, "最低价需小于最高价");
                    }
                }else if((TextUtils.isEmpty(min)||TextUtils.isEmpty(max))&&labelList.size() > 0){
                    String label = "";
                    for (int i = 0; i<labelList.size(); i++){
                        if(i == labelList.size()-1){
                            label = label+labelList.get(i);
                        }else {
                            label = label+labelList.get(i)+",";
                        }
                    }
                    intent.putExtra("label", label);
                    setResult(5, intent);
                    finish();
                }else {
//                    toToast(context, "请选择筛选条件");
                    setResult(7, intent);
                    finish();
                }
                break;
        }
    }

}
