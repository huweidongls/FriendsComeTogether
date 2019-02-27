package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJiAdapter;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonRememberActivity extends BaseActivity {

    private Context context = PersonRememberActivity.this;
    private SpImp spImp;
    private PersonMainModel model;
    private List<PersonMainModel.ObjBean.FriendBean> list_youji = new ArrayList<>();
    private String person_id;
    @BindView(R.id.rv_remember)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_remember);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonRememberActivity.this);
        spImp = new SpImp(context);
        person_id = getIntent().getStringExtra("person_id");
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.personMain)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.personMain))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("type", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, PersonMainModel.class);
                                //----------友记---------------
                                LinearLayoutManager manager1 = new LinearLayoutManager(PersonRememberActivity.this);
                                list_youji.addAll(model.getObj().getFriend());
                                recyclerView.setLayoutManager(manager1);
                                recyclerView.setAdapter(new TaRenZhuYeYouJiAdapter(list_youji));
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

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
