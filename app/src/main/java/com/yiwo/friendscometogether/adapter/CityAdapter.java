package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/7/15.
 */

public class CityAdapter extends BaseAdapter {
    List<CityModel> list;
    Context context;
    public CityAdapter(Context context, List<CityModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CityModel cityBean = list.get(position);
        if (cityBean != null) {
            if (position == 0) {
                if (StringUtils.isEmpty(cityBean.getName())) {
                    holder.tv.setText("当前定位城市：未开启定位功能");
                } else {
                    holder.tv.setText("当前定位城市：" + cityBean.getName());
                }
            } else {
                holder.tv.setText(cityBean.getName());
            }
            String id = cityBean.getId();
            if (id == null || id.equals("null") || StringUtils.isEmpty(id)) {
                holder.rl.setBackgroundColor(context.getResources().getColor(R.color.white_ffffff));
                holder.tv.setTextColor(context.getResources().getColor(R.color.black_333333));
            } else {
                if (cityBean.getId().equals("-1")) {
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.black_f8f8f8));
                    holder.tv.setTextColor(context.getResources().getColor(R.color.black_333333));
                } else {
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.white_ffffff));
                    holder.tv.setTextColor(context.getResources().getColor(R.color.black_333333));
                }
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private RelativeLayout rl;
        private TextView tv;

        public ViewHolder(View itemView) {
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_item_city);
            tv = (TextView) itemView.findViewById(R.id.tv_item_city);
        }
    }
}
