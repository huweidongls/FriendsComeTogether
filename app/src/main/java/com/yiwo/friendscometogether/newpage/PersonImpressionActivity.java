package com.yiwo.friendscometogether.newpage;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonImpressonRvAdapter;
import com.yiwo.friendscometogether.newmodel.PersonImpressonModel;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonImpressionActivity extends BaseActivity {


    @BindView(R.id.rl_impresson)
    NestedRecyclerView rl_impresson;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.lay0)
    LinearLayout lay0;
    @BindView(R.id.lay1)
    LinearLayout lay1;
    @BindView(R.id.lay2)
    RelativeLayout lay2;
    @BindView(R.id.lay3)
    RelativeLayout lay3;
    @BindView(R.id.lay4)
    LinearLayout lay4;
    @BindView(R.id.lay5)
    LinearLayout lay5;
    @BindView(R.id.lay6)
    LinearLayout lay6;
    @BindView(R.id.lay7)
    LinearLayout lay7;
    @BindView(R.id.lay8)
    LinearLayout lay8;
    @BindView(R.id.lay9)
    LinearLayout lay9;

    private String person_id;
    private List<PersonImpressonModel.ObjBean> list = new ArrayList<>();
    private PersonImpressonRvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_impression);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonImpressionActivity.this);
        person_id = getIntent().getStringExtra("person_id");
        initRv();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.commentLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.commentLabel))
                .addParam("userID",person_id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonImpressonModel model = gson.fromJson(data,PersonImpressonModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
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

    private void initRv() {
        FlowLayoutManager manager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rl_impresson.setLayoutManager(manager);
        adapter = new PersonImpressonRvAdapter(list);
        rl_impresson.setAdapter(adapter);

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
