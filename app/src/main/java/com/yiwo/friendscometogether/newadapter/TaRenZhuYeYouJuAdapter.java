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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2018/12/26.
 */

public class TaRenZhuYeYouJuAdapter extends RecyclerView.Adapter<TaRenZhuYeYouJuAdapter.ViewHolder>{

    private List<PersonMainModel.ObjBean.ActivityBean> data;
    private Context context;

    private  SpImp spImp;
    private String uid;
    public TaRenZhuYeYouJuAdapter(List<PersonMainModel.ObjBean.ActivityBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tarenzhuye_youju, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        TaRenZhuYeYouJuAdapter.ViewHolder holder = new TaRenZhuYeYouJuAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//            holder.tv_focus
        holder.rl_tarenzhuye_youju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                intent.putExtra("pfID",data.get(position).getPfID());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(data.get(position).getHeadportrait()).into(holder.iv_avatar);
        Glide.with(context).load(data.get(position).getPfpic()).into(holder.iv_image);
        if(data.get(position).getFollow().equals("1")){
            holder.tv_focus.setBackgroundResource(R.drawable.bg_red_24px);
            holder.tv_focus.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_focus.setText("已关注");
        }else {
            holder.tv_focus.setBackgroundResource(R.drawable.bg_red_border_24px);
            holder.tv_focus.setTextColor(Color.parseColor("#D84C37"));
            holder.tv_focus.setText("关注");
        }
        holder.tv_focus.setOnClickListener(new View.OnClickListener() {
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
                                                    holder.tv_focus.setBackgroundResource(R.drawable.bg_red_24px);
                                                    holder.tv_focus.setTextColor(Color.parseColor("#ffffff"));
                                                    holder.tv_focus.setText("已关注");
                                                    data.get(position).setFollow("1");
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    holder.tv_focus.setBackgroundResource(R.drawable.bg_red_border_24px);
                                                    holder.tv_focus.setTextColor(Color.parseColor("#D84C37"));
                                                    holder.tv_focus.setText("+关注");
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
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_title.setText(data.get(position).getPftitle());
        holder.tv_content.setText(data.get(position).getPfcontent());
        holder.tv_location.setText(data.get(position).getPfaddress());
        holder.tv_see_num.setText(data.get(position).getPflook());
        holder.tv_pinglun_num.setText("*");
        holder.tv_time.setText(data.get(position).getPftime());

    }
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rl_tarenzhuye_youju;

        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_location;
        private TextView tv_see_num;
        private TextView tv_pinglun_num;

        private TextView tv_focus;

        private ImageView iv_avatar;
        private ImageView iv_image;

        //        https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009
        public ViewHolder(View itemView) {
            super(itemView);
            rl_tarenzhuye_youju = itemView.findViewById(R.id.rl_tarenzhuye_youju);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_see_num = itemView.findViewById(R.id.tv_see_num);
            tv_pinglun_num = itemView.findViewById(R.id.tv_pinglun_num);
            tv_focus = itemView.findViewById(R.id.tv_focus);

            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }
}
