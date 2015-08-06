package com.jiayantech.jyandroid.eventbus;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditFinishEvent {
    public static final int ACTION_EDIT_NAME = 0;
    public static final int ACTION_EDIT_GENDER = 1;
    public static final int ACTION_EDIT_PROVINCE = 2;
    public static final int ACTION_EDIT_CITY = 3;
    public static final int ACTION_EDIT_BIRTHDAY = 4;
    public static final int ACTION_EDIT_PHONE = 5;
    public static final int ACTION_EDIT_PASSWORD = 6;
    public static final int ACTION_EDIT_AVATAR = 7;

    public int action;
    public String name;
    public int gender;
    public String province;
    public String city;
    public long birthday;

    public String phone;
    public String password;
    public String avatar;
}
