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
import com.yiwo.friendscometogether.model.PersonMain_Youji_model;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class PersonMainActivity_YouJi_Adapter extends RecyclerView.Adapter<PersonMainActivity_YouJi_Adapter.ViewHolder>{

    private List<PersonMain_Youji_model.ObjBean.FriendBean> data;
    private Context context;
    public PersonMainActivity_YouJi_Adapter(List<PersonMain_Youji_model.ObjBean.FriendBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_main_youji, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        PersonMainActivity_YouJi_Adapter.ViewHolder holder = new PersonMainActivity_YouJi_Adapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsWebActivity2.class);
                intent.putExtra("fmid", data.get(position).getPfID());
                context.startActivity(intent);
            }
        });
        holder.tv_name.setText(data.get(position).getPftitle());
        holder.tv_address.setText(data.get(position).getPfaddress());
        holder.tv_pinglun_num.setText(data.get(position).getFmcomment());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv_name;
        private TextView tv_pinglun_num;
        private TextView tv_address;
        private RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            rl_item = itemView.findViewById(R.id.rl_item);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_pinglun_num = itemView.findViewById(R.id.tv_pinglun_num);
        }
    }
}
