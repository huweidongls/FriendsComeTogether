package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class YouJiAdapter extends RecyclerView.Adapter<YouJiAdapter.ViewHolder>{

    private List<YouJiListModel> data;
    private Context context;
    public YouJiAdapter(List<YouJiListModel> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        YouJiAdapter.ViewHolder holder = new YouJiAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data.get(position).getType() == 1){
//            ViewGroup.LayoutParams layoutParams = holder.rv_youji.getLayoutParams();
//            layoutParams.height = 546;//获取最终图片高度
//            holder.rv_youji.setLayoutParams(layoutParams);//应用高度到布局中
            holder.rv_youji.setVisibility(View.VISIBLE);
            holder.rv_video.setVisibility(View.GONE);
            Glide.with(context).load("http://s4.sinaimg.cn/bmiddle/4ecbc7eah83f76e6fdf73&690").into(holder.iv_youji);
        }else {
//            ViewGroup.LayoutParams layoutParams = holder.rv_video.getLayoutParams();
//            layoutParams.height = 458;//获取最终图片高度
//            holder.rv_video.setLayoutParams(layoutParams);//应用高度到布局中
            holder.rv_video.setVisibility(View.VISIBLE);
            holder.rv_youji.setVisibility(View.GONE);
            Glide.with(context).load("http://s4.sinaimg.cn/bmiddle/4ecbc7eah83f76e6fdf73&690").into(holder.iv_video);
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rv_youji;
        private RelativeLayout rv_video;
        private ImageView iv_youji;
        private ImageView iv_video;
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            rv_video = itemView.findViewById(R.id.rv_video);
            rv_youji = itemView.findViewById(R.id.rv_youji);
            iv_youji = itemView.findViewById(R.id.iv_youju);
            iv_video = itemView.findViewById(R.id.iv_video);
        }
    }
}
