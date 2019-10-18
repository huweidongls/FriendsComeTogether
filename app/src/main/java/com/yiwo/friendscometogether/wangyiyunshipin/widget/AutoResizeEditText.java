package com.yiwo.friendscometogether.wangyiyunshipin.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.netease.nim.uikit.common.util.sys.ScreenUtil;

/**
 * Created by winnie on 2017/9/6.
 */

public class AutoResizeEditText extends AppCompatEditText {

    // 最小字体
    private static final float DEFAULT_MIN_TEXT_SIZE = 8.0f;
    // 最大字体
    private static final float DEFAULT_MAX_TEXT_SIZE = 28.0f;

    private Paint textPaint;
    private float minTextSize = DEFAULT_MIN_TEXT_SIZE;
    private float maxTextSize = DEFAULT_MAX_TEXT_SIZE;

    private float trySize;

    private int lastX = 0;
    private int lastY = 0;

    private boolean isMove = false;


    public AutoResizeEditText(Context context, AttributeSet attrs) {
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
    private void initialise() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (this.textPaint == null) {
            this.textPaint = new Paint();
            this.textPaint.set(this.getPaint());
        }
        this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        if (DEFAULT_MIN_TEXT_SIZE >= maxTextSize) {
            this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        }
        this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        this.minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.minTextSize, displayMetrics);
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     */
    private void fitText(String text, int textWidth) {
        if (textWidth > 0) {
            // 单行可见文字宽度
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            trySize = maxTextSize;
            // 先用最大字体写字
            textPaint.setTextSize(trySize);
            // 如果最大字体>最小字体 && 最大字体画出字的宽度>单行可见文字宽度
            while ((trySize > minTextSize) && (textPaint.measureText(text) > availableWidth)) {
                // 最大字体小一号
                trySize -= 1;
                // 保证大于最小字体
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                // 再次用新字体写字
                textPaint.setTextSize(trySize);
            }
            this.setTextSize(trySize);
        }
    }


    /**
     * 重写setText
     * 每次setText的时候
     *
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        this.initialise();
        String textString = text.toString();
        float trySize = maxTextSize;
        if (this.textPaint == null) {
            this.textPaint = new Paint();
            this.textPaint.set(this.getPaint());
        }
        this.textPaint.setTextSize(trySize);
        // 计算设置内容前 内容占据的宽度
        int textWidth = (int) this.textPaint.measureText(textString);
        // 拿到宽度和内容，进行调整
        this.fitText(textString, textWidth);
        super.setText(text, type);
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        this.fitText(text.toString(), this.getWidth());
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 如果当前view的宽度 != 原来view的宽度
        if (w != oldw) this.fitText(this.getText().toString(), w);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        this.fitText(this.getText().toString(), params.width);
        super.setLayoutParams(params);
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
        return super.onTouchEvent(event);
    }

    private void setPosition(int dx, int dy) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        layoutParams.leftMargin = layoutParams.leftMargin + dx;
        layoutParams.topMargin = layoutParams.topMargin + dy;
        setLayoutParams(layoutParams);
    }
}