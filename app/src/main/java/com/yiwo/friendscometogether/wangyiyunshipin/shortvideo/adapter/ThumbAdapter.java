package com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by winnie on 2017/6/14.
 */

public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;

    private View headerView;
    private View footerView;

    private Context context;
    private List<Bitmap> datas;
    private LayoutInflater inflater;

    public ThumbAdapter(Context context, List<Bitmap> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public View getFooterView() {
        return footerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(headerView);
        }
        if (footerView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(footerView);
        }
        View view = inflater.inflate(R.layout.video_thumb_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        holder.imageView.setImageBitmap(datas.get(getRealPosition(holder)));
    }

    @Override
    public int getItemCount() {
        if (headerView == null && footerView == null) {
            return datas.size();
        } else if (headerView != null && footerView == null) {
            return datas.size() + 1;
        } else  if (headerView == null) {
            return datas.size() + 1;
        }
        return datas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null && footerView == null) {
            return TYPE_NORMAL;
        }
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }

        if (footerView != null && position == getItemCount() -1) {
            return TYPE_FOOTER;
        }

        return TYPE_NORMAL;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headerView) {
                return;
            }
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
