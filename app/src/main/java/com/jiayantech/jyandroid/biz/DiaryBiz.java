package com.jiayantech.jyandroid.biz;

import android.text.TextUtils;

import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

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
    public static final String ACTION_MY_HEADER = MODEL + "/my_headers";
    private static final String ACTION_MY_DIARY = MODEL + "/my_diarys";

    public static void create(String categoryIds, long operationTime, String hospitalId, String doctorId, String doctorName, String price, float satisfyLevel, String post_content, String post_photoes, ResponseListener<?> l) {
        Map<String, String> params = new HashMap<>();
        HttpReq.putParams(params, "categoryIds", categoryIds);
        HttpReq.putParams(params, "operationTime", operationTime);
        HttpReq.putParams(params, "hospitalId", hospitalId);
        if (TextUtils.isEmpty(doctorId)) {
            HttpReq.putParams(params, "doctorName", doctorName);
        } else {
            HttpReq.putParams(params, "doctorId", doctorId);
        }
        HttpReq.putParams(params, "price", price);
        HttpReq.putParams(params, "satisfyLevel", (int) satisfyLevel);
        //HttpReq.putParams(params, "tags", tags);
        //HttpReq.putParams(params, "previousPhotoes", previousPhotoes);
        //HttpReq.putParams(params, "currentPhoto", currentPhoto);
        HttpReq.putParams(params, "post_content", post_content);
        HttpReq.putParams(params, "post_photoes", post_photoes);
        HttpReq.post(ACTION_CREATE, params, l);
    }

    public static void verify(String diaryHeaderId, String status, ResponseListener<?> l) {
        HttpReq.post(ACTION_VERIFY, null, l);
    }

    public static void post(String content, String photoes, ResponseListener<?> l) {
        Map<String, String> params = new HashMap<>();
        HttpReq.putParams(params, "content", content);
        HttpReq.putParams(params, "photoes", photoes);
        HttpReq.post(ACTION_POST, params, l);
    }

    public static void myHeader(String sinceId, String maxId, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_HEADER, null, l);
    }

    public static void myDiary(String headerId, String sinceId, String maxId, ResponseListener<?> l) {
        HttpReq.post(ACTION_MY_DIARY, null, l);
    }
}
