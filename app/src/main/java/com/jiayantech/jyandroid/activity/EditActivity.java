package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;

/**
 * Created by janseon on 15/9/8.
 */
public class EditActivity extends BaseActivity {
    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_TEXT = "extra_text";

    private static final int PHONE_NUM_LENGTH = 11;

    protected EditText edit_text;
    private ImageView img_delete;
    private String mTitle;
    protected String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setViews();
    }


    private void setViews() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(EXTRA_TITLE);
        mText = intent.getStringExtra(EXTRA_TEXT);

        edit_text = (EditText) findViewById(R.id.edit_text);
        img_delete = (ImageView) findViewById(R.id.img_delete);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text.setText("");
            }
        });

        if (TextUtils.isEmpty(mTitle)) mTitle = getString(R.string.edit);
        setTitle(mTitle);
        edit_text.setText(mText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mText != null) {
            edit_text.setSelection(mText.length());
        }else{
            mText = "";
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UIUtil.showSoftKeyBoard(EditActivity.this, edit_text);
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onSave(edit_text.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onSave(String text) {
        if (TextUtils.isEmpty(text)) {
            ToastUtil.showMessage(R.string.hint_input_null);
            return;
        } else if (getString(R.string.phone).equals(mTitle)) {
            if (!TextUtils.isDigitsOnly(text) || text.length() != PHONE_NUM_LENGTH) {
                ToastUtil.showMessage(R.string.hint_input_error);
                return;
            }
        }
        ActivityResult.onFinishResult(this, text);
    }

    public static void start(BaseActivity context, int titleId, String text, ActivityResult activityResult) {
        start(context, context.getString(titleId), text, EditActivity.class, activityResult);
    }

    public static void start(BaseActivity context, String title, String text, Class<? extends EditActivity> cls, ActivityResult activityResult) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_TEXT, text);
        if (activityResult == null) {
            context.startActivity(intent);
        } else {
            context.startActivityForResult(intent, activityResult);
        }
    }
}
