package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.HomeHotFriendsRememberModel;
import com.yiwo.friendscometogether.pages.VideoActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    List<HomeHotFriendsRememberModel.ObjBean.VideoBean> data;
    Context context;

    public VideoAdapter(List<HomeHotFriendsRememberModel.ObjBean.VideoBean> data) {
        this.data = data;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hot_video, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        VideoAdapter.ViewHolder holder = new VideoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, final int position) {
//        Picasso.with(context).load(data.get(position).getImg()).into(holder.videoIv);
//        holder.videoTv.setText(data.get(position).getVname());
        Glide.with(context).load(data.get(position).getImg()).into(holder.iv);
        holder.videoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, VideoActivity.class);
                it.putExtra("videoUrl", data.get(position).getVurl());
                it.putExtra("title", data.get(position).getVname());
                it.putExtra("picUrl", data.get(position).getImg());
                it.putExtra("vid", data.get(position).getVID());
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //        ImageView videoIv;
//        TextView videoTv;
        private LinearLayout videoRl;
        private ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
//            videoIv = (itemView).findViewById(R.id.home_hot_video_iv);
//            videoTv = (itemView).findViewById(R.id.home_hot_video_tv);
            videoRl = (itemView).findViewById(R.id.ll_video);
            iv = itemView.findViewById(R.id.iv_video);
        }
    }
}
