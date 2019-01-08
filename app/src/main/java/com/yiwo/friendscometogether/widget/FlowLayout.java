package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by ljc on 2019/1/8.
 */

public class FlowLayout extends ViewGroup {
    private Context mContext;
    private BaseAdapter mAdapter;
    private TagItemClickListener mListener;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int usedWidth = 0;      //已使用的宽度
        int remaining = 0;      //剩余可用宽度
        int totalHeight = 0;    //总高度
        int lineHeight = 0;     //当前行高
        int maxLineHeight = 0;  //最大行高

        //for 循环遍历 子 view
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //获取 layoutParams
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            if (widthMode == MeasureSpec.AT_MOST) {
                throw new RuntimeException("FlowLayout 的 \"layout_width\" 必须为 \"match_parent\" 或者 精确数值");
            } else {
                //测量 子 view
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                // 剩余可用 width
                remaining = widthSize - usedWidth - getPaddingLeft() - getPaddingRight();
                //当剩余空间不足以放下一个新 view 时，换行
                if (childView.getMeasuredWidth() > remaining) {
                    //累加高度，用于作为当前 FlowLayout 的最终高度
                    totalHeight += maxLineHeight;
                    //重置
                    maxLineHeight = 0;
                    usedWidth = 0;
                }
                //已使用 width 进行 累加
                usedWidth += lp.leftMargin + lp.rightMargin + childView.getMeasuredWidth();
                //当前 view 的高度
                lineHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                //取出每行 view 的最大高度
                maxLineHeight = Math.max(lineHeight, maxLineHeight);
            }
        }

        //最终高度，记得加上最后一行的view 的高度
        totalHeight += maxLineHeight + getPaddingTop() + getPaddingBottom();
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = totalHeight;
        }
        //去较大的一个作为 FlowLayout 的最终高度
        heightSize = Math.max(totalHeight, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();

        int childTop = paddingTop;
        int childLeft = paddingLeft;
        int childRight = 0;
        int childBottom = 0;

        // FlowLayout 的 width
        final int width = right - left;

        //当前 FlowLayout 中 子 View 可使用的最大宽度
        int childWidthSpace = width - paddingLeft - paddingRight;

        //行高
        int lineHeight = 0;

        //最大行高
        int maxLineHeight = 0;
        //已使用 width
        int usedWidth = 0;
        //总高度
        int totalHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //已使用的 width 计算
            usedWidth += lp.leftMargin + lp.rightMargin + childWidth;
            //当前 view 的高度
            lineHeight = childHeight + lp.topMargin + lp.bottomMargin;

            //当剩余空间不足时，换行
            if (usedWidth > childWidthSpace) {
                totalHeight += maxLineHeight;
                //重置 left
                childLeft = paddingLeft;
                //增加 top 值
                childTop = paddingTop + totalHeight;
                maxLineHeight = 0;
                usedWidth = lp.leftMargin + lp.rightMargin + childWidth;
            }

            maxLineHeight = Math.max(lineHeight, maxLineHeight);


            childLeft += lp.leftMargin;
            childTop += lp.topMargin;
            childRight = childLeft + childWidth;
            childBottom = childTop + childHeight;

            childView.layout(childLeft, childTop, childRight, childBottom);

            childLeft = childRight + lp.rightMargin;
        }
    }

    //添加 子 view
    public void setAdapter(BaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i, null, null);
            Log.e("=========",view.getLayoutParams()+"");
            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.itemClick(position);
                    }
                }
            });
            addView(view);
        }
        requestLayout();
    }

    public void setItemClickListener(TagItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface TagItemClickListener {
        void itemClick(int position);
    }



    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
