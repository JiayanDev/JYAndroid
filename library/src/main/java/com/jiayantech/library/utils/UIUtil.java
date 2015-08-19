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

//    /**
//     * 给Button设置一个DrawableLeft
//     * @param icon  要设置的Drawable
//     * @param button 要设置的按钮
//     */
//    public static void setPaddingLeft(Drawable icon, Button button){
//        SpannableString spanText = new SpannableString(button.getText());
//        int a = (int)button.getTextSize();
//        icon.setBounds(0, 0, a, a);
//        ImageSpan imageSpan = new ImageSpan(icon, ImageSpan.ALIGN_BASELINE);
//        spanText.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        button.setText(spanText);
//    }

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
    }
}
