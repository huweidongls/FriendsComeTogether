package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.OrderPagerAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.fragment.AllOrderFragment;
import com.yiwo.friendscometogether.fragment.ReturnPriceFragment;
import com.yiwo.friendscometogether.fragment.ToCommentFragment;
import com.yiwo.friendscometogether.fragment.ToPayFragment;
import com.yiwo.friendscometogether.fragment.ToTripFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.activity_my_order_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_order_top_title)
    TextView tvTopTitle;
    @BindView(R.id.activity_my_order_tab)
    TabLayout mTb;
    @BindView(R.id.activity_my_order_viewpager)
    ViewPager mVp;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        tvTopTitle.setText("我的订单");
        init();

    }

    private void init() {

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        initTitile();
        initFragment();
        //设置适配器
        mVp.setAdapter(new OrderPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);
        mTb.getTabAt(position).select();

    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        ToPayFragment toPayFragment = new ToPayFragment();
        ToTripFragment toTripFragment = new ToTripFragment();
        ToCommentFragment toCommentFragment = new ToCommentFragment();
        ReturnPriceFragment returnPriceFragment = new ReturnPriceFragment();
        mFragmentList.add(allOrderFragment);
        mFragmentList.add(toPayFragment);
        mFragmentList.add(toTripFragment);
        mFragmentList.add(toCommentFragment);
        mFragmentList.add(returnPriceFragment);
    }

    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待支付");
        mTitleList.add("进行中");
        mTitleList.add("待评价");
        mTitleList.add("退款");
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        for(int i = 0; i<mTitleList.size(); i++){
            mTb.addTab(mTb.newTab().setText(mTitleList.get(i)));
        }
    }

    @OnClick({R.id.activity_my_order_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_my_order_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyOrderActivity.this.finish();
    }
}
