package com.yiwo.friendscometogether.model;

/**
 * Created by Administrator on 2018/7/12.
 */

public class RequestModel<T> {
    private int code;//返回码
    private String message;//返回信息（是否注册成功）
    private T obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
