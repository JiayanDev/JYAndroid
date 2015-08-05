package com.jiayantech.jyandroid.manager;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.model.location.Province;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/8/4.
 * 管理省份城市信息
 */
public class LocationManager {
    public static LocationManager sLocationManager;
    private List<Province> mLocationList;

    private LocationManager() {
    }

    public static LocationManager getInstance() {
        if (sLocationManager == null) {
            sLocationManager = new LocationManager();
        }
        return sLocationManager;
    }

    public List<Province> getLocationList() {
        if(mLocationList == null) {
            long startTime = System.currentTimeMillis();
            parse();
            long stopTime = System.currentTimeMillis();
            LogUtil.i("LocationManager", "parse json costs " + (stopTime - startTime));
        }
        return mLocationList;
    }

    private void parse() {
        try {
            Reader jsonReader = new InputStreamReader(BaseApplication.getContext().getAssets().open("area.json"));
            mLocationList = GsonUtils.build().fromJson(jsonReader, new ArrayList<Province>().getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
