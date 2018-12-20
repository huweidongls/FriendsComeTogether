package com.yiwo.friendscometogether.newpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

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

    @BindView(R.id.rv_wogaunzhude)
    RecyclerView rv_wogaunzhude;
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

                break;
            case R.id.rl_guanzhuwode:

                break;
            case R.id.rl_guanzhuhuodong:

                break;
        }
    }
}
