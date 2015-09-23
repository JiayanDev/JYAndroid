package com.jiayantech.jyandroid.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.CommunityFragment;
import com.jiayantech.jyandroid.fragment.HomePagePostFragment;
import com.jiayantech.jyandroid.fragment.MineFragment;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.jyandroid.model.Data;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.widget.UnslidableViewPager;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.umeng_push.UnreadMessageEvent;

import de.greenrobot.event.EventBus;

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

    private ImageView mImageUnreadDot;
    private TextView mTextUnreadCount;

    private long lastBackPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        setBackgroundResource(android.R.color.white);
        //开启友盟推送服务
        //PushAgent.getInstance(this).enable();
        UmengPushManager.getInstance().enablePush(true);

        mTitles = getResources().getStringArray(R.array.tab_title);

        setSwipeBackEnable(false);

        initView();
        initFragments();
        initViewPager();

        setDisplayHomeAsUpEnabled(false);
        mImageUnreadDot = (ImageView) findViewById(R.id.img_unread_dot);
        mTextUnreadCount = (TextView) findViewById(R.id.txt_unread_count);

        int unreadNotificationCount = UmengPushManager.getInstance().getUnreadNotificationCount();
        boolean unreadCompany = UmengPushManager.getInstance().getUnreadCompanyCount();
        boolean unreadAngel = UmengPushManager.getInstance().getUnreadAngelCount();
        UnreadMessageEvent event = new UnreadMessageEvent(unreadNotificationCount,
                unreadCompany, unreadAngel);
        onEvent(event);

        //如果是从通知栏推送消息点击启动app，根据推送消息的参数跳转到对应Activity
        if (getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE) != null) {
            long id = getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
            String type = getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE);
            launchActivityFromNotification(id, type);
        }
        EventBus.getDefault().register(this);

        checkVersion();
    }

    private final String DATA_URL = "http://admintest.jiayantech.com/data.html";

    private void checkVersion() {
        HttpReq._request(Request.Method.GET, DATA_URL, null, null, false, false, null, new ResponseListener<AppResponse<Data>>() {
            @Override
            public void onResponse(AppResponse<Data> dataAppResponse) {
                final Data data = dataAppResponse.data;
                if (BaseApplication.getVersionValue(data.version) > BaseApplication.getVersionValue(BaseApplication.getContext().getVersionName())) {
                    //Context context = BaseApplication.getContext();
                    Context context = _this;
                    AlertDialog dialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).
                            setTitle("版本更新: v" + dataAppResponse.data.version).
                            setNegativeButton("取消", null).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.url));
                                    startActivity(intent);
                                }
                            }).
                            create();
                    //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//指定会全局,可以在后台弹出
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void launchActivityFromNotification(long id, String type) {
        Intent intent = null;
        switch (type) {
            case WebConstans.Type.TYPE_DIARY:
            case WebConstans.Type.TYPE_TOPIC:
            case WebConstans.Type.TYPE_EVENT:
            case WebConstans.Type.TYPE_PERSONAL_PAGE:
                intent = WebViewActivity.createLaunchIntent(this, id, type);
                break;
            case "my_angel":
                intent = new Intent(this, MyEventsActivity.class);
                break;
            case "my_company":
                intent = new Intent(this, CompanyEventActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void onEvent(UnreadMessageEvent event) {
        LogUtil.i(TAG, "handling UnreadMessageEvent");
        if (event.unreadNotificaition > 0) {
            mTextUnreadCount.setVisibility(View.VISIBLE);
            mImageUnreadDot.setVisibility(View.INVISIBLE);
            mTextUnreadCount.setText(String.valueOf(event.unreadNotificaition));
        } else {
            if (event.unreadCompany || event.unreadAngel) {
                mTextUnreadCount.setVisibility(View.INVISIBLE);
                mImageUnreadDot.setVisibility(View.VISIBLE);
            } else {
                mTextUnreadCount.setVisibility(View.INVISIBLE);
                mImageUnreadDot.setVisibility(View.INVISIBLE);
            }
        }
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
        //getMenuInflater().inflate(R.menu.select_publish_action, menu);
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
                new HomePagePostFragment(),
                CommunityFragment.newInstance(null),
                MineFragment.newInstance(null)};
    }

    @Override
    public void onCheckedChanged(RadioGroup group, final int checkedId) {
        switch (checkedId) {
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
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i(TAG, "MainActivity onNewIntent");
        ToastUtil.showMessage("MainActivity onNewIntent");
    }

    public void resetCheck() {
        ((RadioButton) mRadioGroup.findViewById(ids[0])).setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastBackPressTime > 2000) {
                ToastUtil.showMessage("再按一次返回键退出");
                lastBackPressTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
