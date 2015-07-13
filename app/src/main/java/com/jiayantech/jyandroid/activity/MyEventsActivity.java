package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.MyEventsFragment;
import com.jiayantech.library.base.SingleFragmentActivity;
import com.jiayantech.library.utils.Utils;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyEventsActivity extends SingleFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的活动");
        addBottomButton();
    }

    @Override
    protected Fragment createFragment() {
        return new MyEventsFragment();
    }

    private void addBottomButton() {
        LinearLayout layout_parent = (LinearLayout) findViewById(R.id.layout_parent);
        layout_parent.setBackgroundDrawable(null);
        Button btn = new Button(this);
        btn.setText("继续成为美丽天使");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) Utils.dp2px(10);
        lp.setMargins(margin, margin, margin, margin);
        layout_parent.addView(btn, lp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateEventActivity.class);
            }
        });
    }
}
