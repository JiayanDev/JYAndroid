package com.jiayantech.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jiayantech.library.base.BaseApplication;

/**
 * Created by liangzili on 15/7/8.
 */
public class UIUtil {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showSoftKeyBoard(Context context, View editText){
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

    public static float dip2px(float dip) {
        return BaseApplication.getContext().getResources().getDisplayMetrics().density * dip;
    }

    public static int dip2px(int dip) {
        return (int) (BaseApplication.getContext().getResources().getDisplayMetrics().density * dip);
    }

    public static float getDimension(int resId) {
        return BaseApplication.getContext().getResources().getDimension(resId);
    }

    public static int px2dip(int pixel){
        return 0;
    };
}
