package com.jiayantech.jyandroid.biz;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jiayantech.jyandroid.app.JYApplication;

/**
 * Created by liangzili on 15/8/27.
 */
public class GeoBiz {
    /**
     * 请求baidu获取当前地址
     * @param listener
     */
    public static void requestLocation(final LocationRequestListener listener){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationPoiList(true);

        final LocationClient locationClient = new LocationClient(JYApplication.getContext(), option);

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                String province = bdLocation.getProvince();
                if(city == null || province == null){
                    listener.onRequestLocationFailed("定位失败, 请手动选择地区!");
                }else {
                    if (province.contains("省")) {
                        province = province.replace("省", "");
                    }
                    if (city.contains("市")) {
                        city = city.replace("市", "");
                    }
                    listener.onRequestLocationSuccess(province, city);
                }
                locationClient.stop();
            }
        });
        locationClient.start();
    }

    /**
     * 请求baidu获取地址的回调，成功时调用onRequestLocationSuccess(String),
     * 参数为获取到的地址，xx省xx市
     *
     */
    public interface LocationRequestListener{
        void onRequestLocationSuccess(String province, String city);

        void onRequestLocationFailed(String errMsg);
    }
}
