package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.LookHistoryAdapter;
import com.yiwo.friendscometogether.adapter.MyDraftAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDraftActivity extends BaseActivity {

    @BindView(R.id.activity_my_draft_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_draft_rv)
    RecyclerView recyclerView;

    private MyDraftAdapter adapter;
    private List<UserRememberModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_draft);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(MyDraftActivity.this);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(mLayoutManager);
        ViseHttp.POST(NetConfig.userRemember)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userRemember))
                .addParam("uid", uid)
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserRememberModel userRememberModel = gson.fromJson(data, UserRememberModel.class);
                                mList = userRememberModel.getObj();
                                Log.d("listSsssss:::",mList.size()+"");
                                adapter = new MyDraftAdapter(userRememberModel.getObj());
                                adapter.setListenner(new MyDraftAdapter.OnBtnClickListenner() {
                                    @Override
                                    public void OnDeleteListen(int position) {
                                        delete(position);
                                    }

                                    @Override
                                    public void onEditListen(int position) {
                                        edit(position);
                                    }
                                });
//                                recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
//                                recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
                                recyclerView.setAdapter(adapter);
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

    private void edit(int position) {
        Intent intent = new Intent();
        intent.setClass(MyDraftActivity.this, EditorFriendRememberActivity.class);
        intent.putExtra("id", mList.get(position).getFmID());
        intent.putExtra("draft", "2");
        startActivity(intent);
        onBackPressed();
    }

    private void delete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyDraftActivity.this);
        builder.setMessage("确定删除此草稿？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViseHttp.POST(NetConfig.deleteFriendRememberUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteFriendRememberUrl))
                                .addParam("id", mList.get(position).getFmID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                toToast(MyDraftActivity.this, "删除成功");
                                                mList.remove(position);
                                                adapter.notifyDataSetChanged();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        toToast(MyDraftActivity.this, "code:"+errCode+" "+errMsg);
                                    }
                                });
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */

//    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
//        @Override
//        public void onItemClick(SwipeMenuBridge menuBridge) {
//            menuBridge.closeMenu();
//
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
//            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
//            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//
//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
////                Toast.makeText(MyDraftActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//                switch (menuPosition){
//                    case 1:
//                        //编辑友记
//                        Intent intent = new Intent();
//                        intent.setClass(MyDraftActivity.this, EditorFriendRememberActivity.class);
//                        intent.putExtra("id", mList.get(adapterPosition).getFmID());
//                        intent.putExtra("draft", "2");
//                        startActivity(intent);
//                        onBackPressed();
//                        break;
//                    case 0:
//                        ViseHttp.POST(NetConfig.releaseDraftUrl)
//                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.releaseDraftUrl))
//                                .addParam("id", mList.get(adapterPosition).getFmID())
//                                .request(new ACallback<String>() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        try {
//                                            JSONObject jsonObject = new JSONObject(data);
//                                            if(jsonObject.getInt("code") == 200){
//                                                toToast(MyDraftActivity.this, "发布成功");
//                                                mList.remove(adapterPosition);
//                                                adapter.notifyDataSetChanged();
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFail(int errCode, String errMsg) {
//
//                                    }
//                                });
//                        break;
//                    case 2:
//                        ViseHttp.POST(NetConfig.deleteFriendRememberUrl)
//                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteFriendRememberUrl))
//                                .addParam("id", mList.get(adapterPosition).getFmID())
//                                .request(new ACallback<String>() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        try {
//                                            JSONObject jsonObject = new JSONObject(data);
//                                            if(jsonObject.getInt("code") == 200){
//                                                toToast(MyDraftActivity.this, "删除成功");
//                                                mList.remove(adapterPosition);
//                                                adapter.notifyDataSetChanged();
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFail(int errCode, String errMsg) {
//
//                                    }
//                                });
//                        break;
//                }
//            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(MyDraftActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
//        @Override
//        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem editItem = new SwipeMenuItem(MyDraftActivity.this)
//                    .setBackgroundColor(Color.parseColor("#5959D3"))
//                    .setText("发布")
//                    .setTextColor(Color.WHITE)
//                    .setWidth(width)
//                    .setHeight(height);
//            rightMenu.addMenuItem(editItem);// 添加菜单到右侧。
//
//            SwipeMenuItem sendItem = new SwipeMenuItem(MyDraftActivity.this)
//                    .setBackgroundColor(Color.parseColor("#ff9d00"))
//                    .setText("编辑")
//                    .setTextColor(Color.WHITE)
//                    .setWidth(width)
//                    .setHeight(height);
//            rightMenu.addMenuItem(sendItem);// 添加菜单到右侧。
//
//            SwipeMenuItem deleteItem = new SwipeMenuItem(MyDraftActivity.this)
//                    .setBackgroundColor(Color.RED)
//                    .setText("删除")
//                    .setTextColor(Color.WHITE)
//                    .setWidth(width)
//                    .setHeight(height);
//            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
//        }
//    };

    @OnClick({R.id.activity_my_draft_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_my_draft_rl_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyDraftActivity.this.finish();
    }
}
