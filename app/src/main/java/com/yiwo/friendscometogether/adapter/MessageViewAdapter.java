package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MessageViewModel;
import com.yiwo.friendscometogether.pages.MessageCenterDetailsActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.ViewHolder> {
    private Context context;
    private List<MessageViewModel.ObjBean> data;
    SpImp spImp;

    public MessageViewAdapter(List<MessageViewModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_view, parent, false);
        this.context = parent.getContext();
        spImp = new SpImp(parent.getContext());
        ScreenAdapterTools.getInstance().loadView(view);
        MessageViewAdapter.ViewHolder holder = new MessageViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titleTv.setText(data.get(position).getMes_title());
        if(!StringUtils.isEmpty(data.get(position).getMes_pic()))
            Picasso.with(context).load(data.get(position).getMes_pic()).into(holder.picIv);
        holder.contentTv.setText(data.get(position).getMes_message());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MessageCenterDetailsActivity.class);
                it.putExtra("mesid",data.get(position).getMes_id());
                context.startActivity(it);

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
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (itemView).findViewById(R.id.message_view_title_tv);
            picIv = (itemView).findViewById(R.id.message_view_pic_iv);
            contentTv = (itemView).findViewById(R.id.message_view_content_tv);
            ll = (itemView).findViewById(R.id.item_ll);
        }
    }
}
