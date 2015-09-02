package com.jiayantech.jyandroid.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.utils.UIUtil;

/**
 * 
 * @Description 条目Item
 * @author 健兴
 * @version 1.0
 * @date 2014-4-30
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class ItemView extends LinearLayout {

	public static final String BLANK = "未填写";
	public static final String NOT_SET = "未设置";
	public static final String NOT_ADD = "未添加";

	private TextView txt_left;
	private TextView txt_right;
	private ImageView img_right;

	private int textSize = 14;
	private int leftTextSize = textSize;

	public ItemView(Context context) {
		super(context);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setGravity(Gravity.CENTER_VERTICAL);
	}

	public void setLeftTextSize(int size) {
		leftTextSize = size;
	}

	public void setLeftText(String text) {
		if (txt_left == null) {
			txt_left = new TextView(getContext());
			txt_left.setTextSize(leftTextSize);
			txt_left.setLines(1);
			txt_left.setEllipsize(TextUtils.TruncateAt.END);
			txt_left.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			txt_left.setTextColor(getResources().getColor(R.color.font_normal));
			int pad = UIUtil.dip2px(15);
			txt_left.setPadding(pad, pad, pad, pad);
			LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
			lp.weight = 1;
			addView(txt_left, lp);
		}
		txt_left.setText(text);
	}

	public void setLeftEdit(String text) {
		if (txt_left == null) {
			txt_left = new EditText(getContext());
			txt_left.setTextSize(leftTextSize);
			txt_left.setLines(1);
			txt_left.setEllipsize(TextUtils.TruncateAt.END);
			txt_left.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			txt_left.setTextColor(getResources().getColor(R.color.font_normal));
			int pad = UIUtil.dip2px(15);
			txt_left.setPadding(pad, pad, pad, pad);
			LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
			lp.weight = 1;
			addView(txt_left, lp);
		}
		txt_left.setText(text);
	}

	public TextView getRightText() {
		return txt_right;
	}

	public TextView getLeftText() {
		return txt_left;
	}

	public void setRightText(String text) {
		setRightText(text, -1);
	}

	public void setRightArrowText(String text) {
		setRightText(text, R.drawable.img_right_arrow_selector);
	}

	public void setRightText(String text, int rightIconId) {
		if (txt_right == null) {
			txt_right = new TextView(getContext());
			txt_right.setTextSize(textSize);
			txt_right.setLines(1);
			txt_right.setEllipsize(TextUtils.TruncateAt.END);
			txt_right.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			txt_right.setTextColor(getResources().getColor(R.color.font_normal));
			int pad = UIUtil.dip2px(15);
			txt_right.setPadding(pad, pad, pad, pad);
			LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
			lp.weight = 1.8f;
			addView(txt_right, lp);
		}
		txt_right.setText(text);
		if (rightIconId > 0) {
			int pad = UIUtil.dip2px(10);
			txt_right.setCompoundDrawablePadding(pad);
			Drawable drawable = getResources().getDrawable(rightIconId);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			txt_right.setCompoundDrawables(null, null, drawable, null);
		}
	}

	public ItemView setRightTextColorGray() {
		if (txt_right != null) {
			txt_right.setTextColor(getResources().getColor(R.color.font_gray));
		}
		return this;
	}

	public ItemView setLeftTextColorGray() {
		if (txt_left != null) {
			txt_left.setTextColor(getResources().getColor(R.color.font_gray));
		}
		return this;
	}

	// //////////////
	public ImageView setRightImage(int resId) {
		if (img_right == null) {
			img_right = new ImageView(getContext());
			img_right.setScaleType(ScaleType.CENTER_CROP);
			int size = UIUtil.dip2px(60);
			LayoutParams lp = new LayoutParams(size, size);
			int margin = UIUtil.dip2px(10);
			lp.setMargins(margin, margin, margin, margin);
			addView(img_right, lp);
		}
		img_right.setImageResource(resId);
		return img_right;
	}
}
