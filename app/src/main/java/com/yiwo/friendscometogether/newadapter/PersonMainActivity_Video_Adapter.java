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
import com.yiwo.friendscometogether.newmodel.PersonMain_Videos_Model;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.VideoActivity;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class PersonMainActivity_Video_Adapter extends RecyclerView.Adapter<PersonMainActivity_Video_Adapter.ViewHolder>{

    private List<PersonMain_Videos_Model.ObjBean> data;
    private Context context;
    public PersonMainActivity_Video_Adapter(List<PersonMain_Videos_Model.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_main_video_item, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youji_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        PersonMainActivity_Video_Adapter.ViewHolder holder = new PersonMainActivity_Video_Adapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_video);
        holder.video_tv_title.setText(data.get(position).getVname());
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
        private RelativeLayout rv_video;
        private ImageView iv_video;

        private TextView video_tv_title;
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            rv_video = itemView.findViewById(R.id.rv_video);
            iv_video = itemView.findViewById(R.id.iv_video);

            video_tv_title = itemView.findViewById(R.id.video_tv_title);
        }
    }
}
