package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.newmodel.PersonMainGuanZhu_FansModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class PersonMainGuanZhu_FansAdapter extends RecyclerView.Adapter<PersonMainGuanZhu_FansAdapter.ViewHolder> {

    private Context context;
    private List<PersonMainGuanZhu_FansModel.ObjBean> data;
    private SpImp spImp;
    private OnCancel_FocusListener listener;
    private OnFocusInvitationListener listener_invitation;
    public void setListener(OnCancel_FocusListener listener) {
        this.listener = listener;
    }
    public void setListener_invitation(OnFocusInvitationListener listener) {
        this.listener_invitation = listener;
    }

    public PersonMainGuanZhu_FansAdapter(List<PersonMainGuanZhu_FansModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_personmain_guanzhu_fans, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        final PersonMainGuanZhu_FansModel.ObjBean bean = data.get(position);
        Glide.with(context).load(bean.getUserpic()).into(holder.iv_icon_user);
        holder.tv_user_name.setText(bean.getUsername());
        holder.tv_user_fans_num.setText("粉丝数："+bean.getNum());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, PersonMainActivity1.class);
                it.putExtra("person_id", bean.getUserID());
                context.startActivity(it);

            }
        });
        if (bean.getUserID().equals(spImp.getUID())){
            holder.tv_guanzhu.setVisibility(View.GONE);
        }else {
            holder.tv_guanzhu.setVisibility(View.VISIBLE);
        }
        if (bean.getStatus()!=null){
            if(bean.getStatus().equals("1")){
                holder.tv_guanzhu.setBackgroundResource(R.drawable.bg_red_24px);
                holder.tv_guanzhu.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_guanzhu.setText("已关注");
            }else if(bean.getStatus().equals("0")){
                holder.tv_guanzhu.setBackgroundResource(R.drawable.bg_gray_border_24px);
                holder.tv_guanzhu.setTextColor(Color.parseColor("#363636"));
                holder.tv_guanzhu.setText("+关注");
            }
        }else {
            holder.tv_guanzhu.setVisibility(View.GONE);
        }

        holder.tv_guanzhu.setFocusable(false);
        holder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.listen(position);
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
        private TextView tv_guanzhu;
        public ViewHolder(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_fans_num = itemView.findViewById(R.id.tv_user_fans_num);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);
        }
    }

    public interface OnCancel_FocusListener{
        void listen(int i);
    }
    public interface OnFocusInvitationListener{
        void onInvitation(int position);
    }

}
