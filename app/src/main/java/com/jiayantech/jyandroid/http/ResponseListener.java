package com.jiayantech.jyandroid.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by janseon on 2015/6/28.
 *
 * @Description: Response Listener
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class ResponseListener<T> implements Response.Listener<T> {
    public void onErrorResponse(VolleyError error) {
    }
}
