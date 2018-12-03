package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserIntercalationListModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/23.
 */

public class MyIntercalationAdapter extends RecyclerView.Adapter<MyIntercalationAdapter.ViewHolder> {

    private Context context;
    private List<UserIntercalationListModel.ObjBean> data;
    private OnDeleteListener listener;

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public MyIntercalationAdapter(List<UserIntercalationListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_intercalation, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFfptitle());
        holder.tvState.setText(data.get(position).getState());
        holder.tvReason.setText(data.get(position).getReason());
        Picasso.with(context).load(data.get(position).getFfpurl()).into(holder.iv);
        holder.tvLocation.setText("插文位置: " + data.get(position).getPosition());
        holder.tvActivity.setText("参与的活动: " + data.get(position).getActivity_name());
        holder.tvTime.setText("上次编辑时间: " + data.get(position).getFfptime());
        holder.tvRememberTitle.setText("文章标题: " + data.get(position).getNewstitle());
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvState;
        private TextView tvReason;
        private ImageView iv;
        private TextView tvLocation;
        private TextView tvActivity;
        private TextView tvTime;
        private LinearLayout llDelete;
        private TextView tvRememberTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_title);
            tvState = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_state);
            tvReason = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_reason);
            iv = itemView.findViewById(R.id.activity_my_intercalation_rv_iv);
            tvLocation = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_location);
            tvActivity = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_activity);
            tvTime = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_time);
            llDelete = itemView.findViewById(R.id.activity_my_intercalation_rv_ll_delete);
            tvRememberTitle = itemView.findViewById(R.id.activity_my_intercalation_rv_tv_remember_title);
        }
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }

}
