package com.yiwo.friendscometogether.wangyiyunshipin.upload.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.VideoPlayerActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.DemoServerHttpClient;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.VideoUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadService;

import java.util.List;

/**
 * Created by zhukkun on 3/13/17.
 */
public class TranscodeVideoListAdapter extends ArrayAdapter<TranscodeVideoListAdapter.TranscodeEntity> {

    private int mResource;


    public TranscodeVideoListAdapter(Context context, int resource, List<TranscodeEntity> objects) {
        super(context, resource, objects);
        mResource = resource;
    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view;
//        final ViewHolder viewHolder;
//        final TranscodeEntity entity = getItem(position);
//
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
//            viewHolder = new ViewHolder(view);
//            view.setTag(viewHolder);
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        viewHolder.tv_title.setText(entity.getTitle());
//        viewHolder.tv_size.setText(getFormateSize(entity.getSize()));
//        viewHolder.tv_info.setText(entity.getUrl());
//
//        if(entity.getState() == UploadService.STATE_TRANSCODE_COMPLETE ||
//                (entity.getState() == UploadService.STATE_TRANSCODE_FAIL && entity.getSize()!=0)){
//            viewHolder.tv_play.setEnabled(true);
//            viewHolder.tv_share.setEnabled(true);
//            viewHolder.tv_delete.setEnabled(true);
//            viewHolder.tv_info.setTextColor(0xff999999);
//            viewHolder.tv_play.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    VideoPlayerActivity.startActivity(getContext(), entity.getUrl());
//                }
//            });
//
//            viewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    VideoUtils.shareUrl(entity.getUrl());
//                }
//            });
//
//            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.rootView.getContext());
//                    builder.setMessage("确认删除该视频, 删除后不可恢复");
//                    builder.setPositiveButton(R.string.ok,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    DialogMaker.showProgressDialog(viewHolder.rootView.getContext(), "加载中", true);
//                                    DemoServerHttpClient.getInstance().videoDelete(entity.getVid(), entity.getFormat(), new DemoServerHttpClient.DemoServerHttpCallback<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            DialogMaker.dismissProgressDialog();
//                                            remove(entity);
//                                            notifyDataSetChanged();
//                                        }
//
//                                        @Override
//                                        public void onFailed(int code, String errorMsg) {;
//                                            DialogMaker.dismissProgressDialog();
//                                            Toast.makeText(viewHolder.rootView.getContext(), "删除失败:" + errorMsg, Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            });
//                    builder.setNegativeButton(R.string.cancel,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    builder.show();
//                }
//            });
//
//        }else {
//            viewHolder.tv_play.setEnabled(false);
//            viewHolder.tv_share.setEnabled(false);
//            viewHolder.tv_delete.setEnabled(false);
//            viewHolder.tv_size.setVisibility(View.GONE);
//            viewHolder.tv_info.setText("转码失败! 请检查您上传的视频是否正确, 或联系您的技术支持");
//            viewHolder.tv_info.setTextColor(0xfff05b60);
//        }
//
//        if(entity.getState() == UploadService.STATE_TRANSCODEING){
//            viewHolder.tv_info.setText("转码中...");
//            viewHolder.tv_info.setTextColor(0xff999999);
//        }
//
//        return view;
//    }
//
//    private String getFormateSize(long size) {
//        return VideoUtils.getFormateSize(size);
//    }
//
//    public class ViewHolder{
//        View rootView;
//
//        TextView tv_title;
//        TextView tv_size;
//        TextView tv_info;
//        TextView tv_play;
//        TextView tv_share;
//        TextView tv_delete;
//
//        public ViewHolder(View view){
//            rootView = view;
//            tv_title = findView(R.id.tv_format);
//            tv_size = findView(R.id.tv_size);
//            tv_info = findView(R.id.tv_info);
//            tv_play = findView(R.id.tv_play);
//            tv_share = findView(R.id.tv_share);
//            tv_delete = findView(R.id.tv_delete);
//        }
//
//        private <T extends View>  T findView(int id){
//            return (T) rootView.findViewById(id);
//        }
//
//    }
//
    public static class TranscodeEntity{
        private int format; //视频转码格式（1表示标清mp4，2表示高清mp4，3表示超清mp4，4表示标清flv，5表示高清flv，6表示超清flv，7表示标清hls，8表示高清hls，9表示超清hls）
        private String title;
        private long size;
        private String url;
        private int state;
        private long vid;

        public int getFormat() {
            return format;
        }

        public void setFormat(int format) {
            this.format = format;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getVid() {
            return vid;
        }

        public void setVid(long vid) {
            this.vid = vid;
        }
    }


}
