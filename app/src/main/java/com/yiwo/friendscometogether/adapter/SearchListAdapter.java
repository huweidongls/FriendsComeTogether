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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.SearchListModel;
import com.yiwo.friendscometogether.pages.SearchListActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private Context context;
    private List<SearchListModel.ObjBean> data;
    private SearchListActivity ac;
    public SearchListAdapter(List<SearchListModel.ObjBean> data,SearchListActivity ac) {
        this.ac = ac;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_editor_friend_together, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getTitle());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_icon);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                if(data.get(position).getType().equals("0")){
                    it.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                    it.putExtra("pfID",data.get(position).getId());
                    context.startActivity(it);
                } else if (data.get(position).getType().equals("1")){
                    it.setClass(context, DetailsOfFriendsWebActivity2.class);
                    it.putExtra("fmid",data.get(position).getId());
                    context.startActivity(it);
                }else if (data.get(position).getType().equals("2")) {
                    it.setClass(context, VideoActivity.class);
                    it.putExtra("videoUrl", data.get(position).getVurl());
                    it.putExtra("title", data.get(position).getTitle());
                    it.putExtra("picUrl", data.get(position).getVimg());
                    it.putExtra("vid", data.get(position).getId());
                    context.startActivity(it);
                }
//                ac.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private RelativeLayout rl;
        private ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_editor_friend_together_rv_tv);
            rl = itemView.findViewById(R.id.activity_editor_friend_together_rv_rl);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }

}
