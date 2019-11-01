package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.PersonMain_Pics_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class PersonMainActivity_Pics_Adapter extends RecyclerView.Adapter<PersonMainActivity_Pics_Adapter.ViewHolder>{

    private List<PersonMain_Pics_model.ObjBean.PhotoBean> data;
    private Context context;
    public PersonMainActivity_Pics_Adapter(List<PersonMain_Pics_model.ObjBean.PhotoBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_main_pics, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youju_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        PersonMainActivity_Pics_Adapter.ViewHolder holder = new PersonMainActivity_Pics_Adapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getUpicurl()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i<data.size(); i++){
                    urlList.add(data.get(i).getUpicurl());
                }
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, position);
                intent.putExtra(Consts.START_IAMGE_POSITION, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }
}
