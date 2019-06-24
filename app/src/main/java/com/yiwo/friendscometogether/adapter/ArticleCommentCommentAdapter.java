package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;

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
        holder.tv.setText(data.get(position).getUsername()+"："+data.get(position).getFctitle());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplyComment(data.get(position).getFcID());
            }
        });
        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("举报该评论？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(context, JuBaoActivity.class);
                                intent.putExtra("pfID",data.get(position).getFcID());
                                intent.putExtra("reportUserID",data.get(position).getUserID());
                                intent.putExtra("type","3");
                                context.startActivity(intent);
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.activity_article_comment_rv_rv_tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnReplyCommentListener{
        void onReplyComment(String ID);
    }

}
