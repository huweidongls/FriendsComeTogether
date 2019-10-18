package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ArticleCommentAdapter extends RecyclerView.Adapter<ArticleCommentAdapter.ViewHolder> {

    private Context context;
    private List<ArticleCommentListModel.ObjBean> data;
    private OnReplyListener listener;
    private OnDeleteListener deleteListener;
    private SpImp spImp;
    public void setOnReplyListener(OnReplyListener listener){
        this.listener = listener;
    }

    public ArticleCommentAdapter(List<ArticleCommentListModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_article_comment, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.ivAvatar);
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, PersonMainActivity1.class);
                it.putExtra("person_id", data.get(position).getUserID());
                context.startActivity(it);
            }
        });
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvTitle.setText(data.get(position).getNewsTile());
        holder.tvContent.setText(data.get(position).getFctitle());
        if (spImp.getIsAdmin().equals("1")){
            holder.btn_delete_pinglun.setVisibility(View.VISIBLE);
        }else {
            holder.btn_delete_pinglun.setVisibility(View.GONE);
        }
        holder.btn_delete_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener!=null){
                    deleteListener.onDelete(data.get(position).getFcID(),data.get(position).getFctitle());
                }
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
        holder.tvTime.setText(data.get(position).getFctime());
        if (data.get(position).getPic().size() > 0) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(manager);
            ArticleCommentCommentAdapter adapter = new ArticleCommentCommentAdapter(data.get(position).getPic());
            holder.recyclerView.setAdapter(adapter);
            adapter.setOnReplyCommentListener(new ArticleCommentCommentAdapter.OnReplyCommentListener() {
                @Override
                public void onReplyComment(String ID) {
                    listener.onReply(position, ID);
                }
            });
            adapter.setDeleteListener(new ArticleCommentCommentAdapter.OnDeleteListener() {
                @Override
                public void onDelete(String id,String content) {
                    deleteListener.onDelete(id,content);
                }
            });
        }else {
            holder.recyclerView.setVisibility(View.GONE);
        }
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReply(position, data.get(position).getFcID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvNickname;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private RecyclerView recyclerView;
        private TextView tvReply;
        private TextView btn_delete_pinglun;
        private LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.activity_article_comment_rv_iv_avatar);
            tvNickname = itemView.findViewById(R.id.activity_article_comment_rv_tv_nickname);
            tvTitle = itemView.findViewById(R.id.activity_article_comment_rv_tv_title);
            tvContent = itemView.findViewById(R.id.activity_article_comment_rv_tv_content);
            tvTime = itemView.findViewById(R.id.activity_article_comment_rv_tv_time);
            recyclerView = itemView.findViewById(R.id.activity_article_comment_rv_rv);
            tvReply = itemView.findViewById(R.id.activity_article_comment_rv_tv_reply);
            btn_delete_pinglun = itemView.findViewById(R.id.btn_delete_pinglun);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnReplyListener{
        void onReply(int position, String id);
    }
    public interface OnDeleteListener{
        void onDelete(String id,String content);
    }
}
