package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by liangzili on 15/7/10.
 */
public class CategoryGridView extends RecyclerView{
    private OnItemClickListener mOnItemClickListener;

    public CategoryGridView(Context context) {
        this(context, null);
    }

    public CategoryGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CategoryGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnItemClickListener(OnItemClickListener listener){


    }

    public interface OnItemClickListener{
        void onClick(long categoryId);
    }

}
