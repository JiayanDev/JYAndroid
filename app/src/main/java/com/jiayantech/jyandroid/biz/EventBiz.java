package com.jiayantech.jyandroid.biz;

import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by janseon on 2015/6/28.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class EventBiz {
    private static final String MODEL = "event";

    private static final String ACTION_CREATE = MODEL + "/create";
    private static final String ACTION_APPLY = MODEL + "/apply";
    private static final String ACTION_COMMENT = MODEL + "/comment";
    public static final String ACTION_LIST = MODEL + "/list";
    public static final String ACTION_HOMEPAGE_LIST = "homepage/event/list";

    /**
     * title  必填，标题
     * desc 必填，描述
     * applyBeginTime  报名开始时间
     * applyEndTime     报名截止时间
     * beginTime    必填，活动开始时间
     * endTime    活动结束时间
     * //-projectIds   项目ID，字符串类型，以逗号隔开-
     * categoryIds  必填，分类
     * hospitalId  必填，医院ID
     * doctorId 必填，医生ID
     * photos 照片，多张照片以逗号隔开 []
     * province   省， 活动地点，如果指定了医院就不需要填地址
     * city  城市
     * district 区
     * addr  详细地址
     * _userId  //测试用，正常情况下不用传 从session中获取
     **/
    public static void create(String name, String phone, String title, String desc, long applyBeginTime, long applyEndTime, long beginTime, long endTime, String categoryIds,
                              long hospitalId, String hospitalName, long doctorId, String doctorName, String photos, String province, String city, String district, String addr, ResponseListener<?> l) {
        Map<String, String> params = new HashMap<>();
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "phoneNum", phone);
        //HttpReq.putParams(params, "title", title);
        //HttpReq.putParams(params, "desc", desc);
        //HttpReq.putParams(params, "applyBeginTime", applyBeginTime);
        //HttpReq.putParams(params, "applyEndTime", applyEndTime);
        HttpReq.putParams(params, "beginTime", beginTime);
        //HttpReq.putParams(params, "endTime", endTime);
        HttpReq.putParams(params, "categoryIds", categoryIds);
        if (hospitalId == 0) {
            HttpReq.putParams(params, "hospitalName", hospitalName);
        } else {
            HttpReq.putParams(params, "hospitalId", hospitalId);
        }
        if (doctorId == 0) {
            HttpReq.putParams(params, "doctorName", doctorName);
        } else {
            HttpReq.putParams(params, "doctorId", doctorId);
        }
        //HttpReq.putParams(params, "photos", photos);
        //HttpReq.putParams(params, "province", province);
        //HttpReq.putParams(params, "city", city);
        //HttpReq.putParams(params, "district", district);
        //HttpReq.putParams(params, "addr", addr);
        HttpReq.post(ACTION_CREATE, params, l);
    }

    /**
     * eventId：必填，活动ID
     * phone：电话，必填，先拉取，有则自动填上，没有则为空，让用户自己填
     * name：必填，姓名，同上
     * gender：可选
     * desc：描述
     **/
    public static void apply(String eventId, String phone, String name, String gender, String desc, ResponseListener<?> l) {
        Map<String, String> params = new HashMap<>();
        HttpReq.putParams(params, "eventId", eventId);
        HttpReq.putParams(params, "phone", phone);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "gender", gender);
        HttpReq.putParams(params, "desc", desc);
        //HttpReq.post(ACTION_APPLY, null, l);
        HttpReq.get(ACTION_APPLY, params, l);
    }

    /**
     * satisfyLevel：满意度
     * _userId: 测试用，用户ID
     * content: 内容
     **/
    public static void comment(String satisfyLevel, String content, ResponseListener<?> l) {
        Map<String, String> params = new HashMap<>();
        HttpReq.putParams(params, "satisfyLevel", satisfyLevel);
        HttpReq.putParams(params, "content", content);
        HttpReq.post(ACTION_COMMENT, null, l);
    }
}