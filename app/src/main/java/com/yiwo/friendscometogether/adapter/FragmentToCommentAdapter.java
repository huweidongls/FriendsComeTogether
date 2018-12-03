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
import com.yiwo.friendscometogether.model.CommentFragmentModel;
import com.yiwo.friendscometogether.pages.DetailsToBePaidActivity;
import com.yiwo.friendscometogether.pages.DetailsToCommentActivity;
import com.yiwo.friendscometogether.pages.OrderCommentActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class FragmentToCommentAdapter extends RecyclerView.Adapter<FragmentToCommentAdapter.ViewHolder> {

    private Context context;
    private List<CommentFragmentModel.ObjBean> data;
    private Activity activity;
    private OnDeleteListener listener;

    public void setOnDeleteListener(OnDeleteListener listener){
        this.listener = listener;
    }

    public FragmentToCommentAdapter(List<CommentFragmentModel.ObjBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_to_comment, parent, false);
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
        if(data.get(position).getOrder_type().equals("6")){
            holder.tvTriping.setVisibility(View.VISIBLE);
            holder.tvComment.setVisibility(View.GONE);
            holder.tvDeleteTrip.setVisibility(View.GONE);
        }else if(data.get(position).getOrder_type().equals("3")){
            holder.tvTriping.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.VISIBLE);
            holder.tvDeleteTrip.setVisibility(View.VISIBLE);
        }
        holder.tvDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(position);
            }
        });
        holder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, OrderCommentActivity.class);
                intent.putExtra("type", "0");
                intent.putExtra("orderid", data.get(position).getOID());
                context.startActivity(intent);
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
        private TextView tvTriping;
        private TextView tvComment;
        private TextView tvDeleteTrip;

        public ViewHolder(View itemView) {
            super(itemView);
            rlDetails = itemView.findViewById(R.id.fragment_to_comment_rv_rl_details);
            tvTitle = itemView.findViewById(R.id.fragment_to_comment_rv_tv_title);
            tvStatus = itemView.findViewById(R.id.fragment_to_comment_rv_tv_status);
            iv = itemView.findViewById(R.id.fragment_to_comment_rv_iv);
            tvContent = itemView.findViewById(R.id.fragment_to_comment_rv_tv_content);
            tvTime = itemView.findViewById(R.id.fragment_to_comment_rv_tv_time);
            tvPeopleNum = itemView.findViewById(R.id.fragment_to_comment_rv_tv_people_num);
            tvPriceDetails = itemView.findViewById(R.id.fragment_to_comment_rv_tv_price_details);
            tvPrice = itemView.findViewById(R.id.fragment_to_comment_rv_tv_price);
            tvTriping = itemView.findViewById(R.id.fragment_to_comment_rv_tv_triping);
            tvComment = itemView.findViewById(R.id.fragment_to_comment_rv_tv_payment);
            tvDeleteTrip = itemView.findViewById(R.id.fragment_to_comment_rv_tv_cancle_trip);
        }
    }

    public interface OnDeleteListener{
        void onDelete(int position);
    }

}
