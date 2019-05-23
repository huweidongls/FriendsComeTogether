package com.yiwo.friendscometogether.create_friendremember;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by ljc on 2019/4/29.
 */

public class PicsMuLuAdapter extends RecyclerView.Adapter<PicsMuLuAdapter.ViewHolder> {
    private List<PicMuluModel> list;
    private Context context;
    private OnItemClickListenner listenner;
    public PicsMuLuAdapter(List<PicMuluModel> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_pics_mulu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("mulumulu",list.get(position).getMuluName());
        holder.tv.setText(list.get(position).getMuluName().substring(list.get(position).getMuluName().lastIndexOf("/")+1));
        Glide.with(context).load(list.get(position).getList().get(0).getPath()).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
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
        TextView tv;
        ImageView iv;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
    public interface OnItemClickListenner{
        void onClick(int postion);
    }
}
