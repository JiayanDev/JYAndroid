package com.jiayantech.jyandroid.commons;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.widget.ItemsLayout;
import com.jiayantech.library.utils.DialogUtils;

/**
 * Created by 健兴 on 2015/9/7.
 */
public class AppDialogUtils {
    public static Dialog showBottomDialog(Context context, String text, View.OnClickListener l) {
        return showBottomDialog(context, new String[]{text}, new View.OnClickListener[]{l});
    }

    public static Dialog showBottomDialog(Context context, String[] texts, View.OnClickListener[] listeners) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_menus, null);
        ItemsLayout itemsLayout = (ItemsLayout) view.findViewById(R.id.layout_items);
        itemsLayout.setDriver();
        itemsLayout.setDriverLeftMargin(0);
        final Dialog dialog = DialogUtils.showViewDialog(view, true);
        int length = texts.length;
        for (int i = 0; i < length; i++) {
            String text = texts[i];
            final View.OnClickListener l = listeners[i];
            itemsLayout.addMenuItem(text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    l.onClick(v);
                }
            });
        }
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
