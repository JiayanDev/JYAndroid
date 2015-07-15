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
public class Login {
    public String token;
    public ProjectCategory projectCategory;

    public static class ProjectCategory {
        public int version;
        public ArrayList<Category> data;
    }

    public static class Category implements Parcelable {
        public int id;
        public String name;
        @Expose
        public Integer reaId;
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
                Login.Category next = it.next();
                buffer.append(next.name);
                if (it.hasNext()) {
                    buffer.append(" ");
                }
            }
            return buffer.toString();
        }

        public static String toIdsString(List<Category> list) {
            if (list.isEmpty()) {
                return "[]";
            }

            StringBuilder buffer = new StringBuilder(list.size() * 16);
            buffer.append('[');
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                buffer.append(next);
                if (it.hasNext()) {
                    buffer.append(", ");
                }
            }
            buffer.append(']');
            return buffer.toString();
        }
    }
}
