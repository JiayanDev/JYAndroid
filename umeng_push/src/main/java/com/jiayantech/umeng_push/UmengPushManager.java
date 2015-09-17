package com.jiayantech.umeng_push;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.jiayantech.library.comm.DataShared;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.umeng_push.model.BasePushMessage;
import com.jiayantech.umeng_push.model.PushMessageClickAction;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/9/1.
 */
public class UmengPushManager {
    private static final String TAG = "UmengPushManager";

    public static final int CODE_DIARY_COMMENTED = 1; //用户日记被评论
    public static final int CODE_TOPIC_COMMENTED = 2; // 用户话题被评论
    public static final int CODE_EVENT_COMMENTED = 3; //用户事件被评论
    public static final int CODE_COMMENT_COMMENTED = 4; //用户评论被评论

    public static final int CODE_ANGEL_APPLY_ACCEPT = 10; // 美丽天使申请通过
    public static final int CODE_ANGEL_APPLY_DENY  = 11; // 美丽天使申请不通过

    public static final int CODE_COMPANY_APPLY_ACCEPT = 20; // 伴美申请通过
    public static final int CODE_COMPANY_APPLY_DENY = 21; // 伴美申请不通过

    public static final int CODE_PUSH_AD = 50; //推送广告

    public static final String JUMP_TO_PAGE_DIARY = "diary_detail";  //跳转到日记详情
    public static final String JUMP_TO_PAGE_TOPIC = "topic_detail";  //跳转到话题详情
    public static final String JUMP_TO_PAGE_MY_ANGEL = "my_angel";   //跳转到我的天使列表
    public static final String JUMP_TO_PAGE_MY_COMPANY = "my_company";//跳转到我的伴美

    public static final String ACTION_JUMP_TO_PAGE = "jump_to_page"; //跳转到本地Activity
    public static final String ACTION_JUMP_TO_WEB = "jump_to_web"; //跳转到web页面

    private static Context appContext;

    private volatile static UmengPushManager sUmengPushManager;
    private static DataShared sDataShared;

    private static final String KEY_UNREAD_NOTIFICATION = "key_unread_notification";
    private static final String KEY_UNREAD_MY_COMPANY = "key_unread_my_company";
    private static final String KEY_UNREAD_MY_ANGEL = "key_unread_my_angel";

    private int mUnreadNotification;
    private boolean mUnreadCompany;
    private boolean mUnreadAngel;

    private UmengPushConfig mUmengPushConfig;

    private List<PushMessageClickAction> mClickActionList =
            new ArrayList<PushMessageClickAction>();



    private UmengPushManager() {
    }

    public static UmengPushManager getInstance() {
        if (sUmengPushManager == null) {
            synchronized (UmengPushManager.class) {
                if(sUmengPushManager == null) {
                    sUmengPushManager = new UmengPushManager();
                }
            }
        }
        return sUmengPushManager;
    }

    public int getUnreadNotificationCount() {
        return mUnreadNotification;
    }

    public boolean getUnreadCompanyCount() {
        return mUnreadCompany;
    }

    public boolean getUnreadAngelCount() {
        return mUnreadAngel;
    }

    public synchronized void setUnreadNotificationCount(int count) {
        sDataShared.putInt(KEY_UNREAD_NOTIFICATION, count);
        mUnreadNotification = count;
        dispatchChange();
    }

    public synchronized void decUnreadNotificationCount(){
        if(mUnreadNotification > 0){
            mUnreadNotification--;
            sDataShared.putInt(KEY_UNREAD_NOTIFICATION, mUnreadNotification);
            dispatchChange();
        }
    }

    public synchronized void setUnreadCompanyCount(boolean flag) {
        sDataShared.putBoolean(KEY_UNREAD_MY_COMPANY, flag);
        mUnreadCompany = flag;
        dispatchChange();
    }

    public synchronized void setUnreadAngelCount(boolean flag) {
        sDataShared.putBoolean(KEY_UNREAD_MY_ANGEL, flag);
        mUnreadAngel= flag;
        dispatchChange();
    }

    private void dispatchChange() {
        UnreadMessageEvent event = new UnreadMessageEvent(
                mUnreadNotification, mUnreadCompany, mUnreadAngel);
        EventBus.getDefault().post(event);
    }

    public void dispatch(BasePushMessage message) {
        LogUtil.i(TAG, "dispatch pushMessage : " + message.action);
        switch (message.code) {
            case CODE_DIARY_COMMENTED:
            case CODE_COMMENT_COMMENTED:
            case CODE_PUSH_AD:
                int notification = sDataShared.getInt(KEY_UNREAD_NOTIFICATION);
                setUnreadNotificationCount(notification + 1);
                break;
            case CODE_ANGEL_APPLY_ACCEPT:
            case CODE_ANGEL_APPLY_DENY:
                setUnreadAngelCount(true);
                break;
            case CODE_COMPANY_APPLY_ACCEPT:
            case CODE_COMPANY_APPLY_DENY:
                setUnreadCompanyCount(true);
                break;
        }
    }

    /**
     * 处理通知栏点击事件
     * @param action
     * @param id
     * @param url
     */
    public void handleClickActionFromNotification(String action, long id, String url){
        for(PushMessageClickAction a: mClickActionList){
            if(a.action.equals(action)){
                a.executeAction(action, id, url);
            }
        }
    }

    public Intent createActionIntent(String action, long id, String url){
        for(PushMessageClickAction a: mClickActionList){
            if(a.action.equals(action)){
                Intent intent = a.createIntent(action, id, url);
                return intent;
            }
        }
        return null;
    }

    /**
     * 添加点击通知跳转的动作处理
     * @param action
     */
    public void addClickAction(PushMessageClickAction action){
        for(PushMessageClickAction a: mClickActionList){
            if(a.action.equals(action)){
                return;
            }
        }
        mClickActionList.add(action);
    }

    public void removeClickAction(String action){
        for(PushMessageClickAction a: mClickActionList){
            if(a.action.equals(action)){
                mClickActionList.remove(a);
            }
        }
    }

    /**
     * enable or disable umeng push notification service
     * @param enable
     */
    public static void enablePush(boolean enable){
        if(appContext == null){
            throw new RuntimeException("UmengPushManager is not initialized!");
        }

        if(enable){
            PushAgent.getInstance(appContext).enable();
        }else{
            PushAgent.getInstance(appContext).disable();
        }
    }

    /**
     * 上传umeng推送的device_token
     *
     */
    public static String getDeviceToken(){
        String device_token = UmengRegistrar.getRegistrationId(appContext);
        return device_token;
    }

    /**
     * 初始化友盟push
     * @param applicationContext
     */
    public void init(Context applicationContext, UmengPushConfig config) {
        appContext = applicationContext;
        sDataShared = new DataShared(applicationContext, "notification");
        mUnreadNotification = sDataShared.getInt(KEY_UNREAD_NOTIFICATION);
        mUnreadCompany = sDataShared.getBoolean(KEY_UNREAD_MY_COMPANY);
        mUnreadAngel = sDataShared.getBoolean(KEY_UNREAD_MY_ANGEL);
        mUmengPushConfig = config;

        //重写友盟自定义行为处理
        PushAgent.getInstance(applicationContext).setNotificationClickHandler(
                new JYUmengNotificationClickHandler(mUmengPushConfig.getMainActivityClass()));

        //重写友盟推送通知栏
        PushAgent.getInstance(applicationContext).setMessageHandler(
                new JYUmengMessageHandler(applicationContext));

        //注册广播, 处理友盟push过来的消息(custom message)
        PushBroadcastReceiver receiver = new PushBroadcastReceiver();
        IntentFilter filter = new IntentFilter(PushBroadcastReceiver.ACTION);
        applicationContext.registerReceiver(receiver, filter);
    }


}
