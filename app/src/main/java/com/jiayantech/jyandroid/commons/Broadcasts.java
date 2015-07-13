package com.jiayantech.jyandroid.commons;

import com.jiayantech.library.base.BaseApplication;

/**
 * Created by janseon on 2015/7/13.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Broadcasts {
    public static final String sPackageName = BaseApplication.getContext().getPackageName();

    public static final String ACTION_PUBLISH_TOPIC = sPackageName + ".publishTopic";
    public static final String ACTION_PUBLISH_DIARY_BOOK = sPackageName + ".publishDiaryBook";
    public static final String ACTION_PUBLISH_DIARY = sPackageName + ".publishDiary";
}
