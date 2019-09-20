package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.SuoShuHuoDongListAdapter;
import com.yiwo.friendscometogether.dbmodel.DuiZhangDeHuoDongDbModel;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.DuiZhangDeHuoDongDbModelDao;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.DuiZhangXuanZeHuoDongListAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class DuiZhangXuanZeHuoDongActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DuiZhangDeHuoDongDbModelDao duiZhangDeHuoDongDbModelDao;
    private List<DuiZhangDeHuoDongDbModel> listDB = new ArrayList<>();

    private DuiZhangXuanZeHuoDongListAdapter adapter;
    private List<DuiZhangXuanZeHuoDongModel.ObjBean> list = new ArrayList<>();
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_zhang_xuan_ze_huo_dong);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        spImp = new SpImp(DuiZhangXuanZeHuoDongActivity.this);
        setDatabase();
        duiZhangDeHuoDongDbModelDao = mDaoSession.getDuiZhangDeHuoDongDbModelDao();
        initData();
    }
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    private void initData() {
        //初始化 rv
        LinearLayoutManager manager = new LinearLayoutManager(DuiZhangXuanZeHuoDongActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new DuiZhangXuanZeHuoDongListAdapter(list);
        adapter.setListionner(new DuiZhangXuanZeHuoDongListAdapter.ItemClickListionner() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent();
                intent.putExtra("xuanzehuodong",list.get(postion));
                setResult(1,intent);
                finish();
            }
        });
        rv.setAdapter(adapter);

        //先从数据库提取数据
        listDB.addAll(duiZhangDeHuoDongDbModelDao.queryBuilder().where(DuiZhangDeHuoDongDbModelDao.Properties.DuiZhangID.eq(spImp.getUID())).list());
        for (DuiZhangDeHuoDongDbModel model:listDB){
            DuiZhangXuanZeHuoDongModel.ObjBean bean = new DuiZhangXuanZeHuoDongModel.ObjBean();
            bean.setPfID(model.getPfID());
            bean.setPfpic(model.getPfpic());
            bean.setPftitle(model.getPftitle());
            bean.setPhase_begin_time(model.getPhase_begin_time());
            bean.setPhase_id(model.getPhase_id());
            bean.setPhase_num(model.getPhase_num());
            list.add(bean);
        }
        if (listDB.size()>0) adapter.notifyDataSetChanged();

        ViseHttp.POST(NetConfig.willBeginActivity)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.willBeginActivity))
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                DuiZhangXuanZeHuoDongModel model = gson.fromJson(data,DuiZhangXuanZeHuoDongModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                changeDBData(list);
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

    private void changeDBData(List<DuiZhangXuanZeHuoDongModel.ObjBean> list) {
        for (DuiZhangDeHuoDongDbModel model : listDB){
            duiZhangDeHuoDongDbModelDao.delete(model);
        }
        if (list.size()>0){
            for (DuiZhangXuanZeHuoDongModel.ObjBean bean : list){
                DuiZhangDeHuoDongDbModel model = new DuiZhangDeHuoDongDbModel();
                model.setDuiZhangID(spImp.getUID());
                model.setPfID(bean.getPfID());
                model.setPfpic(bean.getPfpic());
                model.setPftitle(bean.getPftitle());
                model.setPhase_begin_time(bean.getPhase_begin_time());
                model.setPhase_id(bean.getPhase_id());
                model.setPhase_num(bean.getPhase_num());
                duiZhangDeHuoDongDbModelDao.insert(model);
            }
        }
    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
