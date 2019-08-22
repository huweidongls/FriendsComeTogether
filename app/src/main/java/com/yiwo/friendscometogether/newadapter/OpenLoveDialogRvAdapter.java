package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.OpenLoveTiaoJianModel;

import java.util.List;

/**
 * Created by ljc on 2019/8/22.
 */

public class OpenLoveDialogRvAdapter extends RecyclerView.Adapter<OpenLoveDialogRvAdapter.ViewHolder>{

    private List<OpenLoveTiaoJianModel.ObjBean.ItemBean> list;
    private Context context;
    public OpenLoveDialogRvAdapter(List<OpenLoveTiaoJianModel.ObjBean.ItemBean> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_openlove, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        OpenLoveDialogRvAdapter.ViewHolder holder = new OpenLoveDialogRvAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getStatus().equals("1")?R.mipmap.choose_green:R.mipmap.choose_gray);
        holder.textView.setText(list.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return list!=null ? list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.iv);
        }
    }
}
