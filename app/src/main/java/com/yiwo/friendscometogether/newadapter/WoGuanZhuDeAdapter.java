package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class WoGuanZhuDeAdapter extends RecyclerView.Adapter<WoGuanZhuDeAdapter.ViewHolder> {

    private Context context;
    private List<UserFocusModel.ObjBean> data;
    private SpImp spImp;
    private OnCancelFocusListener listener;
    private OnFocusInvitationListener listener_invitation;
    public void setListener(OnCancelFocusListener listener) {
        this.listener = listener;
    }
    public void setListener_invitation(OnFocusInvitationListener listener) {
        this.listener_invitation = listener;
    }

    public WoGuanZhuDeAdapter(List<UserFocusModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_woguanzhude, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        final UserFocusModel.ObjBean bean = data.get(position);
        Glide.with(context).load(bean.getUpicurl()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon_user);
        holder.tv_user_name.setText(bean.getUsername());
        holder.tv_user_fans_num.setText("粉丝数："+bean.getLike_num());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, PersonMainActivity.class);
                it.putExtra("person_id", bean.getLikeuserID());
                context.startActivity(it);

            }
        });

        holder.rl_guanzhu.setFocusable(false);
        holder.rl_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(position);
            }
        });
        holder.rl_yaoqing.setFocusable(false);
        holder.rl_yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_invitation.onInvitation(position);
            }
        });
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
        private ImageView iv_guanzhu;
        public ViewHolder(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_fans_num = itemView.findViewById(R.id.tv_user_fans_num);
            rl_guanzhu = itemView.findViewById(R.id.rl_guanzhu);
            rl_yaoqing = itemView.findViewById(R.id.rl_yaoqing);
            iv_guanzhu = itemView.findViewById(R.id.iv_guanzhu);
        }
    }

    public interface OnCancelFocusListener{
        void onCancel(int i);
    }
    public interface OnFocusInvitationListener{
        void onInvitation(int position);
    }

}
