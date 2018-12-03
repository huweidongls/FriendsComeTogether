package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.DetailsOrderModel;
import com.yiwo.friendscometogether.model.JoinGetCommentInfoModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderCommentActivity extends BaseActivity {

    @BindView(R.id.activity_order_comment_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_order_comment_rl_complete)
    RelativeLayout rlComplete;
    @BindView(R.id.activity_order_comment_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_order_comment_iv_title)
    ImageView ivTitle;
    @BindView(R.id.activity_order_comment_tv_content)
    TextView tvContent;
    @BindView(R.id.activity_order_comment_tv_time)
    TextView tvTime;
    @BindView(R.id.activity_order_comment_tv_people_num)
    TextView tvPeopleNum;
    @BindView(R.id.activity_order_comment_tv_price_details)
    TextView tvPriceDetails;
    @BindView(R.id.activity_order_comment_tv_price)
    TextView tvPrice;
    @BindView(R.id.activity_order_comment_et_content)
    EditText etContent;

    private SpImp spImp;
    private String uid = "";
    private String orderId = "";
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comment);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(OrderCommentActivity.this);
        spImp = new SpImp(OrderCommentActivity.this);

        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        orderId = intent.getStringExtra("orderid");
        uid = spImp.getUID();
        if(type.equals("0")){
            ViseHttp.POST(NetConfig.detailsOrderUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.detailsOrderUrl))
                    .addParam("order_id", orderId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                Log.e("222", orderId);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Gson gson = new Gson();
                                    DetailsOrderModel model = gson.fromJson(data, DetailsOrderModel.class);
                                    tvTitle.setText(model.getObj().getTitle());
                                    if(!TextUtils.isEmpty(model.getObj().getPicture())){
                                        Picasso.with(OrderCommentActivity.this).load(model.getObj().getPicture()).into(ivTitle);
                                    }
                                    tvContent.setText(model.getObj().getContent());
                                    tvTime.setText("活动时间: " + model.getObj().getTime());
                                    tvPeopleNum.setText("参加人数: " + model.getObj().getGo_num());
                                    tvPriceDetails.setText(model.getObj().getPrice_type());
                                    tvPrice.setText("合计费用: " + model.getObj().getPrice());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else if(type.equals("1")){
            ViseHttp.POST(NetConfig.joinGetCommentInfoUrl)
                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.joinGetCommentInfoUrl))
                    .addParam("ujID", orderId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("22222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    Gson gson = new Gson();
                                    JoinGetCommentInfoModel model = gson.fromJson(data, JoinGetCommentInfoModel.class);
                                    tvTitle.setText(model.getObj().getTitle());
                                    if(!TextUtils.isEmpty(model.getObj().getPicture())){
                                        Picasso.with(OrderCommentActivity.this).load(model.getObj().getPicture()).into(ivTitle);
                                    }
                                    tvContent.setText(model.getObj().getContent());
                                    tvTime.setText("行程时间: " + model.getObj().getTime());
                                    tvPeopleNum.setText("参加人数: " + model.getObj().getGo_num());
                                    tvPriceDetails.setText(model.getObj().getPrice_type());
                                    tvPrice.setText("合计费用: " + model.getObj().getPrice());
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

    @OnClick({R.id.activity_order_comment_rl_back, R.id.activity_order_comment_rl_complete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_order_comment_rl_back:
                onBackPressed();
                break;
            case R.id.activity_order_comment_rl_complete:
                onComplete(type);
                break;
        }
    }

    /**
     * 提交评价
     */
    private void onComplete(String type) {

        if(TextUtils.isEmpty(etContent.getText().toString())){
            toToast(OrderCommentActivity.this, "评价内容不能为空");
        }else {
            if(type.equals("0")){
                ViseHttp.POST(NetConfig.orderCommentUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.orderCommentUrl))
                        .addParam("type", type)
                        .addParam("userID", uid)
                        .addParam("order_id", orderId)
                        .addParam("content", etContent.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(OrderCommentActivity.this, "评价成功");
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }else if(type.equals("1")){
                ViseHttp.POST(NetConfig.orderCommentUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.orderCommentUrl))
                        .addParam("type", type)
                        .addParam("userID", uid)
                        .addParam("ujID", orderId)
                        .addParam("content", etContent.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(OrderCommentActivity.this, "评价成功");
                                        finish();
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OrderCommentActivity.this.finish();
    }
}
