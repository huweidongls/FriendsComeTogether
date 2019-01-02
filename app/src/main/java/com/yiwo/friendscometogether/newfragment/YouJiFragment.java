package com.yiwo.friendscometogether.newfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/12/26.
 */

public class YouJiFragment extends BaseFragment {

    @BindView(R.id.rv_youji)
    RecyclerView rv_youji;
    @BindView(R.id.fragment_youji_refreshLayout)
    RefreshLayout refreshLayout;

    private YouJiAdapter adapter;
    private List<YouJiListModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youji, null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {

        uid = spImp.getUID();
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        ViseHttp.POST(NetConfig.newYoujiData)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.newYoujiData))
                .addParam("page", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                YouJiListModel model = gson.fromJson(data, YouJiListModel.class);
                                mList = model.getObj();
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                rv_youji.setLayoutManager(mLayoutManager);
                                Collections.shuffle(mList);
                                adapter = new YouJiAdapter(mList);
                                rv_youji.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    @OnClick({R.id.searchLl})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.searchLl:
                intent.setClass(getContext(), SearchActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
        }
    }

}
