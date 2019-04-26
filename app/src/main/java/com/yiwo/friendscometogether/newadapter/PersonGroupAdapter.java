package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yiwo.friendscometogether.newmodel.PersonGroupModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/4/26.
 */

public class PersonGroupAdapter extends RecyclerView.Adapter<PersonGroupAdapter.ViewHolder> {
    private List<PersonGroupModel.ObjBean> data;
    private Context context;
    public  PersonGroupAdapter(List<PersonGroupModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_group, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        PersonGroupAdapter.ViewHolder holder = new PersonGroupAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_head);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PersonMainActivity.class);
                intent.putExtra("person_id",data.get(position).getUserID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("adsasdas",data.size()+"");
        return data!=null?data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_head;
        private TextView tv_name;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            rl = itemView.findViewById(R.id.rl);
        }
    }
}
