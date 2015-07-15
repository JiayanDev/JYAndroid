package com.jiayantech.library.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.List;

/**
 * Created by janseon on 2015/7/15.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseGridAdapter<T> extends BaseSimpleModelAdapter<T> {
    private int itemHeight;

    public BaseGridAdapter(List<T> list) {
        super(list);
    }

    public void resetGridHeight(final RecyclerView recyclerView, final int spanCount) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                recyclerView.setAdapter(BaseGridAdapter.this);
                setItemHeight(recyclerView.getWidth() / spanCount);
                resetViewHeight(recyclerView, spanCount);
            }
        });
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void resetViewHeight(View view, int spanCount) {
        int columns = spanCount;
        int rows;
        if (getItemCount() % columns > 0) {
            rows = getItemCount() / columns + 1;
        } else {
            rows = getItemCount() / columns;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = itemHeight * rows;//最后加上分割线总高度
        view.setLayoutParams(params);
    }

    public static class ViewHolder<T> extends BaseSimpleModelAdapter.ViewHolder<T> {
        public ViewHolder(ViewGroup parent, int layoutId, BaseGridAdapter adapter) {
            super(parent, layoutId, adapter);
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = adapter.itemHeight;
            lp.height = adapter.itemHeight;
            itemView.setLayoutParams(lp);
        }
    }
}


