package com.jiayantech.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiayantech.library.base.BaseApplication;

/**
 * @author 健兴
 * @version 1.0
 * @Description 不排队显示的Toast，如果当前存在Toast的显示，则替代以前的
 * @date 2014-4-19
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class ToastUtil {
    private static Toast toast = null;
    private static Object synObj = new Object();

    private static int Y_OFFSET = (int) (60 * BaseApplication.getContext().getResources().getDisplayMetrics().density);

    /**
     * 居中显示
     */
    public static void showCenterMessage(Context act, String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT, true);
    }

    /**
     * 底部显示
     */
    public static void showMessage(String msg) {
        Context act = BaseApplication.getContext();
        if (act != null) {
            showMessage(act, msg, Toast.LENGTH_SHORT, false);
        }
    }

    /**
     * 底部显示
     */
    public static void showMessage(Context act, String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT, false);
    }

    /**
     * 显示
     */
    public static void showMessage(Context act, int msg, boolean isCenter) {
        showMessage(act, "" + msg, Toast.LENGTH_SHORT, isCenter);
    }

    public static void showMessage(final Context act, final String msg, final int len, final boolean isCenter) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast == null) {
                                toast = Toast.makeText(act, msg, len);
                            } else {
                                toast.setText(msg);
                                toast.setDuration(len);
                            }
                            if (isCenter) {
                                toast.setGravity(Gravity.CENTER, 0, 0);
                            } else {
                                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, Y_OFFSET);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 描述：左边有图标，居中显示
     *
     * @param context
     * @param msg
     * @param iconId
     * @version 1.0
     * @createTime 2014-4-29 上午10:56:00
     * @createAuthor 健兴
     * @updateTime 2014-4-29 上午10:56:00
     * @updateAuthor 健兴
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void leftIconCenterAlert(Context context, String msg, int iconId) {
        float newDensity = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        // 定义一个ImageView
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (20 * newDensity), (int) (20 * newDensity));
        imageView.setImageResource(iconId);

        int padding = (int) (5 * newDensity);
        imageView.setPadding(padding, padding, padding, padding);
        // 获得Toast的View
        // View toastView = toast.getView();
        // toastView.setBackgroundColor(Color.TRANSPARENT);
        TextView textView = new TextView(context);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 18 * newDensity);
        textView.setTextColor(Color.WHITE);
        textView.setText(msg);
        // 定义一个Layout，这里是Layout
        LinearLayout linearLayout = new LinearLayout(context);

        ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK); // Color.DKGRAY
        colorDrawable.setAlpha(220);

        float radii = 5 * newDensity;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{radii, radii, radii, radii, radii, radii, radii, radii}, null, null));
        Paint paint = shapeDrawable.getPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        paint.setAlpha(180);

        linearLayout.setBackgroundDrawable(shapeDrawable);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 将ImageView和ToastView合并到Layout中
        linearLayout.addView(imageView, layoutParams);
        // linearLayout.addView(toastView);
        linearLayout.addView(textView);
        // 替换掉原有的ToastView
        toast.setView(linearLayout);
        toast.show();
    }

    public static void clearToast() {
        toast = null;
    }
}
