package com.jiayantech.umeng_push.model;

/**
 * Created by liangzili on 15/7/29.
 * 友盟推送的model
 */

public class BasePushMessage<T> {
    public String action;
    public int code;
    public String msg;
    public T data;
}
