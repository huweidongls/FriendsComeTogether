package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.SuperLikeModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class SuperLikeAdapter extends RecyclerView.Adapter<SuperLikeAdapter.ViewHolder>{

    private List<SuperLikeModel.ObjBean> data;
    private Context context;

    private SayHelloListener sayHelloListener;

    public void setSayHelloListener(SayHelloListener sayHelloListener) {
        this.sayHelloListener = sayHelloListener;
    }

    public interface  SayHelloListener{
        void sayHelloListen(int postion);
    }
    public SuperLikeAdapter(List<SuperLikeModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_super_like_item, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        SuperLikeAdapter.ViewHolder holder = new SuperLikeAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final List<String> list_biaoqian = new ArrayList<>();
        String[] strs = data.get(position).getLabel().split(",");
        if (strs!=null&&!strs[0].equals("")){
            for (int i = 0;i<strs.length;i++){
                list_biaoqian.add(strs[i]);
            }
        }
        Log.d("333",list_biaoqian.size()+"");
        FlowLayoutManager manager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.rv_label.setLayoutManager(manager);
        holder.rv_label.setAdapter(new SuperLikeLabelAdapter(list_biaoqian));
        holder.tv_username.setText(data.get(position).getUsername());
        holder.tv_age.setText(data.get(position).getUserbirthday()+"岁");
        holder.tv_degree.setText(data.get(position).getMatching_degree());
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.iv_icon_user);
        holder.iv_icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonMainActivity.class);
                intent.putExtra("person_id",data.get(position).getUserID());
                context.startActivity(intent);
            }
        });
        holder.tv_say_hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHelloListener.sayHelloListen(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private NestedRecyclerView rv_label;
        private ImageView iv_icon_user;
        private TextView tv_username;
        private TextView tv_age;
        private TextView tv_degree;
        private TextView tv_say_hello;
        public ViewHolder(View itemView) {
            super(itemView);
            rv_label = itemView.findViewById(R.id.rv_label);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_degree = itemView.findViewById(R.id.tv_degree);
            tv_say_hello = itemView.findViewById(R.id.tv_say_hello);
        }
    }
}
