package com.jiayantech.library.http;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: rr
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class AppResponse<T> {
    public int code;
    public String msg;
    public T data;
    public Object obj;
    public Object extend;

}
