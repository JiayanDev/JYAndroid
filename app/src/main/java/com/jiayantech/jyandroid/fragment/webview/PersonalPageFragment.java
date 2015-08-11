package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.MyDiariesActivity;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;

/**
 * Created by liangzili on 15/8/10.
 */
public class PersonalPageFragment extends WebViewFragment{
    Button mPostButton;

    public static PersonalPageFragment newInstance(long userId, String userName){
        PersonalPageFragment fragment = new PersonalPageFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
        args.putString(WebViewFragment.EXTRA_USERNAME, userName);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_PERSONAL_PAGE);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected String onGetUrl() {
        return null;
    }

    @Override
    protected String onGetUrlParams() {
        return null;
    }

    @Override
    protected String onSetTitle() {
        if(AppInitManger.getUserId() == mUserId &&
                AppInitManger.getRole().equals(AppInit.ROLE_ANGEL)){

            return getString(R.string.title_my_personal_page);
        }else{
            return getString(R.string.title_personal_page, new Object[]{mUserName});
        }
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
//        if(AppInitManger.getUserId() == mUserId &&
//                AppInitManger.getRole().equals(AppInit.ROLE_ANGEL)){
        long id = AppInitManger.getUserId();
        if(id == mUserId){
            View bottom = inflater.inflate(R.layout.layout_personal_page_bottom, null);
            mPostButton = (Button)bottom.findViewById(R.id.publish_diary);
            mPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MyDiariesActivity.class);
                }
            });
            return bottom;
        }
        return null;
    }
}
