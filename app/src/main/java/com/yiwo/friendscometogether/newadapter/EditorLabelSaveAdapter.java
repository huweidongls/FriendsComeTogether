package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by Administrator on 2019/1/8.
 */

public class EditorLabelSaveAdapter extends RecyclerView.Adapter<EditorLabelSaveAdapter.ViewHolder> {

    private Context context;
    private String[] data;
    private int a;

    public EditorLabelSaveAdapter(String[] data, int a) {
        this.data = data;
        this.a = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_editor_label_save_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(a == 1){
            holder.tv.setBackgroundResource(R.drawable.bg_b5b6b7_10px);
            holder.tv.setTextColor(Color.parseColor("#3D546E"));
        }else if(a == 2){
            holder.tv.setBackgroundResource(R.drawable.bg_cfead8_10px);
            holder.tv.setTextColor(Color.parseColor("#115F2D"));
        }else if(a == 3){
            holder.tv.setBackgroundResource(R.drawable.bg_b6bfde_10px);
            holder.tv.setTextColor(Color.parseColor("#344DA0"));
        }else if(a == 4){
            holder.tv.setBackgroundResource(R.drawable.bg_f7b0a4_10px);
            holder.tv.setTextColor(Color.parseColor("#B5321B"));
        }else if(a == 5){
            holder.tv.setBackgroundResource(R.drawable.bg_cac9e2_10px);
            holder.tv.setTextColor(Color.parseColor("#535180"));
        }else if(a == 6){
            holder.tv.setBackgroundResource(R.drawable.bg_feedbf_10px);
            holder.tv.setTextColor(Color.parseColor("#A98010"));
        }else if(a == 7){
            holder.tv.setBackgroundResource(R.drawable.bg_d3dde3_10px);
            holder.tv.setTextColor(Color.parseColor("#307299"));
        }
        holder.tv.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}
