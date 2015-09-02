package com.jiayantech.jyandroid.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.commons.Constants;
import com.jiayantech.jyandroid.eventbus.ShareFinishEvent;
import com.jiayantech.library.utils.ToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static SocialLoginBiz.GetCodeListener sGetCodeListener;

    public static void setGetCodeListener(SocialLoginBiz.GetCodeListener l) {
        sGetCodeListener = l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_appId, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.getType()){
            //sendauth的回调
            case ConstantsAPI.COMMAND_SENDAUTH:
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        //      可用以下两种方法获得code
                        //      Bundle bundle = new Bundle();
                        //      resp.toBundle(bundle);
                        //      Resp sp = new Resp(bundle);
                        //      String code = sp.code;<span style="white-space:pre">
                        //      或者
                        String code = ((SendAuth.Resp) resp).code;
                        //上面的code就是接入指南里要拿到的code
                        if (sGetCodeListener != null) {
                            SocialLoginBiz.GetCodeListener getCodeListener = sGetCodeListener;
                            getCodeListener.onGetCode(code);
                        } else {
                            ToastUtil.showMessage("code:" + code);
                        }
                        break;
                    default:
                        break;
                }
                finish();
                break;

            //分享的回调
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                switch (resp.errCode){
                    case BaseResp.ErrCode.ERR_OK:
                        EventBus.getDefault().post(
                                new ShareFinishEvent(ShareFinishEvent.ERR_CODE_SUCCESS, "分享成功"));
                }
                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sGetCodeListener != null) {
            sGetCodeListener = null;
        }
    }
}
