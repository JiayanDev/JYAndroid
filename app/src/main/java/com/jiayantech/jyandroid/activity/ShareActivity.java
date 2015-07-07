package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * Created by liangzili on 15/7/6.
 */
public class ShareActivity extends BaseActivity{

    private Button mShare;
    final UMSocialService mController = UMServiceFactory.getUMSocialService(
            "com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mShare = (Button)findViewById(R.id.share);
        mController.setShareContent("大家好，我是友盟分享");

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.openShare(ShareActivity.this, false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
