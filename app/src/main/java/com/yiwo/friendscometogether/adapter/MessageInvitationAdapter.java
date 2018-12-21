package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MessageInvitationListModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class MessageInvitationAdapter extends RecyclerView.Adapter<MessageInvitationAdapter.ViewHolder> {

    private Context context;
    private List<MessageInvitationListModel.ObjBean> data;
    private OnApplyListener listener;

    public void setOnApplyListener(OnApplyListener listener){
        this.listener = listener;
    }

    public MessageInvitationAdapter(List<MessageInvitationListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_invitation, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(data.get(position).getSex().equals("0")){
            Glide.with(context).load(R.mipmap.yaoqing_nan).into(holder.ivSex);
        }else {
            Glide.with(context).load(R.mipmap.yaoqing_nv).into(holder.ivSex);
        }
        if(!TextUtils.isEmpty(data.get(position).getPfpic())){
            Glide.with(context).load(data.get(position).getPfpic()).into(holder.picIv);
        }
        holder.tvTime.setText(data.get(position).getYqtime());
        holder.titleTv.setText(data.get(position).getPftitle());
//        holder.contentTv.setText(data.get(position).getPfcontent());
        if(data.get(position).getSex().equals("0")){
            String str = "哇哦！您收到来自<font color='#0765AA'>"+"@"+data.get(position).getUsername()+"</font>帅哥的活动邀请。";
            holder.tvNickname.setText(Html.fromHtml(str));
        }else {
            String str = "哇哦！您收到来自<font color='#0765AA'>"+"@"+data.get(position).getUsername()+"</font>美女的活动邀请。";
            holder.tvNickname.setText(Html.fromHtml(str));
        }
        holder.llNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onApply(0, position);
            }
        });
        holder.llYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onApply(1, position);
            }
        });
        holder.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("pfID", data.get(position).getTid());
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
        private TextView titleTv;
        private ImageView picIv;
        private LinearLayout llDetails;
        private LinearLayout llYes;
        private LinearLayout llNo;
        private TextView tvNickname;
        private TextView tvTime;
        private ImageView ivSex;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (itemView).findViewById(R.id.message_view_title_tv);
            picIv = (itemView).findViewById(R.id.message_view_pic_iv);
            llDetails = itemView.findViewById(R.id.ll_huodong_details);
            llYes = itemView.findViewById(R.id.ll_yes);
            llNo = itemView.findViewById(R.id.ll_no);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivSex = itemView.findViewById(R.id.iv_sex);
        }
    }

    public interface OnApplyListener{
        void onApply(int type, int position);
    }

}
