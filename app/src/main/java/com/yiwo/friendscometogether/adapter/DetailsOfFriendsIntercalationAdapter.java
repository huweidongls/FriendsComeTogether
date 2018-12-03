package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.DetailsRememberModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class DetailsOfFriendsIntercalationAdapter extends RecyclerView.Adapter<DetailsOfFriendsIntercalationAdapter.ViewHolder> {

    private Context context;
    private List<DetailsRememberModel.ObjBean.RenewBean> data;

    public DetailsOfFriendsIntercalationAdapter(List<DetailsRememberModel.ObjBean.RenewBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_details_of_friends_intercalation, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(data.get(position).getFftitle());
        holder.tvContent.setText(data.get(position).getFfcontect());
        LinearLayoutManager manager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(manager);
        DetailsOfFriendsUpDataAdapter adapter;
        adapter = new DetailsOfFriendsUpDataAdapter(data.get(position).getPic());
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvContent;
        private RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_details_of_friends_rv_tv_title);
            tvContent = itemView.findViewById(R.id.activity_details_of_friends_rv_tv_content);
            recyclerView = itemView.findViewById(R.id.activity_details_of_friends_rv_rv);
        }
    }

}
