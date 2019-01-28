package com.yiwo.friendscometogether.newfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MessageCommentAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.MessageCommentModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.MessageCommentActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2019/1/28.
 */

public class MyCommentHuoDongFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView messageRv;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private SpImp spImp;
    private String uid = "";

    private MessageCommentAdapter adapter;
    private List<MessageCommentModel.ObjBean> mList;

    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_comment_huo_dong, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {
        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.messageCommentUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageCommentUrl))
                .addParam("page", "1")
                .addParam("user_id", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MessageCommentModel model = gson.fromJson(data, MessageCommentModel.class);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                messageRv.setLayoutManager(manager);
                                mList = model.getObj();
                                adapter = new MessageCommentAdapter(mList);
                                messageRv.setAdapter(adapter);
                                page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.messageCommentUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageCommentUrl))
                        .addParam("page", "1")
                        .addParam("user_id", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        MessageCommentModel model = gson.fromJson(data, MessageCommentModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                    }
                                    refreshlayout.finishRefresh();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishRefresh();
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.messageCommentUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.messageCommentUrl))
                        .addParam("page", page + "")
                        .addParam("user_id", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        MessageCommentModel model = gson.fromJson(data, MessageCommentModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
                                    }
                                    refreshlayout.finishLoadMore();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishLoadMore();
                            }
                        });
            }
        });
    }
}
