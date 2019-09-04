package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.model.SearchListModel;
import com.yiwo.friendscometogether.pages.SearchListActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class SuoShuHuoDongListAdapter extends RecyclerView.Adapter<SuoShuHuoDongListAdapter.ViewHolder> {

    private Context context;
    private List<GetFriendActiveListModel.ObjBean> data;
    private ItemClickListionner listionner;
    public SuoShuHuoDongListAdapter(List<GetFriendActiveListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_suo_shuo_huo_dong, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getPftitle());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listionner.onClick(position);
//                ac.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListionner(ItemClickListionner listionner) {
        this.listionner = listionner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface ItemClickListionner{
        void onClick(int postion);
    }
}
