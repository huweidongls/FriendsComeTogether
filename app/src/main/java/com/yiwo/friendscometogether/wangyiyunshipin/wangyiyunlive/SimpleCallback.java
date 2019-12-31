package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive;

/**
 * Created by ljc on 2019/11/26.
 */

public interface SimpleCallback<T> {

    /**
     * 回调函数返回结果
     *
     * @param success 是否成功，结果是否有效
     * @param result  结果
     */
    void onResult(boolean success, T result);
}