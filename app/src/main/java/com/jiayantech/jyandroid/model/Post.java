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
public class Post extends BaseModel {
    public static final String POST_TYPE_DIARY = "diary";
    public static final String POST_TYPE_TOPIC = "topic";
    public String userName;
    public String content;
    public String[] photoes;
    public long commentCount;
    public long likeCount;
    public long userId;
    public long diaryId;
    public String type;
    public boolean isLike;

    public long headerId;
    public int[] categoryIds;

    @Expose
    private String categoryNames;

    public String getCategoryNames() {
        if (TextUtils.isEmpty(categoryNames)) {
            if (categoryIds != null) {
                StringBuilder builder = new StringBuilder();
                for (int categoryId : categoryIds) {
                    Login.Category category = UserManger.sProjectCategoryData.get(categoryId);
                    if (category != null) {
                        builder.append(category.name);
                        builder.append(' ');
                    }
                }
                categoryNames = builder.toString();
            }
        }
        return categoryNames;
    }
}
