package com.jiayantech.jyandroid.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.commons.Constants;
import com.jiayantech.library.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static SocialLoginBiz.GetCodeListener sGetCodeListener;

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
                    sGetCodeListener.onGetCode(code);
                } else {
                    ToastUtil.showMessage("code:" + code);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sGetCodeListener = null;
    }
}
