package com.jiayantech.jyandroid.manager;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Login;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class UserManger {
    public static Login sLogin;
    public static SparseArray<Login.Category> sProjectCategoryData = new SparseArray<>();

//    public static Map<String, String> sProjectCategoryData;
//    public static Map<String, List<String>> sProjectCategoryLevels = new HashMap<>();
//    public static List<String> sProjectCategoryTopLevels = new ArrayList<>();

    public static void saveLogin(Login login) {
        sLogin = login;
        listSave(login.projectCategory.data);
//        if (login.projectCategory.data instanceof Map) {
//            //mapSave((Map<String, String>) login.projectCategory.data);
//        } else if (login.projectCategory.data instanceof ArrayList) {
//            //mapSave(new HashMap<String, String>());
////            sProjectCategoryData = new HashMap<>();
////            listSave((ArrayList<Map<String, String>>) login.projectCategory.data);
//        }
    }

    //    public static void mapSave(Map<String, String> data) {
//        sProjectCategoryData = data;
//        Set<Map.Entry<String, String>> entrySet = data.entrySet();
//        for (Map.Entry<String, String> entry : entrySet) {
//            String key = entry.getKey();
//            if (key.length() > 2) {
//                String parent = key.substring(0, 2);
//                List<String> list = sProjectCategoryLevels.get(parent);
//                if (list == null) {
//                    list = new ArrayList<>();
//                    sProjectCategoryLevels.put(parent, list);
//                }
//                list.add(key);
//            }
//        }
//        sProjectCategoryTopLevels = new ArrayList(sProjectCategoryLevels.keySet());
//    }
//
    private static void listSave(ArrayList<Login.Category> data) {
        SparseIntArray resIdArray = new SparseIntArray();
        resIdArray.put(11, R.drawable.category_breast);
        resIdArray.put(10, R.drawable.category_face);
        resIdArray.put(20, R.drawable.category_nose);
        for (Login.Category category : data) {
            category.resId = resIdArray.get(category.id);
            sProjectCategoryData.put(category.id, category);
            if (category.sub != null && category.sub.size() > 0) {
                listSave(category.sub);
            }
        }
    }
//
//    public static List<String> getProjectCategoryTopLevels() {
//        return sProjectCategoryTopLevels;
//    }
}
