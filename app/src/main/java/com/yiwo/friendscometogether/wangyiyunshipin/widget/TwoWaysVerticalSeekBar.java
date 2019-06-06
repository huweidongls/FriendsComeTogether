package com.yiwo.friendscometogether.wangyiyunshipin.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yiwo.friendscometogether.R;

import java.math.BigDecimal;

public class TwoWaysVerticalSeekBar extends View {

    private static final int CLICK_ON_PRESS = 1;    //点击在滑块上
    private static final int CLICK_INVAILD = 0;

    private static final int[] STATE_NORMAL = {};
    private static final int[] STATE_PRESSED = {android.R.attr.state_pressed, android.R.attr.state_window_focused};

    private Drawable mThumb;    //滑块

    private int mSeekBarWidth;  //控件宽度
    private int mSeekBarHeight; //滑动条高度
    private int mThumbWidth;    //滑块宽度
    private int mThumbHeight;   //滑块高度

    private double mThumbOffset = 0;    //滑块中心坐标
    private double mDefaultHighest = 100; // 默认最高值
    private double mDefaultLowest = -100; // 默认最低值
    private double mDefaultValue = 100; // 默认滑块位置
    private int mThumbMarginLeft = 40;   //滑动块距离左边框距离
    private int mThumbMarginRight = 40; //滑动块距离右边框距离
    private int mThumbMarginTop = 50; // 滑动块顶部距离上边框的距离
    private int mDistance = 0;  //滑动的总距离，固定值
    private int mFlag = CLICK_INVAILD;
    private OnSeekBarChangeListener mSeekBarChangeListener;
    private boolean isInit = false;

    public TwoWaysVerticalSeekBar(Context context) {
        this(context, null);
    }

    public TwoWaysVerticalSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public TwoWaysVerticalSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        Resources resources = getResources();
        mThumb = resources.getDrawable(R.mipmap.ic_blue_circle);

        mThumbWidth = mThumb.getIntrinsicWidth();
        mThumbHeight = mThumb.getIntrinsicHeight();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoWaysVerticalSeekBar);
        if (typedArray != null) {
            mSeekBarWidth = (int) typedArray.getDimension(R.styleable.TwoWaysVerticalSeekBar_seekbar_width, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInit) {
            return;
        }
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        mSeekBarHeight = height - mThumbMarginTop;
        mDistance = mSeekBarHeight - mThumbHeight;
        mThumbOffset = formatDouble((mDefaultValue / (mDefaultHighest - mDefaultLowest)) * (mDistance) + mThumbHeight / 2);
        setMeasuredDimension(mThumbMarginLeft + mThumbWidth + mThumbMarginRight, mThumbMarginTop + mSeekBarHeight);
        isInit = true;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int defaultWidth = 100;
        if (specMode == MeasureSpec.AT_MOST) {

        } else if (specMode == MeasureSpec.EXACTLY) {
            defaultWidth = specSize;
        }
        return defaultWidth;
    }

    @SuppressWarnings("unused")
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
        }
        //fill_parent
        else if (specMode == MeasureSpec.EXACTLY) {
        }

        return specSize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint text_Paint = new Paint();

        canvas.drawColor(getResources().getColor(R.color.color_black_CC000000), PorterDuff.Mode.SRC);

        int notScrollBarXleft = mThumbMarginLeft + mThumbWidth / 2 - mSeekBarWidth / 2;
        int notScrollBarXright = notScrollBarXleft + mSeekBarWidth;

        // 进度条背景色的左上右下
        text_Paint.setColor(Color.GRAY);
        canvas.drawRect(notScrollBarXleft, mThumbMarginTop + mThumbHeight / 2, notScrollBarXright, mThumbMarginTop + mSeekBarHeight - mThumbHeight / 2, text_Paint);

        // 拖动的进度条，并且比进度条背景色左右各多1个像素
        text_Paint.setColor(getResources().getColor(R.color.color_blue_FF2084FF));
        if (mThumbOffset > mSeekBarHeight / 2) {
            // 往下滑动
            canvas.drawRect(notScrollBarXleft - 1, mThumbMarginTop + mSeekBarHeight / 2, notScrollBarXright + 1, mThumbMarginTop + (int) mThumbOffset, text_Paint);
        } else {
            // 往上滑动
            canvas.drawRect(notScrollBarXleft - 1, mThumbMarginTop + (int) mThumbOffset, notScrollBarXright + 1, mThumbMarginTop + mSeekBarHeight / 2, text_Paint);
        }

        // 小圆点
        mThumb.setBounds(mThumbMarginLeft, mThumbMarginTop + (int) mThumbOffset - mThumbHeight / 2, mThumbMarginLeft + mThumbWidth, mThumbMarginTop + (int) mThumbOffset + mThumbHeight / 2);
        mThumb.draw(canvas);

        // (mDefaultHighest - mDefaultLowest) / mDistance)=>表示每个像素代表的进度
        // (mThumbOffset - mThumbWidth / 2) => 移动了多少像素
        double progress = formatDouble((mThumbOffset - mThumbWidth / 2) * (mDefaultHighest - mDefaultLowest) / mDistance);   //progress初始值为100
        progress = mDefaultHighest - progress;

        // 保留一位小数
        BigDecimal b = new BigDecimal(progress);
        double pro = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (mSeekBarChangeListener != null) {
            mSeekBarChangeListener.onProgressChanged(this, pro);
        }

        text_Paint.setTextAlign(Paint.Align.CENTER);
        text_Paint.setColor(getResources().getColor(R.color.color_blue_FF2084FF));
        text_Paint.setAntiAlias(true);  //抗锯齿
        text_Paint.setAlpha(255);   //透明度
        text_Paint.setTextSize(40);
        canvas.drawText(pro + "", notScrollBarXleft, 40, text_Paint);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mSeekBarChangeListener == null) {
                    break;
                }
                mFlag = getAreaFlag(event);
                if (mFlag == CLICK_ON_PRESS) {
                    mThumb.setState(STATE_PRESSED);
                    mSeekBarChangeListener.onProgressBefore();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mFlag == CLICK_ON_PRESS) {
                    float y = event.getY() - mThumbMarginTop;
                    if (y < 0 || y <= mThumbHeight / 2) {
                        mThumbOffset = mThumbHeight / 2;
                    } else if (y >= mSeekBarHeight - mThumbHeight / 2) {
                        mThumbOffset = mDistance + mThumbHeight / 2;
                    } else {
                        mThumbOffset = formatDouble(y);
                    }
                }
                refresh();
                break;
            case MotionEvent.ACTION_UP:
                mThumb.setState(STATE_NORMAL);
                if (mSeekBarChangeListener != null) {
                    mSeekBarChangeListener.onProgressAfter();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public int getAreaFlag(MotionEvent e) {
        float y = e.getY() - mThumbMarginTop;
        // 点击的范围，只能在thumb的坐标范围内（扩大一下热区范围）
        if (y >= (mThumbOffset - mThumbHeight / 2 - 20) && y <= (mThumbOffset + mThumbHeight / 2 + 20)) {
            return CLICK_ON_PRESS;
        } else {
            return CLICK_INVAILD;
        }

    }

    private void refresh() {
        invalidate();
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener) {
        this.mSeekBarChangeListener = mListener;
    }

    public void setMaxProgress(double maxProgress) {
        mDefaultHighest = maxProgress;
    }

    public void setLowestProgress(double lowestProgress) {
        mDefaultLowest = lowestProgress;
    }

    public void setDefaultValue(double defaultValue) {
        mDefaultValue = mDefaultHighest - defaultValue;
    }

    public interface OnSeekBarChangeListener {
        //滑动前
        public void onProgressBefore();

        //滑动中
        public void onProgressChanged(TwoWaysVerticalSeekBar seekBar, double progress);

        //滑动后
        public void onProgressAfter();
    }

    public static double formatDouble(double mDouble) {
        BigDecimal bd = new BigDecimal(mDouble);
        BigDecimal bd1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        mDouble = bd1.doubleValue();
        return mDouble;
    }
}