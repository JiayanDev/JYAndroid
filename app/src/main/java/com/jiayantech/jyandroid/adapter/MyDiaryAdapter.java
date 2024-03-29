package com.jiayantech.jyandroid.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.DiaryHeader;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.utils.UIUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyDiaryAdapter extends BaseSimpleModelAdapter<DiaryHeader> {
    private static final int VIEW_TYPE_EVENT = 10;

    private final int eventFirstPosition = 0;
    private final int diaryFirstPosition = 1;

    public MyDiaryAdapter(List<DiaryHeader> list) {
        super(list);
    }

    @Override
    public void addNew(List<DiaryHeader> list) {
        super.addNew(list);
        super.addNew(list);
        super.addNew(list);
        super.addNew(list);
        super.addNew(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_my_diary, this);
    }

    @Override
    protected void onBind(BaseSimpleModelAdapter.ViewHolder holder, DiaryHeader diaryHeader, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mDiaryHeader = diaryHeader;
        if (position == eventFirstPosition) {
            viewHolder.view_big_divider.setVisibility(View.VISIBLE);
            viewHolder.txt_title.setVisibility(View.VISIBLE);
            setDividerMargin(viewHolder.view_divider, false);
            viewHolder.txt_title.setText(R.string.my_diary_share);

            viewHolder.txt_operate.setText(R.string.create_diary_book);
            setTextDrawable(viewHolder.txt_operate, R.mipmap.icon_create_event_diary);
        } else if (position == diaryFirstPosition) {
            viewHolder.view_big_divider.setVisibility(View.VISIBLE);
            viewHolder.txt_title.setVisibility(View.VISIBLE);
            setDividerMargin(viewHolder.view_divider, false);
            viewHolder.txt_title.setText(R.string.my_diary_mime);

            viewHolder.txt_operate.setText(R.string.update_diary);
            setTextDrawable(viewHolder.txt_operate, R.mipmap.icon_update_diary);
        } else {
            viewHolder.view_big_divider.setVisibility(View.GONE);
            viewHolder.txt_title.setVisibility(View.GONE);
            setDividerMargin(viewHolder.view_divider, true);

            viewHolder.txt_operate.setText(R.string.update_diary);
            setTextDrawable(viewHolder.txt_operate, R.mipmap.icon_update_diary);
        }
        viewHolder.txt_content.setText(diaryHeader.getCategoryNamesString());
        //viewHolder.img_diary.setImageResource(R.mipmap.icon_create_diary);
    }

    private void setTextDrawable(TextView txt, int resId) {
        Drawable drawable = txt.getResources().getDrawable(resId);
        //drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        txt.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    private void setDividerMargin(View divider, boolean hasMargin) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) divider.getLayoutParams();
        int px = hasMargin ? (int) UIUtil.getDimension(R.dimen.normal_margin) : 0;
        lp.setMargins(px, 0, px, 0);
        divider.setLayoutParams(lp);
    }

    private static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<DiaryHeader> implements View.OnClickListener {
        private DiaryHeader mDiaryHeader;
        private View view_big_divider;
        private TextView txt_title;
        private View view_divider;
        private ImageView img_diary;
        private TextView txt_content;
        private TextView txt_operate;

        public ViewHolder(ViewGroup parent, int layoutId, MyDiaryAdapter adapter) {
            super(parent, layoutId, adapter);
            view_big_divider = itemView.findViewById(R.id.view_big_divider);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            img_diary = (ImageView) itemView.findViewById(R.id.img_diary);
            view_divider = itemView.findViewById(R.id.view_divider);
            img_diary = (ImageView) itemView.findViewById(R.id.img_diary);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            txt_operate = (TextView) itemView.findViewById(R.id.txt_operate);
            txt_operate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
