package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.KVMode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/8.
 */

public class PersonSameLabelAdapter extends RecyclerView.Adapter<PersonSameLabelAdapter.ViewHolder> {

    private Context context;
    private List<KVMode> data;
    public PersonSameLabelAdapter(List<KVMode> data) {
        this.data = data;
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
        Log.d("labellabelabel","SIZE:::"+data.size());
        if(data.get(position).getI() == 1){
            holder.tv.setBackgroundResource(R.drawable.bg_b5b6b7_10px);
            holder.tv.setTextColor(Color.parseColor("#3D546E"));
        }else if(data.get(position).getI()  == 2){
            holder.tv.setBackgroundResource(R.drawable.bg_cfead8_10px);
            holder.tv.setTextColor(Color.parseColor("#115F2D"));
        }else if(data.get(position).getI()  == 3){
            holder.tv.setBackgroundResource(R.drawable.bg_b6bfde_10px);
            holder.tv.setTextColor(Color.parseColor("#344DA0"));
        }else if(data.get(position).getI()  == 4){
            holder.tv.setBackgroundResource(R.drawable.bg_f7b0a4_10px);
            holder.tv.setTextColor(Color.parseColor("#B5321B"));
        }else if(data.get(position).getI()  == 5){
            holder.tv.setBackgroundResource(R.drawable.bg_cac9e2_10px);
            holder.tv.setTextColor(Color.parseColor("#535180"));
        }else if(data.get(position).getI()  == 6){
            holder.tv.setBackgroundResource(R.drawable.bg_feedbf_10px);
            holder.tv.setTextColor(Color.parseColor("#A98010"));
        }else if(data.get(position).getI()  == 7){
            holder.tv.setBackgroundResource(R.drawable.bg_d3dde3_10px);
            holder.tv.setTextColor(Color.parseColor("#307299"));
        }else if (data.get(position).getI()  == 0){
            holder.tv.setBackgroundResource(R.drawable.bg_74abca_10px);
            holder.tv.setTextColor(Color.WHITE);
        }
        holder.tv.setText(data.get(position).getString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}
