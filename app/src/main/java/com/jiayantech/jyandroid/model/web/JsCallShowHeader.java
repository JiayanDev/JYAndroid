package com.jiayantech.jyandroid.model.web;

/**
 * Created by liangzili on 15/9/14.
 */
public class JsCallShowHeader extends BaseJsCall<JsCallShowHeader.UserInfo>{
    public static class UserInfo{
        public String province;
        public String city;
        public String name;
        public int gender;
        public int age;
        public String phone;
        public String role;
        public long id;
        public String avatar;
    }
}
