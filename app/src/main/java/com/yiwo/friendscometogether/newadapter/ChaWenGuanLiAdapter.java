package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.ChaWenGuanLiModel;

import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by ljc on 2019/3/8.
 */

public class ChaWenGuanLiAdapter extends RecyclerView.Adapter<ChaWenGuanLiAdapter.ViewHolder> {


    private List<ChaWenGuanLiModel.ObjBean> data;
    private Context context;
    private ChaWenGuanLiPicsAdapter adapter;
    public ChaWenGuanLiAdapter(List<ChaWenGuanLiModel.ObjBean> list){
        this.data = list;
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chawenguanli, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ChaWenGuanLiAdapter.ViewHolder holder = new ChaWenGuanLiAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ChaWenGuanLiModel.ObjBean model = data.get(position);
        Glide.with(context).load(model.getUserpic()).into(holder.ivHead);
        holder.tvName.setText(model.getUsername());
        holder.tvTime.setText(model.getFfptime());
        holder.tvTitle.setText(model.getFftitle());
//        holder.rvPics
        holder.tvReason.setVisibility(View.GONE);
        adapter = new ChaWenGuanLiPicsAdapter(model.getPiclist());
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(context,
                        3// 每行显示item项数目
                ) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        holder.rvPics.setLayoutManager(layoutManager);
        holder.rvPics.setAdapter(adapter);
        if (model.getRadio().equals("2")){//允许展示
            holder.ivAlow.setImageResource(R.mipmap.show);
            holder.tvAlow.setTextColor(Color.parseColor("#333333"));
            holder.llAlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(2,position);
                }
            });
        }else if (model.getRadio().equals("1")){//不允许展示
            holder.ivAlow.setImageResource(R.mipmap.show_not);
            holder.tvAlow.setTextColor(Color.parseColor("#333333"));
            holder.llAlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(1,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvTitle;
        private RecyclerView rvPics;
        private TextView tvReason;
        private android.widget.LinearLayout llAlow;
        private ImageView ivAlow;
        private TextView tvAlow;

        public ViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rvPics = itemView.findViewById(R.id.rv_pics);
            tvReason = itemView.findViewById(R.id.tv_reason);
            llAlow = itemView.findViewById(R.id.ll_alow);
            ivAlow = itemView.findViewById(R.id.iv_alow_show);
            tvAlow = itemView.findViewById(R.id.tv_alow_show);

        }

    }
    public interface OnItemClickListener{
        void onClick(int type, int position);
    }
}
