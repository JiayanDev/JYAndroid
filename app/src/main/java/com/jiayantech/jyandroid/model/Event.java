package com.jiayantech.jyandroid.model;

import com.jiayantech.library.base.BaseModel;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Event extends BaseModel {
    public long userId;
    public String desc;
    public double applyBeginTime;
    //public double beginTime;
    public String categoryId;
    public String categoryName;
    public String province;
    public String city;
    //public long hospitalId;
    public String hospitalName;
    public String doctorName;
    //public long doctorId;
    //public String[] photos;
    public long applymentCount;
    public long commentCount;
    public long likeCount;
    public int hasLike;  //0表示当前用户没有对此点赞
}
