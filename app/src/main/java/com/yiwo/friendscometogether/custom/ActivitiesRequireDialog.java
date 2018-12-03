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

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/23.
 */

public class ActivitiesRequireDialog extends Dialog {
    private Context context;
    private TextView sexNoneTv, sexManTv, sexWomanTv, sexEqualTv, marriageNoTv, marriageYesTv,
            submitTv;
    private EditText ageMinEt, ageMaxEt, otherEt;
    private RelativeLayout closeRl;
    CreateFriendsTogetherRequestModel model = new CreateFriendsTogetherRequestModel();
    ActivitiesRequireListener listener;

    private String minAge = "";
    private String maxAge = "";
    private String peoplesex = "";
    private String marry = "";
    private String warning = "";

    public interface ActivitiesRequireListener {
        void setActivityText(CreateFriendsTogetherRequestModel model);
    }

    public ActivitiesRequireDialog(@NonNull Context context, String minAge, String maxAge, String peoplesex, String marry, String warning, ActivitiesRequireListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.peoplesex = peoplesex;
        this.marry = marry;
        this.warning = warning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_activities_require, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        sexNoneTv = (TextView) view.findViewById(R.id.dialog_activities_require_sex_none_tv);
        sexManTv = (TextView) view.findViewById(R.id.dialog_activities_require_sex_man_tv);
        sexWomanTv = (TextView) view.findViewById(R.id.dialog_activities_require_sex_woman_tv);
        sexEqualTv = (TextView) view.findViewById(R.id.dialog_activities_require_equal_tv);
        marriageNoTv = (TextView) view.findViewById(R.id.dialog_activities_require_unmarriage_tv);
        marriageYesTv = (TextView) view.findViewById(R.id.dialog_activities_require_marriage_tv);
        submitTv = (TextView) view.findViewById(R.id.dialog_activities_require_submit_tv);
        closeRl = (RelativeLayout) view.findViewById(R.id.dialog_activities_require_rl_close);

        ageMinEt = (EditText) view.findViewById(R.id.dialog_activities_require_age_min_et);
        ageMaxEt = (EditText) view.findViewById(R.id.dialog_activities_require_age_max_et);
        otherEt = (EditText) view.findViewById(R.id.dialog_activities_require_other_et);

        ageMinEt.setText(minAge);
        if(!TextUtils.isEmpty(minAge)){
            ageMinEt.setSelection(minAge.length());
        }
        ageMaxEt.setText(maxAge);
        otherEt.setText(warning);
        if(!TextUtils.isEmpty(peoplesex)){
            if(peoplesex.equals("0")){
                model.setPeoplesex("0");
                setColor(0);
            }else if(peoplesex.equals("1")){
                model.setPeoplesex("1");
                setColor(1);
            }else if(peoplesex.equals("2")){
                model.setPeoplesex("2");
                setColor(2);
            }else if(peoplesex.equals("3")){
                model.setPeoplesex("3");
                setColor(3);
            }
        }
        if(!TextUtils.isEmpty(marry)){
            if(marry.equals("1")){
                model.setMarry("1");
                marriageNoTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                marriageNoTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                marriageYesTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
                marriageYesTv.setTextColor(context.getResources().getColor(R.color.black_333333));
            }else if(marry.equals("0")){
                model.setMarry("0");
                marriageYesTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                marriageYesTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                marriageNoTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
                marriageNoTv.setTextColor(context.getResources().getColor(R.color.black_333333));
            }
        }

        sexNoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPeoplesex("0");
                setColor(0);
            }
        });
        sexManTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPeoplesex("1");
                setColor(1);
            }
        });
        sexWomanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPeoplesex("2");
                setColor(2);
            }
        });
        sexEqualTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setPeoplesex("3");
                setColor(3);
            }
        });
        marriageNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setMarry("1");
                marriageNoTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                marriageNoTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                marriageYesTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
                marriageYesTv.setTextColor(context.getResources().getColor(R.color.black_333333));
            }
        });
        marriageYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setMarry("0");
                marriageYesTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                marriageYesTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                marriageNoTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
                marriageNoTv.setTextColor(context.getResources().getColor(R.color.black_333333));
            }
        });
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(model.getPeoplesex()) || StringUtils.isEmpty(model.getMarry())) {
                    Toast.makeText(context, "请完善信息", Toast.LENGTH_SHORT).show();
                } else if (StringUtils.isEmpty(ageMinEt.getText().toString()) ||
                        StringUtils.isEmpty(ageMaxEt.getText().toString())) {
                    Toast.makeText(context, "年龄不能为空", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(ageMinEt.getText().toString()) > Integer.valueOf(ageMaxEt.getText().toString())) {
                    Toast.makeText(context, "最小年龄已经超过最大年龄", Toast.LENGTH_SHORT).show();
                } else {
                    model.setAge_begin(ageMinEt.getText().toString());
                    model.setAge_end(ageMaxEt.getText().toString());
                    model.setWarning(otherEt.getText().toString());
                    listener.setActivityText(model);
                    dismiss();
                }
            }
        });
        closeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setColor(int key) {
        sexNoneTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        sexNoneTv.setTextColor(context.getResources().getColor(R.color.black_333333));

        sexManTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        sexManTv.setTextColor(context.getResources().getColor(R.color.black_333333));

        sexWomanTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        sexWomanTv.setTextColor(context.getResources().getColor(R.color.black_333333));

        sexEqualTv.setBackgroundResource(R.drawable.bg_dialog_price_un_select);
        sexEqualTv.setTextColor(context.getResources().getColor(R.color.black_333333));
        switch (key) {
            case 0:
                sexNoneTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                sexNoneTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                break;
            case 1:
                sexManTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                sexManTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                break;
            case 2:
                sexWomanTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                sexWomanTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                break;
            case 3:
                sexEqualTv.setBackgroundResource(R.drawable.bg_dialog_price_select);
                sexEqualTv.setTextColor(context.getResources().getColor(R.color.red_F71F1F));
                break;
        }
    }
}
