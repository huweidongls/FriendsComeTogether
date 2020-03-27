package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ViewUtil;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJian_DuiZhangPuZi_Adapter extends RecyclerView.Adapter<HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private SpImp spImp;
    public HomeTuiJian_DuiZhangPuZi_Adapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_duizhangpuzi, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder holder = new HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
