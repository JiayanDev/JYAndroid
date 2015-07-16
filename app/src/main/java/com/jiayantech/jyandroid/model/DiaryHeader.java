package com.jiayantech.jyandroid.model;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class DiaryHeader extends CategoryModel {
    public long userId;
    public String verifyStatus;
    public String hospitalId;
    public String doctorId;
    public String projectId;
    public String projectName;
    public double operationTime;
    public long price;
    public float satisfyLevel;
    public String tags;
    public double lastModifyTime;
    public String[] previousPhotos;
    public String currentPhoto;
}
