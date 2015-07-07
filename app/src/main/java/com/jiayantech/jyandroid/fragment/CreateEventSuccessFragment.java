package com.jiayantech.jyandroid.fragment;

import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseFragment;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CreateEventSuccessFragment extends BaseFragment implements View.OnClickListener {
    private Button btn_finish;

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_create_event_success;
    }

    @Override
    protected void onInitView() {
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                getActivity().finish();
                break;
        }
    }
}
