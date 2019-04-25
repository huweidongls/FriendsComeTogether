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
import com.yiwo.friendscometogether.newmodel.PersonImpressonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/4/24.
 */

public class PersonImpressonRvAdapter extends RecyclerView.Adapter<PersonImpressonRvAdapter.ViewHolder> {
    private List<PersonImpressonModel.ObjBean> data;
    private Context context ;
    private OnCommentListenner listenner;
    public PersonImpressonRvAdapter(List<PersonImpressonModel.ObjBean> data){
        this.data = data;
    }
    public void setListenner(OnCommentListenner listenner){
        this.listenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_impresson_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getLabel_name());
        if (data.get(position).getStatus().equals("0")){//此标签未做过评价
            holder.tv.setBackgroundResource(R.drawable.bg_f2cac6_30px);
            holder.tv.setTextColor(Color.parseColor("#d84c37"));
        }else {//此标签已经做过评价
            holder.tv.setBackgroundResource(R.drawable.bg_d84c37_30px);
            holder.tv.setTextColor(Color.WHITE);
        }
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.conmmentListen(position,data.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    public interface OnCommentListenner{
        void conmmentListen(int posion,String labelID);
    }
}
