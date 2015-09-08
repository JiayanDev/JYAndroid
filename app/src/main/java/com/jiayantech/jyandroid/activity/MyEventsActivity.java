package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.AboutAngelFragment;
import com.jiayantech.jyandroid.fragment.MyEventsFragment;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.base.SingleFragmentActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.utils.Utils;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyEventsActivity extends SingleFragmentActivity implements MyEventsFragment.OnRefreshResponseListener {
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_mine_angel);
        addBottomButton();
    }

    @Override
    protected Fragment createFragment() {
        MyEventsFragment fragment = new MyEventsFragment();
        fragment.setOnRefreshResponseListener(this);
        return fragment;
    }

    private void addBottomButton() {
        LinearLayout layout_parent = (LinearLayout) findViewById(R.id.layout_parent);
        btn = new Button(this);
        btn.setBackgroundResource(R.drawable.bg_round_theme);
        btn.setText(R.string.become_angel);
        btn.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = -(int) Utils.dp2px(4);
        lp.setMargins(margin, 0, margin, 0);
        layout_parent.addView(btn, lp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(BecomeAngelActivity.class, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        if (mFragment != null) {
                            ((MyEventsFragment) mFragment).setRefreshing(true);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRefreshResponse(BaseSimpleModelAdapter<Event> adapter) {
        if (adapter.getList().size() <= 0) {
            replace(new AboutAngelFragment());
        } else {
            btn.setText(R.string.continue_apply_angel);
        }
    }
}
