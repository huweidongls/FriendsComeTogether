package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by ljc on 2019/11/28.
 */

public class HomeDataRecommendLiveListAdapter extends RecyclerView.Adapter<HomeDataRecommendLiveListAdapter.ViewHolder> {

    private Context context;
    private List<HomeDataModel1.ObjBean.ZhiboBean> data;
    private SpImp spImp;
    private LiveListAdapterListener listener;
    public HomeDataRecommendLiveListAdapter(List<HomeDataModel1.ObjBean.ZhiboBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        HomeDataRecommendLiveListAdapter.ViewHolder holder = new HomeDataRecommendLiveListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon);
        if (data.get(position).getZhibostatus().equals("1")){
            holder.tv_live_state.setText("直播中");
        }else {
            holder.tv_live_state.setText("待直播");
        }
        String time_start = data.get(position).getPfStart();
        String time_end = data.get(position).getPfEnd();
        if (((!TextUtils.isEmpty(time_start)) && time_start.length()>2) && ((!TextUtils.isEmpty(time_end)) && time_end.length()>2)){
            holder.tv_address.setText("["+time_start.substring(time_start.length()-2,time_start.length())+"-"+time_end.substring(time_start.length()-2,time_start.length())+"]"+data.get(position).getZhiboAddress());
        }else {
            holder.tv_address.setText(data.get(position).getZhiboAddress());
        }
        holder.tv_live_person.setText(data.get(position).getUsername());
        holder.btn_gaunzhu.setText(data.get(position).getLike().equals("1")?"已关注":"关注");
        if (listener!= null){
            holder.btn_gaunzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onGuanZhuListen(position);
                }
            });
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCLickListen(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListener(LiveListAdapterListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll;
        ImageView iv_icon;
        TextView tv_live_state;
        TextView tv_address;
        TextView tv_live_person;
        TextView btn_gaunzhu;
        public ViewHolder(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_live_state = itemView.findViewById(R.id.tv_live_state);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_live_person = itemView.findViewById(R.id.tv_live_person);
            btn_gaunzhu = itemView.findViewById(R.id.btn_gaunzhu);
        }
    }
    public interface LiveListAdapterListener{
        void onCLickListen(int pos);
        void onGuanZhuListen(int pos);
    }
}
