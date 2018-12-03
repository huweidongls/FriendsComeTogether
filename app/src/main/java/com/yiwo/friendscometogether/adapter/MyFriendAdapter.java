package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MyFriendModel;

import java.util.List;

import me.zhouzhuo.zzletterssidebar.adapter.BaseSortListViewAdapter;
import me.zhouzhuo.zzletterssidebar.viewholder.BaseViewHolder;

/**
 * Created by Administrator on 2018/9/4 0004.
 */

public class MyFriendAdapter extends BaseSortListViewAdapter<MyFriendModel.ObjBean, MyFriendAdapter.ViewHolder> {

    private Context context;
    private List<MyFriendModel.ObjBean> data;

    public MyFriendAdapter(Context ctx, List<MyFriendModel.ObjBean> datas) {
        super(ctx, datas);
        this.context = ctx;
        this.data = datas;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_my_friend;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.iv = view.findViewById(R.id.iv);
        holder.tvName = view.findViewById(R.id.tv);
        return holder;
    }

    @Override
    public void bindValues(ViewHolder viewHolder, int position) {
        Glide.with(context).load(data.get(position).getUserpic()).into(viewHolder.iv);
        viewHolder.tvName.setText(data.get(position).getUsername());
    }

    public static class ViewHolder extends BaseViewHolder {
        protected ImageView iv;
        protected TextView tvName;
    }

}
