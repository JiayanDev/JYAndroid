package com.jiayantech.jyandroid.model.web;

import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;

/**
 * Created by liangzili on 15/9/17.
 */
public class UserInfo extends BaseNativeResponse<UserInfo.Info>{
    public static class Info{
        public long id;
        public String nickname;
        public String phone;
        public String avatar;
        public String token;
        public String province;
        public String city;
        public int gender;
    }

    public UserInfo(){}
    public UserInfo(AppInit appInit){
        data = new UserInfo.Info();
        data.id = AppInitManger.getUserId();
        data.nickname = AppInitManger.getUserName();
        data.phone = AppInitManger.getPhoneNum();
        data.token = AppInitManger.getToken();
        data.avatar = AppInitManger.getAvatar();
        data.province = AppInitManger.getProvince();
        data.city = AppInitManger.getCity();
        data.gender = AppInitManger.getUserGender();
    }
}
