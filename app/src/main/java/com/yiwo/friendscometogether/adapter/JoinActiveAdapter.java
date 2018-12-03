package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.EditContentDialog;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.JoinActivemodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.EditorFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.OrderCommentActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class JoinActiveAdapter extends RecyclerView.Adapter<JoinActiveAdapter.ViewHolder> {

    private Context context;
    private List<JoinActivemodel.ObjBean> data;
    private SpImp spImp;

    public JoinActiveAdapter(List<JoinActivemodel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_join_active, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!StringUtils.isEmpty(data.get(position).getPfpic())) {
            Picasso.with(context).load(data.get(position).getPfpic()).into(holder.picIv);
        }
        holder.titleTv.setText(data.get(position).getPftitle());
        holder.beginTimeTv.setText("开始时间：" + data.get(position).getPfgotime());
        holder.endTimeTv.setText("结束时间：" + data.get(position).getPfendtime());
        holder.priceTv.setText("人均费用：" + data.get(position).getPfspend());
        holder.applyTv.setText("报名人数：" + data.get(position).getJoin_num());
        holder.viewsyTv.setText("浏览：" + data.get(position).getPflook());
        holder.focusOnTv.setText("关注：" + data.get(position).getFocusOn());

        if(data.get(position).getIs_over().equals("0")){
            holder.tvll2.setText("咨询");
            holder.tvll3.setText("取消活动");
        }else if(data.get(position).getIs_over().equals("1")){
            holder.tvll2.setText("评价");
            holder.tvll3.setText("删除");
        }

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("pfID", data.get(position).getPfID());
                intent.setClass(context, DetailsOfFriendTogetherActivity.class);
                context.startActivity(intent);
            }
        });

        holder.rlLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getIs_over().equals("0")){
                    liaotian(data.get(position).getWy_accid());
                }else if(data.get(position).getIs_over().equals("1")){
                    Intent intent = new Intent();
                    intent.putExtra("type", "1");
                    intent.putExtra("orderid", data.get(position).getUjID());
                    intent.setClass(context, OrderCommentActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        holder.rlEnterChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViseHttp.POST(NetConfig.enterChatRoomUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.enterChatRoomUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("activity_id", data.get(position).getPfID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        String teamid = jsonObject.getJSONObject("obj").getString("roomid");
                                        team(teamid);
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

        holder.cancleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getIs_over().equals("0")){
                    EditContentDialog dialog = new EditContentDialog(context, new EditContentDialog.OnReturnListener() {
                        @Override
                        public void onReturn(String content) {
                            if (StringUtils.isEmpty(content)) {
                                Toast.makeText(context, "取消原因不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                ViseHttp.POST(NetConfig.cancleActivityUrl)
                                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.cancleActivityUrl))
                                        .addParam("user_id", spImp.getUID())
                                        .addParam("pfID", data.get(position).getPfID())
                                        .addParam("info", content)
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String obj) {
                                                Log.e("222", obj+"uid"+spImp.getUID()+"pfid"+data.get(position).getPfID());
                                                try {
                                                    JSONObject jsonObject = new JSONObject(obj);
                                                    if (jsonObject.getInt("code") == 200) {
                                                        FocusOnToFriendTogetherModel model = new Gson().fromJson(obj, FocusOnToFriendTogetherModel.class);
                                                        data.remove(position);
                                                        notifyItemRemoved(position);
                                                        notifyDataSetChanged();
                                                        Toast.makeText(context, "活动取消成功", Toast.LENGTH_SHORT).show();
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
                    });
                    dialog.show();
                }else if(data.get(position).getIs_over().equals("1")){
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
            }
        });
    }

    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(context, liaotianAccount);
    }

    private void team(String teamId) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(context, teamId);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView beginTimeTv;
        private TextView endTimeTv;
        private TextView priceTv;
        private TextView applyTv;
        private ImageView picIv;
        private TextView viewsyTv;
        private TextView focusOnTv;
        private RelativeLayout cancleRl;
        private LinearLayout ll;
        private RelativeLayout rlLeader;
        private RelativeLayout rlEnterChatRoom;
        private TextView tvll2;
        private TextView tvll3;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_title);
            beginTimeTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_begin_time);
            endTimeTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_end_time);
            priceTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_price);
            applyTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_apply_num);
            picIv = (itemView).findViewById(R.id.activity_join_active_rv_iv_title);
            viewsyTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_views_num);
            focusOnTv = (itemView).findViewById(R.id.recyclerview_join_active_tv_focus_on_num);
            cancleRl = (itemView).findViewById(R.id.recyclerview_join_active_rl_cancle_active);
            ll = itemView.findViewById(R.id.ll);
            rlLeader = itemView.findViewById(R.id.rl_consulting_leader);
            rlEnterChatRoom = itemView.findViewById(R.id.rl_enter_chat_room);
            tvll2 = itemView.findViewById(R.id.tv_ll2);
            tvll3 = itemView.findViewById(R.id.tv_ll3);
        }
    }

}
