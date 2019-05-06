package com.yiwo.friendscometogether.adapter;

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
import com.yiwo.friendscometogether.pages.DetailsToBePaidActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class FragmentToTripAdapter extends RecyclerView.Adapter<FragmentToTripAdapter.ViewHolder> {

    private Context context;
    private List<TripFragmentModel.ObjBean> data;
    private Activity activity;
    private OnCancelListener listener;

    public void setOnCancelListener(OnCancelListener listener){
        this.listener = listener;
    }

    public FragmentToTripAdapter(List<TripFragmentModel.ObjBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_to_trip, parent, false);
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
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvStatus.setText(data.get(position).getStatus());
        if (!TextUtils.isEmpty(data.get(position).getPf_pic())) {
            Picasso.with(context).load(data.get(position).getPf_pic()).into(holder.iv);
        }

        holder.tvStartTime.setText("开始时间: " + data.get(position).getBegin_time());
        holder.tvEndTime.setText("结束时间: " + data.get(position).getEnd_time());
        holder.tvJoinNum.setText("参加人数："+ data.get(position).getJoin_num());
        holder.tvNoName.setText("是否匿名："+(data.get(position).getNoname().equals("0")? "否":"是"));

//        holder.tvPriceDetails.setText(data.get(position).getPrice_type());

        String str_money = "合计："+data.get(position).getOpaymoney();
//        String str_money = "合计："+"48.90";
        SpannableStringBuilder ssb_money = new SpannableStringBuilder(str_money);
        AbsoluteSizeSpan ab = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab,0,str_money.indexOf("："), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab0 = new AbsoluteSizeSpan(16,true);
        ssb_money.setSpan(ab0,str_money.indexOf("：")+1,str_money.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan ab1 = new AbsoluteSizeSpan(12,true);
        ssb_money.setSpan(ab1,str_money.indexOf(".")+1,str_money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvPrice.setText(ssb_money);

        if (data.get(position).getOrderStatus().equals("1")){  //我被邀请
            holder.tv_niming_staus.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setText("邀请人："+data.get(position).getUser());
        }else if (data.get(position).getOrderStatus().equals("2")){//邀请他人
            holder.tv_niming_staus.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setVisibility(View.VISIBLE);
            holder.tvPriceDetails.setText("邀请："+data.get(position).getBUser());
        }else {
            holder.tvPriceDetails.setVisibility(View.GONE);// 邀请：***、邀请人：***
            holder.tv_niming_staus.setVisibility(View.GONE);// 被邀请人不取消活动，此订单不可退款
        }
        holder.tvCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果为邀请他人的订单  只显示付款 其他提示由被邀请人操作（取消订单）
                if (data.get(position).getOrderStatus().equals("2")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("需被邀请人取消订单，方可退款")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                }else {
                    listener.onCancel(position);
                }
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
        private TextView tvStatus;
        private ImageView iv;
        private TextView tvPriceDetails;
        private TextView tvPrice;
        private TextView tvCancelTrip;
        //新详情
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvJoinNum;
        private TextView tvNoName;

        //
        private TextView tv_niming_staus;

        public ViewHolder(View itemView) {
            super(itemView);
            rlDetails = itemView.findViewById(R.id.fragment_to_trip_rv_rl_details);
            tvTitle = itemView.findViewById(R.id.fragment_to_trip_rv_tv_title);
            tvStatus = itemView.findViewById(R.id.fragment_to_trip_rv_tv_status);
            iv = itemView.findViewById(R.id.fragment_to_trip_rv_iv);
            tvPriceDetails = itemView.findViewById(R.id.fragment_to_trip_rv_tv_price_details);
            tvPrice = itemView.findViewById(R.id.fragment_to_trip_rv_tv_price);
            tvCancelTrip = itemView.findViewById(R.id.fragment_to_trip_rv_tv_cancle_trip);

            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvEndTime = itemView.findViewById(R.id.tv_end_time);
            tvJoinNum = itemView.findViewById(R.id.tv_people_num);
            tvNoName = itemView.findViewById(R.id.tv_noname);

            tv_niming_staus = itemView.findViewById(R.id.tv_niming_staus);
        }
    }

    public interface OnCancelListener{
        void onCancel(int position);
    }

}
