package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.newmodel.HomeDataBannerHuoDongLiveModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */

public class HomeTui_JianJingCaiLuXian_Adapter extends RecyclerView.Adapter<HomeTui_JianJingCaiLuXian_Adapter.ViewHolder> {

    private Context context;
    private List<HomeTuiJianModel.ObjBean.ActivityBean> data;
    private SpImp spImp;
    private String uid = "";
    private LiveListAdapterListener listener;
    public HomeTui_JianJingCaiLuXian_Adapter(List<HomeTuiJianModel.ObjBean.ActivityBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_jingcailuxian, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (data.get(position).getCaptain().getUid().equals("")){
            holder.tv_live_state.setText("待直播");
            holder.btn_gaunzhu.setVisibility(View.GONE);
            holder.tv_live_person.setText("队长待定");
            Glide.with(context).load(R.mipmap.nosure_head).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon);
        }else {
            Glide.with(context).load(data.get(position).getCaptain().getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon);
            holder.btn_gaunzhu.setVisibility(View.VISIBLE);
            if (data.get(position).getCaptain().getZhibostatus().equals("1")){
                holder.tv_live_state.setText("直播中");
            }else {
                holder.tv_live_state.setText("待直播");
            }
            holder.tv_live_person.setText(data.get(position).getCaptain().getUsername());
            holder.btn_gaunzhu.setText(data.get(position).getCaptain().getFollow().equals("1")?"已关注":"+关注");
            if (listener!= null){
                holder.btn_gaunzhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onGuanZhuListen(position);
                    }
                });
                holder.rl_head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onCLickListen(position);
                    }
                });
            }
        }

            holder.tvName.setText(data.get(position).getPftitle());
            holder.tv_go_time.setText(data.get(position).getDayTime());
            Glide.with(context).load(data.get(position).getPfpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent();
                    if (TextUtils.isEmpty(data.get(position).getPfpwd())) {
                        intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                        intent.putExtra("pfID", data.get(position).getPfID());
                        context.startActivity(intent);
                    } else {
                        LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                            @Override
                            public boolean setActivityText(String s) {
                                if (s.equals(data.get(position).getPfpwd())) {
                                    intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                                    intent.putExtra("pfID", data.get(position).getPfID());
                                    context.startActivity(intent);
                                    return true;
                                }else {
                                    Toast.makeText(context,"密码错误",Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            }
                        });
                        lookPasswordDialog.show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListener(LiveListAdapterListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        ImageView iv;
        RelativeLayout rl;
        RelativeLayout rl_head;
        ImageView iv_icon;
        TextView tv_live_state;
        TextView tv_live_person;
        TextView btn_gaunzhu;
        TextView tv_go_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            iv = itemView.findViewById(R.id.iv);
            rl = itemView.findViewById(R.id.rl);
            tvName = itemView.findViewById(R.id.tv_name);
            tvName = itemView.findViewById(R.id.tv_name);

            rl_head = itemView.findViewById(R.id.rl_head);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_live_state = itemView.findViewById(R.id.tv_live_state);
            tv_live_person = itemView.findViewById(R.id.tv_live_person);
            tv_go_time = itemView.findViewById(R.id.tv_go_time);
            btn_gaunzhu = itemView.findViewById(R.id.btn_gaunzhu);
        }
    }
    public interface LiveListAdapterListener{
        void onCLickListen(int pos);
        void onGuanZhuListen(int pos);
    }

}
