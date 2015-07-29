package com.jiayantech.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.helper.ActivityResultHelper;

/**
 * Created by janseon on 2015/6/29.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseFragment extends Fragment {
    protected ViewGroup mRoot;

    protected int getInflaterResId() {
        return 0;
    }

    protected void onInitView() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = (ViewGroup) onInflateView(inflater, container);
            onInitView();
        }
        return mRoot;
    }

    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getInflaterResId(), container, false);
    }


    public final View findViewById(int id) {
        if (mRoot == null) {
            return null;
        }
        return mRoot.findViewById(id);
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }


    public void startActivityForResult(Intent intent, int requestCode, ActivityResult activityResult) {
        startActivityForResult(intent, requestCode);
        mActivityResultHelper.addActivityResult(activityResult);
    }

    public void startActivityForResult(Intent intent, ActivityResult activityResult) {
        startActivityForResult(intent, ActivityResult.REQUEST_CODE_DEFAUTE, activityResult);
    }

    private ActivityResultHelper mActivityResultHelper = new ActivityResultHelper();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityResultHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
