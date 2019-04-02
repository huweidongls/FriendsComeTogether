package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MessageInvitationListModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

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
            Glide.with(context).load(data.get(position).getPfpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian)).into(holder.picIv);
        }
        holder.tvTime.setText(data.get(position).getYqtime());
        holder.titleTv.setText(data.get(position).getPftitle());
//        holder.contentTv.setText(data.get(position).getPfcontent());
        if (data.get(position).getNo_name().equals("0")){//不是匿名邀请
            if(data.get(position).getSex().equals("0")){
                String str = "哇哦！您收到来自<font color='#0765AA'>"+"@"+data.get(position).getUsername()+"</font>帅哥的活动邀请。";
                holder.tvNickname.setText(Html.fromHtml(str));
            }else {
                String str = "哇哦！您收到来自<font color='#0765AA'>"+"@"+data.get(position).getUsername()+"</font>美女的活动邀请。";
                holder.tvNickname.setText(Html.fromHtml(str));
            }
            holder.tvNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("person_id", data.get(position).getTid());
                    intent.setClass(context, PersonMainActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if (data.get(position).getNo_name().equals("1")){
            String str = "哇哦！您收到来自<font color='#0765AA'>"+"@"+"神秘人"+"</font>的活动邀请。";
            holder.tvNickname.setText(Html.fromHtml(str));
        }

        holder.llNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定拒绝邀请？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onApply(0, position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });
        holder.llYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定接收邀请？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onApply(1, position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        holder.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("pfID", data.get(position).getTid());
                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                context.startActivity(intent);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("pfID", data.get(position).getId());
                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
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

        private LinearLayout ll;

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
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnApplyListener{
        void onApply(int type, int position);
    }
}
