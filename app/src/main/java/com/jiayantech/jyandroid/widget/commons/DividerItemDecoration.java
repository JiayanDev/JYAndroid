package com.jiayantech.jyandroid.widget.commons;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.utils.UIUtil;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;

/**
 * Created by janseon on 2015/7/15.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class DividerItemDecoration extends HorizontalDividerItemDecoration {
    protected DividerItemDecoration(Builder builder) {
        super(builder);
    }

    @Override
    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        if (!mShowFirstEnable && position == 0) {
            return new Rect();
        }
        return super.getDividerBound(position, parent, child);
    }

    private boolean mShowFirstEnable = false;

    protected DividerItemDecoration showFirstEnable(boolean enable) {
        mShowFirstEnable = enable;
        return this;
    }

    public static class Builder extends HorizontalDividerItemDecoration.Builder {
        private boolean mShowFirstEnable;

        public Builder(Context context) {
            this(context, true);
        }

        public Builder(Context context, boolean showFirstEnable) {
            super(context);
            mShowFirstEnable = showFirstEnable;
            color(context.getResources().getColor(R.color.bg_gray_color));
            size((int) UIUtil.getDimension(R.dimen.normal_margin));
        }

        public Builder showFirstEnable(boolean enable) {
            mShowFirstEnable = enable;
            return this;
        }

        @Override
        public DividerItemDecoration build() {
            return new DividerItemDecoration(this).showFirstEnable(mShowFirstEnable);
        }
    }
}


