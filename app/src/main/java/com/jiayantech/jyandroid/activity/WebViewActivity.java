package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.jiayantech.jyandroid.factory.WebViewFragmentFactory;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebViewActivity extends SingleFragmentActivity{

    WebViewFragment mFragment;

    @Override
    protected Fragment createFragment() {
        long id = getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
        long userId = getIntent().getLongExtra(WebViewFragment.EXTRA_USER_ID, -1);
        String userName = getIntent().getStringExtra(WebViewFragment.EXTRA_USERNAME);
        String type = getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE);

        mFragment = WebViewFragmentFactory.createFragment(type, id, userId, userName);
        return mFragment;
    }

    public static void launchActivity(Context context, long id, long userId,
                                      String userName, String type){
        context.startActivity(createLaunchIntent(context, id, userId, userName, type));
    }

    public static Intent createLaunchIntent(Context context, long id, long userId,
                                            String userName, String type){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewFragment.EXTRA_ID, id);
        intent.putExtra(WebViewFragment.EXTRA_USER_ID, userId);
        intent.putExtra(WebViewFragment.EXTRA_USERNAME, userName);
        intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
        return intent;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mFragment.isSharePanelShowing()){
                mFragment.dismissSharePanel();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
