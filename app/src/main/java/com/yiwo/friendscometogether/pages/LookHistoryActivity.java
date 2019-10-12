package com.yiwo.friendscometogether.pages;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yiwo.friendscometogether.adapter.MyCollectionAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.dbmodel.LookHistoryDbModel;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.LookHistoryDbModelDao;
import com.yiwo.friendscometogether.model.LookHistoryModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LookHistoryActivity extends BaseActivity {

    @BindView(R.id.activity_look_history_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_look_history_rv)
    RecyclerView recyclerView;


    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private LookHistoryDbModelDao lookHistoryDbModelDao;

    private LookHistoryAdapter adapter;
    private List<LookHistoryDbModel> mList = new ArrayList<>();

    private SpImp spImp;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_history);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(LookHistoryActivity.this);
        setDatabase();
        lookHistoryDbModelDao = mDaoSession.getLookHistoryDbModelDao();
        initData();

    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private void initData() {

        uid = spImp.getUID();

        LinearLayoutManager manager = new LinearLayoutManager(LookHistoryActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mList.addAll(lookHistoryDbModelDao.queryBuilder().where(LookHistoryDbModelDao.Properties.User_id.eq(spImp.getUID())).orderDesc(LookHistoryDbModelDao.Properties.Look_time).list());
        adapter = new LookHistoryAdapter(mList);
        adapter.setDeleteListenner(new LookHistoryAdapter.OnDeleteListenner() {
            @Override
            public void deleteListen(int position) {
                lookHistoryDbModelDao.delete(mList.get(position));
                mList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
//        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
//        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        recyclerView.setAdapter(adapter);
//
//        adapter = new LookHistoryAdapter(mList);
//        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
//        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
//        recyclerView.setAdapter(adapter);

//        ViseHttp.POST(NetConfig.lookHistoryUrl)
//                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.lookHistoryUrl))
//                .addParam("uid", uid)
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(data);
//                            if(jsonObject.getInt("code") == 200){
//                                Gson gson = new Gson();
//                                LookHistoryModel model = gson.fromJson(data, LookHistoryModel.class);
//                                mList = model.getObj();
//                                adapter = new LookHistoryAdapter(mList);
//                                recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
//                                recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
//                                recyclerView.setAdapter(adapter);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            lookHistoryDbModelDao.delete(mList.get(adapterPosition));
            mList.remove(adapterPosition);
            adapter.notifyDataSetChanged();
//                Toast.makeText(LookHistoryActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();

//                ViseHttp.POST(NetConfig.deleteLookHistoryUrl)
//                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteLookHistoryUrl))
//                        .addParam("id", mList.get(adapterPosition).getId())
//                        .request(new ACallback<String>() {
//                            @Override
//                            public void onSuccess(String data) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(data);
//                                    if(jsonObject.getInt("code") == 200){
//                                        toToast(LookHistoryActivity.this, "删除成功");
//                                        mList.remove(adapterPosition);
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onFail(int errCode, String errMsg) {
//
//                            }
//                        });

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(LookHistoryActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(LookHistoryActivity.this)
                    .setBackgroundColor(Color.RED)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    @OnClick({R.id.activity_look_history_rl_back,R.id.activity_look_history_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_look_history_rl_back:
                onBackPressed();
                break;
            case R.id.activity_look_history_clear:
                clearHistory();
                break;
        }
    }

    private void clearHistory() {
        for (LookHistoryDbModel lookHistoryDbModel:mList){
            lookHistoryDbModelDao.delete(lookHistoryDbModel);
        }
        mList.clear();
        adapter.notifyDataSetChanged();
//        ViseHttp.POST(NetConfig.clearLookHistory)
//                .addParam("app_key",getToken(NetConfig.BaseUrl+NetConfig.clearLookHistory))
//                .addParam("uid",uid)
//                .request(new ACallback<String>() {
//
//                    @Override
//                    public void onSuccess(String data) {
//                        Log.d("2222222",data);
//                        try {
//                            JSONObject jsonObject = new JSONObject(data);
//                            if (jsonObject.getInt("code") == 200){
//                                toToast(LookHistoryActivity.this,"已清空");
//                                mList.clear();
//                                adapter.notifyDataSetChanged();
//                            }else {
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LookHistoryActivity.this.finish();
    }
}
