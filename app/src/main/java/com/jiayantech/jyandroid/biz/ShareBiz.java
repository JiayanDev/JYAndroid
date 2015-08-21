package com.jiayantech.jyandroid.biz;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.app.JYApplication;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by liangzili on 15/7/20.
 */
public class ShareBiz {
    //微信相关
    public static final int WECHAT_SESSION = 0;  //分享给朋友
    public static final int WECHAT_TIMELINE = 1; //分享到朋友圈
    public static final int WECHAT_FAVORITE = 2; //添加到微信收藏
    private static final String WECHAT_APP_ID = "wxb6f54f1fe561c970";
    private static IWXAPI sIWXAPI;


    public static void registerToWx(Context context){
        sIWXAPI = WXAPIFactory.createWXAPI(context, WECHAT_APP_ID, true);
        sIWXAPI.registerApp(WECHAT_APP_ID);
    }

    public static void shareToWechat(String url, String title, int type){

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpageObject;
        msg.title = title;
        msg.setThumbImage(BitmapFactory.decodeResource(JYApplication.getContext().getResources(),
                R.mipmap.icon_wechat));

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

        switch (type){
            case WECHAT_SESSION:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case WECHAT_TIMELINE:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case WECHAT_FAVORITE:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
        }
        sIWXAPI.sendReq(req);
    }
}
