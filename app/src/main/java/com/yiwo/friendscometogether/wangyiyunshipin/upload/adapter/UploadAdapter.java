package com.yiwo.friendscometogether.wangyiyunshipin.upload.adapter;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseFetchLoadAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.DemoServerHttpClient;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.VideoInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadController;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadService;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadDataAccessor;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;
import com.yiwo.friendscometogether.wangyiyunshipin.widget.ItemSlideHelper;

import java.util.List;


/**
 * Created by zhukkun on 2/22/17.
 */
public class UploadAdapter extends BaseFetchLoadAdapter<VideoItem, BaseViewHolder> implements ItemSlideHelper.Callback {

    UploadDataAccessor dataAccessor;
    UploadController controller;

    private RecyclerView attachedRecyclerView;

    RelativeLayout rl_content;
    ImageView imageView;
    TextView tv_upload_state;
    TextView tv_upload_progress;
    TextView tv_other_state;
    TextView tv_name;
    TextView tv_retry;
    View iv_next;
    TextView tv_delete;
    ProgressBar uploadProgressBar;
    ProgressBar transcodeProgressBar;

    public UploadAdapter(RecyclerView recyclerView, List<VideoItem> data, UploadController uploadController) {
        super(recyclerView, R.layout.layout_upload_item, data);
        this.controller = uploadController;
        this.dataAccessor = uploadController.getDataAccessor();
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        this.attachedRecyclerView = recyclerView;
        this.attachedRecyclerView.addOnItemTouchListener(new ItemSlideHelper(recyclerView.getContext(), this));
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoItem videoItem, int position, boolean isScrolling) {
        rl_content = holder.getView(R.id.rl_content);
        imageView = holder.getView(R.id.iv_thumb);
        tv_name = holder.getView(R.id.tv_name);
        tv_upload_state = holder.getView(R.id.tv_upload_state);
        tv_other_state = holder.getView(R.id.tv_other_state);
        tv_upload_progress = holder.getView(R.id.tv_progress);
        uploadProgressBar = holder.getView(R.id.progress);
        transcodeProgressBar = holder.getView(R.id.transcode_progress);
        tv_retry = holder.getView(R.id.upload_retry);
        iv_next = holder.getView(R.id.iv_next);
        tv_delete = holder.getView(R.id.tv_delete);

        if (videoItem.getEntity() == null) {
            tv_name.setText(videoItem.getDisplayName());
            Glide.with(mContext).load(Uri.parse(videoItem.getUriString())).into(imageView);
        } else {
            //包含Entity的项,为云端拉取的数据
            tv_name.setText(videoItem.getEntity().getVideoName());

            if (videoItem.getEntity().getSnapshotUrl() != null) {
                Glide.with(mContext).load(videoItem.getEntity().getSnapshotUrl()).apply(new RequestOptions().placeholder(R.mipmap.video_thumb)).into(imageView);
            } else if (videoItem.getUriString() != null) {
                Glide.with(mContext).load(Uri.parse(videoItem.getUriString())).apply(new RequestOptions().placeholder(R.mipmap.video_thumb)).into(imageView);
            } else {
                Glide.with(mContext).load(R.mipmap.video_thumb).into(imageView);
            }
        }

        setSubViewVisible(videoItem.getUploadProgress(), videoItem.getState());

        setListener(holder, videoItem);
    }

    private void setListener(BaseViewHolder holder, final VideoItem videoItem) {
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                RecyclerView.ViewHolder clickHolder = attachedRecyclerView.getChildViewHolder(attachedRecyclerView.findContainingItemView(v));
                final int clickPosition = clickHolder.getAdapterPosition();
                if (videoItem.getState() == UploadService.STATE_TRANSCODEING) {
                    Toast.makeText(attachedRecyclerView.getContext(), "转码中的视频不能删除", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(attachedRecyclerView.getContext());
                builder.setMessage("确认删除该视频, 删除后不可恢复");
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RecyclerView.ViewHolder clickHolder = attachedRecyclerView.getChildViewHolder(attachedRecyclerView.findContainingItemView(v));
                                final int clickPosition = clickHolder.getAdapterPosition();
                                if (videoItem.getState() == UploadService.STATE_TRANSCODEING) {
                                    Toast.makeText(attachedRecyclerView.getContext(), "转码中的视频不能删除", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                deleteItem(videoItem, clickPosition);
                            }
                        });

                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder clickHolder = attachedRecyclerView.getChildViewHolder(v);
                final int clickPosition = clickHolder.getAdapterPosition();

                switch (videoItem.getState()) {
                    case UploadService.STATE_UPLOADING: {
                        Toast.makeText(mContext,"视频正在上传中",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case UploadService.STATE_TRANSCODEING:
                    case UploadService.STATE_TRANSCODE_COMPLETE:
                    case UploadService.STATE_TRANSCODE_FAIL: {
//                        queryVideoAndEnterDetail(videoItem);
                        break;
                    }
                }
            }
        });


        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder clickHolder = attachedRecyclerView.getChildViewHolder(attachedRecyclerView.findContainingItemView(v));
                final int clickPosition = clickHolder.getAdapterPosition();
                //videoItem.setUploadToken(null); //由于已经上传失败,故设置token为null,重新请求token
                videoItem.setState(UploadService.STATE_WAIT);
                notifyItemChanged(clickPosition);
                controller.startUploadIfAllow();
            }
        });
    }

    private void setSubViewVisible(int uploadProgress, int state) {
        if (state == UploadService.STATE_WAIT || state == UploadService.STATE_UPLOADING) {
            tv_upload_state.setVisibility(View.VISIBLE);
            tv_upload_progress.setVisibility(View.VISIBLE);
            uploadProgressBar.setVisibility(View.VISIBLE);

            tv_other_state.setVisibility(View.GONE);
            iv_next.setVisibility(View.GONE);
            tv_retry.setVisibility(View.GONE);

            tv_name.setLines(1);
            tv_upload_progress.setText("(" + uploadProgress + "%)");
            uploadProgressBar.setProgress(uploadProgress);
            setStateToTextView(tv_upload_state, state);
            rl_content.setAlpha(0.4f);
        } else {
            tv_upload_state.setVisibility(View.GONE);
            tv_upload_progress.setVisibility(View.GONE);
            uploadProgressBar.setVisibility(View.GONE);
            tv_other_state.setVisibility(View.VISIBLE);
            iv_next.setVisibility(View.VISIBLE);
            tv_retry.setVisibility(View.GONE);
            tv_name.setLines(2);
            setStateToTextView(tv_other_state, state);
            rl_content.setAlpha(1f);
        }

        if (state == UploadService.STATE_TRANSCODEING) {
            transcodeProgressBar.setVisibility(View.VISIBLE);
        } else {
            transcodeProgressBar.setVisibility(View.GONE);
        }
        if (state == UploadService.STATE_UPLOAD_FAIL) {
            iv_next.setVisibility(View.GONE);
            tv_retry.setVisibility(View.VISIBLE);
        }

        if (state == UploadService.STATE_UPLOAD_FAIL || state == UploadService.STATE_TRANSCODE_FAIL) {
            tv_other_state.setTextColor(tv_other_state.getContext().getResources().getColor(R.color.color_upload_fail));
        } else {
            tv_other_state.setTextColor(tv_other_state.getContext().getResources().getColor(R.color.color_upload_normal));
        }

    }

    public void setStateToTextView(TextView tv_state, int state) {
        switch (state) {
            case UploadService.STATE_UPLOAD_COMPLETE:
                tv_state.setText("上传完成");
                break;
            case UploadService.STATE_WAIT:
                tv_state.setText("等待上传");
                break;
            case UploadService.STATE_UPLOAD_FAIL:
                tv_state.setText("上传失败");
                break;
            case UploadService.STATE_UPLOADING:
                tv_state.setText("上传中");
                break;
            case UploadService.STATE_TRANSCODEING:
                tv_state.setText("转码中");
                break;
            case UploadService.STATE_TRANSCODE_FAIL:
                tv_state.setText("转码失败");
                break;
            case UploadService.STATE_TRANSCODE_COMPLETE:
                tv_state.setText("正常");
                break;
        }
    }

    private void deleteItem(final VideoItem videoItem, final int position) {
        //包含entity的项为云端拉取的, vid不为0的项为上传成功且添加至服务端的项
        if (videoItem.getVid() != 0) {
            DialogMaker.showProgressDialog(attachedRecyclerView.getContext(), "请稍候");
            DemoServerHttpClient.getInstance().videoDelete(videoItem.getVid(), null, new DemoServerHttpClient.DemoServerHttpCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    remove(position);
                    dataAccessor.removeItem(videoItem);
                    DialogMaker.dismissProgressDialog();
                }

                @Override
                public void onFailed(int code, String errorMsg) {
                    Toast.makeText(mContext, "删除失败:" + errorMsg, Toast.LENGTH_SHORT).show();
                    DialogMaker.dismissProgressDialog();
                }
            });
        } else {
            //上传完成前的项
            remove(position);
            controller.deleteUploadItem(videoItem);
        }
    }

//    private void queryVideoAndEnterDetail(final VideoItem videoItem) {
//        DialogMaker.showProgressDialog(attachedRecyclerView.getContext(), "请稍候");
//        DemoServerHttpClient.getInstance().videoInfoGet(videoItem.getVid(), 0, new DemoServerHttpClient.DemoServerHttpCallback<List<VideoInfoEntity>>() {
//            @Override
//            public void onSuccess(List<VideoInfoEntity> videoInfoEntities) {
//                videoItem.setEntity(videoInfoEntities.get(0));
//                VideoDetailInfoActivity.startActivity(mContext, videoItem.getEntity(), videoItem.getState(), true);
//                DialogMaker.dismissProgressDialog();
//                refreshTranscodeState(videoItem);
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailed(int code, String errorMsg) {
//                Toast.makeText(mContext, code + ":" + errorMsg, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
//            }
//        });
//    }

    /**
     * 更新转码状态
     *
     * @param videoItem
     */
    private void refreshTranscodeState(VideoItem videoItem) {
        switch (videoItem.getEntity().getStatus()) {
            case 20: {
                videoItem.setState(UploadService.STATE_TRANSCODE_FAIL);
                dataAccessor.removeTranscodeItemByVid(videoItem.getVid());
                break;
            }
            case 30:
                videoItem.setState(UploadService.STATE_TRANSCODEING);
                break;
            case 40: {
                videoItem.setState(UploadService.STATE_TRANSCODE_COMPLETE);
                dataAccessor.removeTranscodeItemByVid(videoItem.getVid());
                break;
            }
        }
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return attachedRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return attachedRecyclerView.findChildViewUnder(x, y);
    }
}
