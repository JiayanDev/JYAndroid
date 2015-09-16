package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;

/**
 * Created by liangzili on 15/8/7.
 */
public class SuccessActivity extends BaseActivity {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMAGE = "image";

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        mImage = (ImageView) findViewById(R.id.img_success);
        mImage.setImageResource(getIntent().getIntExtra(EXTRA_IMAGE, -1));
        setTitle(getIntent().getStringExtra(EXTRA_IMAGE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_finish:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launchActivity(Context context, String title, @DrawableRes int drawableId) {
        Intent intent = new Intent(context, SuccessActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_IMAGE, drawableId);
        context.startActivity(intent);
    }
}
