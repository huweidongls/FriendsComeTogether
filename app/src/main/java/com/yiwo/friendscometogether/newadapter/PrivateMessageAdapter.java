package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
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
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.PrivateMessageModel;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/1/10.
 */

public class PrivateMessageAdapter extends RecyclerView.Adapter<PrivateMessageAdapter.ViewHolder> {
    private List<PrivateMessageModel.ObjBean> data ;
    private Context context;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_message_time.setText(data.get(position).getTimes());
        if(data.get(position).getSex().equals("0")){
            String str = "您收到来自<font color='#0765AA'>"+"@"+data.get(position).getTitleusername()+"</font>帅哥的打招呼。";
            holder.tv_name_info.setText(Html.fromHtml(str));
        }else {
            String str = "您收到来自<font color='#0765AA'>"+"@"+data.get(position).getTitleusername()+"</font>美女的打招呼。";
            holder.tv_name_info.setText(Html.fromHtml(str));
        }
        Glide.with(context).load(data.get(position).getUserpic()).into(holder.iv_icon_user);
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
        holder.rv_label.setAdapter(new SuperLikeLabelAdapter(list_biaoqian));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_icon_user;
        private TextView tv_message_time;
        private TextView tv_name_info;
        private RecyclerView rv_label;
        private LinearLayout ll_agree,ll_disagree;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon_user = itemView.findViewById(R.id.iv_icon_user);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_name_info = itemView.findViewById(R.id.tv_name_info);
            rv_label = itemView.findViewById(R.id.rv_label);
            ll_agree = itemView.findViewById(R.id.ll_agree);
            ll_disagree = itemView.findViewById(R.id.ll_disagree);

        }
    }
}
