package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FocusOnLeaderModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.ActiveEvaluationActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/7/18.
 */

public class FriendTogetherUpDataAdapter extends RecyclerView.Adapter<FriendTogetherUpDataAdapter.ViewHolder> {
    List<FriendsTogethermodel.ObjBean> data;
    private Context context;
    //    View v;
//    ImageView iv;
    SpImp spImp;

    public FriendTogetherUpDataAdapter(List<FriendsTogethermodel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_friend_together, parent, false);
//        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false);
        spImp = new SpImp(context);
//        iv = (ImageView) v.findViewById(R.id.item_pic);
        ScreenAdapterTools.getInstance().loadView(view);
//        ScreenAdapterTools.getInstance().loadView(v);
        FriendTogetherUpDataAdapter.ViewHolder holder = new FriendTogetherUpDataAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.i("123456", data.get(position).getFocusOn());
        if (!StringUtils.isEmpty(data.get(position).getPfpic())) {
            Picasso.with(context).load(data.get(position).getPfpic()).into(holder.picIv);
        }
        holder.titleTv.setText(data.get(position).getPftitle());
        holder.contentTv.setText(data.get(position).getPfcontent());
        if (!StringUtils.isEmpty(data.get(position).getUpicurl())) {
            Picasso.with(context).load(data.get(position).getUpicurl()).into(holder.headIv);
        }
        holder.tvStartTime.setText("开始时间: " + data.get(position).getPfgotime());
        if (!StringUtils.isEmpty(data.get(position).getCaptain()) && !data.get(position).getCaptain().equals("0")) {
            if (data.get(position).getSign().equals("1")) {
                Glide.with(context).load(R.mipmap.hg_yellow).into(holder.ivLevel);
            } else {
                Glide.with(context).load(R.mipmap.hg_gray).into(holder.ivLevel);
            }
        }

//        holder.levelBg.setBackgroundResource(data.get(position).getUsergrade().equals("1") ? R.mipmap.level_golden_yellow : R.mipmap.level_red);
//        holder.levelTv.setText("LV" + data.get(position).getUsergrade());
        holder.personTv.setText("参与人数：" + data.get(position).getHave_num() + "/" + data.get(position).getPfpeople());
        holder.focusOnIv.setImageResource(data.get(position).getFocusOn().equals("0") ? R.mipmap.focus_on_empty_y : R.mipmap.focus_on_y);
//        holder.focusOnBtn.setText(data.get(position).getCaptain_focusOn().equals("0") ? "+ 关注" : "已关注");
//        holder.focusOnBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (spImp.getUID().equals("0")) {
//                    context.startActivity(new Intent(context, LoginActivity.class));
//                } else {
//                    ViseHttp.POST(NetConfig.focusOnLeaderUrl)
//                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnLeaderUrl))
//                            .addParam("userID", spImp.getUID())
//                            .addParam("attention_userID", data.get(position).getCaptain())
//                            .request(new ACallback<String>() {
//                                @Override
//                                public void onSuccess(String data) {
//                                    try {
//                                        JSONObject js = new JSONObject(data);
//                                        if (js.getInt("code") == 200) {
//                                            FocusOnLeaderModel model = new Gson().fromJson(data, FocusOnLeaderModel.class);
//                                            if (model.getCode() == 200) {
//                                                if (model.getObj().getAttention().equals("0")) {
//                                                    holder.focusOnBtn.setText("+ 关注");
//                                                } else {
//                                                    holder.focusOnBtn.setText("已关注");
//                                                }
//                                            }
//                                        } else {
//                                            Toast.makeText(context, js.getString("message"), Toast.LENGTH_SHORT).show();
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFail(int errCode, String errMsg) {
//
//                                }
//                            });
//                }
//            }
//        });
//        holder.focusOnll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViseHttp.POST(NetConfig.focusOnLeaderUrl)
//                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.focusOnLeaderUrl))
//                        .addParam("userID",spImp.getUID())
//                        .addParam("attention_userID",data.get(position).getCaptain())
//                        .request(new ACallback<String>() {
//                            @Override
//                            public void onSuccess(String data) {
//                                FocusOnLeaderModel model = new Gson().fromJson(data,FocusOnLeaderModel.class);
//                                if(model.getCode()==200){
//                                    if(model.getObj().getAttention().equals("0")){
//                                        holder.focusOnLeaderIv.setImageResource(R.mipmap.focus_on_empty_y);
//                                    } else {
//                                        holder.focusOnLeaderIv.setImageResource(R.mipmap.focus_on_y);
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFail(int errCode, String errMsg) {
//
//                            }
//                        });
//            }
//        });
        holder.personll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, OtherInformationActivity.class);
                it.putExtra("uid", data.get(position).getCaptain());
                context.startActivity(it);
            }
        });
        holder.focus_on_activitiesLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = spImp.getUID();
                if (userID.equals("0")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", userID)
                            .addParam("pfID", data.get(position).getPfID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                    if (model.getCode() == 200) {
                                        if (model.getObj().equals("1")) {
                                            holder.focusOnIv.setImageResource(R.mipmap.focus_on_y);
                                            Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            holder.focusOnIv.setImageResource(R.mipmap.focus_on_empty_y);
                                            Toast.makeText(context, "取消成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }

            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                if (TextUtils.isEmpty(data.get(position).getPfpwd())) {
                    intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                    intent.putExtra("pfID", data.get(position).getPfID());
                    context.startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public void setActivityText(String s) {
                            if (s.equals(data.get(position).getPfpwd())) {
                                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                                intent.putExtra("pfID", data.get(position).getPfID());
                                context.startActivity(intent);
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });
        holder.look_overLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ActiveEvaluationActivity.class);
                intent.putExtra("pfID", data.get(position).getPfID());
                context.startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.vessel.setLayoutManager(manager);
//        List<String> mList = new ArrayList<>();
//        mList.addAll(data.get(position).getAll_u_pic());
        FriendTogetherJoinPeopleAdapter adapter = new FriendTogetherJoinPeopleAdapter(data.get(position).getAll_u_pic());
        holder.vessel.setAdapter(adapter);

//        if (data.get(position).getAll_u_pic().size() < 8) {
//            for (int i = 0; i < data.get(position).getAll_u_pic().size(); i++) {
//                if (!StringUtils.isEmpty(data.get(position).getAll_u_pic().get(i))) {
//                    Picasso.with(context).load(data.get(position).getAll_u_pic().get(i)).into(iv);
//                }
//                if (iv.getParent() != null) {
//                    ((ViewGroup) iv.getParent()).removeView(iv);
//                    holder.vessel.addView(iv);
//                }
//            }
//        } else {
//            for (int i = 0; i < 8; i++) {
//                if (!StringUtils.isEmpty(data.get(position).getAll_u_pic().get(i))) {
//                    Picasso.with(context).load(data.get(position).getAll_u_pic().get(i)).into(iv);
//                }
//                if (iv.getParent() != null) {
//                    ((ViewGroup) iv.getParent()).removeView(iv);
//                    holder.vessel.addView(iv);
//                }
//            }
//        }
        holder.llleader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = spImp.getUID();
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    liaotian(data.get(position).getWy_accid());
                }
            }
        });
    }

    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(context, liaotianAccount);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        //        private ImageView focusOnLeaderIv;
        private TextView titleTv;
        private TextView contentTv;
        private RecyclerView vessel;
        private ImageView headIv;
        private ImageView focusOnIv;
        //        private RelativeLayout levelBg;
//        private TextView levelTv;
        private TextView personTv;
        private LinearLayout look_overLl;
        private LinearLayout consult_leaderLl;
        private LinearLayout focus_on_activitiesLl;
        private LinearLayout ll;
        private LinearLayout personll;
        //        private Button focusOnBtn;
        //        private LinearLayout focusOnll;
        private LinearLayout llleader;
        private TextView tvStartTime;
        private ImageView ivLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            picIv = (itemView).findViewById(R.id.fragment_friend_together_rv_iv_title);
            titleTv = (itemView).findViewById(R.id.titleTv);
            contentTv = (itemView).findViewById(R.id.contentTv);
            vessel = (itemView).findViewById(R.id.vessel);
//            levelBg = (itemView).findViewById(R.id.item_levelBg);
            headIv = (itemView).findViewById(R.id.headIv);
//            levelTv = (itemView).findViewById(R.id.levelTv);
            personTv = (itemView).findViewById(R.id.item_person);
            look_overLl = (itemView).findViewById(R.id.look_overLl);
            focusOnIv = (itemView).findViewById(R.id.focus_on_iv);
            consult_leaderLl = (itemView).findViewById(R.id.consult_leaderLl);
            focus_on_activitiesLl = (itemView).findViewById(R.id.focus_on_activitiesLl);
            ll = (itemView).findViewById(R.id.recyclerview_fragment_friend_together_ll);
            personll = (itemView).findViewById(R.id.recyclerview_fragment_friend_together_ll_person_content);
//            focusOnll = (itemView).findViewById(R.id.recyclerview_fragment_friend_together_ll_top_focus);
//            focusOnLeaderIv = (itemView).findViewById(R.id.recyclerview_fragment_friend_together_iv_focus);
//            focusOnBtn = (itemView).findViewById(R.id.recyclerview_fragment_friend_together_btn_top_focus);
            llleader = itemView.findViewById(R.id.consult_leaderLl);
            tvStartTime = itemView.findViewById(R.id.start_time);
            ivLevel = itemView.findViewById(R.id.iv_level);
        }
    }
}
