package com.yiwo.friendscometogether.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

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
import com.yiwo.friendscometogether.adapter.MessageViewAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MessageViewModel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息中心查看
 */
public class MessageViewActivity extends BaseActivity {
    @BindView(R.id.activity_message_view_rl_back)
    RelativeLayout backRl;
    @BindView(R.id.activity_message_view_rv)
    RecyclerView messageRv;
    @BindView(R.id.activity_message_refreshLayout)
    RefreshLayout refreshLayout;

    private SpImp spImp;
    private MessageViewAdapter adapter;
    List<MessageViewModel.ObjBean> list = new ArrayList<>();

    private String type = "";
    private int page = 1;
    private List<MessageViewModel.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(MessageViewActivity.this);
        initData();
    }

    public void initData() {

        type = getIntent().getStringExtra("type");

        ViseHttp.POST(NetConfig.systemHotMessageListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.systemHotMessageListUrl))
                .addParam("page", "1")
                .addParam("user_id", spImp.getUID())
                .addParam("type", type)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                MessageViewModel model = gson.fromJson(data, MessageViewModel.class);
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

        refreshLayout.setRefreshHeader(new ClassicsHeader(MessageViewActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(MessageViewActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.systemHotMessageListUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.systemHotMessageListUrl))
                        .addParam("page", "1")
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", type)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        MessageViewModel model = gson.fromJson(data, MessageViewModel.class);
                                        page = 2;
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
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
                ViseHttp.POST(NetConfig.systemHotMessageListUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.systemHotMessageListUrl))
                        .addParam("page", page + "")
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", type)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        MessageViewModel model = gson.fromJson(data, MessageViewModel.class);
                                        page = page + 1;
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
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

    public void initList(List<MessageViewModel.ObjBean> data) {

        mList = data;

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        messageRv.setLayoutManager(manager);
        adapter = new MessageViewAdapter(mList);
        messageRv.setAdapter(adapter);
    }

    @OnClick({R.id.activity_message_view_rl_back, R.id.rl_clean})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_message_view_rl_back:
                finish();
                break;
            case R.id.rl_clean:
                if(type.equals("1")){
                    ViseHttp.POST(NetConfig.deleteMessageUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteMessageUrl))
                            .addParam("user_id", spImp.getUID())
                            .addParam("type", "1")
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.e("22222", data);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.getInt("code") == 200){
                                            toToast(MessageViewActivity.this, "已清空");
                                            mList.clear();
                                            adapter.notifyDataSetChanged();
                                        }else {
                                            toToast(MessageViewActivity.this, jsonObject.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }else if(type.equals("0")){
                    ViseHttp.POST(NetConfig.deleteMessageUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteMessageUrl))
                            .addParam("user_id", spImp.getUID())
                            .addParam("type", "0")
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.e("22222", data);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.getInt("code") == 200){
                                            toToast(MessageViewActivity.this, "已清空");
                                            mList.clear();
                                            adapter.notifyDataSetChanged();
                                        }else {
                                            toToast(MessageViewActivity.this, jsonObject.getString("message"));
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
                break;
        }
    }
}
