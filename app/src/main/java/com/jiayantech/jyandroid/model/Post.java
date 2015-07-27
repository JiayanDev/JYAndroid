package com.jiayantech.jyandroid.model;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/6/29.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Post extends CategoriesModel {
    public static final String POST_TYPE_DIARY = "diary";
    public static final String POST_TYPE_TOPIC = "topic";
    public String avatar;
    public String userName;
    public String content;
    public ArrayList<String> photoes;
    public long commentCount;
    public long likeCount;
    public long userId;
    public long diaryId;
    public String type;
    public boolean isLike;
    public String headerId;

}