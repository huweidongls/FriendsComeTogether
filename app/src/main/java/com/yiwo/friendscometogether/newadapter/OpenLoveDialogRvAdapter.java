package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.OpenLoveTiaoJianModel;
import com.yiwo.friendscometogether.newpage.EditorLabelActivity;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;

import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by ljc on 2019/8/22.
 */

public class OpenLoveDialogRvAdapter extends RecyclerView.Adapter<OpenLoveDialogRvAdapter.ViewHolder>{

    private List<OpenLoveTiaoJianModel.ObjBean.ItemBean> list;
    private Context context;
    private OnClickJumpListionner listionner;
    public OpenLoveDialogRvAdapter(List<OpenLoveTiaoJianModel.ObjBean.ItemBean> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_openlove, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        OpenLoveDialogRvAdapter.ViewHolder holder = new OpenLoveDialogRvAdapter.ViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getStatus().equals("1")?R.mipmap.choose_green:R.mipmap.choose_gray);
        holder.textView.setText(list.get(position).getText());
        if (list.get(position).getName().equals("renzheng")&&!list.get(position).getStatus().equals("1")){//未完成实名认证跳转
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, RealNameActivity.class);
                    context.startActivity(intent);
                    if (listionner!=null){
                        listionner.startShiMing();
                    }
                }
            });
        }
        if (list.get(position).getName().equals("tags")&&!list.get(position).getStatus().equals("1")){//未完成标签跳转
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, EditorLabelActivity.class);
                    context.startActivity(intent);
                    if (listionner!=null){
                        listionner.startBiaoqian();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list!=null ? list.size():0;
    }

    public void setListionner(OnClickJumpListionner listionner) {
        this.listionner = listionner;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl;
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.iv);
            rl = itemView.findViewById(R.id.rl);
        }
    }
    public interface OnClickJumpListionner{
        void startShiMing();
        void startBiaoqian();
    }
}
