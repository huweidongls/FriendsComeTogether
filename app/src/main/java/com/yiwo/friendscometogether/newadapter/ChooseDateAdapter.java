package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ChooseDateModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;

import java.util.List;

/**
 * Created by ljc on 2019/2/26.
 */

public class ChooseDateAdapter extends RecyclerView.Adapter<ChooseDateAdapter.ViewHolder> {

    private Context context;
    private List<FriendsTogetherDetailsModel.ObjBean.PhaseBean> data;
    private ChooseDatePostionListener chooseDatePostionListen;
    public ChooseDateAdapter(List<FriendsTogetherDetailsModel.ObjBean.PhaseBean> data){
        this.data =data;
    }
    public void setChooseDatePostionListen(ChooseDatePostionListener postionListen){
        this.chooseDatePostionListen = postionListen;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_date_choose, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ChooseDateAdapter.ViewHolder holder = new ChooseDateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tv_date.setText("日期："+data.get(position).getPhase_begin_time().substring(5,10));
            holder.tv_price.setText(data.get(position).getPhase_price());
            if (data.get(position).getSelected()){
                holder.iv_choosed.setVisibility(View.VISIBLE);
                holder.rl.setBackgroundResource(R.drawable.bg_d84c37_border_6px);
            }else {
                holder.iv_choosed.setVisibility(View.GONE);
                holder.rl.setBackgroundResource(R.drawable.bg_gray_border_6px);
            }
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDatePostionListen.choosePOstion(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_choosed;
        RelativeLayout rl;
        TextView tv_date;
        TextView tv_price;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_choosed = itemView.findViewById(R.id.iv_choosed);
            rl = itemView.findViewById(R.id.rl);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
    public interface ChooseDatePostionListener{
        void choosePOstion(int postion);
    }
}
