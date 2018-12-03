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
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.MyFocusActiveModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class MyFocusActiveAdapter extends RecyclerView.Adapter<MyFocusActiveAdapter.ViewHolder> {

    private Context context;
    private List<MyFocusActiveModel.ObjBean> data;

    public MyFocusActiveAdapter(List<MyFocusActiveModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_collection, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(!TextUtils.isEmpty(data.get(position).getPfpic())){
            Glide.with(context).load(data.get(position).getPfpic()).into(holder.iv);
        }
        holder.tvTitle.setText(data.get(position).getPftitle());
        holder.tvTime.setText(data.get(position).getPfgotime());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                if (TextUtils.isEmpty(data.get(position).getPfpwd())) {
                    intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                    intent.putExtra("pfID", data.get(position).getPfID());
                    context.startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public void setActivityText(String s) {
                            if (s.equals(data.get(position).getPfpwd())) {
                                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                                intent.putExtra("pfID", data.get(position).getPfID());
                                context.startActivity(intent);
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvTime;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_my_collection_rv_iv);
            tvTitle = itemView.findViewById(R.id.activity_my_collection_rv_tv_title);
            tvTime = itemView.findViewById(R.id.activity_my_collection_rv_tv_time);
            ll = itemView.findViewById(R.id.activity_my_collection_rv_ll);
        }
    }

}
