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
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeAdapter;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;

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
 */
public class PersonGuanZhuActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private PopupWindow popupWindow;

    private String[] block;
    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;
    private int type = 3;

    private List<UserFocusModel.ObjBean> guanZhuDeList ;
    private WoGuanZhuDeAdapter guanZhuDeAdapter;
    private String userID;
    private String userName;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_guan_zhu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonGuanZhuActivity.this);
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
        ViseHttp.POST(NetConfig.userFocus)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                .addParam("page", "1")
                .addParam("userID", userID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                guanZhuDeList = userFocusModel.getObj();
                                guanZhuDeAdapter = new WoGuanZhuDeAdapter(guanZhuDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(PersonGuanZhuActivity.this);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(guanZhuDeAdapter);
                                page = 2;
                                Log.e("222", page+"");
                                //取消关注监听回调
                                guanZhuDeAdapter.setListener(new WoGuanZhuDeAdapter.OnCancelFocusListener() {
                                    @Override
                                    public void onCancel(final int i) {
                                        toDialog(PersonGuanZhuActivity.this, "提示", "是否取消关注", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ViseHttp.POST(NetConfig.userCancelFocusUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCancelFocusUrl))
                                                        .addParam("listId", guanZhuDeList.get(i).getLID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                        toToast(PersonGuanZhuActivity.this, "取消关注成功");
                                                                        guanZhuDeList.remove(i);
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
                                        }, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                    }
                                });
                                //邀请监听回调
                                guanZhuDeAdapter.setListener_invitation(new WoGuanZhuDeAdapter.OnFocusInvitationListener() {
                                    @Override
                                    public void onInvitation(int position) {
                                        showInvitationPopupwindow(guanZhuDeList.get(position).getLikeuserID());
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
                ViseHttp.POST(NetConfig.userFocus)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                        .addParam("page", "1")
                        .addParam("userID", userID)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
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
                ViseHttp.POST(NetConfig.userFocus)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                        .addParam("page", page+"")
                        .addParam("userID",userID)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
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
    private void showInvitationPopupwindow(final String otherUid) {

        View view = LayoutInflater.from(PersonGuanZhuActivity.this).inflate(R.layout.popupwindow_invitation, null);
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

        ViseHttp.POST(NetConfig.activeInvitationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationListUrl))
                .addParam("uid",userID)
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
                            new AlertDialog.Builder(PersonGuanZhuActivity.this);
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
                    toToast(PersonGuanZhuActivity.this, "暂无活动");
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定
                ViseHttp.POST(NetConfig.activeInvitationUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationUrl))
                        .addParam("uid", userID)
                        .addParam("bid", otherUid)
                        .addParam("tid", yourChoiceActiveId)
                        .addParam("type", type + "")
                        .addParam("text", et.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(PersonGuanZhuActivity.this, "邀请成功");
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
}
