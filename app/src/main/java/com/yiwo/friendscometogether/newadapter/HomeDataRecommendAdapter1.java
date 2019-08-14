package com.yiwo.friendscometogether.newadapter;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeDataModel;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */

public class HomeDataRecommendAdapter1 extends RecyclerView.Adapter<HomeDataRecommendAdapter1.ViewHolder> {

    private Context context;
    private List<HomeDataModel1.ObjBean.ArticleBean> data;
    private SpImp spImp;
    private String uid = "";

    public HomeDataRecommendAdapter1(List<HomeDataModel1.ObjBean.ArticleBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_home_rv_new_1, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(data.get(position).getData_type().equals("1")){
            holder.llYouju.setVisibility(View.VISIBLE);
            holder.llYouji.setVisibility(View.GONE);
            holder.llYouju.setOnClickListener(new View.OnClickListener() {
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
            Glide.with(context).load(data.get(position).getHeadportrait()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.ivYoujuAvatar);
            holder.tvYoujuName.setText(data.get(position).getUsername());
            holder.tvYoujuTime.setText(data.get(position).getPftime());
            if(data.get(position).getFollow().equals("1")){
                holder.tvYoujuFocus.setBackgroundResource(R.drawable.bg_red_24px);
                holder.tvYoujuFocus.setTextColor(Color.parseColor("#ffffff"));
                holder.tvYoujuFocus.setText("已关注");
            }else {
                holder.tvYoujuFocus.setBackgroundResource(R.drawable.bg_gray_border_24px);
                holder.tvYoujuFocus.setTextColor(Color.parseColor("#363636"));
                holder.tvYoujuFocus.setText("+关注");
            }
            holder.tvYoujuFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uid = spImp.getUID();
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                                .addParam("userID", uid)
                                .addParam("pfID", data.get(position).getPfID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getInt("code") == 200){
                                                FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                                if (model.getCode() == 200) {
                                                    if (model.getObj().equals("1")) {
                                                        holder.tvYoujuFocus.setBackgroundResource(R.drawable.bg_red_24px);
                                                        holder.tvYoujuFocus.setTextColor(Color.parseColor("#ffffff"));
                                                        holder.tvYoujuFocus.setText("已关注");
                                                        data.get(position).setFollow("1");
                                                        notifyDataSetChanged();
                                                        Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        holder.tvYoujuFocus.setBackgroundResource(R.drawable.bg_gray_border_24px);
                                                        holder.tvYoujuFocus.setTextColor(Color.parseColor("#363636"));
                                                        holder.tvYoujuFocus.setText("+关注");
                                                        data.get(position).setFollow("0");
                                                        notifyDataSetChanged();
                                                        Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }else if(jsonObject.getInt("code") == 400) {
                                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    } else {
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
            Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivYoujuTitle);
            holder.tvYoujuTitle.setText(data.get(position).getPftitle());
            if (data.get(position).getPfcontent().equals("")){
                holder.tvYoujuContent.setVisibility(View.GONE);
            }
            holder.tvYoujuContent.setText(data.get(position).getPfcontent());
            holder.tvYoujuAddress.setText(data.get(position).getPfaddress());
            holder.tvYoujuLookNum.setText(data.get(position).getPflook());
        }else {
            holder.llYouju.setVisibility(View.GONE);
            holder.llYouji.setVisibility(View.VISIBLE);
            holder.llYouji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent();
                    if (TextUtils.isEmpty(data.get(position).getPfpwd())) {
                        intent.setClass(context, DetailsOfFriendsWebActivity1.class);
                        intent.putExtra("fmid", data.get(position).getPfID());
                        context.startActivity(intent);
                    } else {
                        LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                            @Override
                            public boolean setActivityText(String s) {
                                if (s.equals(data.get(position).getPfpwd())) {
                                    intent.setClass(context, DetailsOfFriendsWebActivity1.class);
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
            Glide.with(context).load(data.get(position).getHeadportrait()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.ivYoujiAvatar);
            holder.ivYoujiAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("person_id", data.get(position).getUserID());
                    intent.setClass(context, PersonMainActivity1.class);
                    context.startActivity(intent);
                }
            });
            holder.tvYoujiNickname.setText(data.get(position).getUsername());
            holder.tvYoujiTime.setText(data.get(position).getPftime());
            if(data.get(position).getFollow().equals("1")){
                holder.tvYoujiFocus.setBackgroundResource(R.drawable.bg_red_24px);
                holder.tvYoujiFocus.setTextColor(Color.parseColor("#ffffff"));
                holder.tvYoujiFocus.setText("已关注");
            }else {
                holder.tvYoujiFocus.setBackgroundResource(R.drawable.bg_gray_border_24px);
                holder.tvYoujiFocus.setTextColor(Color.parseColor("#363636"));
                holder.tvYoujiFocus.setText("+关注");
            }
            holder.tvYoujiFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uid = spImp.getUID();
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                        if(data.get(position).getFollow().equals("0")){
                            ViseHttp.POST(NetConfig.userFocusUrl)
                                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                    .addParam("uid", uid)
                                    .addParam("likeId", data.get(position).getUserID())
                                    .request(new ACallback<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getInt("code") == 200) {
                                                    data.get(position).setFollow("1");
                                                    notifyDataSetChanged();
                                                    holder.tvYoujiFocus.setBackgroundResource(R.drawable.bg_red_24px);
                                                    holder.tvYoujiFocus.setTextColor(Color.parseColor("#ffffff"));
                                                    holder.tvYoujiFocus.setText("已关注");
                                                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                                }else if(jsonObject.getInt("code") == 400){

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFail(int errCode, String errMsg) {

                                        }
                                    });
                        }
                    }else {
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

            if (data.get(position).getPfpic().size()>0){
                Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivYoujiTitle1);
                if (data.get(position).getPfpic().size()>=4){
                    Glide.with(context).load(data.get(position).getPfpic().get(1)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivYoujiTitle2);
                    Glide.with(context).load(data.get(position).getPfpic().get(2)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivYoujiTitle3);
                    Glide.with(context).load(data.get(position).getPfpic().get(3)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.ivYoujiTitle4);
                }else {
                    holder.ivYoujiTitle2.setVisibility(View.GONE);
                    holder.ivYoujiTitle3.setVisibility(View.GONE);
                    holder.ivYoujiTitle4.setVisibility(View.GONE);
                }
            }
            holder.tvYoujiTitle.setText(data.get(position).getPftitle());
            if(TextUtils.isEmpty(data.get(position).getPfaddress())){
                holder.llCity.setVisibility(View.GONE);
            }
            holder.tvYoujiAddress.setText(data.get(position).getPfaddress());
            holder.tvYoujiLookNum.setText(data.get(position).getPflook());
            holder.tvYoujiCommentNum.setText(data.get(position).getCommentcount());
            holder.tvYoujiGoodNum.setText(data.get(position).getFmgood()+"人觉得很赞");
            holder.llYoujiComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                    } else {
                        intent.setClass(context, ArticleCommentActivity.class);
                        intent.putExtra("id", data.get(position).getPfID());
                        context.startActivity(intent);
                    }
                }
            });

            //评论信息
            if(data.get(position).getComment_list().size()>=2){
                holder.tvName1.setText(data.get(position).getComment_list().get(0).getUsername()+": ");
                holder.tvComment1.setText(data.get(position).getComment_list().get(0).getFctitle());
                holder.tvName2.setText(data.get(position).getComment_list().get(1).getUsername()+": ");
                holder.tvComment2.setText(data.get(position).getComment_list().get(1).getFctitle());
                holder.llComment1.setVisibility(View.VISIBLE);
                holder.llComment2.setVisibility(View.VISIBLE);
            }else if(data.get(position).getComment_list().size()==1){
                holder.tvName1.setText(data.get(position).getComment_list().get(0).getUsername()+": ");
                holder.tvComment1.setText(data.get(position).getComment_list().get(0).getFctitle());
                holder.llComment1.setVisibility(View.VISIBLE);
                holder.llComment2.setVisibility(View.GONE);
            }else {
                holder.llComment1.setVisibility(View.GONE);
                holder.llComment2.setVisibility(View.GONE);
            }
            holder.tvAllCommentNum.setText("全部"+data.get(position).getCommentcount()+"条评论");
            holder.tvAllCommentNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                    } else {
                        intent.setClass(context, ArticleCommentActivity.class);
                        intent.putExtra("id", data.get(position).getPfID());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llYouju;
        private LinearLayout llYouji;
        private ImageView ivYoujuAvatar;
        private TextView tvYoujuName;
        private TextView tvYoujuTime;
        private TextView tvYoujuFocus;
        private ImageView ivYoujuTitle;
        private TextView tvYoujuTitle;
        private TextView tvYoujuContent;
        private TextView tvYoujuAddress;
        private TextView tvYoujuLookNum;
        private ImageView ivYoujiAvatar;
        private TextView tvYoujiNickname;
        private TextView tvYoujiTime;
        private TextView tvYoujiFocus;
        private ImageView ivYoujiTitle1;
        private ImageView ivYoujiTitle2;
        private ImageView ivYoujiTitle3;
        private ImageView ivYoujiTitle4;
        private TextView tvYoujiTitle;
        private TextView tvYoujiAddress;
        private TextView tvYoujiLookNum;
        private TextView tvYoujiCommentNum;
        private LinearLayout llYoujiComment;
        private TextView tvYoujiGoodNum;
        private LinearLayout llComment1;
        private TextView tvName1;
        private TextView tvComment1;
        private LinearLayout llComment2;
        private TextView tvName2;
        private TextView tvComment2;
        private TextView tvAllCommentNum;
        private LinearLayout llCity;

        public ViewHolder(View itemView) {
            super(itemView);
            llYouju = itemView.findViewById(R.id.ll_youju);
            llYouji = itemView.findViewById(R.id.ll_youji);
            ivYoujuAvatar = itemView.findViewById(R.id.iv_youju_avatar);
            tvYoujuName = itemView.findViewById(R.id.tv_youju_name);
            tvYoujuTime = itemView.findViewById(R.id.tv_youju_time);
            tvYoujuFocus = itemView.findViewById(R.id.tv_youju_focus);
            ivYoujuTitle = itemView.findViewById(R.id.iv_youju_title);
            tvYoujuTitle = itemView.findViewById(R.id.tv_youju_title);
            tvYoujuContent = itemView.findViewById(R.id.tv_youju_content);
            tvYoujuAddress = itemView.findViewById(R.id.tv_youju_address);
            tvYoujuLookNum = itemView.findViewById(R.id.tv_youju_look_num);
            ivYoujiAvatar = itemView.findViewById(R.id.iv_youji_avatar);
            tvYoujiNickname = itemView.findViewById(R.id.tv_youji_nickname);
            tvYoujiTime = itemView.findViewById(R.id.tv_youji_time);
            tvYoujiFocus = itemView.findViewById(R.id.tv_youji_focus);
            ivYoujiTitle1 = itemView.findViewById(R.id.iv_youji_title1);
            ivYoujiTitle2 = itemView.findViewById(R.id.iv_youji_title2);
            ivYoujiTitle3 = itemView.findViewById(R.id.iv_youji_title3);
            ivYoujiTitle4 = itemView.findViewById(R.id.iv_youji_title4);
            tvYoujiTitle = itemView.findViewById(R.id.tv_youji_title);
            tvYoujiAddress = itemView.findViewById(R.id.tv_youji_address);
            tvYoujiLookNum = itemView.findViewById(R.id.tv_youji_look_num);
            tvYoujiCommentNum = itemView.findViewById(R.id.tv_youji_comment_num);
            llYoujiComment = itemView.findViewById(R.id.ll_youji_comment);
            tvYoujiGoodNum = itemView.findViewById(R.id.tv_youji_good_num);
            llComment1 = itemView.findViewById(R.id.ll_comment1);
            tvName1 = itemView.findViewById(R.id.tv_name1);
            tvComment1 = itemView.findViewById(R.id.tv_comment1);
            llComment2 = itemView.findViewById(R.id.ll_comment2);
            tvName2 = itemView.findViewById(R.id.tv_name2);
            tvComment2 = itemView.findViewById(R.id.tv_comment2);
            tvAllCommentNum = itemView.findViewById(R.id.tv_all_comment_num);
            llCity = itemView.findViewById(R.id.ll_city);
        }
    }

}
