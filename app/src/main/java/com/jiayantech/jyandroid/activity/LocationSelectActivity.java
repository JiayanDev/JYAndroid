package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.eventbus.CitySelectEvent;
import com.jiayantech.jyandroid.eventbus.ProvinceSelectEvent;
import com.jiayantech.jyandroid.fragment.webview.LocationSelectFragment;
import com.jiayantech.library.base.BaseActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/31.
 */
public class LocationSelectActivity extends BaseActivity{
    private String mProvince;
    private String mCity;
    private LocationSelectFragment mProvinceSelectFragment;
    private LocationSelectFragment mCitySelectFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mFragmentManager = getSupportFragmentManager();
        if(mProvinceSelectFragment == null){
            mProvinceSelectFragment = LocationSelectFragment.
                    newInstance(LocationSelectFragment.TYPE_PROVINCE, null);
            mFragmentManager.beginTransaction().add(R.id.fragment_container,
                    mProvinceSelectFragment, "province").commit();
        }

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(ProvinceSelectEvent event){
        mProvince = event.provinceName;
        mCitySelectFragment = LocationSelectFragment.
                newInstance(LocationSelectFragment.TYPE_CITY, mProvince);
        mFragmentManager.beginTransaction().replace(R.id.fragment_container,
                mCitySelectFragment, "city").addToBackStack("city").commit();
    }

    public void onEvent(CitySelectEvent event){
        mCity = event.cityName;
        Intent intent = new Intent();
        intent.putExtra("province", mProvince);
        intent.putExtra("city", mCity);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
