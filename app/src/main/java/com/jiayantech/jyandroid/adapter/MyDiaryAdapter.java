package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.DiaryHeader;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
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
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_my_diary, this);
    }

    @Override
    protected void onBind(BaseSimpleModelAdapter.ViewHolder holder, DiaryHeader diaryHeader, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mDiaryHeader = diaryHeader;
        if (position == eventFirstPosition) {
            viewHolder.txt_title.setVisibility(View.VISIBLE);
            viewHolder.view_divider.setVisibility(View.VISIBLE);
            viewHolder.txt_title.setText(R.string.my_diary_share);

            viewHolder.txt_operate.setText(R.string.create_diary);
            setTextDrawble(viewHolder.txt_operate, R.mipmap.icon_create_diary);
        } else if (position == diaryFirstPosition) {
            viewHolder.txt_title.setVisibility(View.VISIBLE);
            viewHolder.view_divider.setVisibility(View.VISIBLE);
            viewHolder.txt_title.setText(R.string.my_diary_mime);

            viewHolder.txt_operate.setText(R.string.update_diary);
            setTextDrawble(viewHolder.txt_operate, R.mipmap.icon_update_diary);
        } else {
            viewHolder.txt_title.setVisibility(View.GONE);
            viewHolder.view_divider.setVisibility(View.GONE);

            viewHolder.txt_operate.setText(R.string.update_diary);
            setTextDrawble(viewHolder.txt_operate, R.mipmap.icon_update_diary);
        }
        viewHolder.txt_content.setText(diaryHeader.projectName + diaryHeader.categoryIds);
        //viewHolder.img_diary.setImageResource(R.mipmap.icon_create_diary);
    }

    public static void setTextDrawble(TextView txt, int resId) {
        Drawable drawable = txt.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        txt.setCompoundDrawables(null, null, drawable, null);
    }

    private static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<DiaryHeader> implements View.OnClickListener {
        private DiaryHeader mDiaryHeader;
        private TextView txt_title;
        private View view_divider;
        private ImageView img_diary;
        private TextView txt_content;
        private TextView txt_operate;

        public ViewHolder(ViewGroup parent, int layoutId, MyDiaryAdapter adapter) {
            super(parent, layoutId, adapter);
            img_diary = (ImageView) itemView.findViewById(R.id.img_diary);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
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
