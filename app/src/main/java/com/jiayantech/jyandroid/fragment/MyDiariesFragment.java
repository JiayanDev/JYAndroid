package com.jiayantech.jyandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.SelectProjectActivity;
import com.jiayantech.jyandroid.adapter.MyDiaryAdapter;
import com.jiayantech.jyandroid.biz.DiaryBiz;
import com.jiayantech.jyandroid.model.DiaryHeader;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyDiariesFragment extends RefreshListFragment<DiaryHeader, AppResponse<List<DiaryHeader>>> {
    private ImageView img_diary;
    private TextView txt_title;
    private TextView btn_operate;

    @Override
    public void onInitView() {
        super.onInitView();
//        ultimateRecyclerView.mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                linearLayoutManager.getOrientation()));
        setParams(new MyDiaryAdapter(null), DiaryBiz.ACTION_MY_HEADER);
//        View headerView = setHeader(R.layout.item_my_diary);
//        img_diary = (ImageView) headerView.findViewById(R.id.img_diary);
//        txt_title = (TextView) headerView.findViewById(R.id.txt_title);
//        btn_operate = (TextView) headerView.findViewById(R.id.txt_operate);
//
//        //img_diary.setBackgroundResource(R.color.light_gray);
//        img_diary.setImageResource(R.drawable.ic_account_box_black_48dp);
//        txt_title.setText("add diary\nLet start");
//        btn_operate.setText("create diary");
//        btn_operate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SelectProjectActivity.class);
//            }
//        });
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        /*
        * RecyclerView的布局方向，默认先赋值
        * 为纵向布局
        * RecyclerView 布局可横向，也可纵向
        * 横向和纵向对应的分割想画法不一样
        * */
        private int mOrientation = LinearLayoutManager.VERTICAL;

        /**
         * item之间分割线的size，默认为1
         */
        private int mItemSize = 1;

        /**
         * 绘制item分割线的画笔，和设置其属性
         * 来绘制个性分割线
         */
        private Paint mPaint;

        /**
         * 构造方法传入布局方向，不可不传
         *
         * @param context
         * @param orientation
         */
        public DividerItemDecoration(Context context, int orientation) {
            this.mOrientation = orientation;
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请传入正确的参数");
            }
            mItemSize = (int) TypedValue.applyDimension(mItemSize, TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDisplayMetrics());
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.BLUE);
            /*设置填充*/
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        /**
         * 绘制纵向 item 分割线
         *
         * @param canvas
         * @param parent
         */
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            //linearLayoutManager.findFirstVisibleItemPosition();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mItemSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }

        /**
         * 绘制横向 item 分割线
         *
         * @param canvas
         * @param parent
         */
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mItemSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }

        /**
         * 设置item分割线的size
         *
         * @param outRect
         * @param view
         * @param parent
         * @param state
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, mItemSize);
            } else {
                outRect.set(0, 0, mItemSize, 0);
            }
        }
    }
}
