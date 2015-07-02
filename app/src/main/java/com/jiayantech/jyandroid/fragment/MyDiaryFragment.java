package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.graphics.Color;
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
public class MyDiaryFragment extends RefreshListFragment<DiaryHeader, AppResponse<List<DiaryHeader>>> {
    private ImageView img_diary;
    private TextView txt_title;
    private Button btn_operate;

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new MyDiaryAdapter(null), DiaryBiz.ACTION_MY_HEADER);
        View headerView = setHeader(R.layout.item_my_diary);
        img_diary = (ImageView) headerView.findViewById(R.id.img_diary);
        txt_title = (TextView) headerView.findViewById(R.id.txt_title);
        btn_operate = (Button) headerView.findViewById(R.id.btn_operate);

        img_diary.setImageResource(R.drawable.ic_account_box_white_48dp);
        txt_title.setText("add diary\\nLet start");
        btn_operate.setText("create diary");
        btn_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectProjectActivity.class);
            }
        });
    }
}
