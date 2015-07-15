package com.jiayantech.jyandroid.manager;

import com.jiayantech.jyandroid.model.Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class UserManger {
    public static Login sLogin;
//    public static Map<String, String> sProjectCategoryData;
//    public static Map<String, List<String>> sProjectCategoryLevels = new HashMap<>();
//    public static List<String> sProjectCategoryTopLevels = new ArrayList<>();

    public static void saveLogin(Login login) {
        sLogin = login;
        if (login.projectCategory.data instanceof Map) {
            //mapSave((Map<String, String>) login.projectCategory.data);
        } else if (login.projectCategory.data instanceof ArrayList) {
            //mapSave(new HashMap<String, String>());
//            sProjectCategoryData = new HashMap<>();
//            listSave((ArrayList<Map<String, String>>) login.projectCategory.data);
        }
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
//    public static void listSave(ArrayList<Map<String, String>> data) {
//        sProjectCategoryTopLevels = new ArrayList<>(data.size());
//        for (Map<String, String> map : data) {
//            Set<Map.Entry<String, String>> entrySet = map.entrySet();
//            for (Map.Entry<String, String> entry : entrySet) {
//                String key = entry.getKey();
//                if (key.length() > 2) {
//                    String parent = key.substring(0, 2);
//                    List<String> list = sProjectCategoryLevels.get(parent);
//                    if (list == null) {
//                        list = new ArrayList<>();
//                        sProjectCategoryLevels.put(parent, list);
//                    }
//                    list.add(key);
//                }
//            }
//        }
//        sProjectCategoryTopLevels = new ArrayList(sProjectCategoryLevels.keySet());
//    }
//
//    public static List<String> getProjectCategoryTopLevels() {
//        return sProjectCategoryTopLevels;
//    }
}
