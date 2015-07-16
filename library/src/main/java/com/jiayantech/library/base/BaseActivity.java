package com.jiayantech.library.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

import com.jiayantech.library.R;

/**
 * Created by liangzili on 15/6/24.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    public BaseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启友盟推送
        PushAgent.getInstance(this).onAppStart();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setSwipeBackEnable(true);
        // setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //集成友盟统计
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //集成友盟统计
        MobclickAgent.onPause(this);
    }

    protected void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void setDisplayHomeAsUpEnabled() {
        setDisplayHomeAsUpEnabled(true);
    }

    protected void setDisplayHomeAsUpEnabled(boolean flag) {
        if (flag) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.up_indicator);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void hideActionBar() {
        getSupportActionBar().hide();
        findViewById(R.id.toolbar_divider).setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onHome();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onHome() {
        onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }


    public View findViewById(int id) {
        View v = super.findViewById(id);
        return v == null && this.mHelper != null ? this.mHelper.findViewById(id) : v;
    }

    /**
     * 重写setContentView将方法传进来的layoutID，嵌套到一个带有toolbar的layout里，再调用super.SetContentView
     * 然后将该toolbar设置成activity的默认actionbar
     */
    @Override
    public void setContentView(int layoutResID) {
        ViewGroup layout_base = setContentView();
        getLayoutInflater().inflate(layoutResID, layout_base);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup layout_base = setContentView();
        layout_base.addView(view);
    }

    private ViewGroup setContentView() {
        super.setContentView(R.layout.activity_base);
        ViewGroup layout_base = (ViewGroup) findViewById(R.id.layout_base);
        Toolbar toolbar = (Toolbar) layout_base.findViewById(R.id.toolbar);

        //set toolbar style
        //toolbar.setBackgroundColor(Color.WHITE);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.theme_color));
        setSupportActionBar(toolbar);

        setDisplayHomeAsUpEnabled(true);

        return layout_base;
    }

    protected void setBackgroundResource(int resid) {
        ViewGroup layout_base = (ViewGroup) findViewById(R.id.layout_base);
        layout_base.setBackgroundResource(resid);
    }

    protected void superSetContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean b) {
        mHelper.getSwipeBackLayout().setEnableGesture(b);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        this.getSwipeBackLayout().scrollToFinishActivity();
    }


    /////////////////////////
    ProgressDialog mProgressDialog;

    protected void showProgressDialog() {
        dismissProgressDialog();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    ///
    protected void finishToStartActivity(Class<?> cls) {
        finishToStartActivity(new Intent(this, cls));
    }

    protected void finishToStartActivity(Intent intent) {
        startActivity(intent);
        super.finish();
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    /**
     * 修改 ActionBar 上的菜单字体颜色
     */
    private static void setMenuItemTextColorToWhite(final Activity activity) {
        activity.getLayoutInflater().setFactory(new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context,
                                     AttributeSet attrs) {
                if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                        || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                    try {
                        LayoutInflater f = activity.getLayoutInflater();
                        final View view = f.createView(name, null, attrs);
                        System.out.println((view instanceof TextView));
                        if (view instanceof TextView) {
                            ((TextView) view).setTextColor(Color.WHITE/*这里修改颜色*/);
                        }
                        return view;
                    } catch (InflateException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

        });
    }
}
