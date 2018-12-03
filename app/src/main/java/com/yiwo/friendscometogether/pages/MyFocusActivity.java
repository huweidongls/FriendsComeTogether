package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.yiwo.friendscometogether.adapter.ActivityVideoAdapter;
import com.yiwo.friendscometogether.adapter.MyFocusAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.UserActiveListModel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.model.VideoActiveModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFocusActivity extends BaseActivity {

    @BindView(R.id.activity_my_focus_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_focus_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_my_focus_refreshLayout)
    RefreshLayout refreshLayout;

    private MyFocusAdapter adapter;
    private List<UserFocusModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    private int page = 1;

    private PopupWindow popupWindow;

    private String[] block;
    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;
    private int type = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(MyFocusActivity.this);

        initData();

    }

    private void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(MyFocusActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(MyFocusActivity.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.userFocus)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                        .addParam("page", "1")
                        .addParam("userID", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                        mList.clear();
                                        mList.addAll(userFocusModel.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2;
                                        Log.e("222", page+"");
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
                ViseHttp.POST(NetConfig.userFocus)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                        .addParam("page", page + "")
                        .addParam("userID", uid)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                        mList.addAll(userFocusModel.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = page + 1;
                                        Log.e("222", page+"");
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
        LinearLayoutManager manager = new LinearLayoutManager(MyFocusActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.userFocus)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                .addParam("page", page + "")
                .addParam("userID", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);
                                mList = userFocusModel.getObj();
                                adapter = new MyFocusAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                page = page + 1;
                                Log.e("222", page+"");
                                adapter.setOnFocusCancelListener(new MyFocusAdapter.OnFocusCancelListener() {
                                    @Override
                                    public void onCancel(final int position) {
                                        toDialog(MyFocusActivity.this, "提示", "是否取消关注", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                ViseHttp.POST(NetConfig.userCancelFocusUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCancelFocusUrl))
                                                        .addParam("listId", mList.get(position).getLID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                        toToast(MyFocusActivity.this, "取消关注成功");
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
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                    }
                                });
                                adapter.setOnInvitationListener(new MyFocusAdapter.OnFocusInvitationListener() {
                                    @Override
                                    public void onInvitation(int position) {
                                        //邀请
//                                        if(mList.get(position).getActivity_id().equals("0")){
//                                            Intent intent = new Intent();
//                                            intent.putExtra("otheruid", mList.get(position).getLikeuserID());
//                                            intent.setClass(MyFocusActivity.this, InvitationActivity.class);
//                                            startActivity(intent);
                                            showInvitationPopupwindow(mList.get(position).getLikeuserID());
//                                        }else {
//                                            Toast.makeText(MyFocusActivity.this, "已参加活动，无法邀请", Toast.LENGTH_SHORT).show();
//                                        }
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

    private void showInvitationPopupwindow(final String otherUid) {

        View view = LayoutInflater.from(MyFocusActivity.this).inflate(R.layout.popupwindow_invitation, null);
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
                            new AlertDialog.Builder(MyFocusActivity.this);
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
                    toToast(MyFocusActivity.this, "暂无活动");
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定
                ViseHttp.POST(NetConfig.activeInvitationUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeInvitationUrl))
                        .addParam("uid", uid)
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
                                        toToast(MyFocusActivity.this, "邀请成功");
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

    @OnClick({R.id.activity_my_focus_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_focus_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyFocusActivity.this.finish();
    }
}
