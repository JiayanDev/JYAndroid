package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by liangzili on 15/7/10.
 */
public class CategoryGridView extends RecyclerView{
    private OnItemClickListener mOnItemClickListener;
    private List<Category> mCategoryList;

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

    public class Category {
        public long id;
        public String title;
        public int imageId;
    }
}
