package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.TeamIntercalationModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class TeamIntercalationAdapter extends RecyclerView.Adapter<TeamIntercalationAdapter.ViewHolder> {

    private Context context;
    private List<TeamIntercalationModel.ObjBean> data;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public TeamIntercalationAdapter(List<TeamIntercalationModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_team_intercalation, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFfptitle());
        if(!TextUtils.isEmpty(data.get(position).getFfpurl())){
            Picasso.with(context).load(data.get(position).getFfpurl()).into(holder.ivTitle);
        }
        if(TextUtils.isEmpty(data.get(position).getUserpic())){
            Picasso.with(context).load(R.mipmap.my_head).into(holder.ivAvatar);
        }else {
            Picasso.with(context).load(data.get(position).getUserpic()).into(holder.ivAvatar);
        }
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvCreateTime.setText("发布时间: "+data.get(position).getFfptime());
        holder.tvState.setText(data.get(position).getRadio());
        holder.llShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getRadio().equals("待审核")||data.get(position).getRadio().equals("已屏蔽")){
                    listener.onClick(1, position);
                }
            }
        });
        holder.llshield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getRadio().equals("允许展示!")){
                    listener.onClick(2, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivTitle;
        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvCreateTime;
        private TextView tvState;
        private LinearLayout llShow;
        private LinearLayout llshield;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_team_intercalation_rv_tv_title);
            ivTitle = itemView.findViewById(R.id.activity_team_intercalation_rv_iv);
            ivAvatar = itemView.findViewById(R.id.activity_team_intercalation_rv_iv_avatar);
            tvNickname = itemView.findViewById(R.id.activity_team_intercalation_rv_tv_nickname);
            tvCreateTime = itemView.findViewById(R.id.activity_team_intercalation_rv_tv_c_time);
            tvState = itemView.findViewById(R.id.activity_team_intercalation_rv_tv_state);
            llShow = itemView.findViewById(R.id.activity_team_intercalation_rv_ll_show);
            llshield = itemView.findViewById(R.id.activity_team_intercalation_rv_ll_shield);
        }
    }

    public interface OnItemClickListener{
        void onClick(int type, int position);
    }

}
