package com.jiayantech.library.utils;

import com.jiayantech.library.base.BaseApplication;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Utils {
    public static float dp2px(float dp) {
        return dp * BaseApplication.getContext().getResources().getDisplayMetrics().density;
    }
}
