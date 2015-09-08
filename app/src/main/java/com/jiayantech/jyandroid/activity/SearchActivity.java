package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.SearchAdapter;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.model.Search;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchActivity extends BaseActivity implements TextWatcher {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_HOSPITAL_ID = "hospitalId";
    public static final String KEY_HOSPITAL_NAME = "hospitalName";

    private EditText edit_search;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;

    private String title;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }

    protected void initViews() {
        Intent intent = getIntent();
        title = intent.getStringExtra(KEY_TITLE);
        action = intent.getStringExtra(KEY_ACTION);
        setTitle(title);
        setDisplayHomeAsUpEnabled();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        edit_search = (EditText) findViewById(R.id.edit_search);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        edit_search.setHint(getString(R.string.hint_search_input, title));

        edit_search.addTextChangedListener(this);
        hideSoftKeyboard();

        mAdapter = new SearchAdapter(null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            String name = edit_search.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showMessage(getString(R.string.hint_search_input, title));
                return true;
            }
            Intent intent = new Intent();
            intent.putExtra(KEY_NAME, edit_search.getText().toString());
            ActivityResult.onFinishResult(SearchActivity.this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mCurBlurName = s.toString();
        edit_search.removeCallbacks(mSearchRunnable);
        edit_search.postDelayed(mSearchRunnable, 200);
    }

    private String mCurBlurName;
    private Runnable mSearchRunnable = new Runnable() {
        @Override
        public void run() {
            final String blurName = mCurBlurName;
            CommBiz.option(action, blurName, new ResponseListener<AppResponse<ArrayList<Search>>>() {
                @Override
                public void onResponse(AppResponse<ArrayList<Search>> appResponse) {
                    if (!blurName.equals(mCurBlurName)) {
                        return;
                    }
                    mAdapter = new SearchAdapter(appResponse.data, blurName);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BaseSimpleModelAdapter.OnItemClickListener<Search>() {
                        @Override
                        public void onItemClick(BaseSimpleModelAdapter<Search> adapter, int position, Search search) {
                            Intent intent = new Intent();
                            intent.putExtra(KEY_ID, search.id);
                            intent.putExtra(KEY_NAME, search.name);
                            if (search.hospitalId != 0) {
                                intent.putExtra(KEY_HOSPITAL_ID, search.hospitalId);
                                intent.putExtra(KEY_HOSPITAL_NAME, search.hospitalName);
                            }
                            ActivityResult.onFinishResult(SearchActivity.this, intent);
                        }
                    });
                }
            });
        }
    };

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
    }

    public static final String KEY_TITLE = "title";
    public static final String KEY_ACTION = "action";

    public static void start(Fragment fragment, String title, String action, ActivityResult activityResult) {
        BaseActivity activity = (BaseActivity) fragment.getActivity();
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_ACTION, action);
        activity.startActivityForResult(intent, activityResult);
    }

    public static void start(BaseActivity activity, String title, String action, ActivityResult activityResult) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_ACTION, action);
        activity.startActivityForResult(intent, activityResult);
    }
}

