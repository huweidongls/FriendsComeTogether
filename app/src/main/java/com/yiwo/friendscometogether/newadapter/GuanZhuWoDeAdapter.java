package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class GuanZhuWoDeAdapter extends RecyclerView.Adapter<GuanZhuWoDeAdapter.ViewHolder> {

    private Context context;
    private List<GuanZhuWoDeModel.ObjBean> data;
    private SpImp spImp;

    private GuanZhuListion guanZhulistion;
    private CancelGuanZhuListion cancelGuanZhuListion;
    private OnFocusInvitationListener listener_invitation;

    public void setListener_invitation(OnFocusInvitationListener listener_invitation) {
        this.listener_invitation = listener_invitation;
    }

    public GuanZhuWoDeAdapter(List<GuanZhuWoDeModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_guanzhuwode, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        final GuanZhuWoDeModel.ObjBean bean = data.get(position);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(context, OtherInformationActivity.class);
//                it.putExtra("uid", bean.getUserID());
//                context.startActivity(it);
                Intent it = new Intent(context, PersonMainActivity.class);
                it.putExtra("person_id", bean.getUserID());
                Log.d("person_id",bean.getUserID());
                context.startActivity(it);
            }
        });
        Glide.with(context).load(bean.getUpicurl()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon_user);
        holder.tv_user_name.setText(bean.getUsername());
        holder.tv_user_fans_num.setText("粉丝数："+bean.getLike_num());
        holder.rl_yaoqing.setFocusable(false);
        holder.rl_yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_invitation.onInvitation(position);
            }
        });
        holder.rl_guanzhu.setFocusable(false);
        //当前用户是否关注该人 0是未关注 1为已关注
        if (bean.getIs_follow().equals("1")){
            holder.iv_guanzhu0.setImageResource(R.mipmap.heart_white);
            holder.iv_guanzhu0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelGuanZhuListion.cancel_guanzhu(position);
                }
            });
            holder.iv_guanzhu1.setVisibility(View.VISIBLE);
        }else if (bean.getIs_follow().equals("0")){
            holder.iv_guanzhu0.setImageResource(R.mipmap.heart_white_border);
            holder.iv_guanzhu0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guanZhulistion.guanzhu(position);
                }
            });
            holder.iv_guanzhu1.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private ImageView iv_icon_user;
        private TextView tv_user_name;
        private TextView tv_user_fans_num;
        private RelativeLayout rl_guanzhu;
        private RelativeLayout rl_yaoqing;
        private ImageView iv_guanzhu0,iv_guanzhu1;
        public ViewHolder(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_fans_num = itemView.findViewById(R.id.tv_user_fans_num);
            rl_guanzhu  = itemView.findViewById(R.id.rl_guanzhu);
            rl_yaoqing = itemView.findViewById(R.id.rl_yaoqing);
            iv_guanzhu0 = itemView.findViewById(R.id.iv_guanzhu0);
            iv_guanzhu1 = itemView.findViewById(R.id.iv_guanzhu1);
        }
    }
    public void setGuanZhuListionner( GuanZhuListion listion){
        this.guanZhulistion = listion;
    }
    public void setCancelGuanZhu(CancelGuanZhuListion listion){
        this.cancelGuanZhuListion = listion;
    }
    public interface GuanZhuListion{
        void guanzhu(int posion);
    }
    public interface CancelGuanZhuListion{
        void cancel_guanzhu(int posion);
    }
    public interface OnFocusInvitationListener{
        void onInvitation(int position);
    }
}
