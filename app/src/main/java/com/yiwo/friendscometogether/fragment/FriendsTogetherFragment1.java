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

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.activecard.CardItemTouchHelperCallback;
import com.yiwo.friendscometogether.activecard.CardLayoutManager;
import com.yiwo.friendscometogether.activecard.OnSwipeListener;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.sp.SpImp;

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
    private List<String> data;

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

        data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        adapter = new CardAdapter(data);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(rv.getAdapter(), data);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, String o, int direction) {
//                Toast.makeText(Main2Activity.this, o, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
//                Toast.makeText(Main2Activity.this, "data clear", Toast.LENGTH_SHORT).show();
                data = new ArrayList<>();
                data.add("");
                data.add("");
                data.add("");
                data.add("");
                data.add("");
                adapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(rv, touchHelper);
        rv.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(rv);

    }

}
