package com.yiwo.friendscometogether.newfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/12/26.
 */

public class YouJiFragment extends BaseFragment {

    @BindView(R.id.rv_youji)
    RecyclerView rv_youji;
    @BindView(R.id.fragment_youji_refreshLayout)
    RefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youji,null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    private void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_youji.setLayoutManager(mLayoutManager);
        List<YouJiListModel> listModels = new ArrayList<>();
        YouJiListModel model = new YouJiListModel();
        model.setType(0);
        YouJiListModel model1 = new YouJiListModel();
        model.setType(1);
        for (int i = 0;i<15;i++){
            if (i%3 == 0){
                listModels.add(model);
            }else {
                listModels.add(model1);
            }
        }
        rv_youji.setAdapter(new YouJiAdapter(listModels));

    }
}
