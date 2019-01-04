package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.ll_taren_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsActivity.class);
                intent.putExtra("fmid", data.get(position).getPfID());
                context.startActivity(intent);
            }
        });
        Log.d("data_Size",data.size()+"///posion"+position);
        holder.tv_youji_time.setText(data.get(position).getPftime());
        holder.tv_youji_name.setText(data.get(position).getPftitle());
        if (data.get(position).getPfaddress()==null||data.get(position).getPfaddress().equals("")){
            holder.ll_youji_location.setVisibility(View.GONE);
        }else {
            holder.ll_youji_location.setVisibility(View.VISIBLE);
            holder.tv_youji_location.setText(data.get(position).getPfaddress());
        }
        holder.tv_youji_pinglun_num.setText(data.get(position).getFmcomment());
        holder.tv_youji_see_num.setText(data.get(position).getPflook());

        Glide.with(context).load(data.get(position).getPfpic().get(0)).into(holder.iv_image_view0);
        Glide.with(context).load(data.get(position).getPfpic().get(1)).into(holder.iv_image_view1);
        Glide.with(context).load(data.get(position).getPfpic().get(2)).into(holder.iv_image_view2);
        Glide.with(context).load(data.get(position).getPfpic().get(3)).into(holder.iv_image_view3);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        private LinearLayout ll_taren_youji;
        private TextView tv_youji_time;
        private TextView tv_youji_name;
        private TextView tv_youji_location;
        private TextView tv_youji_see_num;
        private TextView tv_youji_pinglun_num;

        private ImageView iv_image_view0;
        private ImageView iv_image_view1;
        private ImageView iv_image_view2;
        private ImageView iv_image_view3;



        private LinearLayout ll_youji_location;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_taren_youji = itemView.findViewById(R.id.ll_taren_youji);
            tv_youji_time = itemView.findViewById(R.id.tv_youji_time);
            tv_youji_name = itemView.findViewById(R.id.tv_youji_name);
            tv_youji_location = itemView.findViewById(R.id.tv_youji_location);
            tv_youji_see_num = itemView.findViewById(R.id.tv_youji_see_num);
            tv_youji_pinglun_num = itemView.findViewById(R.id.tv_youji_pinglun_num);

            ll_youji_location = itemView.findViewById(R.id.ll_youji_location);
            iv_image_view0 = itemView.findViewById(R.id.iv_taren_pics0);
            iv_image_view1 = itemView.findViewById(R.id.iv_taren_pics1);
            iv_image_view2 = itemView.findViewById(R.id.iv_taren_pics2);
            iv_image_view3 = itemView.findViewById(R.id.iv_taren_pics3);
        }
    }
}
