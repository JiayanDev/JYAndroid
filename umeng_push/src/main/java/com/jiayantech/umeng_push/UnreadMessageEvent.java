package com.jiayantech.umeng_push;

/**
 * Created by liangzili on 15/9/1.
 */
public class UnreadMessageEvent {
    public int unreadNotificaition;
    public boolean unreadCompany;
    public boolean unreadAngel;

    public UnreadMessageEvent(int notification, boolean company, boolean angel){
        unreadNotificaition = notification;
        unreadCompany = company;
        unreadAngel = angel;
    }
}
