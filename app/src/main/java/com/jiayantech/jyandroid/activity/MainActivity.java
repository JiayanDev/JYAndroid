package com.jiayantech.jyandroid.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.jyandroid.fragment.ActivityFragment;
import com.jiayantech.jyandroid.fragment.BeautyWithFragment;
import com.jiayantech.jyandroid.fragment.CommunityFragment;
import com.jiayantech.jyandroid.fragment.UserInfoFragment;
import com.umeng.message.PushAgent;

/**
 * Created by liangzili on 15/6/24.
 */
public class MainActivity extends BaseActivity {

    private String[] mTitles;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment[] mFragments;
    private RadioGroup mRadioGroup;
    private RadioButton mBeautyWithBtn, mCommunityBtn, mActivityBtn, mUserInfoBtn;
    private RadioButton[] mRadioButtons = new RadioButton[]{
            mBeautyWithBtn, mCommunityBtn, mActivityBtn, mUserInfoBtn
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启友盟推送服务
        PushAgent.getInstance(this).enable();

        mTitles = getResources().getStringArray(R.array.tab_title);

        setSwipeBackEnable(false);

        initView();
        initFragments();
        initViewPager();

    }

    private void initViewPager() {
        mFragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments[i];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        };

        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.length - 1);
        mViewPager.setCurrentItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(mTitles[position]);
                mRadioButtons[position].setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragments() {
        BeautyWithFragment beautyWithFragment = BeautyWithFragment.newInstance(null);
        CommunityFragment communityFragment = CommunityFragment.newInstance(null);
//        ActivityFragment activityFragment = ActivityFragment.newInstance(null);
        ActivityFragment activityFragment = new ActivityFragment();
        UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(null);

        mFragments = new Fragment[]{
                beautyWithFragment, communityFragment, activityFragment, userInfoFragment
        };
    }

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener
            = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radio_beauty_with:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.radio_community:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.radio_activity:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.radio_userinfo:
                    mViewPager.setCurrentItem(3);
                    break;
            }
        }
    };

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mRadioButtons[0] = (RadioButton) findViewById(R.id.radio_beauty_with);
        mRadioButtons[1] = (RadioButton) findViewById(R.id.radio_community);
        mRadioButtons[2] = (RadioButton) findViewById(R.id.radio_activity);
        mRadioButtons[3] = (RadioButton) findViewById(R.id.radio_userinfo);
        mRadioButtons[0].setChecked(true);
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_tab);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }


}
