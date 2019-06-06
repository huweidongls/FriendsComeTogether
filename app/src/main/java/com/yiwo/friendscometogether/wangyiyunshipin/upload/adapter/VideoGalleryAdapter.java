package com.yiwo.friendscometogether.wangyiyunshipin.upload.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.LineContainer;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoGalleryDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhukkun on 2/24/17.
 */
public class VideoGalleryAdapter extends VcloudRecyclerViewAdapter<LineContainer> {

    public static final int TYPE_DATE_TIP = 0; //日期提示
    public static final int TYPE_NORMAL = 1; //正常项

    GalleryUiDelegate mUiDelegate;

    public interface GalleryUiDelegate {

        void onSelectCountChanged(int selected, int total);

        /**
         * 显示超过1G提示, 当确认后,执行runnable
         * @param confirmRunnable
         */
        void showMoreThan1GConfirm(Runnable confirmRunnable);

        /**
         * 显示超过5G提示
         */
        void showMoreThan5GConfirm();
    }

    VideoGalleryDataAccessor dataAccessor;
    Activity activity;

    public VideoGalleryAdapter(Activity activity, VideoGalleryDataAccessor dataAccessor, GalleryUiDelegate listener) {
        this.activity = activity;
        this.dataAccessor = dataAccessor;
        this.mUiDelegate = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getDataAtPos(position).isDateTip() ? TYPE_DATE_TIP : TYPE_NORMAL;
    }

    @Override
    public VcloudViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videogallery_normal, null);
            return new VideoGalleryLineHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videogallery_datetip, null);
            return new VideoDateTipHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final VcloudViewHolder holder, final int position) {

        if(holder instanceof VideoDateTipHolder){
            ((VideoDateTipHolder) holder).tv_date.setText(getDataAtPos(position).getDate());
        }else{
            VideoGalleryLineHolder lineHolder = (VideoGalleryLineHolder) holder;
            LineContainer lineContainer = getDataAtPos(position);
            for (int i = 0; i < 3; i++) {
                final VideoGalleryHolder subHolder = lineHolder.subHolders.get(i);

                //当此行数据小于3时,隐藏多余项
                if(i >= lineContainer.getVideoItems().size()){
                    subHolder.itemView.setVisibility(View.INVISIBLE);
                    continue;
                }
                VideoItem videoItem = lineContainer.getVideoItems().get(i);
                subHolder.itemView.setVisibility(View.VISIBLE);

                if(videoItem.getDuration()!=null) {
                    subHolder.tv_time.setVisibility(View.VISIBLE);
                    subHolder.tv_time.setText(videoItem.getDuration());
                }else{
                    subHolder.tv_time.setVisibility(View.GONE);
                }

                subHolder.iv_select_mark.setImageResource(dataAccessor.isSelected(holder.getAdapterPosition(), i) ? R.mipmap.video_list_selected : R.mipmap.video_list_unselected);

                Glide.with(activity).load(Uri.parse(videoItem.getUriString())).apply(new RequestOptions().placeholder(R.mipmap.video_thumb)).into(subHolder.iv_display);

                subHolder.itemView.setTag(i);
                subHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int line = holder.getAdapterPosition();
                        final int column = (int)subHolder.itemView.getTag();

                        if(dataAccessor.isSelected(line, column)){
                            dataAccessor.markSelected(line, column, false);
                            mUiDelegate.onSelectCountChanged(dataAccessor.getSeletedCount(), dataAccessor.getLargestCount());
                            subHolder.iv_select_mark.setImageResource(dataAccessor.isSelected(line, column) ? R.mipmap.video_list_selected : R.mipmap.video_list_unselected);
                        } else if (dataAccessor.canSelecteMore() && !dataAccessor.isSelected(line, column)) {

                            if (dataAccessor.isMoreThan_5G(line, column)) {
                                mUiDelegate.showMoreThan5GConfirm();
                            } else if (dataAccessor.isMoreThan_1G(line, column)) {
                                mUiDelegate.showMoreThan1GConfirm(new Runnable() {
                                    @Override
                                    public void run() {
                                        dataAccessor.markSelected(line, column, true);
                                        mUiDelegate.onSelectCountChanged(dataAccessor.getSeletedCount(), dataAccessor.getLargestCount());
                                        subHolder.iv_select_mark.setImageResource(dataAccessor.isSelected(line, column) ? R.mipmap.video_list_selected : R.mipmap.video_list_unselected);
                                    }
                                });
                            } else {
                                dataAccessor.markSelected(line, column, true);
                                mUiDelegate.onSelectCountChanged(dataAccessor.getSeletedCount(), dataAccessor.getLargestCount());
                                subHolder.iv_select_mark.setImageResource(dataAccessor.isSelected(line, column) ? R.mipmap.video_list_selected : R.mipmap.video_list_unselected);
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("您最多可选择" + dataAccessor.getLargestCount() + "个视频");
                            builder.setPositiveButton("我知道了",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.show();
                            return;
                        }

                    }
                });
            }

        }
    }


    class VideoGalleryLineHolder extends VcloudViewHolder{

        List<VideoGalleryHolder> subHolders;

        View subItem1, subItem2, subItem3;

        public VideoGalleryLineHolder(View itemView) {
            super(itemView);
            subHolders = new ArrayList<>();
            subItem1 = findView(itemView, R.id.gallery_subitem1);
            subItem2 = findView(itemView, R.id.gallery_subitem2);
            subItem3 = findView(itemView, R.id.gallery_subitem3);
            subHolders.add(new VideoGalleryHolder(subItem1));
            subHolders.add(new VideoGalleryHolder(subItem2));
            subHolders.add(new VideoGalleryHolder(subItem3));
        }

    }

    class VideoGalleryHolder extends VcloudViewHolder{

        ImageView iv_display;
        ImageView iv_select_mark;
        TextView tv_time;

        public VideoGalleryHolder(View itemView) {
            super(itemView);
            iv_display = findView(itemView, R.id.iv_display);
            iv_select_mark = findView(itemView, R.id.iv_select_mark);
            tv_time = findView(itemView, R.id.tv_total_play_time);
        }
    }

    class VideoDateTipHolder extends VcloudViewHolder{

        TextView tv_date;

        public VideoDateTipHolder(View itemView) {
            super(itemView);
            tv_date = findView(itemView, R.id.tv_date);
        }
    }
}
