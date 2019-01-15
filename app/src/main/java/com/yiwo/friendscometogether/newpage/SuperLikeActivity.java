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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.SuperLikeModel;
import com.yiwo.friendscometogether.model.SuperLikeSXMode;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.R2.id.sex;

public class SuperLikeActivity extends BaseActivity {

    private SuperLikeAdapter superLikeAdapter;
    private SuperLikeModel model;
    private SuperLikeSXMode sxMode;//筛选设置实体
    private SpCache spCache;//第三方存储
    private List<SuperLikeModel.ObjBean> list_data = new ArrayList<>();
    private Context context  = SuperLikeActivity.this;
    private final int SX_RESULT_CODE = 2;
    private final int SX_REQUEST_CODE = 1;

    private SpImp spImp;
    @BindView(R.id.rv_super_like)
    RecyclerView recyclerView;
    @BindView(R.id.tv_matching_text)
    TextView tv_matching_text;
    @BindView(R.id.iv_loading)
    ImageView iv_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(SuperLikeActivity.this);
        spImp = new SpImp(context);
        spCache = new SpCache(context);
        initData();
    }

    private void initData() {
        sxMode = (SuperLikeSXMode) spCache.get("SuperLikeSXMode");
        if (sxMode == null){
            sxMode = new SuperLikeSXMode();
            sxMode.setSex(1);
            sxMode.setAges("10-30");
            sxMode.setAddress(1);
        }
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(R.drawable.gif).apply(options).into(iv_loading);
        recyclerView.setVisibility(View.GONE);
        tv_matching_text.setText("正在为您搜索匹配另一半…");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ViseHttp.POST(NetConfig.matching_user)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.matching_user))
                        .addParam("uid",spImp.getUID())
                        .addParam("sex",sxMode.getSex()+"")
                        .addParam("address",sxMode.getAddress()+"")
                        .addParam("age",sxMode.getAges())
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
                                                        GridLayoutManager.VERTICAL,
                                                        false
                                                ){
                                                    @Override
                                                    public boolean canScrollVertically() {
                                                        return  false;
                                                    }
                                                };
                                        list_data.clear();
                                        list_data.addAll(model.getObj());
                                        if (list_data.size()>0){
                                            tv_matching_text.setText("成功为您匹配 "+list_data.size()+" 名瞳伴！\n 还等什么赶快去打招呼吧！");
                                        }else {
                                            tv_matching_text.setText("没有找到和您匹配的瞳伴\n快去完善资料吧！");
                                        }
                                        Glide.with(context).asBitmap().load(R.drawable.gif).into(iv_loading);
                                        recyclerView.setLayoutManager(layoutManager);
                                        superLikeAdapter = new SuperLikeAdapter(list_data);
                                        superLikeAdapter.setSayHelloListener(sayHelloListener);
                                        recyclerView.setAdapter(superLikeAdapter);
                                        recyclerView.setVisibility(View.VISIBLE);
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
        },2500);
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
                startActivityForResult(intent,SX_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case SX_RESULT_CODE:
//                intent.putExtra("age",ages);
//                intent.putExtra("sex",sex);
//                intent.putExtra("address",address);
                initData();
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
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    Log.d("22222",data);
                                    toToast(context,"打招呼成功！");
                                }else {
                                    toToast(context,"您已经打过招呼了");
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
    };
}
