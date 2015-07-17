package com.jiayantech.jyandroid.model;

import android.text.TextUtils;

import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.library.base.BaseModel;
import com.google.gson.annotations.Expose;

/**
 * Created by janseon on 2015/6/29.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Post extends CategoryModel {
    public static final String POST_TYPE_DIARY = "diary";
    public static final String POST_TYPE_TOPIC = "topic";
    public String userName;
    public String content;
    public String[] photoes;
    public long commentCount;
    public long likeCount;
    public String userId;
    public String diaryId;
    public String type;
    public boolean isLike;
    public String headerId;

}