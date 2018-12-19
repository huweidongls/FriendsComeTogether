package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserCollectionModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class AllCollectionAdapter extends RecyclerView.Adapter<AllCollectionAdapter.ViewHolder> {

    private Context context;
    private List<UserCollectionModel.ObjBean> data;

    public AllCollectionAdapter(List<UserCollectionModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_all_collection, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFtitle());
        holder.tvLookNum.setText("12345阅读了这篇友记");
        Glide.with(context).load(data.get(position).getFpic()).into(holder.iv);
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvLookNum;
        private ImageView iv;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLookNum = itemView.findViewById(R.id.tv_look_num);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

}
