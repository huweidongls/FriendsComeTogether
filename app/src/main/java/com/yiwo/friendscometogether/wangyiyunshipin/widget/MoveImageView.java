package com.yiwo.friendscometogether.wangyiyunshipin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.netease.nim.uikit.common.util.sys.ScreenUtil;

/**
 * Created by winnie on 2017/6/30.
 */

public class MoveImageView extends FrameLayout {

    private int lastX = 0;
    private int lastY = 0;

    private boolean isMove = false;

    public MoveImageView(Context context) {
        super(context);
    }

    public MoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public boolean isMove() {
        return isMove;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < 0) {
                    dx = 0;
                }
                if (right > ScreenUtil.screenWidth) {
                    dx = 0;
                }
                if (top < 0) {
                    dy = 0;
                }
                if (bottom > (ScreenUtil.screenHeight - (getHeight() / 2))) {
                    dy = 0;
                }

                setPosition(dx, dy);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }


    private void setPosition(int dx, int dy) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.leftMargin = layoutParams.leftMargin + dx;
        layoutParams.topMargin = layoutParams.topMargin + dy;
        setLayoutParams(layoutParams);
    }
}
