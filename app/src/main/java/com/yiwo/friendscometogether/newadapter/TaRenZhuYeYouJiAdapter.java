package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;

import java.util.List;

/**
 * Created by ljc on 2018/12/26.
 */

public class TaRenZhuYeYouJiAdapter extends RecyclerView.Adapter<TaRenZhuYeYouJiAdapter.ViewHolder>{

    private Context context;
    private List<PersonMainModel.ObjBean.FriendBean> data;
    public TaRenZhuYeYouJiAdapter(List<PersonMainModel.ObjBean.FriendBean> list){
        this.data = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tarenzhuye_youji, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        TaRenZhuYeYouJiAdapter.ViewHolder holder = new TaRenZhuYeYouJiAdapter.ViewHolder(view);
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
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
