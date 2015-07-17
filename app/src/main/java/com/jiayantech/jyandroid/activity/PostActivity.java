package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.PostListFragment;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.jyandroid.widget.PagerSlidingTabStrip;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.widget.UnslidableViewPager;

/**
 * Created by liangzili on 15/7/8.
 */
public class PostActivity extends BaseActivity {
    private String[] mTabName;
    private UnslidableViewPager mViewPager;
    private PagerSlidingTabStrip mSlidingTabStrip;
    private BaseFragment[] mFragments;
    private Login.Category category;

    //private String mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        category = getIntent().getParcelableExtra(PostListFragment.EXTRA_CATEGORY);
        //mType = getIntent().getStringExtra(PostListFragment.EXTRA_TYPE);
        init();
    }

    public void init() {
        setTitle(category.name);
        mTabName = getResources().getStringArray(R.array.tab_post);
        mViewPager = (UnslidableViewPager) findViewById(R.id.tab_content);
        mSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_title);
        mFragments = new BaseFragment[]{
                PostListFragment.newInstance("topic", category.id, false),
                PostListFragment.newInstance("diary", category.id, false)
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
