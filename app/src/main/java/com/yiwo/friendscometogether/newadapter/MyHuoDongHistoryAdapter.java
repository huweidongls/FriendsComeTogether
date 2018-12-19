package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyHuoDongHistoryAdapter extends RecyclerView.Adapter<MyHuoDongHistoryAdapter.ViewHolder> {

    private Context context;
    private List<HuoDongListModel.ObjBean> data;
    private SpImp spImp;
    public MyHuoDongHistoryAdapter(List<HuoDongListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_history, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        final HuoDongListModel.ObjBean bean = data.get(position);
        holder.btn_gochatroom.setFocusable(false);
        holder.btn_gochatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team(bean.getRoomid());
            }
        });
        holder.ll_huodong_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                intent.putExtra("pfID",bean.getPfID());
                context.startActivity(intent);
            }
        });
        holder.tv_title.setText(bean.getPftitle());
        Glide.with(context).load(bean.getPfpic()).into(holder.iv_image_huodong);
        holder.tv_name_owner.setText(bean.getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.iv_icon_owner);
        holder.tv_timeinfo.setText(bean.getActivities_data());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private void team(String teamId) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(context, teamId);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView btn_gochatroom;
        private TextView tv_title;
        private ImageView iv_icon_owner;
        private TextView tv_name_owner;
        private TextView tv_timeinfo;
        private ImageView iv_image_huodong;
        private LinearLayout ll_huodong_history;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_gochatroom = itemView.findViewById(R.id.btn_gochatroom);
            tv_title = itemView.findViewById(R.id.tv_huodong_title);
            iv_icon_owner = itemView.findViewById(R.id.iv_icon_owner);
            tv_name_owner = itemView.findViewById(R.id.tv_name_owner);
            tv_timeinfo = itemView.findViewById(R.id.tv_timeinfo);
            iv_image_huodong = itemView.findViewById(R.id.iv_image_huodong);
            ll_huodong_history = itemView.findViewById(R.id.ll_huodong_history);
        }
    }

}
