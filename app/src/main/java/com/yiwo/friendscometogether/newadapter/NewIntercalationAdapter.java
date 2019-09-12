package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.PicDescribeDialog;
import com.yiwo.friendscometogether.model.ModifyFriendRememberModel;
import com.yiwo.friendscometogether.model.UserIntercalationPicModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/28.
 */

public class NewIntercalationAdapter extends RecyclerView.Adapter<NewIntercalationAdapter.ViewHolder> {

    private Context context;
    private List<ModifyFriendRememberModel.ObjBean.FmpicBean> data;

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private static final int MAX_SIZE = 9;

    private int picNum;

    private boolean isDescribe = true;

    public void setDescribe(boolean describe) {
        isDescribe = describe;
    }

    private OnAddImgListener addListener;
    private OnDeleteImgListener deleteImgListener;
    private OnAddDescribeListener describeListener;
    private OnSetFirstPicListienner setFirstPicListienner;
    public void setListener(OnAddImgListener addListener, OnDeleteImgListener deleteImgListener, OnAddDescribeListener describeListener,OnSetFirstPicListienner setFirstPicListienner) {
        this.addListener = addListener;
        this.deleteImgListener = deleteImgListener;
        this.describeListener = describeListener;
        this.setFirstPicListienner = setFirstPicListienner;
    }

    public NewIntercalationAdapter(List<ModifyFriendRememberModel.ObjBean.FmpicBean> data) {
        this.data = data;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_intercalation_pic, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(!isDescribe){
            holder.tvContent.setVisibility(View.GONE);
        }
        if (data.size() >= MAX_SIZE) {
            //最多9张
            holder.rlAdd.setVisibility(View.GONE);
        } else {
            holder.rlImg.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.rlAdd.setVisibility(View.VISIBLE);
        }
        if (getItemViewType(position) == TYPE_ADD) {
            holder.tvPicNum.setText(picNum + position + "/9");
            holder.rlImg.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
            holder.rl_is_first.setVisibility(View.GONE);
        } else {
            holder.rlAdd.setVisibility(View.GONE);
            holder.rlImg.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getPic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
            if (data.get(position).getType().equals("0")){
                holder.rl_is_first.setVisibility(View.GONE);
            }else if (data.get(position).getType().equals("1")){
                holder.rl_is_first.setVisibility(View.VISIBLE);
            }else {
                holder.rl_is_first.setVisibility(View.GONE);
            }
        }
        holder.rlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirstPicListienner.onSetFirst(position);
            }
        });
        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListener.onAddImg();
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImgListener.onDeleteImg(position);
            }
        });
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicDescribeDialog dialog = new PicDescribeDialog(context);
                dialog.show();
                dialog.setOnReturnListener(new PicDescribeDialog.OnReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        describeListener.onAddDescribe(position, title);
                    }
                });
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 1;
    }

    public void setSetFirstPicListienner(OnSetFirstPicListienner setFirstPicListienner) {
        this.setFirstPicListienner = setFirstPicListienner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlAdd;
        private TextView tvPicNum;
        private RelativeLayout rlImg;
        private ImageView iv;
        private TextView tvContent;
        private ImageView ivDelete;
        private RelativeLayout rl_is_first;
        public ViewHolder(View itemView) {
            super(itemView);
            rlAdd = itemView.findViewById(R.id.activity_create_intercalation_rv_rl_add);
            tvPicNum = itemView.findViewById(R.id.activity_create_intercalation_rv_tv_img_num);
            rlImg = itemView.findViewById(R.id.activity_create_intercalation_rv_rl_img);
            iv = itemView.findViewById(R.id.activity_create_intercalation_rv_iv_img);
            tvContent = itemView.findViewById(R.id.activity_create_intercalation_rv_tv_content);
            ivDelete = itemView.findViewById(R.id.activity_create_intercalation_rv_iv_delete);
            rl_is_first = itemView.findViewById(R.id.rl_is_first);
        }
    }

    public interface OnAddImgListener {
        void onAddImg();
    }

    public interface OnDeleteImgListener {
        void onDeleteImg(int i);
    }

    public interface OnAddDescribeListener {
        void onAddDescribe(int i, String s);
    }
    public interface OnSetFirstPicListienner{
        void onSetFirst(int postion);
    }
}
