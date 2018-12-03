package com.yiwo.friendscometogether.utils;

/**
 * Created by Administrator on 2018/7/13.
 */

import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.pages.RegisterActivity;

public class TimeCount extends CountDownTimer {

    private SpannableString msp = null;
    private int timeNum = 60;
    private RegisterActivity activity;

    public int getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(int timeNum) {
        this.timeNum = timeNum;
    }

    public RegisterActivity getActivity() {
        return activity;
    }

    public void setActivity(RegisterActivity activity) {
        this.activity = activity;
    }

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * 倒计时中
     */
    @Override
    public void onTick(long millisUntilFinished) {
        activity.getCode_btn().setEnabled(
                false);
        int timeInt = (int) (millisUntilFinished / 1000);
        setTimeNum(timeInt);

        // 创建一个 SpannableString对象
        msp = new SpannableString("重新发送(" + timeInt + "秒)");

//        if (timeInt > 9) {
//            msp.setSpan(new ForegroundColorSpan(activity
//                            .getResources().getColor(R.color.yellow_ff9d00)), 0, 12,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
//        } else {
//            msp.setSpan(new ForegroundColorSpan(activity
//                            .getResources().getColor(R.color.yellow_ff9d00)), 0, 11,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
//        }
        activity.getCode_btn().setTextColor(activity.getResources().getColor(R.color.blue_449CFA));
        activity.getCode_btn().setText(msp);
//        activity.getCode_btn().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.white_60white_box));
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onFinish() {
        // 创建一个 SpannableString对象
        msp = new SpannableString("获取验证码");

        // 设置字体背景色
        msp.setSpan(new ForegroundColorSpan(activity
                        .getResources().getColor(R.color.white_ffffff)), 0, 0,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为

//        activity.getCode_btn().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.white_60white_box));
        activity.getCode_btn().setText(msp);
        activity.getCode_btn().setEnabled(true);
        setTimeNum(60);
    }
}
