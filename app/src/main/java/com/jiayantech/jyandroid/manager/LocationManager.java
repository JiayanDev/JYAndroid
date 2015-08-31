package com.jiayantech.jyandroid.manager;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.model.location.City;
import com.jiayantech.jyandroid.model.location.Province;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.utils.GsonUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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

    public List<Province> getProvinceList() {
        if(mLocationList == null) {
            parse();
        }
        return mLocationList;
    }

    public List<City> getCityList(String province){
        if(mLocationList == null){
            parse();
        }
        for(Province p: mLocationList){
            if(p.name.equals(province)){
                return p.children;
            }
        }
        return null;
    }

    private void parse() {
        try {
            Reader jsonReader = new InputStreamReader(BaseApplication.getContext().getAssets().open("area.json"));
//            mLocationList = GsonUtils.build().fromJson(jsonReader,
//                    new ArrayList<Province>().getClass());
            mLocationList = GsonUtils.build().fromJson(jsonReader,
                    new TypeToken<List<Province>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
