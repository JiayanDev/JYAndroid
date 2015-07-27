//package com.jiayantech.jyandroid.biz;
//
//import android.app.Activity;
//import android.content.Context;
//
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//
///**
// * Created by liangzili on 15/7/6.
// */
//public class UmengShareBiz {
//    private static final String WECHAT_APP_ID = "wx1a08863dfa30e584";
//    private static final String WECHAT_APP_SECRET = "35999d388f94b493549a78c2bf2a20f9";
//
//    public static void initShareModule(Context context){
//        initWechatShare(context);
//        initQQShare(context);
//    }
//
//    public static void share(Activity activity, String content){
//        share(activity, content, null);
//    }
//
//    public static void share(Activity activity, String content, String imagePath){
//        UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share");
//        controller.setShareContent(content);
//        if(imagePath != null){
//            controller.setShareImage(new UMImage(activity, imagePath));
//        }
//        controller.openShare(activity, false);
//    }
//
//    private static void initWechatShare(Context context){
//
//        //添加分享微信
//        UMWXHandler wxHandler = new UMWXHandler(context, WECHAT_APP_ID);
//        wxHandler.addToSocialSDK();
//
//        //添加分享到微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(context, WECHAT_APP_ID);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//    }
//
//    private static void initQQShare(Context context){
//
//    }
//}
