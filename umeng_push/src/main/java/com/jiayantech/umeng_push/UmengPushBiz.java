//package com.jiayantech.umeng_push;
//
//import android.content.Context;
//import android.content.IntentFilter;
//
//import com.umeng.message.PushAgent;
//import com.umeng.message.UmengRegistrar;
//
///**
// * Created by liangzili on 15/7/6.
// */
//public class UmengPushBiz {
//    public static final int CODE_DIARY_COMMENTED = 1; //用户日记被评论
//    public static final int CODE_COMMENT_COMMENTED = 2; // 用户评论被评论
//
//    public static final int CODE_ANGEL_APPLY_ACCEPT = 10; // 美丽天使申请通过
//    public static final int CODE_ANGEL_APPLY_DENY  = 11; // 美丽天使申请不通过
//
//    public static final int CODE_COMPANY_APPLY_ACCEPT = 20; // 伴美申请通过
//    public static final int CODE_COMPANY_APPLY_DENY = 21; // 伴美申请不通过
//
//    public static final int CODE_PUSH_AD = 50; //推送广告
//
//    public static final String JUMP_TO_PAGE_DIARY = "diary_detail";
//    public static final String JUMP_TO_PAGE_TOPIC = "topic_detail";
//    public static final String JUMP_TO_PAGE_MY_ANGEL = "my_angel";
//    public static final String JUMP_TO_PAGE_MY_COMPANY = "my_company";
//
//    public static final String ACTION_JUMP_TO_PAGE = "jump_to_page";
//    public static final String ACTION_JUMP_TO_WEB = "jump_to_web";
//
//    private static Context appContext;
//    /**
//     * enable or disable umeng push notification service
//     * @param enable
//     */
//    public static void enablePush(Context context, boolean enable){
//        if(enable){
//            PushAgent.getInstance(context).enable();
//        }else{
//            PushAgent.getInstance(context).disable();
//        }
//    }
//
//    /**
//     * 上传umeng推送的device_token
//     *
//     */
//    public static String getDeviceToken(){
//        String device_token = UmengRegistrar.getRegistrationId(appContext);
//        return device_token;
//    }
//
//    /**
//     * 初始化友盟push
//     * @param applicationContext
//     */
//    public static void init(Context applicationContext) {
//        appContext = applicationContext;
//        //重写友盟自定义行为处理
//        PushAgent.getInstance(applicationContext).setNotificationClickHandler(
//                new JYUmengNotificationClickHandler());
//        //重写友盟推送通知栏
//        PushAgent.getInstance(applicationContext).setMessageHandler(
//                new JYUmengMessageHandler(applicationContext));
//
//        //注册广播, 处理友盟push过来的消息
//        PushBroadcastReceiver receiver = new PushBroadcastReceiver();
//        IntentFilter filter = new IntentFilter(PushBroadcastReceiver.ACTION);
//        applicationContext.registerReceiver(receiver, filter);
//    }
//}
