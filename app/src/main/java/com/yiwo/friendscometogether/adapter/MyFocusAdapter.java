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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.pages.InvitationActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MyFocusAdapter extends RecyclerView.Adapter<MyFocusAdapter.ViewHolder> {

    private Context context;
    private List<UserFocusModel.ObjBean> data;
    private OnFocusCancelListener listener;
    private OnFocusInvitationListener listener1;

    public void setOnInvitationListener(OnFocusInvitationListener listener1){
        this.listener1 = listener1;
    }

    public void setOnFocusCancelListener(OnFocusCancelListener listener) {
        this.listener = listener;
    }

    public MyFocusAdapter(List<UserFocusModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_focus, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(data.get(position).getUpicurl())) {
            Picasso.with(context).load(data.get(position).getUpicurl()).into(holder.ivAvatar);
        } else {
            Picasso.with(context).load(R.mipmap.my_head).into(holder.ivAvatar);
        }
        holder.tvNickname.setText("昵称: " + data.get(position).getUsername());
        Picasso.with(context).load(data.get(position).getUsersex().equals("0") ? R.mipmap.nan : R.mipmap.nv).into(holder.ivSex);
        holder.tvArticleNum.setText("文章: " + data.get(position).getArticle_num());
        holder.tvLikeNum.setText("粉丝: " + data.get(position).getLike_num());
        holder.tvActivityId.setText(data.get(position).getActivity_id().equals("0") ? "活动状态: 未参加活动" : "活动状态: 活动中");
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel(position);
            }
        });
        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener1.onInvitation(position);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("uid", data.get(position).getLikeuserID());
                intent.setClass(context, OtherInformationActivity.class);
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
        private TextView tvNickname;
        private ImageView ivSex;
        private TextView tvArticleNum;
        private TextView tvLikeNum;
        private TextView tvActivityId;
        private TextView tv2;
        private TextView tv1;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.activity_my_focus_rv_iv_avatar);
            tvNickname = itemView.findViewById(R.id.activity_my_focus_rv_tv_nickname);
            ivSex = itemView.findViewById(R.id.activity_my_focus_rv_iv_sex);
            tvArticleNum = itemView.findViewById(R.id.activity_my_focus_rv_tv_article_num);
            tvLikeNum = itemView.findViewById(R.id.activity_my_focus_rv_tv_like_num);
            tvActivityId = itemView.findViewById(R.id.activity_my_focus_rv_tv_activity_id);
            tv2 = itemView.findViewById(R.id.activity_my_focus_rv_tv_2);
            tv1 = itemView.findViewById(R.id.activity_my_focus_rv_tv_1);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnFocusInvitationListener{
        void onInvitation(int position);
    }

    public interface OnFocusCancelListener {
        void onCancel(int position);
    }

}
