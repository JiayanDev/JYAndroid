package com.jiayantech.jyandroid.model;

import com.jiayantech.library.base.BaseModel;

/**
 * Created by liangzili on 15/8/6.
 */
public class HomePageEvent extends BaseModel{
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_TOPIC = "topic";

    public long eventId;
    public long topicId;
    public long applymentCount;
    public String hospitalName;
    public String itemType;
    public String title;
    public long userId;
    public String userName;
    public String userRole;
    public long hospitalId;
    public long[] hospitalIds;
    public long beginTime;
    public String[] photoes;
    public String desc;
}
