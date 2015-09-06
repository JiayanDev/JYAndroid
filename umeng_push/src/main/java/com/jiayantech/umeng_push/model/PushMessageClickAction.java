package com.jiayantech.umeng_push.model;

/**
 * Created by liangzili on 15/9/6.
 */
public abstract class PushMessageClickAction {

    public String action;
    public String url;
    public PushMessageClickAction(String action){
        this.action = action;
    }

    public abstract void executeAction(String action, long id, String url);
}
