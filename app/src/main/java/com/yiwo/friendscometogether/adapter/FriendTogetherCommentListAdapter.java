package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/18.
 */

public class FriendTogetherCommentListAdapter extends RecyclerView.Adapter<FriendTogetherCommentListAdapter.ViewHolder> {

    private Context context;
    private List<FriendsTogetherDetailsModel.ObjBean.CommentListBean> data;

    public FriendTogetherCommentListAdapter(List<FriendsTogetherDetailsModel.ObjBean.CommentListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friend_together_comment_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(!TextUtils.isEmpty(data.get(position).getUserpic())){
            Picasso.with(context).load(data.get(position).getUserpic()).into(holder.iv);
        }
        holder.tvNickname.setText("昵称: "+data.get(position).getUsername());
        holder.tvContent.setText(data.get(position).getPctitle());
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("uid", data.get(position).getUserID());
                intent.setClass(context, OtherInformationActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tvNickname;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.avatar);
            tvNickname = itemView.findViewById(R.id.nickname);
            tvContent = itemView.findViewById(R.id.content);
        }
    }

}
