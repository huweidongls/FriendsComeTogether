package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.EditorLabelModel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class EditorLabelListAdapter extends RecyclerView.Adapter<EditorLabelListAdapter.ViewHolder> {

    private Context context;
    private List<EditorLabelModel.ObjBean> data;

    public EditorLabelListAdapter(List<EditorLabelModel.ObjBean> data) {
        this.data = data;
    }

    public List<EditorLabelModel.ObjBean> getList(){
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_editor_label_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getT_name());
        if(data.get(position).getDatetype().equals("1")){
            holder.ivSure.setVisibility(View.VISIBLE);
        }else {
            holder.ivSure.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getDatetype().equals("1")){
                    holder.ivSure.setVisibility(View.GONE);
                    data.get(position).setDatetype("0");
                    notifyDataSetChanged();
                }else {
                    holder.ivSure.setVisibility(View.VISIBLE);
                    data.get(position).setDatetype("1");
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private ImageView ivSure;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            ivSure = itemView.findViewById(R.id.iv_sure);
        }
    }

}
