package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.DetailsRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class DetailsOfFriendsIntercalation1Adapter extends RecyclerView.Adapter<DetailsOfFriendsIntercalation1Adapter.ViewHolder> {

    private Context context;
    private List<DetailsRememberModel.ObjBean.InserListBean> data;
    private SpImp spImp;

    public DetailsOfFriendsIntercalation1Adapter(List<DetailsRememberModel.ObjBean.InserListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_details_of_friends_intercalation1, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        spImp = new SpImp(context);
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.ivAvatar);
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvTime.setText(data.get(position).getFftime());
        if(data.get(position).getFollow().equals("0")){
            holder.tvIsFocus.setText("+关注");
        }else if(data.get(position).getFollow().equals("1")){
            holder.tvIsFocus.setText("已关注");
        }
        holder.tvTitle.setText(data.get(position).getFftitle());
        holder.tvContent.setText(data.get(position).getFfcontect());
        LinearLayoutManager manager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(manager);
        DetailsOfFriendsUpDataAdapter adapter;
        adapter = new DetailsOfFriendsUpDataAdapter(data.get(position).getPic());
        holder.recyclerView.setAdapter(adapter);

        //头像链接
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, OtherInformationActivity.class);
                intent.putExtra("uid", data.get(position).getUserID());
                context.startActivity(intent);
            }
        });

        holder.tvIsFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getFollow().equals("0")){
                    if(spImp.getUID().equals("0")|| TextUtils.isEmpty(spImp.getUID())){
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }else {
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.userFocus))
                                .addParam("uid", spImp.getUID())
                                .addParam("likeId", data.get(position).getUserID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                holder.tvIsFocus.setText("已关注");
                                                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvContent;
        private RecyclerView recyclerView;
        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvTime;
        private TextView tvIsFocus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_details_of_friends_rv_tv_title);
            tvContent = itemView.findViewById(R.id.activity_details_of_friends_rv_tv_content);
            recyclerView = itemView.findViewById(R.id.activity_details_of_friends_rv_rv);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvIsFocus = itemView.findViewById(R.id.tv_is_focus);
        }
    }

}
