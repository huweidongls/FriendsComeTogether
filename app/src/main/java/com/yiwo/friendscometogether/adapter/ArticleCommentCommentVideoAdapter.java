package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ActicleCommentVideoModel;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ArticleCommentCommentVideoAdapter extends RecyclerView.Adapter<ArticleCommentCommentVideoAdapter.ViewHolder> {

    private Context context;
    private List<ActicleCommentVideoModel.ObjBean.ReplyListBean> data;
    private OnReplyCommentListener listener;
    private OnDeleteHuiFuListener onDeleteHuiFuListener;
    private SpImp spImp;
    public void setOnReplyCommentListener(OnReplyCommentListener listener){
        this.listener = listener;
    }

    public ArticleCommentCommentVideoAdapter(List<ActicleCommentVideoModel.ObjBean.ReplyListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_article_comment_comment_video, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.imageView);
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv.setText(data.get(position).getVcontent()+"  "+data.get(position).getVctime());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplyComment(data.get(position).getVcID());
            }
        });
        if (spImp.getIsAdmin().equals("1")){
            holder.btn_delete.setVisibility(View.VISIBLE);
        }else {
            holder.btn_delete.setVisibility(View.INVISIBLE);
        }
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spImp.getIsAdmin().equals("1")){
                    onDeleteHuiFuListener.OnDelete(data.get(position).getVcID(),data.get(position).getVcontent());
                }
            }
        });
//        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("举报该评论？")
//                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent();
//                                intent.setClass(context, JuBaoActivity.class);
//                                intent.putExtra("pfID",data.get(position).getVcID());
//                                intent.putExtra("reportUserID",data.get(position).getUserID());
//                                intent.putExtra("type","3");
//                                context.startActivity(intent);
//                            }
//                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnDeleteHuiFuListener(OnDeleteHuiFuListener onDeleteHuiFuListener) {
        this.onDeleteHuiFuListener = onDeleteHuiFuListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private LinearLayout ll;
        private TextView tv_name;
        private ImageView imageView;
        private TextView btn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.activity_article_comment_rv_rv_tv);
            ll = itemView.findViewById(R.id.ll);
            tv_name = itemView.findViewById(R.id.tv_name);
            imageView = itemView.findViewById(R.id.activity_article_comment_rv_rv_iv_avatar);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }

    }

    public interface OnReplyCommentListener{
        void onReplyComment(String ID);
    }
    public interface OnDeleteHuiFuListener{
        void OnDelete(String id,String content);
    }
}
