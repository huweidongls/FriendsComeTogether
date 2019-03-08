package com.yiwo.friendscometogether.activecard;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.yiwo.friendscometogether.model.FriendsTogethermodel;

import java.util.List;

/**
 * 介绍：人人影视效果的Callback
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */

public class RenRenCallback extends ItemTouchHelper.SimpleCallback {

    protected RecyclerView mRv;
    protected List<FriendsTogethermodel.ObjBean> mDatas;
    protected RecyclerView.Adapter mAdapter;
    private OnSwipeListener mListener;
    private int dx_temp;
    private boolean aBoolean;
    public RenRenCallback(RecyclerView rv, RecyclerView.Adapter adapter, List<FriendsTogethermodel.ObjBean> datas) {
        this(0,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                rv, adapter, datas);
    }

    public void setOnSwipedListener(OnSwipeListener mListener) {
        this.mListener = mListener;
    }

    public RenRenCallback(int dragDirs, int swipeDirs
            , RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return mRv.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return super.getMovementFlags(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
        //rollBack(viewHolder);
        //★实现循环的要点
//        FriendsTogethermodel.ObjBean remove = mDatas.remove(viewHolder.getLayoutPosition());
//        mDatas.add(0, remove);
//        mAdapter.notifyDataSetChanged();
        if (mListener != null) {
            if(direction == ItemTouchHelper.LEFT ||direction == ItemTouchHelper.DOWN){
                FriendsTogethermodel.ObjBean remove = mDatas.remove(viewHolder.getLayoutPosition());
                mDatas.add(0, remove);
                mAdapter.notifyDataSetChanged();
                Log.d("direction,LEFT",direction+"");
                mListener.onSwiped(viewHolder, mDatas.get(mDatas.size()-1), direction == ItemTouchHelper.LEFT ? 0 : 1);
            }else if (direction == ItemTouchHelper.RIGHT||direction == ItemTouchHelper.UP){
                //1:将下标为0，保存在一个临时的变量中
                FriendsTogethermodel.ObjBean remove = mDatas.get(0);

                //2:循环向前移位
                for(int i = 0;i<mDatas.size()-1;i++){
                    mDatas.set(i,mDatas.get(i+1));
                }
                //3 把第一个值放到最后一位
                mDatas.set(mDatas.size()-1,remove);
                mAdapter.notifyDataSetChanged();
                Log.d("direction,RIGHT",direction+"");
                mListener.onSwiped(viewHolder, mDatas.get(mDatas.size()-1), direction == ItemTouchHelper.LEFT ? 0 : 1);
            }
        }

    }
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);

        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));

                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                } else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }
        // 得到滑动的阀值
          float ratio = dX / getThreshold(recyclerView, viewHolder);
        // ratio 最大为 1 或 -1
        if (ratio > 1) {
            ratio = 1;
        } else if (ratio < -1) {
            ratio = -1;
        }
        // 回调监听器
        if (mListener != null) {
            if (ratio != 0) {
                mListener.onSwiping(viewHolder, ratio, ratio < 0 ? CardConfig.SWIPING_LEFT : CardConfig.SWIPING_RIGHT);
            } else {
                mListener.onSwiping(viewHolder, ratio, CardConfig.SWIPING_NONE);
            }
        }

//        int i_dx_temp = dx_temp;//
//        if (dX>0){
//            dx_temp = 1;
//        }else{
//            dx_temp = -1;
//        }
//        //当滑动偏移方向 不同时  加载数据；
//        if (dX>0&&i_dx_temp!=dx_temp){
//            //1:将下标为0，保存在一个临时的变量中
//            FriendsTogethermodel.ObjBean remove = mDatas.get(0);
//
//            //2:循环向前移位
//            for(int j = 0;j<mDatas.size()-1;j++){
//                mDatas.set(j,mDatas.get(j+1));
//            }
//            //3 把第一个值放到最后一位
//            mDatas.set(mDatas.size()-1,remove);
//            mAdapter.notifyDataSetChanged();
//            Log.d("direction,RIGHT",dX+"");
//        }else if (dX<0&&i_dx_temp!=dx_temp){
//            FriendsTogethermodel.ObjBean remove = mDatas.remove(mDatas.size()-1);
//            mDatas.add(0, remove);
//            mAdapter.notifyDataSetChanged();
//            Log.d("direction,LEFT",dX+"");
//        }
    }
    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }
}
