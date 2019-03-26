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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MessageFriendsModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class MessageFriendsAdapter extends RecyclerView.Adapter<MessageFriendsAdapter.ViewHolder> {

    private Context context;
    private List<MessageFriendsModel.ObjBean> data;
    private OnFriendsListener listener;

    public void setOnFriendsListener(OnFriendsListener listener){
        this.listener = listener;
    }

    public MessageFriendsAdapter(List<MessageFriendsModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_friends, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvUserName.setText(data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getPic()).apply(new RequestOptions().error(R.mipmap.my_head)).into(holder.picIv);
        switch (data.get(position).getType()){
            case "0"://好友申请
                holder.titleTv.setText("请求加为好友");
                holder.tvContent.setText("留言："+data.get(position).getDescribe());
                holder.tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onFriend(1, position);
                    }
                });
                holder.tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onFriend(2, position);
                    }
                });
                break;
            case "1"://接受了好友
                holder.titleTv.setText("同意了你的好友申请");
                holder.tvContent.setVisibility(View.GONE);
                holder.ll.setVisibility(View.GONE);
                break;
            case "2"://拒绝了好友
                holder.titleTv.setText("拒绝了你的好友申请");
                holder.tvContent.setVisibility(View.GONE);
                holder.ll.setVisibility(View.GONE);
                break;
        }
//        holder.picIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("uid", data.get(position).getUserID());
//                intent.setClass(context, OtherInformationActivity.class);
//                context.startActivity(intent);
//            }
//        });
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private TextView titleTv;
        private ImageView picIv;
        private TextView tvNo;
        private TextView tvOk;
        private TextView tvContent;
        private RelativeLayout rl;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.message_view_username_tv);
            titleTv = (itemView).findViewById(R.id.message_view_title_tv);
            picIv = (itemView).findViewById(R.id.message_view_pic_iv);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvOk = itemView.findViewById(R.id.tv_ok);
            tvContent = itemView.findViewById(R.id.message_view_content_tv);
            rl = itemView.findViewById(R.id.rl);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnFriendsListener{
        void onFriend(int type, int position);
    }

}
