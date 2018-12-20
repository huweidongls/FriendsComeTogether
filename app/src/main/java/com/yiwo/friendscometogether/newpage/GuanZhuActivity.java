package com.yiwo.friendscometogether.newpage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanZhuActivity extends BaseActivity {

    @BindView(R.id.rl_woguanhude)
    RelativeLayout rl_woguanzhude;
    @BindView(R.id.rl_guanzhuwode)
    RelativeLayout rl_guanzhuwode;
    @BindView(R.id.rl_guanzhuhuodong)
    RelativeLayout rl_guanzhuhuodong;

    @BindView(R.id.tv_woguanzhude)
    TextView tv_woguanzhude;
    @BindView(R.id.tv_guanzhuwode)
    TextView tv_guanzhuwode;
    @BindView(R.id.tv_guanzhuhuodong)
    TextView tv_guanzhuhuodong;


    @BindView(R.id.rv_woguanzhude)
    RecyclerView rv_woguanzhude;
    @BindView(R.id.rv_guanzhuwode)
    RecyclerView rv_guanzhuwode;
    @BindView(R.id.rv_guanzhuhuodong)
    RecyclerView rv_guanzhuhuodong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(GuanZhuActivity.this);
    }
    @OnClick({R.id.rl_woguanhude,R.id.rl_guanzhuwode,R.id.rl_guanzhuhuodong})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_woguanhude:
                tv_woguanzhude.setTextColor(Color.parseColor("#FFAB26"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                rv_woguanzhude.setVisibility(View.VISIBLE);
                rv_guanzhuwode.setVisibility(View.GONE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                break;
            case R.id.rl_guanzhuwode:
                tv_guanzhuwode.setTextColor(Color.parseColor("#FFAB26"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuwode.setVisibility(View.VISIBLE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                rv_woguanzhude.setVisibility(View.GONE);
                break;
            case R.id.rl_guanzhuhuodong:
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#FFAB26"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuhuodong.setVisibility(View.VISIBLE);
                rv_woguanzhude.setVisibility(View.GONE);
                rv_guanzhuwode.setVisibility(View.GONE);
                break;
        }
    }
}
