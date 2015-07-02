package com.jiayantech.jyandroid.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.jiayantech.library.comm.MD5;
import com.jiayantech.library.utils.FileUtil;

/**
 * @author 健兴
 * @version 1.0
 * @Description bitmap 相关的方法
 * @date 2014-4-28
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class BitmapUtil {

    /**
     * 描述：按某宽度解析图片文件为Bitmap
     *
     * @param path     文件路径
     * @param reqWidth 请求的宽度
     * @return
     * @version 1.0
     * @createTime 2014-4-29 上午11:38:51
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:38:51
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static Bitmap decodeSampledBitmapFromFilePath(String path, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 描述：压缩图片
     *
     * @param path 图片路径
     * @return 压缩成功后的图片路径
     * @version 1.0
     * @createTime 2014-4-29 上午11:39:54
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:39:54
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String compressBitmapFile(String path) {
        return compressBitmapFile(path, 640);
    }

    /**
     * 描述：压缩图片
     *
     * @param path     图片路径
     * @param reqWidth 求情的宽度
     * @return 压缩成功后的图片路径
     * @version 1.0
     * @createTime 2014-4-29 上午11:40:46
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:40:46
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String compressBitmapFile(String path, int reqWidth) {
        Bitmap bitmap = decodeSampledBitmapFromFilePath(path, reqWidth);
        String fileName = MD5.encode(path + "compress") + ".png";
        return savePicture(bitmap, "compress", fileName);
    }

    /**
     * 描述：计算压缩的inSampleSize值
     *
     * @param options
     * @param reqWidth
     * @return
     * @version 1.0
     * @createTime 2014-4-29 上午11:41:19
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:41:19
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        return inSampleSize;
    }

    /**
     * 描述：保存图片
     *
     * @param bitmap
     * @param dir
     * @param fileName
     * @return
     * @version 1.0
     * @createTime 2014-4-29 上午11:41:51
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:41:51
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String savePicture(Bitmap bitmap, String dir, String fileName) {
        if (bitmap == null || dir == null || dir.equals("") || fileName == null || fileName.equals("")) {
            return null;
        }
        FileOutputStream iStream = null;
        try {
            String filePath = FileUtil.getCachePath(dir, fileName);
            File fImage = new File(filePath);
            if (!fImage.exists()) {
                fImage.createNewFile();
            }
            iStream = new FileOutputStream(fImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            iStream.flush();
            iStream.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述：根据Intent对象获取图片路径
     *
     * @param activity
     * @param data
     * @return
     * @version 1.0
     * @createTime 2014-4-29 上午11:42:04
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午11:42:04
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getUriPath(Activity activity, Intent data) {
        if (data == null) {
            return null;
        }
        Uri uri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, proj, // Which
                // columns
                // to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }
}
