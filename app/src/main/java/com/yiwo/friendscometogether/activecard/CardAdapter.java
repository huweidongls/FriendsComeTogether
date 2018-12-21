package com.yiwo.friendscometogether.activecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<FriendsTogethermodel.ObjBean> data;
    private int currentPositon = 0;

    public CardAdapter(List<FriendsTogethermodel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public int getPosition(){
        return currentPositon;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        currentPositon = position;
        Glide.with(context).load(data.get(position).getUpicurl()).into(holder.ivAvatar);
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvTitle.setText(data.get(position).getPftitle());
        Glide.with(context).load(data.get(position).getPfpic()).into(holder.ivTitle);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvFabuTime;
        private TextView tvTitle;
        private ImageView ivTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvFabuTime = itemView.findViewById(R.id.tv_fabu_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivTitle = itemView.findViewById(R.id.iv_title);
        }
    }

}
