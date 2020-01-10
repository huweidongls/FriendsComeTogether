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
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by ljc on 2018/12/27.
 */

public class HomeListYouJiAdapter extends RecyclerView.Adapter<HomeListYouJiAdapter.ViewHolder>{

    private List<HomeDataModel.ObjBean> data;
    private Context context;
    private OnLongClickListener onLongClickListener;
    public HomeListYouJiAdapter(List<HomeDataModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_youji_item, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youji_waterfall_item,null);
        ScreenAdapterTools.getInstance().loadView(view);
        HomeListYouJiAdapter.ViewHolder holder = new HomeListYouJiAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//            ViewGroup.LayoutParams layoutParams = holder.rv_youji.getLayoutParams();
//            layoutParams.height = 546;//获取最终图片高度
//            holder.rv_youji.setLayoutParams(layoutParams);//应用高度到布局中
        if (data.get(position).getPfpic().size()>=0){
            Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_youji);
        }
        holder.tvYoujiTitle.setText(data.get(position).getPftitle());
        Glide.with(context).load(data.get(position).getHeadportrait()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.ivAvatar);
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        holder.tvUsername.setText(data.get(position).getUsername());
        holder.tvTime.setText(data.get(position).getPftime());
//        holder.tvGoodNum.setText(data.get(position).getFmgood());//修改为浏览数量
        holder.tvGoodNum.setText(data.get(position).getPflook());
        holder.rv_youji.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClickListener!=null){
                    onLongClickListener.OnLongClick(position);
                }
                return false;
            }
        });
        holder.rv_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.get(position).getPfpwd())) {
                    Intent intent = new Intent();
                    intent.setClass(context, DetailsOfFriendsWebActivity2.class);
                    intent.putExtra("fmid", data.get(position).getPfID());
                    context.startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public boolean setActivityText(String s) {
                            if (s.equals(data.get(position).getPfpwd())) {
                                Intent intent = new Intent();
                                intent.setClass(context, DetailsOfFriendsWebActivity2.class);
                                intent.putExtra("fmid", data.get(position).getPfID());
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

    public OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rv_youji;
        private ImageView iv_youji;
        private TextView tvYoujiTitle;
        private ImageView ivAvatar;
        private TextView tvUsername;
        private TextView tvTime;
        private TextView tvGoodNum;

        public ViewHolder(View itemView) {
            super(itemView);
            rv_youji = itemView.findViewById(R.id.rv_youji);
            iv_youji = itemView.findViewById(R.id.iv_youju);
            tvYoujiTitle = itemView.findViewById(R.id.tv_youji_title);
            ivAvatar = itemView.findViewById(R.id.iv_icon_user);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvGoodNum = itemView.findViewById(R.id.tv_good_num);
        }
    }
    public interface OnLongClickListener{
        void OnLongClick(int position);
    }
}
