package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.TongBiPriceModel;

import java.util.List;

/**
 * Created by ljc on 2019/12/23.
 */

public class TongBiPriceAdapter extends RecyclerView.Adapter<TongBiPriceAdapter.ViewHolder>{

    private List<TongBiPriceModel.ObjBean> data;
    private Context context;
    private ItemClickListen itemClickListen;
    public TongBiPriceAdapter(List<TongBiPriceModel.ObjBean> list,ItemClickListen listen){
        this.data = list;
        this.itemClickListen = listen;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_tong_bi_price,parent,false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_yuan.setText(data.get(position).getMoney()+"元");
        holder.tv_num.setText(data.get(position).getIntegral());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListen.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_yuan;
        TextView tv_num;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_yuan = itemView.findViewById(R.id.tv_yuan);
            ll = itemView.findViewById(R.id.ll);
        }
    }
    public interface ItemClickListen{
        void onItemClick(int pos);
    }
}
