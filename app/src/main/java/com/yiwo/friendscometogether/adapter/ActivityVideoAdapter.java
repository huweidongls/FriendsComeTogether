package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.VideoActiveModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class ActivityVideoAdapter extends RecyclerView.Adapter<ActivityVideoAdapter.ViewHolder> {

    private Context context;
    private List<VideoActiveModel.ObjBean> data;

    public ActivityVideoAdapter(List<VideoActiveModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_activity_video, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getPftitle());
        if(!TextUtils.isEmpty(data.get(position).getPfpic())){
            Glide.with(context).load(data.get(position).getPfpic()).into(holder.iv);
        }
        holder.tvContent.setText(data.get(position).getPfcontent());
        holder.time.setText(data.get(position).getPftime());
        holder.lookNum.setText(data.get(position).getPflook());
        holder.commentNum.setText(data.get(position).getPfcomment());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("pfID", data.get(position).getPfID());
                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView iv;
        private TextView tvContent;
        private TextView time;
        private TextView lookNum;
        private TextView commentNum;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            iv = itemView.findViewById(R.id.iv);
            tvContent = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            lookNum = itemView.findViewById(R.id.look_num);
            commentNum = itemView.findViewById(R.id.comment_num);
            ll = itemView.findViewById(R.id.ll);
        }
    }

}
