package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.newmodel.YouJuTopLabelModel;

import java.util.List;

/**
 * Created by ljc on 2019/1/9.
 */

public class FriendsTogetherFragmentLabelTopAdapter extends RecyclerView.Adapter<FriendsTogetherFragmentLabelTopAdapter.ViewHolder> {

    private Context context;
    private List<YouJuTopLabelModel> data;
    private OnSelelectedListionner onSelelectedListionner;
    public FriendsTogetherFragmentLabelTopAdapter(List<YouJuTopLabelModel> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friend_together_top_lable, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        FriendsTogetherFragmentLabelTopAdapter.ViewHolder holder = new FriendsTogetherFragmentLabelTopAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_label.setText(data.get(position).getName());
        holder.tv_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelelectedListionner.onSelect(position);
            }
        });
        if (data.get(position).getIstSelected()){
            holder.tv_label.setTextSize(18);
            holder.tv_label.setTextColor(Color.parseColor("#333333"));
            holder.tv_label.getPaint().setFakeBoldText(true);
            holder.bottom_line.setVisibility(View.VISIBLE);
        }else {
            holder.tv_label.setTextSize(17);
            holder.tv_label.setTextColor(Color.parseColor("#696969"));
            holder.tv_label.getPaint().setFakeBoldText(false);
            holder.bottom_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnSelelectedListionner(OnSelelectedListionner onSelelectedListionner) {
        this.onSelelectedListionner = onSelelectedListionner;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_label;
        View bottom_line;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_label = itemView.findViewById(R.id.tv_label);
            bottom_line = itemView.findViewById(R.id.bottom_line);
        }
    }
    public interface OnSelelectedListionner{
        void onSelect(int position);
    }
}
