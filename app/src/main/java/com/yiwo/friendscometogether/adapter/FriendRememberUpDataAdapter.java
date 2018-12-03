package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FriendsRememberModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public class FriendRememberUpDataAdapter extends RecyclerView.Adapter<FriendRememberUpDataAdapter.ViewHolder> {

    private List<FriendsRememberModel.ObjBean> data;
    private Context context;
    private OnFocusListener listener;

    public void setOnFocusListener(OnFocusListener listener){
        this.listener = listener;
    }

    public FriendRememberUpDataAdapter(List<FriendsRememberModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_friend_remember, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(TextUtils.isEmpty(data.get(position).getFmpic())){

        }else {
            Picasso.with(context).load(data.get(position).getFmpic()).into(holder.ivTitle);
        }
        holder.tvTitle.setText(data.get(position).getFmtitle());
        if(TextUtils.isEmpty(data.get(position).getUpicurl())){
            Picasso.with(context).load(R.mipmap.my_head).into(holder.ivAvatar);
        }else {
            Picasso.with(context).load(data.get(position).getUpicurl()).into(holder.ivAvatar);
        }
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvUsergrade.setText("LV" + data.get(position).getUsergrade());
        holder.tvCreateTime.setText(data.get(position).getFmtime());
        holder.tvLookNum.setText(data.get(position).getFmlook());
        if(data.get(position).getLook().equals("0")){
//            Picasso.with(context).load(R.mipmap.focus_on_empty_y).into(holder.ivIsFocus);
            holder.btnIsFocus.setText("+关注");
        }else {
//            Picasso.with(context).load(R.mipmap.focus_on_y).into(holder.ivIsFocus);
            holder.btnIsFocus.setText("已关注");
        }
        holder.tvCommentNum.setText(data.get(position).getFmcomment());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                if(TextUtils.isEmpty(data.get(position).getAccesspassword())){
                    intent.setClass(context, DetailsOfFriendsActivity.class);
                    intent.putExtra("fmid", data.get(position).getFmID());
                    context.startActivity(intent);
                }else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public void setActivityText(String s) {
                            if(s.equals(data.get(position).getAccesspassword())){
                                intent.setClass(context, DetailsOfFriendsActivity.class);
                                intent.putExtra("fmid", data.get(position).getFmID());
                                context.startActivity(intent);
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("uid", data.get(position).getUserID());
                intent.setClass(context, OtherInformationActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btnIsFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFocus(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTitle;
        private TextView tvTitle;
        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvUsergrade;
        private TextView tvCreateTime;
        private TextView tvLookNum;
        private TextView tvCommentNum;
        private LinearLayout ll;
//        private ImageView ivIsFocus;
        private Button btnIsFocus;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTitle = itemView.findViewById(R.id.fragment_friend_remember_rv_iv_title);
            tvTitle = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_title);
            ivAvatar = itemView.findViewById(R.id.fragment_friend_remember_rv_iv_avatar);
            tvNickname = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_nickname);
            tvUsergrade = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_usergrade);
            tvCreateTime = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_c_time);
            tvLookNum = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_look_num);
            tvCommentNum = itemView.findViewById(R.id.fragment_friend_remember_rv_tv_comment_num);
            ll = itemView.findViewById(R.id.fragment_friend_remember_rv_ll);
//            ivIsFocus = itemView.findViewById(R.id.fragment_friend_remember_rv_iv_is_focus);
            btnIsFocus = itemView.findViewById(R.id.activity_details_of_friends_btn_focus);
        }
    }

    public interface OnFocusListener{
        void onFocus(int position);
    }

}
