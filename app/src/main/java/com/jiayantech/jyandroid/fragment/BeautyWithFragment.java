package com.jiayantech.jyandroid.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/6/25.
 */
public class BeautyWithFragment extends BaseFragment {

    public static BeautyWithFragment newInstance(Bundle args) {
        BeautyWithFragment fragment = new BeautyWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String path = "/storage/emulated/0/DCIM/Camera/IMG_20150321_224952.jpg";
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//
//        HttpReq.uploadImage(bitmap, "hello.jpg", new ResponseListener() {
//            @Override
//            public void onResponse(Object o) {
//                Log.d("Beautiful", "haha, upload success");
//                ToastUtil.showMessage(getActivity(), "终于成功啦");
//            }
//        });
        
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty_with, container, false);

        return view;
    }

}
