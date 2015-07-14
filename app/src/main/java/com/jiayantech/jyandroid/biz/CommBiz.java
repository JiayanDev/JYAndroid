package com.jiayantech.jyandroid.biz;

import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by janseon on 2015/7/10.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CommBiz {
    private static final String HOSPITAL_MODEL = "hospital";
    public static final String ACTION_HOSPITAL_OPTION = HOSPITAL_MODEL + "/option";

    private static final String DOCTOR_MODEL = "doctor";
    public static final String ACTION_DOCTOR_OPTION = DOCTOR_MODEL + "/option";

    public static void option(String action, String blurName, ResponseListener<?> l) {
        HttpReq.get(action, HttpReq.getInitParams("blurName", blurName), l);
    }
}
