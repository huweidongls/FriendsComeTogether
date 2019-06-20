package com.yiwo.friendscometogether.activecard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<FriendsTogethermodel.ObjBean> data;
    private int currentPositon = 0;
    private OnBottomButtonClickListionner onBottomButtonClickListionner;
    public CardAdapter(List<FriendsTogethermodel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public int getPosition(){
        return currentPositon;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        currentPositon = position;
        Glide.with(context).load(data.get(position).getUpicurl()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.ivAvatar);
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvTitle.setText(data.get(position).getPftitle());
        Glide.with(context).load(data.get(position).getPfpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivTitle);

        holder.tvBaoming.setText("报名人数: "+data.get(position).getHave_num()+"人");
        holder.tvAddress.setText("活动地点："+data.get(position).getPfaddress());

//        -----------------------已隐藏字段-------------------------------------------
        holder.tvFabuTime.setText(data.get(position).getPftime());
        holder.tvShengyu.setText("剩余名额: "+data.get(position).getSurplus()+"人");
        holder.tvStartTime.setText("开始时间: "+data.get(position).getPfgotime());
        holder.tvRenjun.setText(data.get(position).getPfspend()+"/人");
//        ----------------------------------------------------------------------------

        if (data.get(position).getFocusOn().equals("0")){
            Glide.with(context).load(R.mipmap.youjuitem_heart_border).into(holder.ivGuanzhu);
            holder.tvGuanzhu.setTextColor(Color.parseColor("#696969"));
        }else {
            Glide.with(context).load(R.mipmap.youjuitem_heart_red).into(holder.ivGuanzhu);
            holder.tvGuanzhu.setTextColor(Color.parseColor("#d84c37"));
        }
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
        holder.llGuanZhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomButtonClickListionner.OnGuanZhuClick(position);
            }
        });
        holder.llFenXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomButtonClickListionner.OnFenXiangClick(position);
            }
        });
        holder.ivBaoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomButtonClickListionner.OnBaoMingClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnBottomButtonClickListionner(OnBottomButtonClickListionner onBottomButtonClickListionner) {
        this.onBottomButtonClickListionner = onBottomButtonClickListionner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvFabuTime;
        private TextView tvTitle;
        private ImageView ivTitle;
        private TextView tvStartTime;
        private TextView tvRenjun;
        private TextView tvBaoming;
        private TextView tvShengyu;
        private TextView tvAddress;
        private LinearLayout ll;
        private LinearLayout llGuanZhu;
        private LinearLayout llFenXiang;
        private ImageView ivBaoming;
        private ImageView ivGuanzhu;
        private TextView tvGuanzhu;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvFabuTime = itemView.findViewById(R.id.tv_fabu_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivTitle = itemView.findViewById(R.id.iv_title);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvRenjun = itemView.findViewById(R.id.tv_renjun);
            tvBaoming = itemView.findViewById(R.id.tv_baoming);
            tvShengyu = itemView.findViewById(R.id.tv_shengyu);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ll = itemView.findViewById(R.id.ll);
            llFenXiang = itemView.findViewById(R.id.ll_fenxiang);
            llGuanZhu = itemView.findViewById(R.id.ll_guanzhu);
            rl = itemView.findViewById(R.id.click_layout);
            ivBaoming = itemView.findViewById(R.id.iv_baoming);
            ivGuanzhu = itemView.findViewById(R.id.iv_guanzhu);
            tvGuanzhu = itemView.findViewById(R.id.tv_guanzhu);
        }
    }
    public interface OnBottomButtonClickListionner{
        void OnGuanZhuClick(int postion);
        void OnBaoMingClick(int postion);
        void OnFenXiangClick(int postion);
    }
}
