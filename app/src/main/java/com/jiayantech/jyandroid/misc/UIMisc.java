package com.jiayantech.jyandroid.misc;

import android.view.View;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;

/**
 * Created by liangzili on 15/10/12.
 */
public class UIMisc {
    public static final String ROLE_ANGEL = "angel";
    public static final String ROLE_COMPANY = "company";
    public static final String ROLE_OFFICIAL = "official";
    public static final String ROLE_NORMAL = "normal";

    public static void setRoleTag(String role, ImageView imageView){
        if(role == null){
            return;
        }
        switch (role){
            case ROLE_ANGEL:
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.mipmap.tag_angel);
                break;
            case ROLE_COMPANY:
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.mipmap.tag_company);
                break;
            case ROLE_OFFICIAL:
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.mipmap.tag_offical);
                break;
            case ROLE_NORMAL:
                imageView.setVisibility(View.GONE);
                break;
            default:
                return;
        }
    }
}
