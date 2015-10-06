package com.jiayantech.jyandroid.model.web;

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
    }
}
