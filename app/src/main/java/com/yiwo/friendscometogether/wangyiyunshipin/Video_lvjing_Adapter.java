package com.yiwo.friendscometogether.wangyiyunshipin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.create_friendremember.PicMuluModel;

import java.util.List;

/**
 * Created by ljc on 2019/4/29.
 */

public class Video_lvjing_Adapter extends RecyclerView.Adapter<Video_lvjing_Adapter.ViewHolder> {
    private List<LvJingMode> list;
    private Context context;
    private OnItemClickListenner listenner;
    public Video_lvjing_Adapter(List<LvJingMode> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_video_lvjing, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.btn.setText(list.get(position).getName());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    public void setListenner(OnItemClickListenner listenner) {
        this.listenner = listenner;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView btn;
        public ViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
        }
    }
    public interface OnItemClickListenner{
        void onClick(int postion);
    }
}
