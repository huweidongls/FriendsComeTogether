package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MyFriendAdapter;
import com.yiwo.friendscometogether.custom.MyFriendDialog;
import com.yiwo.friendscometogether.model.MyFriendModel;
import com.yiwo.friendscometogether.model.UserActiveListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.FindFriendByTelActivity;
import com.yiwo.friendscometogether.newpage.GuanZhuActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhouzhuo.zzletterssidebar.ZzLetterSideBar;
import me.zhouzhuo.zzletterssidebar.interf.OnLetterTouchListener;

public class MyFriendActivity extends AppCompatActivity {

    @BindView(R.id.activity_my_friend_rl_back)
    RelativeLayout rlBack;
    private ListView listView;
    private ZzLetterSideBar sideBar;
    private TextView dialog;
    private MyFriendAdapter adapter;
    private List<MyFriendModel.ObjBean> mDatas;

    private SpImp spImp;
    private String uid = "";
    private String account = "";

    private PopupWindow popupWindow;

    private String[] block;
    private String[] activeId;
    private String[] activeName;
    private String yourChoiceActiveId = "";
    private String yourChoiceActiveName = "";
    private List<UserActiveListModel.ObjBean> activeList;
    private int type = 3; //0 我付费，1 我不付费，3 免费
    private int typeNoName = 0; // 0不是匿名要请  1匿名要请



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        ButterKnife.bind(MyFriendActivity.this);
        spImp = new SpImp(MyFriendActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        uid = spImp.getUID();
        account = spImp.getYXID();

        sideBar = (ZzLetterSideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.tv_dialog);
        listView = (ListView) findViewById(R.id.list_view);

//        //optional
//        View header = LayoutInflater.from(this).inflate(R.layout.list_item_head, null);
//        listView.addHeaderView(header);
//
//        //optional
//        View footer = LayoutInflater.from(this).inflate(R.layout.list_item_foot, null);
//        tvFoot = (TextView) footer.findViewById(R.id.tv_foot);
//        listView.addFooterView(footer);

        ViseHttp.POST(NetConfig.MyFriendListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.MyFriendListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyFriendModel model = gson.fromJson(data, MyFriendModel.class);
                                //set adapter
                                mDatas = model.getObj();
                                adapter = new MyFriendAdapter(MyFriendActivity.this, mDatas);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        liaotian(mDatas.get(i).getWy_accid());
                                    }
                                });
                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                        MyFriendDialog friendDialog = new MyFriendDialog(MyFriendActivity.this, 1, new MyFriendDialog.OnMyFriendListener() {
                                            @Override
                                            public void onReturn(int type) {
                                                switch (type){
                                                    case 0:
                                                        //删除好友
                                                        ViseHttp.POST(NetConfig.deleteFriendUrl)
                                                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.deleteFriendUrl))
                                                                .addParam("id", mDatas.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                Toast.makeText(MyFriendActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                                                mDatas.remove(i);
                                                                                adapter.updateListView(mDatas);
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
                                                    case 1:
                                                        //拉黑
                                                        ViseHttp.POST(NetConfig.blackFriendUrl)
                                                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.blackFriendUrl))
                                                                .addParam("id", mDatas.get(i).getId())
                                                                .request(new ACallback<String>() {
                                                                    @Override
                                                                    public void onSuccess(String data) {
                                                                        try {
                                                                            JSONObject jsonObject1 = new JSONObject(data);
                                                                            if(jsonObject1.getInt("code") == 200){
                                                                                Toast.makeText(MyFriendActivity.this, "已加入黑名单", Toast.LENGTH_SHORT).show();
                                                                                mDatas.remove(i);
                                                                                adapter.updateListView(mDatas);
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
                                                    case 2:
                                                        //进主页
                                                        Intent intent = new Intent();
                                                        intent.putExtra("person_id", mDatas.get(i).getUid());
                                                        intent.setClass(MyFriendActivity.this, PersonMainActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 3:
                                                        showInvitationPopupwindow(mDatas.get(i).getUid());

                                                        break;
                                                }
                                            }
                                        });
                                        friendDialog.show();
                                        return true;
                                    }
                                });

                                //update data
//                                adapter.updateListView(mDatas);
//        tvFoot.setText(mDatas.size() + "位联系人");
                                //设置右侧触摸监听
                                sideBar.setLetterTouchListener(listView, adapter, dialog, new OnLetterTouchListener() {
                                    @Override
                                    public void onLetterTouch(String letter, int position) {
                                    }

                                    @Override
                                    public void onActionUp() {
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

    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(MyFriendActivity.this, liaotianAccount);
    }

    private void showMore(View view_p) {

        View view = LayoutInflater.from(MyFriendActivity.this).inflate(R.layout.popupwindow_myfriendactivity_show_more, null);
        ScreenAdapterTools.getInstance().loadView(view);
        final PopupWindow popupWindow;
        LinearLayout ll_add_friend = view.findViewById(R.id.ll_add_friend);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        ll_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyFriendActivity.this, FindFriendByTelActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        LinearLayout ll_hei_ming_dan = view.findViewById(R.id.ll_heimingdan);
        ll_hei_ming_dan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(MyFriendActivity.this, BlackUserActivity.class);
                startActivity(intent);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(view_p,0,0);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });


    }

    @OnClick({R.id.activity_my_friend_rl_back, R.id.activity_my_friend_show_more})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_my_friend_rl_back:
                onBackPressed();
                break;
            case R.id.activity_my_friend_show_more:
                showMore(view);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyFriendActivity.this.finish();
    }
    private void showInvitationPopupwindow(final String otherUid) {

        View view = LayoutInflater.from(MyFriendActivity.this).inflate(R.layout.popupwindow_invitation, null);
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
                                if (block.length>0){
                                    if(block[0].equals("0")){
                                        checkBox.setVisibility(View.GONE);
                                        type = 3;
                                    }else {
                                        checkBox.setVisibility(View.VISIBLE);
                                        type = 1;
                                    }
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
                            new AlertDialog.Builder(MyFriendActivity.this);
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
                    toToast(MyFriendActivity.this, "暂无活动");
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
                                        toToast(MyFriendActivity.this, "邀请成功");
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
    public void toToast(Context c, String content){
        Toast.makeText(c,content,Toast.LENGTH_SHORT).show();
    }
    public String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }
}
