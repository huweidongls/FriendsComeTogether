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
import com.yiwo.friendscometogether.dbmodel.LookHistoryDbModel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class LookHistoryAdapter extends RecyclerView.Adapter<LookHistoryAdapter.ViewHolder> {

    private Context context;
    private List<LookHistoryDbModel> data;
    private OnDeleteListenner deleteListenner;
    public LookHistoryAdapter(List<LookHistoryDbModel> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_look_history, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(data.get(position).getPic_url()).into(holder.ivTitle);
        holder.tvTime.setText(data.get(position).getLook_time());
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(data.get(position).getType().equals("0")){
                    intent.putExtra("pfID", data.get(position).getLook_id());
                    intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                    context.startActivity(intent);
                }else {
                    intent.putExtra("fmid", data.get(position).getLook_id());
                    intent.setClass(context, DetailsOfFriendsWebActivity2.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.tvBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListenner!=null){
                    deleteListenner.deleteListen(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setDeleteListenner(OnDeleteListenner deleteListenner) {
        this.deleteListenner = deleteListenner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTitle;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvBtnDelete;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTitle = itemView.findViewById(R.id.activity_look_history_rv_iv);
            tvTime = itemView.findViewById(R.id.activity_look_history_rv_tv_time);
            tvTitle = itemView.findViewById(R.id.activity_look_history_rv_tv_title);
            ll = itemView.findViewById(R.id.activity_look_history_rv_ll);
            tvBtnDelete = itemView.findViewById(R.id.tv_btn_delete);
        }
    }
    public interface OnDeleteListenner{
        void deleteListen(int position);
    }

}
