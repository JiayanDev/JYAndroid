package com.jiayantech.jyandroid.model;

import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.annotations.Expose;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.base.BaseModel;

/**
 * Created by janseon on 2015/7/16.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CategoriesModel extends BaseModel {
    public int[] categoryIds;
    @Expose
    private String categoryNames;

    public String getCategoryNames() {
        if (TextUtils.isEmpty(categoryNames)) {
            if (categoryIds != null) {
                StringBuilder builder = new StringBuilder();
                SparseArray<AppInit.Category> data = AppInitManger.getProjectCategoryData();
                for (int categoryId : categoryIds) {
                    AppInit.Category category = data.get(categoryId);
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
