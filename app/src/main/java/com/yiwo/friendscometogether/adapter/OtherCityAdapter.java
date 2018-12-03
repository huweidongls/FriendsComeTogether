package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.MyFriendModel;
import com.yiwo.friendscometogether.model.OtherCityModel;

import java.util.List;

import me.zhouzhuo.zzletterssidebar.adapter.BaseSortListViewAdapter;
import me.zhouzhuo.zzletterssidebar.viewholder.BaseViewHolder;

/**
 * Created by Administrator on 2018/9/18.
 */

public class OtherCityAdapter extends BaseSortListViewAdapter<OtherCityModel.ObjBean, OtherCityAdapter.ViewHolder> {

    private Context context;
    private List<OtherCityModel.ObjBean> data;

    public OtherCityAdapter(Context ctx, List<OtherCityModel.ObjBean> datas) {
        super(ctx, datas);
        this.context = ctx;
        this.data = datas;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_city_other;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.tvCityName = view.findViewById(R.id.tv_item_city);
        return holder;
    }

    @Override
    public void bindValues(ViewHolder viewHolder, int position) {
        viewHolder.tvCityName.setText(data.get(position).getName());
    }

    public static class ViewHolder extends BaseViewHolder {
        protected TextView tvCityName;
    }

}
