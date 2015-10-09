package com.jiayantech.jyandroid.biz;

import android.support.v4.util.ArrayMap;

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
    public static final int MODE_LIKE = 0;
    public static final int MODE_CANCEL_LIKE = 1;

    private static final String MODEL = "post";

    private static final String ACTION_LIKE = MODEL + "/like";
    private static final String ACTION_CANCEL_LIKE = MODEL + "/cancel_like";

    private static final String ACTION_MY_TOPIC = MODEL + "/comment";
    //private static final String ACTION_VERIFY = MODEL + "/verify";
    public static final String ACTION_LIST = MODEL + "/list";
    private static final String ACTION_TOPIC_ONE = "recommend/topic/one";
    private static final String ACTION_TOPIC_LIST = "recommend/topic/list";

    public static void getOneTopic(ResponseListener<?> l) {
        HttpReq.get(ACTION_TOPIC_ONE, null, l);
    }

    public static void getTopicList(ResponseListener<?> l) {
        HttpReq.get(ACTION_TOPIC_LIST, null, true, true, l);
    }

    public static void like(String id, boolean hasLike, ResponseListener<?> l) {
        Map<String, String> params = new ArrayMap<>();
        if (hasLike) {
            params.put("postId", id);
            HttpReq.post(ACTION_CANCEL_LIKE, params, l);
        } else {
            params.put("id", id);
            HttpReq.post(ACTION_LIKE, params, l);
        }
    }


    public static void comment(long subjectId, String subject, String content,
                               ResponseListener<?> l) {
        Map<String, String> params = new ArrayMap<>();
        params.put("subjectId", String.valueOf(subjectId));
        params.put("subject", subject);
        params.put("content", content);
        HttpReq.post(ACTION_MY_TOPIC, params, l);
    }

    public static void verify(String id, String status, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_TOPIC, null, l);
    }


}
