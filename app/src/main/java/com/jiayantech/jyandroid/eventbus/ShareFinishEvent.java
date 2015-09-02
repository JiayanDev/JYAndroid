package com.jiayantech.jyandroid.eventbus;

/**
 * Created by liangzili on 15/9/1.
 */
public class ShareFinishEvent {
    public static final int ERR_CODE_SUCCESS = 0;
    public static final int ERR_CODE_FAIL = 1;

    public int errCode;

    public String msg;

    public ShareFinishEvent(int code, String msg){
        errCode = code;
        this.msg = msg;
    }
}
