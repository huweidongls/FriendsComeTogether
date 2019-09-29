package com.yiwo.friendscometogether.newpage;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.MyNoDialog;
import com.yiwo.friendscometogether.dbmodel.MyGameCardDbModel;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.DuiZhangFenZuDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.MyGameCardDbModelDao;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonGroupAdapter;
import com.yiwo.friendscometogether.newadapter.YouXiFenZuPersonsAdapter;
import com.yiwo.friendscometogether.newmodel.GameCardModel;
import com.yiwo.friendscometogether.newmodel.YouXiFenZuPersonModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGameCardActivity extends BaseActivity {

    @BindView(R.id.tv_huodong_name)
    TextView tvHuoDongName;
    @BindView(R.id.tv_duiwu)
    TextView tvDuiWu;
    @BindView(R.id.tv_duizhang)
    TextView tvDuiZhang;
    @BindView(R.id.tv_my_no)
    TextView tvMyNo;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private MyGameCardDbModelDao myGameCardDbModelDao;

    private List<MyGameCardDbModel> list_grouppers = new ArrayList<>();
    private SpImp spImp;
    private YouXiFenZuPersonsAdapter adapter;
    private List<YouXiFenZuPersonModel.ObjBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game_card);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(MyGameCardActivity.this);
        spImp = new SpImp(MyGameCardActivity.this);
        setDatabase();
        myGameCardDbModelDao = mDaoSession.getMyGameCardDbModelDao();
        initData();
    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private void initData() {
        GridLayoutManager managerWeiXuan = new GridLayoutManager(MyGameCardActivity.this,5);
        recyclerView.setLayoutManager(managerWeiXuan);
        adapter = new YouXiFenZuPersonsAdapter(list);
        adapter.setListener(new YouXiFenZuPersonsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int postion) {

            }
        });
        recyclerView.setAdapter(adapter);

        list_grouppers.addAll(myGameCardDbModelDao.queryBuilder().where(MyGameCardDbModelDao.Properties.UserId.eq(spImp.getUID())).build().list());
        if (list_grouppers.size()>0){
            tvDuiWu.setText("队伍：第"+list_grouppers.get(0).getGroup_No()+"组");
            tvDuiZhang.setText("队长："+list_grouppers.get(0).getCaptain_username());
            tvHuoDongName.setText("活动："+list_grouppers.get(0).getTitle());
            tvMyNo.setText(list_grouppers.get(0).getMyNo());
            list.clear();
            for (MyGameCardDbModel model:list_grouppers){
                YouXiFenZuPersonModel.ObjBean bean = new YouXiFenZuPersonModel.ObjBean();
                bean.setUser_ID(model.getGroup_user_ID());
                bean.setUserID(model.getGroup_user_ID());
                bean.setUsername(model.getGroup_username());
                bean.setUserpic(model.getGroup_userpic());
                bean.setGameNum(model.getGroup_game_num());
                list.add(bean);
            }
            adapter.notifyDataSetChanged();
        }
        ViseHttp.POST(NetConfig.lookGroup)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.lookGroup))
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("datadata0",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GameCardModel model = gson.fromJson(data,GameCardModel.class);
                                tvDuiWu.setText("队伍：第"+model.getObj().getGroup_No()+"组");
                                GameCardModel.ObjBean.CaptainBean captain = model.getObj().getCaptain();
                                if (captain !=null){
                                    tvDuiZhang.setText("队长："+captain.getUsername());
                                }
                                tvHuoDongName.setText("活动："+model.getObj().getTitle());
                                tvMyNo.setText(model.getObj().getMyNo());
                                list.clear();
                                List<MyGameCardDbModel> list_DeleteDb = myGameCardDbModelDao.queryBuilder().where(MyGameCardDbModelDao.Properties.UserId.eq(spImp.getUID())).list();
                                for (MyGameCardDbModel modelDbDelete:list_DeleteDb){
                                    myGameCardDbModelDao.delete(modelDbDelete);
                                }
                                for (GameCardModel.ObjBean.GroupInfoBean bean:model.getObj().getGroupInfo()){
                                    YouXiFenZuPersonModel.ObjBean model1 = new YouXiFenZuPersonModel.ObjBean();
                                    model1.setGameNum(bean.getGame_num());
                                    model1.setUserpic(bean.getUserpic());
                                    model1.setUsername(bean.getUsername());
                                    model1.setUserID(bean.getUser_ID());
                                    model1.setUser_ID(bean.getUser_ID());
                                    list.add(model1);
                                    MyGameCardDbModel model2 = new MyGameCardDbModel();
                                    model2.setTitle(model.getObj().getTitle());
                                    model2.setUserId(spImp.getUID());
                                    model2.setGroup_No(model.getObj().getGroup_No());
                                    model2.setMyNo(model.getObj().getMyNo());
                                    model2.setCaptain_group_No(model.getObj().getCaptain().getGroup_No());
                                    model2.setCaptain_user_ID(model.getObj().getCaptain().getUser_ID());
                                    model2.setCaptain_userpic(model.getObj().getCaptain().getUserpic());
                                    model2.setCaptain_username(model.getObj().getCaptain().getUsername());
                                    model2.setGroup_game_num(bean.getGame_num());
                                    model2.setGroup_user_ID(bean.getUser_ID());
                                    model2.setGroup_userpic(bean.getUserpic());
                                    model2.setGroup_username(bean.getUsername());
                                    myGameCardDbModelDao.insert(model2);

                                }
                                adapter.notifyDataSetChanged();

                            }else if (jsonObject.getInt("code") == 400){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyGameCardActivity.this);
                                builder.setCancelable(false);
                                builder.setMessage("您没有进行中的活动，或者活动人员未分组。")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    @OnClick({R.id.rl_back,R.id.rl_my_no})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_my_no:
                MyNoDialog dialog = new MyNoDialog(MyGameCardActivity.this,tvMyNo.getText().toString());
                dialog.show();
                break;
        }
    }
}
