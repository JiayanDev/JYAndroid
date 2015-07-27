package com.jiayantech.library.comm;

import com.jiayantech.library.base.BaseApplication;

/**
 * Created by janseon on 2015/7/20.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class DataManager {
    private static final String FILE_NAME = "data";

    public static void put(String key, String value) {
        getDataShared().put(key, value);
    }

    public static String get(String key) {
        return getDataShared().get(key);
    }

    private static DataShared getDataShared() {
        return new DataShared(BaseApplication.getContext(), FILE_NAME);
    }
}
