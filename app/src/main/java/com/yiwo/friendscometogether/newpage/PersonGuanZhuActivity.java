package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.UserActiveListModel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.GuanZhuWoDeAdapter;
import com.yiwo.friendscometogether.newadapter.PersonMainGuanZhu_FansAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeAdapter;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;
import com.yiwo.friendscometogether.newmodel.PersonMainGuanZhu_FansModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 他人关注页面
 * 进入传  String userID //  getIntent().getStringExtra("userID");
 *         String userName // getIntent().getStringExtra("userName");(如果是我的传入"我")
 *         int tyoe // getIntent().getIntExtra("type",1);1 关注的人 2 粉丝
 */
public class PersonGuanZhuActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<PersonMainGuanZhu_FansModel.ObjBean> guanZhuDeList ;
    private PersonMainGuanZhu_FansAdapter guanZhuDeAdapter;
    private String userID;
    private String userName;
    private int page;
    private int type = 1;// 1 关注的人 2 粉丝
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_guan_zhu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonGuanZhuActivity.this);
        spImp = new SpImp(PersonGuanZhuActivity.this);
        type = getIntent().getIntExtra("type",1);
        userID = getIntent().getStringExtra("userID");
        userName = getIntent().getStringExtra("userName");
        initData();
        initRefresh();
    }
    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
    private void initData() {
        if (type == 1){
            tvTitle.setText(userName+"关注的人");
        }else {
            tvTitle.setText(userName+"的粉丝");
        }
        ViseHttp.POST(NetConfig.lookUserAttention)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.lookUserAttention))
                .addParam("page", "1")
                .addParam("myID", spImp.getUID())
                .addParam("userID",userID)
                .addParam("type",type+"")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                PersonMainGuanZhu_FansModel userFocusModel = gson.fromJson(data, PersonMainGuanZhu_FansModel.class);
                                guanZhuDeList = userFocusModel.getObj();
                                guanZhuDeAdapter = new PersonMainGuanZhu_FansAdapter(guanZhuDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(PersonGuanZhuActivity.this);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(guanZhuDeAdapter);
                                page = 2;
                                Log.e("222qwe", data+"");
                                guanZhuDeAdapter.setListener(new PersonMainGuanZhu_FansAdapter.OnCancel_FocusListener() {
                                    @Override
                                    public void listen(final int i) {
                                        ViseHttp.POST(NetConfig.focusOnLeaderUrl)
                                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.focusOnLeaderUrl))
                                                .addParam("userID", spImp.getUID())
                                                .addParam("attention_userID", guanZhuDeList.get(i).getUserID())
                                                .request(new ACallback<String>() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        try {
                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                            if (jsonObject1.getInt("code") == 200) {
                                                                guanZhuDeList.get(i).setStatus(guanZhuDeList.get(i).getStatus().equals("0")?"1":"0");
                                                                guanZhuDeAdapter.notifyDataSetChanged();
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
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(PersonGuanZhuActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(PersonGuanZhuActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.lookUserAttention)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.lookUserAttention))
                        .addParam("page", "1")
                        .addParam("myID", spImp.getUID())
                        .addParam("userID",userID)
                        .addParam("type",type+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        PersonMainGuanZhu_FansModel userFocusModel = gson.fromJson(data, PersonMainGuanZhu_FansModel.class);
                                        guanZhuDeList.clear();
                                        guanZhuDeList.addAll(userFocusModel.getObj());
                                        guanZhuDeAdapter.notifyDataSetChanged();
                                        page = 2;
                                        Log.e("222page_woguanzhude", page+"");
                                        refreshLayout.finishRefresh(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.lookUserAttention)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.lookUserAttention))
                        .addParam("page", ""+page)
                        .addParam("myID", spImp.getUID())
                        .addParam("userID",userID)
                        .addParam("type",type+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        PersonMainGuanZhu_FansModel userFocusModel = gson.fromJson(data, PersonMainGuanZhu_FansModel.class);
                                        guanZhuDeList.addAll(userFocusModel.getObj());
                                        guanZhuDeAdapter.notifyDataSetChanged();
                                        page++;
                                        Log.e("222page_woguanzhude", page+"");
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }
}
