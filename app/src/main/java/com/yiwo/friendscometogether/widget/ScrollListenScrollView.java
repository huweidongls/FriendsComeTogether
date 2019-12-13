package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ljc on 2019/12/11.
 */

public class ScrollListenScrollView extends ScrollView{
    private OnScrollListener listener;

    public ScrollListenScrollView(Context context) {
        super(context);
    }

    public ScrollListenScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListenScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }


    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t);
        }
    }
}
