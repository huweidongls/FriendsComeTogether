package com.yiwo.friendscometogether.newpage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.JuBaoModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.JuBaoAdapter;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JuBaoActivity extends BaseActivity {

    @BindView(R.id.btn_jubao)
    Button btnJuBao;
    @BindView(R.id.rv_jubao)
    RecyclerView rvJuBao;

    private String pfID;
    private String type;
    private String reportUserID = "";
    private SpImp spImp;
//    @"垃圾营销",@"涉黄信息",@"不实信息",@"人身攻击",@"有害信息",@"违法信息",@"诈骗信息"
    private String[] strs = new String[]{"垃圾营销","涉黄信息","不实信息","人身攻击","有害信息","违法信息","诈骗信息"};
    private List<JuBaoModel> list = new ArrayList<>();
    private JuBaoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_bao);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(JuBaoActivity.this);

        pfID = getIntent().getStringExtra("pfID");
        type = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("reportUserID")!=null){
            reportUserID = getIntent().getStringExtra("reportUserID");
        }
        LinearLayoutManager manager = new LinearLayoutManager(JuBaoActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvJuBao.setLayoutManager(manager);
        for (String string:strs){
            JuBaoModel model = new JuBaoModel();
            model.setText(string);
            model.setChoose(false);
            list.add(model);
        }
        adapter = new JuBaoAdapter(list);
        rvJuBao.setAdapter(adapter);
        adapter.setListener(new JuBaoAdapter.OnSelectListener() {
            @Override
            public void onSelete(int i, boolean ischecked) {
                for (JuBaoModel model:list){
                    model.setChoose(false);
                }
                list.get(i).setChoose(ischecked);
                adapter.notifyDataSetChanged();
            }
        });




    }
    @OnClick({R.id.btn_jubao,R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_jubao:
                String info = "";
                for (JuBaoModel model:list){
                    if (model.isChoose()){
                        info = model.getText();
                        break;
                    }
                }
                if (info.equals("")){
                    toToast(JuBaoActivity.this,"请选择举报类型");
                }else {
                    Log.d("adsasd::",info);
                    ViseHttp.POST(NetConfig.report)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.report))
                            .addParam("userID",spImp.getUID())
                            .addParam("reportUserID",reportUserID)
                            .addParam("reportID",pfID)
                            .addParam("type",type)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            toToast(JuBaoActivity.this,"举报成功！");
                                            finish();
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
                break;
        }
    }
}
