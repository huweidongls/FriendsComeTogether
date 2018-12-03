package com.yiwo.friendscometogether.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.yiwo.friendscometogether.adapter.FragmentReturnPriceAdapter;
import com.yiwo.friendscometogether.adapter.FragmentToCommentAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.base.OrderBaseFragment;
import com.yiwo.friendscometogether.model.CommentFragmentModel;
import com.yiwo.friendscometogether.model.PayFragmentModel;
import com.yiwo.friendscometogether.model.ReturnPriceFragmentModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/18.
 */

public class ReturnPriceFragment extends OrderBaseFragment {

    @BindView(R.id.fragment_return_price_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_return_price_refreshLayout)
    RefreshLayout refreshLayout;

    private FragmentReturnPriceAdapter adapter;
    private List<ReturnPriceFragmentModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    private int page = 1;

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_return_price, null);
        ScreenAdapterTools.getInstance().loadView(view);

        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        return view;
    }

    @Override
    public void initData() {
        initData1();
    }

    private void initData1() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.myOrderListUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.myOrderListUrl))
                        .addParam("page", "1")
                        .addParam("userID", uid)
                        .addParam("type", "4")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        ReturnPriceFragmentModel model = gson.fromJson(data, ReturnPriceFragmentModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                        refreshlayout.finishRefresh(1000);
                                    }
                                    refreshlayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.myOrderListUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.myOrderListUrl))
                        .addParam("page", page + "")
                        .addParam("userID", uid)
                        .addParam("type", "4")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        ReturnPriceFragmentModel model = gson.fromJson(data, ReturnPriceFragmentModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
                                        refreshlayout.finishLoadMore(1000);
                                    }
                                    refreshlayout.finishLoadMore(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshlayout.finishLoadMore(1000);
                            }
                        });
            }
        });

        uid = spImp.getUID();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.myOrderListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.myOrderListUrl))
                .addParam("page", "1")
                .addParam("userID", uid)
                .addParam("type", "4")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ReturnPriceFragmentModel model = gson.fromJson(data, ReturnPriceFragmentModel.class);
                                mList = model.getObj();
                                adapter = new FragmentReturnPriceAdapter(mList, getActivity());
                                recyclerView.setAdapter(adapter);
                                page = 2;
                                adapter.setOnDeleteListener(new FragmentReturnPriceAdapter.OnDeleteListener() {
                                    @Override
                                    public void onDelete(final int position) {
                                        AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
                                        normalDialog.setIcon(R.mipmap.ic_launcher);
                                        normalDialog.setTitle("提示");
                                        normalDialog.setMessage("是否删除活动");
                                        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                ViseHttp.POST(NetConfig.deleteOrderTripUrl)
                                                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.deleteOrderTripUrl))
                                                        .addParam("order_id", mList.get(position).getOID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                        Toast.makeText(getContext(), "删除活动成功", Toast.LENGTH_SHORT).show();
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
                                        });
                                        normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        // 显示
                                        normalDialog.show();
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
