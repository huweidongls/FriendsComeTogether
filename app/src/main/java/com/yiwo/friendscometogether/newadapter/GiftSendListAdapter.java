package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PayRankTongBiModel;
import com.yiwo.friendscometogether.newmodel.SendGiftListModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class GiftSendListAdapter extends RecyclerView.Adapter<GiftSendListAdapter.ViewHolder> {

    private Context context;
    private List<SendGiftListModel.ObjBean> data;

    public GiftSendListAdapter(List<SendGiftListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_send_gift_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_pay_time.setText(data.get(position).getAddTime());
        Glide.with(context).load(data.get(position).getPresentImg()).apply(new RequestOptions().error(R.mipmap.icon_new)).into(holder.iv_gift);
        holder.tv_gift_name.setText(data.get(position).getPresentName());
        holder.tv_gift_num.setText("×"+data.get(position).getNum());
        holder.tv_integral.setText(data.get(position).getIntegral());
        holder.tv_user_name.setText(data.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pay_time;
        ImageView iv_gift;
        TextView tv_gift_name;
        TextView tv_gift_num;
        TextView tv_integral;
        TextView tv_user_name;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_pay_time = itemView.findViewById(R.id.tv_pay_time);
            iv_gift = itemView.findViewById(R.id.iv_gift);
            tv_gift_name = itemView.findViewById(R.id.tv_gift_name);
            tv_gift_num = itemView.findViewById(R.id.tv_gift_num);
            tv_integral = itemView.findViewById(R.id.tv_integral);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
        }
    }
}
