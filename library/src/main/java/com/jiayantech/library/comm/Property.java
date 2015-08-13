package com.jiayantech.library.comm;

import com.jiayantech.library.base.BaseApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by janseon on 2015/6/29.
 *
 * @Description: 配置信息
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Property {

    /**
     * 获取配置文件的属性值
     *
     * @param key 键
     * @return
     */
    public static String getProperty(String key) {
        if (sProperties == null) {
            return null;
        } else {
            return sProperties.getProperty(key);
        }
    }

    private static final Properties sProperties = new Properties();
    static {
        try {
            InputStream is = BaseApplication.getContext().getAssets().open("apptest.properties");
            sProperties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
