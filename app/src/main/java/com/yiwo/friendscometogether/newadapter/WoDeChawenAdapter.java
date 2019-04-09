package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.WoDeChaWenModel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class WoDeChawenAdapter extends RecyclerView.Adapter<WoDeChawenAdapter.ViewHolder> {

    private Context context;
    private List<WoDeChaWenModel.ObjBean> data;
    private OnDeleteListener listener;
    private WoDeChaWenPicsAdapter adapter;
    public void setListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public WoDeChawenAdapter(List<WoDeChaWenModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_chawen, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFftitle());
        holder.tvDate.setText(data.get(position).getFfptime());
        if (data.get(position).getRadio().equals("1")){
            holder.tvStaus.setTextColor(Color.parseColor("#d84c37"));
            holder.tvStaus.setText("作者未展示");
        }else if(data.get(position).getRadio().equals("2")){
            holder.tvStaus.setText("作者已通过");
        }
        adapter = new WoDeChaWenPicsAdapter(data.get(position).getPiclist());
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(context,
                        3// 每行显示item项数目
                ) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        holder.rvPics.setLayoutManager(layoutManager);
        holder.rvPics.setAdapter(adapter);
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(position);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsWebActivity.class);
                intent.putExtra("fmid", data.get(position).getFmID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvStaus;
        private TextView tvDate;
        private TextView tvDelete;
        private RecyclerView rvPics;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStaus = itemView.findViewById(R.id.tv_staus);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvDate = itemView.findViewById(R.id.tv_date);
            rvPics = itemView.findViewById(R.id.rv_pics);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnDeleteListener{
        void onDelete(int position);
    }

}
