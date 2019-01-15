package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PrivateMessageModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/1/10.
 */

public class PrivateMessageAdapter extends RecyclerView.Adapter<PrivateMessageAdapter.ViewHolder> {
    private List<PrivateMessageModel.ObjBean> data ;
    private Context context;
    private MessageListioner messageListioner;

    public void setMessageListioner(MessageListioner messageListioner) {
        this.messageListioner = messageListioner;
    }

    public interface MessageListioner{
        void agreeListion(int position,String messageId,String name,String YX_id);
        void disAgreeListion(int position,String messageId);
        void liaotianListion(String Yx_id);
    }
    public PrivateMessageAdapter(List<PrivateMessageModel.ObjBean> data){
        this.data =data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_private, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        PrivateMessageAdapter.ViewHolder holder = new PrivateMessageAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_message_time.setText(data.get(position).getTimes());
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.iv_icon_user);
        holder.iv_icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PersonMainActivity.class);
                intent.putExtra("person_id",data.get(position).getUid());
                context.startActivity(intent);
            }
        });
        switch (data.get(position).getRadio()){
            case "0"://未操作打招呼
                holder.ll_private_message.setVisibility(View.VISIBLE);
                holder.ll_say_hello.setVisibility(View.VISIBLE);
                if(data.get(position).getSex().equals("0")){
                    String str = "您收到来自<font color='#0765AA'>"+"@"+data.get(position).getTitleusername()+"</font>帅哥的打招呼。";
                    holder.tv_name_info.setText(Html.fromHtml(str));
                }else {
                    String str = "您收到来自<font color='#0765AA'>"+"@"+data.get(position).getTitleusername()+"</font>美女的打招呼。";
                    holder.tv_name_info.setText(Html.fromHtml(str));
                }
                final List<String> list_biaoqian = new ArrayList<>();
                String[] strs = data.get(position).getLabel().split(",");
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
                        messageListioner.agreeListion(position,data.get(position).getId(),data.get(position).getTitleusername(),data.get(position).getYxuser());
                    }
                });
                holder.ll_disagree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageListioner.disAgreeListion(position,data.get(position).getId());
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
                        messageListioner.liaotianListion(data.get(position).getYxuser());
                    }
                });
                break;
            case "2"://已拒绝聊天
                holder.ll_private_message.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_icon_user;
        private TextView tv_message_time;
        private TextView tv_name_info;
        private NestedRecyclerView rv_label;
        private LinearLayout ll_private_message,ll_agree,ll_disagree,ll_say_hello;
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

        }
    }
}
