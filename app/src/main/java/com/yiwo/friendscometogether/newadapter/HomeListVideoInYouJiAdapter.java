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
import com.yiwo.friendscometogether.newmodel.HomeDataYouJiVideoModel;
import com.yiwo.friendscometogether.newmodel.HomeVideoListModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.VideoActivity;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class HomeListVideoInYouJiAdapter extends RecyclerView.Adapter<HomeListVideoInYouJiAdapter.ViewHolder>{

    private List<HomeDataYouJiVideoModel.ObjBean.VListBean> data;
    private Context context;
    public HomeListVideoInYouJiAdapter(List<HomeDataYouJiVideoModel.ObjBean.VListBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_video_in_youji_item, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        HomeListVideoInYouJiAdapter.ViewHolder holder = new HomeListVideoInYouJiAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_video);
        holder.video_tv_youji_title.setText(data.get(position).getVname());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.video_iv_icon_user);
        holder.video_iv_icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        holder.video_tv_username.setText(data.get(position).getUsername());
        holder.video_tv_time.setText(data.get(position).getVtime());
//        holder.video_tv_good_num.setText(data.get(position).getPraise_num());//改为浏览数量
        holder.video_tv_good_num.setText(data.get(position).getLook_num());
        holder.rv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, VideoActivity.class);
                it.putExtra("videoUrl", data.get(position).getVurl());
                it.putExtra("title", data.get(position).getVname());
                it.putExtra("picUrl", data.get(position).getImg());
                it.putExtra("vid", data.get(position).getVID());
                context.startActivity(it);
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
        private ImageView iv_video;

        private ImageView video_iv_icon_user;
        private TextView video_tv_username;
        private TextView video_tv_time;
        private TextView video_tv_good_num;
        private TextView video_tv_youji_title;
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            rv_video = itemView.findViewById(R.id.rv_video);
            rv_youji = itemView.findViewById(R.id.rv_youji);
            iv_video = itemView.findViewById(R.id.iv_video);

            video_iv_icon_user = itemView.findViewById(R.id.video_iv_icon_user);
            video_tv_username = itemView.findViewById(R.id.video_tv_username);
            video_tv_time = itemView.findViewById(R.id.video_tv_time);
            video_tv_good_num = itemView.findViewById(R.id.video_tv_good_num);
            video_tv_youji_title = itemView.findViewById(R.id.video_tv_youji_title);
        }
    }
}
