package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.RecyclerViewDialog;
import com.yiwo.friendscometogether.dbmodel.DuiZhangFenZuDbModel;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.DuiZhangFenZuDbModelDao;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouXiFenZuAdapter;
import com.yiwo.friendscometogether.newadapter.YouXiFenZuPersonsAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.newmodel.YouXiFenZuPersonModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YouXiFenZuActivity extends BaseActivity {

    @BindView(R.id.rl_right_btn)
    RelativeLayout rlRightBtn;
    @BindView(R.id.rv_all_person)
    RecyclerView rvAllPerson;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;

    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DuiZhangFenZuDbModelDao duiZhangFenZuDbModelDao;


    private SpImp spImp;

    private DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDongModel;
    private List<YouXiFenZuPersonModel.ObjBean> listWeiXuanRenYuan = new ArrayList<>();//未被选择的人员
    private YouXiFenZuPersonsAdapter adapterWeiXuan;//未被选择的人员

    private List<List<YouXiFenZuPersonModel.ObjBean>> listsFenZu = new ArrayList<>();//分组 list
    private YouXiFenZuAdapter adapterFenZu;// 分组


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_xi_fen_zu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(YouXiFenZuActivity.this);
        if (getIntent().getSerializableExtra("yiXuanHuoDongModel")!=null){
            yiXuanHuoDongModel = (DuiZhangXuanZeHuoDongModel.ObjBean) getIntent().getSerializableExtra("yiXuanHuoDongModel");
        }
        setDatabase();
        duiZhangFenZuDbModelDao = mDaoSession.getDuiZhangFenZuDbModelDao();
        initData();
    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private void initData() {

        int i= 0 ;
        while (true){
            List<DuiZhangFenZuDbModel> listDb = duiZhangFenZuDbModelDao.queryBuilder().where(DuiZhangFenZuDbModelDao.Properties.PfID.eq(yiXuanHuoDongModel.getPfID()),
                    DuiZhangFenZuDbModelDao.Properties.Phase_id.eq(yiXuanHuoDongModel.getPhase_id()),DuiZhangFenZuDbModelDao.Properties.Group_No.eq(i+""))
                    .build().list();
            if (i==0){
                for (DuiZhangFenZuDbModel model:listDb){
                    listWeiXuanRenYuan.add(dBMode2YouXiMode(model));
                }
            }else {
                if (listDb.size() == 0) break;
                List<YouXiFenZuPersonModel.ObjBean> listTemp = new ArrayList<>();
                for (DuiZhangFenZuDbModel model:listDb){
                    listTemp.add(dBMode2YouXiMode(model));
                }
                listsFenZu.add(listTemp);
            }
            i++;
        }
        GridLayoutManager managerWeiXuan = new GridLayoutManager(YouXiFenZuActivity.this,5);
        rvAllPerson.setLayoutManager(managerWeiXuan);
        adapterWeiXuan = new YouXiFenZuPersonsAdapter(listWeiXuanRenYuan);
        adapterWeiXuan.setListener(new YouXiFenZuPersonsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int postion) {

            }
        });
        rvAllPerson.setAdapter(adapterWeiXuan);

        LinearLayoutManager managerFenZu = new LinearLayoutManager(YouXiFenZuActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGroup.setLayoutManager(managerFenZu);
        adapterFenZu = new YouXiFenZuAdapter(listsFenZu);
        adapterFenZu.setListenner(new YouXiFenZuAdapter.OnAddListenner() {
            @Override
            public void onAddListen(final int postion) {
                showAddDialog(postion);
            }
            @Override
            public void deleteGroup(final int postion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(YouXiFenZuActivity.this);
                builder.setMessage("确定删除此分组？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (YouXiFenZuPersonModel.ObjBean bean:listsFenZu.get(postion)){
                                    listWeiXuanRenYuan.add(bean);
                                }
                                listsFenZu.remove(postion);
                                adapterWeiXuan.notifyDataSetChanged();
                                adapterFenZu.notifyDataSetChanged();
                            }

                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }

            @Override
            public void deleteItemListen(int postion,int itemPosition) {
                listWeiXuanRenYuan.add(listsFenZu.get(postion).get(itemPosition));
                listsFenZu.get(postion).remove(itemPosition);
                adapterWeiXuan.notifyDataSetChanged();
                adapterFenZu.notifyDataSetChanged();
            }
        });
        rvGroup.setAdapter(adapterFenZu);
        //解决数据加载不完的问题
        rvGroup.setNestedScrollingEnabled(false);
        rvGroup.setHasFixedSize(true);
    //解决数据加载完成后, 没有停留在顶部的问题
        rvGroup.setFocusable(false);

        ViseHttp.POST(NetConfig.getAllUser)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getAllUser))
                .addParam("pfID",yiXuanHuoDongModel.getPfID())
                .addParam("phase_id",yiXuanHuoDongModel.getPhase_id())
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Log.d("asasas",data);
                                Gson gson = new Gson();
                                YouXiFenZuPersonModel model = gson.fromJson(data,YouXiFenZuPersonModel.class);
                                List<YouXiFenZuPersonModel.ObjBean> listTemp = new ArrayList<>();
                                for (int i =0;i<model.getObj().size();i++){
                                    for (int j = 0 ;j<listWeiXuanRenYuan.size();j++){
                                        if (model.getObj().get(i).getUserID().equals(listWeiXuanRenYuan.get(j).getUserID())){
                                            listTemp.add(model.getObj().get(i));
                                            Log.d("asasas","REMOVE_WEIXUAN");
                                        }
                                    }
                                    for (int m = 0 ;m<listsFenZu.size();m++){
                                        Log.d("asasas_fenzu:",""+m);
                                        for (int n = 0;n<listsFenZu.get(m).size();n++){
                                            Log.d("asasas","TEMP_ID:"+model.getObj().get(i).getUserID()+":::listsFenZu_ID"+listsFenZu.get(m).get(n).getUserID());
                                            if (model.getObj().get(i).getUserID().equals(listsFenZu.get(m).get(n).getUserID())){
                                                listTemp.add(model.getObj().get(i));
                                                Log.d("asasas","REMOVE_FENZU");
                                            }
                                        }
                                    }
                                }
                                model.getObj().removeAll(listTemp);
                                listWeiXuanRenYuan.addAll(model.getObj());
                                adapterWeiXuan.notifyDataSetChanged();
                                //更新数据库数据
                                List<DuiZhangFenZuDbModel> listDb = duiZhangFenZuDbModelDao.queryBuilder()
                                        .where(DuiZhangFenZuDbModelDao.Properties.PfID.eq(yiXuanHuoDongModel.getPfID()),
                                                DuiZhangFenZuDbModelDao.Properties.Phase_id.eq(yiXuanHuoDongModel.getPhase_id()))
                                        .list();
                                for (DuiZhangFenZuDbModel model1:listDb){
                                    duiZhangFenZuDbModelDao.delete(model1);
                                }
                                for (YouXiFenZuPersonModel.ObjBean mode2:listWeiXuanRenYuan){
                                    duiZhangFenZuDbModelDao.insert(youXiModel2dBModel(mode2,"0"));
                                }
                                for (int i = 0;i<listsFenZu.size();i++){
                                    for (YouXiFenZuPersonModel.ObjBean mode3:listsFenZu.get(i)){
                                        duiZhangFenZuDbModelDao.insert(youXiModel2dBModel(mode3,""+(i+1)));
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
    }

    private void showAddDialog(final int postion) {
        final YouXiFenZuPersonsAdapter adapter11 = new YouXiFenZuPersonsAdapter(listWeiXuanRenYuan);
        adapter11.setListener(new YouXiFenZuPersonsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int postion1) {
                listsFenZu.get(postion).add(listWeiXuanRenYuan.get(postion1));
                listWeiXuanRenYuan.remove(postion1);
                adapterWeiXuan.notifyDataSetChanged();
                adapterFenZu.notifyDataSetChanged();
                adapter11.notifyDataSetChanged();
            }
        });
        RecyclerViewDialog dialog = new RecyclerViewDialog(YouXiFenZuActivity.this,
                "选择人员",
                "关闭",
                adapter11,
                new RecyclerViewDialog.Listenner() {
                    @Override
                    public void onBtnClickListen(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    @OnClick({R.id.rl_back,R.id.tv_add_group,R.id.rl_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_add_group:
                AlertDialog.Builder builder = new AlertDialog.Builder(YouXiFenZuActivity.this);
                builder.setMessage("确定添加分组？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addGroup();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.rl_right_btn:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(YouXiFenZuActivity.this);
                builder1.setMessage("确定提交分组信息？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (listsFenZu.size()==0){
                                    toToast(YouXiFenZuActivity.this,"请添加分组！");
                                    dialog.dismiss();
                                    return;
                                }
                                Gson gson = new Gson();
                                JSONArray jsonArray = new JSONArray();
                                try {
                                    for (int i = 0;i<listsFenZu.size();i++){
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("group_No",(i+1)+"");
                                        JSONArray jsonArray1 = new JSONArray(gson.toJson(listsFenZu.get(i)));
                                        jsonObject.put("group_info",jsonArray1);
                                        jsonArray.put(jsonObject);
                                    }
                                    Log.d("jsonsjon",jsonArray.toString());
                                    ViseHttp.POST(NetConfig.gameGroup)
                                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.gameGroup))
                                            .addParam("userID",spImp.getUID())
                                            .addParam("pfID",yiXuanHuoDongModel.getPfID())
                                            .addParam("phase_id",yiXuanHuoDongModel.getPhase_id())
                                            .addParam("userList",jsonArray.toString())
                                            .request(new ACallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {
                                                    Log.d("datadata",data);
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(data);
                                                        if (jsonObject.getInt("code") == 200){
                                                            toToast(YouXiFenZuActivity.this,"分组成功！");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onFail(int errCode, String errMsg) {

                                                }
                                            });
                                    List<DuiZhangFenZuDbModel> list = duiZhangFenZuDbModelDao.queryBuilder()
                                            .where(DuiZhangFenZuDbModelDao.Properties.PfID.eq(yiXuanHuoDongModel.getPfID()),
                                                    DuiZhangFenZuDbModelDao.Properties.Phase_id.eq(yiXuanHuoDongModel.getPhase_id()))
                                            .list();
                                    for (int i = 0 ; i < list.size();i++){
                                        duiZhangFenZuDbModelDao.delete(list.get(i));
                                    }
                                    for (int i =0;i<listWeiXuanRenYuan.size();i++){
                                        duiZhangFenZuDbModelDao.insert(youXiModel2dBModel(listWeiXuanRenYuan.get(i),"0"));
                                    }
                                    List<List<YouXiFenZuPersonModel.ObjBean>> listsEmpty = new ArrayList<>();
                                    for (int i = 0 ;i<listsFenZu.size();i++){
                                        if (listsFenZu.get(i).size() == 0){
                                            listsEmpty.add(listsFenZu.get(i));
                                        }
                                    }
                                    listsFenZu.removeAll(listsEmpty);
                                    for (int i = 0 ;i<listsFenZu.size();i++){
                                        for (int j =0 ;j<listsFenZu.get(i).size();j++){
                                            duiZhangFenZuDbModelDao.insert(youXiModel2dBModel(listsFenZu.get(i).get(j),(i+1)+""));
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                break;
        }
    }
    private YouXiFenZuPersonModel.ObjBean dBMode2YouXiMode(DuiZhangFenZuDbModel model){
        YouXiFenZuPersonModel.ObjBean bean = new YouXiFenZuPersonModel.ObjBean();
        bean.setUser_ID(model.getUser_ID());
        bean.setUserID(model.getUserID());
        bean.setUsername(model.getUsername());
        bean.setUserpic(model.getUserpic());
        bean.setGameNum(model.getGameNum());
        return bean;
    }
    private DuiZhangFenZuDbModel youXiModel2dBModel(YouXiFenZuPersonModel.ObjBean model,String group_no){
        DuiZhangFenZuDbModel bean = new DuiZhangFenZuDbModel();

        bean.setPfID(yiXuanHuoDongModel.getPfID());
        bean.setPhase_id(yiXuanHuoDongModel.getPhase_id());

        bean.setUser_ID(model.getUser_ID());
        bean.setUserID(model.getUserID());
        bean.setUsername(model.getUsername());
        bean.setUserpic(model.getUserpic());
        bean.setGameNum(model.getGameNum());
        bean.setGroup_No(group_no);
        return bean;
    }
    private void addGroup() {
        listsFenZu.add(new ArrayList<YouXiFenZuPersonModel.ObjBean>());
        adapterFenZu.notifyDataSetChanged();
    }
}
