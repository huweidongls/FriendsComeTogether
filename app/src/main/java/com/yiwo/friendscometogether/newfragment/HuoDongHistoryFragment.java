package com.yiwo.friendscometogether.newfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.newadapter.MyHuoDongApplyAdapter;
import com.yiwo.friendscometogether.newadapter.MyHuoDongHistoryAdapter;
import com.yiwo.friendscometogether.pages.JoinActiveActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/12/18.
 */

public class HuoDongHistoryFragment extends BaseFragment {

    @BindView(R.id.rv_history_huodong)
    RecyclerView rv_history_huodong;

    @BindView(R.id.fragment_huodong_history_refreshLayout)
    RefreshLayout refreshLayout;

    private List<String> mList ;
    private MyHuoDongHistoryAdapter applyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_history_huodong,null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        initData();
        return view;
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        applyAdapter = new MyHuoDongHistoryAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_history_huodong.setLayoutManager(manager);
        rv_history_huodong.setAdapter(applyAdapter);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });

    }
}
