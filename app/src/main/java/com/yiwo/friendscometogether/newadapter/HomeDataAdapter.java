package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */

public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.ViewHolder> {

    private Context context;
    private List<HomeDataModel.ObjBean> data;

    public HomeDataAdapter(List<HomeDataModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_home_rv_new, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(data.get(position).getData_type().equals("1")){
            holder.llYouju.setVisibility(View.VISIBLE);
            holder.llYouji.setVisibility(View.GONE);
            Glide.with(context).load(data.get(position).getHeadportrait()).into(holder.ivYoujuAvatar);
            holder.tvYoujuName.setText(data.get(position).getUsername());
            holder.tvYoujuTime.setText(data.get(position).getPftime());
            if(data.get(position).getFollow().equals("1")){
                holder.tvYoujuFocus.setText("已关注");
            }else {
                holder.tvYoujuFocus.setText("+关注");
            }
            Glide.with(context).load(data.get(position).getPfpic().get(0)).into(holder.ivYoujuTitle);
            holder.tvYoujuTitle.setText(data.get(position).getPftitle());
            holder.tvYoujuContent.setText(data.get(position).getPfcontent());
            holder.tvYoujuAddress.setText(data.get(position).getPfaddress());
            holder.tvYoujuLookNum.setText(data.get(position).getPflook());
        }else {
            holder.llYouju.setVisibility(View.GONE);
            holder.llYouji.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getHeadportrait()).into(holder.ivYoujiAvatar);
            holder.tvYoujiNickname.setText(data.get(position).getUsername());
            holder.tvYoujiTime.setText(data.get(position).getPftime());
            if(data.get(position).getFollow().equals("1")){
                holder.tvYoujiFocus.setText("已关注");
            }else {
                holder.tvYoujiFocus.setText("+关注");
            }
            Glide.with(context).load(data.get(position).getPfpic().get(0)).into(holder.ivYoujiTitle1);
            Glide.with(context).load(data.get(position).getPfpic().get(1)).into(holder.ivYoujiTitle2);
            Glide.with(context).load(data.get(position).getPfpic().get(2)).into(holder.ivYoujiTitle3);
            Glide.with(context).load(data.get(position).getPfpic().get(3)).into(holder.ivYoujiTitle4);
            holder.tvYoujiTitle.setText(data.get(position).getPftitle());
            holder.tvYoujiAddress.setText(data.get(position).getPfaddress());
            holder.tvYoujiLookNum.setText(data.get(position).getPflook());
            holder.tvYoujiCommentNum.setText(data.get(position).getCommentcount());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llYouju;
        private LinearLayout llYouji;
        private ImageView ivYoujuAvatar;
        private TextView tvYoujuName;
        private TextView tvYoujuTime;
        private TextView tvYoujuFocus;
        private ImageView ivYoujuTitle;
        private TextView tvYoujuTitle;
        private TextView tvYoujuContent;
        private TextView tvYoujuAddress;
        private TextView tvYoujuLookNum;
        private ImageView ivYoujiAvatar;
        private TextView tvYoujiNickname;
        private TextView tvYoujiTime;
        private TextView tvYoujiFocus;
        private ImageView ivYoujiTitle1;
        private ImageView ivYoujiTitle2;
        private ImageView ivYoujiTitle3;
        private ImageView ivYoujiTitle4;
        private TextView tvYoujiTitle;
        private TextView tvYoujiAddress;
        private TextView tvYoujiLookNum;
        private TextView tvYoujiCommentNum;

        public ViewHolder(View itemView) {
            super(itemView);
            llYouju = itemView.findViewById(R.id.ll_youju);
            llYouji = itemView.findViewById(R.id.ll_youji);
            ivYoujuAvatar = itemView.findViewById(R.id.iv_youju_avatar);
            tvYoujuName = itemView.findViewById(R.id.tv_youju_name);
            tvYoujuTime = itemView.findViewById(R.id.tv_youju_time);
            tvYoujuFocus = itemView.findViewById(R.id.tv_youju_focus);
            ivYoujuTitle = itemView.findViewById(R.id.iv_youju_title);
            tvYoujuTitle = itemView.findViewById(R.id.tv_youju_title);
            tvYoujuContent = itemView.findViewById(R.id.tv_youju_content);
            tvYoujuAddress = itemView.findViewById(R.id.tv_youju_address);
            tvYoujuLookNum = itemView.findViewById(R.id.tv_youju_look_num);
            ivYoujiAvatar = itemView.findViewById(R.id.iv_youji_avatar);
            tvYoujiNickname = itemView.findViewById(R.id.tv_youji_nickname);
            tvYoujiTime = itemView.findViewById(R.id.tv_youji_time);
            tvYoujiFocus = itemView.findViewById(R.id.tv_youji_focus);
            ivYoujiTitle1 = itemView.findViewById(R.id.iv_youji_title1);
            ivYoujiTitle2 = itemView.findViewById(R.id.iv_youji_title2);
            ivYoujiTitle3 = itemView.findViewById(R.id.iv_youji_title3);
            ivYoujiTitle4 = itemView.findViewById(R.id.iv_youji_title4);
            tvYoujiTitle = itemView.findViewById(R.id.tv_youji_title);
            tvYoujiAddress = itemView.findViewById(R.id.tv_youji_address);
            tvYoujiLookNum = itemView.findViewById(R.id.tv_youji_look_num);
            tvYoujiCommentNum = itemView.findViewById(R.id.tv_youji_comment_num);
        }
    }

}
