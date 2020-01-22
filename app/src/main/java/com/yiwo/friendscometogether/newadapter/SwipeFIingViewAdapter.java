package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.utils.ViewUtil;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import java.util.List;

/**
 * Created by ljc on 2019/3/19.
 */

public class SwipeFIingViewAdapter extends BaseAdapter{

    private Context context;
    public List<FriendsTogethermodel.ObjBean> data;
    private int currentPositon = 0;
    private OnBottomButtonClickListionner onBottomButtonClickListionner;
    public interface OnBottomButtonClickListionner{
        void OnGuanZhuClick(int postion);
        void OnBaoMingClick(int postion);
        void OnFenXiangClick(int postion);
    }
    public void setOnBottomButtonClickListionner(OnBottomButtonClickListionner onBottomButtonClickListionner) {
        this.onBottomButtonClickListionner = onBottomButtonClickListionner;
    }
    public SwipeFIingViewAdapter(Context context,List<FriendsTogethermodel.ObjBean> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_new, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            ScreenAdapterTools.getInstance().loadView(convertView);
        }
        currentPositon = position;
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (data.get(position).getCaptainInfo().getCapatinWyaccid() == null ||data.get(position).getCaptainInfo().getCaptainUserID().equals("") ){
            holder.tvTitle.setText(data.get(position).getPftitle());
            holder.iv_head.setImageResource(R.mipmap.pf_title);
        }else {
            holder.tvTitle.setText(data.get(position).getCaptainInfo().getCaptainUsername());
            Glide.with(context).load(data.get(position).getCaptainInfo().getCaptainUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_head);
            holder.iv_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("person_id",data.get(position).getCaptainInfo().getCaptainUserID());
                    intent.putExtra("status","0");
                    intent.setClass(context, PersonMainActivity1.class);
                    context.startActivity(intent);
                }
            });
        }
        holder.tvSeeNum.setText(data.get(position).getPflook());
        holder.tvPrice.setText(data.get(position).getPfspend());
        Glide.with(context).load(data.get(position).getPfpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian_long).error(R.mipmap.zanwutupian_long)).into(holder.ivTitle);
        holder.tv_content.setText(data.get(position).getPfcontent());
        if (data.get(position).getShop_recommend().equals("0")){
            holder.iv_tuijian.setVisibility(View.GONE);
        }else if (data.get(position).getShop_recommend().equals("1")){
            holder.iv_tuijian.setVisibility(View.VISIBLE);
        }
//        ----------------------------------------------------------------------------

        if (data.get(position).getFocusOn().equals("0")){
            Glide.with(context).load(R.mipmap.youjuitem_heart_border).into(holder.ivGuanzhu);
            holder.tvGuanzhu.setTextColor(Color.parseColor("#101010"));
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
        return convertView;
    }
    public void  refrsh(){

    }
    public class ViewHolder{

        private TextView tvTitle;
        private ImageView ivTitle;
        private TextView tv_content;
        private LinearLayout llGuanZhu;
        private LinearLayout llFenXiang;
        private ImageView iv_head;
        private ImageView ivBaoming;
        private ImageView ivGuanzhu;
        private TextView tvGuanzhu;
        private TextView tvSeeNum;
        private TextView tvPrice;
        private RelativeLayout rl;
        private RelativeLayout rl_price,rl_image;
        private ImageView iv_tuijian;
        public ViewHolder(View itemView) {
            iv_head = itemView.findViewById(R.id.iv_head);
            tvTitle = itemView.findViewById(R.id.tv_title);//
            tv_content = itemView.findViewById(R.id.tv_content);
            ivTitle = itemView.findViewById(R.id.iv_title);//
            tvSeeNum = itemView.findViewById(R.id.tv_see_num);
            tvPrice = itemView.findViewById(R.id.tv_price);
            rl_price = itemView.findViewById(R.id.rl_price);
            rl_image = itemView.findViewById(R.id.rl_image);

            final ViewGroup.LayoutParams layoutParams = ivTitle.getLayoutParams();
            ViewUtil.getViewWidth(ivTitle, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.height = height;
                    layoutParams.width = (int) (height * 0.75);
                    Log.d("asdasdasd2ivTitle",layoutParams.width+"////"+layoutParams.height);
                    ivTitle.setLayoutParams(layoutParams);
                }
            });

            llFenXiang = itemView.findViewById(R.id.ll_fenxiang);
            llGuanZhu = itemView.findViewById(R.id.ll_guanzhu);
            rl = itemView.findViewById(R.id.click_layout);
            ivBaoming = itemView.findViewById(R.id.iv_baoming);
            ivGuanzhu = itemView.findViewById(R.id.iv_guanzhu);
            tvGuanzhu = itemView.findViewById(R.id.tv_guanzhu);

            iv_tuijian = itemView.findViewById(R.id.iv_tuijian);
        }

    }
}
