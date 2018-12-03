package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MyPicListModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class MyPicturesAdapter extends RecyclerView.Adapter<MyPicturesAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;

    private Context context;
    private List<MyPicListModel.ObjBean> data;

    private onItemClickListener listener;

    public MyPicturesAdapter(List<MyPicListModel.ObjBean> data) {
        this.data = data;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_pictures, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            holder.rlAdd.setVisibility(View.VISIBLE);
            holder.rlIv.setVisibility(View.GONE);
        } else {
            holder.rlAdd.setVisibility(View.GONE);
            holder.rlIv.setVisibility(View.VISIBLE);
            Picasso.with(context).load(data.get(position - 1).getUpicurl()).into(holder.iv);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(2, position);
                }
            });
        }
        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(1, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlAdd;
        private ImageView iv;
        private RelativeLayout rlIv;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            rlAdd = itemView.findViewById(R.id.activity_my_pictures_rv_rl_add);
            iv = itemView.findViewById(R.id.activity_my_pictures_rv_iv);
            rlIv = itemView.findViewById(R.id.activity_my_pictures_rv_rl_iv);
            ivDelete = itemView.findViewById(R.id.activity_my_pictures_rv_iv_delete);
        }
    }

    public interface onItemClickListener {
        void onClick(int type, int position);
    }

}
