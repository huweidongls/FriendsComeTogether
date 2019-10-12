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
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class MyDraftAdapter extends RecyclerView.Adapter<MyDraftAdapter.ViewHolder> {

    private Context context;
    private List<UserRememberModel.ObjBean> data;
    private OnBtnClickListenner listenner;
    public MyDraftAdapter(List<UserRememberModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_draft, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv);
        holder.tvTitle.setText(data.get(position).getFmtitle());
        holder.tvCreateTime.setText("创建时间:\n" + data.get(position).getFmtime());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsWebActivity.class);
                intent.putExtra("fmid", data.get(position).getFmID());
                context.startActivity(intent);
            }
        });
        if (listenner!=null){
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenner.OnDeleteListen(position);
                }
            });
            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenner.onEditListen(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListenner(OnBtnClickListenner listenner) {
        this.listenner = listenner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvCreateTime;
        private RelativeLayout rl;
        private TextView btn_delete;
        private TextView btn_edit;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_my_draft_rv_iv);
            tvTitle = itemView.findViewById(R.id.activity_my_draft_rv_tv_title);
            tvCreateTime = itemView.findViewById(R.id.activity_my_draft_rv_tv_c_time);
            rl = itemView.findViewById(R.id.rl);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
    public interface OnBtnClickListenner{
        void OnDeleteListen(int position);
        void onEditListen(int position);

    }

}
