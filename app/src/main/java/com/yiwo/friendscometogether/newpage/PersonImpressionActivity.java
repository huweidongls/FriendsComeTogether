package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.ImpressionPersonAdapter;
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


    @BindView(R.id.rl_algin_right_heart)
    RelativeLayout rlXinDong;
    @BindView(R.id.tv_xindong)
    TextView tv_xindong;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    @BindView(R.id.rv_person_impression)
    RecyclerView rv_person_impression;
    @BindView(R.id.rv_impresson)
    NestedRecyclerView rv_impresson;
    @BindView(R.id.tv_title)
    TextView tv_title;
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
    @BindView(R.id.iv_head)
    ImageView iv_head;
    private SpImp spImp;
    private String person_id;
    private boolean isHeart;
    private List<PersonImpressonModel.ObjBean> listAllLabel = new ArrayList<>();
    private List<UserPersonImpressionModel.ObjBean> listUserLabel = new ArrayList<>();
    private PersonImpressonRvAdapter adapter;//评价按钮
    private ImpressionPersonAdapter adapter_person;//被评价的标签
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_impression);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonImpressionActivity.this);
        spImp = new SpImp(PersonImpressionActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        if (spImp.getUID().equals(person_id)){
            tv_xindong.setText("对我心动");
        }else {
            if (getIntent().getStringExtra("sex").equals("0")){
                tv_xindong.setText("心动男生");
            }else {
                tv_xindong.setText("心动女生");
            }
        }

        if (person_id.equals(spImp.getUID())){
            tv_title.setText("我的印象");
        }else {
            tv_title.setText("Ta的印象");
        }
        Glide.with(PersonImpressionActivity.this).load(getIntent().getStringExtra("user_icon"))
                .apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(iv_head);
        initRv();
        initData();
    }

    private void initData() {
        // 心动状态
        ViseHttp.POST(NetConfig.heartBeatStatus)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.heartBeatStatus))
                .addParam("heartbeatID",person_id)
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                if (jsonObject1.getString("status").equals("0")){
                                    iv_heart.setImageResource(R.mipmap.xindong_border);
                                    isHeart = false;
                                }else {
                                    iv_heart.setImageResource(R.mipmap.xindong);
                                    isHeart = true;
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
                .addParam("type","1")
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
        if (listUserLabel.size()>10){
            rv_person_impression.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonImpressionActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_person_impression.setLayoutManager(linearLayoutManager);
            adapter_person = new ImpressionPersonAdapter(listUserLabel.subList(10,listUserLabel.size()),person_id);
            rv_person_impression.setAdapter(adapter_person);
        }else {
            rv_person_impression.setVisibility(View.GONE);
        }
    }
    private void initRv() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonImpressionActivity.this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rv_person_impression.setLayoutManager(linearLayoutManager);
//        adapter_person = new ImpressionPersonAdapter(listUserLabel.subList(10,listUserLabel.size()));
//        rv_person_impression.setAdapter(adapter_person);
        FlowLayoutManager manager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_impresson.setLayoutManager(manager);
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
        rv_impresson.setAdapter(adapter);

    }

//    private void refreshUserLabels(String labelID,String type) {
//        for (int i = 0;i<listUserLabel.size();i++){
//            if (listUserLabel.get(i).getLabel_name().equals())
//        }
//    }

    @OnClick({R.id.rl_back,R.id.rl_algin_right_heart,R.id.lay0,R.id.lay1,R.id.lay2,R.id.lay3,R.id.lay4,R.id.lay5,R.id.lay6,R.id.lay7,R.id.lay8,R.id.lay9})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){

            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_algin_right_heart:
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    if (person_id.equals(spImp.getUID())){//自己查看自己
                        intent.setClass(PersonImpressionActivity.this,PersonsWhoHeartMeActivity.class);
                        intent.putExtra("person_id",person_id);
                        intent.putExtra("title","对我心动的人");
                        startActivity(intent);
                    }else {
                        duiTaXinDong();
                    }
                }else {
                    intent.setClass(PersonImpressionActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lay0:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(0).getLabelID());
                intent.putExtra("person_id",person_id);
                intent.putExtra("title",tv0.getText().toString());
                startActivity(intent);
                break;
            case R.id.lay1:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(1).getLabelID());
                intent.putExtra("title",tv1.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay2:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(2).getLabelID());
                intent.putExtra("title",tv2.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay3:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(3).getLabelID());
                intent.putExtra("title",tv3.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay4:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(4).getLabelID());
                intent.putExtra("title",tv4.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay5:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(5).getLabelID());
                intent.putExtra("title",tv5.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay6:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(6).getLabelID());
                intent.putExtra("title",tv6.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay7:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(7).getLabelID());
                intent.putExtra("title",tv7.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay8:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(8).getLabelID());
                intent.putExtra("title",tv8.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;
            case R.id.lay9:
                intent.setClass(PersonImpressionActivity.this,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",listUserLabel.get(9).getLabelID());
                intent.putExtra("title",tv9.getText().toString());
                intent.putExtra("person_id",person_id);
                startActivity(intent);
                break;

        }
    }

    private void duiTaXinDong() {
        ViseHttp.POST(NetConfig.heartbeat)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.heartbeat))
                .addParam("heartbeatID",person_id)
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                isHeart = !isHeart;
                                iv_heart.setImageResource(isHeart?R.mipmap.xindong:R.mipmap.xindong_border);
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
}
