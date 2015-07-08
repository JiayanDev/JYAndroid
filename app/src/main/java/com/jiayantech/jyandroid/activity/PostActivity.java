package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.PostListFragment;
import com.jiayantech.jyandroid.widget.PagerSlidingTabStrip;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;

/**
 * Created by liangzili on 15/7/8.
 */
public class PostActivity extends BaseActivity{
    private String[] mTabName;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip mSlidingTabStrip;
    private BaseFragment[] mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        init();
    }

    public void init(){
        mTabName = getResources().getStringArray(R.array.tab_post);
        mViewPager = (ViewPager)findViewById(R.id.tab_content);
        mSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tab_title);
        mFragments = new BaseFragment[]{
                PostListFragment.newInstance(null, false), PostListFragment.newInstance(null, false)
        };

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }


            @Override
            public CharSequence getPageTitle(int position) {
                return mTabName[position];
            }
        });
        mSlidingTabStrip.setViewPager(mViewPager);
        mSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
