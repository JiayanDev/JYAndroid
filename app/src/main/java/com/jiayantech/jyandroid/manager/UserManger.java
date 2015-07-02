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
    public static Map<String, String> sProjectCategoryData;
    public static Map<String, List<String>> sProjectCategoryLevels = new HashMap<>();
    public static List<String> sProjectCategoryTopLevels = new ArrayList<>();

    public static void saveLogin(Login login) {
        sLogin = login;
        sProjectCategoryData = login.projectCategory.data;
        Set<Map.Entry<String, String>> entrySet = login.projectCategory.data.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            if (key.length() > 2) {
                String parent = key.substring(0, 2);
                List<String> list = sProjectCategoryLevels.get(parent);
                if (list == null) {
                    list = new ArrayList<>();
                    sProjectCategoryLevels.put(parent, list);
                }
                list.add(key);
            }
        }
        sProjectCategoryTopLevels = new ArrayList(sProjectCategoryLevels.keySet());
    }

    public List<String> getProjectCategoryTopLevels(){
        return sProjectCategoryTopLevels;
    }
}
