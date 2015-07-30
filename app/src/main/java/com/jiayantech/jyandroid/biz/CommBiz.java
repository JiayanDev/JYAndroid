package com.jiayantech.jyandroid.biz;

import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.Map;

/**
 * Created by janseon on 2015/7/10.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CommBiz {
    private static final String KEY_CONFIG_VERSION = "configVersion";

    private static final String APP_MODEL = "app";
    public static final String ACTION_APP_INIT = APP_MODEL + "/init";

    private static final String HOSPITAL_MODEL = "hospital";
    public static final String ACTION_HOSPITAL_OPTION = HOSPITAL_MODEL + "/option";

    private static final String DOCTOR_MODEL = "doctor";
    public static final String ACTION_DOCTOR_OPTION = DOCTOR_MODEL + "/option";

    public static void appInit(ResponseListener<?> l) {
        Map<String, String> params = HttpReq.getInitParams("deviceToken", UmengPushBiz.getDeviceToken());
        params.put(KEY_CONFIG_VERSION, ConfigManager.getConfig(KEY_CONFIG_VERSION, "0"));
        HttpReq.post(ACTION_APP_INIT, params, l);
    }

    public static AppInit appInitCache() {
        Map<String, String> params = HttpReq.getInitParams("deviceToken", UmengPushBiz.getDeviceToken());
        params.put(KEY_CONFIG_VERSION, ConfigManager.getConfig(KEY_CONFIG_VERSION, "0"));
        return HttpReq.getCache(ACTION_APP_INIT, params, AppInit.class);
    }

    public static void option(String action, String blurName, ResponseListener<?> l) {
        HttpReq.get(action, HttpReq.getInitParams("blurName", blurName), l);
    }
}
