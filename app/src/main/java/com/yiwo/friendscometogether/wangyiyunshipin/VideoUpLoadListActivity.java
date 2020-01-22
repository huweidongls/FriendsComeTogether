package com.yiwo.friendscometogether.wangyiyunshipin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.recyclerview.listener.OnItemClickListener;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.tencent.mm.opensdk.utils.Log;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.VideoInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.UploadState;
import com.yiwo.friendscometogether.wangyiyunshipin.shortvideo.adapter.VideoAdapter;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.constant.UploadType;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.controller.UploadController;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.UploadDbHelper;
import com.yiwo.friendscometogether.wangyiyunshipin.upload.model.VideoItem;
import com.yiwo.friendscometogether.wangyiyunshipin.utils.FileUtil;
import com.yiwo.friendscometogether.wangyiyunshipin.widget.RecyclerViewEmptySupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoUpLoadListActivity extends com.yiwo.friendscometogether.base.BaseActivity implements UploadController.UploadUi, VideoAdapter.EventListener {


    // constant
    private static final int VIDEO_LIMIT = 10;
    // view
    private RecyclerViewEmptySupport videoListView; // 视频列表控件
    private RelativeLayout rl_back;
    // data
    private int videoCount; // 已经上传服务器的视频数量
    private VideoAdapter videoAdapter;
    private List<VideoItem> items; // 视频item列表
    private VideoItem videoItem;
    private HashMap<Long, VideoInfoEntity> videosFromServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_up_load_list);
        findViews();
        initData();
        videoItem = (VideoItem) getIntent().getSerializableExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM);
        if (videoItem != null){
            android.util.Log.d("VIdeoUpLOadList",videoItem.getUriString()+"|||"+videoItem.getFilePath());
            uploadFile(videoItem);
        }else {
            if (items.size()>0){
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("是否上传全部未上传视频？")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                UploadController.getInstance().loadVideoDataFromLocal(UploadType.SHORT_VIDEO);
//                                UploadController.getInstance().uploadLocalItem(items, UploadType.SHORT_VIDEO, true);
//                            }
//                        }).show();
            }
        }
    }
    public  static void startVideoUpLoadListActivity(Context context,VideoItem videoItem){
        Intent intent = new Intent(context,VideoUpLoadListActivity.class);
        intent.putExtra(TakeVideoFragment_new.EXTRA_VIDEO_ITEM,videoItem);
        context.startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        UploadController.getInstance().suspend();
    }
    private void findViews() {

        videoListView = findViewById(R.id.video_list);
        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // adapter
        items = new ArrayList<>();
        items.addAll(UploadDbHelper.queryUploadListFromDbByType(UploadType.SHORT_VIDEO));//查询本地数据库
        videoAdapter = new VideoAdapter(videoListView, R.layout.video_item_layout, items);
        videoAdapter.setEventListener(this);
//        videoAdapter.setFetchMoreView(new MsgListFetchLoadMoreView());
//        videoAdapter.setOnFetchMoreListener(new BaseFetchLoadAdapter.RequestFetchMoreListener() {
//            @Override
//            public void onFetchMoreRequested() {
//
//                videoAdapter.fetchMoreEnd(true);
//            }
//        });
        videoListView.addOnItemTouchListener(new OnItemClickListener<VideoAdapter>() {
            @Override
            public void onItemClick(VideoAdapter adapter, View view, int position) {
                VideoInfoEntity entity = adapter.getItem(position).getEntity();
//                if (entity != null) {
//                    if (TextUtils.isEmpty(entity.getSnapshotUrl())) {
//                        entity.setSnapshotUrl(adapter.getItem(position).getUriString());
//                    }
//                    VideoDetailInfoActivity.startActivity(getActivity(), adapter.getItem(position).getEntity(),
//                            adapter.getItem(position).getState(), false);
//                }
            }

            @Override
            public void onItemLongClick(VideoAdapter adapter, View view, int position) {
//                onNormalLongClick(position);
            }
        });
        videoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListView.setEmptyView(findViewById(R.id.list_empty));
        videoListView.setAdapter(videoAdapter);
    }
    private void initData() {
        UploadController.getInstance().init(VideoUpLoadListActivity.this);
        UploadController.getInstance().attachUi(VideoUpLoadListActivity.this);
        videosFromServer = new HashMap<>();
//        getVideoList(null);
//        UploadController.getInstance().loadVideoDataFromLocal(UploadType.SHORT_VIDEO);
        // 删除thumb文件夹里面的缓存图片
//        FileUtil.delete(new File(StorageUtil.getDirectoryByDirType(StorageType.TYPE_THUMB_IMAGE)));
    }
    @Override
    public boolean isUiInit() {
        return false;
    }

    @Override
    public void onRetryUpload(VideoItem videoItem) {
        uploadFile(videoItem);
    }
    private void uploadFile(final VideoItem videoItem) {
        if (!NetworkUtil.isNetAvailable(this)) {
//            EasyAlertDialogHelper.showOneButtonDiolag(this, null, getString(R.string.network_is_not_available),
//                    "我知道了", false, null);
        }
        doRealUpload(videoItem);
    }

    /**
     * 调用上传接口，上传视频
     */
    private void doRealUpload(VideoItem videoItem) {
        Log.d("VIdeoUpLOadList","doRealUpload::"+"");
        videoItem.setType(UploadType.SHORT_VIDEO);
        videoItem.setState(UploadState.STATE_WAIT);
        List<VideoItem> videoItemList = new ArrayList<>(1);
        videoItemList.add(videoItem);
        UploadController.getInstance().uploadLocalItem(videoItemList, UploadType.SHORT_VIDEO, true);
    }

    @Override
    public void onVideoDeleted(int position, VideoItem videoItem) {

    }

    @Override
    public void updateAllItems() {

    }

    @Override
    public void updateUploadState(int state) {
        Log.d("VIdeoUpLOadList","updateUploadState::"+state+"");
        if (state == UploadState.STATE_UPLOAD_COMPLETE) {
            videoCount++;
        }
    }

    @Override
    public void updateItemProgress(String id, int progress, int state) {
        Log.d("VIdeoUpLOadList","updateItemProgress::"+progress+"/id::"+id+"");
        int index = getItemIndex(id);
        if (index >= 0) {
            videoAdapter.putProgress(id, progress);
            videoAdapter.notifyDataItemChanged(index);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private int getItemIndex(String id) {
        for (int i = 0; i < items.size(); i++) {
            VideoItem videoItem = items.get(i);
            if (TextUtils.equals(videoItem.getId(), id)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void onDataSetChanged(List<VideoItem> data) {
        for (VideoItem item : data) {
            boolean b = false;//items  里是否含有 此项Item;
            for (int i = 0;i<items.size();i++){
                if (items.get(i).getId().equals(item.getId())) {
                    b = true;
                }
            }
            if (!b){//如果没有
                items.add(0, item);
            }
        }
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddVideoResult(int code, String id, AddVideoResponseEntity addVideoResponseEntity) {
        Log.d("VIdeoUpLOadList","addResult::"+code+"/id::"+id+"");
        if (code == 200) {
            int index = getItemIndex(id);
            if (index >= 0 && index < items.size() && items.get(index) != null) {
                VideoItem videoItem = items.get(index);
                videoItem.setVid(addVideoResponseEntity.getVid());
                videoItem.setEntity(addVideoResponseEntity.getVideoInfoEntity());
                // 删除上传成功的文件
                FileUtil.deleteFile(videoItem.getFilePath());
                Log.d("VIdeoUpLOadList","addResult::items.lenth"+items.size()+"/index::"+index+"");
                items.remove(index);
            }
//            videoAdapter.notifyDataItemChanged(index);
            videoAdapter.notifyDataSetChanged();
        }
    }
}
