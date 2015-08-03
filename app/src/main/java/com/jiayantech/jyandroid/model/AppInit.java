package com.jiayantech.jyandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class AppInit {
    public static final String ROLE_NORMAL = "normal";
    public static final String ROLE_ANGEL = "angel";
    public String wxReceipt;
    public boolean register;
    public String token;
    public ProjectCategory projectCategory;
    public long id;
    public String avatar;
    public String name;

    public String phoneNum;
    public String phone;
    public boolean bindWX;
    public boolean bindWB;
    public boolean bindQQ;

    /**
     * 是否是美丽天使
     */
    public String role;

    public static class ProjectCategory {
        public int version;
        public ArrayList<Category> data;
    }

    public static class Category implements Parcelable {
        public int id;
        public String name;
        @Expose
        public Integer resId;
        public ArrayList<Category> sub;

        public Category(Parcel source) {
            id = source.readInt();
            name = source.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }

        public static final Parcelable.Creator<Category> CREATOR = new Creator<Category>() {

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
            }

            @Override
            public Category createFromParcel(Parcel source) {
                return new Category(source);
            }
        };

        public static String toNamesString(List<Category> list) {
            if (list.isEmpty()) {
                return "";
            }
            StringBuilder buffer = new StringBuilder(list.size() * 16);
            Iterator<Category> it = list.iterator();
            while (it.hasNext()) {
                AppInit.Category next = it.next();
                buffer.append(next.name);
                if (it.hasNext()) {
                    buffer.append(" ");
                }
            }
            return buffer.toString();
        }
    }
}
