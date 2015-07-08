package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

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
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        setViewsContent();
    }

    protected void setViewsContent() {
        setDisplayHomeAsUpEnabled();
        ArrayList<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=9d5ecce64434970a4726436ff3f7e5fa/64380cd7912397dd94c99ccd5c82b2b7d1a28742.jpg"));
        photoList.add(new Photo("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=d96fe1a5153853438c9ad461f52e844c/86d6277f9e2f0708c47d38c9ec24b899a801f2fb.jpg"));
        photoList.add(new Photo("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=da906c02feedab6474271e80910b9bf1/9d82d158ccbf6c81fe953e21b93eb13532fa405c.jpg"));
        mViewPager.setAdapter(new ImagePagerAdapter<>(this, photoList));
    }
}