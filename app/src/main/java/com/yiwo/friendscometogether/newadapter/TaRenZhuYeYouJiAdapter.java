package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;
import com.yiwo.friendscometogether.pages.LoginActivity;

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
                intent.setClass(context, DetailsOfFriendsWebActivity1.class);
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
        if (data.get(position).getPfpic().size()>0){
            Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_view0);
            if (data.get(position).getPfpic().size()>=4){
                Glide.with(context).load(data.get(position).getPfpic().get(1)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_view1);
                Glide.with(context).load(data.get(position).getPfpic().get(2)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_view2);
                Glide.with(context).load(data.get(position).getPfpic().get(3)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_view3);
            }else {
                holder.iv_image_view1.setVisibility(View.GONE);
                holder.iv_image_view2.setVisibility(View.GONE);
                holder.iv_image_view3.setVisibility(View.GONE);
            }
        }
        //评论信息
        if(data.get(position).getComment_list().size()>=2){
            holder.tv_youji_pinglun_username_0.setText(data.get(position).getComment_list().get(0).getUsername()+"：");
            holder.tv_youji_pinglun_content_0.setText(data.get(position).getComment_list().get(0).getFctitle());
            holder.tv_youji_pinglun_username_1.setText(data.get(position).getComment_list().get(1).getUsername()+"：");
            holder.tv_youji_pinglun_content_1.setText(data.get(position).getComment_list().get(1).getFctitle());
            holder.llComment1.setVisibility(View.VISIBLE);
            holder.llComment2.setVisibility(View.VISIBLE);
        }else if(data.get(position).getComment_list().size()==1){
            holder.tv_youji_pinglun_username_0.setText(data.get(position).getComment_list().get(0).getUsername()+"：");
            holder.tv_youji_pinglun_content_0.setText(data.get(position).getComment_list().get(0).getFctitle());
            holder.llComment1.setVisibility(View.VISIBLE);
            holder.llComment2.setVisibility(View.GONE);
        }else {
            holder.llComment1.setVisibility(View.GONE);
            holder.llComment2.setVisibility(View.GONE);
        }
        holder.tv_feelgoods.setText(data.get(position).getFmgood()+"人觉得很赞");
        holder.tvAllCommentNum.setText("全部"+data.get(position).getCommentcount()+"条评论");
        holder.tvAllCommentNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ArticleCommentActivity.class);
                intent.putExtra("id", data.get(position).getPfID());
                context.startActivity(intent);
            }
        });
        holder.ll_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ArticleCommentActivity.class);
                intent.putExtra("id", data.get(position).getPfID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        private LinearLayout ll_taren_youji,ll_pinglun;
        private TextView tv_youji_time;
        private TextView tv_youji_name;
        private TextView tv_youji_location;
        private TextView tv_youji_see_num;
        private TextView tv_youji_pinglun_num;
        private TextView tvAllCommentNum;
        private ImageView iv_image_view0;
        private ImageView iv_image_view1;
        private ImageView iv_image_view2;
        private ImageView iv_image_view3;

        private TextView tv_feelgoods;
        //两条评论
        private TextView tv_youji_pinglun_username_0;
        private TextView tv_youji_pinglun_username_1;
        private TextView tv_youji_pinglun_content_0;
        private TextView tv_youji_pinglun_content_1;

        private LinearLayout ll_youji_location,llComment1,llComment2;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_taren_youji = itemView.findViewById(R.id.ll_taren_youji);
            ll_pinglun = itemView.findViewById(R.id.ll_pinglun);
            tv_youji_time = itemView.findViewById(R.id.tv_youji_time);
            tv_youji_name = itemView.findViewById(R.id.tv_youji_name);
            tv_youji_location = itemView.findViewById(R.id.tv_youji_location);
            tv_youji_see_num = itemView.findViewById(R.id.tv_youji_see_num);
            tv_youji_pinglun_num = itemView.findViewById(R.id.tv_youji_pinglun_num);
            tvAllCommentNum = itemView.findViewById(R.id.tvAllCommentNum);
            tv_feelgoods = itemView.findViewById(R.id.tv_feelgoods);
            llComment1 = itemView.findViewById(R.id.llComment1);
            llComment2 = itemView.findViewById(R.id.llComment2);
            tv_youji_pinglun_username_0 = itemView.findViewById(R.id.tv_youji_pinglun_username_0);
            tv_youji_pinglun_username_1 = itemView.findViewById(R.id.tv_youji_pinglun_username_1);
            tv_youji_pinglun_content_0 = itemView.findViewById(R.id.tv_youji_pinglun_content_0);
            tv_youji_pinglun_content_1 = itemView.findViewById(R.id.tv_youji_pinglun_content_1);
            ll_youji_location = itemView.findViewById(R.id.ll_youji_location);
            iv_image_view0 = itemView.findViewById(R.id.iv_taren_pics0);
            iv_image_view1 = itemView.findViewById(R.id.iv_taren_pics1);
            iv_image_view2 = itemView.findViewById(R.id.iv_taren_pics2);
            iv_image_view3 = itemView.findViewById(R.id.iv_taren_pics3);
        }
    }
}
