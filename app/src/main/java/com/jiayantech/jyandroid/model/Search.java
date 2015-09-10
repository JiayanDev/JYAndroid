package com.jiayantech.jyandroid.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;

import com.google.gson.annotations.Expose;
import com.jiayantech.library.base.BaseModel;

/**
 * Created by janseon on 2015/7/14.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Search extends BaseModel implements Parcelable {
    public String name;
    public long hospitalId;
    public String hospitalName;
    @Expose
    public SpannableString spannableName;

    public Search() {
    }

    public Search(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Search(Parcel source) {
        id = source.readLong();
        name = source.readString();
        hospitalId = source.readLong();
        hospitalName = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(hospitalId);
        dest.writeString(hospitalName);
    }

    public static final Parcelable.Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }

        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }
    };
}
