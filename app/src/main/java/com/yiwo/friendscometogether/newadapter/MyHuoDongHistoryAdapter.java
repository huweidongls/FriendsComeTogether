package com.yiwo.friendscometogether.newadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.OrderCommentActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyHuoDongHistoryAdapter extends RecyclerView.Adapter<MyHuoDongHistoryAdapter.ViewHolder> {

    private Context context;
    private List<HuoDongListModel.ObjBean> data;
    private SpImp spImp;
    public MyHuoDongHistoryAdapter(List<HuoDongListModel.ObjBean> data) {
        this.data = data;
    }

    private DeleteListion deleteListion;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_history, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        final HuoDongListModel.ObjBean bean = data.get(position);
        holder.btn_gochatroom.setFocusable(false);
        holder.btn_gochatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team(bean.getRoomid());
            }
        });
        holder.btn_delete.setFocusable(false);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteListion.deleteHuoDong(position);
//                删除 活动
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.mipmap.ic_launcher);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("是否删除");
                normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViseHttp.POST(NetConfig.deleteActiveUrl)
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.deleteActiveUrl))
                                .addParam("type", "1")
                                .addParam("ujID", data.get(position).getUjID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String obj) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(obj);
                                            if(jsonObject.getInt("code") == 200){
                                                data.remove(position);
                                                notifyItemRemoved(position);
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
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
                });
                normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // 显示
                normalDialog.show();
            }
        });
        holder.btn_pingjia.setFocusable(false);
        holder.btn_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"评价",Toast.LENGTH_SHORT).show();
                //评价
                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.putExtra("orderid", data.get(position).getUjID());
                intent.setClass(context, OrderCommentActivity.class);
                context.startActivity(intent);
            }
        });
        holder.ll_huodong_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                intent.putExtra("pfID",bean.getPfID());
                context.startActivity(intent);
            }
        });
        holder.tv_title.setText(bean.getPftitle());
        Glide.with(context).load(bean.getPfpic()).into(holder.iv_image_huodong);
        holder.tv_name_owner.setText(bean.getUsername());
        holder.tv_name_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("uid", data.get(position).getCaptain());
//                intent.setClass(context, OtherInformationActivity.class);
//                context.startActivity(intent);
            }
        });

        Glide.with(context).load(data.get(position).getUserpic()).into(holder.iv_icon_owner);
        holder.iv_icon_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv_timeinfo.setText(bean.getActivities_data());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private void team(String teamId) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(context, teamId);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView btn_gochatroom;//此按钮已隐藏
        private TextView btn_pingjia;
        private TextView btn_delete;
        private TextView tv_title;
        private ImageView iv_icon_owner;
        private TextView tv_name_owner;
        private TextView tv_timeinfo;
        private ImageView iv_image_huodong;
        private LinearLayout ll_huodong_history;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_gochatroom = itemView.findViewById(R.id.btn_gochatroom);
            btn_pingjia = itemView.findViewById(R.id.btn_pingjia);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            tv_title = itemView.findViewById(R.id.tv_huodong_title);
            iv_icon_owner = itemView.findViewById(R.id.iv_icon_owner);
            tv_name_owner = itemView.findViewById(R.id.tv_name_owner);
            tv_timeinfo = itemView.findViewById(R.id.tv_timeinfo);
            iv_image_huodong = itemView.findViewById(R.id.iv_image_huodong);
            ll_huodong_history = itemView.findViewById(R.id.ll_huodong_history);
        }
    }
    public void setDeleteListion(DeleteListion listion){
        this.deleteListion = listion;
    }
    public interface DeleteListion{
        void deleteHuoDong(int posion);
    }

}
