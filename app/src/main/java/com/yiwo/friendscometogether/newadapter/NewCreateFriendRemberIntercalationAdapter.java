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
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.PicDescribeDialog;
import com.yiwo.friendscometogether.model.NewUserIntercalationPicModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class NewCreateFriendRemberIntercalationAdapter extends RecyclerView.Adapter<NewCreateFriendRemberIntercalationAdapter.ViewHolder> {

    private Context context;
    private List<NewUserIntercalationPicModel> data;

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
    private OnSetFirstPicListienner setFirdtPicListienner;

    public void setListener(OnAddImgListener addListener, OnDeleteImgListener deleteImgListener, OnAddDescribeListener describeListener,OnSetFirstPicListienner setFirdtPicListienner) {
        this.addListener = addListener;
        this.deleteImgListener = deleteImgListener;
        this.describeListener = describeListener;
        this.setFirdtPicListienner = setFirdtPicListienner;
    }

    public NewCreateFriendRemberIntercalationAdapter(List<NewUserIntercalationPicModel> data) {
        this.data = data;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_intercalation_pic_new, parent, false);
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
            Glide.with(context).load("file://" + data.get(position).getPic()).into(holder.iv);
            if (!data.get(position).getDescribe().equals("")) {
                holder.tvContent.setText(data.get(position).getDescribe());
            } else {
                holder.tvContent.setText(null);
            }
            if (data.get(position).getFirstPic()){
                holder.rl_is_first.setVisibility(View.VISIBLE);
            }else {
                holder.rl_is_first.setVisibility(View.GONE);
            }
        }
//        //不在此界面添加照片 隐藏 添加按钮----------
//        holder.rlAdd.setVisibility(View.GONE);//  \
//        //------------------------------------------
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
        holder.rlImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //20190910 点击图片  不再是添加描述，改为设置首图！
//                PicDescribeDialog dialog = new PicDescribeDialog(context);
//                dialog.show();
//                dialog.setOnReturnListener(new PicDescribeDialog.OnReturnListener() {
//                    @Override
//                    public void onReturn(String title) {
//                        describeListener.onAddDescribe(position, title);
//                    }
//                });
                setFirdtPicListienner.onSetFirst(position);

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
