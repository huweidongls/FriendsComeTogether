package com.yiwo.friendscometogether.wangyiyunshipin.upload.controller;

import android.content.Context;

/**
 * Created by zhukkun on 2/22/17.
 */
abstract class BaseController {

    private boolean mInit;
    protected Context mContext;

    public void init(Context context){
        if (isInit()) {
            return;
        }
        this.mContext = context;
        onInit();
        mInit = true;
    }

    public void suspend(){
        onSuspend();
        mInit = false;
        this.mContext = null;
    }

    public final boolean isInit(){
        return mInit;
    }

    protected abstract void onInit();

    protected abstract void onSuspend();
}
