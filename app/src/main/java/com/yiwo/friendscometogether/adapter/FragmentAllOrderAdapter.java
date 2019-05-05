package com.yiwo.friendscometogether.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.yiwo.friendscometogether.model.AllOrderFragmentModel;
import com.yiwo.friendscometogether.pages.DetailsToBePaidActivity;
import com.yiwo.friendscometogether.pages.OrderCommentActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class FragmentAllOrderAdapter extends RecyclerView.Adapter<FragmentAllOrderAdapter.ViewHolder> {

    private Context context;
    private List<AllOrderFragmentModel.ObjBean> data;
    private Activity activity;
    private OnPayListener listener;
    private OnCancelListener listener1;
    private OnDeleteListener listener2;

    public void setOnDeleteListener(OnDeleteListener listener2){
        this.listener2 = listener2;
    }

    public void setOnCancelListener(OnCancelListener listener1){
        this.listener1 = listener1;
    }

    public void setOnPayListener(OnPayListener listener){
        this.listener = listener;
    }

    public FragmentAllOrderAdapter(List<AllOrderFragmentModel.ObjBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_all_order, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rlDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsToBePaidActivity.class);
                intent.putExtra("order_id", data.get(position).getOID());
                context.startActivity(intent);
//                activity.finish();
            }
        });
        holder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, OrderCommentActivity.class);
                intent.putExtra("orderid", data.get(position).getOID());
                intent.putExtra("type","0");
                context.startActivity(intent);
            }
        });
        holder.tvTitle.setText(data.get(position).getTitle());
        if (!TextUtils.isEmpty(data.get(position).getPf_pic())) {
            Picasso.with(context).load(data.get(position).getPf_pic()).into(holder.iv);
        }

        holder.tvStartTime.setText("开始时间: " + data.get(position).getBegin_time());
        holder.tvEndTime.setText("结束时间: " + data.get(position).getEnd_time());
        holder.tvJoinNum.setText("参加人数："+ data.get(position).getJoin_num());
        holder.tvNoName.setText("是否匿名："+(data.get(position).getNoname().equals("0")? "否":"是"));
        if (data.get(position).isIf_comment()){
            holder.tvComment.setText("已评价");
        }else {
            holder.tvComment.setText("评价");
        }
        String str_money = "合计："+data.get(position).getOpaymoney();
//        String str_money = "合计："+"48.90";
        SpannableStringBuilder ssb_money = new SpannableStringBuilder(str_money);
        AbsoluteSizeSpan ab = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab,0,str_money.indexOf("："), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab0 = new AbsoluteSizeSpan(16,true);
        ssb_money.setSpan(ab0,str_money.indexOf("：")+1,str_money.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab1 = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab1,str_money.indexOf(".")+1,str_money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        holder.tvPrice.setText("合计: ¥" +ssb_money);
        if (data.get(position).getOrderStatus().equals("1")){  //我被邀请
            holder.tvPriceDetails.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setText("邀请人："+data.get(position).getUser());
        }else if (data.get(position).getOrderStatus().equals("2")){//邀请他人
            holder.tvPriceDetails.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setText("邀请："+data.get(position).getBUser());
        }else {
            holder.tvPriceDetails.setVisibility(View.GONE);
        }
        holder.tvPrice.setText(ssb_money);
        holder.tvStatus.setText(data.get(position).getStatus());
        if(data.get(position).getOrder_type().equals("7")){//已取消
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("6")){//行程中
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.GONE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.VISIBLE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("5")){//已退款
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.VISIBLE);
            holder.tvReturning.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("4")){//退款中
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.VISIBLE);
        }else if(data.get(position).getOrder_type().equals("3")){//行程结束
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.VISIBLE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("2")){ //待行程
            holder.tvCancelTrip.setVisibility(View.VISIBLE);
            holder.tvDeleteTrip.setVisibility(View.GONE);
            holder.tvToTrip.setVisibility(View.VISIBLE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.GONE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("1")){//1待支付
            holder.tvCancelTrip.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
            holder.tvToTrip.setVisibility(View.GONE);
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvPay.setVisibility(View.VISIBLE);
            holder.tvOkReturn.setVisibility(View.GONE);
            holder.tvReturning.setVisibility(View.GONE);
        }
        //如果为邀请他人的订单  只显示付款 其他提示由被邀请人操作（取消订单）
        if (data.get(position).getOrderStatus().equals("2")){

        }
        //不可点击按钮全部隐藏！！！20190301
        holder.tvToTrip.setVisibility(View.GONE);
        holder.tvTriping.setVisibility(View.GONE);
        holder.tvOkReturn.setVisibility(View.GONE);
        holder.tvReturning.setVisibility(View.GONE);
        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPay(position);
            }
        });
        holder.tvCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener1.onCancel(position);
            }
        });
        holder.tvDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener2.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlDetails;
        private TextView tvTitle;
        private ImageView iv;
        private TextView tvPrice;
        private TextView tvPriceDetails;
        private TextView tvStatus;
        private TextView tvCancelTrip;
        private TextView tvDeleteTrip;
        private TextView tvToTrip;
        private TextView tvTriping;
        private TextView tvComment;
        private TextView tvPay;
        private TextView tvOkReturn;
        private TextView tvReturning;

        //新详情
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvJoinNum;
        private TextView tvNoName;

        public ViewHolder(View itemView) {
            super(itemView);
            rlDetails = itemView.findViewById(R.id.fragment_all_order_rv_rl_details);
            tvTitle = itemView.findViewById(R.id.fragment_all_order_rv_tv_title);
            iv = itemView.findViewById(R.id.fragment_all_order_rv_iv);
            tvPrice = itemView.findViewById(R.id.fragment_all_order_rv_tv_price);
            tvPriceDetails = itemView.findViewById(R.id.fragment_all_order_rv_tv_price_details);
            tvStatus = itemView.findViewById(R.id.fragment_all_order_rv_tv_status);
            tvCancelTrip = itemView.findViewById(R.id.fragment_to_pay_rv_tv_cancle_trip);
            tvDeleteTrip = itemView.findViewById(R.id.fragment_to_pay_rv_tv_delete_trip);
            tvToTrip = itemView.findViewById(R.id.fragment_to_pay_rv_tv_to_trip);
            tvTriping = itemView.findViewById(R.id.fragment_to_pay_rv_tv_triping);
            tvComment = itemView.findViewById(R.id.fragment_to_pay_rv_tv_comment);
            tvPay = itemView.findViewById(R.id.fragment_to_pay_rv_tv_payment);
            tvOkReturn = itemView.findViewById(R.id.fragment_to_pay_rv_tv_ok_return);
            tvReturning = itemView.findViewById(R.id.fragment_to_pay_rv_tv_returning);

            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvEndTime = itemView.findViewById(R.id.tv_end_time);
            tvJoinNum = itemView.findViewById(R.id.tv_people_num);
            tvNoName = itemView.findViewById(R.id.tv_noname);
        }
    }

    public interface OnPayListener{
        void onPay(int position);
    }

    public interface OnCancelListener{
        void onCancel(int position);
    }

    public interface OnDeleteListener{
        void onDelete(int position);
    }

}
