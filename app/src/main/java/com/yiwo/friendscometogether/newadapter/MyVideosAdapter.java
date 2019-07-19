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
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.MyVideosModel;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by ljc on 2019/7/5.
 */

public class MyVideosAdapter extends RecyclerView.Adapter<MyVideosAdapter.ViewHolder> {
    private List<MyVideosModel.ObjBean> data;
    private SpImp spImp;
    private Context context;
    private BtnClickListener listener;
    public MyVideosAdapter(List<MyVideosModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_myvideos, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        MyVideosAdapter.ViewHolder holder = new MyVideosAdapter.ViewHolder(view);
        spImp = new SpImp(parent.getContext());
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv);
        holder.tv_title.setText(data.get(position).getVname());
        holder.tv_time.setText(data.get(position).getVtime());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(position);
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(position);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
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
        return data.size()>0?data.size():0;
    }

    public void setListener(BtnClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv_title;
        TextView tv_time;
        TextView tv_delete;
        TextView tv_edit;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_delete = itemView.findViewById(R.id.tv_btn_delete);
            tv_edit = itemView.findViewById(R.id.tv_btn_edit);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
    public interface BtnClickListener{
        void onDeleteClick(int i);
        void onEditClick(int i);
    }
}
