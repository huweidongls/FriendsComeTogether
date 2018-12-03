package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.EditorFriendRememberModel;
import com.yiwo.friendscometogether.model.GetEditorFriendTogetherModel;
import com.yiwo.friendscometogether.pages.EditorFriendTogetherSubTitleContentActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class EditorFriendTogetherAdapter extends RecyclerView.Adapter<EditorFriendTogetherAdapter.ViewHolder> {

    private Context context;
    private List<GetEditorFriendTogetherModel.ObjBean.TitleListBean> data;

    public EditorFriendTogetherAdapter(List<GetEditorFriendTogetherModel.ObjBean.TitleListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_editor_friend_together, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getPfptitle());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, EditorFriendTogetherSubTitleContentActivity.class);
                it.putExtra("id",data.get(position).getId());
                it.putExtra("title",data.get(position).getPfptitle());
                it.putExtra("content",data.get(position).getPfpcontent());
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_editor_friend_together_rv_tv);
            rl = itemView.findViewById(R.id.activity_editor_friend_together_rv_rl);
        }
    }

}
