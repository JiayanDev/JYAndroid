package com.jiayantech.library.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //集成友盟统计
        MobclickAgent.onResume(this);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void hideActionBar() {
        getSupportActionBar().hide();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        View activityView = getLayoutInflater().inflate(R.layout.activity_base, null);
        ((ViewGroup) activityView).addView(view);
        Toolbar toolbar = (Toolbar) activityView.findViewById(R.id.toolbar);

        //set toolbar style
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.theme_color));

        setSupportActionBar(toolbar);
        super.setContentView(activityView);
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
}
