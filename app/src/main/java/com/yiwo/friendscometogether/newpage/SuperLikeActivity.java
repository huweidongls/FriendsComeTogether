package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.SuperLikeModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.SuperLikeAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJuAdapter;
import com.yiwo.friendscometogether.newadapter.YouJiAdapter;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperLikeActivity extends BaseActivity {

    private SuperLikeAdapter superLikeAdapter;
    private SuperLikeModel model;
    private List<SuperLikeModel.ObjBean> list_data = new ArrayList<>();
    private Context context  = SuperLikeActivity.this;
    private SpImp spImp;
    @BindView(R.id.rv_super_like)
    RecyclerView recyclerView;
    @BindView(R.id.tv_matching_text)
    TextView tv_matching_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(SuperLikeActivity.this);
        spImp = new SpImp(context);
        initData();
    }

    private void initData() {
        tv_matching_text.setText("正在为您搜索匹配另一半…");
        ViseHttp.POST(NetConfig.matching_user)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.matching_user))
                .addForm("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, SuperLikeModel.class);
                                // 2.Grid布局
                                RecyclerView.LayoutManager layoutManager =
                                        new GridLayoutManager(context,
                                                2, // 每行显示item项数目
                                                GridLayoutManager.HORIZONTAL, //水平排列
                                                false
                                        ){
                                            @Override
                                            public boolean canScrollVertically() {
                                                return  false;
                                            }
                                        };
                                list_data.addAll(model.getObj());
                                tv_matching_text.setText("成功为您匹配 "+list_data.size()+" 名瞳伴！ 还等什么赶快去打招呼吧");
                                recyclerView.setLayoutManager(layoutManager);
                                superLikeAdapter = new SuperLikeAdapter(list_data);
                                superLikeAdapter.setSayHelloListener(sayHelloListener);
                                recyclerView.setAdapter(superLikeAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.d("matching_user:err:::",errCode+"/////"+errMsg);
                    }
                });
    }
    @OnClick({R.id.rl_back,R.id.rl_Sx})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_Sx:
                intent.setClass(context,SuperLikeSxActivity.class);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
    SuperLikeAdapter.SayHelloListener sayHelloListener = new SuperLikeAdapter.SayHelloListener() {

        @Override
        public void sayHelloListen(int postion) {
            ViseHttp.POST(NetConfig.sayHello)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.sayHello))
                    .addForm("uid",spImp.getUID())
                    .addForm("bid",list_data.get(postion).getUserID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.d("22222",data);
                            toToast(context,"打招呼成功！");
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }
    };
}
