package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yiwo.friendscometogether.model.ActiveCommentModel;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MyActiveCommentAdapter extends RecyclerView.Adapter<MyActiveCommentAdapter.ViewHolder> {

    private Context context;
    private List<ActiveCommentModel.ObjBean> data;
    private MyActiveCommentAdapter.OnReplyListener listener;

    public void setOnReplyListener(MyActiveCommentAdapter.OnReplyListener listener){
        this.listener = listener;
    }
    public MyActiveCommentAdapter(List<ActiveCommentModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_active_comment, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (StringUtils.isEmpty(data.get(position).getPic())) {
            Picasso.with(context).load(R.mipmap.my_head).into(holder.ivAvatar);
        } else {
            Picasso.with(context).load(data.get(position).getPic()).into(holder.ivAvatar);
        }
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvTime.setText(data.get(position).getTime());
//        holder.tvReply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onReply(position, data.get(position).getPfID());
//            }
//        });
        if(data.get(position).getList().size() == 0){
            holder.tvComment.setText("暂无评价");
        }
        if (data.get(position).getList().size() > 0) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(manager);
            MyActiveCommentCommentAdapter adapter = new MyActiveCommentCommentAdapter(data.get(position).getList());
            holder.recyclerView.setAdapter(adapter);
            adapter.setOnReplyCommentListener(new MyActiveCommentCommentAdapter.OnReplyCommentListener() {
                @Override
                public void onReplyComment(String ID) {
                    listener.onReply(position, ID);
                }
            });
        }else {
            holder.recyclerView.setVisibility(View.GONE);
        }
        holder.rlFriendRemember.setOnClickListener(new View.OnClickListener() {
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

        private ImageView ivAvatar;
//        private TextView tvReply;
        private TextView tvTitle;
        private TextView tvTime;
        private RecyclerView recyclerView;
        private RelativeLayout rlFriendRemember;
        private TextView tvComment;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.activity_active_comment_rv_iv);
//            tvReply = itemView.findViewById(R.id.activity_active_comment_rv_reply_tv);
            tvTitle = itemView.findViewById(R.id.activity_active_comment_rv_title_tv);
            tvTime = itemView.findViewById(R.id.activity_active_comment_rv_time_tv);
            recyclerView = itemView.findViewById(R.id.activity_active_comment_rv_rv);
            rlFriendRemember = itemView.findViewById(R.id.rl_friend_remember);
            tvComment = itemView.findViewById(R.id.activity_active_comment_rv_tv);
        }
    }
    public interface OnReplyListener{
        void onReply(int position, String id);
    }
}
