package com.yiwo.friendscometogether.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.activecard.CardItemTouchHelperCallback;
import com.yiwo.friendscometogether.activecard.CardLayoutManager;
import com.yiwo.friendscometogether.activecard.OnSwipeListener;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/20.
 */

public class FriendsTogetherFragment1 extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private CardAdapter adapter;
    private List<FriendsTogethermodel.ObjBean> mList;

    private SpImp spImp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_together1, null);

        StatusBarUtils.setStatusBar(getActivity(), Color.parseColor("#FB473F"));
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        spImp = new SpImp(getContext());

        return view;
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    private void initData() {

        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                .addParam("app_key", token)
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                mList = model.getObj();
                                adapter = new CardAdapter(mList);
                                rv.setItemAnimator(new DefaultItemAnimator());
                                rv.setAdapter(adapter);
                                CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(rv.getAdapter(), mList);
                                cardCallback.setOnSwipedListener(new OnSwipeListener() {
                                    @Override
                                    public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                                    }

                                    @Override
                                    public void onSwiped(RecyclerView.ViewHolder viewHolder, FriendsTogethermodel.ObjBean o, int direction) {
//                Toast.makeText(Main2Activity.this, o, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onSwipedClear() {

                                    }
                                });
                                ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
                                CardLayoutManager cardLayoutManager = new CardLayoutManager(rv, touchHelper);
                                rv.setLayoutManager(cardLayoutManager);
                                touchHelper.attachToRecyclerView(rv);
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
