package com.jiayantech.umeng_push.model;

import com.jiayantech.library.base.BaseModel;

/**
 * Created by liangzili on 15/7/29.
 * 友盟推送的model
 */

public class BasePushMessage<T> extends BaseModel{
    public String action;
    public int code;
    public String msg;
    public T data;

    public long fromUserId;
    public String fromUserName;
    public String fromUserRole;
    public String fromUserAvatar;

    public long commentId;
    public String commentContent;
    public String subject;
    public long subjectId;
    public String subjectContent;

}
