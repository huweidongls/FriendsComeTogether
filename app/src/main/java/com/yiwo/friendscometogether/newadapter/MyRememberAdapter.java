package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
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
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.pages.EditorFriendRememberActivity;
import com.yiwo.friendscometogether.pages.TeamIntercalationActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyRememberAdapter extends RecyclerView.Adapter<MyRememberAdapter.ViewHolder> {

    private Context context;
    private List<UserRememberModel.ObjBean> data;
    private OnDeleteListener listener;

    public void setListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public MyRememberAdapter(List<UserRememberModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_remember, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFmtitle());
        holder.tvLookNum.setText(data.get(position).getFmlook()+"人阅读了这篇友记");
        Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsWebActivity2.class);
                intent.putExtra("fmid", data.get(position).getFmID());
                context.startActivity(intent);
            }
        });

        holder.tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", data.get(position).getFmID());
                intent.putExtra("draft", "1");
                intent.setClass(context, EditorFriendRememberActivity.class);
                context.startActivity(intent);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(position);
            }
        });

        holder.tvTeamChawen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", data.get(position).getFmID());
                intent.setClass(context, TeamIntercalationActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvLookNum;
        private ImageView iv;
        private LinearLayout ll;
        private TextView tvEditor;
        private TextView tvDelete;
        private TextView tvTeamChawen;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLookNum = itemView.findViewById(R.id.tv_look_num);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
            tvEditor = itemView.findViewById(R.id.tv_editor);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvTeamChawen = itemView.findViewById(R.id.tv_team_chawen);
        }
    }

    public interface OnDeleteListener{
        void onDelete(int i);
    }

}
