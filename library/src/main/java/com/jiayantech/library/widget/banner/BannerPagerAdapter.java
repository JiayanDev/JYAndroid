package com.jiayantech.library.widget.banner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/6/29.
 */
class BannerPagerAdapter extends FragmentPagerAdapter{
    List<String> mImageUrlList;

    public BannerPagerAdapter(FragmentManager fm) {
        super(fm);
        mImageUrlList = new ArrayList<>();
    }

    public void setImageUrlList(List<String> list){
        mImageUrlList = list;
    }

    public List<String> getImageUrlList(){
        return mImageUrlList;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mImageUrlList.size();
    }
}
