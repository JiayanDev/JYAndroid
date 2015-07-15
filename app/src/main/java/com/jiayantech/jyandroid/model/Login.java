package com.jiayantech.jyandroid.model;

import java.util.Map;

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

        public Map<String, String> data;
    }
}
