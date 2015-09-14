package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.LocationAdapter;
import com.jiayantech.jyandroid.biz.GeoBiz;
import com.jiayantech.jyandroid.eventbus.CitySelectEvent;
import com.jiayantech.jyandroid.eventbus.ProvinceSelectEvent;
import com.jiayantech.jyandroid.manager.LocationManager;
import com.jiayantech.jyandroid.model.location.BaseLocation;
import com.jiayantech.library.base.BaseFragment;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/28.
 */
public class LocationSelectFragment extends BaseFragment{
    private static final String TAG = "LocationSelectFragment";

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_PROVINCE = "province";
    public static final int TYPE_PROVINCE = 0;
    public static final int TYPE_CITY = 1;

    private List<? extends BaseLocation> mLocationList;
    private String mProvince;
    private int mType;

    private TextView mAutoLocation;
    private RecyclerView mLocationView;

    private LocationAdapter.LocationClickListener mLocationClickListener;

    public static LocationSelectFragment newInstance(int type, String province){
        LocationSelectFragment fragment = new LocationSelectFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TYPE, type);
        if(type == TYPE_CITY){
            args.putString(EXTRA_PROVINCE, province);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_location_select;
    }

    @Override
    protected void onInitView() {
        mType = getArguments().getInt(EXTRA_TYPE);

        if(mType == TYPE_CITY){
            mProvince = getArguments().getString(EXTRA_PROVINCE);
            mLocationList = LocationManager.getInstance().getCityList(mProvince);
        }else{
            mLocationList = LocationManager.getInstance().getProvinceList();
            ViewStub stub = (ViewStub)findViewById(R.id.layout_auto_position);
            stub.inflate();
            mAutoLocation = (TextView)findViewById(R.id.txt_auto_location);
        }

        mLocationView = (RecyclerView)findViewById(R.id.list_location);

        mLocationClickListener = new LocationAdapter.LocationClickListener() {
            @Override
            public void onLocationClick(String name) {
                if(mType == TYPE_CITY){
                    EventBus.getDefault().post(new CitySelectEvent(name));
                }else{
                    EventBus.getDefault().post(new ProvinceSelectEvent(name));
                }
            }
        };

        mLocationView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocationView.setAdapter(new LocationAdapter(getActivity(), mLocationList,
                mLocationClickListener));

        if(mType == TYPE_PROVINCE) {
            mAutoLocation.setEnabled(false);
            GeoBiz.requestLocation(new GeoBiz.LocationRequestListener() {

                @Override
                public void onRequestLocationSuccess(final String province, final String city) {
                    mAutoLocation.setText(province + " " + city);
                    mAutoLocation.setEnabled(true);
                    mAutoLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post(new ProvinceSelectEvent(province));
                            EventBus.getDefault().post(new CitySelectEvent(city));
                        }
                    });
                }

                @Override
                public void onRequestLocationFailed(String errMsg) {
                    mAutoLocation.setText(errMsg);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mType == TYPE_CITY){
            getActivity().setTitle("选择城市");
        }else{
            getActivity().setTitle("选择省份");
        }
    }
}
