package com.yiwo.friendscometogether.newfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.HomeTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MyHuoDongApplyAdapter;
import com.yiwo.friendscometogether.newadapter.MyRememberAdapter;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/12/18.
 */

public class HuoDongApplyFragment extends BaseFragment {

    @BindView(R.id.rv_apply_huodong)
    RecyclerView rv_allpy_houdong;

    @BindView(R.id.fragment_huodong_apply_refreshLayout)
    RefreshLayout refreshLayout;

    private List<HuoDongListModel.ObjBean> mList ;
    private MyHuoDongApplyAdapter applyAdapter;
    private int page = 1;
    private SpImp spImp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apply_huodong,null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        initData();

        return view;
    }

    private void initData() {

        mList = new ArrayList<>();

        applyAdapter = new MyHuoDongApplyAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_allpy_houdong.setLayoutManager(manager);
        rv_allpy_houdong.setAdapter(applyAdapter);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        String tokens = getToken(NetConfig.BaseUrl + NetConfig.activityJoin);
        ViseHttp.POST(NetConfig.activityJoin)
                .addParam("app_key", tokens)
                .addParam("page", "1")
                .addParam("user_id", spImp.getUID())
                .addParam("type", "1")//1报名活动，2往期活动
                .request(new ACallback<String>() {
                    @Override
                    public void onFail(int errCode, String errMsg) {
                        refreshLayout.finishRefresh(1000);
                    }
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.d("ljc_data",data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                HuoDongListModel model = new Gson().fromJson(data, HuoDongListModel.class);
                                page = 2;
                                mList.clear();
                                mList.addAll(model.getObj());
                                applyAdapter.notifyDataSetChanged();
                            }
                            refreshLayout.finishRefresh(1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                String tokens = getToken(NetConfig.BaseUrl + NetConfig.activityJoin);
                ViseHttp.POST(NetConfig.activityJoin)
                        .addParam("app_key", tokens)
                        .addParam("page", "1")
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "1")//1报名活动，2往期活动
                        .request(new ACallback<String>() {
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }

                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        HuoDongListModel model = new Gson().fromJson(data, HuoDongListModel.class);
                                        page = 2;
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        applyAdapter.notifyDataSetChanged();
                                    }
                                    refreshLayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                String tokens = getToken(NetConfig.BaseUrl + NetConfig.activityJoin);
                Log.d("ljc",tokens+"///"+spImp.getUID());
                ViseHttp.POST(NetConfig.activityJoin)
                        .addParam("app_key", tokens)
                        .addParam("page", page+"")
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "1")//1报名活动，2往期活动
                        .request(new ACallback<String>() {
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        HuoDongListModel model = new Gson().fromJson(data, HuoDongListModel.class);
                                        page = page + 1;
                                        mList.addAll(model.getObj());
                                        applyAdapter.notifyDataSetChanged();
                                    }
                                    refreshLayout.finishLoadMore(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

    }
}
