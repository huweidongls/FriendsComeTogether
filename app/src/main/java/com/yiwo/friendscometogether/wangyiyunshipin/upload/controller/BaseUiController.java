package com.yiwo.friendscometogether.wangyiyunshipin.upload.controller;

import android.content.Intent;


/**
 * Created by zhukkun on 2/22/17.
 */
abstract class BaseUiController< U extends Ui> extends BaseController {

    protected U mUi;

    public BaseUiController(){

    }

    public synchronized void attachUi(U ui){

        mUi = ui;
        if(isInit()){
            onUiAttached(ui);
            populateUi(ui);
        }
    }

    public synchronized void detachUi(U ui){
        onUiDetached(ui);
        mUi = null;
    }

    public abstract void handleIntent(Intent intent);

    /**
     * ui的绑定
     * @param ui
     */
    protected abstract void onUiAttached(U ui);

    /**
     * 可在该回调中,处理页面相关的数据
     * @param ui
     */
    protected abstract void populateUi(U ui);

    /**
     * ui的解除
     * @param ui
     */
    protected abstract void onUiDetached(U ui);

}
