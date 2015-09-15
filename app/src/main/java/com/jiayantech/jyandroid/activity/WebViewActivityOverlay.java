package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.factory.WebViewFragmentFactory;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.library.base.BaseActivity;

/**
 * Toolbar可以overlay的WebViewActivity
 * Created by liangzili on 15/9/14.
 */
public class WebViewActivityOverlay extends BaseActivity{
    Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        superSetContentView(R.layout.activity_webview_overlay);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        FragmentManager fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(com.jiayantech.library.R.id.fragment_container);
        if (mFragment == null) {
            mFragment = createFragment();
            fm.beginTransaction()
                    .add(com.jiayantech.library.R.id.fragment_container, mFragment)
                    .commit();
        }
    }

    public static Intent createLaunchIntent(Context context, long id, String type) {
        Intent intent = new Intent(context, WebViewActivityOverlay.class);
        intent.putExtra(WebViewFragment.EXTRA_ID, id);
        intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
        return intent;
    }

    public static void launchActivity(Context context, long id, String type) {
        context.startActivity(createLaunchIntent(context, id, type));
    }


    protected Fragment createFragment() {
        long id = getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
        String type = getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE);
        mFragment = WebViewFragmentFactory.createFragment(type, id);
        return mFragment;
    }
}
