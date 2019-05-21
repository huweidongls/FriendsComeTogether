package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.api.NimUIKit;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.newmodel.MyGroupListModel;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by ljc on 2019/5/21.
 */

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.ViewHolder> {

    private Context context;
    private List<MyGroupListModel.ObjBean> data;
    private SpImp spImp;
    public MyGroupAdapter(List<MyGroupListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_group, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        MyGroupAdapter.ViewHolder holder = new MyGroupAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getName());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team(data.get(position).getGroupid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            rl = itemView.findViewById(R.id.rl);
        }
    }
    private void team(String teamId) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(context, teamId);
    }
}
