package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.netease.nim.uikit.common.adapter.TViewHolder;
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
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeHuoDongAdapter;
import com.yiwo.friendscometogether.newmodel.AttentionNumModel;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanZhuActivity extends BaseActivity {

    private Context context = GuanZhuActivity.this;

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.rl_woguanhude)
    RelativeLayout rl_woguanzhude;
    @BindView(R.id.rl_guanzhuwode)
    RelativeLayout rl_guanzhuwode;
    @BindView(R.id.rl_guanzhuhuodong)
    RelativeLayout rl_guanzhuhuodong;

    @BindView(R.id.tv_woguanzhude)
    TextView tv_woguanzhude;
    @BindView(R.id.tv_guanzhuwode)
    TextView tv_guanzhuwode;
    @BindView(R.id.tv_guanzhuhuodong)
    TextView tv_guanzhuhuodong;


    @BindView(R.id.rv_woguanzhude)
    RecyclerView rv_woguanzhude;
    @BindView(R.id.rv_guanzhuwode)
    RecyclerView rv_guanzhuwode;
    @BindView(R.id.rv_guanzhuhuodong)
    RecyclerView rv_guanzhuhuodong;

    @BindView(R.id.guanzhu_refreshlayout)
    RefreshLayout refreshLayout;

    private SpImp spImp;
    private int page_woguanzhude = 1;
    private int page_guanzhuwode = 1;
    private int page_guanzhuhuodong = 1; //  关注活动接口无分页

    private PopupWindow popupWindow;

    private String[] block;
    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;
    private int type = 3; //0 我付费，1 我不付费，3 免费
    private int typeNoName = 0; // 0不是匿名要请  1匿名要请
    private int type_showLayout = 0;//0为我关注的，1为关注我的，2 为关注活动
    private List<UserFocusModel.ObjBean> mWoGuanZhuDeList ;
    private WoGuanZhuDeAdapter woGuanZhuDeAdapter;

    private List<GuanZhuWoDeModel.ObjBean> mGuanZhuWoDeList ;
    private GuanZhuWoDeAdapter guanZhuWoDeAdapter;

    private List<GuanZhuHuoDongModel.ObjBean> mGuanZhuHuoDongList ;
    private WoGuanZhuDeHuoDongAdapter guanZhuDeHuoDongAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu);
        spImp = new SpImp(GuanZhuActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(GuanZhuActivity.this);
        initRefresh();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(GuanZhuActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(GuanZhuActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                initNum();
                switch (type_showLayout){
                    case 0:
                        ViseHttp.POST(NetConfig.userFocus)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                                .addParam("page", "1")
                                .addParam("userID", spImp.getUID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                Gson gson = new Gson();
                                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                                mWoGuanZhuDeList.clear();
                                                mWoGuanZhuDeList.addAll(userFocusModel.getObj());
                                                woGuanZhuDeAdapter.notifyDataSetChanged();
                                                page_woguanzhude = 2;
                                                Log.e("222page_woguanzhude", page_woguanzhude+"");
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
                        break;
                    case 1:
                        ViseHttp.POST(NetConfig.guanZhuWoDe)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                                .addParam("page", "1")
                                .addParam("userID", spImp.getUID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                Gson gson = new Gson();
                                                GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                                mGuanZhuWoDeList.clear();
                                                mGuanZhuWoDeList.addAll(guanZhuWoDeModel.getObj());
                                                guanZhuWoDeAdapter.notifyDataSetChanged();
                                                page_guanzhuwode = 2 ;
                                                Log.e("222page_guanzhuwode", page_guanzhuwode+"");
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
                        break;
                    case 2:
                        ViseHttp.POST(NetConfig.MyFocusActiveUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.MyFocusActiveUrl))
                                .addParam("uid", spImp.getUID())
                                .request(new ACallback<String>() {

                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                GuanZhuHuoDongModel model = gson.fromJson(data, GuanZhuHuoDongModel.class);
                                                mGuanZhuHuoDongList = model.getObj();
                                                guanZhuDeHuoDongAdapter.notifyDataSetChanged();
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

                        break;
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (type_showLayout){
                    case 0:
                        ViseHttp.POST(NetConfig.userFocus)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                                .addParam("page", page_woguanzhude+"")
                                .addParam("userID", spImp.getUID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                Gson gson = new Gson();
                                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                                mWoGuanZhuDeList.addAll(userFocusModel.getObj());
                                                woGuanZhuDeAdapter.notifyDataSetChanged();
                                                page_woguanzhude++;
                                                Log.e("222page_woguanzhude", page_woguanzhude+"");
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
                        break;
                    case 1:
                        ViseHttp.POST(NetConfig.guanZhuWoDe)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                                .addParam("page", page_guanzhuwode+"")
                                .addParam("userID", spImp.getUID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200) {
                                                Gson gson = new Gson();
                                                GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                                mGuanZhuWoDeList.addAll(guanZhuWoDeModel.getObj());
                                                guanZhuWoDeAdapter.notifyDataSetChanged();
                                                page_guanzhuwode++ ;
                                                Log.e("222page_guanzhuwode", page_guanzhuwode+"");
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
                        break;
                    case 2:
                        refreshLayout.finishLoadMore();
                        break;
                }
            }
        });
    }

    private void initData() {


        //---------------数量---------------------------
        initNum();
        //----------------我关注的---------------------
        ViseHttp.POST(NetConfig.userFocus)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                mWoGuanZhuDeList = userFocusModel.getObj();
                                woGuanZhuDeAdapter = new WoGuanZhuDeAdapter(mWoGuanZhuDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_woguanzhude.setLayoutManager(manager);
                                rv_woguanzhude.setAdapter(woGuanZhuDeAdapter);
                                page_woguanzhude = 2;
                                Log.e("222", page_woguanzhude+"");
                                //取消关注监听回调
                                woGuanZhuDeAdapter.setListener(new WoGuanZhuDeAdapter.OnCancelFocusListener() {
                                    @Override
                                    public void onCancel(final int i) {
                                        toDialog(context, "提示", "是否取消关注", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ViseHttp.POST(NetConfig.userCancelFocusUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCancelFocusUrl))
                                                        .addParam("listId", mWoGuanZhuDeList.get(i).getLID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                        toToast(context, "取消关注成功");
                                                                        initNum();
                                                                        mWoGuanZhuDeList.remove(i);
                                                                        woGuanZhuDeAdapter.notifyDataSetChanged();
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
                                //邀请监听回调
                                woGuanZhuDeAdapter.setListener_invitation(new WoGuanZhuDeAdapter.OnFocusInvitationListener() {
                                    @Override
                                    public void onInvitation(int position) {
                                        showInvitationPopupwindow(mWoGuanZhuDeList.get(position).getLikeuserID());
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
        //-------------------关注我的---------------------
        ViseHttp.POST(NetConfig.guanZhuWoDe)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                mGuanZhuWoDeList = guanZhuWoDeModel.getObj();
                                guanZhuWoDeAdapter = new GuanZhuWoDeAdapter(mGuanZhuWoDeList);
                                guanZhuWoDeAdapter.setGuanZhuListionner(new GuanZhuWoDeAdapter.GuanZhuListion() {
                                    @Override
                                    public void guanzhu(final int posion) {
                                        ViseHttp.POST(NetConfig.userFocusUrl)
                                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                                .addParam("uid", spImp.getUID())
                                                .addParam("likeId", mGuanZhuWoDeList.get(posion).getUserID())
                                                .request(new ACallback<String>() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(data);
                                                            if (jsonObject.getInt("code") == 200) {
                                                                toToast(GuanZhuActivity.this, "关注成功");
                                                                mGuanZhuWoDeList.get(posion).setIs_follow("1");
                                                                guanZhuWoDeAdapter.notifyDataSetChanged();
                                                                initNum();
                                                            }else {
                                                                toToast(GuanZhuActivity.this, jsonObject.getString("message"));
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
                                guanZhuWoDeAdapter.setListener_invitation(new GuanZhuWoDeAdapter.OnFocusInvitationListener() {
                                    @Override
                                    public void onInvitation(int position) {
                                        showInvitationPopupwindow(mGuanZhuWoDeList.get(position).getUserID());
                                    }
                                });
                                guanZhuWoDeAdapter.setCancelGuanZhu(new GuanZhuWoDeAdapter.CancelGuanZhuListion() {
                                    @Override
                                    public void cancel_guanzhu(final int posion) {
                                    }
                                });
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_guanzhuwode.setLayoutManager(manager);
                                rv_guanzhuwode.setAdapter(guanZhuWoDeAdapter);

                                page_guanzhuwode = 2;
                                Log.e("222", page_guanzhuwode+"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //---------------关注活动-------------------
        Log.d("app_key",getToken(NetConfig.BaseUrl+NetConfig.MyFocusActiveUrl));
        ViseHttp.POST(NetConfig.MyFocusActiveUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.MyFocusActiveUrl))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("ljc_",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GuanZhuHuoDongModel model = gson.fromJson(data, GuanZhuHuoDongModel.class);
                                mGuanZhuHuoDongList = model.getObj();
                                guanZhuDeHuoDongAdapter = new WoGuanZhuDeHuoDongAdapter(mGuanZhuHuoDongList);
                                guanZhuDeHuoDongAdapter.setCancelGuanZHuLis(new WoGuanZhuDeHuoDongAdapter.CancelGuanZhuListion() {
                                    @Override
                                    public void cancleGuanzhu(final int posion) {
                                        toDialog(context, "提示：", "是否取消关注", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.focusOnToFriendTogetherUrl))
                                                        .addParam("userID", spImp.getUID())
                                                        .addParam("pfID", mGuanZhuHuoDongList.get(posion).getPfID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(data);
                                                                    if(jsonObject.getInt("code") == 200){
                                                                        toToast(context,"取消关注成功");
                                                                        initNum();
                                                                        mGuanZhuHuoDongList.remove(posion);
                                                                        guanZhuDeHuoDongAdapter.notifyDataSetChanged();
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
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_guanzhuhuodong.setLayoutManager(manager);
                                rv_guanzhuhuodong.setAdapter(guanZhuDeHuoDongAdapter);
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

    private void initNum() {
        ViseHttp.POST(NetConfig.getAttentionNum)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getAttentionNum))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                AttentionNumModel attentionNumModel = gson.fromJson(data, AttentionNumModel.class);
                                tv_woguanzhude.setText("我关注的\n("+attentionNumModel.getObj().getAttentionNum()+")");
                                tv_guanzhuwode.setText("关注我的\n("+attentionNumModel.getObj().getAttentionMe()+")");
                                tv_guanzhuhuodong.setText("关注活动\n("+attentionNumModel.getObj().getAttentionActivity()+")");
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

    @OnClick({R.id.rl_woguanhude,R.id.rl_guanzhuwode,R.id.rl_guanzhuhuodong,R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_woguanhude:
                type_showLayout = 0;
                tv_woguanzhude.setTextColor(Color.parseColor("#D84C37"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                rv_woguanzhude.setVisibility(View.VISIBLE);
                rv_guanzhuwode.setVisibility(View.GONE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                initData();
                break;
            case R.id.rl_guanzhuwode:
                type_showLayout = 1;
                tv_guanzhuwode.setTextColor(Color.parseColor("#D84C37"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuwode.setVisibility(View.VISIBLE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                rv_woguanzhude.setVisibility(View.GONE);
                initData();
                break;
            case R.id.rl_guanzhuhuodong:
                type_showLayout = 2;
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#D84C37"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuhuodong.setVisibility(View.VISIBLE);
                rv_woguanzhude.setVisibility(View.GONE);
                rv_guanzhuwode.setVisibility(View.GONE);
                initData();
                break;
            case R.id.rl_back:
               finish();
                break;
        }
    }
    private void showInvitationPopupwindow(final String otherUid) {

        View view = LayoutInflater.from(GuanZhuActivity.this).inflate(R.layout.popupwindow_invitation, null);
        ScreenAdapterTools.getInstance().loadView(view);

        RelativeLayout rl = view.findViewById(R.id.activity_create_friend_remember_rl_active_title);
//        final RadioGroup radioGroup = view.findViewById(R.id.rg);
        final TextView tvActiveTitle = view.findViewById(R.id.activity_create_friend_remember_tv_active_title);
        TextView tvOk = view.findViewById(R.id.tv_ok);
        final EditText et = view.findViewById(R.id.et);
        final CheckBox checkBox = view.findViewById(R.id.cb);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    type = 0;
//                    toToast(MyFocusActivity.this, type+"");
                }else {
                    type = 1;
//                    toToast(MyFocusActivity.this, type+"");
                }
            }
        });
        final CheckBox checkBox_no_name = view.findViewById(R.id.cb_niming);
        checkBox_no_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    typeNoName = 1;
                }else {
                    typeNoName = 0;
                }
            }
        });

        ViseHttp.POST(NetConfig.activeInvitationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationListUrl))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserActiveListModel model = gson.fromJson(data, UserActiveListModel.class);
                                activeList = model.getObj();
                                activeId = new String[model.getObj().size()];
                                activeName = new String[model.getObj().size()];
                                block = new String[model.getObj().size()];
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    activeId[i] = model.getObj().get(i).getPfID();
                                    activeName[i] = model.getObj().get(i).getPftitle();
                                    block[i] = model.getObj().get(i).getBlock();
                                }
                                if(block[0].equals("0")){
                                    checkBox.setVisibility(View.GONE);
                                    type = 3;
                                }else {
                                    checkBox.setVisibility(View.VISIBLE);
                                    type = 1;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.rb1:
//                        toToast(MyFocusActivity.this, "我请客");
//                        type = 0;
//                        break;
////                    case R.id.rb2:
////                        toToast(MyFocusActivity.this, "自费");
////                        type = 1;
////                        break;
//                }
//            }
//        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //活动标题
                if (activeList.size() > 0) {
                    AlertDialog.Builder singleChoiceDialog1 =
                            new AlertDialog.Builder(GuanZhuActivity.this);
                    singleChoiceDialog1.setTitle("请选择活动");
                    // 第二个参数是默认选项，此处设置为0
                    singleChoiceDialog1.setSingleChoiceItems(activeName, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    yourChoiceActiveName = activeName[which];
                                    yourChoiceActiveId = activeId[which];
                                    if(block[which].equals("0")){
                                        checkBox.setVisibility(View.GONE);
                                        type = 3;
                                    }else {
                                        checkBox.setVisibility(View.VISIBLE);
                                        type = 1;
                                    }
                                }
                            });
                    singleChoiceDialog1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TextUtils.isEmpty(yourChoiceActiveName)) {
                                        tvActiveTitle.setText(activeName[0]);
                                        yourChoiceActiveId = activeId[0];
                                    } else {
                                        tvActiveTitle.setText(yourChoiceActiveName);
                                        yourChoiceActiveName = "";
                                    }
                                }
                            });
                    singleChoiceDialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    singleChoiceDialog1.show();
                } else {
                    toToast(GuanZhuActivity.this, "暂无活动");
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定
                ViseHttp.POST(NetConfig.activeInvitationUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("bid", otherUid)
                        .addParam("tid", yourChoiceActiveId)
                        .addParam("type", type + "")
                        .addParam("text", et.getText().toString())
                        .addParam("no_name",typeNoName+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(GuanZhuActivity.this, "邀请成功");
                                        popupWindow.dismiss();
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

//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popupWindow.dismiss();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
