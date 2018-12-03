package com.yiwo.friendscometogether.pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.SystemHotMessageDetailsModel;
import com.yiwo.friendscometogether.network.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息中心详情
 */
public class MessageCenterDetailsActivity extends BaseActivity {
    @BindView(R.id.activity_message_center_details_title_tv)
    TextView titleTv;
    @BindView(R.id.activity_message_center_details_pic_iv)
    ImageView picIv;
    @BindView(R.id.activity_message_center_details_content_tv)
    TextView contentTv;

    private String mesId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center_details);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {

        mesId = getIntent().getStringExtra("mesid");
        ViseHttp.POST(NetConfig.systemHotMessageDetailsUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.systemHotMessageDetailsUrl))
                .addParam("mes_id", mesId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                SystemHotMessageDetailsModel model = gson.fromJson(data, SystemHotMessageDetailsModel.class);
                                titleTv.setText(model.getObj().getMes_title());
                                if (!TextUtils.isEmpty(model.getObj().getMes_pic())) {
                                    Picasso.with(MessageCenterDetailsActivity.this).load(model.getObj().getMes_pic()).into(picIv);
                                }
                                contentTv.setText(model.getObj().getMes_message());
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

    @OnClick({R.id.activity_message_center_details_rl_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_message_center_details_rl_back:
                finish();
                break;
        }
    }
}
