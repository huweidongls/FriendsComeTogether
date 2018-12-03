package com.yiwo.friendscometogether.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.yiwo.friendscometogether.adapter.FriendTogetherUpDataAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.AllBannerModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.FriendsRememberModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.CreateFriendRememberActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFocusActivity;
import com.yiwo.friendscometogether.pages.SearchActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

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

public class FriendsTogetherFragment extends BaseFragment {
    View rootView;
    @BindView(R.id.fragment_friend_together_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_friend_together_refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.locationRl)
    RelativeLayout locationRl;
    @BindView(R.id.searchLl)
    LinearLayout searchLl11;
    @BindView(R.id.cityTv)
    TextView tvCity;
    @BindView(R.id.rv_label)
    RecyclerView rvLabel;

    private FriendTogetherUpDataAdapter adapter;
    private String[] itemId;
    private String[] itemName;
    private String yourChoiceId = "";
    private String yourChoiceName = "";
    SpImp spImp;

    private int page = 1;
    private List<FriendsTogethermodel.ObjBean> mList;

    private String cityId = "";

    private String sign = "";

    private FriendTogetherLabelAdapter labelAdapter;
    private List<UserLabelModel.ObjBean> labelList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friends_together, null);
        ScreenAdapterTools.getInstance().loadView(rootView);

        ButterKnife.bind(this, rootView);

        spImp = new SpImp(getContext());
//        initData();
        return rootView;
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    private void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
                ViseHttp.POST(NetConfig.friendsTogetherUrl)
                        .addParam("app_key", token)
                        .addParam("page", "1")
                        .addParam("userID", spImp.getUID())
                        .addParam("city_id", cityId)
                        .addParam("sign", sign)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Log.e("222", data);
                                        FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                        page = 2;
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
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
                String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
                ViseHttp.POST(NetConfig.friendsTogetherUrl)
                        .addParam("app_key", token)
                        .addParam("page", page + "")
                        .addParam("userID", spImp.getUID())
                        .addParam("city_id", cityId)
                        .addParam("sign", sign)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Log.e("222", data);
                                        FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                        page = page + 1;
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
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

        getLable();

        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                .addParam("app_key", token)
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                page = 2;
                                initList(model.getObj());
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

    private void initList(List<FriendsTogethermodel.ObjBean> data) {

        mList = data;

        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new FriendTogetherUpDataAdapter(mList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.locationRl, R.id.searchLl})
    public void OnClick(View v) {
        if (spImp.getUID().equals("0")) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            Log.i("110120", spImp.getUID());
            switch (v.getId()) {
                case R.id.locationRl:
                    Intent it1 = new Intent(getActivity(), CityActivity.class);
                    it1.putExtra(ActivityConfig.ACTIVITY, "home");
                    startActivityForResult(it1, 1);
                    break;
                case R.id.searchLl:
                    Intent intent1 = new Intent(getContext(), SearchActivity.class);
                    intent1.putExtra("type", "1");
                    startActivity(intent1);
                    break;
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && resultCode == 1) {
            CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
            tvCity.setText(model.getName());
            cityId = model.getId();
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("city_id", cityId)
                    .addParam("sign", sign)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    page = 2;
                                    initList(model.getObj());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else if(requestCode == 1 && resultCode == 2){
            cityId = "";
            tvCity.setText("选择城市");
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("city_id", cityId)
                    .addParam("sign", sign)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    page = 2;
                                    initList(model.getObj());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else if (requestCode == 1 && resultCode == 3) {
            String city = data.getStringExtra("city");
            cityId = data.getStringExtra("cityid");
            tvCity.setText(city);
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("city_id", cityId)
                    .addParam("sign", sign)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    page = 2;
                                    initList(model.getObj());
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

    public void getLable() {
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "0")
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
                                        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
                                        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                                                .addParam("app_key", token)
                                                .addParam("page", "1")
                                                .addParam("userID", spImp.getUID())
                                                .addParam("city_id", cityId)
                                                .addParam("sign", sign)
                                                .request(new ACallback<String>() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(data);
                                                            if (jsonObject.getInt("code") == 200) {
                                                                Log.e("222", data);
                                                                FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                                                page = 2;
                                                                initList(model.getObj());
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
}
