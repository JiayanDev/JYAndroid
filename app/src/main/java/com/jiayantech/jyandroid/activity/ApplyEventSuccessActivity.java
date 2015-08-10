package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;

/**
 * Created by liangzili on 15/8/7.
 */
public class ApplyEventSuccessActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_event_success);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_finish:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
