package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserRememberModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MyDraftAdapter extends RecyclerView.Adapter<MyDraftAdapter.ViewHolder> {

    private Context context;
    private List<UserRememberModel.ObjBean> data;

    public MyDraftAdapter(List<UserRememberModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_draft, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        holder.tvTitle.setText(data.get(position).getFmtitle());
        holder.tvCreateTime.setText("创建时间: " + data.get(position).getFmtime());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvCreateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_my_draft_rv_iv);
            tvTitle = itemView.findViewById(R.id.activity_my_draft_rv_tv_title);
            tvCreateTime = itemView.findViewById(R.id.activity_my_draft_rv_tv_c_time);
        }
    }

}
