package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.ChaWenGuanLiModel;

import java.util.List;

/**
 * Created by ljc on 2019/3/8.
 */

public class ChaWenGuanLiPicsAdapter extends RecyclerView.Adapter<ChaWenGuanLiPicsAdapter.ViewHolder> {
    private List<ChaWenGuanLiModel.ObjBean.PiclistBean> data;
    private Context context;
    public ChaWenGuanLiPicsAdapter(List<ChaWenGuanLiModel.ObjBean.PiclistBean> list){
        this.data = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chawenguanli_pics, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ChaWenGuanLiPicsAdapter.ViewHolder holder = new ChaWenGuanLiPicsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getPicurl()).into(holder.iv);
        if (!data.get(position).getPictitle().equals("")){
            holder.tv.setText(data.get(position).getPictitle());
        }else{
            holder.rl.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_pic);
            tv = itemView.findViewById(R.id.tv);
            rl = itemView.findViewById(R.id.rl);
        }
    }
}
