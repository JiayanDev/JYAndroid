package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ImagePagerAdapter;
import com.jiayantech.jyandroid.model.Photo;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.widget.HackyViewPager;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/8.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PhotosActivity extends BaseActivity {
    private static final String KEY_TITLE = "title";
    private static final String KEY_PHOTO_LIST = "photoList";
    private static final String KEY_PHOTO_POSITION = "photoPosition";
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        setViewsContent();
    }

    protected void setViewsContent() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY_TITLE);
        ArrayList<String> photoList = intent.getStringArrayListExtra(KEY_PHOTO_LIST);
        int position = intent.getIntExtra(KEY_PHOTO_POSITION, 0);
        setTitle(title == null ? getString(R.string.title_photo_browse) : title);
        mViewPager.setAdapter(new ImagePagerAdapter<>(this, photoList));
        mViewPager.setCurrentItem(position);
    }

    public static void start(Context context, String title, ArrayList<String> list, int position) {
        Intent intent = new Intent(context, PhotosActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_PHOTO_LIST, list);
        intent.putExtra(KEY_PHOTO_POSITION, position);
        context.startActivity(intent);
    }
}