package com.jiayantech.library.comm;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 健兴
 * @version 1.0
 * @Description 给应用程序存储数据
 * @date 2014-4-19
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class DataShared {

    SharedPreferences Spkeywords;

    public DataShared(Context context, String Filename) {
        Spkeywords = context.getSharedPreferences(Filename, Activity.MODE_PRIVATE);
    }

    /**
     * 根据键值获取数据
     */
    public Map<String, ?> getAll() {
        return Spkeywords.getAll();
    }

    /**
     * 根据键值获取数据
     */
    public String get(String key) {
        return Spkeywords.getString(key, "");
    }

    /**
     * 保存数据
     */
    public void put(String key, String value) {
        SharedPreferences.Editor editor = Spkeywords.edit();// 取得编辑对象
        editor.putString(key, value);// 添加值
        editor.commit();// 提交保存
    }

//	/** 根据键值获取保密数据，并且进行解密 */
//	public String get(String key, boolean endecode) {
//		String value = Spkeywords.getString(key, "");
//		if (endecode) {
//			value = DES.decode(value, "");
//		}
//		return value;
//	}
//
//	/** 保存保密数据 */
//	public void put(String key, String value, boolean endecode) {
//		SharedPreferences.Editor editor = Spkeywords.edit();// 取得编辑对象
//		if (endecode) {
//			value = DES.encode(value, "");
//		}
//		editor.putString(key, value);// 添加值
//		editor.commit();// 提交保存
//		return;
//	}

    // ////////////////

    /**
     * 获取int类型数据
     */
    public int getInt(String key) {
        return Spkeywords.getInt(key, -1);
    }

    /**
     * 获取Long类型数据
     */
    public long getLong(String key) {
        return Spkeywords.getLong(key, -1);
    }

    /**
     * 获取Boolean类型数据
     */
    public boolean getBoolean(String key) {
        return Spkeywords.getBoolean(key, false);
    }

    /**
     * 获取Boolean类型数据
     */
    public boolean getBoolean(String key, boolean defValue) {
        return Spkeywords.getBoolean(key, defValue);
    }

    /**
     * 保存int类型数据
     */
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = Spkeywords.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存Long类型数据
     */
    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = Spkeywords.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存Boolean类型数据
     */
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = Spkeywords.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = Spkeywords.edit();
        editor.clear();
        editor.commit();
    }
}
