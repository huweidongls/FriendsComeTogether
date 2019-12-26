package com.yiwo.friendscometogether.newfragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.create_friendremember.PicBean;
import com.yiwo.friendscometogether.create_friendremember.PicMuluModel;
import com.yiwo.friendscometogether.create_friendremember.PicsMuLuAdapter;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberActivity1;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberNew_ChoosePicsActivity;
import com.yiwo.friendscometogether.widget.choose_pics_view.BitmapUtils;
import com.yiwo.friendscometogether.widget.choose_pics_view.CoordinatorLinearLayout;
import com.yiwo.friendscometogether.widget.choose_pics_view.CoordinatorRecyclerView;
import com.yiwo.friendscometogether.widget.choose_pics_view.MCropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ljc on 2019/5/23.
 */

public class CreateFriendRememberNew_ChoosePicsFragment extends BaseFragment {

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
    private int choose_num = 0;//选择照片数量
    private int last_postion = -1;//上一张选中的照片的postion
    private Map<Integer,String> map_choose_postion = new LinkedHashMap<>();//选择的照片 的 索引
    private Map<Integer,Bitmap> map_choose_bitmap = new LinkedHashMap<>();//选择照片 的bitmap
//    private List<Integer> list_choose_postion = new ArrayList<>();//选择的照片 的 索引
    //    List<Integer> list_choosed_postion = new ArrayList<>(); //选择过的 item Postion;

    private List<Bitmap> listCropBitmap = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createfriendremeber_choose_pics, null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        context = getContext();
        initView();
        return view;
    }
    private void initView() {
        initPhotoInfo();
        data_pic = datas.get(0).getList();
        GridLayoutManager manager = new GridLayoutManager(context,4);
        recyclerView.setLayoutManager(manager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        myAdapter.setNewData(data_pic);
//        Log.d("sssssssss",datas.get(0).getList().get(0).getPath());
        if (data_pic!= null && data_pic.size()>0){
            mMCropImageView.setImagePath(data_pic.get(0).getPath());
        }
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
    @OnClick({R.id.tv,R.id.rl_next,R.id.rl_back_text})
    public void OnCLick(View v){
        switch (v.getId()){
            case R.id.tv:
                getAllPhotoInfo();
                break;
            case R.id.rl_next:
                ArrayList<String> list = new ArrayList<>();
                boolean isFirst = true;
                String new_path = "/storage/emulated/0/Pictures/"+"瞳伴diyi友记_"+System.currentTimeMillis() + ".jpg";
                BitmapUtils.saveBitmap(mMCropImageView.convertToBitmap(),new_path);
                for (Map.Entry<Integer, String> entry : map_choose_postion.entrySet()) {
//                    Log.d("ddaad",";;Key:"+entry.getKey()+"//Value:"+entry.getValue());
//                    if (isFirst&&map_choose_postion.size()>0){
//
////                        String new_path = "/storage/emulated/0/Pictures/tongban_"+entry.getValue().substring(entry.getValue().lastIndexOf("/")+1);
//                        String new_path = "/storage/emulated/0/Pictures/"+"瞳伴友记_"+System.currentTimeMillis() + ".jpg";
//                        BitmapUtils.saveBitmap(BitmapUtils.getFixedBitmap(context,entry.getValue(),false),new_path);
////                        BitmapUtils.saveBitmap(mMCropImageView.getBitmap(mMCropImageView.getWidth(),mMCropImageView.getHeight()),new_path);
//                        BitmapUtils.saveBitmap(BitmapUtils.getFixedBitmap(context,entry.getValue(),false),new_path);
//                        list.add(new_path);
//                        isFirst = false;
//                        Log.d("ddaad",";;Key:"+entry.getKey()+"//Value:"+entry.getValue()+"//new_path"+new_path+ isFirst);
//                        continue;
//                    }
                    list.add(entry.getValue());
                }
                if (list.size()<1){
                    toToast(context,"请选择图片");
                    break;
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra("paths",list);
                intent.setClass(context, CreateFriendRememberActivity1.class);
                context.startActivity(intent);
                getActivity().finish();
                break;
            case R.id.rl_back_text:
                    getActivity().finish();
                break;
        }
    }
    private void initPhotoInfo(){
        List<PicBean> mediaBeen = new ArrayList<>();
        HashMap<String,List<PicBean>> allPhotosTemp = new HashMap<>();//所有照片
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = context.getContentResolver().query(mImageUri,
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
//                Log.d("asdas","1:"+path+";;\n2:"+displayName);
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
                Cursor mCursor = context.getContentResolver().query(mImageUri,
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
//                        Log.d("asdas","1:"+path+";;\n2:"+displayName);
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
                getActivity().runOnUiThread(new Runnable() {
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
        View view = LayoutInflater.from(context).inflate(R.layout.pop_pics_mulu, null);
//        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView rv = view.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        picsMuLuAdapter = new PicsMuLuAdapter(datas);
        picsMuLuAdapter.setListenner(new PicsMuLuAdapter.OnItemClickListenner() {
            @Override
            public void onClick(int postion) {
                textView.setText(datas.get(postion).getMuluName().substring(datas.get(postion).getMuluName().lastIndexOf("/")+1));
                data_pic.clear();
                data_pic.addAll(datas.get(postion).getList());
                choose_num = 0;
                map_choose_postion.clear();
                mMCropImageView.setImagePath(data_pic.get(0).getPath());
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
        protected void convert(final BaseViewHolder helper, final PicBean item) {
            ImageView imageView = helper.itemView.findViewById(R.id.iv);
            final RelativeLayout rl = helper.itemView.findViewById(R.id.rl);
            final TextView tv_num = helper.itemView.findViewById(R.id.tv_num);

            Glide.with(context).load(item.getPath()).into(imageView);
            if (item.getSelected()){
                rl.setVisibility(View.VISIBLE);
            }else {
                rl.setVisibility(View.GONE);
            }
            tv_num.setBackgroundResource(item.getChoose_num()>=0 ? R.drawable.bg_oval_red:R.drawable.bg_oval_round_white);
            tv_num.setText(item.getChoose_num()>=0 ? item.getChoose_num()+"":"");

            tv_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.itemView.callOnClick();
                    if (item.getChoose_num()<0){
                        if (choose_num<9){
                            choose_num ++;
                            item.setChoose_num(choose_num);
                            tv_num.setBackgroundResource(R.drawable.bg_oval_red);
                            tv_num.setText(item.getChoose_num()+"");
                            map_choose_postion.put(helper.getLayoutPosition(),item.getPath());
                        }else {
                            toToast(context,"最多选择9张图片");
                        }
                    }else {
                        if (choose_num>=0){
                            int last_num = item.getChoose_num();
                            item.setChoose_num(-1);
                            tv_num.setBackgroundResource(R.drawable.bg_oval_round_white);
                            tv_num.setText("");
                            map_choose_postion.remove(helper.getLayoutPosition());
                            for (Map.Entry<Integer, String> entry : map_choose_postion.entrySet()) {
                                int num = data_pic.get(entry.getKey()).getChoose_num();
                                if (num>=last_num){
                                    data_pic.get(entry.getKey()).setChoose_num(num-1);
                                    notifyItemChanged(entry.getKey());
                                }
                            }
                            choose_num --;
                        }
                    }
                }
            });
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("ssss",item.getPath());
                    mMCropImageView.setImagePath(item.getPath());
                    if (item.getSelected()){
//                        item.setSelected(false);
//                        rl.setBackground(null);
                    }else {
                        item.setSelected(true);
                        notifyItemChanged(helper.getLayoutPosition());
                        if (last_postion>=0){
                            data_pic.get(last_postion).setSelected(false);
                            notifyItemChanged(last_postion);
                        }
                        last_postion = helper.getLayoutPosition();
                    }
                }
            });
        }
    }
}
