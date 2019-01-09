package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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

    private String label1 = "empty";
    private String label2 = "empty";
    private String label3 = "empty";
    private String label4 = "empty";
    private String label5 = "empty";
    private String label6 = "empty";
    private String label7 = "empty";

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
                                Log.e("123123", data);
                                Gson gson = new Gson();
                                UserSaveLabelModel model = gson.fromJson(data, UserSaveLabelModel.class);
                                String[] s1 = model.getObj().getPersonality().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getPersonality())){
                                    tv1.setVisibility(View.GONE);
                                    rv1.setVisibility(View.VISIBLE);
                                }else {
                                    tv1.setVisibility(View.VISIBLE);
                                    rv1.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager1 = new FlowLayoutManager();
                                rv1.setLayoutManager(manager1);
                                adapter1 = new EditorLabelSaveAdapter(s1, 1);
                                rv1.setAdapter(adapter1);
                                String[] s2 = model.getObj().getMotion().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getMotion())){
                                    tv2.setVisibility(View.GONE);
                                    rv2.setVisibility(View.VISIBLE);
                                }else {
                                    tv2.setVisibility(View.VISIBLE);
                                    rv2.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager2 = new FlowLayoutManager();
                                rv2.setLayoutManager(manager2);
                                adapter2 = new EditorLabelSaveAdapter(s2, 2);
                                rv2.setAdapter(adapter2);
                                String[] s3 = model.getObj().getMusic().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getMusic())){
                                    tv3.setVisibility(View.GONE);
                                    rv3.setVisibility(View.VISIBLE);
                                }else {
                                    tv3.setVisibility(View.VISIBLE);
                                    rv3.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager3 = new FlowLayoutManager();
                                rv3.setLayoutManager(manager3);
                                adapter3 = new EditorLabelSaveAdapter(s3, 3);
                                rv3.setAdapter(adapter3);
                                String[] s4 = model.getObj().getDelicious().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getDelicious())){
                                    tv4.setVisibility(View.GONE);
                                    rv4.setVisibility(View.VISIBLE);
                                }else {
                                    tv4.setVisibility(View.VISIBLE);
                                    rv4.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager4 = new FlowLayoutManager();
                                rv4.setLayoutManager(manager4);
                                adapter4 = new EditorLabelSaveAdapter(s4, 4);
                                rv4.setAdapter(adapter4);
                                String[] s5 = model.getObj().getFilm().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getFilm())){
                                    tv5.setVisibility(View.GONE);
                                    rv5.setVisibility(View.VISIBLE);
                                }else {
                                    tv5.setVisibility(View.VISIBLE);
                                    rv5.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager5 = new FlowLayoutManager();
                                rv5.setLayoutManager(manager5);
                                adapter5 = new EditorLabelSaveAdapter(s5, 5);
                                rv5.setAdapter(adapter5);
                                String[] s6 = model.getObj().getBook().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getBook())){
                                    tv6.setVisibility(View.GONE);
                                    rv6.setVisibility(View.VISIBLE);
                                }else {
                                    tv6.setVisibility(View.VISIBLE);
                                    rv6.setVisibility(View.GONE);
                                }
                                FlowLayoutManager manager6 = new FlowLayoutManager();
                                rv6.setLayoutManager(manager6);
                                adapter6 = new EditorLabelSaveAdapter(s6, 6);
                                rv6.setAdapter(adapter6);
                                String[] s7 = model.getObj().getTravel().split(",");
                                if(!TextUtils.isEmpty(model.getObj().getTravel())){
                                    tv7.setVisibility(View.GONE);
                                    rv7.setVisibility(View.VISIBLE);
                                }else {
                                    tv7.setVisibility(View.VISIBLE);
                                    rv7.setVisibility(View.GONE);
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

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6, R.id.rl7})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:
                ViseHttp.POST(NetConfig.saveuserlabel)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.saveuserlabel))
                        .addParam("uid", uid)
                        .addParam("personality", label1)
                        .addParam("motion", label2)
                        .addParam("Music", label3)
                        .addParam("Delicious", label4)
                        .addParam("Film", label5)
                        .addParam("book", label6)
                        .addParam("Travel", label7)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(context, "保存成功");
                                        finish();
                                    }else {
                                        toToast(context, "保存失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
            case R.id.rl1:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl2:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl3:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "3");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl4:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "4");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl5:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "5");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl6:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "6");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl7:
                intent.setClass(context, EditorLabelListActivity.class);
                intent.putExtra("type", "7");
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            String type = data.getStringExtra("type");
            String label = data.getStringExtra("label");
            Log.e("123123", label);
            if(type.equals("1")){
                if(TextUtils.isEmpty(label)){
                    label1 = "";
                    tv1.setVisibility(View.VISIBLE);
                    rv1.setVisibility(View.GONE);
                }else {
                    label1 = label;
                    String[] a = label.split(",");
                    adapter1 = new EditorLabelSaveAdapter(a, 1);
                    rv1.setAdapter(adapter1);
                    tv1.setVisibility(View.GONE);
                    rv1.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("2")){
                if(TextUtils.isEmpty(label)){
                    label2 = "";
                    tv2.setVisibility(View.VISIBLE);
                    rv2.setVisibility(View.GONE);
                }else {
                    label2 = label;
                    String[] a = label.split(",");
                    adapter2 = new EditorLabelSaveAdapter(a, 2);
                    rv2.setAdapter(adapter2);
                    tv2.setVisibility(View.GONE);
                    rv2.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("3")){
                if(TextUtils.isEmpty(label)){
                    label3 = "";
                    tv3.setVisibility(View.VISIBLE);
                    rv3.setVisibility(View.GONE);
                }else {
                    label3 = label;
                    String[] a = label.split(",");
                    adapter3 = new EditorLabelSaveAdapter(a, 3);
                    rv3.setAdapter(adapter3);
                    tv3.setVisibility(View.GONE);
                    rv3.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("4")){
                if(TextUtils.isEmpty(label)){
                    label4 = "";
                    tv4.setVisibility(View.VISIBLE);
                    rv4.setVisibility(View.GONE);
                }else {
                    label4 = label;
                    String[] a = label.split(",");
                    adapter4 = new EditorLabelSaveAdapter(a, 4);
                    rv4.setAdapter(adapter4);
                    tv4.setVisibility(View.GONE);
                    rv4.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("5")){
                if(TextUtils.isEmpty(label)){
                    label5 = "";
                    tv5.setVisibility(View.VISIBLE);
                    rv5.setVisibility(View.GONE);
                }else {
                    label5 = label;
                    String[] a = label.split(",");
                    adapter5 = new EditorLabelSaveAdapter(a, 5);
                    rv5.setAdapter(adapter5);
                    tv5.setVisibility(View.GONE);
                    rv5.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("6")){
                if(TextUtils.isEmpty(label)){
                    label6 = "";
                    tv6.setVisibility(View.VISIBLE);
                    rv6.setVisibility(View.GONE);
                }else {
                    label6 = label;
                    String[] a = label.split(",");
                    adapter6 = new EditorLabelSaveAdapter(a, 6);
                    rv6.setAdapter(adapter6);
                    tv6.setVisibility(View.GONE);
                    rv6.setVisibility(View.VISIBLE);
                }
            }else if(type.equals("7")){
                if(TextUtils.isEmpty(label)){
                    label7 = "";
                    tv7.setVisibility(View.VISIBLE);
                    rv7.setVisibility(View.GONE);
                }else {
                    label7 = label;
                    String[] a = label.split(",");
                    adapter7 = new EditorLabelSaveAdapter(a, 7);
                    rv7.setAdapter(adapter7);
                    tv7.setVisibility(View.GONE);
                    rv7.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
