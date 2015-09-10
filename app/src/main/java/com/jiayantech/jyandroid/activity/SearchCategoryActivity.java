package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.Search;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/9/9.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchCategoryActivity extends SearchActivity {
    private final ArrayList<Search> mList = toSearchCategoryTopList();

    private ArrayList<Search> toSearchCategoryTopList() {
        ArrayList<Search> searchList = new ArrayList<>();
        ArrayList<AppInit.Category> list = AppInitManger.getProjectCategoryTopList();
        for (AppInit.Category category : list) {
            searchList.add(new Search(category.id, category.name));
        }
        return searchList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        option("");
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(getString(R.string.title_search_category));
    }

    @Override
    protected void option(String blurName) {
        mCurBlurName = blurName;
        if (TextUtils.isEmpty(blurName)) {
            optionResponse(blurName, mList);
            return;
        }
        ArrayList<Search> searchList = new ArrayList<>();
        for (Search search : mList) if (search.name.contains(blurName)) searchList.add(search);
        optionResponse(blurName, searchList);
    }
}
