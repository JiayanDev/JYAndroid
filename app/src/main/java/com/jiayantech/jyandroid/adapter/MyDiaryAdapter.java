package com.jiayantech.jyandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
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
    public MyDiaryAdapter(List<DiaryHeader> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_my_diary);
    }


    private static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<DiaryHeader> implements View.OnClickListener {
        private DiaryHeader mDiaryHeader;
        private ImageView img_diary;
        private TextView txt_title;
        private Button btn_operate;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            img_diary = (ImageView) itemView.findViewById(R.id.img_diary);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            btn_operate = (Button) itemView.findViewById(R.id.btn_operate);
            btn_operate.setOnClickListener(this);
        }

        @Override
        public void onBind(DiaryHeader diaryHeader, int position) {
            mDiaryHeader = diaryHeader;
            img_diary.setImageResource(R.drawable.ic_account_box_black_48dp);
            txt_title.setText(diaryHeader.projectName + diaryHeader.categoryIds);
            btn_operate.setText("update diary");
        }

        @Override
        public void onClick(View v) {
        }
    }
}
