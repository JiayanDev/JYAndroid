package com.jiayantech.jyandroid.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.jiayantech.jyandroid.widget.DotMarkRadioButton;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.utils.BitmapUtil;
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

//        mRadioButtons[2].setCompoundDrawables(null, displayUnreadDot(this, R.mipmap.icon_me, 50), null, null);
        //((DotMarkRadioButton)mRadioButtons[2]).setContent(3);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i(TAG, "MainActivity onNewIntent");
    }

    //    public static Drawable displayUnreadDot(Context context, int icon, int iconSize){
//        Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), icon);
//        Bitmap dotBitmap = BitmapFactory.decodeResource(context.getResources(),
//                com.jiayantech.library.R.drawable.shape_dot);
//        Canvas canvas = new Canvas(iconBitmap);
//
//        Paint iconPaint = new Paint();
//        iconPaint.setDither(true);
//        iconPaint.setFilterBitmap(true);
//        iconPaint.setAntiAlias(true);
//        Rect src = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
//        Rect dst = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
//        canvas.drawBitmap(iconBitmap, src, dst, iconPaint);
//        iconPaint.setColor(Color.RED);
//        canvas.drawCircle(iconSize - 13, 20, 10, iconPaint);
//
//        return new BitmapDrawable(context.getResources(), iconBitmap);
//    }

}
