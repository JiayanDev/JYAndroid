package com.jiayantech.jyandroid.model;

import com.google.gson.annotations.Expose;
import com.jiayantech.library.base.BaseModel;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Event extends BaseModel {
    public long eventId;
    public long userId;
    public String userName;

    @Expose
    public String desc;
    public long beginTime;
    public long[] categoryIds;
    public String categoryName;
    public String province;
    public String city;
    public String hospitalName;
    public long hospitalId;
    public String doctorName;
    public long applymentCount;
    public long commentCount;
    public long likeCount;
    public int hasLike;  //0表示当前用户没有对此点赞
    public String status;
    public String applyStatus;
    public String coverImg;
    public String thumbnailImg;
}
