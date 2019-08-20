package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.netease.nim.uikit.api.NimUIKit;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.EditContentDialog;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyHuoDongApplyAdapter extends RecyclerView.Adapter<MyHuoDongApplyAdapter.ViewHolder> {

    private Context context;
    private List<HuoDongListModel.ObjBean> data;
    private SpImp spImp;

    public MyHuoDongApplyAdapter(List<HuoDongListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_apply, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        Glide.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        final HuoDongListModel.ObjBean bean = data.get(position);
        holder.btn_gochatroom.setFocusable(false);
        holder.btn_gochatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team(bean.getRoomid());
            }
        });
        holder.btn_cancel.setFocusable(false);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditContentDialog dialog = new EditContentDialog(context, new EditContentDialog.OnReturnListener() {
                    @Override
                    public void onReturn(final String content) {
                        if (StringUtils.isEmpty(content)) {
                            Toast.makeText(context, "取消原因不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            ViseHttp.POST(NetConfig.cancleActivityUrl)
                                    .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.cancleActivityUrl))
                                    .addParam("user_id", spImp.getUID())
                                    .addParam("pfID", bean.getPfID())
                                    .addParam("info", content)
                                    .addParam("phase",bean.getPhase())
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
                                                }else {
                                                    Toast.makeText(context, "取消失败", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(context, jsonObject.get("messages")+"", Toast.LENGTH_SHORT).show();
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
            }
        });
        //咨询
        holder.btn_zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liaotian(data.get(position).getWy_accid());
            }
        });
        holder.ll_huodong_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                intent.putExtra("pfID",bean.getPfID());
                context.startActivity(intent);
            }
        });
        holder.tv_title.setText(bean.getPftitle());
        Glide.with(context).load(bean.getPfpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_huodong);
        holder.tv_timeinfo.setText(bean.getActivities_data());
        holder.tv_qishu.setText("第"+data.get(position).getPhase()+"期");
        holder.tv_start_time.setText("开始时间："+data.get(position).getPfgotime());
        holder.tv_end_time.setText("结束时间："+data.get(position).getPfendtime());
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
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(context, liaotianAccount);
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_timeinfo;
        private TextView btn_gochatroom;
        private TextView btn_zixun;
        private TextView btn_cancel;
        private TextView tv_title;
        private ImageView iv_image_huodong;
        private LinearLayout ll_huodong_apply;
        private TextView tv_start_time;
        private TextView tv_end_time;
        private TextView tv_qishu;
        private TextView tv_niming;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_gochatroom = itemView.findViewById(R.id.btn_gochatroom);
            btn_zixun = itemView.findViewById(R.id.btn_zixun);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_title = itemView.findViewById(R.id.tv_huodong_title);
            tv_timeinfo = itemView.findViewById(R.id.tv_timeinfo);
            iv_image_huodong = itemView.findViewById(R.id.iv_image_huodong);
            ll_huodong_apply = itemView.findViewById(R.id.ll_huodong_apply);
            tv_start_time = itemView.findViewById(R.id.tv_start_time);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
            tv_qishu = itemView.findViewById(R.id.tv_qishu);
            tv_niming = itemView.findViewById(R.id.tv_noname);
        }
    }

}
