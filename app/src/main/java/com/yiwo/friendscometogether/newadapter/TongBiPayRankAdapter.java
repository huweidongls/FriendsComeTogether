package com.yiwo.friendscometogether.newadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.TripFragmentModel;
import com.yiwo.friendscometogether.newmodel.PayRankTongBiModel;
import com.yiwo.friendscometogether.pages.DetailsToBePaidActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class TongBiPayRankAdapter extends RecyclerView.Adapter<TongBiPayRankAdapter.ViewHolder> {

    private Context context;
    private List<PayRankTongBiModel.ObjBean> data;

    public TongBiPayRankAdapter(List<PayRankTongBiModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tong_bi_pay_rank, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_pay_time.setText(data.get(position).getAddTime());
        holder.tv_status.setText(data.get(position).getIfPay());
        holder.tv_order_sn.setText("订单号："+data.get(position).getOrderSn());
        holder.tv_pay_type.setText("支付方式："+data.get(position).getPayType());
        holder.tv_tong_bi_num.setText(data.get(position).getBuyIntegral());
        String str_money = "合计："+data.get(position).getPayMoney();
//        String str_money = "合计："+"48.90";
        SpannableStringBuilder ssb_money = new SpannableStringBuilder(str_money);
        AbsoluteSizeSpan ab = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab,0,str_money.indexOf("："), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab0 = new AbsoluteSizeSpan(16,true);
        ssb_money.setSpan(ab0,str_money.indexOf("：")+1,str_money.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab1 = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab1,str_money.indexOf(".")+1,str_money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_price.setText(ssb_money);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_sn;
        TextView tv_status;
        TextView tv_pay_time;
        TextView tv_tong_bi_num;
        TextView tv_pay_type;
        TextView tv_price;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_sn = itemView.findViewById(R.id.tv_order_sn);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_pay_time = itemView.findViewById(R.id.tv_pay_time);
            tv_tong_bi_num = itemView.findViewById(R.id.tv_tong_bi_num);
            tv_pay_type = itemView.findViewById(R.id.tv_pay_type);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
