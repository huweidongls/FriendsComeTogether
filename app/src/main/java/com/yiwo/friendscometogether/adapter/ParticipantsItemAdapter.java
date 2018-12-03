package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class ParticipantsItemAdapter extends RecyclerView.Adapter<ParticipantsItemAdapter.ViewHolder>{
    private Context context;
    private List<FriendsTogetherDetailsModel.ObjBean.UserListBean> data;

    public ParticipantsItemAdapter(List<FriendsTogetherDetailsModel.ObjBean.UserListBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participants, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(data.size()!=0){
            if(!StringUtils.isEmpty(data.get(position).getUserpic())){
                Picasso.with(context).load(data.get(position).getUserpic()).into(holder.headIv);
            }
            holder.nicknameTv.setText(data.get(position).getUsername());
//            if(data.get(position).getNum().equals("1")){
//                holder.numTv.setVisibility(View.INVISIBLE);
//            } else {
//                holder.numTv.setText(data.get(position).getNum());
//            }
            holder.personRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.get(position).getUserID().equals("0")){
                        Toast.makeText(context, "无法查看匿名信息", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent it = new Intent(context, OtherInformationActivity.class);
                        it.putExtra("uid",data.get(position).getUserID());
                        context.startActivity(it);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView numTv;
        private ImageView headIv;
        private TextView nicknameTv;
        private RelativeLayout personRl;

        public ViewHolder(View itemView) {
            super(itemView);
            numTv = (itemView).findViewById(R.id.item_num);
            headIv = (itemView).findViewById(R.id.item_head);
            nicknameTv = (itemView).findViewById(R.id.item_nickname);
            personRl = (itemView).findViewById(R.id.item_onclick_rl);
        }
    }

}
