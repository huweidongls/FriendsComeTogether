package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class LabelChooseOneAdapter extends RecyclerView.Adapter<LabelChooseOneAdapter.ViewHolder> {

    private Context context;
    private List<UserLabelModel.ObjBean> data;
    private OnSelectLabelListener listener;

    public void setListener(OnSelectLabelListener listener) {
        this.listener = listener;
    }

    public LabelChooseOneAdapter(List<UserLabelModel.ObjBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_label, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(!data.get(position).isChoose()){
            holder.tv.setBackgroundResource(R.drawable.bg_gray_border_5dp);
            holder.tv.setTextColor(Color.parseColor("#979797"));
        }else {
            holder.tv.setBackgroundResource(R.drawable.bg_redd84c37_border_5dp);
            holder.tv.setTextColor(Color.parseColor("#d84c37"));
        }
        holder.tv.setText(data.get(position).getLname());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface OnSelectLabelListener{
        void onSelete(int i);
    }

}
