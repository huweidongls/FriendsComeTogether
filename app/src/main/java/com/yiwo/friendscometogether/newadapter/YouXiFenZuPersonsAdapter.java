package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
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
import com.yiwo.friendscometogether.newmodel.YouXiFenZuPersonModel;

import java.util.List;

/**
 * Created by ljc on 2019/9/19.
 */

public class YouXiFenZuPersonsAdapter extends RecyclerView.Adapter<YouXiFenZuPersonsAdapter.ViewHolder>{

    private List<YouXiFenZuPersonModel.ObjBean> data;
    private Context context;
    private OnItemClickListener listener;
    private OnDeleteListener deleteListener;//删除监听   只在 已分组列表中使用；
    public Boolean isShowDelete = false;//删除监听   只在 已分组列表中使用；
    public YouXiFenZuPersonsAdapter(List<YouXiFenZuPersonModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youxi_fenzu_all_per, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        YouXiFenZuPersonsAdapter.ViewHolder holder = new YouXiFenZuPersonsAdapter.ViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText("No."+data.get(position).getGameNum()+"\n"+data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.itemClick(position);
                }
            }
        });
        if (isShowDelete){
            holder.ivDelete.setVisibility(View.VISIBLE);
        }else {
            holder.ivDelete.setVisibility(View.GONE);
        }
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener!=null){
                    deleteListener.delete(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size()>0?data.size():0;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        LinearLayout ll;
        ImageView ivDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
    public interface OnItemClickListener{
        void itemClick(int postion);
    }
    public interface OnDeleteListener{
        void delete(int postion);
    }
    public void showDeleteBtn(){
        isShowDelete = true;
        notifyDataSetChanged();
    }
}
