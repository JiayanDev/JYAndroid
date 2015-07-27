package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.PostListFragment;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.widget.PagerSlidingTabStrip;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.widget.UnslidableViewPager;

/**
 * Created by liangzili on 15/7/8.
 */
public class PostActivity extends BaseActivity {
    private UnslidableViewPager mViewPager;
    private BaseFragment[] mFragments;
    private AppInit.Category category;
    private RadioGroup mRadioGroup;
    private RadioButton mTopicRadioBtn;
    private RadioButton mDiaryRadioBtn;

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
        mViewPager = (UnslidableViewPager) findViewById(R.id.tab_content);
        mRadioGroup = (RadioGroup)findViewById(R.id.radiogroup_tab);
        mTopicRadioBtn = (RadioButton)findViewById(R.id.tab_topic);
        mDiaryRadioBtn = (RadioButton)findViewById(R.id.tab_diary);

        mFragments = new BaseFragment[]{
                PostListFragment.newInstance("topic", category.id, false),
                PostListFragment.newInstance("diary", category.id, false)
        };

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == mTopicRadioBtn.getId()){
                    mViewPager.setCurrentItem(0);
                }else{
                    mViewPager.setCurrentItem(1);
                }
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return mTabName[position];
//            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mTopicRadioBtn.setChecked(true);
                }else{
                    mDiaryRadioBtn.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mSlidingTabStrip.setViewPager(mViewPager);
//        mSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }
}
