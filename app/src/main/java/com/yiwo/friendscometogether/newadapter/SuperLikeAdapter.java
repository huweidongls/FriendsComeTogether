package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.YouJiListModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class SuperLikeAdapter extends RecyclerView.Adapter<SuperLikeAdapter.ViewHolder>{

    private List<String> data;

    private Context context;
    public SuperLikeAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_super_like_item, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        SuperLikeAdapter.ViewHolder holder = new SuperLikeAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final List<String> list_biaoqian = new ArrayList<>();
        list_biaoqian.add("90后");
        list_biaoqian.add("江南妹子");
        list_biaoqian.add("想去美国逛逛");
        list_biaoqian.add("抖腿");
        list_biaoqian.add("古典风");

        flowLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list_biaoqian.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(context).inflate(R.layout.flowlayout_item, parent, false);
                TextView textView = view.findViewById(R.id.tv_flowlayout_item);
                ScreenAdapterTools.getInstance().loadView(view);
                textView.setText(list_biaoqian.get(position));
                return view;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private FlowLayout flowLayout;

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
            flowLayout = itemView.findViewById(R.id.flow_layout);
        }
    }
}
