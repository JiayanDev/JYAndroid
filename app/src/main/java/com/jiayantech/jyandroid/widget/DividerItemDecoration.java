package com.jiayantech.jyandroid.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liangzili on 15/7/14.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration{
        private Drawable divider;

        public DividerItemDecoration(Drawable divider) {
            this.divider = divider.mutate();
        }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(divider == null || parent.getChildLayoutPosition(view) < 2){
            return;
        }
        outRect.top = divider.getIntrinsicHeight();
    }



    @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for(int i=1; i<childCount; i++){
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams)child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }

        }

        private int getOrientation(RecyclerView parent) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
                return layoutManager.getOrientation();
            } else {
                throw new IllegalStateException("DividerItemDecoration can only be used with a " +
                        "LinearLayoutManager");
            }
        }
}
