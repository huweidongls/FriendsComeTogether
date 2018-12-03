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

/**
 * Created by Administrator on 2018/7/23.
 */

public class PeopleRequireDialog extends Dialog {
    private Context context;
    RelativeLayout closeRl;
    EditText minEt, maxEt;
    TextView submitTv;
    PeopleRequireListener listener;
    CreateFriendsTogetherRequestModel model = new CreateFriendsTogetherRequestModel();

    private String min = "";
    private String max = "";

    public interface PeopleRequireListener {
        void setActivityText(CreateFriendsTogetherRequestModel model);
    }

    public PeopleRequireDialog(@NonNull Context context, String min, String max, PeopleRequireListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_people_require, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        minEt = (EditText) view.findViewById(R.id.dialog_people_require_et_min);
        maxEt = (EditText) view.findViewById(R.id.dialog_people_require_et_max);
        submitTv = (TextView) view.findViewById(R.id.dialog_people_require_bt_submit);
        closeRl = (RelativeLayout) view.findViewById(R.id.dialog_people_require_rl_close);

        minEt.setText(min);
        if(!TextUtils.isEmpty(min)){
            minEt.setSelection(min.length());
        }
        maxEt.setText(max);

        closeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(minEt.getText().toString()) && !StringUtils.isEmpty(maxEt.getText().toString())) {
                    if (Integer.valueOf(minEt.getText().toString()) <= Integer.valueOf(maxEt.getText().toString())) {
                        model.setMin_num(minEt.getText().toString());
                        model.setMax_num(maxEt.getText().toString());
                        listener.setActivityText(model);
                        dismiss();
                    } else {
                        Toast.makeText(context, "您输入的最小人数已超过最大人数", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "请检查输入框是否为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
