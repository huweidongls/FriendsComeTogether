package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ArticleCommentCommentAdapter extends RecyclerView.Adapter<ArticleCommentCommentAdapter.ViewHolder> {

    private Context context;
    private List<ArticleCommentListModel.ObjBean.PicBean> data;
    private OnReplyCommentListener listener;

    public void setOnReplyCommentListener(OnReplyCommentListener listener){
        this.listener = listener;
    }

    public ArticleCommentCommentAdapter(List<ArticleCommentListModel.ObjBean.PicBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_article_comment_comment, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getFctitle());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplyComment(data.get(position).getFcID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.activity_article_comment_rv_rv_tv);
        }
    }

    public interface OnReplyCommentListener{
        void onReplyComment(String ID);
    }

}
