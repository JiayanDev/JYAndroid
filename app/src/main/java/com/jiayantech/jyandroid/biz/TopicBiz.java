package com.jiayantech.jyandroid.biz;

import com.jiayantech.jyandroid.model.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class TopicBiz {
    private static final String MODEL = "topic";

    private static final String ACTION_CREATE = MODEL + "/create";
    private static final String ACTION_MY_TOPIC = MODEL + "/my_topic";

    public static void create(String categoryId, String content, String photoUrls, ResponseListener<AppResponse<BaseModel>> l) {
        Map<String, String> params = new HashMap<>();
        params.put("daddy", "8");
        params.put("categoryId", categoryId);
        params.put("content", content);
        params.put("photoUrls", photoUrls);
        HttpReq.post(ACTION_CREATE, params, l);
    }


    public static void myTopic(String headerId, String sinceId, String maxId, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_TOPIC, null, l);
    }
}
