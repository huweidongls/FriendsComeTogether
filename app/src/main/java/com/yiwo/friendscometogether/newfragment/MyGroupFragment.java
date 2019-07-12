package com.yiwo.friendscometogether.newfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.MyFriendModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MyGroupAdapter;
import com.yiwo.friendscometogether.newmodel.MyGroupListModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ljc on 2019/5/21.
 */

public class MyGroupFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    private SpImp spImp;
    private List<MyGroupListModel.ObjBean> list = new ArrayList<>();
    private MyGroupAdapter adapter = new MyGroupAdapter(list);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_group, null);
//        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        initData();
        return view;
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter.setListenner(new MyGroupAdapter.OnLongClickListenner() {
            @Override
            public void onLongClick(final int postion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定退出该群？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViseHttp.POST(NetConfig.moveOutGroup)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.moveOutGroup))
                                        .addParam("group_id",list.get(postion).getGroupid())
                                        .addParam("userID",spImp.getUID())
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200){
                                                        toToast(getContext(),"已退出该群");
                                                        list.remove(postion);
                                                        adapter.notifyDataSetChanged();
                                                    }else {
                                                        toToast(getContext(),"删除失败:");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFail(int errCode, String errMsg) {
                                                toToast(getContext(),"删除失败"+errCode+":"+errMsg);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });
        rv.setAdapter(adapter);

        ViseHttp.POST(NetConfig.groupList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.groupList))
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyGroupListModel model = gson.fromJson(data,MyGroupListModel.class);
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
}
