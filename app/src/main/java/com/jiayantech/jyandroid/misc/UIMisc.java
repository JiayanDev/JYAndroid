package com.jiayantech.jyandroid.misc;

import android.widget.ImageView;

import com.jiayantech.jyandroid.R;

/**
 * Created by liangzili on 15/10/12.
 */
public class UIMisc {
    public static final String ROLE_ANGEL = "angel";
    public static final String ROLE_COMPANY = "company";
    public static final String ROLE_OFFICIAL = "official";
    public static void setRoleTag(String role, ImageView imageView){
        switch (role){
            case ROLE_ANGEL:
                imageView.setImageResource(R.mipmap.tag_angel);
                break;
            case ROLE_COMPANY:
                imageView.setImageResource(R.mipmap.tag_company);
                break;
            case ROLE_OFFICIAL:
                imageView.setImageResource(R.mipmap.tag_offical);
                break;
            default:
                return;
        }
    }
}
