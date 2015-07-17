package com.jiayantech.library.http;

import android.content.res.Resources;

import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.comm.Property;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: http config
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class HttpConfig {
    private static final Resources sResources = BaseApplication.getContext().getResources();
    private static final String sPackageName = BaseApplication.getContext().getPackageName();

    private static final int DEFAULT_ID = android.R.color.darker_gray;

    private static final String KEY_BASE_URL = "base.url";
    private static final String KEY_IMAGE_UPLOAD_URL = "image.upload.url";
    private static final String KEY_IMAGE_SHOW_URL = "image.show.url";


    /**
     * base server url
     */
    public static final String BASE_URL = Property.getProperty(KEY_BASE_URL);
    public static final String IMAGE_UPLOAD_URL = Property.getProperty(KEY_IMAGE_UPLOAD_URL);
    public static final String IMAGE_SHOW_URL = Property.getProperty(KEY_IMAGE_SHOW_URL);
    public static int DEFAULT_IMAGE_ID = DEFAULT_ID;
    public static int ERROR_IMAGE_ID = DEFAULT_ID;

}
