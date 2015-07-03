package com.jiayantech.jyandroid.biz;

import android.support.v4.util.ArrayMap;

import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.Map;

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
        Map<String, String> params = new ArrayMap<>();
        params.put("id", id);
        HttpReq.post(ACTION_CREATE, params, l);
    }


        public static void comment(String subjectId, String subject, String content,
                                   ResponseListener<?> l) {
        Map<String, String> params = new ArrayMap<>();
        params.put("subjectId", subjectId);
        params.put("subject", subject);
        params.put("content", content);
        HttpReq.post(ACTION_MY_TOPIC, params, l);
    }

    public static void verify(String id, String status, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_TOPIC, null, l);
    }


}
