package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.newadapter.SuperLikeAdapter;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperLikeActivity extends BaseActivity {

    private SuperLikeAdapter superLikeAdapter;
    private List<String> data = new ArrayList<>();
    private Context context  = SuperLikeActivity.this;

    @BindView(R.id.rv_super_like)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(SuperLikeActivity.this);
        initData();
    }

    private void initData() {
        // 2.Grid布局
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this,
                        2, // 每行显示item项数目
                        GridLayoutManager.HORIZONTAL, //水平排列
                        false
                ){
                    @Override
                    public boolean canScrollVertically() {
                        return  false;
                    }
                };
        recyclerView.setLayoutManager(layoutManager);
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        superLikeAdapter = new SuperLikeAdapter(data);
        recyclerView.setAdapter(superLikeAdapter);
    }
    @OnClick({R.id.rl_back,R.id.rl_Sx})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_Sx:
                intent.setClass(context,SuperLikeSxActivity.class);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
