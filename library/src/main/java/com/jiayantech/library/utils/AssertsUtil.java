package com.jiayantech.library.utils;

import com.jiayantech.library.base.BaseApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 健兴
 * @version 1.0
 * @Description Asserts资源的相关工具
 * @date 2014-4-29
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class AssertsUtil {

    /**
     * 描述：获取Asserts文件夹里面某个文件的字符串内容
     *
     * @param fileName 文件名
     * @return
     * @version 1.0
     * @createTime 2014-4-19 上午10:29:34
     * @createAuthor 健兴
     * @updateTime 2014-4-119 上午10:29:34
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getAssertsFileString(String fileName) {
        try {
            InputStream is = BaseApplication.getContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
