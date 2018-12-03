package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.CreateFriendsTogetherRequestModel;
import com.yiwo.friendscometogether.utils.StringUtils;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/20.
 */

public class PeoplePriceDialog extends Dialog {

    private Context context;
    private TextView showTv, treatTv, payTv, submitBtn, contentTv;
    private EditText priceEt, otherEt;
    private RelativeLayout closeRl;
    CreateFriendsTogetherRequestModel model = new CreateFriendsTogetherRequestModel();
    private String price;
    private String price_type;
    private String price_info;

    public interface PeoplePriceListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void setActivityText(CreateFriendsTogetherRequestModel model);
    }

    private PeoplePriceListener listener;

    public PeoplePriceDialog(@NonNull Context context, String price, String price_type, String price_info, PeoplePriceListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.price = price;
        this.price_type = price_type;
        this.price_info = price_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_people_price, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        showTv = (TextView) view.findViewById(R.id.dialog_people_price_tv_left);
        treatTv = (TextView) view.findViewById(R.id.dialog_people_price_tv_right);
        payTv = (TextView) view.findViewById(R.id.dialog_people_price_tv_pay);
        contentTv = (TextView) view.findViewById(R.id.dialog_people_price_tv_content);
        submitBtn = (TextView) view.findViewById(R.id.dialog_people_price_btn);
        priceEt = (EditText) view.findViewById(R.id.dialog_people_price_et_price);
        otherEt = (EditText) view.findViewById(R.id.dialog_people_price_et_explain);
        closeRl = (RelativeLayout) view.findViewById(R.id.dialog_people_price_rl_close);

        if(!TextUtils.isEmpty(price_type)){
            if(price_type.equals("0")){
                model.setPrice_type("0");
                setViewColor(0);
            }else if(price_type.equals("1")){
                model.setPrice_type("1");
                setViewColor(1);
            }else if(price_type.equals("2")){
                model.setPrice_type("2");
                setViewColor(2);
            }
        }
        priceEt.setText(price);
        if(!TextUtils.isEmpty(price)){
            priceEt.setSelection(price.length());
        }
        otherEt.setText(price_info);

        closeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        showTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPrice_type("0");
                setViewColor(0);
            }
        });
        treatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPrice_type("1");
                setViewColor(1);
            }
        });

        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPrice_type("2");
                setViewColor(2);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(priceEt.getText().toString())) {
                    model.setPrice(priceEt.getText().toString());
                    model.setPrice_info(otherEt.getText().toString());
                    listener.setActivityText(model);
                    dismiss();
                } else {
                    Toast.makeText(context, "花费为空，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setViewColor(int key) {
        showTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        showTv.setTextColor(context.getResources().getColor(R.color.black_333333));
        treatTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        treatTv.setTextColor(context.getResources().getColor(R.color.black_333333));
        payTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        payTv.setTextColor(context.getResources().getColor(R.color.black_333333));
        switch (key) {
            case 0:
                showTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                showTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                contentTv.setText("活动费用现场实际收费");
                break;
            case 1:
                treatTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                treatTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                contentTv.setText("由活动创建者承担费用，用户免费报名参加");
                break;
            case 2:
                payTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                payTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                contentTv.setText("在线直接付款，通过平台结算");
                break;
        }

    }
}
