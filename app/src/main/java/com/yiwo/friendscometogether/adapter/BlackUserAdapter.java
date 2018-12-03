package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.BlackUserModel;

import java.util.List;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class BlackUserAdapter extends BaseAdapter {

    private Context context;
    private List<BlackUserModel.ObjBean> data;
    private LayoutInflater inflater;

    public BlackUserAdapter(Context context, List<BlackUserModel.ObjBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_my_friend, null);
            holder.iv = view.findViewById(R.id.iv);
            holder.tv = view.findViewById(R.id.tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(data.get(i).getUserpic()).into(holder.iv);
        holder.tv.setText(data.get(i).getUsername());

        return view;
    }

    class ViewHolder {
        private ImageView iv;
        private TextView tv;
    }

}
