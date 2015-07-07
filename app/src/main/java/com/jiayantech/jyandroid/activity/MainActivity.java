package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.EventsFragment;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.jyandroid.fragment.BeautyWithFragment;
import com.jiayantech.jyandroid.fragment.CommunityFragment;
import com.jiayantech.jyandroid.fragment.UserInfoFragment;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * Created by liangzili on 15/6/24.
 */
public class MainActivity extends BaseActivity {

    private String[] mTitles;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment[] mFragments;
    private RadioGroup mRadioGroup;
    private RadioButton[] mRadioButtons = new RadioButton[4];

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

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mRadioButtons[0] = (RadioButton) findViewById(R.id.radio_beauty_with);
        mRadioButtons[1] = (RadioButton) findViewById(R.id.radio_community);
        mRadioButtons[2] = (RadioButton) findViewById(R.id.radio_activity);
        mRadioButtons[3] = (RadioButton) findViewById(R.id.radio_userinfo);
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_tab);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_diary:
                startActivity(new Intent(this, MyDiaryActivity.class));
                return true;
            case R.id.action_topic:
                startActivity(new Intent(this, PublishPostActivity.class));
                return true;
            case R.id.action_share:
                startActivity(new Intent(this, ShareActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        EventsFragment eventFragment = new EventsFragment();
        UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(null);

        mFragments = new Fragment[]{
                beautyWithFragment, communityFragment, eventFragment, userInfoFragment
        };
    }

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener
            = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int pageItemNum = 0;
            switch (checkedId) {
                case R.id.radio_beauty_with:
                    pageItemNum = 0;
                    break;
                case R.id.radio_community:
                    pageItemNum = 1;
                    break;
                case R.id.radio_activity:
                    pageItemNum = 2;
                    break;
                case R.id.radio_userinfo:
                    pageItemNum = 3;
                    break;
            }
            mViewPager.setCurrentItem(pageItemNum, false);
        }
    };

    private void initUmengPush() {
        PushAgent.getInstance(this).enable();
        String device_token = UmengRegistrar.getRegistrationId(this);

    }
}
