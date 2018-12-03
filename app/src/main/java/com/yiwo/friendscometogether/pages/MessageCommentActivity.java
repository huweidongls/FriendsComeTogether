package com.yiwo.friendscometogether.pages;

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
import com.yiwo.friendscometogether.adapter.MessageCommentAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MessageCommentModel;
import com.yiwo.friendscometogether.model.MessageViewModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageCommentActivity extends BaseActivity {

    @BindView(R.id.activity_message_comment_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_message_view_rv)
    RecyclerView messageRv;
    @BindView(R.id.activity_message_refreshLayout)
    RefreshLayout refreshLayout;

    private SpImp spImp;
    private String uid = "";

    private MessageCommentAdapter adapter;
    private List<MessageCommentModel.ObjBean> mList;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_comment);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(MessageCommentActivity.this);
        spImp = new SpImp(MessageCommentActivity.this);

        initData();

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
                                LinearLayoutManager manager = new LinearLayoutManager(MessageCommentActivity.this);
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

        refreshLayout.setRefreshHeader(new ClassicsHeader(MessageCommentActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(MessageCommentActivity.this));
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

    @OnClick({R.id.activity_message_comment_rl_back, R.id.rl_clean})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_message_comment_rl_back:
                finish();
                break;
            case R.id.rl_clean:
                ViseHttp.POST(NetConfig.deleteMessageUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteMessageUrl))
                        .addParam("user_id", spImp.getUID())
                        .addParam("type", "2")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(MessageCommentActivity.this, "已清空");
                                        mList.clear();
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        toToast(MessageCommentActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MessageCommentActivity.this.finish();
    }
}
