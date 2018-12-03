package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24 0024.
 */

public class FriendTogetherLabelAdapter extends RecyclerView.Adapter<FriendTogetherLabelAdapter.ViewHolder> {

    private Context context;
    private List<UserLabelModel.ObjBean> data;
    private OnLabelListener listener;

    public void setOnLabelListener(OnLabelListener listener){
        this.listener = listener;
    }

    public FriendTogetherLabelAdapter(List<UserLabelModel.ObjBean> data) {
        this.data = data;
    }

    //当前Item被点击的位置
    private int currentItem = 0;

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friend_together_label, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getLname());
        if(currentItem == position){
            holder.tv.setSelected(true);
        }else {
            holder.tv.setSelected(false);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLabel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    public interface OnLabelListener{
        void onLabel(int position);
    }

}
