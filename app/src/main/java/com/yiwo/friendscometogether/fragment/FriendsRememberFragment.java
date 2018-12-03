package com.yiwo.friendscometogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.yiwo.friendscometogether.adapter.FriendRememberUpDataAdapter;
import com.yiwo.friendscometogether.adapter.FriendTogetherLabelAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.GlideImageLoader;
import com.yiwo.friendscometogether.model.AllBannerModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.FriendsRememberModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.CreateIntercalationActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/16.
 */

public class FriendsRememberFragment extends BaseFragment {
    View rootView;

//    @BindView(R.id.fragment_friend_remember_banner)
//    Banner banner;
    @BindView(R.id.fragment_friend_remember_rv)
    RecyclerView recyclerView;
    @BindView(R.id.searchLl)
    LinearLayout llSearch;
    @BindView(R.id.fragment_friend_remember_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_label)
    RecyclerView rvLabel;

    private FriendRememberUpDataAdapter adapter;
    private List<FriendsRememberModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    private int page = 1;

    private String sign = "";
    private FriendTogetherLabelAdapter labelAdapter;
    private List<UserLabelModel.ObjBean> labelList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friends_remember, null);
        ScreenAdapterTools.getInstance().loadView(rootView);

        ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            getLable();
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            getLable();
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    public void getLable() {
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);

                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                rvLabel.setLayoutManager(manager);

                                labelList = new ArrayList<>();
                                labelList.add(new UserLabelModel.ObjBean("", "全部", ""));
                                labelList.addAll(userLabelModel.getObj());
                                labelAdapter = new FriendTogetherLabelAdapter(labelList);
                                rvLabel.setAdapter(labelAdapter);

                                labelAdapter.setOnLabelListener(new FriendTogetherLabelAdapter.OnLabelListener() {
                                    @Override
                                    public void onLabel(int position) {
                                        labelAdapter.setCurrentItem(position);
                                        labelAdapter.notifyDataSetChanged();
                                        sign = labelList.get(position).getLID();
                                        ViseHttp.POST(NetConfig.friendsRememberUrl)
                                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.friendsRememberUrl))
                                                .addParam("page", "1")
                                                .addParam("userID", uid)
                                                .addParam("table", sign)
                                                .request(new ACallback<String>() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        Log.e("222", data);
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(data);
                                                            int code = jsonObject.getInt("code");
                                                            if (code == 200) {
                                                                Gson gson = new Gson();
                                                                FriendsRememberModel friendsRememberModel = gson.fromJson(data, FriendsRememberModel.class);
                                                                mList.clear();
                                                                mList.addAll(friendsRememberModel.getObj());
                                                                adapter.notifyDataSetChanged();
                                                                page = 2;
                                                            } else {
                                                                toToast(getContext(), jsonObject.getString("message"));
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

    private void initData() {

//        ViseHttp.POST(NetConfig.allBannerUrl)
//                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.allBannerUrl))
//                .addParam("type", "3")
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(data);
//                            if (jsonObject.getInt("code") == 200) {
//                                Gson gson = new Gson();
//                                final AllBannerModel bannerModel = gson.fromJson(data, AllBannerModel.class);
//                                List<String> list = new ArrayList<>();
//                                for (int i = 0; i < bannerModel.getObj().size(); i++) {
//                                    list.add(bannerModel.getObj().get(i).getPic());
//                                }
//                                init(banner, list);
//                                banner.setOnBannerListener(new OnBannerListener() {
//                                    @Override
//                                    public void OnBannerClick(int position) {
//                                        Intent intent = new Intent(getContext(), DetailsOfFriendsActivity.class);
//                                        intent.putExtra("fmid", bannerModel.getObj().get(position).getLeftid());
//                                        startActivity(intent);
//                                    }
//                                });
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

        uid = spImp.getUID();

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.friendsRememberUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.friendsRememberUrl))
                        .addParam("page", "1")
                        .addParam("userID", uid)
                        .addParam("table", sign)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    int code = jsonObject.getInt("code");
                                    if (code == 200) {
                                        Gson gson = new Gson();
                                        FriendsRememberModel friendsRememberModel = gson.fromJson(data, FriendsRememberModel.class);
                                        mList.clear();
                                        mList.addAll(friendsRememberModel.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                    } else {
                                        toToast(getContext(), jsonObject.getString("message"));
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
                ViseHttp.POST(NetConfig.friendsRememberUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.friendsRememberUrl))
                        .addParam("page", page + "")
                        .addParam("userID", uid)
                        .addParam("table", sign)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    int code = jsonObject.getInt("code");
                                    if (code == 200) {
                                        Gson gson = new Gson();
                                        FriendsRememberModel friendsRememberModel = gson.fromJson(data, FriendsRememberModel.class);
                                        mList.addAll(friendsRememberModel.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
                                    } else {
                                        toToast(getContext(), jsonObject.getString("message"));
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

        ViseHttp.POST(NetConfig.friendsRememberUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.friendsRememberUrl))
                .addParam("page", "1")
                .addParam("userID", uid)
                .addParam("table", sign)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            if (code == 200) {
                                Gson gson = new Gson();
                                FriendsRememberModel friendsRememberModel = gson.fromJson(data, FriendsRememberModel.class);
                                page = page + 1;
                                initList(friendsRememberModel.getObj());
                            } else {
                                toToast(getContext(), jsonObject.getString("message"));
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

    private void initList(final List<FriendsRememberModel.ObjBean> data) {

        mList = data;
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new FriendRememberUpDataAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnFocusListener(new FriendRememberUpDataAdapter.OnFocusListener() {
            @Override
            public void onFocus(final int position) {
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (mList.get(position).getLook().equals("0")) {
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                .addParam("uid", uid)
                                .addParam("likeId", data.get(position).getUserID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                mList.get(position).setLook("1");
                                                adapter.notifyDataSetChanged();
                                                toToast(getContext(), "关注成功");
                                            }else {
                                                toToast(getContext(), jsonObject.getString("message"));
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
