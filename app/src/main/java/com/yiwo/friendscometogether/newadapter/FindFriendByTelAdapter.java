package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.FindFriendByTelModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;

import java.util.List;

/**
 * Created by ljc on 2019/4/24.
 */

public class FindFriendByTelAdapter extends RecyclerView.Adapter<FindFriendByTelAdapter.ViewHolder> {
    private List<FindFriendByTelModel.ObjBean> data;
    private Context context;
    private AddFriendListenner addFriendListenner;

    public FindFriendByTelAdapter(List<FindFriendByTelModel.ObjBean> data){
        this.data = data;
    }
    public void setAddFriendListenner(AddFriendListenner listenner){
        this.addFriendListenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_find_friend_by_tel, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        FindFriendByTelAdapter.ViewHolder holder = new FindFriendByTelAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PersonMainActivity.class);
                intent.putExtra("person_id",data.get(position).getUserID());
                context.startActivity(intent);
            }
        });
        holder.tv_name.setText(data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_avatar);
        if (data.get(position).getStatus().equals("0")){
            holder.tv_add_friend.setVisibility(View.VISIBLE);
        }else if (data.get(position).getStatus().equals("1")){
            holder.tv_add_friend.setVisibility(View.GONE);
        }
        holder.tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendListenner.addFriendListen(data.get(position).getUserID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rl;
        private TextView tv_name;
        private TextView tv_add_friend;
        private ImageView iv_avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            rl = itemView.findViewById(R.id.rl);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_add_friend = itemView.findViewById(R.id.tv_add_friend);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
        }
    }
    public interface AddFriendListenner{
        void addFriendListen(String userID);
    }
}
