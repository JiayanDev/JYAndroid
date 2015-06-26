package com.jiayantech.jyandroid.customwidget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liangzili on 15/6/24.
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
            mRoot = (ViewGroup) inflater.inflate(getInflaterResId(), container, false);
            onInitView();
        }
        return mRoot;
    }


    public final View findViewById(int id) {
        if (mRoot == null) {
            return null;
        }
        return mRoot.findViewById(id);
    }

}
