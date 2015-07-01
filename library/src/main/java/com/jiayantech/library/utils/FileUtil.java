package com.jiayantech.library.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import com.jiayantech.library.base.BaseApplication;

/**
 * @author 健兴
 * @version 1.0
 * @Description 文件工具类
 * @date 2014-4-19
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class FileUtil {
    /**
     * 描述：获取缓存文件路径
     *
     * @param fileName
     * @return
     * @version 1.0
     * @createTime 2014-4-28 下午5:11:26
     * @createAuthor 健兴
     * @updateTime 2014-4-28 下午5:11:26
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getCachePath(String dir, String fileName) {
        File fileDir = getDiskCacheDir(BaseApplication.getContext(), dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir.getAbsolutePath() + File.separator + fileName;
    }

    /**
     * 获取可以使用的缓存目录
     *
     * @param context
     * @param uniqueName 目录名称
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取程序外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 根据路径获取文件名
     * @param path
     * @return
     */
    public static String getFileName(String path){
        File file = new File(path);
        String result = file.getName();
        return result;
    }
}
