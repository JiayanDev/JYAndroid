package com.jiayantech.library.utils;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class DialogUtils {

    public static Dialog showViewDialog(View view, boolean isBottom) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (isBottom) {
            localLayoutParams.gravity = Gravity.BOTTOM;
        }
        window.setAttributes(localLayoutParams);
        dialog.show();
        return dialog;
    }
}
