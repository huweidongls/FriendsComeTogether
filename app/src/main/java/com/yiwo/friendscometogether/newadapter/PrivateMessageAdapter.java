package com.yiwo.friendscometogether.newadapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.netease.nim.uikit.api.NimUIKit;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.PrivateMessageModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by ljc on 2019/1/10.
 */

public class PrivateMessageAdapter extends RecyclerView.Adapter<PrivateMessageAdapter.ViewHolder> {
    private List<PrivateMessageModel.ObjBean> list_data ;
    private Context context;
    private MessageListioner messageListioner;
    private SpImp spImp;
    public void setMessageListioner(MessageListioner messageListioner) {
        this.messageListioner = messageListioner;
    }

    public interface MessageListioner{
        void agreeListion(int position,String messageId,String name,String YX_id);
        void disAgreeListion(int position,String messageId);
        void liaotianListion(String Yx_id);
    }
    public PrivateMessageAdapter(List<PrivateMessageModel.ObjBean> data){
        this.list_data =data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_private, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        spImp = new SpImp(context);
        PrivateMessageAdapter.ViewHolder holder = new PrivateMessageAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_message_time.setText(list_data.get(position).getTimes());
        Glide.with(context).load(list_data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_icon_user);
        holder.iv_icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PersonMainActivity1.class);
                intent.putExtra("person_id",list_data.get(position).getUid());
                context.startActivity(intent);
            }
        });
        if (list_data.get(position).getType().equals("0")){//打招呼消息
            switch (list_data.get(position).getRadio()){
                case "0"://未操作打招呼
                    holder.ll_private_message.setVisibility(View.VISIBLE);
                    holder.ll_say_hello.setVisibility(View.VISIBLE);
                    holder.tv_shenqing_content.setVisibility(View.GONE);
                    if(list_data.get(position).getSex().equals("0")){
                        String str = "您收到来自<font color='#0765AA'>"+"@"+list_data.get(position).getTitleusername()+"</font>帅哥的打招呼。";
                        holder.tv_name_info.setText(Html.fromHtml(str));
                    }else {
                        String str = "您收到来自<font color='#0765AA'>"+"@"+list_data.get(position).getTitleusername()+"</font>美女的打招呼。";
                        holder.tv_name_info.setText(Html.fromHtml(str));
                    }
                    final List<String> list_biaoqian = new ArrayList<>();
                    String[] strs = list_data.get(position).getLabel().split(",");
                    if (strs!=null&&!strs[0].equals("")){
                        for (int i = 0;i<strs.length;i++){
                            list_biaoqian.add(strs[i]);
                        }
                    }
                    Log.d("333",list_biaoqian.size()+"");
                    FlowLayoutManager manager = new FlowLayoutManager(){
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    holder.rv_label.setLayoutManager(manager);
                    holder.rv_label.setAdapter(new PrivateMessageLabelAdapter(list_biaoqian));
                    holder.ll_agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageListioner.agreeListion(position,list_data.get(position).getId(),list_data.get(position).getTitleusername(),list_data.get(position).getYxuser());
                        }
                    });
                    holder.ll_disagree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageListioner.disAgreeListion(position,list_data.get(position).getId());
                        }
                    });
                    break;
                case "1"://已接收聊天
                    holder.ll_private_message.setVisibility(View.VISIBLE);
                    holder.ll_say_hello.setVisibility(View.GONE);
                    String str = "你们现在可以聊天啦";
                    holder.tv_name_info.setText(Html.fromHtml(str));
                    holder.ll_private_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageListioner.liaotianListion(list_data.get(position).getYxuser());
                        }
                    });
                    holder.ll_private_message.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("提示")
                                    .setMessage("确定删除这条消息？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ViseHttp.POST(NetConfig.delFriendInfo)
                                                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.delFriendInfo))
                                                    .addParam("uid", spImp.getUID())
                                                    .addParam("type","0")
                                                    .addParam("id",list_data.get(position).getId())
                                                    .request(new ACallback<String>() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(data);
                                                                if (jsonObject.getInt("code") == 200){
                                                                    list_data.remove(position);
                                                                    notifyDataSetChanged();
                                                                    Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();
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
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                            return false;
                        }
                    });
                    break;
                case "2"://已拒绝聊天
                    holder.ll_private_message.setVisibility(View.GONE);
                    break;
            }
        }else if(list_data.get(position).getType().equals("1")){//申请进群消息
            holder.ll_private_message.setVisibility(View.VISIBLE);
            holder.ll_say_hello.setVisibility(View.VISIBLE);
            holder.tv_shenqing_content.setVisibility(View.VISIBLE);
            holder.tv_shenqing_content.setText(list_data.get(position).getHelloMessage());
            holder.ll_biaoqian.setVisibility(View.GONE);//标签隐藏
            holder.tvAgree.setText("同意");
            holder.tvNoWay.setText("拒绝");
            String str = "<font color='#0765AA'>"+"@"+list_data.get(position).getTitleusername()+"</font>申请进入群聊<font color='#d84c37'>"
                    +"@"+list_data.get(position).getGroup_name()+"</font>";
            holder.tv_name_info.setText(Html.fromHtml(str));
            holder.ll_agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageListioner.agreeListion(position,list_data.get(position).getId(),list_data.get(position).getTitleusername(),list_data.get(position).getYxuser());
                }
            });
            holder.ll_disagree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageListioner.disAgreeListion(position,list_data.get(position).getId());
                }
            });
        }else if(list_data.get(position).getType().equals("2")){
            holder.ll_private_message.setVisibility(View.VISIBLE);
            holder.ll_say_hello.setVisibility(View.GONE);
            holder.tv_shenqing_content.setVisibility(View.GONE);
            String str= "<font color='#0765AA'>"+"@"+list_data.get(position).getTitleusername()+"</font>同意你进入"+"<font color='#d84c37'>"
                    +"@"+list_data.get(position).getGroup_name()+"</font>群聊";
            holder.tv_name_info.setText(Html.fromHtml(str));
        }else if (list_data.get(position).getType().equals("3")){
            holder.tv_shenqing_content.setVisibility(View.GONE);
            holder.ll_private_message.setVisibility(View.VISIBLE);
            holder.ll_say_hello.setVisibility(View.GONE);
            String str="<font color='#0765AA'>"+"@"+list_data.get(position).getTitleusername()+"</font>拒绝你进入"+"<font color='#d84c37'>"
                    +"@"+list_data.get(position).getGroup_name()+"</font>群聊";
            holder.tv_name_info.setText(Html.fromHtml(str));
        }

    }

    @Override
    public int getItemCount() {
        return list_data == null ? 0 : list_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_icon_user;
        private TextView tv_message_time;
        private TextView tv_name_info;
        private NestedRecyclerView rv_label;
        private TextView tv_shenqing_content;
        private LinearLayout ll_private_message,ll_agree,ll_disagree,ll_say_hello,ll_biaoqian;
        private TextView tvAgree,tvNoWay;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_name_info = itemView.findViewById(R.id.tv_name_info);
            rv_label = itemView.findViewById(R.id.rv_label);
            ll_private_message= itemView.findViewById(R.id.ll_private_message);
            ll_agree = itemView.findViewById(R.id.ll_agree);
            ll_disagree = itemView.findViewById(R.id.ll_disagree);
            ll_say_hello = itemView.findViewById(R.id.ll_say_hello);
            ll_biaoqian = itemView.findViewById(R.id.ll_biaoqian);
            tvAgree = itemView.findViewById(R.id.tv_agree);
            tvNoWay = itemView.findViewById(R.id.tv_noway);
            tv_shenqing_content = itemView.findViewById(R.id.tv_shenqing_content);

        }
    }
}
