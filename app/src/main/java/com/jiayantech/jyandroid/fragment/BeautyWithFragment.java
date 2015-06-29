package com.jiayantech.jyandroid.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.TopicActivity;
import com.jiayantech.library.base.BaseFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty_with, container, false);
        Button jump = (Button) view.findViewById(R.id.jump);
        initFragments();


        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initFragments() {
        ActivityFragment activityFragment = new ActivityFragment();
        UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(null);

        mFragments = new Fragment[]{
                activityFragment, userInfoFragment
        };
    }
}
