package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserCollectionModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> {

    private Context context;
    private List<UserCollectionModel.ObjBean> data;

    public MyCollectionAdapter(List<UserCollectionModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_collection, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(data.get(position).getFpic()).into(holder.iv);
        holder.tvTitle.setText(data.get(position).getFtitle());
        holder.tvTime.setText(data.get(position).getFtime());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(data.get(position).getFtype().equals("1")){
                    intent.putExtra("pfID", data.get(position).getFtableid());
                    intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                    context.startActivity(intent);
                }else {
                    intent.putExtra("fmid", data.get(position).getFtableid());
                    intent.setClass(context, DetailsOfFriendsActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvTime;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_my_collection_rv_iv);
            tvTitle = itemView.findViewById(R.id.activity_my_collection_rv_tv_title);
            tvTime = itemView.findViewById(R.id.activity_my_collection_rv_tv_time);
            ll = itemView.findViewById(R.id.activity_my_collection_rv_ll);
        }
    }

}
