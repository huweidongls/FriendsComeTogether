package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class YouJiAdapter extends RecyclerView.Adapter<YouJiAdapter.ViewHolder>{

    private List<YouJiListModel.ObjBean> data;
    private Context context;
    public YouJiAdapter(List<YouJiListModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        YouJiAdapter.ViewHolder holder = new YouJiAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (data.get(position).getType().equals("1")){
//            ViewGroup.LayoutParams layoutParams = holder.rv_youji.getLayoutParams();
//            layoutParams.height = 546;//获取最终图片高度
//            holder.rv_youji.setLayoutParams(layoutParams);//应用高度到布局中
            holder.rv_youji.setVisibility(View.VISIBLE);
            holder.rv_video.setVisibility(View.GONE);
            Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_youji);
            holder.tvYoujiTitle.setText(data.get(position).getFmtitle());
            Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.ivAvatar);
            holder.tvUsername.setText(data.get(position).getUsername());
            holder.tvTime.setText(data.get(position).getFmtime());
            holder.tvGoodNum.setText(data.get(position).getFmgood());
        }else {
//            ViewGroup.LayoutParams layoutParams = holder.rv_video.getLayoutParams();
//            layoutParams.height = 458;//获取最终图片高度
//            holder.rv_video.setLayoutParams(layoutParams);//应用高度到布局中
            holder.rv_video.setVisibility(View.VISIBLE);
            holder.rv_youji.setVisibility(View.GONE);
            Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_video);
        }

        holder.rv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, VideoActivity.class);
                it.putExtra("videoUrl", data.get(position).getVurl());
                it.putExtra("title", data.get(position).getFmtitle());
                it.putExtra("picUrl", data.get(position).getFmpic());
                it.putExtra("vid", data.get(position).getFmID());
                context.startActivity(it);
            }
        });

        holder.rv_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.get(position).getAccesspassword())) {
                    Intent intent = new Intent();
                    intent.setClass(context, DetailsOfFriendsWebActivity.class);
                    intent.putExtra("fmid", data.get(position).getFmID());
                    context.startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public boolean setActivityText(String s) {
                            if (s.equals(data.get(position).getAccesspassword())) {
                                Intent intent = new Intent();
                                intent.setClass(context, DetailsOfFriendsWebActivity.class);
                                intent.putExtra("fmid", data.get(position).getFmID());
                                context.startActivity(intent);
                                return true;
                            }else {
                                Toast.makeText(context,"密码错误",Toast.LENGTH_SHORT).show();
                                return false;
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rv_youji;
        private RelativeLayout rv_video;
        private ImageView iv_youji;
        private ImageView iv_video;
        private TextView tvYoujiTitle;
        private ImageView ivAvatar;
        private TextView tvUsername;
        private TextView tvTime;
        private TextView tvGoodNum;
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            rv_video = itemView.findViewById(R.id.rv_video);
            rv_youji = itemView.findViewById(R.id.rv_youji);
            iv_youji = itemView.findViewById(R.id.iv_youju);
            iv_video = itemView.findViewById(R.id.iv_video);
            tvYoujiTitle = itemView.findViewById(R.id.tv_youji_title);
            ivAvatar = itemView.findViewById(R.id.iv_icon_user);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvGoodNum = itemView.findViewById(R.id.tv_good_num);
        }
    }
}
