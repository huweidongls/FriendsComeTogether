package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ljc on 2019/7/3.
 */

    public class CanNotScollViewPager extends ViewPager {

        private boolean isCanScroll = true;

        public CanNotScollViewPager(Context context) {
            super(context);
        }

        public CanNotScollViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * 设置其是否能滑动换页
         * @param isCanScroll false 不能换页， true 可以滑动换页
         */
        public void setScanScroll(boolean isCanScroll) {
            this.isCanScroll = isCanScroll;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return isCanScroll && super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return isCanScroll && super.onTouchEvent(ev);

        }
    }
