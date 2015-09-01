package com.jiayantech.jyandroid.model;

import com.jiayantech.library.base.BaseModel;

/**
 * Created by liangzili on 15/8/6.
 */
public class HomePagePost extends BaseModel {
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_TOPIC = "topic";
    public static final String ROLE_ANGEL = "angel";

    public long userId;
    public String userName;
    public String userAvatar;
    public String role;

    public String type;
    public long eventId;
    public long topicId;
    public String title;
    public String coverImg;
    public String desc;
    public long hospitalId;
    public String hospitalName;
    public long beginTime;
    public long applymentCount;
    public int[] categoryIds;
}
