package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.GetActiveIntercalationInfoModel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */

public class ModifyFriendTogetherIntercalationPicAdapter extends RecyclerView.Adapter<ModifyFriendTogetherIntercalationPicAdapter.ViewHolder> {

    private Context context;
    private List<GetActiveIntercalationInfoModel.ObjBean.ImgsBean> data;
    private OnModifyListener listener;

    public void setOnModifyListener(OnModifyListener listener){
        this.listener = listener;
    }

    public ModifyFriendTogetherIntercalationPicAdapter(List<GetActiveIntercalationInfoModel.ObjBean.ImgsBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_modify_intercalation_pic, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(!TextUtils.isEmpty(data.get(position).getPfpurl())){
            Picasso.with(context).load(data.get(position).getPfpurl()).into(holder.iv);
        }
        holder.tv.setText(data.get(position).getPfpcontent());
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModify(1, position);
            }
        });
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModify(2, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;
        private ImageView ivDel;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_create_intercalation_rv_iv_img);
            tv = itemView.findViewById(R.id.activity_create_intercalation_rv_tv_content);
            ivDel = itemView.findViewById(R.id.activity_create_intercalation_rv_iv_delete);
        }
    }

    public interface OnModifyListener{
        void onModify(int type, int position);
    }

}
