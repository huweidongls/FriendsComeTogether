package com.yiwo.friendscometogether;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionEventListener;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.broadcastreceiver.MyGoPersonMainBroadcastReceiver;
import com.yiwo.friendscometogether.broadcastreceiver.MyShenQingJinQunBroadcastReceiver;
import com.yiwo.friendscometogether.fragment.ChatFragment;
import com.yiwo.friendscometogether.fragment.FriendsRememberFragment;
import com.yiwo.friendscometogether.fragment.FriendsTogetherFragment;
import com.yiwo.friendscometogether.fragment.FriendsTogetherFragment1;
import com.yiwo.friendscometogether.fragment.FriendsTogetherFragment2;
import com.yiwo.friendscometogether.fragment.HomeFragment;
import com.yiwo.friendscometogether.fragment.HomeFragment1;
import com.yiwo.friendscometogether.fragment.MyFragment;
import com.yiwo.friendscometogether.fragment.MyFragment1;
import com.yiwo.friendscometogether.newfragment.YouJiFragment;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.pages.CreateFriendRememberActivity;
import com.yiwo.friendscometogether.pages.GuideActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.WelcomeActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    private Context context = MainActivity.this;

    public static FragmentTabHost tabHost;
    // Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "友聚", "友记", "聊天", "我的"};
    // 定义一个布局
    private LayoutInflater layoutInflater;
    // 定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.select_index,
            R.drawable.select_friends_together, R.drawable.select_friends_remember, R.drawable.select_chat, R.drawable.select_my};

    // 定义数组来存放Fragment界面
    private Class[] fragmentArray = {HomeFragment.class, FriendsTogetherFragment.class, FriendsRememberFragment.class, ChatFragment.class, MyFragment.class};

    //安卓6.0动态获取权限
    String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO
    , Manifest.permission.CAMERA};

    List<String> mPermissionList = new ArrayList<>();
    boolean mShowRequestPermission = true;//用户是否禁止权限

    private List<Fragment> fragmentList = new ArrayList<>();
    private MenuOnClickListener listener = new MenuOnClickListener();

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @BindView(R.id.menu_index)
    ImageButton ibIndex;
    @BindView(R.id.menu_friend_together)
    ImageButton ibFriendTogether;
    @BindView(R.id.menu_friend_remember)
    ImageButton ibFriendRemember;
    @BindView(R.id.menu_chat)
    ImageButton ibChat;
    @BindView(R.id.menu_wd)
    ImageButton ibMy;
    @BindView(R.id.menu1)
    RelativeLayout rl1;
    @BindView(R.id.menu2)
    RelativeLayout rl2;
    @BindView(R.id.menu3)
    RelativeLayout rl3;
    @BindView(R.id.menu4)
    RelativeLayout rl4;
    @BindView(R.id.menu5)
    RelativeLayout rl5;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_friend_together)
    TextView tvFriendTogether;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.iv_point_new_chat_message)
    ImageView ivNewChatMessage;

    private MyGoPersonMainBroadcastReceiver myGoPersonMainBroadcastReceiver;
    private MyShenQingJinQunBroadcastReceiver myShenQingJinQunBroadcastReceiver;
    private long exitTime = 0;

    private SpImp spImp;
    private String uid = "";
    private String account = "";

    private ChatFragment fragmentChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(MainActivity.this);
        MyApplication.getInstance().addActivity(this);
        spImp = new SpImp(context);
//        account = spImp.getYXID();
//        NimUIKit.loginSuccess(account);
        getPermissions();
//        initView();
        init();
        initSessionListener();
        registReceiver();
        AppUpdateUtil.checkUpdate(context,true);
    }

    private void registReceiver() {
        myGoPersonMainBroadcastReceiver = new MyGoPersonMainBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yiwo.friendscometogether.broadcastreceiver.MyGoPersonMainBroadcastReceiver");
        registerReceiver(myGoPersonMainBroadcastReceiver, intentFilter);

        myShenQingJinQunBroadcastReceiver = new MyShenQingJinQunBroadcastReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.yiwo.friendscometogether.broadcastreceiver.MyShenQingJinQunBroadcastReceiver");
        registerReceiver(myShenQingJinQunBroadcastReceiver, intentFilter1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkHasNewChatmessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myGoPersonMainBroadcastReceiver);
        unregisterReceiver(myShenQingJinQunBroadcastReceiver);
        Log.d("destory++destory","Maindestory");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("destory++destory","Mainstop");
    }

    private void checkHasNewChatmessage() {
        if (!isNetworkConnected(context)){
            return;
        }
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")){
            if (NIMClient.getService(MsgService.class).getTotalUnreadCount()>0){//获取未读消息数
                ivNewChatMessage.setVisibility(View.VISIBLE);
            }else {
                ivNewChatMessage.setVisibility(View.GONE);
            }
        }else {
            ivNewChatMessage.setVisibility(View.GONE);
        }
        newMessageLis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    /**
     * 初始化各个组件
     */
    private void init() {

        ibIndex.setOnClickListener(listener);
        ibFriendTogether.setOnClickListener(listener);
        ibFriendRemember.setOnClickListener(listener);
        ibChat.setOnClickListener(listener);
        ibMy.setOnClickListener(listener);

        rl1.setOnClickListener(listener);
        rl2.setOnClickListener(listener);
        rl3.setOnClickListener(listener);
        rl4.setOnClickListener(listener);
        rl5.setOnClickListener(listener);
        Fragment fragmentHome = new HomeFragment1();
//        Fragment fragmentFriendTogether = new FriendsTogetherFragment1();
        Fragment fragmentFriendTogether = new FriendsTogetherFragment2();
        Fragment fragmentFriendRemember = new YouJiFragment();
        fragmentChat = new ChatFragment();
        Fragment fragmentMy = new MyFragment1();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentFriendTogether);
        fragmentList.add(fragmentFriendRemember);
        fragmentList.add(fragmentChat);
        fragmentList.add(fragmentMy);

        fragmentTransaction.add(R.id.fl_container, fragmentHome);
        fragmentTransaction.add(R.id.fl_container, fragmentFriendTogether);
        fragmentTransaction.add(R.id.fl_container, fragmentFriendRemember);
        fragmentTransaction.add(R.id.fl_container, fragmentChat);
        fragmentTransaction.add(R.id.fl_container, fragmentMy);

        fragmentTransaction.show(fragmentHome).hide(fragmentFriendTogether).hide(fragmentFriendRemember).hide(fragmentChat).hide(fragmentMy);
        fragmentTransaction.commit();

        selectButton(ibIndex);
        selectText(tvIndex);

    }

    private class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.menu_index:
                    selectButton(ibIndex);
                    selectText(tvIndex);
                    switchFragment(0);
                    break;
                case R.id.menu_friend_together:
                    selectButton(ibFriendTogether);
                    selectText(tvFriendTogether);
                    switchFragment(1);
                    break;
                case R.id.menu_friend_remember:
//                    selectButton(ibFriendRemember);
//                    selectText(tvFriendRemember);
//                    switchFragment(2);
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        intent.setClass(context, CreateFriendRememberActivity.class);
                        startActivity(intent);
                    } else {
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.menu_chat:
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        selectButton(ibChat);
                    selectText(tvChat);
                        switchFragment(3);
                    } else {
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.menu_wd:
                    selectButton(ibMy);
                    selectText(tvMy);
                    switchFragment(4);
                    break;
                case R.id.menu1:
                    selectButton(ibIndex);
                    selectText(tvIndex);
                    switchFragment(0);
                    break;
                case R.id.menu2:
                    selectButton(ibFriendTogether);
                    selectText(tvFriendTogether);
                    switchFragment(1);
                    break;
                case R.id.menu3:
//                    selectButton(ibFriendRemember);
//                    selectText(tvFriendRemember);
//                    switchFragment(2);
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        intent.setClass(context, CreateFriendRememberActivity.class);
                        startActivity(intent);
                    } else {
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.menu4:
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        selectButton(ibChat);
                    selectText(tvChat);
                        switchFragment(3);
                    } else {
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.menu5:
                    selectButton(ibMy);
                    selectText(tvMy);
                    switchFragment(4);
                    break;
            }

        }
    }

    /**
     * 选择隐藏与显示的Fragment
     *
     * @param index 显示的Frgament的角标
     */
    public void switchFragment(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                fragmentTransaction.show(fragmentList.get(index));
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    public void startYouji(){
        ibIndex.setSelected(false);
        tvIndex.setSelected(false);
    }
    //退出友记 恢复到首页fragment
    public void exitYouji(){
        switchFragment(0);
        selectButton(ibIndex);
        selectText(tvIndex);
    }
    public void selectText(View v) {
        tvIndex.setSelected(false);
        tvFriendTogether.setSelected(false);
//        tvFriendRemember.setSelected(false);
        tvChat.setSelected(false);
        tvMy.setSelected(false);
        v.setSelected(true);
    }

    /**
     * 控制底部菜单按钮的选中
     *
     * @param v
     */
    public void selectButton(View v) {
        ibIndex.setSelected(false);
        ibFriendTogether.setSelected(false);
        ibFriendRemember.setSelected(false);
        ibChat.setSelected(false);
        ibMy.setSelected(false);
        v.setSelected(true);
    }

//    private void initView() {
//        layoutInflater = LayoutInflater.from(this);
//        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//        tabHost.getTabWidget().setDividerDrawable(null);
//
//        // 得到fragment的个数
//        int count = fragmentArray.length;
//
//        for (int i = 0; i < count; i++) {
//            // 为每一个Tab按钮设置图标、文字和内容
//            TabHost.TabSpec tabSpec = tabHost.newTabSpec(mTextviewArray[i])
//                    .setIndicator(getTabItemView(i));
//            // 将Tab按钮添加进Tab选项卡中
//            tabHost.addTab(tabSpec, fragmentArray[i], null);
//        }
//    }

    /**
     * 给Tab按钮设置图标和文字
     */
//    private View getTabItemView(int index) {
//        View view = layoutInflater.inflate(R.layout.item_tableview, null);
//        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
//        imageView.setImageResource(mImageViewArray[index]);
//
//        TextView textView = (TextView) view.findViewById(R.id.textview);
//        textView.setText(mTextviewArray[index]);
//
//        return view;
//    }

    public void setmTabHost(int i) {
        tabHost.setCurrentTab(i);
    }

    public void getPermissions() {
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            init();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO 自动生成的方法存根
        backtrack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 退出销毁所有activity
     */
    private void backtrack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
            exitTime = 0;
        }
    }
    private void newMessageLis(){//新聊天消息监听
        Observer<List<IMMessage>> incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        ivNewChatMessage.setVisibility(View.VISIBLE);
//                        if (NIMClient.getService(MsgService.class).getTotalUnreadCount()>0){//获取未读消息数
//                            ivNewChatMessage.setVisibility(View.VISIBLE);
//                        }else {
//                            ivNewChatMessage.setVisibility(View.GONE);
//                        }
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }
    private void initSessionListener() {
        NimUIKit.setSessionListener(new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {//设置头像点击监听
                Intent intent = new Intent();
                intent.putExtra("person_id",message.getFromAccount());
                intent.putExtra("status","1");
                intent.setClass(context, PersonMainActivity.class);
                context.startActivity(intent);

            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {

            }

            @Override
            public void onAckMsgClicked(Context context, IMMessage message) {

            }
        });
    }
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
