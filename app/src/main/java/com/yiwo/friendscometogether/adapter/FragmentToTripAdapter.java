package com.yiwo.friendscometogether.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
                activity.finish();
            }
        });
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvStatus.setText(data.get(position).getStatus());
        if (!TextUtils.isEmpty(data.get(position).getPf_pic())) {
            Picasso.with(context).load(data.get(position).getPf_pic()).into(holder.iv);
        }
        holder.tvContent.setText(data.get(position).getInfo());
        holder.tvTime.setText("活动时间: " + data.get(position).getTime_info());
        holder.tvPeopleNum.setText("参加人数: " + data.get(position).getJoin_num());
        holder.tvPriceDetails.setText(data.get(position).getPrice_type());
        holder.tvPrice.setText("合计费用: " + data.get(position).getOpaymoney());
        holder.tvCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel(position);
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
        private TextView tvContent;
        private TextView tvTime;
        private TextView tvPeopleNum;
        private TextView tvPriceDetails;
        private TextView tvPrice;
        private TextView tvCancelTrip;

        public ViewHolder(View itemView) {
            super(itemView);
            rlDetails = itemView.findViewById(R.id.fragment_to_trip_rv_rl_details);
            tvTitle = itemView.findViewById(R.id.fragment_to_trip_rv_tv_title);
            tvStatus = itemView.findViewById(R.id.fragment_to_trip_rv_tv_status);
            iv = itemView.findViewById(R.id.fragment_to_trip_rv_iv);
            tvContent = itemView.findViewById(R.id.fragment_to_trip_rv_tv_content);
            tvTime = itemView.findViewById(R.id.fragment_to_trip_rv_tv_time);
            tvPeopleNum = itemView.findViewById(R.id.fragment_to_trip_rv_tv_people_num);
            tvPriceDetails = itemView.findViewById(R.id.fragment_to_trip_rv_tv_price_details);
            tvPrice = itemView.findViewById(R.id.fragment_to_trip_rv_tv_price);
            tvCancelTrip = itemView.findViewById(R.id.fragment_to_trip_rv_tv_cancle_trip);
        }
    }

    public interface OnCancelListener{
        void onCancel(int position);
    }

}
