package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.MyFocusActiveAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.MyFocusActiveModel;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.GuanZhuWoDeAdapter;
import com.yiwo.friendscometogether.newadapter.MyHuoDongApplyAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeHuoDongAdapter;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.newmodel.WoGuanZhuDeModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanZhuActivity extends BaseActivity {

    private Context context = GuanZhuActivity.this;

    @BindView(R.id.rl_woguanhude)
    RelativeLayout rl_woguanzhude;
    @BindView(R.id.rl_guanzhuwode)
    RelativeLayout rl_guanzhuwode;
    @BindView(R.id.rl_guanzhuhuodong)
    RelativeLayout rl_guanzhuhuodong;

    @BindView(R.id.tv_woguanzhude)
    TextView tv_woguanzhude;
    @BindView(R.id.tv_guanzhuwode)
    TextView tv_guanzhuwode;
    @BindView(R.id.tv_guanzhuhuodong)
    TextView tv_guanzhuhuodong;


    @BindView(R.id.rv_woguanzhude)
    RecyclerView rv_woguanzhude;
    @BindView(R.id.rv_guanzhuwode)
    RecyclerView rv_guanzhuwode;
    @BindView(R.id.rv_guanzhuhuodong)
    RecyclerView rv_guanzhuhuodong;


    private SpImp spImp;
    private int page = 1;
    private List<UserFocusModel.ObjBean> mWoGuanZhuDeList ;
    private WoGuanZhuDeAdapter woGuanZhuDeAdapter;

    private List<GuanZhuWoDeModel.ObjBean> mGuanZhuWoDeList ;
    private GuanZhuWoDeAdapter guanZhuWoDeAdapter;

    private List<HuoDongListModel.ObjBean> mGuanZhuHuoDongList ;
    private WoGuanZhuDeHuoDongAdapter guanZhuDeHuoDongAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu);
        spImp = new SpImp(GuanZhuActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(GuanZhuActivity.this);

        initData();
    }

    private void initData() {


        //----------------我关注的---------------------
        ViseHttp.POST(NetConfig.userFocus)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocus))
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserFocusModel userFocusModel = gson.fromJson(data, UserFocusModel.class);

                                mWoGuanZhuDeList = userFocusModel.getObj();
                                woGuanZhuDeAdapter = new WoGuanZhuDeAdapter(mWoGuanZhuDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_woguanzhude.setLayoutManager(manager);
                                rv_woguanzhude.setAdapter(woGuanZhuDeAdapter);
                                page = 2;
                                Log.e("222", page+"");
                                woGuanZhuDeAdapter.setListener(new WoGuanZhuDeAdapter.OnCancelFocusListener() {
                                    @Override
                                    public void onCancel(final int i) {
                                        toDialog(context, "提示", "是否取消关注", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ViseHttp.POST(NetConfig.userCancelFocusUrl)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userCancelFocusUrl))
                                                        .addParam("listId", mWoGuanZhuDeList.get(i).getLID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if (jsonObject1.getInt("code") == 200) {
                                                                        toToast(context, "取消关注成功");
                                                                        mWoGuanZhuDeList.remove(i);
                                                                        woGuanZhuDeAdapter.notifyDataSetChanged();
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //-------------------关注我的---------------------
        ViseHttp.POST(NetConfig.guanZhuWoDe)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.guanZhuWoDe))
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                GuanZhuWoDeModel guanZhuWoDeModel = gson.fromJson(data, GuanZhuWoDeModel.class);
                                mGuanZhuWoDeList = guanZhuWoDeModel.getObj();
                                guanZhuWoDeAdapter = new GuanZhuWoDeAdapter(mGuanZhuWoDeList);
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_guanzhuwode.setLayoutManager(manager);
                                rv_guanzhuwode.setAdapter(guanZhuWoDeAdapter);

                                page = 2;
                                Log.e("222", page+"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //---------------关注活动-------------------
        ViseHttp.POST(NetConfig.MyFocusActiveUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.MyFocusActiveUrl))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HuoDongListModel.ObjBean bean = new HuoDongListModel.ObjBean();

                                mGuanZhuHuoDongList = new ArrayList<>();
                                mGuanZhuHuoDongList.add(bean);
                                mGuanZhuHuoDongList.add(bean);
                                mGuanZhuHuoDongList.add(bean);

                                guanZhuDeHuoDongAdapter = new WoGuanZhuDeHuoDongAdapter(mGuanZhuHuoDongList);
                                LinearLayoutManager manager = new LinearLayoutManager(GuanZhuActivity.this);
                                rv_guanzhuhuodong.setLayoutManager(manager);
                                rv_guanzhuhuodong.setAdapter(guanZhuDeHuoDongAdapter);
                                page = 2;
                                Log.e("222", page+"");
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

    @OnClick({R.id.rl_woguanhude,R.id.rl_guanzhuwode,R.id.rl_guanzhuhuodong})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_woguanhude:
                tv_woguanzhude.setTextColor(Color.parseColor("#FFAB26"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                rv_woguanzhude.setVisibility(View.VISIBLE);
                rv_guanzhuwode.setVisibility(View.GONE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                break;
            case R.id.rl_guanzhuwode:
                tv_guanzhuwode.setTextColor(Color.parseColor("#FFAB26"));
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#333333"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuwode.setVisibility(View.VISIBLE);
                rv_guanzhuhuodong.setVisibility(View.GONE);
                rv_woguanzhude.setVisibility(View.GONE);
                break;
            case R.id.rl_guanzhuhuodong:
                tv_guanzhuhuodong.setTextColor(Color.parseColor("#FFAB26"));
                tv_woguanzhude.setTextColor(Color.parseColor("#333333"));
                tv_guanzhuwode.setTextColor(Color.parseColor("#333333"));
                rv_guanzhuhuodong.setVisibility(View.VISIBLE);
                rv_woguanzhude.setVisibility(View.GONE);
                rv_guanzhuwode.setVisibility(View.GONE);
                break;
        }
    }
}
