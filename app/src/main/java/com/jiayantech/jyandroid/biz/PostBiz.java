package com.jiayantech.jyandroid.biz;

import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PostBiz {
    private static final String MODEL = "post";

    private static final String ACTION_CREATE = MODEL + "/like";
    private static final String ACTION_MY_TOPIC = MODEL + "/comment";
    private static final String ACTION_VERIFY = MODEL + "/verify";


    public static void like(String id, String type, ResponseListener<?> l) {
        HttpReq.post(ACTION_CREATE, null, l);
    }


    public static void comment(String id, String type, String content, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_TOPIC, null, l);
    }

    public static void verify(String id, String status, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_TOPIC, null, l);
    }
}
