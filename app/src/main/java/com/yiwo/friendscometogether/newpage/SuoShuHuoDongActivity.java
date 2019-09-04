package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.SearchListAdapter;
import com.yiwo.friendscometogether.adapter.SuoShuHuoDongListAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.model.SearchListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuoShuHuoDongActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    SuoShuHuoDongListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suo_shuo_huo_dong);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        initData();
    }

    public void initData() {
        List<GetFriendActiveListModel.ObjBean> data = (List<GetFriendActiveListModel.ObjBean>) getIntent().getSerializableExtra("list");
        initList(data);
    }

    public void initList(final List<GetFriendActiveListModel.ObjBean> data) {
        LinearLayoutManager manager = new LinearLayoutManager(SuoShuHuoDongActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new SuoShuHuoDongListAdapter(data);
        adapter.setListionner(new SuoShuHuoDongListAdapter.ItemClickListionner() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent();
                intent.putExtra("suoshuhuodong",data.get(postion));
                setResult(1,intent);
                finish();
            }
        });
        rv.setAdapter(adapter);
    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
