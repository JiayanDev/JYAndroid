package com.jiayantech.jyandroid.model.umengpush;

/**
 * Created by liangzili on 15/7/29.
 * 友盟推送的model
 */

public class BasePushMessage<T> {
    public String action;
    public int code;
    public String msg;
    T data;
}
