package com.jiayantech.jyandroid.model.web;

import com.google.gson.Gson;

/**
 * Created by liangzili on 15/7/16.
 */
public class BaseNativeResponse<T> {
    private static Gson sGson = new Gson();

    public int code;
    public String msg;
    public T data;

    public String toString(){
        return sGson.toJson(this).replace("\"", "\'");
    }
}
