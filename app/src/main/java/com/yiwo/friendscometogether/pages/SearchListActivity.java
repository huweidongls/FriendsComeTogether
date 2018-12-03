package com.yiwo.friendscometogether.pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.SearchListAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.SearchListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchListActivity extends BaseActivity {
    @BindView(R.id.activity_search_list_rv)
    RecyclerView rv;
    SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        initData();
    }

    public void initData() {
        List<SearchListModel.ObjBean> data = (List<SearchListModel.ObjBean>) getIntent().getSerializableExtra("list");
        initList(data);
    }

    public void initList(List<SearchListModel.ObjBean> data) {
        LinearLayoutManager manager = new LinearLayoutManager(SearchListActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new SearchListAdapter(data, this);
        rv.setAdapter(adapter);
    }

    @OnClick({R.id.activity_search_list_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_search_list_rl_back:
                finish();
                break;
        }
    }

}
