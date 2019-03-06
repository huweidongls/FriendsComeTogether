package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MyCommentAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.MyCommentActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2019/1/28.
 */

public class MyCommentYouJiFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MyCommentAdapter adapter;
    private List<ArticleCommentListModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_comment_you_ji, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.userComment)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userComment))
                        .addParam("userID", uid)
                        .addParam("page", "1")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("32131",data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                        Log.e("222", page + "");
                                        refreshlayout.finishRefresh();
                                    }
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
                ViseHttp.POST(NetConfig.userComment)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userComment))
                        .addParam("userID", uid)
                        .addParam("page", page + "")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
                                        Log.e("222", page + "");
                                        refreshlayout.finishLoadMore();
                                    }
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

        uid = spImp.getUID();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.userComment)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userComment))
                .addParam("userID", uid)
                .addParam("page", page + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                Log.d("sadsada",data);
                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                mList = model.getObj();
                                adapter = new MyCommentAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                page = page + 1;
                                Log.e("222", page + "");
                                adapter.setOnDeleteListener(new MyCommentAdapter.OnDelete() {
                                    @Override
                                    public void onDelete(final int position) {
                                        toDialog(getContext(), "提示", "是否删除评论", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ViseHttp.POST(NetConfig.deleteCommentUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteCommentUrl))
                                                        .addParam("id", mList.get(position).getFcID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if(jsonObject1.getInt("code") == 200){
                                                                        toToast(getContext(), "删除成功");
                                                                        mList.remove(position);
                                                                        adapter.notifyDataSetChanged();
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
                                        }, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                    }
                                });
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
}
