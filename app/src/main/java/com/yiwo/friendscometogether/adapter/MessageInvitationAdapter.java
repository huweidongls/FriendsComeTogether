package com.yiwo.friendscometogether.adapter;

import android.content.Context;
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
import com.yiwo.friendscometogether.model.MessageInvitationListModel;

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
        if(!TextUtils.isEmpty(data.get(position).getPfpic())){
            Picasso.with(context).load(data.get(position).getPfpic()).into(holder.picIv);
        }
        holder.titleTv.setText(data.get(position).getPftitle());
        holder.contentTv.setText(data.get(position).getPfcontent());
        holder.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onApply(0, position);
            }
        });
        holder.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onApply(1, position);
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
        private TextView contentTv;
        private TextView tvOk;
        private TextView tvNo;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (itemView).findViewById(R.id.message_view_title_tv);
            picIv = (itemView).findViewById(R.id.message_view_pic_iv);
            contentTv = (itemView).findViewById(R.id.message_view_content_tv);
            tvOk = itemView.findViewById(R.id.tv_ok);
            tvNo = itemView.findViewById(R.id.tv_no);
        }
    }

    public interface OnApplyListener{
        void onApply(int type, int position);
    }

}
