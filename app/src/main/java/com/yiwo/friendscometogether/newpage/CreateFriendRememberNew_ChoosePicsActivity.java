package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.create_friendremember.PicBean;
import com.yiwo.friendscometogether.create_friendremember.PicMuluModel;
import com.yiwo.friendscometogether.create_friendremember.PicsMuLuAdapter;
import com.yiwo.friendscometogether.widget.choose_pics_view.CoordinatorLinearLayout;
import com.yiwo.friendscometogether.widget.choose_pics_view.CoordinatorRecyclerView;
import com.yiwo.friendscometogether.widget.choose_pics_view.MCropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateFriendRememberNew_ChoosePicsActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.recycler)
    CoordinatorRecyclerView recyclerView;
    @BindView(R.id.crop_view)
    MCropImageView mMCropImageView;
    @BindView(R.id.coordinator_layout)
    CoordinatorLinearLayout mCoordinatorLayout;

    private Context context;

    private PicsMuLuAdapter picsMuLuAdapter;
    private List<PicMuluModel> datas = new ArrayList<>();
    private List<PicBean> data_pic = new ArrayList<>();
    private PopupWindow popupWindow;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend_new__choose_pics);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(CreateFriendRememberNew_ChoosePicsActivity.this);
        context = CreateFriendRememberNew_ChoosePicsActivity.this;
        initView();
    }

    private void initView() {
        initPhotoInfo();
        data_pic = datas.get(0).getList();
        GridLayoutManager manager = new GridLayoutManager(context,4);
        recyclerView.setLayoutManager(manager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        myAdapter.setNewData(data_pic);
        Log.d("sssssssss",datas.get(0).getList().get(0).getPath());
        mMCropImageView.setImagePath(datas.get(0).getList().get(0).getPath());
        // 实现回调接口
        recyclerView.setOnCoordinatorListener(new CoordinatorRecyclerView.OnCoordinatorListener() {
            @Override
            public void onScroll(float y, float deltaY, int maxParentScrollRange) {
                mCoordinatorLayout.onScroll(y, deltaY, maxParentScrollRange);
            }

            @Override
            public void onFiling(int velocityY) {
                mCoordinatorLayout.onFiling(velocityY);
            }

            @Override
            public void handlerInvalidClick(int rawX, int rawY) {
                handlerRecyclerInvalidClick(recyclerView, rawX, rawY);
            }
        });

        mCoordinatorLayout.setOnScrollListener(new CoordinatorLinearLayout.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                recyclerView.setCurrentParenScrollY(scrollY);
            }

            @Override
            public void isExpand(boolean isExpand) {
                recyclerView.setExpand(isExpand);
            }

            @Override
            public void completeExpand() {
                recyclerView.resetRecyclerHeight();
            }
        });
    }
    private void initPhotoInfo(){
        List<PicBean> mediaBeen = new ArrayList<>();
        HashMap<String,List<PicBean>> allPhotosTemp = new HashMap<>();//所有照片
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = getContentResolver().query(mImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if(mCursor!=null){
            datas.clear();
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                Log.d("asdas","1:"+path+";;\n2:"+displayName);
                //用于展示相册初始化界面
                mediaBeen.add(new PicBean(path,size,displayName));
                // 获取该图片的父路径名
                String dirPath = new File(path).getParentFile().getAbsolutePath();
                //存储对应关系
                if (allPhotosTemp.containsKey(dirPath)) {
                    List<PicBean> data = allPhotosTemp.get(dirPath);
                    data.add(new PicBean(path,size,displayName));
                    continue;
                } else {
                    List<PicBean> data = new ArrayList<>();
                    data.add(new PicBean(path,size,displayName));
                    allPhotosTemp.put(dirPath,data);
                    PicMuluModel model = new PicMuluModel();
                    model.setMuluName(dirPath);
                    model.setList(data);
                    datas.add(model);
                }
            }
            PicMuluModel model = new PicMuluModel();
            model.setMuluName("所有照片");
            model.setList(mediaBeen);
            datas.add(0,model);
            mCursor.close();
        }
    }
    /**
     * 读取手机中所有图片信息
     */
    private void getAllPhotoInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PicBean> mediaBeen = new ArrayList<>();
                HashMap<String,List<PicBean>> allPhotosTemp = new HashMap<>();//所有照片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projImage = { MediaStore.Images.Media._ID
                        , MediaStore.Images.Media.DATA
                        ,MediaStore.Images.Media.SIZE
                        ,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor mCursor = getContentResolver().query(mImageUri,
                        projImage,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED+" desc");

                if(mCursor!=null){
                    datas.clear();
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        Log.d("asdas","1:"+path+";;\n2:"+displayName);
                        //用于展示相册初始化界面
                        mediaBeen.add(new PicBean(path,size,displayName));
                        // 获取该图片的父路径名
                        String dirPath = new File(path).getParentFile().getAbsolutePath();
                        //存储对应关系
                        if (allPhotosTemp.containsKey(dirPath)) {
                            List<PicBean> data = allPhotosTemp.get(dirPath);
                            data.add(new PicBean(path,size,displayName));
                            continue;
                        } else {
                            List<PicBean> data = new ArrayList<>();
                            data.add(new PicBean(path,size,displayName));
                            allPhotosTemp.put(dirPath,data);
                            PicMuluModel model = new PicMuluModel();
                            model.setMuluName(dirPath);
                            model.setList(data);
                            datas.add(model);
                        }
                    }
                    PicMuluModel model = new PicMuluModel();
                    model.setMuluName("所有照片");
                    model.setList(mediaBeen);
                    datas.add(0,model);
                    mCursor.close();
                }
                //更新界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //...
                        show_pop();
                    }
                });
            }
        }).start();
    }
    private void show_pop(){
        View view = LayoutInflater.from(CreateFriendRememberNew_ChoosePicsActivity.this).inflate(R.layout.pop_pics_mulu, null);
//        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView rv = view.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(CreateFriendRememberNew_ChoosePicsActivity.this);
        rv.setLayoutManager(manager);
        picsMuLuAdapter = new PicsMuLuAdapter(datas);
        picsMuLuAdapter.setListenner(new PicsMuLuAdapter.OnItemClickListenner() {
            @Override
            public void onClick(int postion) {
                textView.setText(datas.get(postion).getMuluName().substring(datas.get(postion).getMuluName().lastIndexOf("/")+1));
                data_pic.clear();
                data_pic.addAll(datas.get(postion).getList());
                myAdapter.setNewData(data_pic);
                popupWindow.dismiss();
            }
        });
        rv.setAdapter(picsMuLuAdapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.TOP,0,150);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {

            }
        });
    }
    /**
     * @param recyclerView
     * @param touchX
     * @param touchY
     */
    public void handlerRecyclerInvalidClick(RecyclerView recyclerView, int touchX, int touchY) {
        if (recyclerView != null && recyclerView.getChildCount() > 0) {
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View childView = recyclerView.getChildAt(i);
                if (childView != null) {
                    if (childView != null && isTouchView(touchX, touchY, childView)) {
                        childView.performClick();
                        return;
                    }
                }
            }
        }
    }

    // 触摸点是否view区域内 parent.isPointInChildBounds(child, x, y)
    private boolean isTouchView(int touchX, int touchY, View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect.contains(touchX, touchY);
    }
    public class MyAdapter extends BaseQuickAdapter<PicBean, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_coordinator_layout, new ArrayList<PicBean>());
        }

        @Override
        protected void convert(BaseViewHolder helper, final PicBean item) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMCropImageView.setImagePath(item.getPath());
                }
            });
            ImageView imageView = helper.itemView.findViewById(R.id.iv);
//            imageView.setImageResource(R.mipmap.ic_launcher);
            Glide.with(context).load(item.getPath()).into(imageView);
        }
    }
}
