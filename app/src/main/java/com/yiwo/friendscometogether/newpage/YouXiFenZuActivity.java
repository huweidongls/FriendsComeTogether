package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.RecyclerViewDialog;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.YouXiFenZuAdapter;
import com.yiwo.friendscometogether.newadapter.YouXiFenZuPersonsAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.newmodel.YouXiFenZuPersonModel;

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

    private DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDongModel;
    private List<YouXiFenZuPersonModel.ObjBean> list = new ArrayList<>();//未被选择的人员
    private YouXiFenZuPersonsAdapter adapter;//未被选择的人员

    private List<List<YouXiFenZuPersonModel.ObjBean>> listsFenZu = new ArrayList<>();//分组 list
    private YouXiFenZuAdapter adapterFenZu;// 分组


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_xi_fen_zu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        if (getIntent().getSerializableExtra("yiXuanHuoDongModel")!=null){
            yiXuanHuoDongModel = (DuiZhangXuanZeHuoDongModel.ObjBean) getIntent().getSerializableExtra("yiXuanHuoDongModel");
        }
        initData();
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(YouXiFenZuActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGroup.setLayoutManager(manager);
        adapterFenZu = new YouXiFenZuAdapter(listsFenZu);
        adapterFenZu.setListenner(new YouXiFenZuAdapter.OnAddListenner() {
            @Override
            public void onAddListen(final int postion) {
                showAddDialog(postion);
            }

            @Override
            public void delteItemListen(int postion,int itemPosition) {
                list.add(listsFenZu.get(postion).get(itemPosition));
                listsFenZu.get(postion).remove(itemPosition);
                adapter.notifyDataSetChanged();
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
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                YouXiFenZuPersonModel model = gson.fromJson(data,YouXiFenZuPersonModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                list.addAll(model.getObj());
                                GridLayoutManager manager = new GridLayoutManager(YouXiFenZuActivity.this,5);
                                rvAllPerson.setLayoutManager(manager);
                                adapter = new YouXiFenZuPersonsAdapter(list);
                                adapter.setListener(new YouXiFenZuPersonsAdapter.OnItemClickListener() {
                                    @Override
                                    public void itemClick(int postion) {

                                    }
                                });
                                rvAllPerson.setAdapter(adapter);
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
        final YouXiFenZuPersonsAdapter adapter11 = new YouXiFenZuPersonsAdapter(list);
        adapter11.setListener(new YouXiFenZuPersonsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int postion1) {
                listsFenZu.get(postion).add(list.get(postion1));
                list.remove(postion1);
                adapter.notifyDataSetChanged();
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
                Gson gson = new Gson();

                JSONArray jsonArray = new JSONArray();
                try {
                    for (int i = 0;i<listsFenZu.size();i++){
                        String string = gson.toJson(listsFenZu.get(i));
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("group_No",(i+1)+"");
                        JSONArray jsonArray1 = new JSONArray(gson.toJson(listsFenZu.get(i)));
                        jsonObject.put("group_info",jsonArray1);
                        jsonArray.put(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonsjon",jsonArray.toString());
                break;
        }
    }

    private void addGroup() {
        listsFenZu.add(new ArrayList<YouXiFenZuPersonModel.ObjBean>());
        adapterFenZu.notifyDataSetChanged();
    }
}
