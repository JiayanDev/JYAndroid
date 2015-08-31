package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.ApplyEventFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/9.
 */
public class ApplyEventActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        long eventId = intent.getLongExtra(ApplyEventFragment.EVENT_ID, -1);
        String angelAvatar = intent.getStringExtra(ApplyEventFragment.ANGEL_AVATAR);
        String angelName = intent.getStringExtra(ApplyEventFragment.ANGEL_NAME);
        String hospitalAndDoctor = intent.getStringExtra(ApplyEventFragment.HOSPITAL_AND_DOCTOR);
        String project = intent.getStringExtra(ApplyEventFragment.PROJECT_NAME);
        String time = intent.getStringExtra(ApplyEventFragment.EVENT_TIME);

        return ApplyEventFragment.
                newInstance(eventId, angelAvatar, angelName, project, hospitalAndDoctor, time);
    }

}
