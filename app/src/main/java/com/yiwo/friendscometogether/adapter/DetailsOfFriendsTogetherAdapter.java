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
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class DetailsOfFriendsTogetherAdapter extends RecyclerView.Adapter<DetailsOfFriendsTogetherAdapter.ViewHolder> {
    private Context context;
    List<FriendsTogetherDetailsModel.ObjBean.InfoListBean> data;
    private FriendsTogetherDetailsItemAdapter adapter;

    public DetailsOfFriendsTogetherAdapter(List<FriendsTogetherDetailsModel.ObjBean.InfoListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_details_friends_together_item, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        DetailsOfFriendsTogetherAdapter.ViewHolder holder = new DetailsOfFriendsTogetherAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTv.setText(data.get(position).getTitle());
        holder.contentTv.setText(data.get(position).getContent());
        LinearLayoutManager manager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.detailsRv.setLayoutManager(manager);
        adapter = new FriendsTogetherDetailsItemAdapter(data.get(position).getImage_list());
        holder.detailsRv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView contentTv;
        private RecyclerView detailsRv;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (itemView).findViewById(R.id.details_friend_together_item_title_tv);
            contentTv = (itemView).findViewById(R.id.details_friend_together_item_content_tv);
            detailsRv = (itemView).findViewById(R.id.details_friend_together_item_rv);
        }
    }
}
