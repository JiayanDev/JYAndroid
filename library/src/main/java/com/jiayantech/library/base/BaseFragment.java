package com.jiayantech.library.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

}
