package com.yiwo.friendscometogether.newfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.UserIntercalationListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MyChawenAdapter;
import com.yiwo.friendscometogether.newadapter.TeamChawenAdapter;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/18.
 */

public class AllChawenFragment extends BaseFragment {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;

    private MyChawenAdapter adapter;
    private List<UserIntercalationListModel.ObjBean> mList;
    private TeamChawenAdapter adapter1;
    private List<String> mList1;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_chawen, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.userIntercalationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userIntercalationListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserIntercalationListModel userIntercalationListModel = gson.fromJson(data, UserIntercalationListModel.class);
                                mList = userIntercalationListModel.getObj();
                                adapter = new MyChawenAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                rv1.setLayoutManager(manager);
                                rv1.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        mList1 = new ArrayList<>();
        mList1.add("");
        mList1.add("");
        mList1.add("");
        adapter1 = new TeamChawenAdapter(mList1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv2.setLayoutManager(manager);
        rv2.setAdapter(adapter1);

    }

    @OnClick({R.id.rl1, R.id.rl2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl1:
                tv1.setTextColor(Color.parseColor("#FFAB26"));
                tv2.setTextColor(Color.parseColor("#333333"));
                rv1.setVisibility(View.VISIBLE);
                rv2.setVisibility(View.GONE);
                break;
            case R.id.rl2:
                tv1.setTextColor(Color.parseColor("#333333"));
                tv2.setTextColor(Color.parseColor("#FFAB26"));
                rv1.setVisibility(View.GONE);
                rv2.setVisibility(View.VISIBLE);
                break;
        }
    }

}
