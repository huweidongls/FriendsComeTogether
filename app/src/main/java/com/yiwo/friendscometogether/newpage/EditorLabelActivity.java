package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.EditorLabelSaveAdapter;
import com.yiwo.friendscometogether.newmodel.UserSaveLabelModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorLabelActivity extends BaseActivity {

    private Context context = EditorLabelActivity.this;

    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.rv4)
    RecyclerView rv4;
    @BindView(R.id.rv5)
    RecyclerView rv5;
    @BindView(R.id.rv6)
    RecyclerView rv6;
    @BindView(R.id.rv7)
    RecyclerView rv7;
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

    private SpImp spImp;
    private String uid = "";

    private EditorLabelSaveAdapter adapter1;
    private EditorLabelSaveAdapter adapter2;
    private EditorLabelSaveAdapter adapter3;
    private EditorLabelSaveAdapter adapter4;
    private EditorLabelSaveAdapter adapter5;
    private EditorLabelSaveAdapter adapter6;
    private EditorLabelSaveAdapter adapter7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_label);

        StatusBarUtils.setStatusBar(EditorLabelActivity.this, Color.parseColor("#D84C37"));
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(EditorLabelActivity.this);
        spImp = new SpImp(context);

        initData();

    }

    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.usersavelabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.usersavelabel))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserSaveLabelModel model = gson.fromJson(data, UserSaveLabelModel.class);
                                String[] s1 = model.getObj().getPersonality().split(",");
                                if(s1.length > 0){
                                    tv1.setVisibility(View.GONE);
                                }else {
                                    tv1.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager1 = new FlowLayoutManager();
                                rv1.setLayoutManager(manager1);
                                adapter1 = new EditorLabelSaveAdapter(s1, 1);
                                rv1.setAdapter(adapter1);
                                String[] s2 = model.getObj().getMotion().split(",");
                                if(s2.length > 0){
                                    tv2.setVisibility(View.GONE);
                                }else {
                                    tv2.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager2 = new FlowLayoutManager();
                                rv2.setLayoutManager(manager2);
                                adapter2 = new EditorLabelSaveAdapter(s2, 2);
                                rv2.setAdapter(adapter2);
                                String[] s3 = model.getObj().getMusic().split(",");
                                if(s3.length > 0){
                                    tv3.setVisibility(View.GONE);
                                }else {
                                    tv3.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager3 = new FlowLayoutManager();
                                rv3.setLayoutManager(manager3);
                                adapter3 = new EditorLabelSaveAdapter(s3, 3);
                                rv3.setAdapter(adapter3);
                                String[] s4 = model.getObj().getDelicious().split(",");
                                if(s4.length > 0){
                                    tv4.setVisibility(View.GONE);
                                }else {
                                    tv4.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager4 = new FlowLayoutManager();
                                rv4.setLayoutManager(manager4);
                                adapter4 = new EditorLabelSaveAdapter(s4, 4);
                                rv4.setAdapter(adapter4);
                                String[] s5 = model.getObj().getFilm().split(",");
                                if(s5.length > 0){
                                    tv5.setVisibility(View.GONE);
                                }else {
                                    tv5.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager5 = new FlowLayoutManager();
                                rv5.setLayoutManager(manager5);
                                adapter5 = new EditorLabelSaveAdapter(s5, 5);
                                rv5.setAdapter(adapter5);
                                String[] s6 = model.getObj().getBook().split(",");
                                if(s6.length > 0){
                                    tv6.setVisibility(View.GONE);
                                }else {
                                    tv6.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager6 = new FlowLayoutManager();
                                rv6.setLayoutManager(manager6);
                                adapter6 = new EditorLabelSaveAdapter(s6, 6);
                                rv6.setAdapter(adapter6);
                                String[] s7 = model.getObj().getTravel().split(",");
                                if(s7.length > 0){
                                    tv7.setVisibility(View.GONE);
                                }else {
                                    tv7.setVisibility(View.VISIBLE);
                                }
                                FlowLayoutManager manager7 = new FlowLayoutManager();
                                rv7.setLayoutManager(manager7);
                                adapter7 = new EditorLabelSaveAdapter(s7, 7);
                                rv7.setAdapter(adapter7);
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

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.rl1})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:

                break;
            case R.id.rl1:
                intent.setClass(this, EditorLabelListActivity.class);
                startActivity(intent);
                break;
        }
    }

}
