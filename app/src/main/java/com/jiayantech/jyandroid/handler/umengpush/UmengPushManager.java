package com.jiayantech.jyandroid.handler.umengpush;


import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.jyandroid.biz.UmengPushBiz;
import com.jiayantech.jyandroid.eventbus.UnreadMessageEvent;
import com.jiayantech.jyandroid.model.umengpush.BasePushMessage;
import com.jiayantech.library.comm.DataShared;
import com.jiayantech.library.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/9/1.
 */
public class UmengPushManager {
    private static final String TAG = "UmengPushManager";

    private static UmengPushManager sUmengPushManager;
    private static DataShared sDataShared;

    private static final String KEY_UNREAD_NOTIFICATION = "key_unread_notification";
    private static final String KEY_UNREAD_MY_COMPANY = "key_unread_my_company";
    private static final String KEY_UNREAD_MY_ANGEL = "key_unread_my_angel";


    private UmengPushManager() {
    }

    public static UmengPushManager getInstance() {
        if (sUmengPushManager == null) {
            sUmengPushManager = new UmengPushManager();
            sDataShared = new DataShared(JYApplication.getContext(), "notification");
        }
        return sUmengPushManager;
    }

    public int getUnreadNotificationCount() {
        return sDataShared.getInt(KEY_UNREAD_NOTIFICATION);
    }

    public boolean getUnreadCompanyCount() {
        return sDataShared.getBoolean(KEY_UNREAD_MY_COMPANY);
    }

    public boolean getUnreadAngelCount() {
        return sDataShared.getBoolean(KEY_UNREAD_MY_ANGEL);
    }

    public void setUnreadNotificationCount(int count) {
        sDataShared.putInt(KEY_UNREAD_NOTIFICATION, count);
        dispatchChange();
    }

    public void setUnreadCompanyCount(boolean flag) {
        sDataShared.putBoolean(KEY_UNREAD_MY_COMPANY, flag);
        dispatchChange();
    }

    public void setUnreadAngelCount(boolean flag) {
        sDataShared.putBoolean(KEY_UNREAD_MY_ANGEL, flag);
        dispatchChange();
    }

    private void dispatchChange() {
        UnreadMessageEvent event = new UnreadMessageEvent(
                sDataShared.getInt(KEY_UNREAD_NOTIFICATION),
                sDataShared.getBoolean(KEY_UNREAD_MY_COMPANY),
                sDataShared.getBoolean(KEY_UNREAD_MY_ANGEL));
        EventBus.getDefault().post(event);
    }

    public void dispatch(BasePushMessage message) {
        LogUtil.i(TAG, "dispatch pushMessage : " + message.action);
        switch (message.code) {
            case UmengPushBiz.CODE_DIARY_COMMENTED:
            case UmengPushBiz.CODE_COMMENT_COMMENTED:
            case UmengPushBiz.CDOE_PUSH_AD:
                int notification = sDataShared.getInt(KEY_UNREAD_NOTIFICATION);
                setUnreadNotificationCount(notification + 1);
                break;
            case UmengPushBiz.CODE_ANGEL_APPLY_ACCEPT:
            case UmengPushBiz.CODE_ANGEL_APPLY_DENY:
                setUnreadAngelCount(true);
                break;
            case UmengPushBiz.CODE_COMPANY_APPLY_ACCEPT:
            case UmengPushBiz.CODE_COMPANY_APPLY_DENY:
                setUnreadCompanyCount(true);
                break;
        }
    }


}
