package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.TopicActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.comm.imageupload.ImageUploader;
import com.jiayantech.library.comm.imageupload.OnUpLoadCompleteListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/6/25.
 */
public class BeautyWithFragment extends BaseFragment {

    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment[] mFragments;

    public static BeautyWithFragment newInstance(Bundle args) {
        BeautyWithFragment fragment = new BeautyWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i = 0; i < 100; i++){
            final int code = i;
            ImageUploader.getInstance().startUpload(i, "localPath", new OnUpLoadCompleteListener() {
                @Override
                public void onUploadSucess(int imageCode, String localPath, String remotePath) {
                    ToastUtil.showMessage(getActivity(), "this is No. " + code + " Message");
                }

                @Override
                public void onUploadFail(int imageCode, String localPath, String errMsg) {

                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty_with, container, false);

        return view;
    }

}
