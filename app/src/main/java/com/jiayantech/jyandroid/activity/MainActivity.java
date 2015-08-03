package com.jiayantech.jyandroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.jyandroid.event.UmengPushCustomMessage;
import com.jiayantech.jyandroid.fragment.CommunityFragment;
import com.jiayantech.jyandroid.fragment.HomeEventFragment;
import com.jiayantech.jyandroid.fragment.MineFragment;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.widget.UnslidableViewPager;
import com.umeng.message.PushAgent;

/**
 * Created by liangzili on 15/6/24.
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
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

        //如果是从通知栏推送消息点击启动app，根据推送消息的参数跳转到对应Activity
        if(getIntent().getBundleExtra(SplashActivity.EXTRA_BUNDLE) != null){
            launchActivityFromNotification(getIntent().getBundleExtra(SplashActivity.EXTRA_BUNDLE));
        }
        //EventBus.getDefault().register(this);
    }

    private void launchActivityFromNotification(Bundle bundleExtra) {
        String type = bundleExtra.getString(WebViewFragment.EXTRA_TYPE);
        long id = bundleExtra.getLong(WebViewFragment.EXTRA_ID, -1);
        long userId = bundleExtra.getLong(WebViewFragment.EXTRA_USER_ID);
        String userName = bundleExtra.getString(WebViewFragment.EXTRA_USERNAME);
        switch (type){
            case WebViewFragment.TYPE_DIARY:
                Intent intent = new Intent(this, PostDetailActivity.class);
                intent.putExtra(WebViewFragment.EXTRA_ID, id);
                intent.putExtra(WebViewFragment.EXTRA_USER_ID, userId);
                intent.putExtra(WebViewFragment.EXTRA_USERNAME, userName);
                intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
                startActivity(intent);
                break;
            default:
                return;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    public void onEvent(UmengPushCustomMessage uMessage) {
        ToastUtil.showMessage("我收到一条自定义的友盟消息");
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
        mRadioGroup.setOnCheckedChangeListener(this);

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
        LoginActivity.checkLoginToRunnable(this, new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = DialogUtils.showViewDialog(_this, R.layout.dialog_publish_actions, false);
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
            }
        });
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
        mFragments = new Fragment[]{
                new HomeEventFragment(),
                CommunityFragment.newInstance(null),
                MineFragment.newInstance(null)};
    }

    @Override
    public void onCheckedChanged(RadioGroup group, final int checkedId) {
        switch (checkedId) {
//                case R.id.radio_beauty_with:
//                    pageItemNum = 0;
//                    break;
            case R.id.radio_activity:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.radio_community:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.radio_userinfo:
                toUserInfo = true;
                LoginActivity.checkLoginToRunnable(_this, new Runnable() {
                    @Override
                    public void run() {
                        toUserInfo = false;
                        mViewPager.setCurrentItem(2, false);
                        ((MineFragment) mFragments[2]).resume();
                    }
                });
                break;
        }
    }

    private int[] ids = new int[]{R.id.radio_activity, R.id.radio_community, R.id.radio_userinfo};
    private boolean toUserInfo = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (toUserInfo && mViewPager.getCurrentItem() != 2) {
            toUserInfo = false;
            ((RadioButton) mRadioGroup.findViewById(ids[mViewPager.getCurrentItem()])).setChecked(true);
            //mRadioGroup.check(ids[mViewPager.getCurrentItem()]);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i(TAG, "MainActivity onNewIntent");
        ToastUtil.showMessage("MainActivity onNewIntent");
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
