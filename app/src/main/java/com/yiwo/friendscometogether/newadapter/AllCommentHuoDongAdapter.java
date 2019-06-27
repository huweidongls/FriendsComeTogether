package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.ArticleCommentAdapter;
import com.yiwo.friendscometogether.adapter.ArticleCommentCommentAdapter;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.newmodel.AllCommentHuoDongModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;

import java.util.List;

/**
 * Created by ljc on 2019/4/7.
 */

public class AllCommentHuoDongAdapter extends RecyclerView.Adapter<AllCommentHuoDongAdapter.ViewHolder>{

    private Context context;
    private List<AllCommentHuoDongModel.ObjBean> data;


    public AllCommentHuoDongAdapter(List<AllCommentHuoDongModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public AllCommentHuoDongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_all_comment_huodong, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        AllCommentHuoDongAdapter.ViewHolder holder = new AllCommentHuoDongAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AllCommentHuoDongAdapter.ViewHolder holder, final int position) {
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head)).into(holder.ivAvatar);
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvContent.setText(data.get(position).getPctitle());
        holder.tvTime.setText(data.get(position).getFctime());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvContent;
        private TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_head);
            tvNickname = itemView.findViewById(R.id.tv_user_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
