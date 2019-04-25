package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonImpressonRvAdapter;
import com.yiwo.friendscometogether.newmodel.PersonImpressonModel;
import com.yiwo.friendscometogether.newmodel.UserPersonImpressionModel;
import com.yiwo.friendscometogether.pages.CreateFriendRememberActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonImpressionActivity extends BaseActivity {


    @BindView(R.id.rl_impresson)
    NestedRecyclerView rl_impresson;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.lay0)
    LinearLayout lay0;
    @BindView(R.id.lay1)
    LinearLayout lay1;
    @BindView(R.id.lay2)
    RelativeLayout lay2;
    @BindView(R.id.lay3)
    RelativeLayout lay3;
    @BindView(R.id.lay4)
    LinearLayout lay4;
    @BindView(R.id.lay5)
    LinearLayout lay5;
    @BindView(R.id.lay6)
    LinearLayout lay6;
    @BindView(R.id.lay7)
    LinearLayout lay7;
    @BindView(R.id.lay8)
    LinearLayout lay8;
    @BindView(R.id.lay9)
    LinearLayout lay9;

    private SpImp spImp;
    private String person_id;
    private List<PersonImpressonModel.ObjBean> listAllLabel = new ArrayList<>();
    private List<UserPersonImpressionModel.ObjBean> listUserLabel = new ArrayList<>();
    private PersonImpressonRvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_impression);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonImpressionActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        spImp = new SpImp(PersonImpressionActivity.this);
        initRv();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.commentLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.commentLabel))
                .addParam("userID",person_id)
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonImpressonModel model = gson.fromJson(data,PersonImpressonModel.class);
                                listAllLabel.clear();
                                listAllLabel.addAll(model.getObj());
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
        initUserListData();
    }

    private void initUserListData() {
        ViseHttp.POST(NetConfig.userCommentLabelList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCommentLabelList))
                .addParam("userID",person_id)
                .addParam("type","0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserPersonImpressionModel model = gson.fromJson(data,UserPersonImpressionModel.class);
                                listUserLabel.clear();
                                listUserLabel.addAll(model.getObj());
                                initLabelTree();
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

    private void initLabelTree() {
        lay0.setVisibility(View.GONE);
        lay1.setVisibility(View.GONE);
        lay2.setVisibility(View.GONE);
        lay3.setVisibility(View.GONE);
        lay4.setVisibility(View.GONE);
        lay5.setVisibility(View.GONE);
        lay6.setVisibility(View.GONE);
        lay7.setVisibility(View.GONE);
        lay8.setVisibility(View.GONE);
        lay9.setVisibility(View.GONE);

        for (int i = 0;i<listUserLabel.size();i++){
            switch (i){
                case 0:
                    lay0.setVisibility(View.VISIBLE);
                    tv0.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 1:
                    lay1.setVisibility(View.VISIBLE);
                    tv1.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 2:
                    lay2.setVisibility(View.VISIBLE);
                    tv2.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 3:
                    lay3.setVisibility(View.VISIBLE);
                    tv3.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 4:
                    lay4.setVisibility(View.VISIBLE);
                    tv4.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 5:
                    lay5.setVisibility(View.VISIBLE);
                    tv5.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 6:
                    lay6.setVisibility(View.VISIBLE);
                    tv6.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 7:
                    lay7.setVisibility(View.VISIBLE);
                    tv7.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 8:
                    lay8.setVisibility(View.VISIBLE);
                    tv8.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
                case 9:
                    lay9.setVisibility(View.VISIBLE);
                    tv9.setText(listUserLabel.get(i).getLabel_name()+"("+listUserLabel.get(i).getNum()+")");
                    break;
            }
        }
    }
    private void initRv() {
        FlowLayoutManager manager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rl_impresson.setLayoutManager(manager);
        adapter = new PersonImpressonRvAdapter(listAllLabel);
        adapter.setListenner(new PersonImpressonRvAdapter.OnCommentListenner() {
            @Override
            public void conmmentListen(final int position, final String labelID) {
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    ViseHttp.POST(NetConfig.userCommentLabel)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCommentLabel))
                            .addParam("userID",spImp.getUID())
                            .addParam("buserID",person_id)
                            .addParam("labelID",labelID)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
//                                            toToast(PersonImpressionActivity.this,"评价成功！");
//                                            refreshUserLabels(labelID,"");
                                            listAllLabel.get(position).setStatus(listAllLabel.get(position).getStatus().equals("0")? "1":"0");
                                            adapter.notifyDataSetChanged();
                                            initUserListData();
                                        }else {
                                            toToast(PersonImpressionActivity.this,jsonObject.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });

                } else {
                    Intent intent = new Intent();
                    intent.setClass(PersonImpressionActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        rl_impresson.setAdapter(adapter);

    }

//    private void refreshUserLabels(String labelID,String type) {
//        for (int i = 0;i<listUserLabel.size();i++){
//            if (listUserLabel.get(i).getLabel_name().equals())
//        }
//    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
