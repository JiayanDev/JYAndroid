package com.jiayantech.jyandroid.widget;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.utils.UIUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @Description 可动态添加控件的LinearLayout
 * @author 健兴
 * @version 1.0
 * @date 2014-4-29
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class ItemsLayout extends LinearLayout {

	private int leftTextSize;
	private boolean isGray;

	public ItemsLayout(Context context) {
		super(context);
	}

	public ItemsLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setLeftTextSize(int size) {
		leftTextSize = size;
	}

	public void setLeftTextGray(boolean isGray) {
		this.isGray = isGray;
	}

	/** 适配器 */
	private BaseAdapter mAdapter;

	/**
	 * 描述：获取适配器
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:21:57
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:21:57
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public BaseAdapter getAdapter() {
		return mAdapter;
	}

	/**
	 * 描述：设置适配器
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:22:16
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:22:16
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		removeAllViews();
		if (adapter == null) {
			return;
		}
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			addItemView(adapter.getView(i, null, this));
		}
	}

	private int mDriverResId = -1;
	private int mDriverHeight = 0;
	private int mDriverLeftMargin = UIUtil.dip2px(15);

	public void setDriver() {
		setDriver(R.color.divider_gray_color, 1);
	}

	public void setDriver(int resId, int height) {
		mDriverResId = resId;
		mDriverHeight = height;
		// Drawable drawable= getResources().getDrawable(resId);
		// setDividerDrawable(drawable);
	}

	public void setDriverLeftMargin(int driverLeftMargin) {
		mDriverLeftMargin = driverLeftMargin;
	}

	public void addDriver() {
		if (mDriverResId > 0 && mDriverHeight > 0) {
			View view = new View(getContext());
			view.setBackgroundResource(mDriverResId);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, mDriverHeight);
			lp.leftMargin = mDriverLeftMargin;
			addView(view, lp);
		}
	}

	public void addItemView(View view) {
		if (getChildCount() > 0) {
			addDriver();
		}
		addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	public ItemView addArrowItemView(String leftText, String rightText) {
		return addItemView(leftText, rightText, true);
	}

	public ItemView addItemView(String leftText, String rightText) {
		return addItemView(leftText, rightText, false);
	}

	private ItemView addItemView(String leftText, String rightText, boolean arrow) {
		ItemView itemView = new ItemView(getContext());
		if (leftTextSize > 0) {
			itemView.setLeftTextSize(leftTextSize);
		}
		itemView.setLeftText(leftText);
		if (isGray) {
			itemView.setLeftTextColorGray();
		}
		if (arrow) {
			itemView.setRightArrowText(rightText);
		} else {
			itemView.setRightText(rightText);
		}
		addItemView(itemView);
		return itemView;
	}

	public ItemView addImageItemView(String leftText, int resId) {
		ItemView itemView = new ItemView(getContext());
		itemView.setLeftText(leftText);
		itemView.setRightImage(resId);
		addItemView(itemView);
		return itemView;
	}

	public TextView addMenuItem(String text) {
		int pad = UIUtil.dip2px(5);
		TextView textView = new TextView(getContext());
		textView.setPadding(pad, pad, pad, pad);
		textView.setText(text);
		textView.setTextSize(17);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setTextColor(getResources().getColor(R.color.font_normal));
		textView.setBackgroundResource(R.drawable.bg_btn_title_selector);
		addItemView(textView);
		LayoutParams params = (LayoutParams) textView.getLayoutParams();
		params.setMargins(pad, pad, pad, pad);
		textView.setLayoutParams(params);
		return textView;
	}
}
