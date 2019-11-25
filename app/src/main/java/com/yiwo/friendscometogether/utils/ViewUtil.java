package com.yiwo.friendscometogether.utils;

/**
 * Created by ljc on 2019/11/19.
 */

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Instruction:控件相关工具类
 */


public class ViewUtil {

    public static void getViewWidth(final View view, final OnViewListener onViewListener) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(onViewListener!=null){
                    onViewListener.onView(view.getWidth(),view.getHeight());
                }
            }
        });
    }

    public interface OnViewListener {
        void onView(int width,int height);
    }
}