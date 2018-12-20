package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class WoGuanZhuDeHuoDongAdapter extends RecyclerView.Adapter<WoGuanZhuDeHuoDongAdapter.ViewHolder> {

    private Context context;
    private List<HuoDongListModel.ObjBean> data;
    private SpImp spImp;

    public WoGuanZhuDeHuoDongAdapter(List<HuoDongListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_guanzhu, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
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
        private TextView btn_cancel;
        private TextView tv_title;
        private ImageView iv_icon_owner;
        private TextView tv_name_owner;
        private TextView tv_timeinfo;
        private ImageView iv_image_huodong;
        private LinearLayout ll_huodong_apply;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_gochatroom = itemView.findViewById(R.id.btn_gochatroom);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_title = itemView.findViewById(R.id.tv_huodong_title);
            iv_icon_owner = itemView.findViewById(R.id.iv_icon_owner);
            tv_name_owner = itemView.findViewById(R.id.tv_name_owner);
            tv_timeinfo = itemView.findViewById(R.id.tv_timeinfo);
            iv_image_huodong = itemView.findViewById(R.id.iv_image_huodong);
            ll_huodong_apply = itemView.findViewById(R.id.ll_huodong_apply);
        }
    }

}
