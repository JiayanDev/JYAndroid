package com.jiayantech.jyandroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.event.UmengPushCustomMessage;
import com.jiayantech.jyandroid.fragment.CommunityFragment;
import com.jiayantech.jyandroid.fragment.EventsFragment;
import com.jiayantech.jyandroid.fragment.UserInfoFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.widget.UnslidableViewPager;
import com.umeng.message.PushAgent;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/6/24.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private String[] mTitles;

    private UnslidableViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment[] mFragments;
    private RadioGroup mRadioGroup;
    private RadioButton[] mRadioButtons = new RadioButton[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackgroundResource(android.R.color.white);
        //开启友盟推送服务
        PushAgent.getInstance(this).enable();

        mTitles = getResources().getStringArray(R.array.tab_title);

        setSwipeBackEnable(false);

        initView();
        initFragments();
        initViewPager();

        setDisplayHomeAsUpEnabled(false);

        EventBus.getDefault().register(this);

    }

    public void onEvent(UmengPushCustomMessage uMessage) {
        ToastUtil.showMessage("我收到一条自定义的友盟消息");
        LogUtil.i(TAG, "EventBus onEvent : " + "我收到一条自定义的友盟消息");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    private void initView() {
        getSupportActionBar().setTitle(mTitles[0]);
        mViewPager = (UnslidableViewPager) findViewById(R.id.id_viewpager);
        //getSupportActionBar().setTitle(mTitles[0]);
        mRadioButtons[0] = (RadioButton) findViewById(R.id.radio_activity);
        ///mRadioButtons[0] = (RadioButton) findViewById(R.id.radio_beauty_with);
        mRadioButtons[1] = (RadioButton) findViewById(R.id.radio_community);
        mRadioButtons[2] = (RadioButton) findViewById(R.id.radio_userinfo);
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_tab);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        setTitle(mRadioButtons[0].getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.post_actions, menu);
        getMenuInflater().inflate(R.menu.select_publish_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_publish:
                checkGotoSelectPublish();
                return true;
            case R.id.action_diary:
                startActivity(new Intent(this, MyDiariesActivity.class));
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


    private void checkGotoSelectPublish() {
        if (AppInitManger.isRegister()) {
            final Dialog dialog = DialogUtils.showViewDialog(this, R.layout.dialog_publish_actions, false);
            dialog.findViewById(R.id.layout_share_diary).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(MyDiariesActivity.class);
                }
            });
            dialog.findViewById(R.id.layout_publish_topic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(PublishPostActivity.class);
                }
            });
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), new ActivityResult() {
                @Override
                public void onActivityResult(Intent data) {
                    checkGotoSelectPublish();
                }
            });
        }
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
                //getSupportActionBar().setTitle(mTitles[position]);
                setTitle(mRadioButtons[position].getText().toString());
                mRadioButtons[position].setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragments() {
//        BeautyWithFragment beautyWithFragment = BeautyWithFragment.newInstance(null);
//        CommunityFragment communityFragment = CommunityFragment.newInstance(null);
////        ActivityFragment activityFragment = ActivityFragment.newInstance(null);
//        EventsFragment eventFragment = new EventsFragment();
//        UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(null);
//        mFragments = new Fragment[]{beautyWithFragment, communityFragment, eventFragment, userInfoFragment};

        mFragments = new Fragment[]{
                new EventsFragment(),
                CommunityFragment.newInstance(null),
                UserInfoFragment.newInstance(null)};
    }

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener
            = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int pageItemNum = 0;
            switch (checkedId) {
//                case R.id.radio_beauty_with:
//                    pageItemNum = 0;
//                    break;
                case R.id.radio_activity:
                    pageItemNum = 0;
                    break;
                case R.id.radio_community:
                    pageItemNum = 1;
                    break;
                case R.id.radio_userinfo:
                    pageItemNum = 2;
                    break;
            }
            mViewPager.setCurrentItem(pageItemNum, false);
        }
    };

}
