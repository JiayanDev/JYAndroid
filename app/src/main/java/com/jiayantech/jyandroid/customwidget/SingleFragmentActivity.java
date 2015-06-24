package com.jiayantech.jyandroid.customwidget;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.jiayantech.jyandroid.R;

/**
 * Created by liangzili on 15/6/24.
 */
public abstract class SingleFragmentActivity extends BaseActivity{
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
