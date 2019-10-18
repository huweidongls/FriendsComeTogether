package com.yiwo.friendscometogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ArticleCommentVideoAdapter extends RecyclerView.Adapter<ArticleCommentVideoAdapter.ViewHolder> {

    private Context context;
    private List<ActicleCommentVideoModel.ObjBean> data;
    private OnReplyListener listener;
    private OnDeletePinLun deletePinLunLis;
    private SpImp spImp;
    public void setOnReplyListener(OnReplyListener listener){
        this.listener = listener;
    }

    public ArticleCommentVideoAdapter(List<ActicleCommentVideoModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_article_comment_video, parent, false);
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
                it.putExtra("person_id", data.get(position).getUser_ID());
                context.startActivity(it);
            }
        });
        holder.tvNickname.setText(data.get(position).getUsername());
        holder.tvTitle.setText(data.get(position).getVname());
        holder.tvContent.setText(data.get(position).getVcontent());
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
                                intent.putExtra("pfID",data.get(position).getVcID());
                                intent.putExtra("reportUserID",data.get(position).getUser_ID());
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
        holder.tvTime.setText(data.get(position).getVctime());
        if (spImp.getIsAdmin().equals("1")){
            holder.btn_delete_pinglun.setVisibility(View.VISIBLE);
        }else {
            holder.btn_delete_pinglun.setVisibility(View.GONE);
        }
        holder.btn_delete_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deletePinLunLis!=null){
                    deletePinLunLis.onDeletePinLun(data.get(position).getVcID(),data.get(position).getVcontent());
                }
            }
        });
        if (data.get(position).getReplyList().size() > 0) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(manager);
            ArticleCommentCommentVideoAdapter adapter = new ArticleCommentCommentVideoAdapter(data.get(position).getReplyList());
            holder.recyclerView.setAdapter(adapter);
            adapter.setOnReplyCommentListener(new ArticleCommentCommentVideoAdapter.OnReplyCommentListener() {
                @Override
                public void onReplyComment(String ID) {
                    listener.onReply(position, ID);
                }
            });
            adapter.setOnDeleteHuiFuListener(new ArticleCommentCommentVideoAdapter.OnDeleteHuiFuListener() {
                @Override
                public void OnDelete(String id, String content) {
                    deletePinLunLis.onDeletePinLun(id,content);
                }
            });
        }else {
            holder.recyclerView.setVisibility(View.GONE);
        }
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReply(position, data.get(position).getVcID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setDeletePinLunLis(OnDeletePinLun deletePinLunLis) {
        this.deletePinLunLis = deletePinLunLis;
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
            ll = itemView.findViewById(R.id.ll);
            btn_delete_pinglun = itemView.findViewById(R.id.btn_delete_pinglun);
        }
    }

    public interface OnReplyListener{
        void onReply(int position, String id);
    }
    public interface OnDeletePinLun{
        void onDeletePinLun(String id,String content);
    }
}
