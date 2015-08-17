package com.jiayantech.jyandroid.model.web;

/**
 * Created by liangzili on 15/8/11.
 */
public class JsCallUserInfo extends BaseJsCall<JsCallUserInfo.UserInfo>{
    public static class UserInfo{
        public long id;
        public String name;
        public String phone;
        public String token;
    }
}
