package com.jiayantech.jyandroid.manager;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.comm.ConfigManager;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class AppInitManger {
    public static final String KEY_APP_INIT = "AppInit";

    private static AppInit sAppInit;
    private static SparseArray<AppInit.Category> sProjectCategoryData = new SparseArray<>();

    public static void save(AppInit appInit) {
        if (appInit.projectCategory == null && sAppInit != null) {
            appInit.projectCategory = sAppInit.projectCategory;
        }
        if (appInit.token == null && sAppInit != null) {
            appInit.token = sAppInit.token;
        }
        sAppInit = appInit;
        ConfigManager.putToken(appInit.token);
        ConfigManager.putConfig(KEY_APP_INIT, new Gson().toJson(appInit));
        listSave(appInit.projectCategory.data);
    }

    public static ArrayList<AppInit.Category> getProjectCategoryTopList() {
        initLoad();
        return sAppInit.projectCategory.data;
    }

    public static SparseArray<AppInit.Category> getProjectCategoryData() {
        initLoad();
        return sProjectCategoryData;
    }


    public static void initLoad() {
        if (sAppInit == null) {
            String value = ConfigManager.getConfig(KEY_APP_INIT);
            if (TextUtils.isEmpty(value)) {
                AppInit appInit = new Gson().fromJson(value, AppInit.class);
                save(appInit);
            }
        }
    }

    private static void listSave(ArrayList<AppInit.Category> data) {
        SparseIntArray resIdArray = new SparseIntArray();
        resIdArray.put(11, R.drawable.category_breast);
        resIdArray.put(10, R.drawable.category_face);
        resIdArray.put(20, R.drawable.category_nose);
        for (AppInit.Category category : data) {
            category.resId = resIdArray.get(category.id);
            sProjectCategoryData.put(category.id, category);
            if (category.sub != null && category.sub.size() > 0) {
                listSave(category.sub);
            }
        }
    }

    public static long getUserId() {
        initLoad();
        return sAppInit.id;
    }

    public static String getUserName() {
        initLoad();
        return sAppInit.name;
    }
}