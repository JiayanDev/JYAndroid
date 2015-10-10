package com.jiayantech.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

    public static void takePhoneCall(Context context, String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public static void openBrowser(Context context, String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
