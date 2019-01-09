package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.common.ui.recyclerview.decoration.SpacingDecoration;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;

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
        FlowLayoutManager manager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.rv_label.setLayoutManager(manager);
        holder.rv_label.setAdapter(new SuperLikeLabelAdapter(list_biaoqian));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv_label;
        public ViewHolder(View itemView) {
            super(itemView);
            rv_label = itemView.findViewById(R.id.rv_label);
        }
    }
}
