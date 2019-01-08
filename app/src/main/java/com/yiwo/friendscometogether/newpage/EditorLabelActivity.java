package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    private SpImp spImp;
    private String uid = "";

    private EditorLabelSaveAdapter adapter1;
    private List<UserSaveLabelModel.ObjBean> mList1;

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
                                mList1 = model.getObj().get(0);
                                FlowLayoutManager manager = new FlowLayoutManager();
                                rv1.setLayoutManager(manager);
                                adapter1 = new EditorLabelSaveAdapter(mList1);
                                rv1.setAdapter(adapter1);
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
