package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.OtherInformationModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/11.
 */

public class OtherInformationWorksAdapter extends RecyclerView.Adapter<OtherInformationWorksAdapter.ViewHolder> {

    private Context context;
    private List<OtherInformationModel.ObjBean.ListPicNewsBean> data;

    public OtherInformationWorksAdapter(List<OtherInformationModel.ObjBean.ListPicNewsBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_other_information_works, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(data.get(position).getFmpic())) {
            Picasso.with(context).load(data.get(position).getFmpic()).into(holder.iv);
        }
        holder.tvTitle.setText(data.get(position).getFmtitle());
        holder.tvLookNum.setText("浏览: " + data.get(position).getFmlook());
        holder.tvCommentNum.setText("评论: " + data.get(position).getFmcomment());
        holder.tvCollectionNum.setText("收藏: " + data.get(position).getFmfavorite());
        holder.tvPraiseNum.setText("赞: " + data.get(position).getFmgood());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("fmid", data.get(position).getFmID());
                intent.setClass(context, DetailsOfFriendsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvLookNum;
        private TextView tvCommentNum;
        private TextView tvCollectionNum;
        private TextView tvPraiseNum;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.activity_other_information_rv_works_iv);
            tvTitle = itemView.findViewById(R.id.activity_other_information_rv_works_tv_title);
            tvLookNum = itemView.findViewById(R.id.activity_other_information_rv_works_tv_look_num);
            tvCommentNum = itemView.findViewById(R.id.activity_other_information_rv_works_tv_comment_num);
            tvCollectionNum = itemView.findViewById(R.id.activity_other_information_rv_works_tv_collection_num);
            tvPraiseNum = itemView.findViewById(R.id.activity_other_information_rv_works_tv_praise_num);
            ll = itemView.findViewById(R.id.activity_other_information_rv_works_ll_item);
        }
    }

}
