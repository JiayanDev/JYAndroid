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
public class DiaryBiz {
    private static final String MODEL = "diary";

    private static final String ACTION_CREATE = MODEL + "/create";
    private static final String ACTION_VERIFY = MODEL + "/verify";
    private static final String ACTION_POST = MODEL + "/post";
    private static final String ACTION_MY_HEADER = MODEL + "/my_header";
    private static final String ACTION_MY_DIARY = MODEL + "/my_diary";

    public static void create(String categoryId, String operationTime, String hospitalId, String doctorId, String price, String satisfyLevel, String tags, String previousPhotoes, String currentPhotoes, ResponseListener<?> l) {
        //HttpReq.getInitParams("configVersion", configVersion);
        HttpReq.post(ACTION_CREATE, null, l);
    }

    public static void verify(String diaryHeaderId, String status, ResponseListener<?> l) {
        HttpReq.post(ACTION_VERIFY, null, l);
    }

    public static void post(String headerId, String content, String photoes, ResponseListener<?> l) {
        HttpReq.post(ACTION_POST, null, l);
    }

    public static void myHeader(String sinceId, String maxId, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_HEADER, null, l);
    }

    public static void myDiary(String headerId, String sinceId, String maxId, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_DIARY, null, l);
    }
}
