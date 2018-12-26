package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserFocusModel;

import java.util.List;

/**
 * Created by ljc on 2018/12/25.
 */

public class TaRenZhuYePicsAdapter extends RecyclerView.Adapter<TaRenZhuYePicsAdapter.ViewHolder>{

    private Context context;
    private List<String> data;

    public TaRenZhuYePicsAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tarenzhuye_pics, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        TaRenZhuYePicsAdapter.ViewHolder holder = new TaRenZhuYePicsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position>=5){
            holder.imageView.setVisibility(View.GONE);
            holder.ll_lookmore.setVisibility(View.VISIBLE);
            holder.tv_morePicNum.setText("+"+(data.size()-5));
        }else {
            holder.ll_lookmore.setVisibility(View.GONE);
            Glide.with(context).load(data.get(position)).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (data!=null){
            if (data.size()>=6){
                return 6;
            }else {
                return data.size();
            }
        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv_morePicNum;
        private LinearLayout ll_lookmore;
//        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_taren_pics);
            ll_lookmore = itemView.findViewById(R.id.ll_lookmore);
            tv_morePicNum = itemView.findViewById(R.id.tv_taren_lookmore);
        }
    }
}
