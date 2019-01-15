package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by ljc on 2019/1/9.
 */

public class PrivateMessageLabelAdapter extends RecyclerView.Adapter<PrivateMessageLabelAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public PrivateMessageLabelAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_privatemessage_lable, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        PrivateMessageLabelAdapter.ViewHolder holder = new PrivateMessageLabelAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_label.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_label;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_label = itemView.findViewById(R.id.tv_label);
        }
    }
}
