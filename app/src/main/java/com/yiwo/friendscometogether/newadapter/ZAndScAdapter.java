package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.ZAndScModel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class ZAndScAdapter extends RecyclerView.Adapter<ZAndScAdapter.ViewHolder> {

    private Context context;
    private List<ZAndScModel.ObjBean> data;

    public ZAndScAdapter(List<ZAndScModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_z_and_sc, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.ivAvatar);
        if(data.get(position).getType().equals("0")){
            String str = "<font color='#0765AA'>"+data.get(position).getUsername()+"</font>点赞了您的文章。";
            holder.tv.setText(Html.fromHtml(str));
        }else {
            String str = "<font color='#0765AA'>"+data.get(position).getUsername()+"</font>收藏了您的文章。";
            holder.tv.setText(Html.fromHtml(str));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAvatar;
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}