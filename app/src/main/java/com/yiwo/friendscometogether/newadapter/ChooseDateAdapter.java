package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ChooseDateModel;

import java.util.List;

/**
 * Created by ljc on 2019/2/26.
 */

public class ChooseDateAdapter extends RecyclerView.Adapter<ChooseDateAdapter.ViewHolder> {

    private Context context;
    private List<ChooseDateModel> data;

    public ChooseDateAdapter(List<ChooseDateModel> data){
        this.data =data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_date_choose, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ChooseDateAdapter.ViewHolder holder = new ChooseDateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_choosed;
        RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_choosed = itemView.findViewById(R.id.iv_choosed);
            rl = itemView.findViewById(R.id.rl);
        }
    }
}
