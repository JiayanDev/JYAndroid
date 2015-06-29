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

    private static final int DEFAULT_ID = android.R.drawable.ic_dialog_alert;

    private static final String KEY_BASE_URL = "base.url";
    private static final String KEY_IMAGE_TYPE = "image.type";
    private static final String KEY_IMAGE_DEFAULT_ID = "image.default.id";
    private static final String KEY_IMAGE_ERROR_ID = "image.error.id";


    /**
     * base server url
     */
    public static final String BASE_URL = Property.getProperty(KEY_BASE_URL);
    //  private static final String IMAGE_TYPE = Property.getProperty(KEY_IMAGE_TYPE);
    //  public static final int DEFAULT_IMAGE_ID = sResources.getIdentifier(Property.getProperty(KEY_IMAGE_DEFAULT_ID), IMAGE_TYPE, sPackageName);
    //  public static final int ERROR_IMAGE_ID = sResources.getIdentifier(Property.getProperty(KEY_IMAGE_ERROR_ID), IMAGE_TYPE, sPackageName);
    public static int DEFAULT_IMAGE_ID = DEFAULT_ID;
    public static int ERROR_IMAGE_ID = DEFAULT_ID;

}
